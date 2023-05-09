package com.uca.controller;

import com.uca.core.PossessionCore;
import com.uca.core.SessionManager;
import com.uca.core.TradeCore;
import com.uca.entity.PossessionEntity;
import com.uca.entity.TradeEntity;
import com.uca.entity.UserEntity;
import com.uca.exception.IllegalRouteException;
import com.uca.gui.UserGUI;

import static spark.Spark.halt;
import static spark.Spark.post;

public class tradesController {

    public static void tradesRoutes(){
        post("/trade",((request, response) -> {
            if(request.queryParams("applicantPossessionID")==null){
                System.err.println("There is not applicantPossessionID, so we display possessionPicker"+ request.queryParams());
                return UserGUI.possessionPicker(SessionManager.getConnectedUser(request,response), Integer.parseInt(request.queryParams("recipientPossessionID")));
            }
            PossessionEntity poss1;
            PossessionEntity poss2;
            int id1, id2;
            System.err.println("There is ");

            try{
                id1=Integer.parseInt(request.queryParams("applicantPossessionID"));
                id2=Integer.parseInt(request.queryParams("recipientPossessionID"));

            }catch (NumberFormatException e) {
                throw  new IllegalRouteException("Cannot use [ "+request.queryParams("recipientPossessionID")+ " ] and [ "+request.queryParams("applicantPossessionID")+" ] as possessions id to setup a trade");
            }

            try{
                poss1= PossessionCore.getPossessionById(id1);
                poss2= PossessionCore.getPossessionById(id2);

            }catch (Exception e){
                throw new IllegalArgumentException("Can't get possessions from id1="+id1+" and id2="+id2);
            }
            if(SessionManager.isConnected(request, response)&& (SessionManager.getConnectedUser(request,response).equals(poss1.getOwner())||SessionManager.getConnectedUser(request,response)==poss2.getOwner())) {
                //coneected user is a part of the trade
                TradeCore.newTradeFromIDs(id1,id2);
            }else {
                System.out.println("Not a part, abort");


            }
            response.redirect("/");
            return null;
        } ));



        post("/profile/trades/accept", ((request, response) -> {
            int tradeId = Integer.parseInt(request.queryParams("tradeID"));
            TradeEntity trade = TradeCore.getTradeById(tradeId);
            UserEntity applicant = trade.getApplicantPossession().getOwner();
            UserEntity recipiant = trade.getRecipientPossession().getOwner();
            if(SessionManager.getConnectedUser(request, response).equals(recipiant)){
                System.out.println("Trying to accept the trade #"+tradeId);
                TradeCore.acceptTrade(trade);
                response.redirect("/");
            }
            else {
                halt(401,"Not connected as recipiant, can't accept this trade");
            }



            return null;
        } ));




    }
}
