package com.cardsgdx.game.database;

import java.sql.*;

public class ConnectionHandler {
    private static final String JDBC_SQLITE_DRIVER = "org.sqlite.JDBC";
    private static final String PLAYERS_DATABASE = "jdbc:sqlite:database//players.db";

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
        if (connection == null) return;

        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
