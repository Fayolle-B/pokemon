package com.uca.core;

import com.uca.dao.UserDAO;
import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;

import com.uca.exception.FailedLoginException;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;


public class SessionManager {
//TODO

    //TODO : move TryToConnect in another place,
    public static boolean tryToConnect(Request request, Response response) throws FailedLoginException {
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

    public static void connect(HttpSession session, UserEntity user) {

        session.setAttribute("user", user);
        session.setMaxInactiveInterval(30 * 60);
        LocalDate today = LocalDate.now();

        LocalDate lastLoginDate = user.getDateConnexion();

        //Let's handle the points number (used to pex every day)
        if (!today.equals(lastLoginDate)) {
            System.out.println("This user haven't connected today, we give him 5 points");
            user.setPoints(PossessionEntity.nb_point);
            user.setDateConnexion(LocalDate.now());
            UserCore.update(user);
        }
    }

    public static UserEntity getConnectedUser(Request request, Response response) throws IllegalAccessException {
        if (!isConnected(request, response)) {
            throw new IllegalAccessException("User is not connected, cannot retrieve user id ");
        }
        return request.session(false).attribute("user");
    }

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

    public static void disconnect(HttpSession session) {
        session.invalidate();
    }


    public static void disconnect(@NotNull Request request, Response response) {
        disconnect(request.session(false).raw());
    }


}



