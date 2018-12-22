<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	


<!--            top menu on xs devices-->
         <nav class="navbar navbar-toggleable-md navbar-light bg-faded hidden-sm-up loanMenu" >
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" 
                    data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" 
                    aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon menuItemXs"></span>
            </button>
            <a class="navbar-brand menuItemXs" href="#">Menu</a>

            <div class="collapse navbar-collapse hidden-sm-up" id="navbarSupportedContent" >
              <ul class="navbar-nav mr-auto">
                
                <li class="nav-item">
                  <a class="nav-link menuItemXs" href="/" >Home</a>
                </li>

	        <li class="nav-item">
	          <a class="nav-link menuItemXs" href="/login">Login</a>
		</li>
	<c:if test="${(not empty userEmail) and ((Plan == '9.99') or (Plan == '19.99'))}">
		<li class="nav-item">
                  <a class="nav-link menuItemXs" href="/quickview">Quick View Loan</a>
                </li>
	</c:if>
	<c:if test="${(not empty userEmail) and ((Plan == '0.0') or (Plan == '9.99') or (Plan == '19.99'))}">
		<li class="nav-item">
                  <a class="nav-link menuItemXs" href="/loansearchask">Search Loan</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link menuItemXs" href="/createloan">Enter Loan</a>
                </li>

                <li class="nav-item">
                  <a class="nav-link menuItemXs" href="/loanamortizeask">Amortize Loan</a>
                </li>

                <li class="nav-item">
                  <a class="nav-link menuItemXs" href="/loanpayoffask">Payoff Loan</a>
                </li>

                <li class="nav-item">
                  <a class="nav-link menuItemXs" href="/loanviewask">View Loans</a><br>
                </li>



    </c:if>
	<c:if test="${(not empty userEmail) and (Plan == '19.99')}">
                  <li class="nav-item">
                      <a class="nav-link menuItemXs" href="/aggregateloanask">Aggregate Loan</a>
                  </li>

                <li class="nav-item">
                    <a class="nav-link menuItemXs" href="/aggregateloanreportask">Aggregate Loan Report</a>
                </li>
        </c:if>

                  <li class="nav-item">
                      <a class="nav-link menuItemXs" href="/payment">Payment</a>
                  </li>
                <li class="nav-item">
                  <a class="nav-link menuItemXs" href="/loanpreferenceviewask">Register</a>
                </li>



		  <li class="nav-item">
		      <a class="nav-link menuItemXs" href="/logout">Log out</a>
		  </li>
              </ul>
            </div>
          </nav>

<!--       aside menu -->
        <ul class="nav flex-column hidden-xs-down">
            <li class="nav-item">
              <a class="nav-link active" href="/" id="menuHome">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link menuItem" href="/login">Login</a>
	    </li>
	<c:if test="${(not empty userEmail) and ((Plan == '9.99') or (Plan == '19.99'))}">
	            
	   <li class="nav-item">
                  <a class="nav-link menuItem" href="/quickview">Quick View Loan</a>
            </li>
	</c:if>
	<c:if test="${(not empty userEmail) and ((Plan == '0.0') or (Plan == '9.99') or (Plan == '19.99'))}">
           <li class="nav-item">
              <a class="nav-link menuItem" href="/loansearchask">Search Loan</a>
            </li>

            <li class="nav-item">
              <a class="nav-link menuItem" href="/createloan">Enter Loan</a>
            </li>
            
            <li class="nav-item">
              <a class="nav-link menuItem" href="/loanamortizeask">Amortize Loan</a>
            </li>
            
            <li class="nav-item">
              <a class="nav-link menuItem" href="/loanpayoffask">Payoff Loan</a>
            </li>
            
            <li class="nav-item">
              <a class="nav-link menuItem" href="/loanviewask">View Loans</a><br>
            </li>


 	</c:if>
            <c:if test="${(not empty userEmail) and (Plan == '19.99')}">
           <li class="nav-item">
                <a class="nav-link menuItem" href="/aggregateloanask">Aggregate Loan</a>
            </li>

            <li class="nav-item">
                <a class="nav-link menuItem" href="/aggregateloanreportask">Aggregate Loan Report</a>
            </li>
        </c:if>

            <li class="nav-item">
                <a class="nav-link menuItem" href="/payment">Payment</a>
            </li>

            <li class="nav-item">
              <a class="nav-link menuItem" href="/loanpreferenceviewask">Register</a>
            </li>


	    <li class="nav-item">
	    	<a class="nav-link menuItem" href="/logout">Log out</a>
	    </li>
         </ul> 

