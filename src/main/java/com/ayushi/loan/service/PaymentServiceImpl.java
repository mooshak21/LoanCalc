package com.ayushi.loan.service;

import com.ayushi.loan.Loan;
import com.ayushi.loan.Payment;
import com.ayushi.loan.dao.PaymentDao;
import com.ayushi.loan.exception.PaymentProcessException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kundan on 3/5/2018.
 */
public class PaymentServiceImpl implements PaymentService {

    private PaymentDao paymentDao;

    public PaymentServiceImpl(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public PaymentDao getPaymentDao() {
        return paymentDao;
    }

    public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    public void createPayment(Payment payment) throws PaymentProcessException {
         paymentDao.insert(payment);
    }

    @Override
    public Payment retrievePayment(Long paymentId) throws PaymentProcessException {
        return (Payment) paymentDao.find(Payment.class,paymentId);
    }

    @Override
    public void modifyPayment(Payment payment) throws PaymentProcessException {
            paymentDao.update(payment);
    }

    @Override
    public void removePayment(Payment payment) throws PaymentProcessException {
          paymentDao.remove(payment);
    }

    @Override
    public List<Serializable> findPayment(String query, Object[] objVals) throws PaymentProcessException {
        return paymentDao.find(query,objVals);
    }
}
