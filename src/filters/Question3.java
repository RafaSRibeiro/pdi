package filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Question3 {

    private PixelReader pixelReader;

    private PixelWriter pixelWriter;

    private WritableImage writableImage;

    private Image image;

    private int width;

    private int height;

    private Color colorBlack = Color.BLACK;

    public Question3(Image _image) {
        image = _image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        pixelReader = image.getPixelReader();
        writableImage = new WritableImage(pixelReader, 0, 0, width, height);
        pixelWriter = writableImage.getPixelWriter();
    }

    public Image run() {
        procuraQuadrado();
        return writableImage;
    }

    private void procuraQuadrado() {
        boolean isPossuiColunasQuebradas = possuiColunasQuebradas();
        boolean isPossuiLinhasQuebrada = possuiLinhasQuebradas();
        if (isPossuiColunasQuebradas || isPossuiLinhasQuebrada)
            System.out.println("Possui um quadrado Aberto");
        else
            System.out.println("Não Possui um quadrado Aberto");
    }

    private boolean possuiColunasQuebradas() {
        int quantidadeMudancasCor = 0;
        int tamanho = 0;
        for (int i = 0; i < width; i++) {
            quantidadeMudancasCor = 0;
            tamanho = 0;
            Color colorAnterior = pixelReader.getColor(i, 0);
            for (int j = 0; j < height; j++) {
                Color color = pixelReader.getColor(i, j);
                if (!colorAnterior.equals(color)) {
                    quantidadeMudancasCor++;
                }
                if (color.equals(colorBlack))
                    tamanho++;
                colorAnterior = pixelReader.getColor(i, j);
            }
            if (quantidadeMudancasCor > 2 && tamanho > 2)
                return true;
        }
        return false;
    }

    private boolean possuiLinhasQuebradas() {
        int quantidadeMudancasCor = 0;
        int tamanho = 0;
        for (int j = 0; j < height; j++) {

            quantidadeMudancasCor = 0;
            tamanho = 0;
            Color colorAnterior = pixelReader.getColor(0, j);
            for (int i = 0; i < width; i++) {
                Color color = pixelReader.getColor(i, j);
                if (!colorAnterior.equals(color)) {
                    quantidadeMudancasCor++;
                }
                if (color.equals(colorBlack))
                    tamanho++;
                colorAnterior = pixelReader.getColor(i, j);
            }
            if (quantidadeMudancasCor > 2 && tamanho > 2)
                return true;
        }
        return false;
    }

}
