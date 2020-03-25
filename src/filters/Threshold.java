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
                    if (oldColor.getRed()  >= threshold) {
                        newColor = new Color(1,1, 1, oldColor.getOpacity());
                    } else {
                        newColor = new Color(0, 0, 0,oldColor.getOpacity());
                    }
                    pixelWriter.setColor(i, j, newColor);

                }
            }
            return writableImage;

        }
        return null;
    }

}