package com.uca.core;


import com.uca.dao.UserDAO;
import com.uca.entity.PokemonEntity;
import com.uca.entity.UserEntity;
import com.uca.entity.PossessionEntity;
import com.uca.exception.BadEmailException;

import javax.management.InvalidAttributeValueException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;


/**

 Provides methods to interact with UserDAO and perform Create/Read/Update/Delete operations w/ userEntity
 */
public class UserCore {

    /**
     Returns a list of all user entities in the database.
     @return an ArrayList of UserEntity objects
     */
    public static ArrayList<UserEntity> getAllUsers() {
        try {
            return new UserDAO().getAllUsers();
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     Creates a new user entity in the database.
     @param firstname the firstname of the new User
     @param lastname the lastname of the new user
     @return the newly created UserEntity object
     */
    public static UserEntity newUser(String firstname, String lastname, String login, String pwdHash, String  email) throws SQLException {
        UserEntity newUser = new UserEntity();
        PokemonEntity pkmn;
        System.out.println(firstname + "+ "+ lastname);

        newUser.setFirstName(firstname);
        newUser.setLastName(lastname);
        newUser.setDateConnexion(LocalDate.now());
        newUser.setLogin(login);
        newUser.setPwdHash(pwdHash);
        newUser.setEmail(email);
        newUser.setPoints(PossessionEntity.nb_point);
        try {
            newUser = new UserDAO().create(newUser);
        }catch (SQLIntegrityConstraintViolationException integrityConstraintViolationException){
            throw new BadEmailException("Email already exist");
        }
        try {
            pkmn = PokemonCore.getPokemon(new Random().nextLong(PokemonEntity.MAX_POKEMON_ID+1));
        } catch (InvalidAttributeValueException e) {
            throw new RuntimeException(e);
        }
        PossessionCore.addPossession(newUser,pkmn,0);
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
     * Updates a user entity in the database.
     *
     * @param userEntity the UserEntity object to be updated
     * @return the updated user entity
     */
    public static UserEntity update(UserEntity userEntity) {
        try {
            new UserDAO().update(userEntity);
            System.err.println("Les points de cette entité : " + userEntity.getPoints());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return userEntity;
    }



    //Added
    public static UserEntity getUserByID(int id){
        return new UserDAO().getUserById(id);
    }
}