package com.ayushi.loan.service;

import com.ayushi.loan.LoanAgg;
import com.ayushi.loan.NewsObject;
import com.ayushi.loan.dao.SiteOfferDao;
import com.ayushi.loan.exception.LoanAccessException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by agarima on 06-02-2018.
 */
public class SiteOfferServiceImpl  implements SiteOfferService {
    private SiteOfferDao siteOfferDao;

    public SiteOfferServiceImpl(SiteOfferDao siteOfferDao){
        this.siteOfferDao = siteOfferDao;
    }

    public void setSiteOfferDao(SiteOfferDao siteOfferDao){
        this.siteOfferDao = siteOfferDao;
    }
    public SiteOfferDao getSiteOfferDao(){
        return siteOfferDao;
    }


    @Override
    public Serializable createNewsObject(NewsObject newsObject) throws LoanAccessException {
        return siteOfferDao.insert(newsObject);
    }

    @Override
    public NewsObject retrieveNewsObject(NewsObject newsObject) throws LoanAccessException {
        return (NewsObject) siteOfferDao.find(LoanAgg.class, newsObject);
    }

    @Override
    public void modifyNewsObject(NewsObject newsObject) throws LoanAccessException {
        siteOfferDao.update(newsObject);
    }

    @Override
    public void removeNewsObject(NewsObject newsObject) throws LoanAccessException {
        siteOfferDao.remove(newsObject);
    }

    @Override
    public List<NewsObject> findNewsObject(String query, Object[] objVals) throws LoanAccessException {
        return (List<NewsObject>) siteOfferDao.find(query, objVals);
    }
}