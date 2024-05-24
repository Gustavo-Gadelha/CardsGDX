package com.cardsgdx.game.dao;

import java.util.List;

public interface IDao<T> {
    T insert(T t);

    T get(long id);

    List<T> getAll();

    void update(T t);

    void delete(T t);
}
