package com.uca.dao;

import com.uca.entity.PossessionEntity;
import com.uca.entity.TradeEntity;
import com.uca.entity.TradeStatus;
import com.uca.entity.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Provides methods to interact with Trades database.
 */
public class TradeDAO extends _Generic<TradeEntity> {


    public TradeDAO() {
        this.connect = _Connector.getInstance();
    }

    public TradeDAO(Connection connection) {
        this.connect = connection;
    }


    /**
     * Creates a new TradeEntity in the database. The TradeEntity's id is set automatically.
     * @param obj the TradeEntity to be created
     * @return the newly created TradeEntity object
     * @throws SQLException if the creation fails
     */
    @Override
    public TradeEntity create(TradeEntity obj) throws SQLException {
        //TODO: le faire quoi
        PreparedStatement statement = this.connect.prepareStatement("INSERT INTO TRADES ( APPOWNID, RECOWNID, SUBMITDATE, ACCEPTDATE, STATUS) VALUES ( ?,?,?,?,? )", PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, obj.getApplicantPossession().getIdPos());
        statement.setInt(2, obj.getRecipientPossession().getIdPos());
        statement.setDate(3,  java.sql.Date.valueOf(obj.getSubmitDate()));
        try {
            statement.setDate(4, java.sql.Date.valueOf(obj.getAcceptDate()));
        }catch (NullPointerException e){
            statement.setDate(4,null);
        }
        statement.setString(5,obj.getStatus().toString());
        //statement.setInt(6,obj.getId());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if(resultSet.next()){
            obj.setId(resultSet.getInt("id"));
        };
        return  obj;
    }

    /**
     * Deletes a TradeEntity from the database.
     * @param obj the TradeEntity to be deleted
     */

    @Override
    public void delete(TradeEntity obj) {;;

    }


    /**
     * Updates a TradeEntity in the database
     * @param tradeEntity the TradeEntity to be updated
     * @throws SQLException if the update fails
     */
    public void update(TradeEntity tradeEntity) throws SQLException {
        PreparedStatement preparedStatement = this.connect.prepareStatement("UPDATE TRADES set ACCEPTDATE = ?, STATUS = ? where ID=?");
        preparedStatement.setDate(1, java.sql.Date.valueOf(tradeEntity.getAcceptDate()));
        preparedStatement.setString(2,tradeEntity.getStatus().toString());
        System.out.println("Le status est : " + tradeEntity.getStatus().toString());
        preparedStatement.setInt(3, tradeEntity.getId());
        preparedStatement.executeUpdate();

    }


    /**
     * Retrieve the list of all TradeEntity in the database.
     * @param id the id of the user
     * @return the list of all TradeEntity in the database
     * @throws SQLException if the retrieval fails
     */
    public TradeEntity getTradeById(int id) throws SQLException {
        TradeEntity tradeEntity = new TradeEntity(null,null);
        PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT ID, APPOWNID, RECOWNID, SUBMITDATE, ACCEPTDATE, STATUS from TRADES where id=?");
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.isLast()){
            throw new SQLException("There is no trade with this ID");
        }
        resultSet.next();
        PossessionDAO possessionDAO = new PossessionDAO();
        tradeEntity.setId(id);
        tradeEntity.setApplicantPossession(possessionDAO.getPossessionById(resultSet.getInt("APPOWNID")));
        tradeEntity.setRecipientPossession(possessionDAO.getPossessionById(resultSet.getInt("RECOWNID")));
        tradeEntity.setSubmitDate(java.sql.Date.valueOf(resultSet.getDate("SUBMITDATE").toLocalDate()).toLocalDate());
        if(resultSet.getDate("ACCEPTDATE")==null){
            tradeEntity.setAcceptDate(null);
        }else{
            tradeEntity.setAcceptDate(java.sql.Date.valueOf(resultSet.getDate("ACCEPTDATE").toLocalDate()).toLocalDate());
        }
        tradeEntity.setStatus(TradeStatus.valueOf(resultSet.getString("STATUS")));
        return  tradeEntity;

    }


    /**
     * Retrieve all trads of a user
     * @param userEntity the user
     * @return the list of all TradeEntity of the user
     */
    public ArrayList<TradeEntity> getAllTradesOf(UserEntity userEntity) {
        ArrayList<TradeEntity> tradeEntities = new ArrayList<>();
        ResultSet resultSet;
        PreparedStatement preparedStatement = null;
        PossessionDAO possessionDAO = new PossessionDAO();
        try {
            preparedStatement = this.connect.prepareStatement("SELECT ID, APPOWNID, RECOWNID, SUBMITDATE, ACCEPTDATE, STATUS from TRADES");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            while(resultSet.next()){
                TradeEntity tradeEntity = new TradeEntity();

                PossessionEntity appPoss = possessionDAO.getPossessionById(resultSet.getInt("APPOWNID"));
                PossessionEntity recPoss= possessionDAO.getPossessionById(resultSet.getInt("RECOWNID"));
                if(appPoss.getOwner().equals(userEntity) || recPoss.getOwner().equals(userEntity)){
                    tradeEntity.setId(resultSet.getInt("ID"));
                    tradeEntity.setApplicantPossession(appPoss);
                    tradeEntity.setRecipientPossession(recPoss);
                    tradeEntity.setSubmitDate(java.sql.Date.valueOf(resultSet.getDate("SUBMITDATE").toLocalDate()).toLocalDate());
                    if(resultSet.getDate("ACCEPTDATE")==null){
                        tradeEntity.setAcceptDate(null);
                    }else{
                        tradeEntity.setAcceptDate((resultSet.getDate("ACCEPTDATE")).toLocalDate());
                    }
                    tradeEntity.setStatus(TradeStatus.valueOf(resultSet.getString("STATUS")));
                    tradeEntities.add(tradeEntity);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  tradeEntities;
    }
}
