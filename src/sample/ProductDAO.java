package sample;

import java.sql.*;
import java.time.LocalDate;

public class ProductDAO {
    private SqlProductParser sqlProductParser;
    String query;
    Statement statement;

    public ProductDAO(Statement statement) {

        this.sqlProductParser = new SqlProductParser();
        this.statement = statement;
    }

    public void createOneNew(Product product)
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
    }
}
