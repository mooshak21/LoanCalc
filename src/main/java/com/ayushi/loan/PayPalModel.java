package com.ayushi.loan;

public class PayPalModel {

	private String paymentId;

	private String payerId;

	public PayPalModel() {
	}

	public PayPalModel(String paymentId, String payerId) {
		this.paymentId = paymentId;
		this.payerId = payerId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

}
