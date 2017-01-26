package com.ayushi.loan.dao;

import java.io.Serializable;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import org.springframework.orm.hibernate.HibernateTemplate;
import net.sf.hibernate.SessionFactory;
import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.Loan;
import java.util.List;

public class LoanDao extends HibernateDaoSupport implements LendingDao {
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
		HibernateTemplate ht = this.createHibernateTemplate(sessionFactory);
		try{
			ht.saveOrUpdate(o);
		}catch(DataAccessException dae){
			throw new LoanAccessException(dae);
		}
	}
	public void update(Object o) throws LoanAccessException{
		HibernateTemplate ht = this.createHibernateTemplate(sessionFactory);
		try{
			ht.saveOrUpdate(o);
		}catch(DataAccessException dae){
			throw new LoanAccessException(dae);
		}
	}
	public void remove(Object o) throws LoanAccessException {
		HibernateTemplate ht = this.createHibernateTemplate(sessionFactory);
		try{
			ht.delete(o);
		}catch(DataAccessException dae){
			throw new LoanAccessException(dae);
		}
	}
	public List<Loan> find(String query, Object[] objVals) throws LoanAccessException{
		HibernateTemplate ht = this.createHibernateTemplate(sessionFactory);
		try{
			return (List<Loan>)ht.find(query, objVals);
		}catch(DataAccessException dae){
			throw new LoanAccessException(dae);
		}
	}
	public Object find(Object o) throws LoanAccessException{
		HibernateTemplate ht = this.createHibernateTemplate(sessionFactory);
		try{
			return ht.get(o);
		}catch(DataAccessException dae){
			throw new LoanAccessException(dae);
		}
	}	

}