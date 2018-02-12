package com.ayushi.loan;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract  class NewsObject implements Serializable {
    protected static final long serialVersionUID = 1L;
    protected String linkUrl;
    protected String newsType;
    protected Long offerId;
    protected String referer;
    protected String bankName;
    protected String loanType;
    protected String region;
    protected Double offerAmount;
    protected Double offerRate;
    protected Calendar offerStartDate;
    protected Calendar offerEndDate;
    protected String newsTitle;


    public NewsObject(Long offerId, String linkUrl, String newsType, String referer, String bankName, String loanType,
                      String region, Double offerAmount, Double offerRate, Calendar offerStartDate, Calendar offerEndDate, String newsTitle){
        this.offerId = offerId;
        this.linkUrl = linkUrl;
        this.newsType = newsType;
        this.referer = referer;
        this.bankName = bankName;
        this.loanType = loanType;
        this.region = region;
        this.offerAmount = offerAmount;
        this.offerRate = offerRate;
        this.offerStartDate = offerStartDate;
        this.offerEndDate = offerEndDate;
        this.newsTitle =  newsTitle;
    }

    public NewsObject(){
        this.offerId = System.currentTimeMillis();;
        this.linkUrl = "contact@loaninsight.online";
        this.referer = "www.loaninsight.online/";
        this.bankName = "Watermark";
        this.loanType = "Home Loan";
        this.region = "USA";
        this.offerAmount = 123456.0;
        this.offerRate = 12.0;
        this.offerStartDate = GregorianCalendar.getInstance();
        this.offerEndDate = GregorianCalendar.getInstance();
    }

    public Double getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(Double offerAmount) {
        this.offerAmount = offerAmount;
    }

    public Double getOfferRate() {
        return offerRate;
    }

    public void setOfferRate(Double offerRate) {
        this.offerRate = offerRate;
    }

    public Calendar getOfferStartDate() {
        return offerStartDate;
    }

    public void setOfferStartDate(Calendar offerStartDate) {
        this.offerStartDate = offerStartDate;
    }

    public Calendar getOfferEndDate() {
        return offerEndDate;
    }

    public void setOfferEndDate(Calendar offerEndDate) {
        this.offerEndDate = offerEndDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
}
