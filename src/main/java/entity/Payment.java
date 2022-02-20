package entity;

public class Payment {

    private Long id;
    private String templateName;
    private Long cardNumber;
    private Long paymentSum;
    private PaymentStatus paymentStatus;
    private Long dateCreation;
    private Long dateChange;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(Long paymentSum) {
        this.paymentSum = paymentSum;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Long dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getDateChange() {
        return dateChange;
    }

    public void setDateChange(Long dateChange) {
        this.dateChange = dateChange;
    }
}
