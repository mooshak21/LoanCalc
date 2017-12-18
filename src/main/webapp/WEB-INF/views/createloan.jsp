
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

    <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-header">
              <h5>${message}</h5>
            </div>
            <div class="card-block">
                 <form name="loanForm" id="loanform" action="/loan" method="POST" onsubmit='if(loanForm.loanAmt.value == ""){ alert("Please enter a Loan Amount"); loanForm.loanAmt.focus(); return false;}'>
                     <div class="form-group">
                        <label for="loanAmount">Loan Amount:</label>
                         <input class="form-control resetMe" type="number" name="loanAmt" value="${loan.amount}" min="1" max="9999999999" id="loanAmount" required="true">
                    </div>

                    <div class="form-group">
                        <label for="numberOfYears">Number of Years:</label>
                        <input class="form-control resetMe" type="number" name="numOfYears" value="${loan.numberOfYears}" min="1" max="100" id="numberOfYears" required="true">
                    </div>

                    <div class="form-group">
                        <label for="lender">Lender:</label>
                        <input class="form-control resetMe" type="text" name="lender" value="${loan.lender}" id="lender" required="true">
                    </div>

                    <div class="form-group">
                        <label for="state">State: </label>
                         <input class="form-control resetMe" type="text" name="state" value="${loan.state}" id="state" required="true">
                    </div>

                    <div class="form-group">
                        <label for="interestRate">Annual Interest Rate: </label>
                        <input class="form-control resetMe" type="number" name="airVal" value="${loan.APR}" min="0" max="100" step="0.01" id="interestRate" required="true">
                    </div>
                   <div class="form-group">
                       <label for="email">Email: </label>
                       <input class="form-control resetMe" type="email" name="email" value="${userEmail}" id="email" required="true">
		   </div>

                     <div class="form-group">
                         <label for="loanType">Loan Type:</label>
                         <select class="form-control resetMe" name="loanType" required="true" id="loanType" >
                             <option value="">Choose a Loan Type</option>
                             <option value="Student Loan"  ${loan.loanType == 'Student Loan' ? 'selected' : ''}>Student Loan</option>
                             <option value="Auto Loan"  ${loan.loanType == 'Auto Loan' ? 'selected' : ''}>Auto Loan</option>
                             <option value="Home Loan" ${loan.loanType == 'Home Loan' ? 'selected' : ''}>Home Loan</option>
                         </select>
                     </div>
                     <div class="form-group">
                         <label for="loanDenomination">Loan Denomination:</label>
                         <select class="form-control resetMe" name="loanDenomination" required="true" id="loanDenomination" >
                             <option value="">Choose a Loan Denomination</option>
                             <option value="USD"  ${loan.loanDenomination == 'USD' ? 'selected' : ''}>USD</option>
                             <option value="CAN$"  ${loan.loanDenomination == 'CAN$' ? 'selected' : ''}>CAN$</option>
                             <option value="MEXPESO" ${loan.loanDenomination == 'MEXPESO' ? 'selected' : ''}>Mexican Peso</option>
                             <option value="EURO"  ${loan.loanDenomination == 'EURO' ? 'selected' : ''}>Euro</option>
                             <option value="UKPOUND"  ${loan.loanDenomination == 'UKPOUND' ? 'selected' : ''}>UK Pound</option>
                             <option value="INR" ${loan.loanDenomination == 'INR' ? 'selected' : ''}>Indian Rupees</option>
                        </select>
                     </div>
                     <input type="submit" class="btn btn-default float-left" value="Submit"/>
                     <input  type= "button" class="btn btn-default float-right"  value="Reset" onclick="resetForm()"/> 
                
                 </form>
            </div>
        </div>
    </div>
   
   
   <c:if test="${not empty loan}"> 
       
        <jsp:include page="/WEB-INF/views/emailForm.jsp">
            <jsp:param name="dataType" value="Loan"/>
        </jsp:include>
       
        <div class="row justify-content-center">
            <div class="card col-10 col-md-8 cardBody">
                <div class="card-block">
                    <table class="table table-hover table-bordered">
                        <tr><td style="width: 40%">Loan Id:<td><h4>${loan.loanId}</h4></td></tr><tr><td>Monthly Payment:($)</td><td><h4><fmt:formatNumber value="${loan.monthly}" pattern="###,###,###.###"/></h4></td></tr>
                        <tr><td style="width: 40%">Interest Rate:(%)</td><td><h4><fmt:formatNumber value="${loan.interestRate}" pattern="###,###,###.########"/></h4></td></tr>
                        <tr><td style="width: 40%">Loan Amount:</td><td><h4><fmt:formatNumber value="${loan.amount}" pattern="###,###,###.00"/></h4></td></tr>
                        <tr><td style="width: 40%">Lender:</td><td><h4>${loan.lender}</h4></td></tr>
                        <tr><td style="width: 40%">State:</td><td><h4>${loan.state}</h4></td></tr>
                        <tr><td style="width: 40%">APR:(%)</td><td><h4><fmt:formatNumber value="${loan.APR}" pattern="###,###,###.########"/></h4></td></tr>
                        <tr><td style="width: 40%">Number of Years:</td><td><h4>${loan.numberOfYears}</h4></td></tr>
                        <tr><td style="width: 40%">Loan Type:</td><td><h4>${loan.loanType}</h4></td></tr>
                        <tr><td style="width: 40%">Loan App:</td><td><h4>${loan.loanApp.lender}</h4></td></tr>
                        <tr><td style="width: 40%">Interest:</td><td><h4><fmt:formatNumber value="${loan.interest}" pattern="###,###,###.###"/></h4></td></tr>
                        <tr><td style="width: 40%">Principal:</td><td><h4><fmt:formatNumber value="${loan.principal}" pattern="###,###,###.###"/></h4></td></tr>
                        <tr><td style="width: 40%" colspan="2"><a href='/amortizeloan?airVal=${loan.APR}&loanAmt=${loan.amount}&state=${loan.state}&lender=${loan.lender}&numOfYears=${loan.numberOfYears}&amortizeOn=01/01/2017'>Amortize Loan</a></td></tr>
                    </table>
                </div>
            </div>
        </div>
   </c:if> 
             
<script>
function resetForm() {
    $( document ).ready(function() {
        $(".resetMe").val("");
    });
    
}
</script>