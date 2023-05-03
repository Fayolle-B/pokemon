package com.uca.core;

import com.uca.dao.PossessionDAO;
import com.uca.dao.TradeDAO;
import com.uca.entity.PossessionEntity;
import com.uca.entity.TradeEntity;
import com.uca.entity.TradeStatus;
import com.uca.entity.UserEntity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class TradeCore {

    public static TradeEntity newTrade(PossessionEntity applicantPossession, PossessionEntity recipientPossession) {
        TradeEntity tradeEntity = new TradeEntity(applicantPossession, recipientPossession);
        try {
            tradeEntity = new TradeDAO().create(tradeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return tradeEntity;
    }

    ;

    public static TradeEntity newTradeFromIDs(int id1, int id2) {
        PossessionEntity applicantPossession, recipientPossession;
        try {
            applicantPossession = PossessionCore.getPossessionById(id1);
            recipientPossession = PossessionCore.getPossessionById(id2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newTrade(applicantPossession, recipientPossession);
    }

    public static ArrayList<TradeEntity> getAllTradesOf(UserEntity userEntity) {
        return new TradeDAO().getAllTradesOf(userEntity);
    }

    public static ArrayList<TradeEntity> getTradesOfHavingStatus(UserEntity userEntity, TradeStatus status) {
        ArrayList<TradeEntity> newTradeEntities = new ArrayList<>();
        ArrayList<TradeEntity> tradeEntities = getAllTradesOf(userEntity);
        for (TradeEntity tr : tradeEntities) {
            if (tr.getStatus().equals(status)) {
                newTradeEntities.add(tr);
            }
        }
        return newTradeEntities;
    }


    public static TradeEntity acceptTrade(TradeEntity tradeEntity) {
        if (tradeEntity.getStatus() != TradeStatus.PENDING)
            throw new IllegalStateException("Canno't accept this trade, wronge status");
        System.out.println("accept trade #" + tradeEntity.getId());
        tradeEntity.setAcceptDate(LocalDate.now());
        tradeEntity.setStatus(TradeStatus.ACCEPTED);
        PossessionEntity appPoss = tradeEntity.getApplicantPossession();
        PossessionEntity recPoss = tradeEntity.getRecipientPossession();
        appPoss.setDatePerte(LocalDate.now());
        recPoss.setDatePerte(LocalDate.now());

        try {
            new TradeDAO().update(tradeEntity);
            new PossessionDAO().update(recPoss);
            new PossessionDAO().update(appPoss);
        } catch (SQLException e) {
            throw new RuntimeException("Can't update this trade in Database");
        }

        int us1possNumber = PossessionCore.possessionOf(tradeEntity.getApplicantPossession().getOwner()).size();
        int us2possNumber = PossessionCore.possessionOf(tradeEntity.getRecipientPossession().getOwner()).size();

        try {
            PossessionCore.addPossession(tradeEntity.getRecipientPossession().getOwner(), tradeEntity.getApplicantPossession().getPokemon(), tradeEntity.getApplicantPossession().getLevel());
            PossessionCore.addPossession(tradeEntity.getApplicantPossession().getOwner(), tradeEntity.getRecipientPossession().getPokemon(), tradeEntity.getRecipientPossession().getLevel());
        } catch (Exception e) {
            throw new RuntimeException("Error while running possession swapping");
        }
        ;
        //let's check if number of posssersion is still good
        //assert us1possNumber ==PossessionCore.possessionOf(tradeEntity.getApplicantPossession().getOwner()).size() && us2possNumber== PossessionCore.possessionOf(tradeEntity.getRecipientPossession().getOwner()).size();
        return tradeEntity;

    }

    public static TradeEntity getTradeById(int id) {
        try {
            return new TradeDAO().getTradeById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to get Trade with id " + id);
        }
    }
}


