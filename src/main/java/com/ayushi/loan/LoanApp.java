package com.ayushi.loan;

import java.io.Serializable;

public class LoanApp implements Serializable {
	private static final long serialVersionUID = 1L;
	private double amount, total, monthly;
	private String lender;
	private String state;
	private double interestRate;
	private double apr;
	private int numberOfYears;
	private long loanId;
	private Loan loan;

	public LoanApp(){
		amount = 0.0;
		total = 0.0;
		monthly = 0.0;
		lender = "Bank";
		state = "NJ";
		interestRate = 0.0;
		apr = 0.0;
		numberOfYears = 1;
		loanId = System.currentTimeMillis();
		loan = new Loan();
	}

	public LoanApp(Loan loan){
		                monthly = loan.getMonthly();
				amount = loan.getAmount();
				total = loan.getTotal();
				lender = loan.getLender();
				state = loan.getState();
				interestRate = loan.getInterestRate();
				apr = loan.getAPR();
				numberOfYears = loan.getNumberOfYears();
				loanId = System.currentTimeMillis();
				loan.setLoanApp(this);
	}
	public LoanApp(double mnthly, double amt, double tot, String lndr, String st, double intRate, double Apr, int numYears){
		monthly = new Double(mnthly);
		amount = new Double(amt);
		total = new Double(tot);
		lender = lndr;
		state = st;
		interestRate = new Double(intRate);
		apr = new Double(Apr);
		numberOfYears = new Integer(numYears);
		loanId = System.currentTimeMillis();
	}
	
	public void setLoan(Loan ln){
		loan = ln;
	}

	public Loan getLoan(){
		return loan;
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
	public String toString(){
		return "Loan with id " + this.getLoanId() + " and amount $" + this.getAmount() + " has a monthly payment of $" + this.getMonthly() + " for " + this.getNumberOfYears() + " years and is from " + this.getLender() + " in state " + this.getState() + " at interest rate of " + this.getInterestRate() + "% and APR of " + this.getAPR() + "%";
	}
}
