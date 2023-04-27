package com.uca.core;


import com.uca.dao.UserDAO;
import com.uca.entity.PokemonEntity;
import com.uca.entity.UserEntity;
import com.uca.entity.PossessionEntity;

import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.Date;
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
        return new UserDAO().getAllUsers();
    }

    /**
     Creates a new user entity in the database.
     @param firstname the firstname of the new User
     @param lastname the lastname of the new user
     @return the newly created UserEntity object
     */
    public static UserEntity newUser(String firstname, String lastname, String login, String pwd, String  email){
        UserEntity newUser = new UserEntity();
        PokemonEntity pkmn;
        System.out.println(firstname + "+ "+ lastname);

        newUser.setFirstName(firstname);
        newUser.setLastName(lastname);
        long millis=System.currentTimeMillis();
        newUser.setDateConnexion(new Date());
        newUser.setLogin(login);
        newUser.setPwd(pwd);
        newUser.setEmail(email);
        newUser.setPoints(PossessionEntity.nb_point);
        newUser = new UserDAO().create(newUser);

        try {
            pkmn = PokemonCore.getPokemon(new Random().nextLong(PokemonEntity.MAX_POKEMON_ID+1));
        } catch (InvalidAttributeValueException e) {
            throw new RuntimeException(e);
        }
        PossessionCore.addOwnership(newUser,pkmn);
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