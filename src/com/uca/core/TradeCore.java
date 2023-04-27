package com.uca.core;

import com.uca.entity.PossessionEntity;
import com.uca.entity.TradeEntity;

public class TradeCore {

    public TradeEntity newTrade(PossessionEntity applicantOwnership, PossessionEntity recipiantOwnership){
        TradeEntity tradeEntity =new TradeEntity(applicantOwnership,recipiantOwnership);



        return tradeEntity;
    };
}
