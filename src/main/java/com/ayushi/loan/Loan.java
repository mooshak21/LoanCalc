package com.ayushi.loan;

import java.io.Serializable;

public class Loan implements Serializable {
	private static final long serialVersionUID = 1L;
	private Double amount, total, monthly;
	private String lender;
	private String state;
	private Double interestRate;
	private Double apr;
	private Integer numberOfYears;
	private Long loanId;
	private LoanApp loanApp;
	private Double principal, interest;

	public Loan(){
		amount = 0.0;
		total = 0.0;
		monthly = 0.0;
		lender = "Bank";
		state = "NJ";
		interestRate = 0.0;
		apr = 0.0;
		numberOfYears = 1;
		loanId = System.currentTimeMillis();
		loanApp = null;
		principal = 0.0;
		interest = 0.0;
	}

	public Loan(double mnthly, double amt, double tot, String lndr, String st, double intRate, double Apr, int numYears, double interestPayment){
		monthly = new Double(mnthly);
		amount = new Double(amt);
		total = new Double(tot);
		lender = lndr;
		state = st;
		interestRate = new Double(intRate);
		apr = new Double(Apr);
		numberOfYears = new Integer(numYears);
		loanId = System.currentTimeMillis();
		loanApp = new LoanApp(mnthly, amt, tot, lndr, st, intRate, Apr, numYears);
		interest = new Double(interestPayment);
		principal = new Double(monthly - interest);
	}
	public void setLoanApp(LoanApp lnApp){
		loanApp = lnApp;
		loanApp.setLoan(this);
	}
	public LoanApp getLoanApp(){
		return loanApp;
	}
	public void setMonthly (Double mthly){
		monthly = mthly;
	}
	public Double getMonthly(){
		return monthly;
	}
	public void setAmount (Double amt){
		amount = amt;
	}
	public Double getAmount(){
		return amount;
	}
	public void setTotal (Double tot){
		total = tot;
	}
	public Double getTotal(){
		return total;
	}
	public void setLender(String lndr){
		lender = lndr;
	}
	public String getLender(){
		return lender;
	}
	public void setState(String st){
		state = st;
	}
	public String getState(){
		return state;
	}
	public void setInterestRate(Double intRate){
		interestRate = intRate;
	}
	public Double getInterestRate(){
		return interestRate;
	}
	public void setAPR(Double Apr){
		apr = Apr;
	}
	public Double getAPR(){
		return apr;
	}
	public void setNumberOfYears (Integer num){
		numberOfYears = num;
	}
	public Integer getNumberOfYears(){
		return numberOfYears;
	}
	public Long getLoanId(){
		return loanId;
	}
	public void setLoanId(Long lnId){
		loanId = lnId;
	}
	public void calculatePrincipalAndInterest(double loanAmount, int numOfYears, double interestRate, double air){
		                        double periodicInterestRate = Double.valueOf(air)/(12*100);
					                        double addOne = (1 + periodicInterestRate);
								                        double loanAmt = Double.valueOf(loanAmount);
											                        double compoundingPeriods = Double.valueOf(numOfYears)*12;

														                        double monthly = 0;
																	                        double total = 0;

																				                        monthly = loanAmt * (((periodicInterestRate * Math.pow(addOne, compoundingPeriods))/(Math.pow(addOne,compoundingPeriods) - 1)));
			principal = loanAmt / compoundingPeriods;
			interest = monthly - principal;
	}

	public void setPrincipal(Double princ){
		principal = princ;
	}
	public Double getPrincipal(){
		return principal;
	}
	public void setInterest(Double intAmt){
		interest = intAmt;
	}
	public Double getInterest(){
		return interest;
	}


	public String toString(){
		return "Loan with id " + this.getLoanId() + " and amount $" + this.getAmount() + " has a monthly payment of $" + this.getMonthly() + " with principal of $ " + this.getPrincipal() + " and interest of $ " + this.getInterest() + " for " + this.getNumberOfYears() + " years and is from " + this.getLender() + " in state " + this.getState() + " at interest rate of " + this.getInterestRate() + "% and APR of " + this.getAPR() + "%" + " and Loan App of " + (this.getLoanApp() != null ? this.getLoanApp().toString() : "");
	}
}
