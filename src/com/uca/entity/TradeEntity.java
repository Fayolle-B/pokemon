package com.uca.entity;

import java.util.Date;

public class TradeEntity {
    private int id;
    private PossessionEntity applicantOwnership;
    private PossessionEntity recipientOwnership;
    private Date submitDate;
    private Date acceptDate;
 //   private ArrayList<PossessionEntity> appliances = new ArrayList<PossessionEntity>();

    private TradeStatus status;

    public TradeEntity(PossessionEntity applicantOwnership, PossessionEntity recipiantOwnership) {
        this.applicantOwnership = applicantOwnership;
        this.recipientOwnership =recipiantOwnership;
        this.submitDate=new Date();
        this.acceptDate=null;
        this.status=TradeStatus.PENDING;
    }

    //TODO:


    public int getId() {
        return id;
    }

    public PossessionEntity getApplicantOwnership() {
        return applicantOwnership;
    }

    public PossessionEntity getRecipientOwnership() {
        return recipientOwnership;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }
    //TODO : equals, trouver "l'ame soeur d'une enttité trade"
}


