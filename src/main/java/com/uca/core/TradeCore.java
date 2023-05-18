package java.com.uca.core;

import java.com.uca.dao.PossessionDAO;
import java.com.uca.dao.TradeDAO;
import java.com.uca.entity.PossessionEntity;
import java.com.uca.entity.TradeEntity;
import java.com.uca.entity.TradeStatus;
import java.com.uca.entity.UserEntity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * The Class TradeCore is the core class for the trade entity.
 */
public class TradeCore {


    /**
     * The method newTrade creates a new trade, and add it to the database.
     * @param applicantPossession the possession of the applicant.
     * @param recipientPossession the possession of the recipient.
     * @return the trade entity created.
     * @throws RuntimeException if the trade can't be created.
     */
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


    /**
     * The method newTradeFromIDs creates a new trade, and add it to the database.
     * @param id1 the id of the applicant possession.
     * @param id2 the id of the recipient possession.
     * @return the trade entity created.
     */
    public static TradeEntity newTradeFromIDs(int id1, int id2) {
        PossessionEntity applicantPossession, recipientPossession;
        applicantPossession = PossessionCore.getPossessionById(id1);
        recipientPossession = PossessionCore.getPossessionById(id2);
        return newTrade(applicantPossession, recipientPossession);
    }


    /**
     * The method getAllTradesOf returns all the trades of a user.
     * @param userEntity the user for which we want to get the trades.
     * @return an ArrayList of TradeEntity containing all the trades of the user.
     */

    public static ArrayList<TradeEntity> getAllTradesOf(UserEntity userEntity) {
        return new TradeDAO().getAllTradesOf(userEntity);
    }

    /**
     * The method getTradesOfHavingStatus returns all the trades of a user having a specific status.
     * @param userEntity the user for which we want to get the trades.
     * @param status the status of the trades we want to get (a trade can have 3 status: PENDING, ACCEPTED, REFUSED), from the enum TradeStatus.
     * @return an ArrayList of TradeEntity containing all the trades of the user having the status specified.
     */
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


    /**
     * acceptTrade accepts a trade.
     * @param tradeEntity the trade to accept.
     * @return the trade accepted.
     */
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

        try {
            PossessionCore.addPossession(tradeEntity.getRecipientPossession().getOwner(), tradeEntity.getApplicantPossession().getPokemon(), tradeEntity.getApplicantPossession().getLevel());
            PossessionCore.addPossession(tradeEntity.getApplicantPossession().getOwner(), tradeEntity.getRecipientPossession().getPokemon(), tradeEntity.getRecipientPossession().getLevel());
        } catch (Exception e) {
            throw new RuntimeException("Error while running possession swapping");
        }
        //let's check if number of posssersion is still good
        //assert us1possNumber ==PossessionCore.possessionOf(tradeEntity.getApplicantPossession().getOwner()).size() && us2possNumber== PossessionCore.possessionOf(tradeEntity.getRecipientPossession().getOwner()).size();
        return tradeEntity;

    }


    /**
     * Retrieve a trade by its id.
      * @param id the id of the trade to retrieve.
     * @return the trade with the id specified.
     */
    public static TradeEntity getTradeById(int id) {
        try {
            return new TradeDAO().getTradeById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to get Trade with id " + id);
        }
    }
}


