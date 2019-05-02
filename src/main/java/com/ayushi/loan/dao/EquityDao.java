package com.ayushi.loan.dao;

import com.ayushi.loan.Equity;
import com.ayushi.loan.exception.LoanAccessException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author garima.agarwal
 * 4/23/2019 10:36 PM
 */
public class EquityDao extends HibernateDaoSupport {

    private SessionFactory sessionFactory;

    public EquityDao(SessionFactory sessFactory) {
        sessionFactory = sessFactory;
    }

    @Transactional(value = "txManager", propagation = Propagation.REQUIRED, readOnly = false)
    public Equity insert(Equity o) throws LoanAccessException {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.saveOrUpdate(o);
        } catch (DataAccessException dae) {
            throw new LoanAccessException(dae);
        }
        return o;
    }
}
