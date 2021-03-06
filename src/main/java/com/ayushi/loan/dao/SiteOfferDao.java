package com.ayushi.loan.dao;

import com.ayushi.loan.NewsObject;
import com.ayushi.loan.exception.LoanAccessException;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by agarima on 06-02-2018.
 */
public class SiteOfferDao extends HibernateDaoSupport {

    private SessionFactory sessionFactory;

    public SiteOfferDao(SessionFactory sessFactory) {
        sessionFactory = sessFactory;
    }

//    public void setSessionFactory(SessionFactory sessFactory) {
//        sessionFactory = sessFactory;
//    }

//    public SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }

    @Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
    public NewsObject insert(NewsObject o) throws LoanAccessException {
//		Session session = sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.saveOrUpdate(o);
//			session.flush();
        } catch (DataAccessException dae) {
            throw new LoanAccessException(dae);
        }
//		tx.commit();
//        	session.close();
        return o;
    }

    @Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
    public void update(Object o) throws LoanAccessException {
//        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        HibernateTemplate ht = getHibernateTemplate();
        try {
            ht.saveOrUpdate(o);
        } catch (DataAccessException dae) {
            throw new LoanAccessException(dae);
        }
    }

    @Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
    public void remove(Object o) throws LoanAccessException {
//        Session session = sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        Session session = sessionFactory.getCurrentSession();
        try {
            session.delete(o);
//            session.flush();
        } catch (DataAccessException dae) {
            throw new LoanAccessException(dae);
        }
//        tx.commit();
//        session.close();
    }

    @Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
    public List<NewsObject> find(String query, Object[] objVals) throws LoanAccessException {
//        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        HibernateTemplate ht = getHibernateTemplate();

        try {
            return (List<NewsObject>) ht.find(query, objVals);
        } catch (DataAccessException dae) {
            throw new LoanAccessException(dae);
        }
    }

    @Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
    public Object find(Class entityClass, Serializable o) throws LoanAccessException {
//        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        HibernateTemplate ht = getHibernateTemplate();

        try {
            return ht.get(entityClass, o);
        } catch (DataAccessException dae) {
            throw new LoanAccessException(dae);
        }
    }

}

