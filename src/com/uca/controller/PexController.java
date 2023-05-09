package com.uca.controller;

import com.uca.core.PossessionCore;
import com.uca.core.SessionManager;
import com.uca.core.UserCore;
import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;
import spark.Request;
import spark.Response;

import static spark.route.HttpMethod.before;
import static spark.route.HttpMethod.post;

import spark.Filter;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

import static spark.Spark.*;

public class PexController {
    public static void pexRoute(){
        //before("/pex", profileController.isConnectedFilter);
        post("/pex", ((request, response) -> {
            int possID = 0;

            try{
                possID = Integer.parseInt(request.queryParams("possessionID"));
            }catch (NumberFormatException e){
                e.printStackTrace();
            }catch (NullPointerException f ){
                System.err.println("Errur de format");
                throw  new RuntimeException();

            }


            PossessionEntity possessionEntity = PossessionCore.getPossessionById(possID);
            UserEntity userEntity = SessionManager.getConnectedUser(request, response);
            if(userEntity.getPoints()>0) {
                System.err.println("Il reste  " + userEntity.getPoints());
                userEntity.setPoints(userEntity.getPoints() - 1);
                UserCore.update(userEntity);
                PossessionCore.pexPossession(possessionEntity);
                PossessionCore.update(possessionEntity);
            };
            response.redirect("/profile/"+possessionEntity.getOwner().getId());
            return null;
        }));
    }
}
