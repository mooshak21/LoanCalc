package com.ayushi.loan.exception;

import org.springframework.dao.DataAccessException;

public class PreferenceAccessException extends Exception {
	public PreferenceAccessException(DataAccessException dae){
		super(dae);
	}
}