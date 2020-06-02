package filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Question2 {

    public static final int ALL = 597;
    public static final int RED = 539;
    public static final int GREEN = 296;
    public static final int BLUE = 697;

    private PixelReader pixelReader;

    private PixelWriter pixelWriter;

    private WritableImage writableImage;

    private Image image;

    private int width;

    private int height;

    private int[] histogramRed;
    private int[] histogramGreen;
    private int[] histogramBlue;

    private int[] accumulatedHistogramRed;
    private int[] accumulatedHistogramGreen;
    private int[] accumulatedHistogramBlue;

    private int tonesRedAmount = 255;
    private int tonesGreenAmount = 255;
    private int tonesBlueAmount = 255;

    private double firstValidPointRed = 0;
    private double firstValidPointGreen = 0;
    private double firstValidPointBlue = 0;

    private double totalPixels;

    public Question2(Image _image) {
        image = _image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        pixelReader = image.getPixelReader();
        writableImage = new WritableImage(pixelReader, 0, 0, width, height);
        pixelWriter = writableImage.getPixelWriter();

        histogramRed = histogram(image, RED);
        histogramGreen = histogram(image, GREEN);
        histogramBlue = histogram(image, BLUE);

        accumulatedHistogramRed = accumulatedHistogram(histogramRed);
        accumulatedHistogramGreen = accumulatedHistogram(histogramGreen);
        accumulatedHistogramBlue = accumulatedHistogram(histogramBlue);

        totalPixels = width * height;
    }

    public Image run() {
        aplicaAlteracao();
        return writableImage;
    }

    private void aplicaAlteracao() {
        Color newColor = Color.BLACK;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = pixelReader.getColor(i, j);
                if (i == j)
                    pixelWriter.setColor(i, j, newColor);
                else if (i > j)
                    histogramEquilize(i, j);
                else
                    pixelWriter.setColor(i, j, color);
            }
        }
    }

    public void histogramEquilize(int i, int j) {

        Color color = pixelReader.getColor(i, j);

        double accumulatedColorRed = accumulatedHistogramRed[(int) (color.getRed() * 255)];
        double accumulatedColorGreen = accumulatedHistogramGreen[(int) (color.getGreen() * 255)];
        double accumulatedColorBlue = accumulatedHistogramBlue[(int) (color.getBlue() * 255)];

        double pxR = ((tonesRedAmount - 1) / totalPixels) * accumulatedColorRed;
        double pxG = ((tonesGreenAmount - 1) / totalPixels) * accumulatedColorGreen;
        double pxB = ((tonesBlueAmount - 1) / totalPixels) * accumulatedColorBlue;

        double colorRed = (firstValidPointRed + pxR) / 255;
        double colorGreen = (firstValidPointGreen + pxG) / 255;
        double colorBlue = (firstValidPointBlue + pxB) / 255;

        Color newColor = new Color(colorRed, colorGreen, colorBlue, color.getOpacity());
        pixelWriter.setColor(i, j, newColor);
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
}
