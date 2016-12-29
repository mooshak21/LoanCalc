<html>
   <head>
   <title>Loan</title>
   </head>
   <body>
	   <form name="loanForm" action="/loan" method="GET">
		   Loan Amount: <input type="text" name="loanAmt"><br>
		   Number of Years: <input type="text" name="numOfYears"><br>
		   Lender: <input type="text" name="lender"><br>
		   State: <input type="text" name="state"><br>
		   Annual Interest Rate: <input type="text" name="airVal"><br>
		   <input type="submit" name="submit" script="if(loanAmt.value == null) return false;"><br> 
	   </form>
   <h2>${message}</h2>
   </body>
</html>

