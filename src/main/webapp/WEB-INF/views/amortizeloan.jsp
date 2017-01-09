
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
   <head>
   <title>Loan</title>
   </head>
   <body>

	   <table><tr><td>Monthly Payment:($)</td><td><h2>${amortizeloan.monthly}</h2></td></tr>
		   <tr><td>Interest Rate:(%)</td><td><h2>${amortizeloan.interestRate}</h2></td></tr>
		   <tr><td>Last Interest:($)</td><td><h2>${amortizeloan.interest}</h2></td></tr>
		   <tr><td>Last Principal:($)</td><td><h2>${amortizeloan.principal}</h2></td></tr>
		   <tr><td>Loan Amount:($)</td><td><h2>${amortizeloan.amount}</h2></td></tr>
		   <tr><td>Lender:</td><td><h2>${amortizeloan.lender}</h2></td></tr>
		   <tr><td>State:</td><td><h2>${amortizeloan.state}</h2></td></tr>
		   <tr><td>APR:(%)</td><td><h2>${amortizeloan.APR}</h2></td></tr>
		   <tr><td>Number of Years:</td><td><h2>${amortizeloan.numberOfYears}</h2></td></tr>

	   </table>
	   <c:if test="${not empty amortizeloan.entries}">
	   <table border="1"><th>Number</th><th>Date</th><th>Principal($)</th><th>Interest($)</th><th>Loan Amount($)</th><th>Monthly($)</th>

		   <% calculator.AmortizedLoan al = (calculator.AmortizedLoan) request.getAttribute("amortizeloan");
		    Integer currIdx = new Integer(0);	
		   java.util.Iterator itr = al.getEntries().values().iterator();
			while(itr.hasNext()){
				
				calculator.LoanEntry entry = (calculator.LoanEntry) itr.next();%>
				<tr><td><%=++currIdx%></td><td><%=(entry.getDateEntry().get(java.util.Calendar.MONTH) + "/" + entry.getDateEntry().get(java.util.Calendar.DAY_OF_MONTH) + "/" + entry.getDateEntry().get(java.util.Calendar.YEAR))%></td><td><%=entry.getPrincipal()%></td><td><%=entry.getInterest()%></td><td><%=entry.getLoanAmount()%></td><td><%=entry.getMonthly()%></td></tr> 
			
			<%}%>
	   </table>
	   </c:if>

   </body>
</html>

