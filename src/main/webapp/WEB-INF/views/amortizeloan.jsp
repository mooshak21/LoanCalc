
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
   <head>
   <title>Loan</title>
   </head>
   <body>

	   <table><tr><td>Monthly Payment:</td><td><h2>${amortizeloan.monthly}</h2></td></tr>
		   <tr><td>Interest Rate:</td><td><h2>${amortizeloan.interestRate}</h2></td></tr>
		   <tr><td>Interest:</td><td><h2>${amortizeloan.interest}</h2></td></tr>
		   <tr><td>Principal:</td><td><h2>${amortizeloan.principal}</h2></td></tr>
		   <tr><td>Loan Amount:</td><td><h2>${amortizeloan.amount}</h2></td></tr>
		   <tr><td>Lender:</td><td><h2>${amortizeloan.lender}</h2></td></tr>
		   <tr><td>State:</td><td><h2>${amortizeloan.state}</h2></td></tr>
		   <tr><td>APR:</td><td><h2>${amortizeloan.APR}</h2></td></tr>
		   <tr><td>Number of Years:</td><td><h2>${amortizeloan.numberOfYears}</h2></td></tr>

	   </table>
	   <c:if test="${not empty amortizeloan.entries}">
	   <table>
		   <%Iterator itr = amortizeloan.getEntries().values().iterator());
			while(itr.hasNext()){
				calculator.LoanEntry entry = itr.next();%>>
		   		<tr><td>${entry.principal}</td><td>${entry.interest}</td></tr> 
			
			<%}%>
	   </table>
	   </c:if>

   </body>
</html>

