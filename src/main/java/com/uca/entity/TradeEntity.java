package java.com.uca.entity;

import java.time.LocalDate;

public class TradeEntity {
    private int id;
    private PossessionEntity applicantPossession;
    private PossessionEntity recipientPossession;
    private LocalDate submitDate;
    private LocalDate acceptDate;
 //   private ArrayList<PossessionEntity> appliances = new ArrayList<PossessionEntity>();

    private TradeStatus status;

    public TradeEntity(PossessionEntity applicantPossession, PossessionEntity recipientPossession) {
        this.applicantPossession = applicantPossession;
        this.recipientPossession =recipientPossession;
        this.submitDate=LocalDate.now();
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

    public LocalDate getSubmitDate() {
        System.out.println("ON récupère la date : "+ submitDate);
        return submitDate;
    }

    public LocalDate getAcceptDate() {
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

    public void setAcceptDate(LocalDate acceptDate) {
        this.acceptDate = acceptDate;
    }
    //TODO : equals, trouver "l'ame soeur d'une enttité trade"


    public void setApplicantPossession(PossessionEntity applicantPossession) {
        this.applicantPossession = applicantPossession;
    }

    public void setRecipientPossession(PossessionEntity recipientPossession) {
        this.recipientPossession = recipientPossession;
    }

    public void setSubmitDate(LocalDate submitDate) {
        this.submitDate = submitDate;
    }
}



