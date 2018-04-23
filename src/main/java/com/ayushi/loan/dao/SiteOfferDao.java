package com.ayushi.loan.dao;

import com.ayushi.loan.NewsObject;
import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.exception.PreferenceAccessException;
import com.ayushi.loan.preferences.Preference;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by agarima on 06-02-2018.
 */
public class SiteOfferDao {

    private SessionFactory sessionFactory;

    public SiteOfferDao(SessionFactory sessFactory) {
        sessionFactory = sessFactory;
    }

    public void setSessionFactory(SessionFactory sessFactory) {
        sessionFactory = sessFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Serializable insert(Object o) throws LoanAccessException {
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try {
            ht.saveOrUpdate(o);
        } catch (DataAccessException dae) {
            throw new LoanAccessException(dae);
        }
        return null;
    }

    public void update(Object o) throws LoanAccessException {
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try {
            ht.saveOrUpdate(o);
        } catch (DataAccessException dae) {
            throw new LoanAccessException(dae);
        }
    }

    public void remove(Object o) throws LoanAccessException {
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try {
            ht.delete(o);
        } catch (DataAccessException dae) {
            throw new LoanAccessException(dae);
        }
    }

    public List<NewsObject> find(String query, Object[] objVals) throws LoanAccessException{
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try{
            return (List<NewsObject>)ht.find(query, objVals);
        }catch(DataAccessException dae){
            throw new LoanAccessException(dae);
        }
    }
    public Object find(Class entityClass, Serializable o) throws LoanAccessException {
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try {
            return ht.get(entityClass, o);
        } catch (DataAccessException dae) {
            throw new LoanAccessException(dae);
        }
    }

}

