<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
	  
       <c:if test="${not empty amortizeloan}"> 

                <jsp:include page="/WEB-INF/views/emailForm.jsp">
                    <jsp:param name="dataType" value="amortizedLoan"/>
                </jsp:include>
               
                <div class="row justify-content-center">
                    <div class="card col-10 col-md-8 cardBody">
                        <div class="card-block">
                            <table class="table table-hover table-bordered">
                                <c:if test="${loans ne null}">
                                    <tr>
                                        <td colspan="2"><% int total = ((java.util.List)request.getSession().getAttribute("loans")).size(); 
                                               int pages = total, pgIdx;
                                               for(pgIdx = 0; pgIdx < pages; pgIdx++){%>
                                                       <a href='/viewloan/<%=(pgIdx+1)%>'</a><%=(pgIdx+1)%>
                                               <%}%>
                                        </td>
                                   </tr>
                                </c:if>
                                <tr>
                                    <td style="width: 40%">Loan Id:</td>
                                    <td>${amortizeloan.loanId}</td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Monthly Payment:</td>
                                    <td><fmt:formatNumber value="${amortizeloan.monthly}" pattern="###,###,###.00"/></td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Interest Rate:(%)</td>
                                    <td><fmt:formatNumber value="${amortizeloan.interestRate}" pattern="###,###,###.########"/></td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Last Interest:</td>
                                    <td><fmt:formatNumber value="${amortizeloan.interest}" pattern="###,###,###.00"/></td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Last Principal:</td>
                                    <td><fmt:formatNumber value="${amortizeloan.principal}" pattern="###,###,###.00"/></td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Loan Amount:</td>
                                    <td><fmt:formatNumber value="${amortizeloan.amount}" pattern="###,###,###.00"/></td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Lender:</td>
                                    <td>${amortizeloan.lender}</td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">State:</td>
                                    <td>${amortizeloan.state}</td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">APR:(%)</td>
                                    <td><fmt:formatNumber value="${amortizeloan.APR}" pattern="###,###,###.########"/></td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Number of Years:</td>
                                    <td>${amortizeloan.numberOfYears}</td>
                                </tr>

                                <tr>
                                    <td style="width: 40%">Loan Type:</td>
                                    <td>${amortizeloan.loanType}</td>
                                </tr>

				                <c:if test="${not empty payoffAmount}">
                                	<tr>
                                    		<td style="width: 40%">Payoff Amount:($)</td>
                                    		<td><fmt:formatNumber value="${payoffAmount}" pattern="###,###,###.00"/></td>
                                	</tr>
                                	<tr>
                                    		<td style="width: 40%">As of Payoff Date on:</td>
                                   		<td>${payoffOn}</td>
                                	</tr>
	                             <tr><td style="width: 40%">Loan Denomination:</td><td><h4>${amortizeloan.loanDenomination}</h4></td></tr>
                                </c:if>
                            </table>
                        </div>
                    </div>
                </div>
	</c:if>	
	  
        <c:if test="${not empty amortizeloan.loanEntries}">
                <div class="row justify-content-center">
                     <div class="card col-10 cardBody">
                        <div class="card-block">
                            <fmt:setLocale value="en_US" scope="application"/>
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
                            <table class="table table-hover table-bordered">
				    <tr><% int total = 0;
					com.ayushi.loan.AmortizedLoan al = (com.ayushi.loan.AmortizedLoan) request.getSession().getAttribute("amortizeloan");
					if(al != null){
						total = ((al.getEntries() != null) ? al.getEntries().size() : 0); 
						int pages = total / 12, pgIdx;
                                            	for(pgIdx = 0; pgIdx < pages; pgIdx++){%>
                                            		<td><a href='/viewloanentries/<%=(pgIdx+1)%>'</a><%=(pgIdx+1)%></td><%}
					}%>
                                </tr>
                            </table>
                         </div>
                     </div>
                 </div>
        
	    </c:if> 
