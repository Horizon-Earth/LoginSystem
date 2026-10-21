package loginsystem.ui;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loginsystem.auth.Autenticacao;
import loginsystem.model.Sessao;
import loginsystem.model.Usuario;
import loginsystem.util.Alertas;

/**
 * Tela de login do Horizon Earth.
 * Só entra quem já está cadastrado no banco; quem não tem conta
 * usa o link "Cadastre-se" (TelaCadastro).
 */
public class TelaLogin {

    private final Stage palco;

    public TelaLogin(Stage palco) {
        this.palco = palco;
    }

    public void mostrar() {

        Label titulo = new Label("Entrar");
        titulo.getStyleClass().add("titulo-card");

        Label pergunta = new Label("Não tem uma conta? ");
        pergunta.getStyleClass().add("texto-card");

        Hyperlink linkCadastro = new Hyperlink("Cadastre-se");
        linkCadastro.getStyleClass().add("link-card");
        linkCadastro.setOnAction(e -> new TelaCadastro(palco).mostrar());

        HBox linhaCadastro = new HBox(pergunta, linkCadastro);
        linhaCadastro.setAlignment(Pos.CENTER_LEFT);

        // ----- Usuário -----
        Label labelUsuario = new Label("Usuário");
        labelUsuario.getStyleClass().add("label-card");

        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Seu usuário");
        campoUsuario.getStyleClass().add("campo-card");

        // ----- Senha (com olho) -----
        Label labelSenha = new Label("Senha");
        labelSenha.getStyleClass().add("label-card");

        CampoSenha campoSenha = new CampoSenha("Sua senha");

        // ----- Botão Entrar -----
        Button botaoEntrar = new Button("Entrar no Sistema");
        botaoEntrar.setMaxWidth(Double.MAX_VALUE);
        botaoEntrar.setPrefHeight(48);
        botaoEntrar.getStyleClass().add("botao-principal");
        botaoEntrar.setOnAction(e -> {
            String usuario = campoUsuario.getText().trim();
            String senha = campoSenha.getTexto();

            if (usuario.isEmpty() || senha.isEmpty()) {
                Alertas.mostrarErro("Campos vazios", "Preencha usuário e senha.");
                return;
            }

            // Consulta ao banco e hash rodam em segundo plano (a janela não trava)
            botaoEntrar.setDisable(true);
            botaoEntrar.setText("Entrando...");

            Task<Usuario> tarefa = new Task<>() {
                @Override
                protected Usuario call() throws Exception {
                    return Autenticacao.autenticar(usuario, senha);
                }
            };

            tarefa.setOnSucceeded(ev -> {
                Usuario logado = tarefa.getValue();
                if (logado != null) {
                    // Login ok: cria a sessão (horário capturado aqui) e abre o painel
                    Sessao sessao = new Sessao(logado.getUsuario());
                    new PainelPrincipal(sessao).mostrar(new Stage());
                    palco.close();
                } else {
                    botaoEntrar.setDisable(false);
                    botaoEntrar.setText("Entrar no Sistema");
                    // Mensagem genérica: não revela se foi o usuário ou a senha
                    Alertas.mostrarErro("Falha no login", "Usuário ou senha incorretos.");
                    campoSenha.limpar();
                    campoSenha.focar();
                }
            });

            tarefa.setOnFailed(ev -> {
                botaoEntrar.setDisable(false);
                botaoEntrar.setText("Entrar no Sistema");
                tarefa.getException().printStackTrace();
                Alertas.mostrarErroBanco();
            });

            Thread thread = new Thread(tarefa);
            thread.setDaemon(true);
            thread.start();
        });

        // Enter no usuário vai para a senha; Enter na senha faz o login
        campoUsuario.setOnAction(e -> campoSenha.focar());
        campoSenha.aoConfirmar(botaoEntrar::fire);

        // ----- Divisor "ou" -----
        Label divisorEsquerda = new Label("");
        divisorEsquerda.setMaxWidth(Double.MAX_VALUE);
        divisorEsquerda.getStyleClass().add("linha-divisora");
        HBox.setHgrow(divisorEsquerda, Priority.ALWAYS);

        Label divisorTexto = new Label("ou entre com");
        divisorTexto.getStyleClass().add("texto-divisor");

        Label divisorDireita = new Label("");
        divisorDireita.setMaxWidth(Double.MAX_VALUE);
        divisorDireita.getStyleClass().add("linha-divisora");
        HBox.setHgrow(divisorDireita, Priority.ALWAYS);

        HBox divisor = new HBox(10, divisorEsquerda, divisorTexto, divisorDireita);
        divisor.setAlignment(Pos.CENTER);

        // ----- Botões Google / Apple (ainda não implementados) -----
        Button botaoGoogle = new Button("Google");
        botaoGoogle.setPrefHeight(44);
        botaoGoogle.getStyleClass().add("botao-social");
        HBox.setHgrow(botaoGoogle, Priority.ALWAYS);
        botaoGoogle.setMaxWidth(Double.MAX_VALUE);
        botaoGoogle.setOnAction(e -> Alertas.mostrarIndisponivel());

        Button botaoApple = new Button("Apple");
        botaoApple.setPrefHeight(44);
        botaoApple.getStyleClass().add("botao-social");
        HBox.setHgrow(botaoApple, Priority.ALWAYS);
        botaoApple.setMaxWidth(Double.MAX_VALUE);
        botaoApple.setOnAction(e -> Alertas.mostrarIndisponivel());

        HBox linhaSocial = new HBox(12, botaoGoogle, botaoApple);

        // ==================== MONTAGEM DO CARD ====================
        VBox formulario = new VBox(14,
            titulo, linhaCadastro,
            new VBox(6, labelUsuario, campoUsuario),
            new VBox(6, labelSenha, campoSenha),
            botaoEntrar,
            divisor,
            linhaSocial
        );
        formulario.setSpacing(16);
        formulario.setAlignment(Pos.CENTER_LEFT);
        formulario.setPadding(new Insets(48, 48, 40, 48));
        formulario.setPrefWidth(460);
        formulario.getStyleClass().add("painel-formulario");

        Scene cena = PainelMarca.criarCena(formulario);
        PainelMarca.exibir(palco, cena);
    }
}
