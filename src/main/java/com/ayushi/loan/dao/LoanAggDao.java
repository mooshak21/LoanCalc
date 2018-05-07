package com.ayushi.loan.dao;

import com.ayushi.loan.exception.LoanAccessException;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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

	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
    public void update(Object o) throws LoanAccessException {
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
    }

	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
	public Serializable insert(Object o) throws LoanAccessException{
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
		return null;
	}
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
    public void remove(Object o) throws LoanAccessException {
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try{
            ht.delete(o);
        }catch(DataAccessException dae){
            throw new LoanAccessException(dae);
        }
    }
	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
    public List<Serializable> find(String query, Object[] objVals) throws LoanAccessException{
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try{
            return (List<Serializable>)ht.find(query, objVals);
        }catch(DataAccessException dae){
            throw new LoanAccessException(dae);
        }
    }
 	@Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
   public Object find(Class entityClass, Serializable o) throws LoanAccessException{
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try{
            return ht.get(entityClass, o);
        }catch(DataAccessException dae){
            throw new LoanAccessException(dae);
        }
    }
}

