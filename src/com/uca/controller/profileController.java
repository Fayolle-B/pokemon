package com.uca.controller;

import com.uca.core.SessionManager;
import com.uca.core.UserCore;
import com.uca.gui.UserGUI;
import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class profileController {
    public static void profileRoute() {

        Filter concernedUserFilter = new Filter() {
            @Override
            public void handle(Request request, Response response) throws Exception {
                if (!(SessionManager.getConnectedUser(request, response).getId() == Integer.parseInt(request.params(":id")))) {
                    halt(401, "Unauthorized");
                }

            }
        };

        Filter isConnectedFilter = new Filter() {
            @Override
            public void handle(Request request, Response response) throws Exception {
                if (request.session(false) == null) {
                    //response.redirect("/",401);
                    response.redirect("/");
                }

            }
        };

        before("/profile/*", isConnectedFilter);

        get("/profile/:id/trades", (request, response) -> {
            if (SessionManager.getConnectedUser(request, response).equals(UserCore.getUserFromId(Integer.parseInt(request.params(":id"))))) {
                return UserGUI.displayProfileTrade(UserCore.getUserFromId(Integer.parseInt(request.params(":id"))));
            }
            response.redirect("/profile/"+request.params(":id"));
            return null;


        });
        get("/profile/:id", (request, response) -> {
            if (SessionManager.getConnectedUser(request, response).equals(UserCore.getUserFromId(Integer.parseInt(request.params(":id"))))) {

                return UserGUI.displayMyProfile(SessionManager.getConnectedUser(request, response).getId());
            }
            return UserGUI.displayOtherProfile(Integer.parseInt(request.params(":id")));
        });
    }
}
