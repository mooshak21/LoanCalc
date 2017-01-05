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
		for(int cmpPeriod = 0; cmpPeriod < numYears * 12; cmpPeriod++){
			entries.put(new Integer(cmpPeriod), calculateLoanEntry(amt, (numYears * 12 - cmpPeriod)/12, intRate, Apr));
		}

	}
	public LoanEntry calculateLoanEntry(double loanAmount, int numOfYears, double interestRate, double air){
		                        double periodicInterestRate = Double.valueOf(air)/(12*100);
					                        double addOne = (1 + periodicInterestRate);
								                        double loanAmt = Double.valueOf(loanAmount);
											                        double compoundingPeriods = Double.valueOf(numOfYears)*12;

														                        double monthly = 0;
																	                        double total = 0;

																				                        monthly = loanAmt * (((periodicInterestRate * Math.pow(addOne, compoundingPeriods))/(Math.pow(addOne,compoundingPeriods) - 1)));
			setPrincipal(loanAmt / compoundingPeriods);
			setInterest(monthly - getPrincipal());
			return new LoanEntry(getPrincipal(), getInterest());
	}

	public String toString(){
		return super.toString();
	}
	public LoanEntry[] getLoanEntries(){
		LoanEntry[] ents = new LoanEntry[entries.values().length()];
		entries.values().toArray(ents);
		return (LoanEntry[]) ents;
	}

}
