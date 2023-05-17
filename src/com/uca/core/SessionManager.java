package com.uca.core;

import com.uca.dao.UserDAO;
import com.uca.entity.PokemonEntity;
import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;

import com.uca.exception.FailedLoginException;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;

import javax.management.InvalidAttributeValueException;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;


/**
 * The Class SessionManager is the core class for the session management.
 * It contains all the methods that are used to manipulate the session of a user.
 */
public class SessionManager {

    /**
     * Try to connect a user by reading the login and password from the request.
     * @param request the request containing the login and password.
     * @return true if the user is connected, false otherwise.
     * @throws FailedLoginException if the login failed.
     */
    public static boolean tryToConnect(Request request) throws FailedLoginException {
        String login = request.queryParams("login");
        String plainPWD = request.queryParams("password");
        UserEntity authUser = null;
        try {
            authUser = authenticate(login, plainPWD);
        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }

        if (authUser==null) {
            System.err.println("Throwing failed login");
            return false;
        } else {
            connect(request.session().raw(), authUser);
        }
        return true;

    }


    /**
     * Connect a user by setting the session attribute "user" to the user.
     * @param session the session to set the attribute to.
     * @param user  the user to set.
     */
    public static void connect(HttpSession session, UserEntity user) {

        session.setAttribute("user", user);
        session.setMaxInactiveInterval(30 * 60);
        LocalDate today = LocalDate.now();

        LocalDate lastLoginDate = user.getDateConnexion();

        //Let's handle the points number (used to pex every day)
        System.out.println(lastLoginDate +" Vs "+ today );
        if (!today.equals(lastLoginDate)) {
            System.out.println("This user haven't connected today, we give him 5 points, and a random pokemon");
            user.setPoints(PossessionEntity.nb_point);
            user.setDateConnexion(LocalDate.now());
            UserCore.update(user);
            PokemonEntity pkmn;
            try {
                pkmn = PokemonCore.getPokemon(new Random().nextLong(PokemonEntity.MAX_POKEMON_ID+1));
            } catch (InvalidAttributeValueException e) {
                throw new RuntimeException(e);
            }
            PossessionCore.addPossession(user,pkmn,0);
        }
    }

    /**
     * Get the connected user from the request.
     * @param request the request to get the user from.
     * @param response the response to send.
     * @return the connected user.
     * @throws IllegalAccessException if the user is not connected.
     */

    public static UserEntity getConnectedUser(Request request, Response response) throws IllegalAccessException {
        if (!isConnected(request, response)) {
            throw new IllegalAccessException("User is not connected, cannot retrieve user id ");
        }
        return request.session(false).attribute("user");
    }


    /**
     * Check if a user is connected.
     * @param request the request to check.
     * @param response the response to send.
     * @return true if the user is connected, false otherwise.
     */
    public static boolean isConnected(Request request, Response response) {
        return request.session(false) != null;
    }


    /**
     * Try to authenticate a user
     * @param login the login of the user we want to auth.
     * @param clearPwd his clear text password
     * @return The corresdponding user if can be auth, null otherwise
     */
    public static UserEntity authenticate(String login, String clearPwd) throws FailedLoginException, SQLException {
        UserEntity user = new UserEntity();
        user.setLogin(login);
        UserEntity dbUser = new UserDAO().getUserByPseudo(login);
        assert dbUser!=null;

        try{
        if (BCrypt.checkpw(clearPwd, dbUser.getPwdHash())) {
            System.out.println("can be authenticate");
            return dbUser;
        }}  catch(Exception e){
            throw new FailedLoginException("Authentication failed");

        };
        System.out.println("can not be authenticate");

        return null;
    }


    /**
     * Disconnect a user by invalidating his session.
     * @param session the session to invalidate.
     */

    private static void disconnect(HttpSession session) {
        session.invalidate();
    }


    /**
     * Disconnect a user by invalidating his session.
     * @param request the request to get the session from.
     * @param response the response to send.
     */
    public static void disconnect(@NotNull Request request, Response response) {
        disconnect(request.session(false).raw());
    }


}



