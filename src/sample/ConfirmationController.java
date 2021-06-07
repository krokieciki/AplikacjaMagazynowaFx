package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ConfirmationController {

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();
    Statement statement = dbConn.getStatement();


    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;


    public void cancelButtonAction()throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Profile.fxml"));

        Stage window = (Stage) cancelButton.getScene().getWindow();
        window.setScene(new Scene(root, 520, 400));
    }

    public void deleteButtonAction()throws Exception{

        String deleteAccount = "DELETE FROM users WHERE user_id = '" + LoginController.loggedUserId + "';";
        String deleteIdFromEmployees = "UPDATE employees SET user_acc_id = null WHERE user_acc_id = '" + LoginController.loggedUserId + "';";

          try  {

              statement.executeUpdate(deleteAccount);
              statement.executeUpdate(deleteIdFromEmployees);
    }
        catch (Exception e){
        e.printStackTrace();
        e.getCause();
          }

        try{
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            Stage window = (Stage) deleteButton.getScene().getWindow();
            window.setScene(new Scene(root, 520, 400));


        }catch(Exception e){
            e.printStackTrace();
            e.getCause();

        }



    }

}
