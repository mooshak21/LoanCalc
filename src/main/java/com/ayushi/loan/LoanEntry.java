package com.ayushi.loan;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LoanEntry implements Serializable{
	public static final long serialVersionUId = 1L;
	private Calendar dateEntry;
	private double principal, interest, loanAmount, monthly;

	public LoanEntry(){
		principal = 0.0;
		interest = 0.0;
		dateEntry = GregorianCalendar.getInstance();
		loanAmount = 0.0;
		monthly = 0.0;
	}
	public LoanEntry(Calendar dtEntry, double princ, double intrst, double loanAmt, double mnthly){
		principal = princ;
		interest = intrst;
		dateEntry = dtEntry;
		loanAmount = loanAmt;
		monthly = mnthly;
	}
	public void setPrincipal(double princ){
		principal = princ;
	}
	public double getPrincipal(){
		return principal;
	}
	public void setInterest(double intrst){
		interest = intrst;
	}
	public double getInterest(){
		return interest;
	}
	public void setDateEntry(Calendar dtEntry){
		dateEntry = dtEntry;	
	}
	public Calendar getDateEntry(){
		return dateEntry;
	}
	public void setLoanAmount(double loanAmt){
		loanAmount = loanAmt;
	}
	public double getLoanAmount(){
		return loanAmount;
	}
	public void setMonthly(double mnthly){
		monthly = mnthly;
	}
	public double getMonthly(){
		return monthly;
	}
	
}
