<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
	   <c:if test="${not empty amortizeloan}">
	   
	   <table><tr><td>Loan Id:</td><td><h2>${amortizeloan.loanId}</h2></td></tr>
		   
		   <tr><td>Monthly Payment:($)</td><td><h2>${amortizeloan.monthly}</h2></td></tr>
		   <tr><td>Interest Rate:(%)</td><td><h2>${amortizeloan.interestRate}</h2></td></tr>
		   <tr><td>Last Interest:($)</td><td><h2>${amortizeloan.interest}</h2></td></tr>
		   <tr><td>Last Principal:($)</td><td><h2>${amortizeloan.principal}</h2></td></tr>
		   <tr><td>Loan Amount:($)</td><td><h2>${amortizeloan.amount}</h2></td></tr>
		   <tr><td>Lender:</td><td><h2>${amortizeloan.lender}</h2></td></tr>
		   <tr><td>State:</td><td><h2>${amortizeloan.state}</h2></td></tr>
		   <tr><td>APR:(%)</td><td><h2>${amortizeloan.APR}</h2></td></tr>
		   <tr><td>Number of Years:</td><td><h2>${amortizeloan.numberOfYears}</h2></td></tr><tr><td>Payoff Amount:($)</td><td><h2>${payoffAmount}</h2></td><td>As of Payoff Date on:</td><td><h2>${payoffOn}</h2></td></tr>

	   </table>
	   </c:if>	
	   <c:if test="${not empty amortizeloan.loanEntries}">
	   <table border="1"><th>Date</th><th>Principal($)</th><th>Interest($)</th><th>Loan Amount($)</th><th>Monthly($)</th>
		   <c:forEach var="entry" items="${amortizeloan.loanEntries}">
	 
		   <tr><td>${entry.dateEntry.time}</td><td>${entry.principal}</td><td>${entry.interest}</td><td>${entry.loanAmount}</td><td>${entry.monthly}</td></tr> 
			
		   </c:forEach>
	   </table>
	   </c:if>
	   <c:if test="${not empty amortizeloan}">
	   	<table><tr><% int total = ((calculator.AmortizedLoan)request.getSession().getAttribute("amortizeloan")).getEntries().size(); 
				int pages = total / 12, pgIdx;
				for(pgIdx = 0; pgIdx < pages; pgIdx++){%>
				<td><a href='/viewloanentries/<%=(pgIdx+1)%>'</a><%=(pgIdx+1)%></td><%}%>
			</tr>
		</table>
	   </c:if>
