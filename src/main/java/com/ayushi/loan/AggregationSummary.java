package com.ayushi.loan;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AggregationSummary implements Serializable {

    private static final long serialVersionUID = 1L;
    private double totalAmount, monthlyAmount;
    private double amountPaid;
    private double remainingAmount ;
    private double remainingPercent;
    private int maximumNumOfYear;
    private Calendar startDate;
    private Calendar payoffDate;

    public AggregationSummary(){
        totalAmount = 0.0;
        monthlyAmount = 0.0;
        amountPaid = 0.0;
        remainingPercent  = 0.0;
        remainingAmount = 0.0;
        maximumNumOfYear = 1;
        startDate = GregorianCalendar.getInstance();
        payoffDate = GregorianCalendar.getInstance();
    }


    public AggregationSummary(double totalAmount, double monthlyAmount, double amountPaid,double remainingPercent, double remainingAmount, int maximumNumOfYear, Calendar startDate, Calendar payoffDate){
        totalAmount = new Double(totalAmount);
        monthlyAmount = new Double(monthlyAmount);
        amountPaid = new Double(amountPaid);
        remainingPercent = new Double(remainingPercent);
        remainingAmount = new Double(remainingAmount);
        maximumNumOfYear = new Integer(maximumNumOfYear);
        startDate = startDate;
        payoffDate = payoffDate;

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(double monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public double getRemainingPercent() {
        return remainingPercent;
    }

    public void setRemainingPercent(double remainingPercent) {
        this.remainingPercent = remainingPercent;
    }

    public int getMaximumNumOfYear() {
        return maximumNumOfYear;
    }

    public void setMaximumNumOfYear(int maximumNumOfYear) {
        this.maximumNumOfYear = maximumNumOfYear;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getPayoffDate() {
        return payoffDate;
    }

    public void setPayoffDate(Calendar payoffDate) {
        this.payoffDate = payoffDate;
    }
}
