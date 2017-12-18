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
                <form name="loanForm" id="loanform" action="/searchloan" method="POST" 
                      onsubmit='if((loanForm.loanId.value != "") || (loanForm.loanAmt.value != ""  || loanForm.numOfYears.value != "" || loanForm.lender.value != "" || loanForm.state.value != "" || loanForm.airVal.value != "") || (loanForm.email.value != "")){ return true;}else { message.innerHTML="Please enter LoanId or at least Loan Amount, Number of Years, Lender, State, APR or email address"; loanForm.loanAmt.focus(); return false;}'>
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
                        <label for="loanType">Loan Type:</label>
                        <select class="form-control resetMe" name="loanType" id="loanType" >
                            <option value="">Choose a Loan Type</option>
                            <option value="Student Loan"  ${amortizeloan.loanType == 'Student Loan' ? 'selected' : ''}>Student Loan</option>
                            <option value="Auto Loan"  ${amortizeloan.loanType == 'Auto Loan' ? 'selected' : ''}>Auto Loan</option>
                            <option value="Home Loan" ${amortizeloan.loanType == 'Home Loan' ? 'selected' : ''}>Home Loan</option>
                        </select>
                    </div>
                   <div class="form-group">
                       <label for="amortize">Amortize on Date: </label>
                       <input class="form-control resetMe" type="text" name="amortizeOn" value="${amortizeOn}" required="true" id="amortize">		
                   </div>

                   <div class="form-group">
                       <label for="payoff"> Payoff on Date: </label>
                       <input class="form-control resetMe" type="text" name="payoffOn" value="${payoffOn}" id="payoff">
                   </div>
                   <div class="form-group">
                       <label for="email">Email: </label>
                       <input class="form-control resetMe" type="email" name="email" value="${userEmail}" id="email">
				   </div>
                  
                    
                        <input type="submit" class="btn btn-default float-left" value="Submit"/>
                        <c:if test="${loanId ne null && loanId > 0}">
                                <button type="button" class="btn btn-default float-left" style="margin-left: 5px" onclick="location.href = '/quickview'">Quick View ${LoanId}</button>
                        </c:if>
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