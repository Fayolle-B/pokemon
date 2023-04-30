package com.uca.controller;

import com.uca.core.PossessionCore;
import com.uca.core.SessionManager;
import com.uca.core.TradeCore;
import com.uca.entity.PossessionEntity;
import com.uca.entity.TradeEntity;
import com.uca.entity.UserEntity;
import com.uca.exception.IllegalRouteException;

import static spark.Spark.halt;
import static spark.Spark.post;

public class tradesController {

    public static void tradesRoutes(){
        post("/trade",((request, response) -> {
            System.out.println("on veut créer un trade");
            PossessionEntity poss1;
            PossessionEntity poss2;
            int id1, id2;
            try{
                id1=Integer.parseInt(request.queryParams("applicantPossessionID"));
                id2=Integer.parseInt(request.queryParams("recipientPossessionID"));

            }catch (NumberFormatException e) {
                throw  new IllegalRouteException("Cannot use [ "+ request.params(":id1")+ " ] and [ "+request.params(":id2")+" ] as possessions id to setup a trade");
            }

            try{
                poss1= PossessionCore.getPossessionById(id1);
                poss2= PossessionCore.getPossessionById(id2);

            }catch (Exception e){
                throw new IllegalArgumentException("Can't get possessions from id1="+id1+" and id2="+id2);
            }
            if(SessionManager.isConnected(request, response)&& (SessionManager.getConnectedUser(request,response).equals(poss1.getOwner())||SessionManager.getConnectedUser(request,response)==poss2.getOwner())) {
                //coneected user is a part of the trade
                TradeCore.newTradeFromIDs(Integer.parseInt(request.params(":id1")), Integer.parseInt(request.params(":id2")));
            }else {
                System.out.println("Not a part, abort");
                System.out.println(SessionManager.isConnected(request,response));
                System.out.println(SessionManager.getConnectedUser(request,response).getId());
                System.out.println(poss1.getOwner().getId());

            }
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
