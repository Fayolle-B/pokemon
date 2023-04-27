package com.uca.dao;

import com.uca.entity.TradeEntity;
import com.uca.entity.TradeStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TradeDAO extends _Generic<TradeEntity> {

    @Override
    public TradeEntity create(TradeEntity obj) throws SQLException {
        //TODO: le faire quoi
        PreparedStatement statement = this.connect.prepareStatement("INSERT INTO TRADES ( APPOWNID, RECOWNID, SUBMITDATE, ACCEPTDATE, STATUS) VALUES ( ?,?,?,?,?,? )", PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, obj.getApplicantPossession().getIdPos());
        statement.setInt(2, obj.getRecipientPossession().getIdPos());
        statement.setDate(3, new java.sql.Date(obj.getSubmitDate().getTime()));
        statement.setDate(4, new java.sql.Date(obj.getAcceptDate().getTime()));
        statement.setString(5,obj.getStatus().toString());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if(resultSet.next()){
            obj.setId(resultSet.getInt("id"));
        };
        return  obj;
    }

    @Override
    public void delete(TradeEntity obj) {;;

    }

    public void update(TradeEntity tradeEntity) throws SQLException {
        PreparedStatement preparedStatement = this.connect.prepareStatement("UPDATE TRADES set ACCEPTDATE = ?, STATUS = ? where ID=?");
        preparedStatement.setDate(1,new java.sql.Date(tradeEntity.getAcceptDate().getTime()));
        preparedStatement.setString(2,tradeEntity.getStatus().toString());
        preparedStatement.setInt(3, tradeEntity.getId());

    }

    public TradeEntity getTradeById(int id) throws SQLException {
        TradeEntity tradeEntity = new TradeEntity(null,null);
        PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT ID, APPOWNID, RECOWNID, SUBMITDATE, ACCEPTDATE, STATUS from TRADES where id=?");
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.isLast()){
            throw new SQLException("Cannot recover a Trade from this ID");
        }
        resultSet.next();
        PossessionDAO possessionDAO = new PossessionDAO();
        tradeEntity.setId(id);
        tradeEntity.setApplicantPossession(possessionDAO.getPossessionById(resultSet.getInt("APPOWNID")));
        tradeEntity.setRecipientPossession(possessionDAO.getPossessionById(resultSet.getInt("RECOWNID")));
        tradeEntity.setSubmitDate(new Date(resultSet.getDate("SUBMITDATE").getTime()));
        tradeEntity.setAcceptDate(new Date(resultSet.getDate("ACCEPTDATE").getTime()));
        try {

            tradeEntity.setStatus(TradeStatus.valueOf(resultSet.getString("STATUS")));
        }catch (SQLException e){
            e.printStackTrace();
            throw  e;
        }
        return  tradeEntity;

    }
}
