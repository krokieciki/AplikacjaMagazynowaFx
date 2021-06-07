package Tests;
import org.junit.jupiter.api.Test;
import sample.Product;
import sample.SqlProductParser;

import java.sql.Date;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertEquals;

class SqlProductParserTest {



        @Test
        public void createOneNewShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            LocalDate localDate = LocalDate.now();
            Product product = new Product(5,"mleko",2.5,localDate);
            String queryWithParameters = parser.createOneNew(product);
            assertEquals("INSERT INTO products VALUES ('mleko', '2.5', '"+localDate+"')",queryWithParameters);
        }

        @Test
        public void createManyNewShouldReturnCorrectQueryIfAmountIsZero() {
            SqlProductParser parser = new SqlProductParser();
            LocalDate localDate = LocalDate.now();
            Product product = new Product(5, "mleko", 2.5, localDate);
            String queryWithParameters = parser.createManyNew(product,0 );
            assertEquals("INSERT INTO products VALUES ;",queryWithParameters);
        }

        @Test
        public void createManyNewShouldReturnCorrectQueryIfAmountIsNotZero() {
            SqlProductParser parser = new SqlProductParser();
            LocalDate localDate = LocalDate.now();
            Product product = new Product(5, "mleko", 2.5, localDate);
            String queryWithParameters = parser.createManyNew(product,1 );
            assertEquals("INSERT INTO products VALUES ('mleko', '2.5', '"+localDate+"');",queryWithParameters);
        }
        @Test
        public void deleteByIdShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            String queryWithParameter = parser.deleteByID(5);
            assertEquals("DELETE FROM products WHERE product_id='5'", queryWithParameter);
        }

        @Test
        public void deleteOneByNameShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            String queryWithParameter = parser.deleteOneByName("mleko");
            assertEquals("WITH MyDel AS (SELECT TOP(1) * FROM products WHERE product_name='mleko' ORDER BY expiry_date) DELETE FROM MyDel", queryWithParameter);
        }

        @Test
        public void deleteAllByNameShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            String queryWithParameter = parser.deleteAllByName("mleko");
            assertEquals("DELETE FROM products WHERE product_name='mleko'", queryWithParameter);
        }

        @Test
        public void displayAllByIDShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            String queryWithParameter = parser.displayAllByID();
            assertEquals("SELECT * FROM products ORDER BY product_id", queryWithParameter);
        }

        @Test
        public void displayAllByNameShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            String queryWithParameter = parser.displayAllByName();
            assertEquals("SELECT * FROM products ORDER BY product_name, expiry_date", queryWithParameter);
        }

        @Test
        public void displayByNameShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            String queryWithParameter = parser.displayByName("mleko");
            assertEquals("SELECT * FROM products WHERE product_name='mleko' ORDER BY expiry_date", queryWithParameter);
        }

        @Test
        public void displayCheaperShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            String queryWithParameter = parser.displayCheaper(2.54);
            assertEquals("SELECT * FROM products WHERE price<='2.54' ORDER BY product_name, price", queryWithParameter);
        }

        @Test
        public void displayMoreExpensiveShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            String queryWithParameter = parser.displayMoreExpensive(2.54);
            assertEquals("SELECT * FROM products WHERE price>='2.54' ORDER BY product_name, price", queryWithParameter);
        }

        @Test
        public void displayDateShorterShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            LocalDate localDate = LocalDate.now();
            String queryWithParameter = parser.displayDateShorter(localDate);
            assertEquals("SELECT * FROM products WHERE expiry_date<='" + localDate + "' ORDER BY product_name, expiry_date", queryWithParameter);
        }

        @Test
        public void displayDateLongerShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            LocalDate localDate = LocalDate.now();
            String queryWithParameter = parser.displayDateLonger(localDate);
            assertEquals("SELECT * FROM products WHERE expiry_date>='" + localDate + "' ORDER BY product_name, expiry_date", queryWithParameter);
        }

        @Test
        public void displayAmountAllShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            String queryWithParameter = parser.displayAmountAll();
            assertEquals("SELECT COUNT(product_id) FROM products", queryWithParameter);
        }

        @Test
        public void displayAmountByNameShouldReturnCorrectQuery() {
            SqlProductParser parser = new SqlProductParser();
            String queryWithParameter = parser.displayAmountByName("mleko");
            assertEquals("SELECT COUNT(product_id) FROM products WHERE product_name='mleko'", queryWithParameter);
        }
    }

