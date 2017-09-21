package com.ayushi.loan.service;

import com.ayushi.loan.Loan;
import com.ayushi.loan.LoanAgg;
import com.ayushi.loan.dao.LoanAggDao;
import com.ayushi.loan.dao.LoanDao;
import com.ayushi.loan.exception.LoanAccessException;

import java.io.Serializable;
import java.util.List;

public class LoanAggServiceImpl implements LoanAggService {
	private LoanAggDao loanAggDao;

	public LoanAggServiceImpl(LoanAggDao loanAggDao){
		this.loanAggDao = loanAggDao;
	}

	public void setLoanAggDao(LoanAggDao loanAggDao){
		this.loanAggDao = loanAggDao;
	}
	public LoanAggDao getLoanAggDao(){
		return loanAggDao;
	}

	@Override
	public void createLoanAgg(LoanAgg loanAgg) throws LoanAccessException {
		loanAggDao.insert(loanAgg);
	}

	@Override
	public LoanAgg retrieveLoanAgg(LoanAgg loanAgg) throws LoanAccessException {
		return (LoanAgg)loanAggDao.find(LoanAgg.class, loanAgg);
	}

	@Override
	public void modifyLoanAgg(LoanAgg loanAgg) throws LoanAccessException {
		loanAggDao.update(loanAgg);
	}

	@Override
	public void removeLoanAgg(LoanAgg loanAgg) throws LoanAccessException {
		loanAggDao.remove(loanAgg);
	}

	@Override
	public List<Serializable> findLoanAgg(String query, Object[] objVals) throws LoanAccessException {
		return (List<Serializable>) loanAggDao.find(query, objVals);
	}
}
