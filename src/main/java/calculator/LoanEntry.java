package calculator;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LoanEntry implements Serializable{
	public static final long serialVersionUId = 1L;
	private Calendar dateEntry;
	private double principal, interest;

	public LoanEntry(){
		principal = 0.0;
		interest = 0.0;
		dateEntry = GregorianCalendar.getInstance();
	}
	public LoanEntry(Calendar dtEntry, double princ, double intrst){
		principal = princ;
		interest = intrst;
		dateEntry = dtEntry;
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

	
}
