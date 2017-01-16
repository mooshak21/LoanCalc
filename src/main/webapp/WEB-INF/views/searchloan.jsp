
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
	   <form name="loanForm" action="/searchloan" method="POST" onsubmit='if(loanForm.loanAmt.value == ""  && loanForm.numOfYears.value == "" && loanForm.lender.value == "" && loanForm.state.value == "" && loanForm.airVal.value == ""){ alert("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); loanForm.loanAmt.focus(); return false;}'>
		   Loan Amount: <input type="number" name="loanAmt" value="${amortizeloan.amount}" min="1" max="9999999999"><br>
			     Number of Years: <input type="number" name="numOfYears" value="${amortizeloan.numberOfYears}" min="1" max="100"><br>
			     Lender: <input type="text" name="lender" value="${amortizeloan.lender}"><br>
			     State: <input type="text" name="state" value="${amortizeloan.state}"><br>
			     Annual Interest Rate: <input type="number" name="airVal" value="${amortizeloan.APR}" min="0" max="100" step="0.01"><br>
			     Amortize on Date : <input type="text" name="amortizeOn" value="${amortizeOn}" required="true"><br>		
			      Payoff on Date: <input type="text" name="payoffOn" value="${payoffOn}"><br>
        	             <input type="submit" name="submit"><br>
			     <a href="/">Home</a><br>
	             </form>
	   <h2>${message}</h2>
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
	   <c:if test="${not empty amoritizeloan.entries}">
	   	<table><tr><% int total = amortizeloan.getEntries().size(); 
				int pages = total / 12;
				for(int pgIdx = 1; pgIdx < pages; pgIdx++)%>
				<td><a href="/viewloans?pageIdx="<%=pgIdx%></a><%=pgIdx%></td>
			</tr>
		</table>
	   </c:if>
	   
