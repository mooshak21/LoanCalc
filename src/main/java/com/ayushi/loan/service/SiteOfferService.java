package com.ayushi.loan.service;

import com.ayushi.loan.NewsObject;
import com.ayushi.loan.exception.LoanAccessException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by agarima on 06-02-2018.
 */
public interface SiteOfferService {
    public Serializable createNewsObject(NewsObject newsObject) throws LoanAccessException;
    public NewsObject retrieveNewsObject(NewsObject newsObject) throws LoanAccessException;
    public NewsObject modifyNewsObject(NewsObject newsObject) throws LoanAccessException;
    public void removeNewsObject(NewsObject newsObject) throws LoanAccessException;
    public List<NewsObject> findNewsObject(String query, Object[] objVals) throws LoanAccessException;
}
