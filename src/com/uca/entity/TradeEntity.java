package com.uca.entity;

import java.util.ArrayList;
import java.util.Date;

public class TradeEntity {
    private int id;
    private PossessionEntity applicantOwnership;
    private PossessionEntity recipiantOwnership;
    private Date submitDate;
    private Date acceptDate;
    private ArrayList<PossessionEntity> appliances = new ArrayList<PossessionEntity>();

    //TODO:
    public boolean send(PossessionEntity other) {
        return true;
    }


    public int getId() {
        return id;
    }

    public PossessionEntity getApplicantOwnership() {
        return applicantOwnership;
    }

    public PossessionEntity getRecipiantOwnership() {
        return recipiantOwnership;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public ArrayList<PossessionEntity> getAppliances() {
        return appliances;
    }

}


