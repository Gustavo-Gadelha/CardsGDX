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
    private static final String FIND_BY_NAME_SQL = "SELECT * FROM players WHERE name = ?;";
    private static final String FIND_ALL_SQL = "SELECT * FROM players;";
    private static final String FIND_LIMIT_SQL = "SELECT * FROM players ORDER BY score DESC LIMIT ?;";
    private static final String UPDATE_SQL = "UPDATE players SET name = ?, score = ? WHERE id = ?;";
    private static final String DELETE_SQL = "DELETE FROM players WHERE id = ?;";

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
        try (PreparedStatement statement = this.connection.prepareStatement(INSERT_SQL)) {
            statement.setString(1, player.getName());
            statement.setInt(2, player.getScore());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertOrUpdate(Player player) {
        Player playerEntity = this.getByName(player.getName());
        if (playerEntity != null) {
            player.setId(playerEntity.getId());
            this.update(player);
        } else {
            this.insert(player);
        }
    }

    @Override
    public Player get(int id) {
        Player player = null;

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                player = this.mapToPlayerObject(resultSet);
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return player;
    }

    public Player getByName(String name) {
        Player player = null;

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_NAME_SQL)) {
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                player = this.mapToPlayerObject(resultSet);
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
                players.add(this.mapToPlayerObject(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return players;
    }

    public List<Player> getTop(int limit) {
        List<Player> players = new ArrayList<>(10);

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_LIMIT_SQL)) {
            statement.setInt(1, limit);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                players.add(this.mapToPlayerObject(resultSet));
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

    private Player mapToPlayerObject(ResultSet rs) {
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
