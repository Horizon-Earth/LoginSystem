package loginsystem;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;

public class Main extends Application {

    private static final String CAMINHO_IMAGENS = "resources/icons/";
    private static final String CAMINHO_CSS     = "resources/style.css";

    @Override
    public void start(Stage primaryStage) {

        // ==================== LOGO (círculo com globo) ====================
        ImageView iconeGlobo = criarIcone(CAMINHO_IMAGENS + "globo.png", 26, 26);
        StackPane circuloLogo = new StackPane(iconeGlobo);
        circuloLogo.setMinSize(56, 56);
        circuloLogo.setMaxSize(56, 56);
        circuloLogo.getStyleClass().add("circulo-logo");

        Label titulo = new Label("Horizon Earth");
        titulo.getStyleClass().add("titulo-login");

        // ==================== CAMPO USUÁRIO ====================
        Label labelUsuario = new Label("USUÁRIO");
        labelUsuario.getStyleClass().add("label-campo");

        ImageView iconeUsuario = criarIcone(CAMINHO_IMAGENS + "pessoa.png", 16, 16);
        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Nome de usuário ou e-mail");
        campoUsuario.getStyleClass().add("campo-texto");

        HBox caixaUsuario = new HBox(10, iconeUsuario, campoUsuario);
        caixaUsuario.setAlignment(Pos.CENTER_LEFT);
        caixaUsuario.setPadding(new Insets(0, 12, 0, 12));
        // O campo cresce para preencher a caixa (não corta o texto)
        HBox.setHgrow(campoUsuario, Priority.ALWAYS);
        caixaUsuario.getStyleClass().add("caixa-campo");

        // ==================== CAMPO SENHA (com olho no final) ====================
        Label labelSenha = new Label("SENHA");
        labelSenha.getStyleClass().add("label-campo");

        ImageView iconeSenha = criarIcone(CAMINHO_IMAGENS + "cadeado.png", 16, 16);
        ImageView iconeOlhoAberto   = criarIcone(CAMINHO_IMAGENS + "olho.png", 16, 16);
        ImageView iconeOlhoFechado  = criarIcone(CAMINHO_IMAGENS + "olho-fechado.png", 16, 16);

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Digite sua senha");
        campoSenha.getStyleClass().add("campo-texto");

        TextField campoSenhaVisivel = new TextField();
        campoSenhaVisivel.setPromptText("Digite sua senha");
        campoSenhaVisivel.getStyleClass().add("campo-texto");
        campoSenhaVisivel.setVisible(false);
        campoSenhaVisivel.setManaged(false);

        campoSenha.textProperty().bindBidirectional(campoSenhaVisivel.textProperty());

        // Olho posicionado no FINAL do campo de senha, com animação ao clicar
        Button botaoOlho = new Button();
        botaoOlho.setGraphic(iconeOlhoAberto);
        botaoOlho.getStyleClass().add("botao-olho");
        botaoOlho.setOnAction(e -> {
            boolean mostrando = campoSenhaVisivel.isVisible();
            campoSenha.setVisible(mostrando);
            campoSenha.setManaged(mostrando);
            campoSenhaVisivel.setVisible(!mostrando);
            campoSenhaVisivel.setManaged(!mostrando);

            // Troca o ícone com um "fade" rápido (animação de transição)
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
        StackPane.setMargin(botaoOlho, new Insets(0, 6, 0, 0));
        areaSenha.getChildren().add(botaoOlho);

        HBox caixaSenha = new HBox(10, iconeSenha, areaSenha);
        caixaSenha.setAlignment(Pos.CENTER_LEFT);
        caixaSenha.setPadding(new Insets(0, 2, 0, 12));
        HBox.setHgrow(areaSenha, Priority.ALWAYS);
        caixaSenha.getStyleClass().add("caixa-campo");

        // ==================== LEMBRAR DE MIM / ESQUECI ====================
        CheckBox lembrar = new CheckBox("Lembrar de mim");
        lembrar.setSelected(true);
        lembrar.getStyleClass().add("checkbox-login");

        Hyperlink esqueci = new Hyperlink("Esqueceu a senha?");
        esqueci.getStyleClass().add("link-login");
        esqueci.setOnAction(e -> mostrarInfo("Recuperação de senha",
            "Entre em contato com o administrador do sistema para redefinir sua senha."));

        HBox linhaOpcoes = new HBox(lembrar, esqueci);
        linhaOpcoes.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(esqueci, Priority.ALWAYS);
        esqueci.setAlignment(Pos.CENTER_RIGHT);

        // ==================== BOTÃO ENTRAR ====================
        Button botaoEntrar = new Button("Entrar no Sistema");
        botaoEntrar.setMaxWidth(Double.MAX_VALUE);
        botaoEntrar.setPrefHeight(40);
        botaoEntrar.getStyleClass().add("botao-entrar");
        botaoEntrar.setOnAction(e -> {
            String usuario = campoUsuario.getText().trim();
            String senha = campoSenha.getText();

            if (usuario.isEmpty() || senha.isEmpty()) {
                mostrarErro("Campos vazios", "Preencha usuário e senha.");
            } else if (Autenticacao.autenticar(usuario, senha)) {
                PainelPrincipal painel = new PainelPrincipal(usuario);
                painel.start(new Stage());
                primaryStage.close();
            } else {
                mostrarErro("Falha no login", "Usuário ou senha incorretos.");
                campoSenha.clear();
                campoSenha.requestFocus();
            }
        });

        // ==================== RODAPÉ DO CARD ====================
        Hyperlink criarConta = new Hyperlink("Criar conta");
        criarConta.getStyleClass().add("link-login");
        criarConta.setOnAction(e -> mostrarInfo("Criar conta",
            "O cadastro de novos usuários é feito pelo administrador do sistema."));

        Label rodape = new Label("Novo no Horizon? ");
        rodape.getStyleClass().add("texto-rodape");
        HBox linhaRodape = new HBox(rodape, criarConta);
        linhaRodape.setAlignment(Pos.CENTER);

        // ==================== CARD CENTRAL ====================
        VBox card = new VBox(12,
            circuloLogo, titulo,
            new VBox(5, labelUsuario, caixaUsuario),
            new VBox(5, labelSenha, caixaSenha),
            linhaOpcoes,
            botaoEntrar,
            linhaRodape
        );
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(32, 36, 28, 36));
        card.setMaxWidth(360);
        card.getStyleClass().add("card-login");

        // ==================== BARRA DE STATUS (rodapé da janela) ====================
        Label pontoStatus = new Label("●");
        pontoStatus.getStyleClass().add("ponto-status");

        Label textoStatus = new Label("Servidor principal online e ativo");
        textoStatus.getStyleClass().add("texto-status");

        HBox statusEsquerda = new HBox(8, pontoStatus, textoStatus);
        statusEsquerda.setAlignment(Pos.CENTER_LEFT);

        String versaoJava = System.getProperty("java.version");
        Label infoDireita = new Label("Java Runtime Environment: " + versaoJava + "    Versão v1.1.1");
        infoDireita.getStyleClass().add("texto-status");

        HBox statusDireita = new HBox(infoDireita);
        statusDireita.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(statusDireita, Priority.ALWAYS);

        HBox barraStatus = new HBox(20, statusEsquerda, statusDireita);
        barraStatus.setAlignment(Pos.CENTER_LEFT);
        barraStatus.setPadding(new Insets(8, 16, 8, 16));
        barraStatus.getStyleClass().add("barra-status");

        // ==================== CENA ====================
        BorderPane raiz = new BorderPane();
        raiz.setCenter(card);
        raiz.setBottom(barraStatus);
        raiz.getStyleClass().add("fundo-login");
        BorderPane.setAlignment(card, Pos.CENTER);
        BorderPane.setMargin(card, new Insets(16));

        Scene cena = new Scene(raiz, 460, 640);

        // Aplica a folha de estilos externa
        cena.getStylesheets().add("file:" + CAMINHO_CSS);

        // Ícone da janela
        try {
            FileInputStream fis = new FileInputStream(CAMINHO_IMAGENS + "globo.png");
            primaryStage.getIcons().add(new Image(fis));
            fis.close();
        } catch (Exception ignored) {
        }

        primaryStage.setTitle("Horizon Earth Desktop Client");
        primaryStage.setScene(cena);
        primaryStage.setMinWidth(420);
        primaryStage.setMinHeight(580);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    // Método auxiliar para carregar ícones
    private ImageView criarIcone(String caminho, int largura, int altura) {
        try {
            FileInputStream fis = new FileInputStream(caminho);
            ImageView imagem = new ImageView(new Image(fis));
            imagem.setFitWidth(largura);
            imagem.setFitHeight(altura);
            fis.close();
            return imagem;
        } catch (Exception e) {
            return new ImageView();
        }
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void mostrarInfo(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
