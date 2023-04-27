package com.uca.core;

import com.uca.dao.TradeDAO;
import com.uca.entity.PossessionEntity;
import com.uca.entity.TradeEntity;

import java.sql.SQLException;

public class TradeCore {

    public TradeEntity newTrade(PossessionEntity applicantPossession, PossessionEntity recipiantPossession){
        TradeEntity tradeEntity =new TradeEntity(applicantPossession,recipiantPossession);
        try {
            tradeEntity = new TradeDAO().create(tradeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return tradeEntity;
    };
}
