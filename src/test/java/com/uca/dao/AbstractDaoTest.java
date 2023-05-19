package com.uca.dao;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractDaoTest<DAO extends _Generic> {

    protected Connection connection;

    protected abstract void createTables() throws SQLException;

    @Mock
    protected  DAO dao;

    //TODO: method to write to the test database, not in the real one.

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:request_no;");
        createTables();
        insertData();
        //intercept the dao.create method to
    }

    @After
    public void tearDown() throws SQLException {
        destroyDatabase();
    }

    protected abstract void insertData() throws SQLException;


    private void destroyDatabase() throws SQLException {
        try {
            execute(connection, "DROP TABLE foo_tbl");
        } finally {
            connection.close();
        }
    }

    private void execute(Connection connection, String sql) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.execute();
        } finally {
            connection.close();
        }
    }
}
