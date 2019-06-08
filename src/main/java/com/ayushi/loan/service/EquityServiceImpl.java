package com.ayushi.loan.service;

import com.ayushi.loan.Equity;
import com.ayushi.loan.dao.EquityDao;
import com.ayushi.loan.exception.LoanAccessException;

import java.io.Serializable;
import java.util.List;

/**
 * @author garima.agarwal
 * 4/23/2019 1:09 PM
 */
public class EquityServiceImpl implements EquityService {

    private EquityDao equityDao;

    public EquityDao getEquityDao() {
        return equityDao;
    }

    public void setEquityDao(EquityDao equityDao) {
        this.equityDao = equityDao;
    }

    @Override
    public Equity createEquity(Equity equity) throws LoanAccessException {
        return  equityDao.insert(equity);
    }

    @Override
    public List<Serializable> findEquity(String query, Object[] objVals) throws LoanAccessException {
        return equityDao.findEquityHistory(query,objVals);
    }
}
