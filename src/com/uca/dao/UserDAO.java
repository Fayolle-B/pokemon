package com.uca.dao;

import com.uca.entity.UserEntity;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO extends _Generic<UserEntity> {


    private UserEntity extractFromResultSet(UserEntity userEntity, ResultSet resultSet) throws SQLException {
        userEntity.setId(resultSet.getInt("ID"));
        userEntity.setFirstName(resultSet.getString("firstname"));
        userEntity.setLastName(resultSet.getString("lastname"));
        userEntity.setEmail(resultSet.getString("email"));
        userEntity.setPoints(resultSet.getInt("points"));
        userEntity.setPwdHash(resultSet.getString("pwd"));
        userEntity.setLogin(resultSet.getString("login"));
        return userEntity;

    }

    public ArrayList<UserEntity> getAllUsers() throws SQLException {
        ArrayList<UserEntity> entities = new ArrayList<>();
        PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM users ORDER BY id;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            UserEntity entity = new UserEntity();
            entity.setFirstName(resultSet.getString("FirstName"));
            entity.setLastName(resultSet.getString("LastName"));
            entity.setId(resultSet.getInt("id"));
            entity.setLogin(resultSet.getString("login"));
            entity.setPwdHash(resultSet.getString("pwd"));
            entity.setEmail(resultSet.getString("email"));
            entity.setPoints(resultSet.getInt("points"));
            entity.setDateConnexion(resultSet.getDate("DateConnexion").toLocalDate());

            entities.add(entity);
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
        statement.setString(4, user.getPwdHash());
        statement.setString(5, user.getEmail());
        statement.setInt(6, user.getPoints());
        statement.setDate(7, java.sql.Date.valueOf(user.getDateConnexion()));
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
        PreparedStatement preparedStatement = this.connect.prepareStatement("UPDATE users SET FIRSTNAME = ?, LastName = ?, pwd = ?, login =?, email=? ,POINTS=?, DATECONNEXION=? WHERE id = ?");

        preparedStatement.setString(1, userEntity.getFirstName());
        preparedStatement.setString(2, userEntity.getLastName());
        preparedStatement.setString(3, userEntity.getPwdHash());
        preparedStatement.setString(4, userEntity.getLogin());
        preparedStatement.setString(5, userEntity.getEmail());
        preparedStatement.setInt(6, userEntity.getPoints());
        preparedStatement.setDate(7, Date.valueOf(userEntity.getDateConnexion()));
        preparedStatement.setInt(8, userEntity.getId());
        preparedStatement.executeUpdate();
    }


    /**
     * This method retrieve the user corresponding to the given id, null if user don't exist
     * @param id int : the id we want to test
     * @return the user, null if user with this id don't exist
     */

    public UserEntity getUserById(int id) {
        UserEntity userEntity = new UserEntity();

        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT ID,FIRSTNAME, LASTNAME, LOGIN, PWD, POINTS, EMAIL, DATECONNEXION, SALT from users where id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            assert resultSet!=null;
            resultSet.next();
            userEntity=extractFromResultSet(userEntity, resultSet);
            userEntity.setDateConnexion(resultSet.getDate("DateConnexion").toLocalDate());


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return userEntity;

    }

    /**
     * Retrieve users from the email, null otherwise
     * @param email we want to test
     * @return
     */
    public ArrayList<UserEntity> getUsersByEmail(String email) {
        UserEntity userEntity = new UserEntity();
        ResultSet resultSet;
        PreparedStatement preparedStatement = null;
        ArrayList<UserEntity> userEntities=new ArrayList<>();
        try {
            preparedStatement = this.connect.prepareStatement("SELECT ID, FIRSTNAME,LASTNAME,LOGIN,PWD,POINTS,EMAIL,DATECONNEXION, SALT from USERS where email = ? ");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isLast()) {
                //empty resultSet
                return null;
            }
            else {
                while (resultSet.next()){
                    userEntities.add(extractFromResultSet(userEntity,resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot query fromm email");
        }
        if(userEntities.isEmpty())return  null;
        else return userEntities;
    }
    public UserEntity getUserByPseudo(String pseudo){
        UserEntity userEntity=new UserEntity();
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        try {
            preparedStatement = this.connect.prepareStatement("SELECT FIRSTNAME, LASTNAME, ID, LOGIN, PWD, POINTS, EMAIL, DATECONNEXION, SALT from USERS where LOGIN = ?");
            preparedStatement.setString(1,pseudo);
            resultSet  = preparedStatement.executeQuery();
            if(resultSet.isLast())return  null;
            if(resultSet.next()){
                assert resultSet.isLast();
                userEntity=extractFromResultSet(userEntity, resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  userEntity;
    }
}


