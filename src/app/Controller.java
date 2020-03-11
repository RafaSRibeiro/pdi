package app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    private Image image1;

    private Image image2;

    private Image image3;

    @FXML
    public void openImageAction() {
        openImage(imageView1, image1);
    }

    @FXML
    public void rasterImage(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getTarget();
        if (imageView.getImage() != null) {
            updateColorStatus(imageView.getImage(), (int) mouseEvent.getX(), (int) mouseEvent.getY());
        }
    }

    private void updateImage3() {
        imageView3.setImage(image3);
        imageView3.setFitHeight(image3.getHeight());
        imageView3.setFitWidth(image3.getWidth());
    }

    private void openImage(ImageView imageView, Image image) {
        File file = selectImage();
        if (file != null) {
            image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitHeight(image.getHeight());
            imageView.setFitWidth(image.getWidth());
        }
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

        }
    }
}
