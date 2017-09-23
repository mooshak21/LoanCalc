package com.ayushi.loan.service;

import com.ayushi.loan.LoanRelationship;
import com.ayushi.loan.dao.LoanRelationshipDao;
import com.ayushi.loan.exception.LoanAccessException;

import java.io.Serializable;
import java.util.List;

public class LoanRelationshipServiceImpl implements LoanRelationshipService {

    private LoanRelationshipDao loanRelationshipDao;

    public LoanRelationshipServiceImpl(LoanRelationshipDao loanRelationshipDao){
        this.loanRelationshipDao = loanRelationshipDao;
    }

    public void setLoanRelationshipDao(LoanRelationshipDao loanRelationshipDao){
        this.loanRelationshipDao = loanRelationshipDao;
    }
    public LoanRelationshipDao getLoanRelationshipDao(){
        return loanRelationshipDao;
    }

    @Override
    public Serializable createLoanRelation(LoanRelationship loanRelationship) throws LoanAccessException {
         loanRelationshipDao.insert(loanRelationship);
         return null;

    }

    @Override
    public LoanRelationship retrieveLoanRelation(LoanRelationship loanRelationship) throws LoanAccessException {
        return (LoanRelationship)loanRelationshipDao.find(LoanRelationship.class, loanRelationship);
    }

    @Override
    public void modifyLoanRelation(LoanRelationship loanRelationship) throws LoanAccessException {
        loanRelationshipDao.update(loanRelationship);
    }

    @Override
    public void removeLoanRelation(LoanRelationship loanRelationship) throws LoanAccessException {
        loanRelationshipDao.remove(loanRelationship);
    }

    @Override
    public List<Serializable> findLoanRelation(String query, Object[] objVals) throws LoanAccessException {
        return (List<Serializable>) loanRelationshipDao.find(query, objVals);
    }

    @Override
    public List<Serializable> findLoanRelation(String query) throws LoanAccessException {
        return (List<Serializable>) loanRelationshipDao.find(query);
    }
}
