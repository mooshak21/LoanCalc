package com.ayushi.loan.dao;

import java.io.Serializable;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import com.ayushi.loan.Loan;
import java.util.List;
import com.ayushi.loan.exception.PreferenceAccessException;

public class PreferenceDao implements LendingDao {
	private SessionFactory sessionFactory;	
	
	public PreferenceDao (SessionFactory sessFactory){
		sessionFactory = sessFactory;
	}
	public void setSessionFactory(SessionFactory sessFactory){
		sessionFactory = sessFactory;
	}
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	public void insert(Object o) throws PreferenceAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.saveOrUpdate(o);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}
	public void update(Object o) throws PreferenceAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.saveOrUpdate(o);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}
	public void remove(Object o) throws PreferenceAccessException {
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.delete(o);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}
	public List<Serializable> find(String query, Object[] objVals) throws PreferenceAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			return (List<Serializable>)ht.find(query, objVals);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}
	public Object find(Class entityClass, Serializable o) throws PreferenceAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			return ht.get(entityClass, o);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}	

}