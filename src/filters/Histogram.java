package filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Histogram {

    public static final int ALL = 597;
    public static final int RED = 539;
    public static final int GREEN = 296;
    public static final int BLUE = 697;

    public static int[] histogram(Image image, int color) {
        int[] values = new int[256];
        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (color == ALL || color == RED)
                    values[(int) Math.round(pixelReader.getColor(i, j).getRed() * 255)]++;
                if (color == ALL || color == BLUE)
                    values[(int) Math.round(pixelReader.getColor(i, j).getGreen() * 255)]++;
                if (color == ALL || color == GREEN)
                    values[(int) Math.round(pixelReader.getColor(i, j).getBlue() * 255)]++;
            }
        }
        return values;
    }

    public static int[] accumulatedHistogram(int[] histogram) {
        int[] values = new int[histogram.length];

        int valueTemp = histogram[0];
        values[0] = valueTemp;
        for (int i = 1; i < values.length; i++) {
            values[i] = histogram[i] + valueTemp;
            valueTemp = values[i];
        }
        return values;
    }

    public static Image histogramEquilize(Image image, boolean onlyValidPixels) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        PixelReader pixelReader = image.getPixelReader();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        int[] histogramRed = histogram(image, RED);
        int[] histogramGreen = histogram(image, GREEN);
        int[] histogramBlue = histogram(image, BLUE);

        int[] accumulatedHistogramRed = accumulatedHistogram(histogramRed);
        int[] accumulatedHistogramGreen = accumulatedHistogram(histogramGreen);
        int[] accumulatedHistogramBlue = accumulatedHistogram(histogramBlue);

        int tonesRedAmount = onlyValidPixels ? 255 : tonesAmount(histogramRed);
        int tonesGreenAmount = onlyValidPixels ? 255 : tonesAmount(histogramGreen);
        int tonesBlueAmount = onlyValidPixels ? 255 : tonesAmount(histogramBlue);

        double firstValidPointRed = onlyValidPixels ? 0 : firstValidPoint(histogramRed);
        double firstValidPointGreen = onlyValidPixels ? 0 : firstValidPoint(histogramGreen);
        double firstValidPointBlue = onlyValidPixels ? 0 : firstValidPoint(histogramBlue);

        double totalPixels = width * height;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = pixelReader.getColor(i, j);

                double accumulatedColorRed = accumulatedHistogramRed[(int) (color.getRed() * 255)];
                double accumulatedColorGreen = accumulatedHistogramGreen[(int) (color.getGreen() * 255)];
                double accumulatedColorBlue = accumulatedHistogramBlue[(int) (color.getBlue() * 255)];

                double pxR = ((tonesRedAmount - 1) / totalPixels) * accumulatedColorRed;
                double pxG = ((tonesGreenAmount - 1) / totalPixels) * accumulatedColorGreen;
                double pxB = ((tonesBlueAmount - 1) / totalPixels) * accumulatedColorBlue;

                double colorRed = (firstValidPointRed + pxR) / 255;
                double colorGreen = (firstValidPointGreen + pxR) / 255;
                double colorBlue = (firstValidPointBlue + pxR) / 255;

                Color newColor = new Color(colorRed, colorGreen, colorBlue, color.getOpacity());
                pixelWriter.setColor(i, j, newColor);
            }
        }

        return writableImage;
    }

    private static int tonesAmount(int[] histogram) {
        int amount = 0;
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > 0)
                amount++;
        }
        return amount;
    }

    private static double firstValidPoint(int[] histogram) {
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > 0)
                return i;
        }
        return 0;
    }
}
