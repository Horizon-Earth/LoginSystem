package loginsystem.ui;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import loginsystem.util.Recursos;

/** Campo de senha com botão "olho" (mostrar/ocultar) e animação de fade. */
public class CampoSenha extends StackPane {

    private final PasswordField campoOculto = new PasswordField();
    private final TextField campoVisivel = new TextField();

    public CampoSenha(String dica) {
        ImageView iconeOlhoAberto  = Recursos.criarIcone(PainelMarca.CAMINHO_ICONES + "olho.png", 18, 18);
        ImageView iconeOlhoFechado = Recursos.criarIcone(PainelMarca.CAMINHO_ICONES + "olho-fechado.png", 18, 18);

        campoOculto.setPromptText(dica);
        campoOculto.getStyleClass().add("campo-card");

        campoVisivel.setPromptText(dica);
        campoVisivel.getStyleClass().add("campo-card");
        campoVisivel.setVisible(false);
        campoVisivel.setManaged(false);

        // Os dois campos compartilham o mesmo texto
        campoOculto.textProperty().bindBidirectional(campoVisivel.textProperty());

        Button botaoOlho = new Button();
        botaoOlho.setGraphic(iconeOlhoAberto);
        botaoOlho.getStyleClass().add("botao-olho");
        botaoOlho.setOnAction(e -> {
            boolean mostrando = campoVisivel.isVisible();
            campoOculto.setVisible(mostrando);
            campoOculto.setManaged(mostrando);
            campoVisivel.setVisible(!mostrando);
            campoVisivel.setManaged(!mostrando);

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

        getChildren().addAll(campoOculto, campoVisivel, botaoOlho);
        StackPane.setAlignment(botaoOlho, Pos.CENTER_RIGHT);
        StackPane.setMargin(botaoOlho, new Insets(0, 14, 0, 0));
        getStyleClass().add("caixa-senha-card");
        setMaxWidth(Double.MAX_VALUE);
    }

    public String getTexto() {
        return campoOculto.getText();
    }

    public void limpar() {
        campoOculto.clear();
    }

    /** Coloca o cursor no campo que estiver visível no momento. */
    public void focar() {
        if (campoVisivel.isVisible()) {
            campoVisivel.requestFocus();
        } else {
            campoOculto.requestFocus();
        }
    }

    /** Define o que acontece ao apertar Enter dentro do campo. */
    public void aoConfirmar(Runnable acao) {
        campoOculto.setOnAction(e -> acao.run());
        campoVisivel.setOnAction(e -> acao.run());
    }
}
