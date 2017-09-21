package com.ayushi.loan.service;

import com.ayushi.loan.Loan;
import com.ayushi.loan.LoanAgg;
import com.ayushi.loan.LoanRelationship;
import com.ayushi.loan.exception.LoanAccessException;

import java.io.Serializable;
import java.util.List;

public interface LoanRelationshipService {
	public void createLoanRelation(LoanRelationship loanRelationship) throws LoanAccessException;
	public LoanRelationship retrieveLoanRelation(LoanRelationship loanRelationship) throws LoanAccessException;
	public void modifyLoanRelation(LoanRelationship loanRelationship) throws LoanAccessException;
	public void removeLoanRelation(LoanRelationship loanRelationship) throws LoanAccessException;
	public List<Serializable> findLoanRelation(String query, Object[] objVals) throws LoanAccessException;
	public List<Serializable> findLoanRelation(String query) throws LoanAccessException;
}