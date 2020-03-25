package filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GrayScale {

    public static Image ArithmeticAverage(Image image) {
        if (image != null) {
            int w = (int) image.getWidth();
            int h = (int) image.getHeight();
            PixelReader pixelReader = image.getPixelReader();
            WritableImage writableImage = new WritableImage(w, h);
            PixelWriter pixelWriter = writableImage.getPixelWriter();
            for (int i = 1; i < w; i++) {
                for (int j = 1; j < h; j++) {
                    Color oldColor = pixelReader.getColor(i, j);
                    double media = (oldColor.getRed() + oldColor.getGreen() + oldColor.getBlue()) / 3;
                    Color newColor = new Color(media, media, media, oldColor.getOpacity());
                    pixelWriter.setColor(i, j, newColor);
                }
            }
            return writableImage;

        }
        return null;
    }

    public static Image PercentageAverage(Image image, int r, int g, int b) {
        if (image != null) {
            int w = (int) image.getWidth();
            int h = (int) image.getHeight();
            PixelReader pr = image.getPixelReader();
            WritableImage wi = new WritableImage(w, h);
            PixelWriter pw = wi.getPixelWriter();
            for (int i = 1; i < w; i++) {
                for (int j = 1; j < h; j++) {
                    Color oldCor = pr.getColor(i, j);
                    double media = ((oldCor.getRed() * r) + (oldCor.getGreen() * g) + (oldCor.getBlue() * b)) / 100;
                    Color newCor = new Color(media, media, media, oldCor.getOpacity());
                    pw.setColor(i, j, newCor);
                }
            }
            return wi;

        }
        return null;
    }
}
