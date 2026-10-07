package loginsystem;

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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {

    private static final String CAMINHO_IMAGENS = "resources/icons/";
    private static final String CAMINHO_CSS     = "resources/style.css";

    @Override
    public void start(Stage primaryStage) {

        // ==================== LOGO (círculo com globo) ====================
        ImageView iconeGlobo = criarIcone(CAMINHO_IMAGENS + "globo.png", 28, 28);
        StackPane circuloLogo = new StackPane(iconeGlobo);
        circuloLogo.setMinSize(64, 64);
        circuloLogo.setMaxSize(64, 64);
        circuloLogo.getStyleClass().add("circulo-logo");

        Label titulo = new Label("Horizon Earth");
        titulo.getStyleClass().add("titulo-login");

        // ==================== CAMPO USUÁRIO ====================
        Label labelUsuario = new Label("USUÁRIO");
        labelUsuario.getStyleClass().add("label-campo");

        ImageView iconeUsuario = criarIcone(CAMINHO_IMAGENS + "pessoa.png", 18, 18);
        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Nome de usuário");
        campoUsuario.getStyleClass().add("campo-texto");

        HBox caixaUsuario = new HBox(10, iconeUsuario, campoUsuario);
        caixaUsuario.setAlignment(Pos.CENTER_LEFT);
        caixaUsuario.setPadding(new Insets(0, 14, 0, 14));
        caixaUsuario.getStyleClass().add("caixa-campo");

        // ==================== CAMPO SENHA ====================
        Label labelSenha = new Label("SENHA");
        labelSenha.getStyleClass().add("label-campo");

        ImageView iconeSenha = criarIcone(CAMINHO_IMAGENS + "cadeado.png", 18, 18);
        ImageView iconeOlho  = criarIcone(CAMINHO_IMAGENS + "olho.png", 18, 18);

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Digite sua senha");
        campoSenha.getStyleClass().add("campo-texto");

        TextField campoSenhaVisivel = new TextField();
        campoSenhaVisivel.setPromptText("Digite sua senha");
        campoSenhaVisivel.getStyleClass().add("campo-texto");
        campoSenhaVisivel.setVisible(false);
        campoSenhaVisivel.setManaged(false);

        campoSenha.textProperty().bindBidirectional(campoSenhaVisivel.textProperty());

        Button botaoOlho = new Button();
        botaoOlho.setGraphic(iconeOlho);
        botaoOlho.getStyleClass().add("botao-olho");
        botaoOlho.setOnAction(e -> {
            boolean mostrando = campoSenhaVisivel.isVisible();
            campoSenha.setVisible(mostrando);
            campoSenha.setManaged(mostrando);
            campoSenhaVisivel.setVisible(!mostrando);
            campoSenhaVisivel.setManaged(!mostrando);
        });

        StackPane areaSenha = new StackPane(campoSenha, campoSenhaVisivel);
        StackPane.setAlignment(botaoOlho, Pos.CENTER_RIGHT);
        areaSenha.getChildren().add(botaoOlho);

        HBox caixaSenha = new HBox(10, iconeSenha, areaSenha);
        caixaSenha.setAlignment(Pos.CENTER_LEFT);
        caixaSenha.setPadding(new Insets(0, 6, 0, 14));
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
        HBox.setHgrow(esqueci, javafx.scene.layout.Priority.ALWAYS);
        esqueci.setAlignment(Pos.CENTER_RIGHT);

        // ==================== BOTÃO ENTRAR ====================
        Button botaoEntrar = new Button("Entrar no Sistema");
        botaoEntrar.setMaxWidth(Double.MAX_VALUE);
        botaoEntrar.setPrefHeight(44);
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
        VBox card = new VBox(14,
            circuloLogo, titulo,
            new VBox(5, labelUsuario, caixaUsuario),
            new VBox(5, labelSenha, caixaSenha),
            linhaOpcoes,
            botaoEntrar,
            linhaRodape
        );
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(40, 44, 36, 44));
        card.setMaxWidth(420);
        card.getStyleClass().add("card-login");

        // ==================== BARRA DE STATUS (rodapé da janela) ====================
        Label pontoStatus = new Label("●");
        pontoStatus.getStyleClass().add("ponto-status");

        Label textoStatus = new Label("Servidor principal online e ativo");
        textoStatus.getStyleClass().add("texto-status");

        HBox statusEsquerda = new HBox(8, pontoStatus, textoStatus);
        statusEsquerda.setAlignment(Pos.CENTER_LEFT);

        String versaoJava = System.getProperty("java.version");
        Label infoDireita = new Label("Java Runtime Environment: " + versaoJava + "    Versão v1.1.0");
        infoDireita.getStyleClass().add("texto-status");

        HBox statusDireita = new HBox(infoDireita);
        statusDireita.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(statusDireita, javafx.scene.layout.Priority.ALWAYS);

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
        BorderPane.setMargin(card, new Insets(20));

        Scene cena = new Scene(raiz, 1320, 820);

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
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(650);
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
