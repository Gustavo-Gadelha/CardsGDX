package com.cardsgdx.game.dao;

import com.cardsgdx.game.Player;

import java.util.List;

public interface IPlayerDao extends IDao<Player> {
    Player getByName(String name);

    List<Player> getTop(int limit);
}
