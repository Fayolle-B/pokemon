package com.uca.controller;

import spark.Filter;

import com.uca.core.SessionManager;
import com.uca.core.UserCore;
import com.uca.entity.UserEntity;
import com.uca.exception.NeedToConnectException;
import com.uca.gui.UserGUI;

import static spark.Spark.before;
import static spark.Spark.get;

/**
 * This class is the controller for the profile route
 * @see com.uca.controller
 */
public class profileController {

    /**
     * This filter is used to check if the user is connected
     */
    public static Filter isConnectedFilter = (request, response) -> {
        if (request.session(false) == null) {

            throw new NeedToConnectException();
        }

    };


    /**
     * This method is the controller for the profile route
     * @see com.uca.controller
     *
     * The routes are :
     * /profile/:id : display the profile of the user with the id :id
     * /profile/:id/trades : display the trades of the user with the id :id
     *
     */
    public static void profileRoute() {

        before("/profile/*", isConnectedFilter);

        get("/profile/:id/trades", (request, response) -> {
            if (SessionManager.getConnectedUser(request, response).equals(UserCore.getUserByID(Integer.parseInt(request.params(":id"))))) {
                return UserGUI.displayProfileTrade(UserCore.getUserByID(Integer.parseInt(request.params(":id"))));
            }
            response.redirect("/profile/"+request.params(":id"));
            return null;


        });
        get("/profile/:id", (request, response) -> {
            UserEntity user = SessionManager.getConnectedUser(request,response);
            if (user.getId()==(Integer.parseInt(request.params(":id")))) {

                return UserGUI.displayMyProfile(user.getId());
            }
            UserEntity connectedUser = SessionManager.getConnectedUser(request, response);
            System.out.println("Connected as user #"+connectedUser.getId());
            return UserGUI.displayOtherProfile(Integer.parseInt(request.params(":id")), connectedUser);
        });
    }
}
