package com.uca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Provides a singleton instance of a database connection to an H2 database.
 */
public class _Connector {

    /**
     * The database URL string.
     */
    private static String url = "jdbc:h2:~/test"; // in the user home directory

    /**
     * The database user name.
     */
    private static String user = "sa";

    /**
     * The database password.
     */
    private static String passwd = "";

    /**
     * The singleton instance of the database connection.
     */
    private static Connection connect;

    /**
     * Returns a singleton instance of the database connection. If the connection has not been
     * established, it will be created.
     *
     * @return the singleton instance of the database connection
     */
    public static Connection getInstance(){
        if(connect == null){
            try {
                connect = DriverManager.getConnection(url, user, passwd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connect;
    }
}