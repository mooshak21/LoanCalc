package com.ayushi.loan;

import com.ayushi.loan.Payment;

/**
 * Created by Kundan on 3/5/2018.
 */
public class PayPalPayment extends Payment {

    private String payPalAccountNumber;
    private String payPalEmailAddress;
    private String payPalAuthPersonName;
    private String payPalPassword;
    private String payPalPlanId;
    private String payPalAgreementId;

    public PayPalPayment() {
        super();
    }


    public String getPayPalAccountNumber() {
        return payPalAccountNumber;
    }

    public void setPayPalAccountNumber(String payPalAccountNumber) {
        this.payPalAccountNumber = payPalAccountNumber;
    }

    public String getPayPalEmailAddress() {
        return payPalEmailAddress;
    }

    public void setPayPalEmailAddress(String payPalEmailAddress) {
        this.payPalEmailAddress = payPalEmailAddress;
    }

    public String getPayPalAuthPersonName() {
        return payPalAuthPersonName;
    }

    public void setPayPalAuthPersonName(String payPalAuthPersonName) {
        this.payPalAuthPersonName = payPalAuthPersonName;
    }

    public String getPayPalPassword() {
        return payPalPassword;
    }

    public void setPayPalPassword(String payPalPassword) {
        this.payPalPassword = payPalPassword;
    }


    public String getPayPalPlanId() {
        return payPalPlanId;
    }

    public void setPayPalPlanId(String payPalPlanId) {
        this.payPalPlanId = payPalPlanId;
    }

    public String getPayPalAgreementId() {
        return payPalAgreementId;
    }

    public void setPayPalAgreementId(String payPalAgreementId) {
        this.payPalAgreementId = payPalAgreementId;
    }
}
