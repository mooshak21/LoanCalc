<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
     <div class="row justify-content-md-center">
        <div class="card col-8 cardBody">
            <div class="card-header">
              <h2>${message}</h2>
            </div>
            <div class="card-block">
                 <form name="loanForm" action="/searchloan" method="POST" onsubmit='if(loanForm.loanAmt.value == ""  && loanForm.numOfYears.value == "" && loanForm.lender.value == "" && loanForm.state.value == "" && loanForm.airVal.value == ""){ alert("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); loanForm.loanAmt.focus(); return false;}'>
                    <div class="form-group">
                       <label for="loanAmount">Loan Amount:</label>
                        <input class="form-control" type="number" name="loanAmt" value="${amortizeloan.amount}" min="1" max="9999999999" id="loanAmount">
                   </div>

                   <div class="form-group">
                       <label for="numberOfYears">Number of Years:</label>
                       <input class="form-control" type="number" name="numOfYears" value="${amortizeloan.numberOfYears}" min="1" max="100" id="numberOfYears">
                   </div>

                   <div class="form-group">
                       <label for="lender">Lender:</label>
                       <input class="form-control" type="text" name="lender" value="${amortizeloan.lender}" id="lender">
                   </div>

                   <div class="form-group">
                       <label for="state">State: </label>
                        <input class="form-control" type="text" name="state" value="${amortizeloan.state}" id="state">
                   </div>

                   <div class="form-group">
                       <label for="interestRate">Annual Interest Rate: </label>
                       <input class="form-control" type="number" name="airVal" value="${amortizeloan.APR}" min="0" max="100" step="0.01" id="interestRate">
                   </div>

                   <div class="form-group">
                       <label for="amortize">Amortize on Date: </label>
                       <input class="form-control" type="text" name="amortizeOn" value="${amortizeOn}" required="true" id="amortize">		
                   </div>

                   <div class="form-group">
                       <label for="payoff"> Payoff on Date: </label>
                       <input class="form-control" type="text" name="payoffOn" value="${payoffOn}" id="payoff">
                   </div>

                   <button type="submit" class="btn btn-default">Submit</button>
                  
                </form>    
            </div>
        </div>
     </div>
<!--	   <form name="loanForm" action="/searchloan" method="POST" onsubmit='if(loanForm.loanAmt.value == ""  && loanForm.numOfYears.value == "" && loanForm.lender.value == "" && loanForm.state.value == "" && loanForm.airVal.value == ""){ alert("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); loanForm.loanAmt.focus(); return false;}'>
		<input class="form-control" type="number" name="loanAmt" value="${amortizeloan.amount}" min="1" max="9999999999" id="loanAmount">   
               Loan Amount: <input type="number" name="loanAmt" value="${amortizeloan.amount}" min="1" max="9999999999"><br>
                    Ç
                    Number of Years: 
                    <input type="number" name="numOfYears" value="${amortizeloan.numberOfYears}" min="1" max="100"><br>
                     <input class="form-control" type="number" name="numOfYears" value="${amortizeloan.numberOfYears}" min="1" max="100" id="numberOfYears">
                    Lender: <input type="text" name="lender" value="${amortizeloan.lender}"><br>
                    State: <input type="text" name="state" value="${amortizeloan.state}"><br>
                    Annual Interest Rate: <input type="number" name="airVal" value="${amortizeloan.APR}" min="0" max="100" step="0.01"><br>
                    Amortize on Date : <input type="text" name="amortizeOn" value="${amortizeOn}" required="true"><br>		
                     Payoff on Date: <input type="text" name="payoffOn" value="${payoffOn}"><br>
                    <input type="submit" name="submit"><br>
                    <a href="/">Home</a><br>
	             </form>
  <h2>${message}</h2>-->
