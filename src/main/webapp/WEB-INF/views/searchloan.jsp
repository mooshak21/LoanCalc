
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
           
            <div class="row justify-content-center">
                <div class="card col-10 col-md-8 cardBody">
                    <div class="card-header">
                        <h5>${message}</h5>
                    </div>
                    <div class="card-block">
                        <form name="loanForm" action="/searchloan" method="POST" onsubmit='if(loanForm.loanAmt.value == ""  && loanForm.numOfYears.value == "" && loanForm.lender.value == "" && loanForm.state.value == "" && loanForm.airVal.value == ""){ alert("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); loanForm.loanAmt.focus(); return false;}'>
                            <div class="form-group row">
                                <label for="loanAmount">Loan Amount:</label>
                                  <input class="form-control" type="number" name="loanAmt" value="${amortizeloan.amount}" min="1" max="9999999999" id="loanAmount">
                            </div>
                            
                            <div class="form-group row">
                                <label for="numberOfYears">Number of Years:</label>
                                  <input class="form-control" type="number" name="numOfYears" value="${amortizeloan.numberOfYears}" min="1" max="100" id="numberOfYears">
                            </div>
                                
                            <div class="form-group row">
                                <label for="lender">Lender:</label>
                                  <input class="form-control" type="text" name="lender" value="${amortizeloan.lender}" id="lender">
                            </div>
                                
                            <div class="form-group row">
                                <label for="state">State: </label>
                                  <input class="form-control" type="text" name="state" value="${amortizeloan.state}" id="state">
                            </div>
                                
                            <div class="form-group row">
                                <label for="interestRate">Annual Interest Rate: </label>
                                  <input class="form-control" type="number" name="airVal" value="${amortizeloan.APR}" min="0" max="100" step="0.01" id="interestRate">
                            </div>
                                
                            <div class="form-group row">
                                <label for="amortize">Amortize on Date: </label>
                                  <input class="form-control" type="text" name="amortizeOn" value="${amortizeOn}" required="true" id="amortize">
                            </div>
                                
                            <div class="form-group row">
                                <label for="payoff"> Payoff on Date: </label>
                                  <input class="form-control" type="text" name="payoffOn" value="${payoffOn}" id="payoff">
                            </div>
                            
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>    
                    </div>
                </div>
            </div>

           
	   <c:if test="${not empty amortizeloan}"> 
               <div class="row justify-content-center">
                <div class="card col-10 col-md-8 cardBody">
                   <div class="card-header">
                     <h2>${message}</h2>
                   </div>
                   <div class="card-block">
                       <table class="table table-hover table-bordered">
                           <tr>
                               <td style="width: 40%">Loan Id:</td>
                               <td><h4>${amortizeloan.loanId}</h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Monthly Payment:($)</td>
                               <td><h4>${amortizeloan.monthly}</h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Interest Rate:(%)</td>
                               <td><h4>${amortizeloan.interestRate}</h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Last Interest:($)</td>
                               <td><h4>${amortizeloan.interest}</h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Last Principal:($)</td>
                               <td><h4>${amortizeloan.principal}</h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Loan Amount:($)</td>
                               <td><h4>${amortizeloan.amount}</h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Lender:</td>
                               <td><h4>${amortizeloan.lender}</h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">State:</td>
                               <td><h4>${amortizeloan.state}</h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">APR:(%)</td>
                               <td><h4>${amortizeloan.APR}</h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Number of Years:</td>
                               <td><h4>${amortizeloan.numberOfYears}</h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Payoff Amount:($)</td>
                               <td><h4>${payoffAmount}</h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">As of Payoff Date on:</td>
                               <td><h4>${payoffOn}</h4></td>
                           </tr>
                      </table>
                   </div>
                 </div>
             </div>
	</c:if> 
          
           
	<c:if test="${not empty amortizeloan.loanEntries}">
               <div class="row justify-content-center">
                    <div class="card col-10 col-md-8 cardBody">
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
                <div class="row justify-content-center">
                    <div class="card col-10 col-md-8 cardBody">
                        <div class="card-block">
                            <table>
                                <tr>
                                    <% int total = ((com.ayushi.loan.AmortizedLoan)request.getSession().getAttribute("amortizeloan")).getEntries().size(); 
                                    int pages = total / 12, pgIdx;
                                    for(pgIdx = 0; pgIdx < pages; pgIdx++){%>
                                    <td><a href='/viewloanentries/<%=(pgIdx+1)%>'</a><%=(pgIdx+1)%></td><%}%>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
	   </c:if> 
