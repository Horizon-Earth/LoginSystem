package loginsystem.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Centraliza todas as caixas de diálogo da aplicação.
 * Assim, o código das telas fica limpo e os textos de
 * mensagem ficam em um único lugar.
 */
public class Alertas {

    private Alertas() {
    }

    /** Alerta de erro (campos vazios, login inválido...). */
    public static void mostrarErro(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    /** Erro de conexão com o banco (MySQL desligado, senha errada no db.properties...). */
    public static void mostrarErroBanco() {
        mostrarErro("Erro de conexão",
                "Não foi possível acessar o banco de dados.\n"
                + "Verifique se o MySQL está em execução e se o arquivo "
                + "resources/db.properties está correto.");
    }

    /** Alerta informativo genérico. */
    public static void mostrarInfo(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    /**
     * Aviso exibido para recursos ainda não implementados.
     * A caixa de texto é bloqueada (setEditable(false)): o usuário
     * não consegue digitar nada, apenas visualiza "indisponível".
     */
    public static void mostrarIndisponivel() {
        TextField campo = new TextField("indisponível");
        campo.setEditable(false);
        campo.setMaxWidth(Double.MAX_VALUE);

        VBox conteudo = new VBox(10,
            new Label("Recurso em desenvolvimento:"), campo);
        conteudo.setMinWidth(280);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Indisponível");
        alerta.setHeaderText("Recurso indisponível");
        alerta.getDialogPane().setContent(conteudo);
        alerta.showAndWait();
    }
}
