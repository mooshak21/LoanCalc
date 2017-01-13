
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
   <head>
   <title>Loan</title>
   </head>
   <body>

	   <form name="loanForm" action="/searchloan" method="POST" onsubmit='if(loanForm.loanAmt.value == ""  && loanForm.numOfYears.value == "" && loanForm.lender.value == "" && loanForm.state.value == "" && loanForm.airVal.value == ""){ alert("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); loanForm.loanAmt.focus(); return false;}'>
			     Loan Amount: <input type="number" name="loanAmt" value="${amortizeloan.amount}" min="1" max="9999999999" required="true"><br>
			     Number of Years: <input type="number" name="numOfYears" value="${amortizeloan.numberOfYears}" min="1" max="100"><br>
			     Lender: <input type="text" name="lender" value="${amortizeloan.lender}"><br>
			     State: <input type="text" name="state" value="${amortizeloan.state}"><br>
			     Annual Interest Rate: <input type="number" name="airVal" value="${amortizeloan.APR}" min="0" max="100" step="0.01"><br>
			     Amortize on Month: <input type="text" name="amortizeOn" value="${amortizeOn}" required="true"><br>		
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
		   <tr><td>Number of Years:</td><td><h2>${amortizeloan.numberOfYears}</h2></td></tr>

	   </table>
	   </c:if>	
	   <c:if test="${not empty amortizeloan.entries}">
	   <table border="1"><th>Number</th><th>Date</th><th>Principal($)</th><th>Interest($)</th><th>Loan Amount($)</th><th>Monthly($)</th>

		   <% calculator.AmortizedLoan al = (calculator.AmortizedLoan) request.getAttribute("amortizeloan");
		    Integer currIdx = new Integer(0);	
		    if(al != null){
		    	java.util.Iterator itr = al.getEntries().values().iterator();
			while(itr != null && itr.hasNext()){
				
				calculator.LoanEntry entry = (calculator.LoanEntry) itr.next();
				if(entry != null){%>
				<tr><td><%=++currIdx%></td><td><%=(entry.getDateEntry().get(java.util.Calendar.MONTH) + "/" + entry.getDateEntry().get(java.util.Calendar.DAY_OF_MONTH) + "/" + entry.getDateEntry().get(java.util.Calendar.YEAR))%></td><td><%=entry.getPrincipal()%></td><td><%=entry.getInterest()%></td><td><%=entry.getLoanAmount()%></td><td><%=entry.getMonthly()%></td></tr> 
			
			<%}
			}
		}%>
	   </table>
	   </c:if>

   </body>
</html>

