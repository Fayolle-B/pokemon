package com.uca.core;


import com.uca.dao.UserDAO;
import com.uca.entity.UserEntity;
import com.uca.entity.PossessionEntity;

import java.sql.Date;
import java.util.ArrayList;


/**

 Provides methods to interact with UserDAO and perform Create/Read/Update/Delete operations w/ userEntity
 */
public class UserCore {

    /**
     Returns a list of all user entities in the database.
     @return an ArrayList of UserEntity objects
     */
    public static ArrayList<UserEntity> getAllUsers() {
        return new UserDAO().getAllUsers();
    }

    /**
     Creates a new user entity in the database.
     @param firstname the firstname of the new User
     @param lastname the lastname of the new user
     @return the newly created UserEntity object
     */
    public static UserEntity create(String firstname, String lastname, String login, String pwd, String  email){
        UserEntity newUser = new UserEntity();
        System.out.println(firstname + "+ "+ lastname);

        newUser.setFirstName(firstname);
        newUser.setLastName(lastname);
        long millis=System.currentTimeMillis();
        newUser.setDateConnexion(new Date(millis));
        newUser.setLogin(login);
        newUser.setPwd(pwd);
        newUser.setEmail(email);
        newUser.setPoints(PossessionEntity.nb_point);
        newUser = new UserDAO().create(newUser);
        PossessionCore.create(newUser,2);
        return newUser;

    }

    /**
     Deletes a user entity from the database.
     @param userEntity the UserEntity object to be deleted
     */
    public static void delete(UserEntity userEntity) {
        new UserDAO().delete(userEntity);
    }

    /**
     Updates a user entity in the database.
     @param userEntity the UserEntity object to be updated
     */
    public static void update(UserEntity userEntity) {
        new UserDAO().update(userEntity);
    }



    //Added
    public static UserEntity getUserFromId(int id){
        return new UserDAO().getUserById(id);
    }

    public static boolean canBeLoggedIn(UserEntity user){
        boolean aBoolean =user.equals(new UserDAO().getUserByEmail(user.getEmail()));
        System.out.println("can be logged in  : "+ aBoolean);
        return (aBoolean);
    }

}