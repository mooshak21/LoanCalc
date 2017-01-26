package com.ayushi.loan.service;

import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.Loan;
import com.ayushi.loan.AmortizedLoan;
import org.springframework.web.client.RestTemplate;

public class LoanWebService implements LendingWebService {
	public Loan calculateLoan(Loan loan) throws LoanAccessException{
		String airVal = new Double(loan.getAPR()).toString();
		String lender = loan.getLender();
		String loanAmt = new Double(loan.getAmount()).toString();
		String state = loan.getState();
		String numOfYears = new Integer(loan.getNumberOfYears()).toString(); 
		if(loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
				   && lender != null && !lender.equals("") && state != null && !state.equals("")
				   && numOfYears != null && !numOfYears.equals("")){
			RestTemplate restTemplate = new RestTemplate();
			Loan loanObject = restTemplate.getForObject("https://ayushiloancalculatorappws.herokuapp.com/calculateloan?airVal=" + airVal + 						"&lender=" + lender + "&loanAmt=" + loanAmt + "&state=" + state + "&numOfYears=" + numOfYears, Loan.class);
		
			return loanObject;
		}else{
			return null;
		}
	}
	public AmortizedLoan amortizeLoan(Loan loan, String amortizedOn) throws LoanAccessException{
		String airVal = new Double(loan.getAPR()).toString();
		String lender = loan.getLender();
		String loanAmt = new Double(loan.getAmount()).toString();
		String state = loan.getState();
		String numOfYears = new Integer(loan.getNumberOfYears()).toString(); 
		if(loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
				   && lender != null && !lender.equals("") && state != null && !state.equals("")
				   && numOfYears != null && !numOfYears.equals("")){
			RestTemplate restTemplate = new RestTemplate();
			AmortizedLoan loanObject = restTemplate.getForObject("https://ayushiloancalculatorappws.herokuapp.com/amortizeloan?airVal=" + 						airVal + "&lender=" + lender + "&loanAmt=" + loanAmt + "&state=" + state + "&numOfYears=" + numOfYears + 					"&amortizedOn=" + amortizedOn, AmortizedLoan.class);
			return loanObject;
		}else{
			return null;
		}
	}
	public AmortizedLoan payoffLoan(Loan loan, String amortizedOn, String payoffOn) throws LoanAccessException{
		String airVal = new Double(loan.getAPR()).toString();
		String lender = loan.getLender();
		String loanAmt = new Double(loan.getAmount()).toString();
		String state = loan.getState();
		String numOfYears = new Integer(loan.getNumberOfYears()).toString(); 
		if(loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
				   && lender != null && !lender.equals("") && state != null && !state.equals("")
				   && numOfYears != null && !numOfYears.equals("")){
			RestTemplate restTemplate = new RestTemplate();
			AmortizedLoan loanObject = restTemplate.getForObject("https://ayushiloancalculatorappws.herokuapp.com/amortizeloan?airVal=" + 						airVal + "&lender=" + lender + "&loanAmt=" + loanAmt + "&state=" + state + "&numOfYears=" + numOfYears + 					"&amortizedOn=" + amortizedOn, AmortizedLoan.class);
			return loanObject;
		}else{
			return null;
		}
	}
}