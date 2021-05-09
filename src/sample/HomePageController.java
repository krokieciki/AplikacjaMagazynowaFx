package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private Button exitButton;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView bagImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("@../../images/HomePageGrafika.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File bagFile = new File("@../../images/work-icon-4454.png");
        Image lockImage = new Image(bagFile.toURI().toString());
        bagImageView.setImage(lockImage);
    }

    public void exitButtonOnAction(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}