package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {

    @FXML
    private ImageView shieldImageView;
    @FXML
    private Button closeButton;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private String firstnameTextField;
    @FXML
    private String lastnameTextField;
    @FXML
    private String usernameTextField;
    @FXML
    private ChoiceBox positionBox;
    @FXML
    private Label positionNotSelectedWarning;

    @FXML
    private void displayValue(ActionEvent event){
        String position = (String) positionBox.getValue();
        if (position == null){
            positionNotSelectedWarning.setText("Please select position");
        }
    }


    ObservableList list = FXCollections.observableArrayList();



    public void initialize(URL url, ResourceBundle resourceBundle){
        File shieldFile = new File("images/diary-icon-46564.png");
        Image shieldImage = new Image(shieldFile.toURI().toString());
        shieldImageView.setImage(shieldImage);
        loadDataToCheckBox();
    }

    public void registerButtonOnAction(ActionEvent event){

        if(setPasswordField.getText().equals(confirmPasswordField.getText())){
            registerUser();//tylko jesli hasla dzialaja to wtedy pozwala na polaczenie z baza
            confirmPasswordLabel.setText("");

        }else{
            confirmPasswordField.setText("Password does not match");
        }

    }

    public void closeButtonOnAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    private void loadDataToCheckBox(){
        list.removeAll(list);
        String a = "1";
        String b = "2";
        String c = "3";

        list.addAll(a, b, c);

        positionBox.getItems().addAll(list);



    }


    public void registerUser(){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameTextField;//.getText();
        String lastname = lastnameTextField;//.getText();
        String username = usernameTextField;//.getText();
        String password = setPasswordField.getText();
        String position = (String) positionBox.getValue();


        String insertFieldsLogin = "INSERT INTO users(name, password) VALUES ('";
        String insertValuesLogin = username + "','" + password + "')";
        String insertToRegisterLogin = insertFieldsLogin + insertValuesLogin;

        String insertFieldsUserData = "INSERT INTO employees(name, last_name, position_id) VALUES ('";
        String insertValuesUserData = firstname + "','" + lastname + "','" + position + "')";
        String insertToRegisterUserData = insertFieldsUserData + insertValuesUserData;


        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegisterLogin);
            statement.executeUpdate(insertToRegisterUserData);

            registrationMessageLabel.setText("User has been registered successfully!");

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }




    }

}
