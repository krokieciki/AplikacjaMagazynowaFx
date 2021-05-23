package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    public Connection databaseLink;
    public Statement statement;
    final String connectingString = "jdbc:sqlserver://127.0.0.1\\MYSQL:1433;Database=magazyn;" +
            "integratedSecurity=True";
    final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public Connection getConnection(){
        /*String databaseName = "magazyn";
        String databaseUser = "";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost/" + databaseName;*/

        try{
            Class.forName(DRIVER);
            databaseLink = DriverManager.getConnection(connectingString);

        }catch(Exception e){
            System.out.print("Błąd przy tworzeniu połączenia\n");
            e.printStackTrace();
        }

        return databaseLink;
    }

    public Statement getStatement() {
        try {
            statement = databaseLink.createStatement();

        } catch (SQLException e) {
            System.out.print("Błąd przy tworzeniu statmentu\n");
            e.printStackTrace();
        }
        return statement;
    }

    public void DatabaseDisconnect() throws SQLException {
        try {
            if (databaseLink != null && !databaseLink.isClosed()) {
                databaseLink.close();
            }
        } catch (Exception e){
            System.out.print("Błąd przy zamykaniu połączenia\n");
            e.printStackTrace();
        }
    }
}
