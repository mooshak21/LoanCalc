package com.ayushi.loan.service;

import com.ayushi.loan.EquityExternalCalculator;
import com.ayushi.loan.dao.ExternalCalculatorDao;
import com.ayushi.loan.exception.LoanAccessException;

import java.io.Serializable;
import java.util.List;

public class EquityExternalCalculatorServiceImpl implements EquityExternalCalculatorService {

    private ExternalCalculatorDao externalCalculatorDao;

    public EquityExternalCalculatorServiceImpl(ExternalCalculatorDao externalCalculatorDao){
        this.externalCalculatorDao = externalCalculatorDao;
    }

    public void setExternalCalculatorDao(ExternalCalculatorDao externalCalculatorDao){
        this.externalCalculatorDao = externalCalculatorDao;
    }
    public ExternalCalculatorDao getExternalCalculatorDao(){
        return externalCalculatorDao;
    }
    @Override
    public EquityExternalCalculator createEquityExternalCalculator(EquityExternalCalculator equityExternalCalculator) throws LoanAccessException {
        return externalCalculatorDao.insert(equityExternalCalculator);
    }

    @Override
    public List<EquityExternalCalculator> findEquityExternalCalculator(String query, Object[] objVals) throws LoanAccessException {
        return (List<EquityExternalCalculator>) externalCalculatorDao.find(query, objVals);
    }

    @Override
    public EquityExternalCalculator modifyEquityExternalCalculator(EquityExternalCalculator equityExternalCalculator) throws LoanAccessException {
        return externalCalculatorDao.insert(equityExternalCalculator);
    }
}
