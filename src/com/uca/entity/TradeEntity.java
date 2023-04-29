package com.uca.entity;

import java.util.Date;

public class TradeEntity {
    private int id;
    private PossessionEntity applicantPossession;
    private PossessionEntity recipientPossession;
    private Date submitDate;
    private Date acceptDate;
 //   private ArrayList<PossessionEntity> appliances = new ArrayList<PossessionEntity>();

    private TradeStatus status;

    public TradeEntity(PossessionEntity applicantPossession, PossessionEntity recipientPossession) {
        this.applicantPossession = applicantPossession;
        this.recipientPossession =recipientPossession;
        this.submitDate=new Date();
        this.acceptDate=null;
        this.status=TradeStatus.PENDING;
    }
    public TradeEntity(){

    }



    public int getId() {
        return id;
    }

    public PossessionEntity getApplicantPossession() {
        return applicantPossession;
    }

    public PossessionEntity getRecipientPossession() {
        return recipientPossession;
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


    public void setApplicantPossession(PossessionEntity applicantPossession) {
        this.applicantPossession = applicantPossession;
    }

    public void setRecipientPossession(PossessionEntity recipientPossession) {
        this.recipientPossession = recipientPossession;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }
}



