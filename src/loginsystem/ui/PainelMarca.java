package loginsystem.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loginsystem.util.Recursos;

/**
 * Visual compartilhado entre TelaLogin e TelaCadastro: lado esquerdo do
 * card (imagem + marca), card central, fundo e configuração da janela.
 */
final class PainelMarca {

    static final String CAMINHO_ICONES = "resources/icons/";
    static final String CAMINHO_MAPA   = "resources/images/logo.png";
    static final String CAMINHO_CSS    = "resources/style.css";

    private PainelMarca() {
    }

    /** Monta a cena completa: fundo + card (imagem à esquerda, formulário à direita). */
    static Scene criarCena(VBox formulario) {
        HBox card = new HBox(criarPainelImagem(), formulario);
        card.setMaxSize(860, 600);
        card.getStyleClass().add("card-central");

        StackPane raiz = new StackPane(card);
        raiz.getStyleClass().add("fundo-janela");

        Scene cena = new Scene(raiz, 1100, 720);
        cena.getStylesheets().add("file:" + CAMINHO_CSS);
        return cena;
    }

    /** Aplica a cena na janela (ícone, título, tamanho mínimo). */
    static void exibir(Stage palco, Scene cena) {
        if (palco.getIcons().isEmpty()) {
            Image icone = Recursos.iconeJanela(CAMINHO_ICONES + "globo.png");
            if (icone != null) {
                palco.getIcons().add(icone);
            }
        }

        boolean jaVisivel = palco.isShowing();
        palco.setTitle("Horizon Earth");
        palco.setScene(cena);
        palco.setMinWidth(940);
        palco.setMinHeight(640);
        if (!jaVisivel) {
            palco.centerOnScreen();
            palco.show();
        }
    }

    // ==================== LADO ESQUERDO DO CARD: IMAGEM ====================
    private static StackPane criarPainelImagem() {
        ImageView mapa = Recursos.carregarImagem(CAMINHO_MAPA);
        mapa.setFitHeight(300);
        mapa.setPreserveRatio(true);

        Label logo = new Label("Horizon");
        logo.getStyleClass().add("logo-card");
        StackPane.setAlignment(logo, Pos.TOP_LEFT);
        StackPane.setMargin(logo, new Insets(28, 0, 0, 28));

        Label nomeMarca = new Label("Horizon Earth");
        nomeMarca.getStyleClass().add("nome-marca-card");

        Label slogan = new Label("Monitoramento de clima e riscos do planeta.");
        slogan.getStyleClass().add("slogan-card");

        VBox identidade = new VBox(6, nomeMarca, slogan);
        identidade.setPadding(new Insets(0, 0, 48, 28));
        identidade.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(identidade, Pos.BOTTOM_LEFT);

        HBox bolinhas = new HBox(6,
            criarBolha(false), criarBolha(false), criarBolha(true));
        bolinhas.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(bolinhas, Pos.BOTTOM_CENTER);
        StackPane.setMargin(bolinhas, new Insets(0, 0, 24, 0));

        StackPane painelImagem = new StackPane(mapa, identidade, logo, bolinhas);
        painelImagem.getStyleClass().add("painel-imagem");
        return painelImagem;
    }

    private static Label criarBolha(boolean ativa) {
        Label bolha = new Label("");
        bolha.setMinSize(ativa ? 26 : 18, 4);
        bolha.setMaxSize(ativa ? 26 : 18, 4);
        bolha.setStyle("-fx-background-color: " + (ativa ? "#ffffff" : "#5a6b85")
                + "; -fx-background-radius: 2;");
        return bolha;
    }
}
