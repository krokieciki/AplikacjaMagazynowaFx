package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TableProductsController implements Initializable {

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();
    Statement statement = dbConn.getStatement();
    String query;
    ResultSet rs;

    @FXML
    private Button goProfile;
    @FXML
    private Button logoutButton;
    @FXML
    private ImageView storageImageView;

    //tabela
    @FXML
    private TableView<ProductModel> table;
    @FXML
    private TableColumn<ProductModel, String> column_id;
    @FXML
    private TableColumn<ProductModel, String> column_name;
    @FXML
    private TableColumn<ProductModel, String> column_price;
    @FXML
    private TableColumn<ProductModel, String> column_date;
    @FXML
    private TableColumn<ProductModel, String> column_quantity;
    //ObservableList<ProductModel> oblist = FXCollections.observableArrayList();

    //dodawanie i usuwanie
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField priceInput;
    @FXML
    private DatePicker dateInput;
    @FXML
    private TextField quantityInput;

    //filtry
    @FXML
    private TextField nameFilterInput;
    @FXML
    private TextField priceFromInput;
    @FXML
    private TextField priceToInput;
    @FXML
    private DatePicker dateFromInput;
    @FXML
    private DatePicker dateToInput;
    @FXML
    private TextField quantityFromInput;
    @FXML
    private TextField quantityToInput;
    @FXML
    private Button applyButton;
    @FXML
    private Button clearButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File storageFile = new File("images/storage-icon.png");
        Image storageImage = new Image(storageFile.toURI().toString());
        storageImageView.setImage(storageImage);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        column_id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        column_date.setCellValueFactory(new PropertyValueFactory<>("expiry_date"));
        column_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        try {
            populateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void populateTable() throws SQLException {
        ObservableList<ProductModel> oblist = FXCollections.observableArrayList();
        rs = statement.executeQuery("SELECT * FROM products");
        oblist.clear();
        while (rs.next()) {
            oblist.add(new ProductModel(rs.getString("product_id"),
                    rs.getString("product_name"), rs.getString("price"),
                    rs.getString("expiry_date"), rs.getString("quantity")));
        }
        table.setItems(oblist);

    }

    public void addButtonOnAction() {
        ProductModel product = new ProductModel();
        product.setProduct_name(nameInput.getText());
        product.setPrice((priceInput.getText()));
        product.setExpiry_date(dateInput.getValue().toString());
        product.setQuantity(quantityInput.getText());

        try {
            conn.createStatement().executeUpdate("INSERT INTO products VALUES ('" +nameInput.getText() + "', '" +
                            priceInput.getText() + "', '" + dateInput.getValue().toString() + "', '" +
                            quantityInput.getText() + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        nameInput.clear();
        priceInput.clear();
        dateInput.getEditor().clear();
        quantityInput.clear();

        try {
            populateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteButtonOnAction() {
        ObservableList<ProductModel> oblist = FXCollections.observableArrayList();
        oblist = table.getSelectionModel().getSelectedItems();
        String idForDelete = "";
        for (ProductModel product : oblist) {
            idForDelete += product.getProduct_id() +", ";
        }
        idForDelete = idForDelete.substring(0, idForDelete.length() - 2);

        try {
            conn.createStatement().executeUpdate("DELETE FROM products WHERE product_id IN (" +
                    idForDelete + ");");
            populateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void applyButtonOnAction() {
        String name = nameFilterInput.getText();
        String priceFrom = priceFromInput.getText();
        String priceTo = priceToInput.getText();


        String dateFrom;
        if(dateFromInput.getValue() != null) {
            dateFrom = dateFromInput.getValue().toString();
        }else dateFrom = "";

        String dateTo;
        if(dateToInput.getValue() != null) {
            dateTo = dateToInput.getValue().toString();
        }else dateTo = "";

        String quantityFrom = quantityFromInput.getText();
        String quantityTo = quantityToInput.getText();

        boolean addAND = false;

        query = "SELECT * FROM products";

        if(name.isEmpty() && priceFrom.isEmpty() && priceTo.isEmpty() && dateFrom.isEmpty() &&
                dateTo.isEmpty() && quantityFrom.isEmpty() && quantityTo.isEmpty()) {
            query += ";";
        } else{
            query += " WHERE";
            if (!name.isEmpty()){
                query += " product_name LIKE '%" + name + "%'";
                addAND = true;
            }
            if (!priceFrom.isEmpty()) {
                if(addAND) query += " AND";
                query += " price >= '" + priceFrom + "'";
                addAND = true;
            }
            if (!priceTo.isEmpty()) {
                if(addAND) query += " AND";
                query += " price <= '" + priceTo + "'";
                addAND = true;
            }
            if (!dateFrom.isEmpty()) {
                if(addAND) query += " AND";
                query += " expiry_date >= '" + dateFrom + "'";
                addAND = true;
            }
            if (!dateTo.isEmpty()) {
                if(addAND) query += " AND";
                query += " expiry_date <= '" + dateTo + "'";
                addAND = true;
            }
            if (!quantityFrom.isEmpty()) {
                if(addAND) query += " AND";
                query += " quantity >= '" + quantityFrom + "'";
                addAND = true;
            }
            if (!quantityTo.isEmpty()) {
                if(addAND) query += " AND";
                query += " quantity <= '" + quantityTo + "'";
            }
            query += ";";
        }

        ObservableList<ProductModel> oblist = FXCollections.observableArrayList();
        try {
            rs = statement.executeQuery(query);

        oblist.clear();
        while (rs.next()) {
            oblist.add(new ProductModel(rs.getString("product_id"),
                    rs.getString("product_name"), rs.getString("price"),
                    rs.getString("expiry_date"), rs.getString("quantity")));
        }
        table.setItems(oblist);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void clearButtonOnAction() {
        nameFilterInput.clear();
        priceFromInput.clear();
        priceToInput.clear();
        dateFromInput.setValue(null);
        dateToInput.setValue(null);
        quantityFromInput.clear();
        quantityToInput.clear();

        try {
            populateTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void logoutButtonOnAction() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));

        Stage window = (Stage) goProfile.getScene().getWindow();
        window.setScene(new Scene(root, 520, 400));
    }

    public void switchToProfile() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Profile.fxml"));

        Stage window = (Stage) goProfile.getScene().getWindow();
        window.setScene(new Scene(root, 520, 400));
    }



    }

