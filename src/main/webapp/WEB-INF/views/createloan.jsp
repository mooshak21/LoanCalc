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
   </body>
</html>

