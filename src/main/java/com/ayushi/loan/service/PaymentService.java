package com.ayushi.loan.service;

import com.ayushi.loan.Loan;
import com.ayushi.loan.PayPalModel;
import com.ayushi.loan.Payment;
import com.ayushi.loan.exception.PaymentProcessException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kundan on 3/5/2018.
 */
public interface PaymentService {

	public void createPayment(Payment payment) throws PaymentProcessException;

	public Payment retrievePayment(Long paymentId) throws PaymentProcessException;

	public void modifyPayment(Payment payment) throws PaymentProcessException;

	public void removePayment(Payment payment) throws PaymentProcessException;

	public List<Serializable> findPayment(String query, Object[] objVals) throws PaymentProcessException;

	// Vatsal Code
	public String createPayment(double amount);

	public String completePayment(PayPalModel payPalModel);

}
