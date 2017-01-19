package com.ayushi.loan;

import java.util.HashMap;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.List;

public class AmortizedLoan extends Loan {
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, LoanEntry> entries = new HashMap<Integer, LoanEntry>();
	private ArrayList<LoanEntry> loanEntries = new ArrayList<LoanEntry>();
	private double amortizedloanAmt = 0;
	public AmortizedLoan(){
		super();
	}

	public AmortizedLoan(String amortizeOnDate, double mnthly, double amt, double tot, String lndr, String st, double intRate, double Apr, int numYears, double interestPayment){
		super(mnthly, amt, tot, lndr, st, intRate, Apr, numYears, interestPayment);
		int maxCmpPeriod = numYears*12, cmpPeriod = 1;
		Calendar dateLastEntry = null;
		amortizedloanAmt = amt;
		for(cmpPeriod = 1; cmpPeriod < maxCmpPeriod && amortizedloanAmt >= 0; cmpPeriod++){
			Calendar dateEntry = Calendar.getInstance();
			try{
				dateEntry.setTime(java.text.SimpleDateFormat.getDateInstance(java.text.DateFormat.SHORT, java.util.Locale.US).parse(amortizeOnDate));
			}catch(java.text.ParseException pe){ pe.printStackTrace(); }
			dateEntry.set(dateEntry.get(Calendar.YEAR),dateEntry.get(Calendar.MONTH)+cmpPeriod-1, dateEntry.get(Calendar.DAY_OF_MONTH));
			LoanEntry loanEntry = calculateLoanEntry(dateEntry, amt, numYears, cmpPeriod % maxCmpPeriod, intRate, Apr);

			if(loanEntry.getPrincipal() <= mnthly){
				dateLastEntry = Calendar.getInstance();
				dateLastEntry.setTimeInMillis(dateEntry.getTimeInMillis());
				dateLastEntry.set(Calendar.MONTH, dateEntry.get(Calendar.MONTH)+1);
				entries.put(new Integer(cmpPeriod), loanEntry);
				loanEntries.add(loanEntry);
			}
			
		}

		entries.put(new Integer(cmpPeriod), new LoanEntry(dateLastEntry, amortizedloanAmt,0,0,mnthly));
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
			setInterest((amortizedloanAmt * Math.pow((1+periodicInterestRate),numOfYears*12) - amortizedloanAmt)/(numOfYears*12));
			currloanAmt = amortizedloanAmt - getInterest();
		
			setPrincipal(monthly - (currloanAmt * periodicInterestRate));
			amortizedloanAmt -= getPrincipal();
			return new LoanEntry(dateEntry, getPrincipal(), getInterest(), amortizedloanAmt, monthly);
	}

	public String toString(){
		return super.toString();
	}
	public ArrayList<LoanEntry> getLoanEntries(){
		return loanEntries;
	}
	public void setLoanEntries(ArrayList<LoanEntry> ent){
		loanEntries = ent;
	}
	public HashMap<Integer, LoanEntry> getEntries(){
		return entries;
	}
	public void setEntries(HashMap<Integer, LoanEntry> ents){
		entries = ents;
	}
	public Double getPayoffAmount(double amt, String payoffOn){
		Calendar dateLastEntry = Calendar.getInstance();
		try{
			dateLastEntry.setTime(java.text.SimpleDateFormat.getDateInstance(java.text.DateFormat.SHORT, java.util.Locale.US).parse(payoffOn));
		}catch(java.text.ParseException pe){ pe.printStackTrace(); }
		Double payoffLoanAmt = 0.0;
		for(LoanEntry entry : entries.values()) {
			Calendar dtEntry = entry.getDateEntry();
			if(dtEntry.equals(dateLastEntry)){
				payoffLoanAmt = entry.getLoanAmount();
				break;
			}
		}
		if(payoffLoanAmt > 0)
			return payoffLoanAmt;
		else
			return -1.0;

	} 

}
