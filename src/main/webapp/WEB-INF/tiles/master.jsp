<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>


    
    <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-header">
              <h5>${message}</h5>
            </div>
            <div class="card-block">
                <form name="loanForm" id="loanform" action="/searchloan" method="POST" onsubmit='if(loanForm.loanAmt.value == ""  && loanForm.numOfYears.value == "" && loanForm.lender.value == "" && loanForm.state.value == "" && loanForm.airVal.value == ""){ alert("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); loanForm.loanAmt.focus(); return false;}'>
                    <div class="form-group">
                       <label for="loanAmount">Loan Amount:</label>
                        <input class="form-control resetMe" type="number" name="loanAmt" value="${amortizeloan.amount}" min="1" max="9999999999" id="loanAmount">
                   </div>

                   <div class="form-group">
                       <label for="numberOfYears">Number of Years:</label>
                       <input class="form-control resetMe" type="number" name="numOfYears" value="${amortizeloan.numberOfYears}" min="1" max="100" id="numberOfYears">
                   </div>

                   <div class="form-group">
                       <label for="lender">Lender:</label>
                       <input class="form-control resetMe" type="text" name="lender" value="${amortizeloan.lender}" id="lender">
                   </div>

                   <div class="form-group">
                       <label for="state">State: </label>
                        <input class="form-control resetMe" type="text" name="state" value="${amortizeloan.state}" id="state">
                   </div>

                   <div class="form-group">
                       <label for="interestRate">Annual Interest Rate: </label>
                       <input class="form-control resetMe" type="number" name="airVal" value="${amortizeloan.APR}" min="0" max="100" step="0.01" id="interestRate">
                   </div>

                   <div class="form-group">
                       <label for="amortize">Amortize on Date: </label>
                       <input class="form-control resetMe" type="text" name="amortizeOn" value="${amortizeOn}" required="true" id="amortize">		
                   </div>

                   <div class="form-group">
                       <label for="payoff"> Payoff on Date: </label>
                       <input class="form-control resetMe" type="text" name="payoffOn" value="${payoffOn}" id="payoff">
                   </div>
                   
                    
                        <input type="submit" class="btn btn-default float-left" value="submit"/>
                        <input  type= "button" class="btn btn-default float-right"  value="Reset" onclick="resetForm()"/> 
                    
                </form>    
            </div>
        </div>
     </div>

<script>
function resetForm() {
    $( document ).ready(function() {
        $(".resetMe").val("");
    });
    
}
</script>