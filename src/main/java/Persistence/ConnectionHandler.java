package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {
    private static ConnectionHandler instance;
    private String url = "127.0.0.1";
    private int port = 3306;
    private String dbName = "beermachine";
    private String username = "root";
    private String password = "secret";
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
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            connection = DriverManager.getConnection("jdbc:mysql://" + url + ":" + port + "/" + dbName, username, password);

            // connection = DriverManager.getConnection("jdbc:mysql://" + url + ":" + port + "/" + dbName, username, password);

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