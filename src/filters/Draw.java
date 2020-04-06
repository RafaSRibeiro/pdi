package filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Draw {

    public static Image square(Image image, int pressedX, int pressedY, int releasedX, int releasedY) {
        try {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            PixelReader pixelReader = image.getPixelReader();
            WritableImage writableImage = new WritableImage(pixelReader, 0, 0, width, height);
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            int tempSwap;
            if (pressedY > releasedY) {
                tempSwap = pressedY;
                pressedY = releasedY;
                releasedY = tempSwap;
            }
            if (pressedX > releasedX) {
                tempSwap = pressedX;
                pressedX = releasedX;
                releasedX = tempSwap;
            }

            Color newColor = Color.BLUE;
            for (int i = pressedX; i <= releasedX; i++) {
                pixelWriter.setColor(i, pressedY, newColor);
                pixelWriter.setColor(i, releasedY, newColor);
            }

            for (int i = pressedY; i <= releasedY; i++) {
                pixelWriter.setColor(pressedX, i, newColor);
                pixelWriter.setColor(releasedX, i, newColor);
            }
            return writableImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
