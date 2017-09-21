package com.ayushi.loan.service;

import com.ayushi.loan.AggregationSummary;
import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.Loan;
import com.ayushi.loan.AmortizedLoan;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
			AmortizedLoan loanObject = restTemplate.getForObject("https://ayushiloancalculatorappws.herokuapp.com/amortizeloan?airVal=" + 						airVal + "&lender=" + lender + "&loanAmt=" + loanAmt + "&state=" + state + "&numOfYears=" + numOfYears + 					"&amortizeOn=" + amortizedOn, AmortizedLoan.class);
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

	public AggregationSummary aggregationSummary(List<Loan> loan, Calendar startDate) throws LoanAccessException, ParseException {
		AggregationSummary aggregationSummary = new AggregationSummary();
		Double totalAmount = 0.0;
		Double monthlyAmount = 0.0;
		Double amountPaid = 0.0;
		Double remainingAmount = 0.0;
		Double remainingPercent = 0.0;
		int maximumNumOfYear = 0;
		Calendar payoffDate = Calendar.getInstance();
		Calendar todayDate = Calendar.getInstance();
		java.util.Calendar calToday = java.util.Calendar.getInstance();
		String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		todayDate.setTime(formatter.parse(calTodayStr));
		long end = todayDate.getTimeInMillis();
		long start = startDate.getTimeInMillis();

		Long numberOfDays = TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
		if (startDate.after(todayDate) || startDate.equals(todayDate)) {
			for (int i = 0; i < loan.size(); i++) {
				totalAmount += loan.get(i).getAmount();
				if (maximumNumOfYear < loan.get(i).getNumberOfYears()) {
					maximumNumOfYear = loan.get(i).getNumberOfYears();
				}
			}
			remainingAmount = totalAmount;
			remainingPercent = (remainingAmount / totalAmount) * 100;
		} else if (startDate.before(todayDate)) {
			for (int i = 0; i < loan.size(); i++) {
				if ((loan.get(i).getNumberOfYears() * 365) > numberOfDays) {
					totalAmount += loan.get(i).getAmount();
					monthlyAmount += loan.get(i).getMonthly();
				}
				if (maximumNumOfYear < loan.get(i).getNumberOfYears()) {
					maximumNumOfYear = loan.get(i).getNumberOfYears();
				}
			}
			amountPaid = (monthlyAmount / 30) * numberOfDays;
			remainingAmount = totalAmount - amountPaid;
			remainingPercent = (remainingAmount / totalAmount) * 100;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(startDate.getTime());
		c.add(Calendar.YEAR, maximumNumOfYear);
		payoffDate = c;
		aggregationSummary.setTotalAmount(totalAmount);
		aggregationSummary.setAmountPaid(amountPaid);
		aggregationSummary.setMonthlyAmount(monthlyAmount);
		aggregationSummary.setRemainingAmount(remainingAmount);
		aggregationSummary.setRemainingPercent(remainingPercent);
		aggregationSummary.setStartDate(startDate);
		aggregationSummary.setPayoffDate(payoffDate);
		aggregationSummary.setMaximumNumOfYear(maximumNumOfYear);
		return aggregationSummary;
	}
}
