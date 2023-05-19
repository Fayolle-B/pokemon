package com.uca.entity;

import com.uca.core.PossessionCore;
import com.uca.core.TradeCore;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserEntity {
    private String      firstName;
    private String      lastName;
    private int         id;
    private String      login;
    private String      pwdHash;
    private String      Email;
    private int         points;
    private LocalDate   dateConnexion;


    public UserEntity() {
        //Ignored !
    }

    public int getId() {
        return this.id;
    }

    public int getPoints() {
        return this.points;
    }

    public String getLogin() {
        return this.login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return this.Email;
    }

    public LocalDate getDateConnexion() {
        return this.dateConnexion;
    }

    public String getPwdHash() {
        return this.pwdHash;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setEmail(String E) {
        this.Email = E;
    }

    public void setDateConnexion(LocalDate date) {
        // voir plus tard comment creer l'objet date
        this.dateConnexion = date;
    }

    public void setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
    }

    public void setPoints(int p) {
        this.points = p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;

        if (!getPwdHash().equals(that.getPwdHash())) return false;
        return getLogin().equals(that.getLogin());
    }

    @Override

    public int hashCode() {
        int result = getPwdHash().hashCode();
        result = 31 * result + getEmail().hashCode();
        return result;
    }

    /**
     * A méthod to get the trades this user proposed, having the PENDING status
     *
     * @return
     */
    public ArrayList<TradeEntity> getProposedPendingTrades() {
        ArrayList<TradeEntity> tradeEntities = TradeCore.getTradesOfHavingStatus(this, TradeStatus.PENDING);
        for (TradeEntity tradeEntity : tradeEntities) {
            System.out.println(tradeEntity.getApplicantPossession().getOwner().getId() + "and " + this.getId());
        }
        tradeEntities.removeIf(tradeEntity -> tradeEntity.getApplicantPossession().getOwner().getId() != this.getId());
        return tradeEntities;
    }

    public ArrayList<TradeEntity> getReceivedPendingTrades() {
        ArrayList<TradeEntity> tradeEntities = TradeCore.getTradesOfHavingStatus(this, TradeStatus.PENDING);
        tradeEntities.removeIf((tradeEntity -> tradeEntity.getRecipientPossession().getOwner().getId() != this.getId()));
        return tradeEntities;
    }

    public ArrayList<PossessionEntity> getAvailableForTrade() {
        ArrayList<PossessionEntity> possessionEntities = new ArrayList<>();
        for (PossessionEntity possessionEntity : PossessionCore.activPossessionOf(this)) {
            if (!possessionEntity.isProposed())
                possessionEntities.add(possessionEntity);
        }
        return possessionEntities;

    }
}
