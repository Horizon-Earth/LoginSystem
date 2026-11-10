package loginsystem.auth;

import com.sun.net.httpserver.HttpServer;

import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** Login com o Google usando OAuth 2.0, PKCE e navegador externo. */
public final class AutenticacaoGoogle {

    private static final String ARQUIVO_CONFIG = "resources/google.properties";
    private static final String URL_AUTORIZACAO =
            "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String URL_TOKEN = "https://oauth2.googleapis.com/token";
    private static final String URL_USUARIO = "https://www.googleapis.com/oauth2/v3/userinfo";
    private static final long TEMPO_LIMITE_MILLIS = TimeUnit.MINUTES.toMillis(3);
    private static final Pattern CAMPO_JSON = Pattern.compile(
            "\\\"([^\\\"]+)\\\"\\s*:\\s*\\\"((?:\\\\.|[^\\\"\\\\])*)\\\"");

    private AutenticacaoGoogle() {
    }

    /** Autentica com o Google; retorna null se a pessoa cancelar o consentimento. */
    public static DadosGoogle autenticar() throws Exception {
        Properties cfg = carregarConfig();
        String clientId = cfg.getProperty("google.client.id");
        if (clientId == null || clientId.isBlank() || clientId.startsWith("SEU_")) {
            throw new IOException("Configure google.client.id em resources/google.properties.");
        }
        String clientSecret = cfg.getProperty("google.client.secret");
        if (clientSecret == null || clientSecret.isBlank() || clientSecret.startsWith("SEU_")) {
            throw new IOException("Configure google.client.secret em resources/google.properties "
                    + "com o secret do cliente OAuth do tipo Desktop.");
        }
        return fluxoOAuth2(clientId.trim(), clientSecret.trim());
    }

    public static boolean temCredenciaisConfiguradas() {
        try {
            String id = carregarConfig().getProperty("google.client.id");
            String secret = carregarConfig().getProperty("google.client.secret");
            return id != null && !id.isBlank() && !id.startsWith("SEU_")
                    && secret != null && !secret.isBlank() && !secret.startsWith("SEU_");
        } catch (IOException e) {
            return false;
        }
    }

    private static DadosGoogle fluxoOAuth2(String clientId, String clientSecret) throws Exception {
        String state = aleatorio(32);
        String verifier = aleatorio(64);
        String challenge = Base64.getUrlEncoder().withoutPadding().encodeToString(
                MessageDigest.getInstance("SHA-256").digest(verifier.getBytes(StandardCharsets.US_ASCII)));
        Holder<String> codigo = new Holder<>();
        Holder<String> estadoRecebido = new Holder<>();
        Holder<String> erro = new Holder<>();
        CountDownLatch latch = new CountDownLatch(1);

        HttpServer servidor = HttpServer.create(
                new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 0), 0);
        // Clientes OAuth do tipo Desktop usam o endereço loopback com porta dinâmica.
        // Não acrescentamos caminho: o URI autorizado pelo Google é
        // http://127.0.0.1:<porta>.
        String redirectUri = "http://127.0.0.1:" + servidor.getAddress().getPort();
        servidor.createContext("/", troca -> {
            try {
                if (!"GET".equals(troca.getRequestMethod())) {
                    enviar(troca, 405, "Método não permitido.");
                    return;
                }
                Map<String, String> params = parametros(troca.getRequestURI().getRawQuery());
                erro.value = params.get("error");
                estadoRecebido.value = params.get("state");
                codigo.value = params.get("code");
                enviar(troca, 200, erro.value == null
                        ? "Login com o Google concluído. Você pode fechar esta janela."
                        : "Login com o Google cancelado. Você pode fechar esta janela.");
            } finally {
                latch.countDown();
            }
        });
        servidor.setExecutor(r -> {
            Thread thread = new Thread(r, "google-oauth-callback");
            thread.setDaemon(true);
            thread.start();
        });
        servidor.start();

        try {
            String url = URL_AUTORIZACAO + "?client_id=" + codificar(clientId)
                    + "&redirect_uri=" + codificar(redirectUri)
                    + "&response_type=code&scope=" + codificar("openid email profile")
                    + "&state=" + codificar(state)
                    + "&code_challenge=" + codificar(challenge)
                    + "&code_challenge_method=S256";
            if (!Desktop.isDesktopSupported()
                    || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                throw new IOException("Não foi possível abrir o navegador padrão para autenticar.");
            }
            Desktop.getDesktop().browse(URI.create(url));

            if (!latch.await(TEMPO_LIMITE_MILLIS, TimeUnit.MILLISECONDS)) {
                throw new IOException("Tempo esgotado aguardando o retorno do Google.");
            }
        } finally {
            servidor.stop(0);
        }

        if (erro.value != null) {
            if ("access_denied".equals(erro.value)) {
                return null;
            }
            throw new IOException("O Google recusou a autenticação (" + erro.value + ").");
        }
        if (codigo.value == null || !state.equals(estadoRecebido.value)) {
            throw new IOException("Resposta OAuth inválida. Tente novamente.");
        }

