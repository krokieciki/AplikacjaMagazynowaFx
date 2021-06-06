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

public class ProfileController implements Initializable {

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();
    Statement statement = dbConn.getStatement();

    @FXML
    private ImageView profileImageView;
    @FXML
    private Button cancelButton;
    @FXML
    private Label UsernameLabel;
    @FXML
    private Label FirstnameLabel;
    @FXML
    private Label LastnameLabel;
    @FXML
    private Label PositionLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File profileFile = new File("images/profile-icon.png");
        Image profileImage = new Image(profileFile.toURI().toString());
        profileImageView.setImage(profileImage);
        try {
            GetUserProfileQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
    public void GetUserProfileQuery() throws SQLException{
        ResultSet employeeQueryResult;
        employeeQueryResult = statement.executeQuery("SELECT name, last_name, position_id FROM employees WHERE user_acc_id = " + LoginController.loggedUserId);
        employeeQueryResult.next();
        FirstnameLabel.setText(employeeQueryResult.getString("name"));
        LastnameLabel.setText(employeeQueryResult.getString("last_name"));
        PositionLabel.setText(employeeQueryResult.getString("position_id"));
        ResultSet userQueryResult;
        userQueryResult = statement.executeQuery("SELECT name FROM users WHERE user_id = " + LoginController.loggedUserId);
            userQueryResult.next();
            UsernameLabel.setText(userQueryResult.getString("name"));
    }

    public void cancelButtonAction()throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TableProducts.fxml"));

        Stage window = (Stage) cancelButton.getScene().getWindow();
        window.setScene(new Scene(root, 900, 550));
    }

}
