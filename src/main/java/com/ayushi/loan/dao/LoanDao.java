package com.ayushi.loan.dao;

import java.io.Serializable;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import com.ayushi.loan.Loan;
import java.util.List;
import com.ayushi.loan.exception.LoanAccessException;

import java.util.Collections;

public class LoanDao implements LendingDao {
	private SessionFactory sessionFactory;	
	
	public LoanDao (SessionFactory sessFactory){
		sessionFactory = sessFactory;
	}
	public void setSessionFactory(SessionFactory sessFactory){
		sessionFactory = sessFactory;
	}
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public Serializable insert(Loan o) throws LoanAccessException{
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
			session.saveOrUpdate(o);
			session.flush();
		}catch(DataAccessException dae){
			throw new LoanAccessException(dae);
		}
		tx.commit();
        	session.close();
		return o;
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void update(Object o) throws LoanAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.saveOrUpdate(o);
		}catch(DataAccessException dae){
			throw new LoanAccessException(dae);
		}
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void remove(Object o) throws LoanAccessException {
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.delete(o);
		}catch(DataAccessException dae){
			throw new LoanAccessException(dae);
		}
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Serializable> find(String query, Object[] objVals) throws LoanAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			  List list =  ht.find(query, objVals);
                          Collections.sort(list);
                              
                        return list;
		}catch(DataAccessException dae){
			throw new LoanAccessException(dae);
		}
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Object find(Class entityClass, Serializable o) throws LoanAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			return ht.get(entityClass, o);
		}catch(DataAccessException dae){
			throw new LoanAccessException(dae);
		}
	}	

}