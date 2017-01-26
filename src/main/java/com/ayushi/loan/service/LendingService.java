package com.ayushi.loan.service;

import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.Loan;
import java.util.List;
import java.io.Serializable;

public interface LendingService {
	public void createLoan(Loan loan) throws LoanAccessException;
	public Loan retrieveLoan(Loan loan) throws LoanAccessException;
	public void modifyLoan(Loan loan) throws LoanAccessException;
	public void removeLoan(Loan loan) throws LoanAccessException;
	public List<Serializable> findLoan(String query, Object[] objVals) throws LoanAccessException;
}