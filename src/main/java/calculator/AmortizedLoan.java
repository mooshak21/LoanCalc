package calculator;

import java.util.HashMap;

public class AmortizedLoan extends Loan {
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, LoanEntry> entries = new HashMap<Integer, LoanEntry>();
	public AmortizedLoan(){
		super();
	}

	public AmortizedLoan(double mnthly, double amt, double tot, String lndr, String st, double intRate, double Apr, int numYears, double interestPayment){
		super(mnthly, amt, tot, lndr, st, intRate, Apr, numYears, interestPayment);
		int maxCmpPeriod = numYears*12;
		for(int cmpPeriod = 1; cmpPeriod < maxCmpPeriod; cmpPeriod++){
			entries.put(new Integer(cmpPeriod), calculateLoanEntry(amt, numYears, cmpPeriod % maxCmpPeriod, intRate, Apr));
		}

	}
	public LoanEntry calculateLoanEntry(double loanAmount, int totalNumYears, double numOfYears, double interestRate, double air){
		                        double periodicInterestRate = Double.valueOf(air)/(12*100);
					                        double addOne = (1 + periodicInterestRate);
								                        double loanAmt = Double.valueOf(loanAmount);
											                        double compoundingPeriods = Double.valueOf(numOfYears)*12;

														                        double monthly = 0;
																	                        double total = 0;

																				                        monthly = loanAmt * (((periodicInterestRate * Math.pow(addOne, totalNumYears*12))/(Math.pow(addOne,(totalNumYears*12)) - 1)));
			setInterest((loanAmt * Math.pow((1+periodicInterestRate),numOfYears*12) - loanAmt)/(numOfYears*12));
i			double currloanAmt = 0;
			currloanAmt = loanAmt - getInterest();
			setPrincipal(monthly - (currloanAmt * periodicInterestRate));	
			return new LoanEntry(getPrincipal(), getInterest());
	}

	public String toString(){
		return super.toString();
	}
	public LoanEntry[] getLoanEntries(){
		System.out.println("Entry count is " + entries.size());
		LoanEntry[] ents = new LoanEntry[entries.size()];
		entries.values().toArray(ents);
		return (LoanEntry[]) ents;
	}
	public void setLoanEntries(LoanEntry[] ent){
	}
	public HashMap<Integer, LoanEntry> getEntries(){
		return entries;
	}
	public void setEntries(HashMap<Integer, LoanEntry> ents){
		entries = ents;
	}
}
