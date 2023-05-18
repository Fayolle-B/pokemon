package java.com.uca.controller;

import java.com.uca.Pages;
import java.com.uca.core.PossessionCore;
import java.com.uca.core.SessionManager;
import java.com.uca.core.UserCore;
import java.com.uca.entity.PossessionEntity;
import java.com.uca.entity.UserEntity;
import java.com.uca.exception.IllegalRouteException;
import java.com.uca.exception.NeedToConnectException;
import java.com.uca.exception.NotFoundException;

import static spark.Spark.post;
import static spark.Spark.get;
//documentation for this file

/**
 * This class is the controller for the pex route
 *
 * @see java.com.uca.controller
 */


public class PexController {
    /**
     * This method is the controller for the pex route
     *
     * @see java.com.uca.controller
     * Routes are :
     * /pex : add a pex to a possession
     * /pex/:id : add a pex to a possession
     */
    public static void pexRoute() {
        get( "/pex",( (req, res) -> {
            res.redirect(Pages.LANDINDPAGE.getUri());
            return null;
        }));
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
                response.status(201);

                // Redirect the user to the profile page
                response.redirect("/profile/" + userEntity.getId());
                return null;
            }
        }));


    }
}
