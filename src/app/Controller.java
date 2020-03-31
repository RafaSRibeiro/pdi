package app;

import filters.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    @FXML
    ImageView imageView1;

    @FXML
    ImageView imageView2;

    @FXML
    ImageView imageView3;

    @FXML
    Label redLabel;

    @FXML
    Label greenLabel;

    @FXML
    Label blueLabel;

    @FXML
    TextField txRed;

    @FXML
    TextField txGreen;

    @FXML
    TextField txBlue;

    @FXML
    Slider thresholdSlider;

    @FXML
    RadioButton radioX;

    @FXML
    RadioButton radio3X3;

    @FXML
    RadioButton radioCross;

    private Image image1;

    private Image image2;

    private Image image3;

    @FXML
    public void openImageAction() {
        image1 = openImage(imageView1, image1);
    }

    @FXML
    public void rasterImage(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getTarget();
        if (imageView.getImage() != null) {
            updateColorStatus(imageView.getImage(), (int) mouseEvent.getX(), (int) mouseEvent.getY());
        }
    }

    @FXML
    public void grayScale() {
        applyFilter(GrayScale.ArithmeticAverage(image1));
    }

    @FXML
    public void weightedGrayScale() {
        try {
            int r = Integer.parseInt(txRed.getText());
            int g = Integer.parseInt(txGreen.getText());
            int b = Integer.parseInt(txBlue.getText());
            if ((r + g + b) > 100 || r == 0 || g == 0 || b == 0) {
                alert("Verifique os valores de R - G - B para escala de cinza, deve ter total de 100%.", Alert.AlertType.ERROR);
            } else {
                applyFilter(GrayScale.PercentageAverage(image1, r, g, b));
            }
        } catch (Exception e) {
            alert("Verifique os valores de R - G - B", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void thresholdSliderChanged() {
        applyFilter(Threshold.threshold(image1, thresholdSlider.getValue() / 255));
    };

    @FXML
    public void negative() {
        applyFilter(Negative.negative(image1));
        image1 = image3;
    }

    @FXML
    public void noise() {
        applyFilter(Noise.noises(image1, noiseType()));
    }

    public void applyFilter(Image image) {
        try {
            image3 = image;
            int width = (int) image3.getWidth();
            int height = (int) image3.getHeight();
            imageView3.setImage(image);
            imageView3.setFitHeight(height);
            imageView3.setFitWidth(width);
        } catch (Exception e) {
            alert("Erro no filtro escolhido.", Alert.AlertType.ERROR);
        }
    }

    private void updateImage3() {
        imageView3.setImage(image3);
        imageView3.setFitHeight(image3.getHeight());
        imageView3.setFitWidth(image3.getWidth());
    }

    private Image openImage(ImageView imageView, Image image) {
        File file = selectImage();
        if (file != null) {
            image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitHeight(image.getHeight());
            imageView.setFitWidth(image.getWidth());
        }
        return image;
    }

    private File selectImage() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        return file != null ? file : null;
    }

    private void updateColorStatus(Image image, int x, int y) {
        try {
            Color color = image.getPixelReader().getColor(x, y);
            redLabel.setText("R: " + (int) color.getRed() * 255);
            greenLabel.setText("G: " + (int) color.getGreen() * 255);
            blueLabel.setText("B: " + (int) color.getBlue() * 255);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private void alert(String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText("");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private int noiseType() {
        if (radio3X3.isSelected())
            return Utils.VIZINHOS_3x3;
        if (radioCross.isSelected())
            return Utils.VIZINHOS_CROSS;
        return Utils.VIZINHOS_X;
    }

}
