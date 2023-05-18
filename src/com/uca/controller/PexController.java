package com.uca.controller;

import com.uca.core.PossessionCore;
import com.uca.core.SessionManager;
import com.uca.core.UserCore;
import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;
import com.uca.exception.IllegalRouteException;
import com.uca.exception.NeedToConnectException;
import com.uca.exception.NotFoundException;

import static spark.Spark.post;
//documentation for this file

/**
 * This class is the controller for the pex route
 *
 * @see com.uca.controller
 */


public class PexController {
    /**
     * This method is the controller for the pex route
     *
     * @see com.uca.controller
     * Routes are :
     * /pex : add a pex to a possession
     * /pex/:id : add a pex to a possession
     */
    public static void pexRoute() {
        //before("/pex", profileController.isConnectedFilter);
        post("/pex", ((request, response) -> {
            int possID = 0;

            try {
                possID = Integer.parseInt(request.queryParams("possessionID"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new IllegalRouteException("Cannot find and/or parse possessionID in request");
            } catch (NullPointerException f) {
                System.err.println("Errur de format");
                throw new RuntimeException();

            }
            PossessionEntity possessionEntity = null;
            try {

                possessionEntity = PossessionCore.getPossessionById(possID);
            } catch (Exception e) {
                throw new NotFoundException("Cannot find possession with id " + possID);
            }
            if (!SessionManager.isConnected(request, response)) {
                throw new NeedToConnectException("You need to be connected to pex a possession");
            } else {

                UserEntity userEntity = SessionManager.getConnectedUser(request, response);
                if (userEntity.getPoints() > 0) {
                    System.err.println("Il reste  " + userEntity.getPoints());
                    userEntity.setPoints(userEntity.getPoints() - 1);
                    UserCore.update(userEntity);
                    PossessionCore.pexPossession(possessionEntity);
                    PossessionCore.update(possessionEntity);
                }
                response.redirect("/profile/" + possessionEntity.getOwner().getId());
                return null;
            }
        }));
    }
}
