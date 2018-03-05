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

    public PayPalPayment() {
        super();
    }

    public PayPalPayment(String payPalAccountNumber, String payPalEmailAddress, String payPalAuthPersonName, String payPalPassword) {
        this.payPalAccountNumber = payPalAccountNumber;
        this.payPalEmailAddress = payPalEmailAddress;
        this.payPalAuthPersonName = payPalAuthPersonName;
        this.payPalPassword = payPalPassword;
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
}
