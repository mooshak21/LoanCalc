package calculator;

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
	}
	public LoanApp(double mnthly, double amt, double tot, String lndr, String st, double intRate, double Apr, int numYears){
		monthly = mnthly;
		amount = amt;
		total = tot;
		lender = lndr;
		state = st;
		interestRate = intRate;
		apr = Apr;
		numberOfYears = numYears;
		loanId = System.currentTimeMillis();
	}
	public void setMonthly (double mthly){
		monthly = mthly;
	}
	public double getMonthly(){
		return monthly;
	}
	public void setAmount (double amt){
		amount = amt;
	}
	public double getAmount(){
		return amount;
	}
	public void setTotal (double tot){
		total = tot;
	}
	public double getTotal(){
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
	public void setInterestRate(double intRate){
		interestRate = intRate;
	}
	public double getInterestRate(){
		return interestRate;
	}
	public void setAPR(double Apr){
		apr = Apr;
	}
	public double getAPR(){
		return apr;
	}
	public void setNumberOfYears (int num){
		numberOfYears = num;
	}
	public int getNumberOfYears(){
		return numberOfYears;
	}
	public long getLoanId(){
		return loanId;
	}
	public void setLoanId(long lnId){
		loanId = lnId;
	}
	public String toString(){
		return "Loan with id " + this.getLoanId() + " and amount $" + this.getAmount() + " has a monthly payment of $" + this.getMonthly() + " for " + this.getNumberOfYears() + " years and is from " + this.getLender() + " in state " + this.getState() + " at interest rate of " + this.getInterestRate() + "% and APR of " + this.getAPR() + "%";
	}
};
