package loginsystem;

import javafx.application.Application;
import javafx.stage.Stage;
import loginsystem.ui.TelaLogin;

/**
 * Ponto de entrada da aplicação Horizon Earth.
 *
 * Aqui a aplicação JavaFX é apenas inicializada — toda a
 * construção da interface fica nas classes da pasta ui.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        new TelaLogin(primaryStage).mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
