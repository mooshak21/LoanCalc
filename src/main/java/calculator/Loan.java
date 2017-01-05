package calculator;

import java.io.Serializable;

public class Loan implements Serializable {
	private static final long serialVersionUID = 1L;
	private double amount, total, monthly;
	private String lender;
	private String state;
	private double interestRate;
	private double apr;
	private int numberOfYears;
	private long loanId;
	private LoanApp loanApp;
	private double principal, interest;

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
		monthly = mnthly;
		amount = amt;
		total = tot;
		lender = lndr;
		state = st;
		interestRate = intRate;
		apr = Apr;
		numberOfYears = numYears;
		loanId = System.currentTimeMillis();
		loanApp = new LoanApp(mnthly, amt, tot, lndr, st, intRate, Apr, numYears);
		//calculatePrincipalAndInterest(amount, numberOfYears, interestRate, apr);
	}
	public void setLoanApp(LoanApp lnApp){
		loanApp = lnApp;
		loanApp.setLoan(this);
	}
	public LoanApp getLoanApp(){
		return loanApp;
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

	public void setPrincipal(double princ){
		principal = princ;
	}
	public double getPrincipal(){
		return principal;
	}
	public void setInterest(double intAmt){
		interest = intAmt;
	}
	public double getInterest(){
		return interest;
	}


	public String toString(){
		return "Loan with id " + this.getLoanId() + " and amount $" + this.getAmount() + " has a monthly payment of $" + this.getMonthly() + " with principal of $ " + this.getPrincipal() + " and interest of $ " + this.getInterest() + " for " + this.getNumberOfYears() + " years and is from " + this.getLender() + " in state " + this.getState() + " at interest rate of " + this.getInterestRate() + "% and APR of " + this.getAPR() + "%" + " and Loan App of " + (this.getLoanApp() != null ? this.getLoanApp().toString() : "");
	}
}
