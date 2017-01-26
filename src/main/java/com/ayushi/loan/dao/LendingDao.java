package com.ayushi.loan.dao;

import com.ayushi.loan.exception.LoanAccessException;

public interface LendingDao {
	public void update(Object o) throws LoanAccessException;
	public void insert(Object o) throws LoanAccessException;
	public void remove(Object o) throws LoanAccessException;
	public List find(String query, Object[] objVals) throws LoanAccessException;
	public Object find(Object o) throws LoanAccessException;
}