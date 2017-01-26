package com.ayushi.loan.service;

import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.Loan;
import com.ayushi.loan.AmortizedLoan;

public interface LendingWebService {
	public Loan calculateLoan(Loan loan) throws LoanAccessException;
	public AmortizedLoan amortizeLoan(Loan loan) throws LoanAccessException;
	public AmortizedLoan payoffLoan(Loan loan) throws LoanAccessException;
}