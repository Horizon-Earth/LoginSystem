package loginsystem.ui;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import loginsystem.auth.Autenticacao;
import loginsystem.model.Sessao;
import loginsystem.util.Alertas;
import loginsystem.util.Recursos;

/**
 * Tela de login do Horizon Earth.
 *
 * Monta toda a interface (card central, formulário, botões sociais)
 * e dispara o fluxo de autenticação. Credenciais válidas: root / toor.
 */
public class TelaLogin {

    private static final String CAMINHO_IMAGENS = "resources/icons/";
    private static final String CAMINHO_MAPA    = "resources/images/logo.png";
    private static final String CAMINHO_CSS     = "resources/style.css";

    private final Stage palco;

    public TelaLogin(Stage palco) {
        this.palco = palco;
    }

    public void mostrar() {

        // ==================== LADO ESQUERDO DO CARD: IMAGEM ====================
        ImageView mapa = Recursos.carregarImagem(CAMINHO_MAPA);
        mapa.setFitHeight(300);
        mapa.setPreserveRatio(true);   // sem distorcer a proporção

        // Logo no canto superior esquerdo
        Label logo = new Label("Horizon");
        logo.getStyleClass().add("logo-card");
        StackPane.setAlignment(logo, Pos.TOP_LEFT);
        StackPane.setMargin(logo, new Insets(28, 0, 0, 28));

        // Texto grande no rodapé da imagem
        Label nomeMarca = new Label("Horizon Earth");
        nomeMarca.getStyleClass().add("nome-marca-card");

        Label slogan = new Label("Monitoramento de clima e riscos do planeta.");
        slogan.getStyleClass().add("slogan-card");

        VBox identidade = new VBox(6, nomeMarca, slogan);
        identidade.setPadding(new Insets(0, 0, 48, 28));
        // Importante: sem isso o VBox estica e o texto "sobe" pro topo
        identidade.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(identidade, Pos.BOTTOM_LEFT);

        // Bolinhas do "carrossel" (decorativas)
        HBox bolinhas = new HBox(6,
            criarBolha(false), criarBolha(false), criarBolha(true));
        bolinhas.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(bolinhas, Pos.BOTTOM_CENTER);
        StackPane.setMargin(bolinhas, new Insets(0, 0, 24, 0));

        StackPane painelImagem = new StackPane(mapa, identidade, logo, bolinhas);
        painelImagem.getStyleClass().add("painel-imagem");

        // ==================== LADO DIREITO DO CARD: FORMULÁRIO ====================
        Label titulo = new Label("Entrar");
        titulo.getStyleClass().add("titulo-card");

        Label pergunta = new Label("Não tem uma conta? ");
        pergunta.getStyleClass().add("texto-card");

        Hyperlink linkCadastro = new Hyperlink("Cadastre-se");
        linkCadastro.getStyleClass().add("link-card");
        linkCadastro.setOnAction(e -> Alertas.mostrarIndisponivel());

        HBox linhaCadastro = new HBox(pergunta, linkCadastro);
        linhaCadastro.setAlignment(Pos.CENTER_LEFT);

        // ----- Usuário -----
        Label labelUsuario = new Label("Usuário");
        labelUsuario.getStyleClass().add("label-card");

        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Seu usuário");
        campoUsuario.getStyleClass().add("campo-card");

        // ----- Senha -----
        Label labelSenha = new Label("Senha");
        labelSenha.getStyleClass().add("label-card");

        ImageView iconeOlhoAberto  = Recursos.criarIcone(CAMINHO_IMAGENS + "olho.png", 18, 18);
        ImageView iconeOlhoFechado = Recursos.criarIcone(CAMINHO_IMAGENS + "olho-fechado.png", 18, 18);

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Sua senha");
        campoSenha.getStyleClass().add("campo-card");

        TextField campoSenhaVisivel = new TextField();
        campoSenhaVisivel.setPromptText("Sua senha");
        campoSenhaVisivel.getStyleClass().add("campo-card");
        campoSenhaVisivel.setVisible(false);
        campoSenhaVisivel.setManaged(false);

        campoSenha.textProperty().bindBidirectional(campoSenhaVisivel.textProperty());

        // Olho com animação ao clicar
        Button botaoOlho = new Button();
        botaoOlho.setGraphic(iconeOlhoAberto);
        botaoOlho.getStyleClass().add("botao-olho");
        botaoOlho.setOnAction(e -> {
            boolean mostrando = campoSenhaVisivel.isVisible();
            campoSenha.setVisible(mostrando);
            campoSenha.setManaged(mostrando);
            campoSenhaVisivel.setVisible(!mostrando);
            campoSenhaVisivel.setManaged(!mostrando);

            ImageView proximoIcone = mostrando ? iconeOlhoAberto : iconeOlhoFechado;
            FadeTransition fade = new FadeTransition(Duration.millis(150), botaoOlho);
            fade.setFromValue(1.0);
            fade.setToValue(0.2);
            fade.setOnFinished(ev -> {
                botaoOlho.setGraphic(proximoIcone);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(150), botaoOlho);
                fadeIn.setFromValue(0.2);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fade.play();
        });

        StackPane areaSenha = new StackPane(campoSenha, campoSenhaVisivel);
        StackPane.setAlignment(botaoOlho, Pos.CENTER_RIGHT);
        StackPane.setMargin(botaoOlho, new Insets(0, 14, 0, 0));
        areaSenha.getChildren().add(botaoOlho);
        areaSenha.getStyleClass().add("caixa-senha-card");
        areaSenha.setMaxWidth(Double.MAX_VALUE);

        // ----- Botão Entrar -----
        Button botaoEntrar = new Button("Entrar no Sistema");
        botaoEntrar.setMaxWidth(Double.MAX_VALUE);
        botaoEntrar.setPrefHeight(48);
        botaoEntrar.getStyleClass().add("botao-principal");
        botaoEntrar.setOnAction(e -> {
            String usuario = campoUsuario.getText().trim();
            String senha = campoSenha.getText();

            if (usuario.isEmpty() || senha.isEmpty()) {
                Alertas.mostrarErro("Campos vazios", "Preencha usuário e senha.");
            } else if (Autenticacao.autenticar(usuario, senha)) {
                // Login ok: cria a sessão (horário capturado aqui) e abre o painel
                Sessao sessao = new Sessao(usuario);
                new PainelPrincipal(sessao).mostrar(new Stage());
                palco.close();
            } else {
                Alertas.mostrarErro("Falha no login", "Usuário ou senha incorretos.");
                campoSenha.clear();
                campoSenha.requestFocus();
            }
        });

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

        // ----- Botões Google / Apple (não implementados) -----
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
            new VBox(6, labelSenha, areaSenha),
            botaoEntrar,
            divisor,
            linhaSocial
        );
        formulario.setSpacing(16);
        formulario.setAlignment(Pos.CENTER_LEFT);
        formulario.setPadding(new Insets(48, 48, 40, 48));
        formulario.setPrefWidth(460);
        formulario.getStyleClass().add("painel-formulario");

        HBox card = new HBox(painelImagem, formulario);
        card.setMaxSize(860, 560);
        card.getStyleClass().add("card-central");

        // ==================== FUNDO (fora do card) ====================
        StackPane raiz = new StackPane(card);
        raiz.getStyleClass().add("fundo-janela");

        Scene cena = new Scene(raiz, 1100, 720);
        cena.getStylesheets().add("file:" + CAMINHO_CSS);

        // Ícone da janela
        javafx.scene.image.Image icone = Recursos.iconeJanela(CAMINHO_IMAGENS + "globo.png");
        if (icone != null) {
            palco.getIcons().add(icone);
        }

        palco.setTitle("Horizon Earth");
        palco.setScene(cena);
        palco.setMinWidth(940);
        palco.setMinHeight(640);
        palco.centerOnScreen();
        palco.show();
    }

    // Bolinha do carrossel
    private Label criarBolha(boolean ativa) {
        Label bolha = new Label("");
        bolha.setMinSize(ativa ? 26 : 18, 4);
        bolha.setMaxSize(ativa ? 26 : 18, 4);
        bolha.setStyle("-fx-background-color: " + (ativa ? "#ffffff" : "#5a6b85")
                + "; -fx-background-radius: 2;");
        return bolha;
    }
}
