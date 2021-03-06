package com.ayushi.loan;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kundan on 3/5/2018.
 */
public abstract class Payment implements Serializable {

    protected static final long serialVersionUID = 1L;
    private Long paymentId;
    private String paymentType;
    private Calendar paymentStartDate;
    private Calendar paymentEndDate;
    private Double paymentAmount;
    private String paymentFrequency;
    private Double balanceAmount;

    public Payment() {
    }

    public Payment(Long paymentId, String paymentType, Calendar paymentStartDate, Calendar paymentEndDate, Double paymentAmount, String paymentFrequency, Double balanceAmount) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
        this.paymentStartDate = paymentStartDate;
        this.paymentEndDate = paymentEndDate;
        this.paymentAmount = paymentAmount;
        this.paymentFrequency = paymentFrequency;
        this.balanceAmount = balanceAmount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Calendar getPaymentStartDate() {
        return paymentStartDate;
    }

    public void setPaymentStartDate(Calendar paymentStartDate) {
        this.paymentStartDate = paymentStartDate;
    }

    public Calendar getPaymentEndDate() {
        return paymentEndDate;
    }

    public void setPaymentEndDate(Calendar paymentEndDate) {
        this.paymentEndDate = paymentEndDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}
