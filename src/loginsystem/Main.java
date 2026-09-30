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

    private static final String CAMINHO_ICONS = "resources/icons/";

    @Override
    public void start(Stage primaryStage) {

        ImageView iconeUsuario = criarIcone(CAMINHO_ICONS + "pessoa.png");
        ImageView iconeSenha   = criarIcone(CAMINHO_ICONS + "cadeado.png");

        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Usuário");

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Senha");

        HBox linhaUsuario = new HBox(10, iconeUsuario, campoUsuario);
        linhaUsuario.setAlignment(Pos.CENTER_LEFT);

        HBox linhaSenha = new HBox(10, iconeSenha, campoSenha);
        linhaSenha.setAlignment(Pos.CENTER_LEFT);

        Button botaoLogin = new Button("Entrar");
        botaoLogin.setMaxWidth(Double.MAX_VALUE);
        botaoLogin.setOnAction(e -> {
            String usuario = campoUsuario.getText();
            String senha = campoSenha.getText();
            System.out.println("Usuário: " + usuario + " | Senha: " + senha);
        });

        Label titulo = new Label("Login System");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox painel = new VBox(15, titulo, linhaUsuario, linhaSenha, botaoLogin);
        painel.setAlignment(Pos.CENTER);
        painel.setPadding(new Insets(30));

        Scene cena = new Scene(painel, 400, 300);

        try {
            FileInputStream fis = new FileInputStream(CAMINHO_ICONS + "cadeado.png");
            primaryStage.getIcons().add(new Image(fis));
            fis.close();
        } catch (Exception e) {
            System.out.println("Ícone do cabeçalho não encontrado em: " + CAMINHO_ICONS);
        }

        primaryStage.setTitle("Login System - Horizon Earth");
        primaryStage.setScene(cena);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private ImageView criarIcone(String caminho) {
        try {
            FileInputStream fis = new FileInputStream(caminho);
            ImageView imagem = new ImageView(new Image(fis));
            imagem.setFitWidth(24);
            imagem.setFitHeight(24);
            fis.close();
            return imagem;
        } catch (Exception e) {
            System.out.println("Ícone não encontrado: " + caminho);
            return new ImageView(); // retorna vazio para não quebrar o layout
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}