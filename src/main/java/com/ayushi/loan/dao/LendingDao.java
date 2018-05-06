package com.ayushi.loan.dao;

import com.ayushi.loan.exception.LoanAccessException;
import java.util.List;
import java.lang.Class;
import java.io.Serializable;

public interface LendingDao {
	public void update(Object o) throws LoanAccessException;
	public void insert(Object o) throws LoanAccessException;
	public void remove(Object o) throws LoanAccessException;
	public List<Serializable> find(String query, Object[] objVals) throws LoanAccessException;
	public Object find(Class entityClass, Serializable o) throws LoanAccessException;
}