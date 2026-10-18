package loginsystem.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

/**
 * Carrega imagens e ícones dos arquivos do projeto.
 * Todos os métodos usam try/catch: se um arquivo não for
 * encontrado, a exceção é capturada e um ImageView vazio é
 * retornado, sem quebrar a aplicação.
 */
public class Recursos {

    private Recursos() {
    }

    /** Carrega um ícone redimensionado (usado em botões e campos). */
    public static ImageView criarIcone(String caminho, int largura, int altura) {
        try {
            FileInputStream fis = new FileInputStream(caminho);
            ImageView imagem = new ImageView(new Image(fis));
            imagem.setFitWidth(largura);
            imagem.setFitHeight(altura);
            fis.close();
            return imagem;
        } catch (Exception e) {
            return new ImageView(); // vazio, para não quebrar o layout
        }
    }

    /** Carrega uma imagem em tamanho natural (usada como fundo). */
    public static ImageView carregarImagem(String caminho) {
        try {
            FileInputStream fis = new FileInputStream(caminho);
            Image img = new Image(fis);
            fis.close();
            return new ImageView(img);
        } catch (Exception e) {
            return new ImageView();
        }
    }

    /** Ícone da barra de título da janela. */
    public static Image iconeJanela(String caminho) {
        try {
            FileInputStream fis = new FileInputStream(caminho);
            Image img = new Image(fis);
            fis.close();
            return img;
        } catch (Exception e) {
            return null; // a janela abre sem ícone mesmo
        }
    }
}
