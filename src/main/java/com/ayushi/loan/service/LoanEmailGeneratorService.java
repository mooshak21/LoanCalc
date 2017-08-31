/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayushi.loan.service;

import com.ayushi.loan.AmortizedLoan;
import com.ayushi.loan.exception.EmailServiceException;

/**
 *
 * @author Daniel Gago
 */
public interface LoanEmailGeneratorService {
    public String buildMessage(AmortizedLoan amortizedLoan, Double payOffAmount, String payOffOn);
    public void sendMail(String emailTo, String subject, String message) throws EmailServiceException;
}
