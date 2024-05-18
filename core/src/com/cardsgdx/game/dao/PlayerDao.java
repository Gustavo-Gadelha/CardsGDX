package com.cardsgdx.game.dao;

import com.badlogic.gdx.utils.Disposable;
import com.cardsgdx.game.Player;
import com.cardsgdx.game.database.ConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao implements IDao<Player>, Disposable {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS players (id INTEGER PRIMARY KEY, name TEXT, score INTEGER);";
    private static final String INSERT_SQL = "INSERT INTO players (name, score) VALUES (?, ?);";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM players WHERE id = ?;";
    private static final String FIND_ALL_SQL = "SELECT * FROM players;";
    private static final String UPDATE_SQL = "UPDATE players SET name = ?, score = ? WHERE id = ?;";
    private static final String DELETE_SQL = "DELETE FROM players WHERE id = ?;";
    private static final String SELECT_LAST_INSERT_ID = "SELECT last_insert_rowid()";

    private final Connection connection;

    public PlayerDao() {
        this.connection = ConnectionHandler.getConnection();
        this.instantiateTable();
    }

    public void instantiateTable() {
        try (PreparedStatement statement = this.connection.prepareStatement(CREATE_TABLE_SQL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Player player) {
        try (
                PreparedStatement statement = this.connection.prepareStatement(INSERT_SQL);
                PreparedStatement function = this.connection.prepareStatement(SELECT_LAST_INSERT_ID)
        ) {
            statement.setString(1, player.getName());
            statement.setInt(2, player.getScore());
            statement.executeUpdate();

            ResultSet resultSet = function.executeQuery();
            if (resultSet.next()) player.setId(resultSet.getInt("last_insert_rowid()"));

            resultSet.close();
            System.out.println(player.getId()); // TODO: DELETE THIS AS SOON AS IT IS CONFIRMED TO WORK
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Player get(int id) {
        Player player = null;

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                player = this.toPlayerObject(resultSet);
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return player;
    }

    @Override
    public List<Player> getAll() {
        List<Player> players = new ArrayList<>();

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                players.add(toPlayerObject(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return players;
    }

    @Override
    public void update(Player player) {
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, player.getName());
            statement.setInt(2, player.getScore());
            statement.setInt(3, player.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Player player) {
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, player.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Player toPlayerObject(ResultSet rs) {
        try {
            return new Player(rs.getInt("id"), rs.getString("name"), rs.getInt("score"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dispose() {
        ConnectionHandler.closeConnection(this.connection);
    }
}
