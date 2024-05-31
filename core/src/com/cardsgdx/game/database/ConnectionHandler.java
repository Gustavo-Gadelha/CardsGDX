package com.cardsgdx.game.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class ConnectionHandler {
    private static final String JDBC_SQLITE_DRIVER = "org.sqlite.JDBC";
    private static final String PLAYERS_DATABASE = "jdbc:sqlite:database/players.db";

    static {
        try {
            Files.createDirectories(Paths.get("database"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionHandler() {}

    public static Connection getConnection() {
        Connection connection;

        try {
            Class.forName(JDBC_SQLITE_DRIVER);
            connection = DriverManager.getConnection(PLAYERS_DATABASE);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
