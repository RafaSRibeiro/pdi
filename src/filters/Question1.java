package filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Question1 {

    private PixelReader pixelReader;

    private PixelWriter pixelWriter;

    private WritableImage writableImage;

    private int width;

    private int height;

    public Question1(Image image) {
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        pixelReader = image.getPixelReader();
        writableImage = new WritableImage(pixelReader, 0, 0, width, height);
        pixelWriter = writableImage.getPixelWriter();
    }

    public Image run(int quadrante1, int quadrante2) {
            inverteQuadrante(quadrante1);
            inverteQuadrante(quadrante2);
            return writableImage;
    }

    private void inverteQuadrante(int quadrante1) {
        int heightInicial = heightInicio(quadrante1, height);
        int heightFinal = heightFinal(quadrante1, height);
        int widthInicial = widthInicio(quadrante1, width);
        int widthtFinal = widthFinal(quadrante1, width);

        int contWidth = widthtFinal;
        int contHeigth = heightFinal;
        for (int i = widthInicial; i < widthtFinal; i++) {
            for (int j = heightInicial; j < heightFinal; j++) {
                Color color = pixelReader.getColor(contWidth, contHeigth);
                pixelWriter.setColor(i, j, color);
                contHeigth--;
            }
            contHeigth = heightFinal;
            contWidth--;
        }
    }

    private static int heightInicio(int quadrante, int totalHeight) {
        return (quadrante == 1 || quadrante == 3) ? 0 : totalHeight/2 - 1;
    }

    private static int heightFinal(int quadrante, int totalHeight) {
        return (quadrante == 1 || quadrante == 3) ? totalHeight/2 : totalHeight - 1;
    }

    private static int widthInicio(int quadrante, int totalWidth) {
        return (quadrante == 1 || quadrante == 2) ? 0 : totalWidth/2 - 1;
    }

    private static int widthFinal(int quadrante, int totalWidth) {
        return (quadrante == 1 || quadrante == 2) ? totalWidth/2 : totalWidth - 1;
    }

}
