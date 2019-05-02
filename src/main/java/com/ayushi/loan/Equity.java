package com.ayushi.loan;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author garima.agarwal
 * 4/23/2019 12:20 PM
 */
public class Equity implements Serializable {
    protected static final long serialVersionUID = 1L;
    private Long equityId;
    private  Long loanId;
    private String email;
    private String loanType;
    private Double loanBalanceAmount;
    private Integer remainingYear;
    private Double equityValue;
    private Double assetValue;
    private Calendar valuationDate;

    public Equity(){
        equityId = System.currentTimeMillis();
        loanId = System.currentTimeMillis();
        email = "contact@loaninsight.online";
        loanType = "Home Loan";
        loanBalanceAmount = 0.0;
        remainingYear = 0;
        equityValue = 0.0;
        assetValue = 0.0;
        valuationDate = GregorianCalendar.getInstance();
    }

    public Equity(Long equityId, Long loanId, String email, String loanType, Double loanBalanceAmount, Integer remainingYear, Double equityValue, Double assetValue, Calendar valuationDate) {
        this.equityId = equityId;
        this.loanId = loanId;
        this.email = email;
        this.loanType = loanType;
        this.loanBalanceAmount = loanBalanceAmount;
        this.remainingYear = remainingYear;
        this.equityValue = equityValue;
        this.assetValue = assetValue;
        this.valuationDate = valuationDate;
    }

    public Long getEquityId() {
        return equityId;
    }

    public void setEquityId(Long equityId) {
        this.equityId = equityId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public Double getLoanBalanceAmount() {
        return loanBalanceAmount;
    }

    public void setLoanBalanceAmount(Double loanBalanceAmount) {
        this.loanBalanceAmount = loanBalanceAmount;
    }

    public Integer getRemainingYear() {
        return remainingYear;
    }

    public void setRemainingYear(Integer remainingYear) {
        this.remainingYear = remainingYear;
    }

    public Double getEquityValue() {
        return equityValue;
    }

    public void setEquityValue(Double equityValue) {
        this.equityValue = equityValue;
    }

    public Double getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(Double assetValue) {
        this.assetValue = assetValue;
    }

    public Calendar getValuationDate() {
        return valuationDate;
    }

    public void setValuationDate(Calendar valuationDate) {
        this.valuationDate = valuationDate;
    }
}
