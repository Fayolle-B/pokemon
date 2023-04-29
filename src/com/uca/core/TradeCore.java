package com.uca.core;

import com.uca.dao.TradeDAO;
import com.uca.entity.PossessionEntity;
import com.uca.entity.TradeEntity;
import com.uca.entity.UserEntity;

import java.sql.SQLException;
import java.util.ArrayList;

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
}
