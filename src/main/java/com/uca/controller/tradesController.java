package java.com.uca.controller;

import java.com.uca.core.PossessionCore;
import java.com.uca.core.SessionManager;
import java.com.uca.core.TradeCore;
import java.com.uca.entity.PossessionEntity;
import java.com.uca.entity.TradeEntity;
import java.com.uca.entity.UserEntity;
import java.com.uca.exception.IllegalRouteException;
import java.com.uca.gui.UserGUI;

import static spark.Spark.halt;
import static spark.Spark.post;


/**
 * This class is the controller for the trades route
 * @see java.com.uca.controller
    */
public class tradesController {



    /**
     * This method is the controller for the trades route
     * @see java.com.uca.controller
     *
     * The routes are :
     * /trade : create a new trade
     * /profile/trades/accept : accept a trade
     *
     */
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
