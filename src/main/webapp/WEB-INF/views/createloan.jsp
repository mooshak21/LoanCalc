
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
	   <form name="loanForm" action="/loan" method="POST" onsubmit='if(loanForm.loanAmt.value == ""){ alert("Please enter a Loan Amount"); loanForm.loanAmt.focus(); return false;}'>
		   Loan Amount: <input type="number" name="loanAmt" value="${loan.amount}" min="1" max="9999999999"><br>
		   Number of Years: <input type="number" name="numOfYears" value="${loan.numberOfYears}" min="1" max="100"><br>
		   Lender: <input type="text" name="lender" value="${loan.lender}"><br>
		   State: <input type="text" name="state" value="${loan.state}"><br>
		   Annual Interest Rate: <input type="number" name="airVal" value="${loan.APR}" min="0" max="100" step="0.01"><br>
		   <input type="submit" name="submit"><br> 
		     <a href="/">Home</a><br>
	   </form>
   <h2>${message}</h2>
   <c:if test="${not empty loan}">
   <table><tr><td>Loan Id:<td><h2>${loan.loanId}</h2></td></tr><tr><td>Monthly Payment:</td><td><h2>${loan.monthly}</h2></td>
		  </tr>
                      <tr><td>Interest Rate:</td><td><h2>${loan.interestRate}</h2></td></tr>
                      <tr><td>Loan Amount:</td><td><h2>${loan.amount}</h2></td></tr>
                      <tr><td>Lender:</td><td><h2>${loan.lender}</h2></td></tr>
                      <tr><td>State:</td><td><h2>${loan.state}</h2></td></tr>
                      <tr><td>APR:</td><td><h2>${loan.APR}</h2></td></tr>
		      <tr><td>Number of Years:</td><td><h2>${loan.numberOfYears}</h2></td></tr><tr><td>Loan App:</td><td><h2>${loan.loanApp.lender}</h2></td></tr>
		      <tr><td>Interest:</td><td><h2>${loan.interest}</h2></td></tr>                   <tr><td>Principal:</td><td><h2>${loan.principal}</h2></td></tr>
		      <tr><td><a href='/amortizeloan?airVal=${loan.APR}&loanAmt=${loan.amount}&state=${loan.state}&lender=${loan.lender}&numOfYears=${loan.numberOfYears}&amortizeOn=01/01/2017'>Amortize Loan</a></td></tr>
   </c:if>
             </table>
