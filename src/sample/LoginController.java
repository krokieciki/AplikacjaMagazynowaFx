package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import java.net.URL;

public class LoginController implements Initializable{

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();
    Statement statement = dbConn.getStatement();
    SqlProductParser sqlProductParser = new SqlProductParser();
    String query;

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button loginButton;

    int id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("images/loginGrafika.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File lockFile = new File("images/login-icon-3042.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }

    public void loginButtonOnAction(ActionEvent event){

        if (usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Proszę podać nazwę i hasło użytkownika");
        }

    }

    public void cancelButtonAction()throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));

        Stage window = (Stage) cancelButton.getScene().getWindow();
        window.setScene(new Scene(root, 520, 400));
    }


    public void validateLogin(){

        String verifyLogin = "SELECT count(1) FROM users WHERE name = '" + usernameTextField.getText() + "' AND password ='" + enterPasswordField.getText() +  "'";

        try{

            ResultSet queryResult = statement.executeQuery(verifyLogin);
            ResultSet idResult = statement.executeQuery("SELECT user_id from users where name =" + "'" + usernameTextField.getText() + "';");

            this.id = idResult.getInt(1);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    //loginMessageLabel.setText("Zalogowano pomyślnie");
                    switchToProducts();
                }else{
                    loginMessageLabel.setText("Podano zły login lub hasło, spróbuj ponownie");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void switchToProducts() throws Exception{
        try{

            FXMLLoader loader = FXMLLoader.load(getClass().getResource("TableProducts.fxml"));
            Parent root = loader.load();
            Stage window = (Stage) loginButton.getScene().getWindow();
            window.setScene(new Scene(root, 900, 550));

            TableProductsController controller = loader.getController();

            controller.getId(id);




        }catch(Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }

}
