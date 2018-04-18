package com.ayushi.loan.service;

import com.ayushi.loan.AggregationSummary;
import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.Loan;
import com.ayushi.loan.AmortizedLoan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
		String region = loan.getRegion();
		String numOfYears = new Integer(loan.getNumberOfYears()).toString();
		String loanType = loan.getLoanType();
		if(loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
				   && lender != null && !lender.equals("") && state != null && !state.equals("") && region != null && !region.equals("")
				   && numOfYears != null && !numOfYears.equals("")&& loanType != null && !loanType.equals("")){
			RestTemplate restTemplate = new RestTemplate();
			Loan loanObject = restTemplate.getForObject("https://ayushiloancalculatorappws.herokuapp.com/calculateloan?airVal=" + airVal + 						"&lender=" + lender + "&loanAmt=" + loanAmt + "&state=" + state + "&numOfYears=" + numOfYears+ "&loanType=" + loanType, Loan.class);
			return loanObject;
		}else{
			return null;
		}
	}
	public AmortizedLoan amortizeLoan(Loan loan, String amortizedOn) throws LoanAccessException{
		String airVal = new Double(loan.getAPR()).toString();
		String lender = loan.getLender();
		String loanAmt = new Double(loan.getAmount()).toString();
		String region = loan.getRegion();
		String state = loan.getState();
		String numOfYears = new Integer(loan.getNumberOfYears()).toString(); 
		if(loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
				   && lender != null && !lender.equals("") && state != null && !state.equals("")
				&& region != null && !region.equals("") && numOfYears != null && !numOfYears.equals("")){
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
		if(loan != null && startDate!=null){
		/*	Gson gson = new Gson();
			// convert your list to json
			String jsonCartList = gson.toJson(loan);*/
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			String str=formatter.format(startDate.getTime());
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
			//restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			ObjectMapper mapper = new ObjectMapper();

			AggregationSummary aggregationSummary = null;
			try {
				org.apache.commons.codec.net.URLCodec codec = new org.apache.commons.codec.net.URLCodec();

				//aggregationSummary = restTemplate.exchange("http://localhost:9999/aggregationSummary?startDate=" + str + "&loan="+ codec.encode(mapper.writeValueAsString(loan)), HttpMethod.GET, requestEntity, new ParameterizedTypeReference<AggregationSummary>() {});
				aggregationSummary = restTemplate.postForObject("https://ayushiloancalculatorappws.herokuapp.com/aggregationSummary?startDate=" + str, codec.encode(mapper.writeValueAsString(loan)), AggregationSummary.class);
				return aggregationSummary;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else{
			return null;
		}
	}
}
