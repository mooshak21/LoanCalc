package com.ayushi.loan.exception;

public class LoanAccessException extends Exception {
	public LoanAccessException(DataAccessException dae){
		super(dae);
	}
}