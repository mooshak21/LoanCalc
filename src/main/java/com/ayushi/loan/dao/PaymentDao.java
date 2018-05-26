package com.ayushi.loan.dao;

import com.ayushi.loan.Payment;
import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.exception.PaymentProcessException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

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
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void insert(Payment o) throws PaymentProcessException{
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
			session.saveOrUpdate(o);
			session.flush();
		}catch(DataAccessException dae){
			throw new PaymentProcessException(dae);
		}
		tx.commit();
        	session.close();
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void update(Object o) throws PaymentProcessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.saveOrUpdate(o);
		}catch(DataAccessException dae){
			throw new PaymentProcessException(dae);
		}
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void remove(Object o) throws PaymentProcessException {
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.delete(o);
		}catch(DataAccessException dae){
			throw new PaymentProcessException(dae);
		}
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
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
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Object find(Class entityClass, Serializable o) throws PaymentProcessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			return ht.get(entityClass, o);
		}catch(DataAccessException dae){
			throw new PaymentProcessException(dae);
		}
	}	

}