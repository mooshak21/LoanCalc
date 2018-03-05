/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayushi.loan.exception;

/**
 *
 * @author Kundan
 */
public class PaymentProcessException extends Exception{

    public PaymentProcessException(String message) {
        super(message);
    }

    public PaymentProcessException(Exception exception) {
        super(exception);
    }
    
}