        String corpoToken = postForm(URL_TOKEN, Map.of(
                "code", codigo.value,
                "client_id", clientId,
                "client_secret", clientSecret,
                "redirect_uri", redirectUri,
                "grant_type", "authorization_code",
                "code_verifier", verifier));
        String accessToken = extrairJson(corpoToken, "access_token");
        if (accessToken == null || accessToken.isBlank()) {
            throw new IOException("O Google não retornou um token de acesso válido.");
        }

        HttpClient cliente = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(15)).build();
        HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(URL_USUARIO))
                .timeout(java.time.Duration.ofSeconds(20))
                .header("Authorization", "Bearer " + accessToken).GET().build();
        HttpResponse<String> resposta = cliente.send(requisicao,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (resposta.statusCode() != 200) {
            throw new IOException("Não foi possível obter a conta Google (HTTP "
                    + resposta.statusCode() + ").");
        }
        String corpoUsuario = resposta.body();
        String idGoogle = extrairJson(corpoUsuario, "sub");
        String email = extrairJson(corpoUsuario, "email");
        boolean emailVerificado = Pattern.compile("\\\"email_verified\\\"\\s*:\\s*true")
                .matcher(corpoUsuario).find();
        if (idGoogle == null || idGoogle.isBlank() || email == null || email.isBlank()
                || !emailVerificado) {
            throw new IOException("O Google não confirmou uma conta com e-mail verificado.");
        }
        return new DadosGoogle(idGoogle, email, extrairJson(corpoUsuario, "name"),
                extrairJson(corpoUsuario, "picture"));
    }

    private static String postForm(String url, Map<String, String> form) throws Exception {
        String corpo = form.entrySet().stream()
                .map(e -> codificar(e.getKey()) + "=" + codificar(e.getValue()))
                .collect(Collectors.joining("&"));
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .timeout(java.time.Duration.ofSeconds(20))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(corpo)).build();
        HttpResponse<String> resposta = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(15)).build()
                .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (resposta.statusCode() != 200) {
            throw new IOException("A troca do código OAuth falhou (HTTP "
                    + resposta.statusCode() + "). Confira o Client ID e a configuração OAuth.");
        }
        return resposta.body();
    }

    private static String extrairJson(String json, String chave) {
        Matcher matcher = CAMPO_JSON.matcher(json);
        while (matcher.find()) {
            if (chave.equals(matcher.group(1))) {
                return desescaparJson(matcher.group(2));
            }
        }
        return null;
    }

    private static String desescaparJson(String valor) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);
            if (c != '\\' || i + 1 >= valor.length()) {
                resultado.append(c);
                continue;
            }
            char escape = valor.charAt(++i);
            switch (escape) {
                case '"': case '\\': case '/': resultado.append(escape); break;
                case 'b': resultado.append('\b'); break;
                case 'f': resultado.append('\f'); break;
                case 'n': resultado.append('\n'); break;
                case 'r': resultado.append('\r'); break;
                case 't': resultado.append('\t'); break;
                case 'u':
                    if (i + 4 < valor.length()) {
                        resultado.append((char) Integer.parseInt(valor.substring(i + 1, i + 5), 16));
                        i += 4;
                    }
                    break;
                default: resultado.append(escape);
            }
        }
        return resultado.toString();
    }

    private static String aleatorio(int bytes) {
        byte[] valor = new byte[bytes];
        new SecureRandom().nextBytes(valor);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(valor);
    }

    private static String codificar(String valor) {
        return URLEncoder.encode(valor, StandardCharsets.UTF_8);
    }

    private static Map<String, String> parametros(String query) {
        Map<String, String> valores = new HashMap<>();
        if (query == null || query.isBlank()) return valores;
        for (String parte : query.split("&")) {
            String[] par = parte.split("=", 2);
            valores.put(decodificar(par[0]), par.length > 1 ? decodificar(par[1]) : "");
        }
        return valores;
    }

    private static String decodificar(String valor) {
        return URLDecoder.decode(valor, StandardCharsets.UTF_8);
    }

    private static void enviar(com.sun.net.httpserver.HttpExchange troca, int status, String texto)
            throws IOException {
        byte[] corpo = ("<!doctype html><meta charset=\"utf-8\"><title>Login</title><p>"
                + texto + "</p>").getBytes(StandardCharsets.UTF_8);
        troca.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        troca.getResponseHeaders().set("Cache-Control", "no-store");
        troca.sendResponseHeaders(status, corpo.length);
        try (OutputStream saida = troca.getResponseBody()) {
            saida.write(corpo);
        }
    }

    private static Properties carregarConfig() throws IOException {
        Properties config = new Properties();
        try (FileInputStream arquivo = new FileInputStream(ARQUIVO_CONFIG)) {
            config.load(new java.io.InputStreamReader(arquivo, StandardCharsets.UTF_8));
        }
        return config;
    }

    private static final class Holder<T> {
        T value;
    }
}
