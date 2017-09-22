package com.ayushi.loan.service;

import com.ayushi.loan.LoanAgg;
import com.ayushi.loan.exception.LoanAccessException;

import java.io.Serializable;
import java.util.List;

public interface LoanAggService {
	public Serializable createLoanAgg(LoanAgg loanAgg) throws LoanAccessException;
	public LoanAgg retrieveLoanAgg(LoanAgg loanAgg) throws LoanAccessException;
	public void modifyLoanAgg(LoanAgg loanAgg) throws LoanAccessException;
	public void removeLoanAgg(LoanAgg loanAgg) throws LoanAccessException;
	public List<Serializable> findLoanAgg(String query, Object[] objVals) throws LoanAccessException;
}