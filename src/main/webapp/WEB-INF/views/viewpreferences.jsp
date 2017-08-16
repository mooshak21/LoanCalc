
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

   <div class="row justify-content-md-center align-items-center">
        <div class="card col-12 col-md-8 cardBody">
            <div class="card-header">
              <h2>${message}</h2>
            </div>
            <div class="card-block">
                <form name="loanForm" action="/vieweditpreferences" method="GET" onsubmit='if(loanForm.loanAmt.value == ""){ alert("Please enter a Loan Amount"); loanForm.loanAmt.focus(); return false;}'>
                   
                   <div class="form-group">
                       <label for="loanAmount">Loan Amount:</label>
                        <input class="form-control" type="number" name="loanAmt" value="${loan.amount}" min="1" max="9999999999" id="loanAmount">
                   </div>

                   <div class="form-group">
                       <label for="numberOfYears">Number of Years:</label>
                       <input class="form-control" type="number" name="numOfYears" value="${loan.numberOfYears}" min="1" max="100" id="numberOfYears">
                   </div>

                   <div class="form-group">
                       <label for="lender">Lender:</label>
                       <input class="form-control" type="text" name="lender" value="${loan.lender}" id="lender">
                   </div>

                   <div class="form-group">
                       <label for="state">State: </label>
                        <input class="form-control" type="text" name="state" value="${loan.state}" id="state">
                   </div>

                   <div class="form-group">
                       <label for="interestRate">Annual Interest Rate: </label>
                       <input class="form-control" type="number" name="airVal" value="${loan.APR}" min="0" max="100" step="0.01" id="interestRate">
                   </div>

                   <div class="form-group">
                       <label for="locationPreference">Location Preference: </label>
                        <input class="form-control" type="text" name="locationPreference" value="state" id="locationPreference">
                   </div>

                   <div class="form-group">
                       <label for="wsPreference">Web Service Preference: </label>
                       <input class="form-control" type="text" name="webServicePreference" value="REST" id="wsPreference">
                   </div>

                    <div class="form-group">
                       <label for="rtPreference">Risk Tolerance Preference: </label>
                       <input class="form-control" type="number" name="riskTolerancePreference" value="1" min="0" max="100" step="1" id="rtPreference">
                   </div>

                   <div class="form-group">
                       <label for="thPreference">Time Horizon Preference: </label>
                       <input class="form-control" type="number" name="timeHorizonPreference" value="1" min="0" max="100" step="0.01" id="thPreference">
                   </div>


                   <button type="submit" class="btn btn-default">Submit</button>
                </form>
            </div>
        </div>
    </div>
	
   <c:if test="${not empty loan}">
       <div class="row">
            <div class="card">
                <div class="card-block">
                    <table class="table table-hover table-bordered">
                      <tr><td>Loan Id:<td><h2>${loan.loanId}</h2></td></tr><tr><td>Monthly Payment:</td><td><h2>${loan.monthly}</h2></td></tr>
                      <tr><td>Interest Rate:</td><td><h2>${loan.interestRate}</h2></td></tr>
                      <tr><td>Loan Amount:</td><td><h2>${loan.amount}</h2></td></tr>
                      <tr><td>Lender:</td><td><h2>${loan.lender}</h2></td></tr>
                      <tr><td>State:</td><td><h2>${loan.state}</h2></td></tr>
                      <tr><td>APR:</td><td><h2>${loan.APR}</h2></td></tr>
		      <tr><td>Number of Years:</td><td><h2>${loan.numberOfYears}</h2></td></tr><tr><td>Loan App:</td><td><h2>${loan.loanApp.lender}</h2></td></tr>
		      <tr><td>Interest:</td><td><h2>${loan.interest}</h2></td></tr>                   <tr><td>Principal:</td><td><h2>${loan.principal}</h2></td></tr>
		      <tr><td><a href='/amortizeloan?airVal=${loan.APR}&loanAmt=${loan.amount}&state=${loan.state}&lender=${loan.lender}&numOfYears=${loan.numberOfYears}&amortizeOn=01/01/2017'>Amortize Loan</a></td></tr>
                    </table>
                </div>
            </div>
        </div>
   <table>
   </c:if>
