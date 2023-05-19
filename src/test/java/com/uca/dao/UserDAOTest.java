package com.uca.dao;

import com.uca.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserDAOTest extends AbstractDaoTest<UserDAO> {

    //TODO : a tester for each method of UserDAO (by using the abstract methods)

    UserDAO dao = new UserDAO();

    @Override
    protected void createTables() throws SQLException {
        PreparedStatement statement = null;
        statement = connection.prepareStatement(
                "CREATE TABLE users (" +
                        "id int primary key auto_increment, " +
                        "first_name VARCHAR(255), " +
                        "last_name VARCHAR(255), " +
                        "login VARCHAR(255), " +
                        "pwd_hash VARCHAR(255), " +
                        "email VARCHAR(255)" +
                        ")"
        );
        statement.execute();

    }

    @Override
    protected void insertData() throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO users (id, first_name, last_name, login, pwd_hash, email) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, 1);
            statement.setString(2, "John");
            statement.setString(3, "Doe");
            statement.setString(4, "johndoe");
            statement.setString(5, "$2a$10$d2345678901234567890.");
            statement.setString(6, "johndoe@example.com");
            statement.execute();
            statement.setInt(1, 2);
            statement.setString(2, "Jane");
            statement.setString(3, "Doe");
            statement.setString(4, "janedoe");
            statement.setString(5, "$2a$10$d2345678901234567890.");
            statement.setString(6, "janedoe@example.com");
            statement.execute();
        } finally {
            assert statement != null;
            statement.close();
        }
    }
    //@Test
    public void shouldReturnUserWithId1() throws SQLException {
        UserEntity user = dao.getUserById(1);
        assertEquals(user.getId(), 1);
        assertEquals(user.getFirstName(), "John");
        assertEquals(user.getLastName(), "Doe");
        assertEquals(user.getLogin(), "johndoe");
        assertEquals(user.getPwdHash(), "$2a$10$d2345678901234567890.");
        assertEquals(user.getEmail(), "anedoe@example.com");
    }

    //@Test
    public void shouldReturnUserWithId2() throws SQLException {
        UserEntity user = dao.getUserById(2);
        assertEquals(user.getId(), (2));
        assertEquals(user.getFirstName(), ("Jane"));
        assertEquals(user.getLastName(), ("Doe"));
        assertEquals(user.getLogin(), ("janedoe"));
        assertEquals(user.getPwdHash(), ("$2a$10$d2345678901234567890."));
        assertEquals(user.getEmail(), ("janedoe@example.com"));


    }

    //@Test
    public void shouldReturnNullWhenUserWithIdDoesNotExist() throws SQLException {
        UserEntity user = dao.getUserById(3);
        assertNull(user);
    }

}
