package com.ayushi.loan.service;

import com.ayushi.loan.Loan;
import com.ayushi.loan.PayPalModel;
import com.ayushi.loan.Payment;
import com.ayushi.loan.dao.PaymentDao;
import com.ayushi.loan.exception.PaymentProcessException;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kundan on 3/5/2018.
 */
public class PaymentServiceImpl implements PaymentService {

	private PaymentDao paymentDao;

	//// Developer Testing credentials
	// private String clientId =
	//// "ATA-TNQRo-8wO-APHyJVCruKLJe137gre0Tfbf8rDmN8a_e1B07kvHGe59NmwdfP91h-p5QzlIM77NCZ";
	//
	// private String clientSecret =
	//// "EBkWxJfwWu1ctf7QkpO-3RtPkzqMhWGhsh1g43iBixezH-xPtgL4q6KtJwNXUVjZlTFx-xpNp4WXaZeK";
	//
	// private String mode = "sandbox";

	// Production credentials
	private String clientId = "AQC1pJyvsgMVFQ388LIzqMBfd6d-TfryYzhEntL4kbj5K7J_GIMkJDQH1LRgKBOTH675HLGcp2AZ31Yj";

	private String clientSecret = "EDQqhjxnGjA2__lSZTbIxwshCN5eeADQahHUb9IDIAQtYnFAjT4flesPKNdEsXHdabk7Ixhb1OhPc1U0";

	private String mode = "live";

	// private String payPalCancelUrl =
	// "http://localhost:8080/cancelPayPalPayment";
	private String payPalCancelUrl = "http://ayushiloancalculatorapp.herokuapp.com/cancelPayPalPayment";

	// private String payPalReturnUrl =
	// "http://localhost:8080/confirmPaypalPayment";

	private String payPalReturnUrl = "http://ayushiloancalculatorapp.herokuapp.com/confirmPaypalPayment";

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
		return (Payment) paymentDao.find(Payment.class, paymentId);
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
		return paymentDao.find(query, objVals);
	}

	// Vatsal Code
	public String createPayment(double sum) {
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(String.valueOf(sum));
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		com.paypal.api.payments.Payment payment = new com.paypal.api.payments.Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(payPalCancelUrl);
		redirectUrls.setReturnUrl(payPalReturnUrl);
		payment.setRedirectUrls(redirectUrls);
		com.paypal.api.payments.Payment createdPayment;
		try {
			String redirectUrl = "";
			APIContext context = new APIContext(clientId, clientSecret, mode);
			createdPayment = payment.create(context);
			if (createdPayment != null && createdPayment.getLinks() != null) {
				redirectUrl = createdPayment.getLinks().stream().filter(link -> link.getRel().equals("approval_url"))
						.findFirst().map(link -> link.getHref()).orElse("");
				// List<Links> links = createdPayment.getLinks();
				// for (Links link : links) {
				// if (link.getRel().equals("approval_url")) {
				// redirectUrl = link.getHref();
				// break;
				// }
				// }
				//
			}
			return redirectUrl;
		} catch (PayPalRESTException e) {
			System.out.println("Error happened during payment creation!");
			return "";
		}
	}

	@Override
	public String completePayment(PayPalModel payPalModel) {
		com.paypal.api.payments.Payment payment = new com.paypal.api.payments.Payment();
		payment.setId(payPalModel.getPaymentId());
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payPalModel.getPayerId());
		try {
			APIContext context = new APIContext(clientId, clientSecret, mode);
			com.paypal.api.payments.Payment createdPayment = payment.execute(context, paymentExecution);
			if (createdPayment != null) {
				return "Create Payment Service Success!";
			}
			return "Payment Service Failed!";
		} catch (PayPalRESTException e) {
			System.err.println(e.getDetails());
			return e.getDetails().getMessage();
		}
	}

}
