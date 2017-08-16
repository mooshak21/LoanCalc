<%@page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

     <div class="row justify-content-md-center">
         <div class="col-12 col-md-8">
         <div class="card cardBody">
            <div class="card-header">
              <h2>${message}</h2>
            </div>
            <div class="card-block">
                <form name="loanForm" action="/amortizeloan" method="GET" onsubmit='if(loanForm.loanAmt.value == ""){ alert("Please enter a Loan Amount"); loanForm.loanAmt.
                                         focus(); return false;}'>
                    <div class="form-group">
                        <label for="loanAmount">Loan Amount:</label>
                          <input class="form-control" type="number" name="loanAmt" value="${amortizeloan.amount}" min="1" max="9999999999" id="loanAmount">
                    </div>
                   
                    <div class="form-group">
                        <label for="numberOfYears">Number of Years:</label>
                          <input class="form-control" type="number" name="numOfYears" value="${amortizeloan.numberOfYears}" min="1" max="100" id="numberOfYears">
                    </div>

                    <div class="form-group">
                        <label for="lender">Lender:</label>
                          <input class="form-control" type="text" name="lender" value="${amortizeloan.lender}" id="lender">
                    </div>

                    <div class="form-group">
                        <label for="state">State: </label>
                          <input class="form-control" type="text" name="state" value="${amortizeloan.state}" id="state">
                    </div>

                    <div class="form-group">
                        <label for="interestRate">Annual Interest Rate: </label>
                        <input class="form-control" type="number" name="airVal" value="${amortizeloan.APR}" min="0" max="100" step="0.01" id="interestRate">
                    </div>

                    <div class="form-group">
                        <label for="amortize">Amortize on Date: </label>
                        <input class="form-control" type="text" name="amortizeOn" value="${amortizeOn}" id="amortize">	
                    </div>

                    <button type="submit" class="btn btn-default">Submit</button>
<!--                    Loan Amount: <input type="number" name="loanAmt" value="${amortizeloan.amount}" min="1" max="9999999999"><br>
                    Number of Years: <input type="number" name="numOfYears" value="${amortizeloan.numberOfYears}" min="1" max="100"><br>
                    Lender: <input type="text" name="lender" value="${amortizeloan.lender}"><br>
                    State: <input type="text" name="state" value="${amortizeloan.state}"><br>
                    Annual Interest Rate: <input type="number" name="airVal" value="${amortizeloan.APR}" min="0" max="100" step="0.01"><br>
                    Amortize on Month: <input type="text" name="amortizeOn" value="${amortizeOn}"><br>		
                    <input type="submit" name="submit"><br>-->
                    <a href="/">Home</a><br>
            </form>
            </div>
        </div>
        </div>
    </div>        
	  
    <c:if test="${not empty amortizeloan}">
         <div class="row">
            <div class="card">
                <div class="card-block">
                    <table class="table table-hover table-bordered">
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
                </div>
            </div>
         </div>
    </c:if>	
                    
    <c:if test="${not empty amortizeloan.loanEntries}">
         <div class="row">
            <div class="card">
                <div class="card-block">
                    <table class="table table-hover table-bordered">
                         <thead class="thead-default">
                             <tr>
                                <th>Date</th>
                                <th>Principal($)</th>
                                <th>Interest($)</th>
                                <th>Loan Amount($)</th>
                                <th>Monthly($)</th>
                             </tr>
                         </thead>
                         <tbody>
                            <c:forEach var="entry" items="${amortizeloan.loanEntries}">
                                <tr>
                                    <td>${entry.dateEntry.time}</td>
                                    <td>${entry.principal}</td>
                                    <td>${entry.interest}</td>
                                    <td>${entry.loanAmount}</td>
                                    <td>${entry.monthly}</td>
                                </tr> 
                            </c:forEach>
                         </tbody>
                    </table>
                </div>
            </div>
         </div>
   </c:if>
                    
    <c:if test="${not empty amortizeloan}">
         <div class="row">
            <div class="card">
                <div class="card-block">
                    <table class="table table-hover table-bordered">
                        <tr><% int total = ((com.ayushi.loan.AmortizedLoan)request.getSession().getAttribute("amortizeloan")).getEntries().size(); 
                         int pages = total / 12, pgIdx;
                         for(pgIdx = 0; pgIdx < pages; pgIdx++){%>
                             <td><a href='/viewloanentries/<%=(pgIdx+1)%>'</a><%=(pgIdx+1)%></td><%}%>
                        </tr>
                    </table>
                </div>
            </div>
         </div>
         
    </c:if>
                    
