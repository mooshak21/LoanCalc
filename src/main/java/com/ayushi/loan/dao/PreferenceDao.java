package com.ayushi.loan.dao;

import java.io.Serializable;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import com.ayushi.loan.Loan;
import java.util.List;
import com.ayushi.loan.exception.PreferenceAccessException;
import com.ayushi.loan.preferences.Preference;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
 

public class PreferenceDao extends HibernateDaoSupport{
	private SessionFactory sessionFactory;
	
	public PreferenceDao (SessionFactory sessFactory){
		sessionFactory = sessFactory;
	}
//	public void setSessionFactory(SessionFactory sessFactory){
//		sessionFactory = sessFactory;
//	}
//	public SessionFactory getSessionFactory(){
//		return sessionFactory;
//	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void insert(Preference o) throws PreferenceAccessException{
//		Session session = sessionFactory.openSession();
		Session session = sessionFactory.getCurrentSession();
//		Transaction tx = session.beginTransaction();
		try{
			session.saveOrUpdate(o);
//			session.flush();
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
//		tx.commit();
//        	session.close();
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void insert(Loan loan, List<Integer> prefIds) throws PreferenceAccessException{
//		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		HibernateTemplate ht = getHibernateTemplate();
		try{
			ht.saveOrUpdate(loan);
			ht.saveOrUpdate(prefIds);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void update(Preference o) throws PreferenceAccessException{
//		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		HibernateTemplate ht = getHibernateTemplate();
		try{
			ht.saveOrUpdate(o);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void remove(Preference o) throws PreferenceAccessException {
//		Session session = sessionFactory.openSession();
		Session session = sessionFactory.getCurrentSession();
//		Transaction tx = session.beginTransaction();
//		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		try{
			session.delete(o);
//			session.flush();
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
//		tx.commit();
//		session.close();
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Preference> find(String query, Object[] objVals) throws PreferenceAccessException{
//		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		HibernateTemplate ht = getHibernateTemplate();
		try{
			return (List<Preference>)ht.find(query, objVals);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Object find(Class entityClass, Serializable o) throws PreferenceAccessException{
//		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		HibernateTemplate ht = getHibernateTemplate();
		try{
			return ht.get(entityClass, o);
		}catch(DataAccessException dae){
			throw new PreferenceAccessException(dae);
		}
	}	

}