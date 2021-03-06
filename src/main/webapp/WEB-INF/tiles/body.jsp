<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <div class="row justify-content-center" >
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-header">
              <h5>${message}</h5>
            </div>
            <div class="card-block">
                <form name="loanForm" action="/loan" method="GET" onsubmit='if(loanForm.loanAmt.value == ""){ alert("Please enter a Loan Amount"); loanForm.loanAmt.focus(); return false;}'>
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
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>
            </div>
        </div>
    </div>
	  
   <c:if test="${not empty loan}">
       <div class="row justify-content-center">
            <div class="card col-10 col-md-8 cardBody">
                <div class="card-block">
                    <table class="table table-hover table-bordered">
                        <tr><td style="width: 40%">Loan Id:<td><h4>${loan.loanId}</h4></td></tr>
                        <tr><td style="width: 40%">Monthly Payment:</td><td><h4>${loan.monthly}</h4></td></tr>
                        <tr><td style="width: 40%">Interest Rate:</td><td><h4>${loan.interestRate}</h4></td></tr>
                        <tr><td style="width: 40%">Loan Amount:</td><td><h4>${loan.amount}</h4></td></tr>
                        <tr><td style="width: 40%">Lender:</td><td><h4>${loan.lender}</h4></td></tr>
                        <tr><td style="width: 40%">State:</td><td><h4>${loan.state}</h4></td></tr>
                        <tr><td style="width: 40%">APR:</td><td><h4>${loan.APR}</h4></td></tr>
                        <tr><td style="width: 40%">Number of Years:</td><td><h4>${loan.numberOfYears}</h4></td></tr>
                        <tr><td style="width: 40%">Loan App:</td><td><h4>${loan.loanApp.lender}</h4></td></tr>
                        <tr><td style="width: 40%">Interest:</td><td><h4>${loan.interest}</h4></td></tr>                 
                        <tr><td style="width: 40%">Principal:</td><td><h4>${loan.principal}</h4></td></tr>
                        <tr><td style="width: 40%" colspan="2"><a href='/amortizeloan?airVal=${loan.APR}&loanAmt=${loan.amount}&state=${loan.state}&lender=${loan.lender}&numOfYears=${loan.numberOfYears}&amortizeOn=01/01/2017'>Amortize Loan</a></td></tr>
                    </table>
                </div>
            </div>
       </div>
    </c:if>
             

