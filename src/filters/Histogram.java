package filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class Histogram {

    public static int[] histogram(Image image) {
        int[] values = new int[256];
        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                values[(int) Math.round(pixelReader.getColor(i,j).getRed()*255)]++;
                values[(int) Math.round(pixelReader.getColor(i,j).getGreen()*255)]++;
                values[(int) Math.round(pixelReader.getColor(i,j).getBlue()*255)]++;
            }
        }
        return values;
    }
}
