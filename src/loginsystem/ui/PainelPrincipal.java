package loginsystem.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loginsystem.model.Sessao;

/**
 * Painel exibido após o login bem-sucedido.
 * Recebe a Sessao criada no momento da autenticação.
 */
public class PainelPrincipal {

    private final Sessao sessao;

    public PainelPrincipal(Sessao sessao) {
        this.sessao = sessao;
    }

    public void mostrar(Stage stage) {

        // Boas-vindas
        Label titulo = new Label("Bem-vindo, " + sessao.getUsuario() + "!");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1b4332;");

        Label subtitulo = new Label("Painel Principal - Horizon Earth");
        subtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #52796f;");

        // Informações da sessão (horário capturado no login)
        Label infoSessao = new Label("Sessão iniciada em: " + sessao.getHorarioAcessoFormatado());
        infoSessao.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b9080;");

        // Botão de sair
        Button botaoSair = new Button("Sair");
        botaoSair.setPrefWidth(120);
        botaoSair.setStyle(
            "-fx-background-color: #c0392b;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );
        botaoSair.setOnAction(e -> {
            // Volta para a tela de login
            stage.close();
            new TelaLogin(new Stage()).mostrar();
        });

        VBox painel = new VBox(14, titulo, subtitulo, infoSessao, botaoSair);
        painel.setAlignment(Pos.CENTER);
        painel.setPadding(new Insets(40));
        painel.setStyle("-fx-background-color: #f4f6f5;");

        Scene cena = new Scene(painel, 500, 350);

        stage.setTitle("Painel Principal - Horizon Earth");
        stage.setScene(cena);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}
