package calculator;

import java.io.Serializable;
import java.lang.HashMap;

public class AmortizedLoan extends Loan {
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, LoanEntry> entries = new HashMap<Integer, LoanEntry>();
	public Loan(){
		super();
	}

	public Loan(double mnthly, double amt, double tot, String lndr, String st, double intRate, double Apr, int numYears, double interestPayment){
		super(mnthly, amt, tot, lndr, st, intRate, Apr, numYears, interestPayment);
		for(int cmpPeriod = 0; cmpPeriod < numYears * 12; cmpPeriod++){
			entries.put(new Integer(cmpPeriod), calculatePrincipal(amt, (numYears * 12 - cmpPeriod)/12, intRate, Apr));
		}

	}
	public LoanEntry calculatePrincipalAndInterest(double loanAmount, int numOfYears, double interestRate, double air){
		                        double periodicInterestRate = Double.valueOf(air)/(12*100);
					                        double addOne = (1 + periodicInterestRate);
								                        double loanAmt = Double.valueOf(loanAmount);
											                        double compoundingPeriods = Double.valueOf(numOfYears)*12;

														                        double monthly = 0;
																	                        double total = 0;

																				                        monthly = loanAmt * (((periodicInterestRate * Math.pow(addOne, compoundingPeriods))/(Math.pow(addOne,compoundingPeriods) - 1)));
			principal = loanAmt / compoundingPeriods;
			interest = monthly - principal;
			return new LoanEntry(principal, interest);
	}

	public String toString(){
		return super.toString();
	}
	public HashMap<Integer, LoanEntry> getLoanEntries(){
		return entries;
	}

}
