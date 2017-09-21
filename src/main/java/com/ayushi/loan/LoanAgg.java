package com.ayushi.loan;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class LoanAgg implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long loanAggId;
    private String name;
    private Calendar startDate;
    private String term;
    private String type;
    private String email;
    private Calendar createdDate;
    private Long createdUserId;
    private Calendar updatedDate;
    private Long updatedUserId;
    private Boolean active;
    private Set<LoanRelationship> loanRelationshipSet =
            new HashSet<LoanRelationship>(0);

    public LoanAgg() {
        loanAggId = System.currentTimeMillis();
        name = "abc";
        startDate = GregorianCalendar.getInstance();
        term = null;
        type = null;
        email = "test@gmail.com";
        createdDate = GregorianCalendar.getInstance();
        createdUserId = System.currentTimeMillis();
        updatedDate = GregorianCalendar.getInstance();
        updatedUserId = System.currentTimeMillis();
        active = true;
    }

    public Long getLoanAggId() {
        return loanAggId;
    }

    public void setLoanAggId(Long loanAggId) {
        this.loanAggId = loanAggId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
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

    public Set<LoanRelationship> getLoanRelationshipSet() {
        return loanRelationshipSet;
    }

    public void setLoanRelationshipSet(Set<LoanRelationship> loanRelationshipSet) {
        this.loanRelationshipSet = loanRelationshipSet;
    }
}
