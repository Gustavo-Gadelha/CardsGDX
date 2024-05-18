package com.cardsgdx.game.database;

import java.sql.*;

public class ConnectionHandler {
    private static final String PLAYERS_DATABASE = "jdbc:sqlite:database//players.db";

    private ConnectionHandler() {}

    public static Connection getConnection() {
        Connection connection;

        try {
            connection = DriverManager.getConnection(PLAYERS_DATABASE);
        } catch (SQLException e) {
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
