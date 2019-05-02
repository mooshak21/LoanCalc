package com.ayushi.loan.service;

import com.ayushi.loan.Equity;
import com.ayushi.loan.exception.LoanAccessException;

/**
 * @author garima.agarwal
 * 4/23/2019 1:08 PM
 */
public interface EquityService {
    public Equity createEquity(Equity equity) throws LoanAccessException;
}
