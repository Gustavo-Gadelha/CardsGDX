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

public class PlayerDao implements IPlayerDao, Disposable {
    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS players (id INTEGER PRIMARY KEY, name TEXT UNIQUE, score INTEGER);";

    private static final String INSERT_SQL =
            "INSERT INTO players (name, score) VALUES (?, ?) RETURNING *;";

    private static final String FIND_BY_ID_SQL =
            "SELECT * FROM players WHERE id = ?;";

    private static final String FIND_BY_NAME_SQL =
            "SELECT * FROM players WHERE name = ?;";

    private static final String FIND_ALL_SQL =
            "SELECT * FROM players;";

    private static final String FIND_LIMIT_SQL =
            "SELECT * FROM players ORDER BY score DESC LIMIT ?;";

    private static final String UPDATE_SQL =
            "UPDATE players SET name = ?, score = ? WHERE id = ?;";

    private static final String DELETE_SQL =
            "DELETE FROM players WHERE id = ?;";

    private final Connection connection;

    public PlayerDao() {
        this.connection = ConnectionHandler.getConnection();

        try (PreparedStatement statement = this.connection.prepareStatement(CREATE_TABLE_SQL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Player insert(Player player) {
        // Inserts a new Player if player name isn't on the database, else updates player score, always return modified player
        Player insertedPlayer = null;

        try (PreparedStatement statement = this.connection.prepareStatement(INSERT_SQL)) {
            statement.setString(1, player.getName());
            statement.setInt(2, player.getScore());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    insertedPlayer = PlayerDao.toPlayer(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return insertedPlayer;
    }

    @Override
    public Player get(long id) {
        Player player = null;

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    player = PlayerDao.toPlayer(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return player;
    }

    @Override
    public Player getByName(String name) {
        Player player = null;

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_NAME_SQL)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    player = PlayerDao.toPlayer(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return player;
    }

    @Override
    public List<Player> getAll() {
        List<Player> players = new ArrayList<>();

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_ALL_SQL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    players.add(PlayerDao.toPlayer(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return players;
    }

    public List<Player> getTop(int limit) {
        List<Player> players = new ArrayList<>(limit);

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_LIMIT_SQL)) {
            statement.setInt(1, limit);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    players.add(PlayerDao.toPlayer(resultSet));
                }
            }
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
            statement.setLong(3, player.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Player player) {
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, player.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Player toPlayer(ResultSet rs) throws SQLException {
        Player player = new Player();
        player.setId(rs.getLong("id"));
        player.setName(rs.getString("name"));
        player.setScore(rs.getInt("score"));
        return player;
    }

    @Override
    public void dispose() {
        ConnectionHandler.closeConnection(this.connection);
    }
}
