package com.solvd.laba.delivery.dao;

import java.util.List;

public interface IGenericDAO<T, ID> {
    void create(T entity);
    T findById(ID id);
    void update(T entity);
    void delete(ID id);
    List<T> findAll();
}
