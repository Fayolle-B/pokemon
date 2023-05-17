package com.uca.controller;

import com.uca.core.SessionManager;
import com.uca.core.UserCore;
import com.uca.entity.UserEntity;
import com.uca.exception.FailedLoginException;
import com.uca.exception.NeedToConnectException;
import com.uca.gui.UserGUI;
import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class profileController {
    public static Filter  concernedUserFilter = new Filter() {
        @Override
        public void handle(Request request, Response response) throws Exception {
            if (!(SessionManager.getConnectedUser(request, response).getId() == Integer.parseInt(request.params(":id")))) {
                //halt(401, "Unauthorized");
                response.status(403);
                response.redirect("/");
            }

        }
    };

    public static Filter isConnectedFilter = (request, response) -> {
        if (request.session(false) == null) {

            throw new NeedToConnectException();
        }

    };

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
