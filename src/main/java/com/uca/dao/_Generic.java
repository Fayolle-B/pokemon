package com.uca.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class _Generic<T> {

    public Connection connect = _Connector.getInstance();

    /**
     * Create an entry in the database
     * @param obj the object to create
     */
    public abstract T create(T obj) throws SQLException;

    /**
     * delete an entry in the database
     * @param obj the object to delete
     */
    public abstract void delete(T obj);

}
