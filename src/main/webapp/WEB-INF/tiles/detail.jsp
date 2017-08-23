<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
	  
       <c:if test="${not empty amortizeloan}"> 
                <div class="row justify-content-center">
                    <div class="card col-10 col-md-8 cardBody">
                        <div class="card-block">
                            <table class="table table-hover table-bordered">
                                <tr>
                                    <td style="width: 40%">Loan Id:</td>
                                    <td>${amortizeloan.loanId}</td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Monthly Payment:($)</td>
                                    <td>${amortizeloan.monthly}</td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Interest Rate:(%)</td>
                                    <td>${amortizeloan.interestRate}</td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Last Interest:($)</td>
                                    <td>${amortizeloan.interest}</td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Last Principal:($)</td>
                                    <td>${amortizeloan.principal}</td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Loan Amount:($)</td>
                                    <td>${amortizeloan.amount}</td>
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
                                    <td>${amortizeloan.APR}</td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Number of Years:</td>
                                    <td>${amortizeloan.numberOfYears}</td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">Payoff Amount:($)</td>
                                    <td>${payoffAmount}</td>
                                </tr>
                                <tr>
                                    <td style="width: 40%">As of Payoff Date on:</td>
                                    <td>${payoffOn}</td>
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
                            <fmt:setLocale value="en_US" scope="application"/>
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
