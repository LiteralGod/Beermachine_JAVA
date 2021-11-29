package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {
    private static ConnectionHandler instance;
    private String url = "localhost";
    private int port = 5432;
    private String dbName = "beermachinedb";
    private String username = "postgres";
    private String password = "nicolai10";
    private Connection connection;


    public ConnectionHandler() {
        initSQLDatabase();
    }

    public static ConnectionHandler getInstance() {
        if (instance == null) {
            instance = new ConnectionHandler();
        }
        return instance;
    }


    private void initSQLDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + dbName, username, password);
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace();
        } finally {
            if (connection == null) System.exit(-1);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}