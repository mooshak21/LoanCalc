package com.ayushi.loan.dao;

import java.io.Serializable;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import com.ayushi.loan.Loan;
import java.util.List;
import com.ayushi.loan.exception.LoanAccessException;

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
	public void insert(Object o) throws LoanAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.saveOrUpdate(o);
		}catch(DataAccessException dae){
			throw new LoanAccessException(dae);
		}
	}
	public void update(Object o) throws LoanAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.saveOrUpdate(o);
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