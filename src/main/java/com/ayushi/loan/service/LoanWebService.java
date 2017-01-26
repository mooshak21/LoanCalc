package com.ayushi.loan.service;

import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.Loan;
import com.ayushi.loan.AmortizedLoan;
import org.springframework.web.client.RestTemplate;

public class LoanWebService implements LendingWebService {
	public Loan calculateLoan(Loan loan) throws LoanAccessException{
			RestTemplate restTemplate = new RestTemplate();
			Loan loanObject = restTemplate.getForObject("https://ayushiloancalculatorappws.herokuapp.com/calculateloan?airVal=" + airVal + 						"&lender=" + lender + "&loanAmt=" + loanAmt + "&state=" + state + "&numOfYears=" + numOfYears, Loan.class);
			
			return loanObject;
	}
	public AmortizedLoan amortizeLoan(Loan loan, String amortizedOn) throws LoanAccessException{
			RestTemplate restTemplate = new RestTemplate();
			AmortizedLoan loanObject = restTemplate.getForObject("https://ayushiloancalculatorappws.herokuapp.com/amortizeloan?airVal=" + 						airVal + "&lender=" + lender + "&loanAmt=" + loanAmt + "&state=" + state + "&numOfYears=" + numOfYears + 					"&amortizedOn=" + amoritizedOn, AmortizedLoan.class);
	
			return loanObject;
	}
	public AmortizedLoan payoffLoan(Loan loan) throws LoanAccessException{
		return null;
	}
}