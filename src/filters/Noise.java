package filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Noise {

	public static Image noises(Image image, int neighborType) {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();

		PixelReader pixelReader = image.getPixelReader();
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int i = 1; i < width-1; i++) {
			for (int j = 1; j < height-1; j++) {
				Color color = pixelReader.getColor(i,j);
				Pixel pixel = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), i, j);
				findNeighbor(image, pixel);
				Pixel pixels[] = null;
				if (neighborType == Utils.VIZINHOS_3x3)
					pixels = pixel.neighbor3X3;
				if (neighborType == Utils.VIZINHOS_CROSS)
					pixels = pixel.neighborCross;
				if (neighborType == Utils.VIZINHOS_X)
					pixels = pixel.neighborX;
				double red = Utils.median(pixels, Utils.CHANNEL_R);
				double green = Utils.median(pixels, Utils.CHANNEL_G);
				double blue = Utils.median(pixels, Utils.CHANNEL_B);
				Color newColor = new Color(red, green, blue, color.getOpacity());
				pixelWriter.setColor(i, j, newColor);
			}
		}
		return writableImage;
	}

	private static void findNeighbor(Image image, Pixel pixel) {
		pixel.neighborX = findNeighborX(image, pixel);
		pixel.neighborCross = findNeighborCross(image, pixel);
		pixel.neighbor3X3 = findNeighbor3X3(image, pixel);
	}

	private static Pixel[] findNeighborX(Image image, Pixel pixel) {
		Pixel[] neighbor = new Pixel[5];
		PixelReader pixelReader = image.getPixelReader();

		Color color = pixelReader.getColor(pixel.x-1, pixel.y+1);
		neighbor[0] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x-1, pixel.y+1);

		color = pixelReader.getColor(pixel.x+1, pixel.y-1);
		neighbor[1] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x+1, pixel.y-1);

		color = pixelReader.getColor(pixel.x-1, pixel.y-1);
		neighbor[2] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x-1, pixel.y-1);

		color = pixelReader.getColor(pixel.x+1, pixel.y+1);
		neighbor[3] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x+1, pixel.y+1);

		neighbor[4] = pixel;

		return neighbor;
	}

	private static Pixel[] findNeighbor3X3(Image image, Pixel pixel) {
		Pixel[] neighbor = new Pixel[9];
		PixelReader pixelReader = image.getPixelReader();

		Color color = pixelReader.getColor(pixel.x-1, pixel.y+1);
		neighbor[0] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x-1, pixel.y+1);

		color = pixelReader.getColor(pixel.x+1, pixel.y-1);
		neighbor[1] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(),pixel.x+1, pixel.y-1);

		color = pixelReader.getColor(pixel.x-1, pixel.y-1);
		neighbor[2] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x-1, pixel.y-1);

		color = pixelReader.getColor(pixel.x+1, pixel.y+1);
		neighbor[3] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x+1, pixel.y+1);

		color = pixelReader.getColor(pixel.x+1, pixel.y);
		neighbor[4] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x+1, pixel.y);

		color = pixelReader.getColor(pixel.x-1, pixel.y);
		neighbor[5] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x-1, pixel.y);

		color = pixelReader.getColor(pixel.x, pixel.y+1);
		neighbor[6] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x, pixel.y+1);

		color = pixelReader.getColor(pixel.x, pixel.y-1);
		neighbor[7] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x, pixel.y-1);

		neighbor[8] = pixel;

		return neighbor;



	}

	private static Pixel[] findNeighborCross(Image image, Pixel pixel) {
		Pixel[] neighbor = new Pixel[5];
		PixelReader pixelReader = image.getPixelReader();

		Color color = pixelReader.getColor(pixel.x+1, pixel.y);
		neighbor[0] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x+1, pixel.y);

		color = pixelReader.getColor(pixel.x-1, pixel.y);
		neighbor[1] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x-1, pixel.y);

		color = pixelReader.getColor(pixel.x, pixel.y+1);
		neighbor[2] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x, pixel.y+1);

		color = pixelReader.getColor(pixel.x, pixel.y-1);
		neighbor[3] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), pixel.x, pixel.y-1);

		neighbor[4] = pixel;

		return neighbor;

	}

}
