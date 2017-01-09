
<%@page language="java" contentType="text/html; charset=UTF-8"^M
    pageEncoding="UTF-8"%>^M
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
   <head>
   <title>Loan</title>
   </head>
   <body>

	   <form name="loanForm" action="/loan" method="POST" onsubmit='if(loanForm.loanAmt.value == ""){ alert("Please enter a Loan Amount"); loanForm.loanAmt.focus(); return false;}'>
		   Loan Amount: <input type="text" name="loanAmt"><br>
		   Number of Years: <input type="text" name="numOfYears"><br>
		   Lender: <input type="text" name="lender"><br>
		   State: <input type="text" name="state"><br>
		   Annual Interest Rate: <input type="text" name="airVal"><br>
		   <input type="submit" name="submit"><br> 
	   </form>
   <h2>${message}</h2>
   <table><tr><td>Loan Id:<td><h2>${loan.loanId}</h2></td></tr><tr><td>Monthly Payment:</td><td><h2>${loan.monthly}</h2></td>
		  </tr>
                      <tr><td>Interest Rate:</td><td><h2>${loan.interestRate}</h2></td></tr>
                      <tr><td>Loan Amount:</td><td><h2>${loan.amount}</h2></td></tr>
                      <tr><td>Lender:</td><td><h2>${loan.lender}</h2></td></tr>
                      <tr><td>State:</td><td><h2>${loan.state}</h2></td></tr>
                      <tr><td>APR:</td><td><h2>${loan.APR}</h2></td></tr>
		      <tr><td>Number of Years:</td><td><h2>${loan.numberOfYears}</h2></td></tr><tr><td>Loan App:</td><td><h2>${loan.loanApp.lender}</h2></td></tr>
		      <tr><td>Interest:</td><td><h2>${loan.interest}</h2></td></tr>                   <tr><td>Principal:</td><td><h2>${loan.principal}</h2></td></tr>
		      <c:if test="${not null loan}">
		      <tr><td><a href='/amortizeloan?airVal=${loan.APR}&loanAmt=${loan.amount}&state=${loan.state}&lender=${loan.lender}&numOfYears=${loan.numberOfYears}'>Amortize Loan</a></td></tr></c:if>
             </table></body>
</html>

