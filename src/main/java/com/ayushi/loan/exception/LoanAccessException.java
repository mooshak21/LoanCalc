package com.ayushi.loan.exception;

import org.springframework.dao.DataAccessException;

public class LoanAccessException extends Exception {
	public LoanAccessException(DataAccessException dae){
		super(dae);
	}
}