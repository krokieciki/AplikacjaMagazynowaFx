package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();
    Statement statement = dbConn.getStatement();
    SqlProductParser sqlProductParser = new SqlProductParser();
    String query;

    @FXML
    private Button exitButton;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView bagImageView;
    @FXML
    private Button goLoginButton;
    @FXML
    private Button goRegisterButton;

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

    public void switchToLogin() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        Stage window = (Stage) goLoginButton.getScene().getWindow();
        window.setScene(new Scene(root, 520, 400));
    }

    public void switchToRegister() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));

        Stage window = (Stage) goRegisterButton.getScene().getWindow();
        window.setScene(new Scene(root, 520, 645));
    }

}