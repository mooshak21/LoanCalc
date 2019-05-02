package com.ayushi.loan.service;

import com.ayushi.loan.EquityExternalCalculator;
import com.ayushi.loan.exception.LoanAccessException;

import java.io.Serializable;
import java.util.List;

public interface EquityExternalCalculatorService {
    public Serializable createEquityExternalCalculator(EquityExternalCalculator equityExternalCalculator) throws LoanAccessException;
    public List<EquityExternalCalculator> findEquityExternalCalculator(String query, Object[] objVals) throws LoanAccessException;
    public EquityExternalCalculator modifyEquityExternalCalculator(EquityExternalCalculator equityExternalCalculator) throws LoanAccessException;
}
