package com.uca.core;

import com.uca.dao.UserDAO;
import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;

import spark.Request;
import spark.Response;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class SessionManager {
//TODO

    //TODO : move TryToConnect in another place,
    public static boolean tryToConnect(Request request, Response response) {
        UserEntity user = new UserEntity();
        user.setPwd(request.queryParams("password"));
        user.setEmail(request.queryParams("email"));

        if (UserCore.canBeLoggedIn(user)) {
            user = (new UserDAO().getUserByEmail(user.getEmail()));
            HttpSession session = request.session().raw();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60);

            LocalDate today = LocalDate.now();

            LocalDate lastLoginDate = user.getDateConnexion();

            //Let's handle the points number (used to pex every day)
            if  (!today.equals(lastLoginDate)){
                System.out.println("This user haven't connected today, we give him 5 points");
                user.setPoints(PossessionEntity.nb_point);
                user.setDateConnexion(LocalDate.now());
                UserCore.update(user);
            }
            return true;
        } else {
            System.out.println("User or password incorrect");
            return false;
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


}



