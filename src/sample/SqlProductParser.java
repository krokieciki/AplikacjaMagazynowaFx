package sample;

import java.sql.Date;
import java.time.LocalDate;

/*tworzy zapytania w SQL dla produktów */


public class SqlProductParser{

    public String createOneNew(Product product) {
        String query = "INSERT INTO products VALUES ('" +
                product.getProductName() + "', '" + product.getPrice() + "', '" +
                Date.valueOf(product.getExpireDate()) + "')";
        return  query;
    }

    public String createManyNew(Product product, int amount) {
        String query = "INSERT INTO products VALUES ";
        for(int i = 0; i< amount; i++)
        {
            if(i!=0) query += ", ";
            query += "('" + product.getProductName() + "', '" + product.getPrice() + "', '" +
                    Date.valueOf(product.getExpireDate()) + "')";
        }
        query += ";";
        return query;
    }

    public String deleteByID(int ID) {
        String query = "DELETE FROM products WHERE product_id='" + ID + "'";
        return query;
    }

    //usunie produkt o najkrótszej dacie
    public String deleteOneByName(String productName) {
        String query = "WITH MyDel AS (SELECT TOP(1) * FROM products WHERE product_name='" + productName +
                "' ORDER BY expiry_date) DELETE FROM MyDel";
        return  query;
    }

    public String deleteAllByName(String productName) {
        String query = "DELETE FROM products WHERE product_name='" + productName + "'";
        return query;
    }

    public String displayAllByID() {
        String query = "SELECT * FROM products ORDER BY product_id";
        return query;
    }

    public String displayAllByName() {
        String query = "SELECT * FROM products ORDER BY product_name, expiry_date";
        return query;
    }


    public String displayByName(String productName) {
        String query = "SELECT * FROM products WHERE product_name='" + productName + "' ORDER BY expiry_date";
        return query;
    }

    public String displayCheaper(double price) {
        String query = "SELECT * FROM products WHERE price<='" + price + "' ORDER BY product_name, price";
        return query;
    }

    public String displayMoreExpensive(double price) {
        String query = "SELECT * FROM products WHERE price>='" + price + "' ORDER BY product_name, price";
        return query;
    }

    public String displayDateShorter(LocalDate date) {
        String query = "SELECT * FROM products WHERE expiry_date<='" + Date.valueOf(date) +
                "' ORDER BY product_name, expiry_date";
        return query;
    }

    public String displayDateLonger(LocalDate date) {
        String query = "SELECT * FROM products WHERE expiry_date>='" + Date.valueOf(date) +
                "' ORDER BY product_name, expiry_date";
        return query;
    }

    public String displayAmountAll() {
        String query = "SELECT COUNT(product_id) FROM products";
        return query;
    }

    public String displayAmountByName(String productName) {
        String query = "SELECT COUNT(product_id) FROM products WHERE product_name='" + productName + "'";
        return query;
    }
}