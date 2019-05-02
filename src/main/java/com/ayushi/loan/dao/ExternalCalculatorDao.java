package com.ayushi.loan.dao;

import com.ayushi.loan.EquityExternalCalculator;
import com.ayushi.loan.exception.LoanAccessException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ExternalCalculatorDao extends HibernateDaoSupport {

    private SessionFactory sessionFactory;

    public ExternalCalculatorDao(SessionFactory sessFactory) {
        sessionFactory = sessFactory;
    }

    @Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
    public EquityExternalCalculator insert(EquityExternalCalculator o) throws LoanAccessException {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.saveOrUpdate(o);
        } catch (DataAccessException dae) {
            throw new LoanAccessException(dae);
        }
        return o;
    }

    @Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
    public List<EquityExternalCalculator> find(String query, Object[] objVals) throws LoanAccessException {
//        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        HibernateTemplate ht = getHibernateTemplate();

        try {
            return (List<EquityExternalCalculator>) ht.find(query, objVals);
        } catch (DataAccessException dae) {
            throw new LoanAccessException(dae);
        }
    }
}
