package com.ayushi.loan;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 */
public class LoanRelationship implements Serializable {
    private static final long serialVersionUID = 1L;
    private LoanAgg loanAgg;
    private Long loanRelId;
    private Long loanId;
    private String name;
    private String type;
    private String email;
    private Calendar createdDate;
    private Long createdUserId;
    private Calendar updatedDate;
    private Long updatedUserId;
    private Boolean active;

    public LoanRelationship(){
        loanAgg = null;
        loanRelId = System.currentTimeMillis();
        loanId = System.currentTimeMillis();
        name = "abc";
        type = null;
        email = "test@gmail.com";
        createdDate = GregorianCalendar.getInstance();
        createdUserId = System.currentTimeMillis();
        updatedDate = GregorianCalendar.getInstance();
        updatedUserId = System.currentTimeMillis();
        active = true;
    }

    public LoanAgg getLoanAgg() {
        return loanAgg;
    }

    public void setLoanAgg(LoanAgg loanAgg) {
        this.loanAgg = loanAgg;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

/*public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }*/

    public Long getLoanRelId() {
        return loanRelId;
    }

    public void setLoanRelId(Long loanRelId) {
        this.loanRelId = loanRelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Calendar getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Calendar updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Long updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
