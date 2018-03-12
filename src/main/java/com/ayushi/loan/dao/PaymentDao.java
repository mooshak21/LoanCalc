package com.ayushi.loan.dao;

import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.exception.PaymentProcessException;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PaymentDao  {
	private SessionFactory sessionFactory;

	public PaymentDao(SessionFactory sessFactory){
		sessionFactory = sessFactory;
	}

	public void setSessionFactory(SessionFactory sessFactory){
		sessionFactory = sessFactory;
	}
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	public Serializable insert(Object o) throws PaymentProcessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.save(o);
		}catch(DataAccessException dae){
			throw new PaymentProcessException(dae);
		}
        return null;
    }
	public void update(Object o) throws PaymentProcessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.saveOrUpdate(o);
		}catch(DataAccessException dae){
			throw new PaymentProcessException(dae);
		}
	}
	public void remove(Object o) throws PaymentProcessException {
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.delete(o);
		}catch(DataAccessException dae){
			throw new PaymentProcessException(dae);
		}
	}
	public List<Serializable> find(String query, Object[] objVals) throws PaymentProcessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			  List list =  ht.find(query, objVals);
                          Collections.sort(list);
                              
                        return list;
		}catch(DataAccessException dae){
			throw new PaymentProcessException(dae);
		}
	}
	public Object find(Class entityClass, Serializable o) throws PaymentProcessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			return ht.get(entityClass, o);
		}catch(DataAccessException dae){
			throw new PaymentProcessException(dae);
		}
	}	

}