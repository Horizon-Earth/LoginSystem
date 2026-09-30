package loginsystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {

    // Caminho dos ícones dentro do projeto (relativo à pasta raiz do projeto)
    private static final String CAMINHO_IMAGENS = "resources/icons/";

    @Override
    public void start(Stage primaryStage) {

        // ----- Ícones -----
        ImageView iconeUsuario = criarIcone(CAMINHO_IMAGENS + "pessoa.png");
        ImageView iconeSenha   = criarIcone(CAMINHO_IMAGENS + "cadeado.png");

        // ----- Caixa de usuário -----
        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Usuário");
        campoUsuario.setPrefWidth(220);

        // ----- Caixa de senha -----
        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Senha");
        campoSenha.setPrefWidth(220);

        // Linha: ícone pessoa + campo usuário
        HBox linhaUsuario = new HBox(10, iconeUsuario, campoUsuario);
        linhaUsuario.setAlignment(Pos.CENTER_LEFT);

        // Linha: ícone cadeado + campo senha
        HBox linhaSenha = new HBox(10, iconeSenha, campoSenha);
        linhaSenha.setAlignment(Pos.CENTER_LEFT);

        // ----- Mensagem de feedback (fica embaixo do botão) -----
        Label mensagem = new Label("");
        mensagem.setStyle("-fx-text-fill: #c0392b; -fx-font-size: 12px;");

        // ----- Botão de login -----
        Button botaoLogin = new Button("Entrar");
        botaoLogin.setPrefWidth(260);
        botaoLogin.setStyle(
            "-fx-background-color: #2d6a4f;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );
        botaoLogin.setOnAction(e -> {
            String usuario = campoUsuario.getText();
            String senha = campoSenha.getText();

            if (usuario.isEmpty() || senha.isEmpty()) {
                mensagem.setText("Preencha usuário e senha.");
            } else {
                mensagem.setText("");
                // TODO: chamar a lógica de autenticação aqui
            }
        });

        // ----- Título -----
        Label titulo = new Label("Login System");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1b4332;");

        VBox painel = new VBox(12, titulo, linhaUsuario, linhaSenha, botaoLogin, mensagem);
        painel.setAlignment(Pos.CENTER);
        painel.setPadding(new Insets(40));
        painel.setStyle("-fx-background-color: #f4f6f5;");

        Scene cena = new Scene(painel, 420, 320);

        try {
            FileInputStream fis = new FileInputStream(CAMINHO_IMAGENS + "cadeado.png");
            primaryStage.getIcons().add(new Image(fis));
            fis.close();
        } catch (Exception ignored) {
            // se o ícone não existir, a janela abre sem ícone mesmo
        }

        primaryStage.setTitle("Login System - Horizon Earth");
        primaryStage.setScene(cena);
        primaryStage.setResizable(false);

        // Centraliza a janela na tela
        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    // Método auxiliar para carregar os ícones das caixas
    private ImageView criarIcone(String caminho) {
        try {
            FileInputStream fis = new FileInputStream(caminho);
            ImageView imagem = new ImageView(new Image(fis));
            imagem.setFitWidth(24);
            imagem.setFitHeight(24);
            fis.close();
            return imagem;
        } catch (Exception e) {
            return new ImageView(); // retorna vazio para não quebrar o layout
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}