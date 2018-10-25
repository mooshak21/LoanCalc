
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
           
            <div class="row justify-content-center">
                <div class="card col-10 col-md-8 cardBody">
                    <div class="card-header">
                        <h5>${message}</h5>
                    </div>
                    <div class="card-block">
                        <form name="loanForm" id="loanform" action="/searchloan" method="POST" onsubmit='if(loanForm.loanAmt.value == ""  && loanForm.numOfYears.value == "" && loanForm.lender.value == "" && loanForm.state.value == "" && loanForm.airVal.value == ""){ alert("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); loanForm.loanAmt.focus(); return false;}'>
                            <div class="form-group row">
                                <label for="loanAmount">Loan Amount:</label>
                                  <input class="form-control resetMe" type="number" name="loanAmt" value="${amortizeloan.amount}" min="1" max="9999999999" id="loanAmount">
                            </div>
                            
                            <div class="form-group row">
                                <label for="numberOfYears">Number of Years:</label>
                                  <input class="form-control resetMe" type="number" name="numOfYears" value="${amortizeloan.numberOfYears}" min="1" max="100" id="numberOfYears">
                            </div>
                                
                            <div class="form-group row">
                                <label for="lender">Lender:</label>
                                  <input class="form-control resetMe" type="text" name="lender" value="${amortizeloan.lender}" id="lender">
                            </div>
                                
                            <div class="form-group row">
                                <label for="state">State: </label>
                                  <input class="form-control resetMe" type="text" name="state" value="${amortizeloan.state}" id="state">
                            </div>
                                
                            <div class="form-group row">
                                <label for="interestRate">Annual Interest Rate: </label>
                                  <input class="form-control resetMe" type="number" name="airVal" value="${amortizeloan.APR}" min="0" max="100" step="0.01" id="interestRate">
                            </div>

                            <div class="form-group row">
                                <label for="amortize">Amortize on Date:*</label>
                                  <input class="form-control resetMe" type="text" name="amortizeOn" value="${amortizeOn}" required="true" id="amortize">
                            </div>
                                
                            <div class="form-group row">
                                <label for="payoff"> Payoff on Date:</label>
                                  <input class="form-control resetMe" type="text" name="payoffOn" value="${payoffOn}" id="payoff">
                            </div>
                            
                             <input type="submit" class="btn btn-default float-left" value="Submit"/>
                            <input  type= "button" class="btn btn-default float-right"  value="Reset" onclick="resetForm()"/> 
                            
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
                               <td style="width: 40%">Monthly Payment:</td>
                               <td><h4><fmt:formatNumber value="${amortizeloan.monthly}" pattern="###,###,###.###" /></h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Interest Rate:(%)</td>
                               <td><h4><fmt:formatNumber value="${amortizeloan.interestRate}" pattern="###,###,###.########"/></h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Last Interest:</td>
                               <td><h4><fmt:formatNumber value="${amortizeloan.interest}" pattern="###,###,###.###" /></h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Last Principal:</td>
                               <td><h4><fmt:formatNumber value="${amortizeloan.principal}" pattern="###,###,###.###" /></h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Loan Amount:</td>
                               <td><h4><fmt:formatNumber value="${amortizeloan.amount}" pattern="###,###,###.###" /></h4></td>
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
                               <td><h4><fmt:formatNumber value="${amortizeloan.APR}" pattern="###,###,###.########"/></h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Number of Years:</td>
                               <td><h4>${amortizeloan.numberOfYears}</h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">Payoff Amount:</td>
                               <td><h4><fmt:formatNumber value="${payoffAmount}" pattern="###,###,###.###" /></h4></td>
                           </tr>
                           <tr>
                               <td style="width: 40%">As of Payoff Date on:</td>
                               <td><h4>${payoffOn}</h4></td>
                           </tr>
                           <tr>
				<td style="width: 40%">Loan Denomination:</td>
				<td><h4>${amortizeloan.loanDenomination}</h4></td>
			   </tr>
                      </table>
                   </div>
                 </div>
             </div>
	</c:if> 
          
           
	<c:if test="${not empty amortizeloan.loanEntries}">
               <div class="row justify-content-center">
                    <div class="card col-10 cardBody">
                         <div class="card-block">
                              <table class="table table-hover table-bordered">
                                  <thead class="thead-default">
                                      <tr>
                                         <th>Date</th>
                                         <th>Principal</th>
                                         <th>Interest</th>
                                         <th>Loan Amount</th>
                                         <th>Monthly</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                     <c:forEach var="entry" items="${amortizeloan.loanEntries}">
                                        <tr>
                                            <td><fmt:formatDate value="${entry.dateEntry.time}" pattern="yyyy/MM/dd"/></td>
                                            <td><fmt:formatNumber value="${entry.principal}" pattern="###,###,###.00"/></td>
                                            <td><fmt:formatNumber value="${entry.interest}" pattern="###,###,###.00"/></td>
                                            <td><fmt:formatNumber value="${entry.loanAmount}" pattern="###,###,###.00"/></td>
                                            <td><fmt:formatNumber value="${entry.monthly}" pattern="###,###,###.00"/></td>
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
                    <div class="card col-10 cardBody">
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

<script>
    function resetForm() {
        $( document ).ready(function() {
            $(".resetMe").val("");
        });

    }
</script>
