package filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Negative {

	public static Image negative(Image image) {
		if (image != null) {
			int width = (int) image.getWidth();
			int height = (int) image.getHeight();

			PixelReader pixelReader = image.getPixelReader();
			WritableImage writableImage = new WritableImage(width, height);
			PixelWriter pixelWriter = writableImage.getPixelWriter();

			for (int i = 1; i < width; i++) {
				for (int j = 1; j < height; j++) {
					Color oldColor = pixelReader.getColor(i, j);
					double newRed = 1 - oldColor.getRed();
					double newGreen = 1 - oldColor.getGreen();
					double newBlue = 1 - oldColor.getBlue();
					Color newCor = new Color(newRed, newGreen, newBlue, oldColor.getOpacity());
					pixelWriter.setColor(i, j, newCor);
				}
			}
			return writableImage;
		}
		return null;
	}

}
