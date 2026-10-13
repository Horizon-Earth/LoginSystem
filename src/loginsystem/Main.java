package loginsystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {

    private static final String CAMINHO_IMAGENS = "resources/icons/";
    private static final String CAMINHO_MAPA    = "resources/images/mapa.png";
    private static final String CAMINHO_CSS     = "resources/style.css";

    @Override
    public void start(Stage primaryStage) {

        // ==================== LADO ESQUERDO: MAPA + IDENTIDADE ====================
        ImageView mapa = carregarImagem(CAMINHO_MAPA);

        Label nomeMarca = new Label("Horizon Earth");
        nomeMarca.getStyleClass().add("nome-marca");

        Label slogan = new Label("Monitoramento de clima e riscos do planeta.");
        slogan.getStyleClass().add("slogan");

        VBox identidade = new VBox(6, nomeMarca, slogan);
        identidade.setPadding(new Insets(0, 0, 60, 60));

        StackPane painelEsquerdo = new StackPane(mapa, identidade);
        // Posiciona o texto no canto inferior esquerdo DO PAINEL
        StackPane.setAlignment(identidade, Pos.BOTTOM_LEFT);
        painelEsquerdo.getStyleClass().add("painel-esquerdo");
        painelEsquerdo.setMinWidth(420);

        // O mapa preenche o painel sem deformar
        mapa.fitWidthProperty().bind(painelEsquerdo.widthProperty());
        mapa.fitHeightProperty().bind(painelEsquerdo.heightProperty());
        mapa.setPreserveRatio(false);

        // ==================== LADO DIREITO: FORMULÁRIO ====================
        Label titulo = new Label("Entrar");
        titulo.getStyleClass().add("titulo-form");

        Label subtitulo = new Label("Acesse o Horizon Earth.");
        subtitulo.getStyleClass().add("subtitulo-form");

        Label labelUsuario = new Label("Usuário");
        labelUsuario.getStyleClass().add("label-campo-claro");

        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Seu usuário");
        campoUsuario.getStyleClass().add("campo-claro");

        Label labelSenha = new Label("Senha");
        labelSenha.getStyleClass().add("label-campo-claro");

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Sua senha");
        campoSenha.getStyleClass().add("campo-claro");

        Button botaoEntrar = new Button("Entrar");
        botaoEntrar.setMaxWidth(Double.MAX_VALUE);
        botaoEntrar.setPrefHeight(52);
        botaoEntrar.getStyleClass().add("botao-entrar-escuro");
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

        Label rodape = new Label("Projeto Final de POO · IFCE Campus Maranguape");
        rodape.getStyleClass().add("rodape-form");

        VBox formulario = new VBox(10,
            titulo, subtitulo,
            new VBox(6, labelUsuario, campoUsuario),
            new VBox(6, labelSenha, campoSenha),
            botaoEntrar
        );
        formulario.setSpacing(18);
        formulario.setAlignment(Pos.CENTER_LEFT);
        formulario.setMaxWidth(460);

        VBox colunaDireita = new VBox(formulario);
        colunaDireita.setAlignment(Pos.CENTER);
        colunaDireita.setPadding(new Insets(60));
        VBox.setVgrow(formulario, Priority.ALWAYS);
        colunaDireita.getStyleClass().add("painel-direito");

        VBox rodapeDireita = new VBox(rodape);
        rodapeDireita.setAlignment(Pos.CENTER);
        VBox.setVgrow(rodapeDireita, Priority.ALWAYS);

        VBox painelDireito = new VBox(colunaDireita, rodapeDireita);
        painelDireito.setAlignment(Pos.CENTER);
        HBox.setHgrow(painelDireito, Priority.ALWAYS);

        // ==================== CENA DIVIDIDA ====================
        HBox raiz = new HBox(painelEsquerdo, painelDireito);
        raiz.getStyleClass().add("fundo-janela");

        Scene cena = new Scene(raiz, 1200, 750);

        // Aplica a folha de estilos externa
        cena.getStylesheets().add("file:" + CAMINHO_CSS);

        // Ícone da janela
        try {
            FileInputStream fis = new FileInputStream(CAMINHO_IMAGENS + "globo.png");
            primaryStage.getIcons().add(new Image(fis));
            fis.close();
        } catch (Exception ignored) {
        }

        primaryStage.setTitle("Horizon Earth");
        primaryStage.setScene(cena);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(650);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    // Método auxiliar para carregar imagem de fundo
    private ImageView carregarImagem(String caminho) {
        try {
            FileInputStream fis = new FileInputStream(caminho);
            Image img = new Image(fis);
            fis.close();
            return new ImageView(img);
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

    public static void main(String[] args) {
        launch(args);
    }
}
