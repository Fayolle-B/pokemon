package com.uca.core;

import com.uca.dao.TradeDAO;
import com.uca.entity.PossessionEntity;
import com.uca.entity.TradeEntity;
import com.uca.entity.TradeStatus;
import com.uca.entity.UserEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class TradeCore {

    public static TradeEntity newTrade(PossessionEntity applicantPossession, PossessionEntity recipientPossession){
        TradeEntity tradeEntity =new TradeEntity(applicantPossession,recipientPossession);
        try {
            tradeEntity = new TradeDAO().create(tradeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return tradeEntity;
    };
    public static TradeEntity newTradeFromIDs(int id1, int id2){
        PossessionEntity applicantPossession, recipientPossession;
        try{
            applicantPossession=PossessionCore.getPossessionById(id1);
            recipientPossession=PossessionCore.getPossessionById(id2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newTrade(applicantPossession, recipientPossession);
    }

    public static ArrayList<TradeEntity> getAllTradesOf(UserEntity userEntity){
        return new TradeDAO().getAllTradesOf(userEntity);
    }

    public static TradeEntity acceptTrade(TradeEntity tradeEntity) {
        PossessionEntity poss1 = tradeEntity.getApplicantPossession();
        PossessionEntity poss2 = tradeEntity.getRecipientPossession();
        TradeEntity newTradeEntity = new TradeEntity(tradeEntity.getApplicantPossession(),tradeEntity.getRecipientPossession());
        newTradeEntity.setSubmitDate(tradeEntity.getSubmitDate());
        newTradeEntity.setId(tradeEntity.getId());
        newTradeEntity.setAcceptDate(new Date());
        newTradeEntity.setStatus(TradeStatus.ACCEPTED);
        try {
            new TradeDAO().update(newTradeEntity);
        } catch (SQLException e) {
            throw new RuntimeException("Can't update this trade in Database");
        }
        return newTradeEntity;
    }
}
