package filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Threshold {

    public static Image threshold(Image image, double threshold) {
        if (image != null) {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            PixelReader pixelReader = image.getPixelReader();
            WritableImage writableImage = new WritableImage(width, height);
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            for (int i = 1; i < width; i++) {
                for (int j = 1; j < height; j++) {
                    Color oldColor = pixelReader.getColor(i, j);
                    Color newColor;
                    if (oldColor.getRed() >= threshold) {
                        newColor = new Color(1, 1, 1, oldColor.getOpacity());
                    } else {
                        newColor = new Color(0, 0, 0, oldColor.getOpacity());
                    }
                    pixelWriter.setColor(i, j, newColor);

                }
            }
            return writableImage;

        }
        return null;
    }

    public static Image thresholdRGB(Image image, Image imageDoenca, double thresholdRed) {
        if (image != null) {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            PixelReader pixelReader = image.getPixelReader();
            WritableImage writableImage = new WritableImage(width, height);
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            Color mediaColor = getColorFerrugem(imageDoenca);
            for (int i = 1; i < width; i++) {
                for (int j = 1; j < height; j++) {
                    Color oldColor = pixelReader.getColor(i, j);
                    if (oldColor.getRed() + thresholdRed >= mediaColor.getRed() && oldColor.getRed() - thresholdRed <= mediaColor.getRed() &&
                            oldColor.getBlue() + thresholdRed >= mediaColor.getBlue() && oldColor.getBlue() - thresholdRed <= mediaColor.getBlue() &&
                            oldColor.getGreen() + thresholdRed >= mediaColor.getGreen() && oldColor.getGreen() - thresholdRed <= mediaColor.getGreen()) {
                        pixelWriter.setColor(i, j, oldColor);
                    } else {
                        Color newColor = new Color(0, 0, 0, oldColor.getOpacity());
                        pixelWriter.setColor(i, j, newColor);
                    }
                }
            }
            return writableImage;

        }
        return null;
    }

    public static Color getColorFerrugem(Image imageDoenca) {
        int width = (int) imageDoenca.getWidth();
        int height = (int) imageDoenca.getHeight();

        double mediaRed = 0;
        double mediaGreen = 0;
        double mediaBlue = 0;
        double mediaOpacity = 0;
        PixelReader pixelReader = imageDoenca.getPixelReader();
        int i = 0;
        int j = 0;
        for (i = 1; i < width; i++) {
            for (j = 1; j < height; j++) {
                Color doencaColor = pixelReader.getColor(i, j);
                mediaRed += doencaColor.getRed();
                mediaGreen += doencaColor.getGreen();
                mediaBlue += doencaColor.getBlue();
                mediaOpacity += doencaColor.getOpacity();
            }
        }
        int totalPixel = i * j;
        mediaRed = (mediaRed / totalPixel);
        mediaGreen = (mediaGreen / totalPixel);
        mediaBlue = (mediaBlue / totalPixel);
        mediaOpacity = (mediaOpacity / totalPixel);
        return new Color(mediaRed, mediaGreen, mediaBlue, mediaOpacity);
    }

}