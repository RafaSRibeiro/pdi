package filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class AdditionSubtraction {

	public static Image addition(Image image1, Image image2, double opacity1, double opacity2) {
		return run(false, image1, image2, opacity1, opacity2);
	}

	public static Image subtraction(Image image1, Image image2, double opacity1, double opacity2) {
		return run(true, image1, image2, opacity1, opacity2);
	}

    private static Image run(boolean subtraction, Image image1, Image image2, double opacity1, double opacity2) {
        if (image1 != null) {

            int width1 = (int) image1.getWidth();
            int height1 = (int) image1.getHeight();
            int width2 = (int) image2.getWidth();
            int height2 = (int) image2.getHeight();
            int wMin = Math.min(width1, width2);
            int hMin = Math.min(height1, height2);

            PixelReader pixelReader = image1.getPixelReader();
            PixelReader pr2 = image2.getPixelReader();
            WritableImage writableImage = new WritableImage(wMin, hMin);
            PixelWriter pixelWriter = writableImage.getPixelWriter();
            for (int i = 1; i < wMin; i++) {
                for (int j = 1; j < hMin; j++) {
                    Color oldColor = pixelReader.getColor(i, j);
                    Color oldColor2 = pr2.getColor(i, j);
                    double newRed = calcColor(subtraction, oldColor.getRed(), oldColor2.getRed(), opacity1, opacity2);
                    double newGreen = calcColor(subtraction, oldColor.getGreen(), oldColor2.getGreen(), opacity1, opacity2);
                    double newBlue = calcColor(subtraction, oldColor.getBlue(), oldColor2.getBlue(), opacity1, opacity2);
                    Color newColor = new Color(newRed, newGreen, newBlue, oldColor.getOpacity());
                    pixelWriter.setColor(i, j, newColor);
                }
            }
            return writableImage;
        }
        return null;
    }

    private static double calcColor(boolean subtrair, double valor1, double valor2, double opacity1, double opacity2) {
        if (subtrair)
            return valor1 - valor2 < 0 ? 0 : valor1 - valor2;
        return ((valor1 * (opacity1 / 100)) + (valor2 * (opacity2 / 100)));
    }

}
