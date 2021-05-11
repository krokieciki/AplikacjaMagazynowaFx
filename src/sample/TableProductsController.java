package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class TableProductsController implements Initializable {
    @FXML
    private ImageView storageImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("@../../images/storage-icon.png");
        Image storageImage = new Image(brandingFile.toURI().toString());
        storageImageView.setImage(storageImage);
    }
}
