package com.ayushi.loan.dao;

import com.ayushi.loan.exception.LoanAccessException;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class LoanAggDao implements LendingDao {

    private SessionFactory sessionFactory;

    public LoanAggDao (SessionFactory sessFactory){
        sessionFactory = sessFactory;
    }
    public void setSessionFactory(SessionFactory sessFactory){
        sessionFactory = sessFactory;
    }
    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public void update(Object o) throws LoanAccessException {
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try{
            ht.saveOrUpdate(o);
        }catch(DataAccessException dae){
            throw new LoanAccessException(dae);
        }
    }

    public Serializable insert(Object o) throws LoanAccessException{
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try{
            return ht.save(o);
        }catch(DataAccessException dae){
            throw new LoanAccessException(dae);
        }
    }

    public void remove(Object o) throws LoanAccessException {
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try{
            ht.delete(o);
        }catch(DataAccessException dae){
            throw new LoanAccessException(dae);
        }
    }
    public List<Serializable> find(String query, Object[] objVals) throws LoanAccessException{
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try{
            return (List<Serializable>)ht.find(query, objVals);
        }catch(DataAccessException dae){
            throw new LoanAccessException(dae);
        }
    }
    public Object find(Class entityClass, Serializable o) throws LoanAccessException{
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try{
            return ht.get(entityClass, o);
        }catch(DataAccessException dae){
            throw new LoanAccessException(dae);
        }
    }
}

