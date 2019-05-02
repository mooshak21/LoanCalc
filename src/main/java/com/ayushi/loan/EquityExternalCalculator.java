package com.ayushi.loan;

import java.io.Serializable;

public class EquityExternalCalculator  implements Serializable {
    protected static final long serialVersionUID = 1L;
    protected String linkUrl;
    protected Long externalCalculatorId;
    protected String region;
    protected String loanType;

    public EquityExternalCalculator(String linkUrl, Long externalCalculatorId, String region, String loanType) {
        this.linkUrl = linkUrl;
        this.externalCalculatorId = externalCalculatorId;
        this.region = region;
        this.loanType = loanType;
    }

    public EquityExternalCalculator(){
        this.externalCalculatorId = System.currentTimeMillis();
        this.linkUrl = "contact@loaninsight.online";
        this.region = "USA";
        this.loanType = "Home Loan";
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Long getExternalCalculatorId() {
        return externalCalculatorId;
    }

    public void setExternalCalculatorId(Long externalCalculatorId) {
        this.externalCalculatorId = externalCalculatorId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
}
