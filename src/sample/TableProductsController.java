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

    int id;

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
    private TextField searchInput;
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
        rs = conn.createStatement().executeQuery("SELECT * FROM products");
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

/*    public void createOneNew(Product product)
    {
        query = sqlProductParser.createOneNew(product);

        try
        {
            int rows = statement.executeUpdate(query);
            System.out.println("Zmieniono " + rows + " wierszy");

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    public void createManyNew(Product product, int amount) {
        query = sqlProductParser.createManyNew(product, amount);

        try {
            int rows = statement.executeUpdate(query);
            System.out.println("Zmieniono " + rows + " wierszy");

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    public void deleteByID(int ID) {
        query = sqlProductParser.deleteByID(ID);

        try {
            int rows = statement.executeUpdate(query);
            System.out.println("Zmieniono " + rows + " wierszy");

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    // usunie produkt o podanej nazwie z najkrótszą datą
    public void deleteOneByName(String productName) {
        query = sqlProductParser.deleteOneByName(productName);

        try {
            int rows = statement.executeUpdate(query);
            System.out.println("Zmieniono " + rows + " wierszy");

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    public void deleteAllByName(String productName) {
        query = sqlProductParser.deleteAllByName(productName);

        try {
            int rows = statement.executeUpdate(query);
            System.out.println("Zmieniono " + rows + " wierszy");

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    public void displayAllByID() {
        query = sqlProductParser.displayAllByID();

        try {
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                do {
                    System.out.printf("%7s %11s %7s PLN %12s\n", "id " + rs.getString(1),
                            rs.getString(2), rs.getString(3), rs.getString(4));
                }while (rs.next());
            }

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    public void displayAllByName() {
        query = sqlProductParser.displayAllByName();

        try {
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                do {
                    System.out.printf("%7s %11s %7s PLN %12s\n", "id " + rs.getString(1),
                            rs.getString(2), rs.getString(3), rs.getString(4));
                }while (rs.next());
            }

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    public void displayByName(String productName) {
        query = sqlProductParser.displayByName(productName);

        try {
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                do {
                    System.out.printf("%7s %11s %7s PLN %12s\n", "id " + rs.getString(1),
                            rs.getString(2), rs.getString(3), rs.getString(4));
                }while (rs.next());
            }
            ;
        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    public void displayCheaperThen(double max) {
        query = sqlProductParser.displayCheaper(max);

        try {
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                do {
                    System.out.printf("%7s %11s %7s PLN %12s\n", "id " + rs.getString(1),
                            rs.getString(2), rs.getString(3), rs.getString(4));
                }while (rs.next());
            }

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    public void displayMoreExpensiveThen(double min) {
        query = sqlProductParser.displayMoreExpensive(min);

        try {
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                do {
                    System.out.printf("%7s %11s %7s PLN %12s\n", "id " + rs.getString(1),
                            rs.getString(2), rs.getString(3), rs.getString(4));
                }while (rs.next());
            }

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    public void displayDateShorter(LocalDate date) {
        query = sqlProductParser.displayDateShorter(date);

        try {
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                do {
                    System.out.printf("%7s %11s %7s PLN %12s\n", "id " + rs.getString(1),
                            rs.getString(2), rs.getString(3), rs.getString(4));
                }while (rs.next());
            }

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    public void displayDateLonger(LocalDate date) {
        query = sqlProductParser.displayDateLonger(date);

        try {
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                do {
                    System.out.printf("%7s %11s %7s PLN %12s\n", "id " + rs.getString(1),
                            rs.getString(2), rs.getString(3), rs.getString(4));
                }while (rs.next());
            }

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    public void displayAmountAll() {
        query = sqlProductParser.displayAmountAll();

        try {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            System.out.println("W magazynie jest " + rs.getInt(1) + " produktów");

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
    }

    public void displayAmountByName(String productName) {
        query = sqlProductParser.displayAmountByName(productName);

        try {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            System.out.println("W magazynie jest " + rs.getInt(1) + " szt. " + productName);

        } catch (SQLException e) {
            System.out.println("Błąd operacji");
        }
<<<<<<< HEAD
    }*/
=======
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

    public void getId(int id) {
    this.id = id;
    }
>>>>>>> michal
}
