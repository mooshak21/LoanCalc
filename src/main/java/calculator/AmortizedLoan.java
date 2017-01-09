package calculator;

import java.util.HashMap;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AmortizedLoan extends Loan {
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, LoanEntry> entries = new HashMap<Integer, LoanEntry>();
	private double amortizedloanAmt = 0;
	public AmortizedLoan(){
		super();
	}

	public AmortizedLoan(double mnthly, double amt, double tot, String lndr, String st, double intRate, double Apr, int numYears, double interestPayment){
		super(mnthly, amt, tot, lndr, st, intRate, Apr, numYears, interestPayment);
		int maxCmpPeriod = numYears*12, cmpPeriod = 1;
		Calendar dateLastEntry = null;
		amortizedloanAmt = amt;
		for(cmpPeriod = 1; cmpPeriod < maxCmpPeriod && amortizedloanAmt >= 0; cmpPeriod++){
			Calendar dateEntry = Calendar.getInstance();
			dateEntry.set(dateEntry.get(Calendar.YEAR),dateEntry.get(Calendar.MONTH)+cmpPeriod-1, dateEntry.get(Calendar.DAY_OF_MONTH));
			LoanEntry loanEntry = calculateLoanEntry(dateEntry, amt, numYears, cmpPeriod % maxCmpPeriod, intRate, Apr);

			if(mnthly <= loanEntry.getPrincipal())
				entries.put(new Integer(cmpPeriod), loanEntry);
	
		}
	}
	public LoanEntry calculateLoanEntry(Calendar dateEntry, double loanAmount, int totalNumYears, double numOfYears, double interestRate, double air){
		                        double periodicInterestRate = Double.valueOf(air)/(12*100);
					                        double addOne = (1 + periodicInterestRate);
								                        double loanAmt = Double.valueOf(loanAmount);
											                        double compoundingPeriods = Double.valueOf(numOfYears)*12;

				double monthly = 0;
				double total = 0;
				double currloanAmt = 0;
											                        monthly = loanAmt * (((periodicInterestRate * Math.pow(addOne, totalNumYears*12))/(Math.pow(addOne,(totalNumYears*12)) - 1)));
			setInterest((loanAmt * Math.pow((1+periodicInterestRate),numOfYears*12) - loanAmt)/(numOfYears*12));
			currloanAmt = amortizedloanAmt - getInterest();
		
			setPrincipal(monthly - (currloanAmt * periodicInterestRate));
			amortizedloanAmt -= getPrincipal();
			return new LoanEntry(dateEntry, getPrincipal(), getInterest(), amortizedloanAmt, monthly);
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
