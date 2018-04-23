package com.ayushi.loan.dao;

import java.io.Serializable;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import com.ayushi.loan.Loan;
import java.util.List;
import com.ayushi.loan.exception.PreferenceAccessException;
import com.ayushi.loan.preferences.Preference;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
 

public class PreferenceDao {
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
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void insert(Object o) throws PreferenceAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.saveOrUpdate(o);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}

	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void insert(Loan loan, List<Integer> prefIds) throws PreferenceAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.saveOrUpdate(loan);
			ht.saveOrUpdate(prefIds);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void update(Object o) throws PreferenceAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.saveOrUpdate(o);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void remove(Object o) throws PreferenceAccessException {
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			ht.delete(o);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Preference> find(String query, Object[] objVals) throws PreferenceAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			return (List<Preference>)ht.find(query, objVals);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Object find(Class entityClass, Serializable o) throws PreferenceAccessException{
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			return ht.get(entityClass, o);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}	

}