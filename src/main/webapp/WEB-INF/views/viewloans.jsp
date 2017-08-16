
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	   <c:if test="${not empty loans}">
               <div class="row justify-content-md-center align-items-center">
                    <div class="card col-12 col-md-8 cardBody">
                        <div class="card-block">
                            <table class="table table-hover table-bordered">
                                <tr><td><% int total = ((java.util.List)request.getSession().getAttribute("loans")).size(); 
                                            int pages = total, pgIdx;
                                            for(pgIdx = 0; pgIdx < pages; pgIdx++){%>
                                            <a href='/viewloan/<%=(pgIdx+1)%>'</a><%=(pgIdx+1)%><%}%></td></tr>
                               <tr><td>Loan Id:</td><td><h2>${amortizeloan.loanId}</h2></td></tr>
                               <td>Monthly Payment:($)</td><td><h2>${amortizeloan.monthly}</h2></td></tr>
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
                                        <tr><td>${entry.dateEntry.time}</td><td>${entry.principal}</td><td>${entry.interest}</td><td>${entry.loanAmount}</td><td>${entry.monthly}</td></tr> 
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
	   </c:if>
