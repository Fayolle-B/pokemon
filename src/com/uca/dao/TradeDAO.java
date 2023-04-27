package com.uca.dao;

import com.uca.entity.TradeEntity;

import java.sql.PreparedStatement;

public class TradeDAO extends _Generic<TradeEntity> {

    @Override
    public TradeEntity create(TradeEntity obj) {
        //TODO: le faire quoi
        PreparedStatement statement = this.connect.prepareStatement("INSERT INTO ")
        return null;
    }

    @Override
    public void delete(TradeEntity obj) {

    }
}
