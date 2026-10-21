package loginsystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import loginsystem.database.ConexaoBD;
import loginsystem.ui.TelaLogin;
import loginsystem.util.Alertas;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        new TelaLogin(primaryStage).mostrar();

        // Testa o MySQL em segundo plano e avisa já na abertura se estiver fora do ar
        Thread teste = new Thread(() -> {
            if (!ConexaoBD.testar()) {
                Platform.runLater(Alertas::mostrarErroBanco);
            }
        });
        teste.setDaemon(true);
        teste.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
