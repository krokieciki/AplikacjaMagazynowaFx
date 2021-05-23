package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();
    Statement statement = dbConn.getStatement();
    SqlProductParser sqlProductParser = new SqlProductParser();
    String query;

    @FXML
    private ImageView shieldImageView;
    @FXML
    private Button cancelRegisterButton;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;
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

    public void registerButtonOnAction(javafx.event.ActionEvent event){

        if(setPasswordField.getText().equals(confirmPasswordField.getText())){
            registerUser();//tylko jesli hasla dzialaja to wtedy pozwala na polaczenie z baza
            confirmPasswordLabel.setText("");

        }else{
            confirmPasswordLabel.setText("Password does not match");
        }

    }

    public void cancelButtonAction()throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));

        Stage window = (Stage) cancelRegisterButton.getScene().getWindow();
        window.setScene(new Scene(root, 520, 400));
    }

    private void loadDataToCheckBox(){
        list.removeAll(list);
        String a = "Kierownik";
        String b = "Kasjer";
        String c = "Magazynier";

        list.addAll(a, b, c);

        positionBox.getItems().addAll(list);
    }

    public void registerUser(){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();
        String password = setPasswordField.getText();
        String position = (String) positionBox.getValue();


        String insertFieldsLogin = "INSERT INTO users(name, password) VALUES ('";
        String insertValuesLogin = username + "','" + password + "')";
        String insertToRegisterLogin = insertFieldsLogin + insertValuesLogin;

        String insertFieldsUserData = "INSERT INTO employees(name, last_name, position_id, user_acc_id) VALUES ('";
        String insertValuesUserData = firstname + "','" + lastname + "','" + position + "', (SELECT user_id from users where name='" + username + "'))";
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
