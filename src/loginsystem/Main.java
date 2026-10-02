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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {

    // Caminho dos ícones dentro do projeto (relativo à pasta raiz do projeto)
    private static final String CAMINHO_IMAGENS = "resources/icons/";

    @Override
    public void start(Stage primaryStage) {

        // Ícones
        ImageView iconeUsuario = criarIcone(CAMINHO_IMAGENS + "pessoa.png");
        ImageView iconeSenha   = criarIcone(CAMINHO_IMAGENS + "cadeado.png");

        // Campos de usuário e senha
        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Usuário");
        campoUsuario.setPrefWidth(226);

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Senha");
        campoSenha.setPrefWidth(226);

        // Linhas com tamanho fixo e alinhadas à esquerda dentro de um bloco centralizado
        HBox linhaUsuario = new HBox(10, iconeUsuario, campoUsuario);
        linhaUsuario.setAlignment(Pos.CENTER_LEFT);
        linhaUsuario.setMaxWidth(260);

        HBox linhaSenha = new HBox(10, iconeSenha, campoSenha);
        linhaSenha.setAlignment(Pos.CENTER_LEFT);
        linhaSenha.setMaxWidth(260);

        // ----- Botão de login (menor)
        Button botaoLogin = new Button("Entrar");
        botaoLogin.setPrefWidth(120);
        botaoLogin.setStyle(
            "-fx-background-color: #2d6a4f;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );
        botaoLogin.setOnAction(e -> {
            String usuario = campoUsuario.getText().trim();
            String senha = campoSenha.getText();

            if (usuario.isEmpty() || senha.isEmpty()) {
                mostrarErro("Campos vazios", "Preencha usuário e senha.");
            } else if (Autenticacao.autenticar(usuario, senha)) {
                // Abre o painel principal e fecha a tela de login
                PainelPrincipal painel = new PainelPrincipal(usuario);
                painel.start(new Stage());
                primaryStage.close();
            } else {
                mostrarErro("Falha no login", "Usuário ou senha incorretos.");
                campoSenha.clear();
                campoSenha.requestFocus();
            }
        });

        // Título
        Label titulo = new Label("Login System");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1b4332;");

        Label subtitulo = new Label("Horizon Earth");
        subtitulo.setStyle("-fx-font-size: 13px; -fx-text-fill: #52796f;");

        // Botão centralizado dentro de uma linha com a mesma largura do bloco
        HBox linhaBotao = new HBox(botaoLogin);
        linhaBotao.setAlignment(Pos.CENTER);
        linhaBotao.setMaxWidth(260);

        // Painel centralizado
        VBox painel = new VBox(12, titulo, subtitulo, linhaUsuario, linhaSenha, linhaBotao);
        painel.setAlignment(Pos.CENTER);
        painel.setPadding(new Insets(40));
        painel.setMaxWidth(340);
        painel.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 4);"
        );

        // Container externo (fundo) para centralizar o painel na janela
        VBox fundo = new VBox(painel);
        fundo.setAlignment(Pos.CENTER);
        fundo.setStyle("-fx-background-color: #f4f6f5;");

        Scene cena = new Scene(fundo, 420, 320);

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

    // Caixa de diálogo de erro (substitui o Label de mensagem)
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
