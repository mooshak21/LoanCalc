package com.ayushi.loan.service;

import com.ayushi.loan.dao.LoanDao;
import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.Loan;
import java.util.List;
import java.io.Serializable;

public class LoanService {
	private LoanDao loanDao;

	public LoanService(LoanDao loanDao){
		this.loanDao = loanDao;
	}
	public void setLoanDao(LoanDao loanDao){
		this.loanDao = loanDao;
	}
	public LoanDao getLoanDao(){
		return loanDao;
	}
	public Loan createLoan(Loan loan) throws LoanAccessException {
		return loanDao.insert(loan);
	}
	public Loan retrieveLoan(Loan loan) throws LoanAccessException {
		return (Loan)loanDao.find(Loan.class, loan);
	}
	public void modifyLoan(Loan loan) throws LoanAccessException {
		loanDao.update(loan);
	}
	public void removeLoan(Serializable loan) throws LoanAccessException {
		loanDao.remove(loan);
	}
	public List<Serializable> findLoan(String query, Object[] objVals) throws LoanAccessException {
		return (List<Serializable>) loanDao.find(query, objVals);
	}
}
