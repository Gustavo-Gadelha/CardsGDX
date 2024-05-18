package com.cardsgdx.game.dao;

import java.util.List;

public interface IDao<T> {
    void insert(T t);

    T get(int id);

    List<T> getAll();

    void update(T t);

    void delete(T t);
}
