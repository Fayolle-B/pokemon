package com.uca.dao;

import com.uca.entity.UserEntity;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO extends _Generic<UserEntity> {

    public ArrayList<UserEntity> getAllUsers() throws SQLException {
        ArrayList<UserEntity> entities = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM users ORDER BY id ASC;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserEntity entity = new UserEntity();
                entity.setFirstName(resultSet.getString("FirstName"));
                entity.setLastName(resultSet.getString("LastName"));
                entity.setId(resultSet.getInt("id"));
                entity.setLogin(resultSet.getString("login"));
                entity.setPwd(resultSet.getString("pwd"));
                entity.setEmail(resultSet.getString("email"));
                entity.setPoints(resultSet.getInt("points"));
                entity.setDateConnexion(resultSet.getDate("DateConnexion"));

                entities.add(entity);
            }
        } catch (SQLException e) {
            throw e;
        }

        return entities;
    }

    @Override
    /* ----------------------------------------- Ajouter ou creer un un user------------------------------------------------------------------------------------------------------------  */
    public UserEntity create(UserEntity user) throws SQLException {
        PreparedStatement statement = this.connect.prepareStatement("INSERT INTO users (FirstName,LastName, login, pwd ,email,points, DateConnexion) VALUES(?, ?,?,?,?,?,?);", PreparedStatement.RETURN_GENERATED_KEYS);

        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getLogin());
        statement.setString(4, user.getPwd());
        statement.setString(5, user.getEmail());
        statement.setInt(6, user.getPoints());
        statement.setDate(7, new java.sql.Date(user.getDateConnexion().getTime()));
        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            user.setId(rs.getInt(1));
        }
        return user;
    }

    @Override
    public void delete(UserEntity obj) {
        PreparedStatement statement = null;
        try {
            statement = this.connect.prepareStatement("DELETE  FROM users where id=?");
            statement.setInt(1, obj.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Update the line row of the given user (with the given id)
     *
     * @param userEntity the "new" user (updated ver. of the user
     */
    public void update(UserEntity userEntity) throws SQLException {
        PreparedStatement preparedStatement = this.connect.prepareStatement("UPDATE users SET FIRSTNAME = ?, LastName = ?, pwd = ?, login =?, email=? WHERE id = ?");

        preparedStatement.setString(1, userEntity.getFirstName());
        preparedStatement.setString(2, userEntity.getLastName());
        preparedStatement.setString(3, userEntity.getPwd());
        preparedStatement.setString(4, userEntity.getLogin());
        preparedStatement.setString(5, userEntity.getEmail());
        preparedStatement.setInt(6, userEntity.getId());
        preparedStatement.executeUpdate();
    }


    public UserEntity getUserById(int id) {
        UserEntity userEntity = new UserEntity();

        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * from users where id=?");
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isLast())
                throw new Exception("Can't do a request on this id\nResult of select query is" + resultSet.toString());
            System.out.println(resultSet.toString());
            resultSet.next();
            userEntity.setId(id);
            userEntity.setFirstName(resultSet.getString("firstname"));
            userEntity.setLastName(resultSet.getString("lastname"));
            userEntity.setEmail(resultSet.getString("email"));
            userEntity.setPoints(resultSet.getInt("points"));
            userEntity.setPwd(resultSet.getString("pwd"));
            userEntity.setLogin(resultSet.getString("login"));
            userEntity.setDateConnexion(new Date(resultSet.getDate("DateConnexion").getTime()));


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return userEntity;

    }

    public UserEntity getUserByEmail(String email) {
        UserEntity userEntity = new UserEntity();
        ResultSet resultSet;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connect.prepareStatement("SELECT ID, FIRSTNAME,LASTNAME,LOGIN,PWD,POINTS,EMAIL,DATECONNEXION from USERS where email = ? ");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                //empty resultSet
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot query fromm email");
        }
        try {
            userEntity.setId(resultSet.getInt("id"));
            userEntity.setFirstName(resultSet.getString("firstname"));
            userEntity.setLastName(resultSet.getString("lastname"));
            userEntity.setEmail(resultSet.getString("email"));
            userEntity.setPoints(resultSet.getInt("points"));
            userEntity.setPwd(resultSet.getString("pwd"));
            userEntity.setLogin(resultSet.getString("login"));
            userEntity.setDateConnexion(new Date(resultSet.getDate("DateConnexion").getTime()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userEntity;


        //TODO:

    }
}


