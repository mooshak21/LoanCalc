
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<c:if test="${not empty loans}">
            
                <jsp:include page="/WEB-INF/views/emailForm.jsp">
                    <jsp:param name="dataType" value="amortizedLoan"/>
                </jsp:include>
            
                <div class="row justify-content-center align-items-center">
                    <div class="card col-10 col-md-8 cardBody">
                        <div class="card-block">
                            <table class="table table-hover table-bordered">
                                <c:if test="${loans.size() > 0}">
                                    <tr>
                                        <td colspan="2">
                                            <c:forEach items="${loans}" varStatus="status">
                                                <a href="/viewloan/${status.index + 1}">${status.index + 1}</a>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:if> 
                               <tr><td style="width: 40%">Loan Id:</td><td><a href='/viewloanexcel/${amortizeloan.loanId}'>${amortizeloan.loanId}</a></td></tr>
                               <tr><td style="width: 40%">Monthly Payment:($)</td><td><h4><fmt:formatNumber value="${amortizeloan.monthly}" pattern="###,###,###.###" /></h4></td></tr>
                               <tr><td style="width: 40%">Interest Rate:(%)</td><td><h4><fmt:formatNumber value="${amortizeloan.interestRate}" pattern="###,###,###.########"/></h4></td></tr>
                               <tr><td style="width: 40%">Last Interest:($)</td><td><h4><fmt:formatNumber value="${amortizeloan.interest}" pattern="###,###,###.###" /></h4></td></tr>
                               <tr><td style="width: 40%">Last Principal:($)</td><td><h4><fmt:formatNumber value="${amortizeloan.principal}" pattern="###,###,###.###" /></h4></td></tr>
                               <tr><td style="width: 40%">Loan Amount:($)</td><td><h4><fmt:formatNumber value="${amortizeloan.amount}" pattern="###,###,###.###" /></h4></td></tr>
                               <tr><td style="width: 40%">Lender:</td><td><h4>${amortizeloan.lender}</h4></td></tr>
                               <tr><td style="width: 40%">State:</td><td><h4>${amortizeloan.state}</h4></td></tr>
                               <tr><td style="width: 40%">APR:(%)</td><td><h4><fmt:formatNumber value="${amortizeloan.APR}" pattern="###,###,###.########"/></h4></td></tr>
                               <tr><td style="width: 40%">Number of Years:</td><td><h4>${amortizeloan.numberOfYears}</h4></td></tr>
                            </table>
                            
                           
                        </div>
                    </div>
                </div>
	  </c:if>
        
         <c:if test="${not empty amortizeloan.loanEntries}">
               <div class="row justify-content-center ">
                    <div class="card col-10 cardBody">
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
                                            <td><fmt:formatDate value="${entry.dateEntry.time}" pattern="yyyy/MM/dd"/></td>
                                            <td><fmt:formatNumber value="${entry.principal}" pattern="###,###,###.00"/></td>
                                            <td><fmt:formatNumber value="${entry.interest}" pattern="###,###,###.00"/></td>
                                            <td><fmt:formatNumber value="${entry.loanAmount}" pattern="###,###,###.00"/></td>
                                            <td><fmt:formatNumber value="${entry.monthly}" pattern="###,###,###.00"/></td>
                                        </tr> 
                                    </c:forEach>
                                </tbody>
                            </table>
                           
                             <c:if test="${amortizeloan.entries ne null}" >
                                <table class="table table-hover table-bordered">
                                    <tr>
                                    <c:forEach begin="1" end="${amortizeloan.entries.size() / 12}" step="1" varStatus="status">
                                          <td><a href='/viewloanentries/${status.index}'</a>${status.index}</td>  
                                    </c:forEach>
                                    </tr>
                                </table>
                            </c:if>
                       
                        </div>
                    </div>
                </div>
	   </c:if> 
