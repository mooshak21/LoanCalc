package com.ayushi.loan.service;

import com.ayushi.loan.exception.LoanAccessException;

public interface LendingService {
	public void createLoan(Loan loan) throws LoanAccessException;
	public Loan retrieveLoan(Loan loan) throws LoanAccessException;
	public void modifyLoan(Loan loan) throws LoanAccessException;
	public void removeLoan(Loan loan) throws LoanAccessException;
	public List<Loan> findLoan(String query) throws LoanAccessException;
}