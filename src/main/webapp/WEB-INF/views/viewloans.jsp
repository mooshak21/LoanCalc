
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<c:if test="${not empty loans}">
        	<jsp:include page="/WEB-INF/views/emailForm.jsp">
            		<jsp:param name="dataType" value="amortizedLoan"/>
	        </jsp:include>
              <div class="row justify-content-center">
                    <div class="card col-10 col-md-8 cardBody">
                        <div class="card-block">
                            <table class="table table-hover table-bordered">
<%--                                <tr>
                                    <td colspan="2"><% int total = ((java.util.List)request.getSession().getAttribute("loans")).size(); 
                                               int pages = total, pgIdx;
                                               for(pgIdx = 0; pgIdx < pages; pgIdx++){%>
                                               <a href='/viewloan/<%=(pgIdx+1)%>'</a><%=(pgIdx+1)%><%}%></td>
                                </tr>--%>
                                <c:if test="${loans.size() > 0}">
                                    <tr>
                                        <td colspan="2">
                                            <c:forEach items="${loans}" varStatus="status">
                                                <a href="/viewloan/${status.index + 1}">${status.index + 1}</a>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:if>                
                               <tr><td style="width: 40%">Loan Id:</td><td>${amortizeloan.loanId}</td></tr>
                               <tr><td style="width: 40%">Monthly Payment:</td><td><fmt:formatNumber value="${amortizeloan.monthly}" pattern="###,###,###.###" /></td></tr>
                               <tr><td style="width: 40%">Interest Rate:(%)</td><td><fmt:formatNumber value="${amortizeloan.interestRate}" pattern="###,###,###.########"/></td></tr>
                               <tr><td style="width: 40%">Last Interest:</td><td><fmt:formatNumber value="${amortizeloan.interest}" pattern="###,###,###.###" /></td></tr>
                               <tr><td style="width: 40%">Last Principal:</td><td><fmt:formatNumber value="${amortizeloan.principal}" pattern="###,###,###.###" /></td></tr>
                               <tr><td style="width: 40%">Loan Amount:</td><td><fmt:formatNumber value="${amortizeloan.amount}" pattern="###,###,###.###" /></td></tr>
                               <tr><td style="width: 40%">Lender:</td><td>${amortizeloan.lender}</td></tr>
                               <tr><td style="width: 40%">State:</td><td>${amortizeloan.state}</td></tr>
                               <tr><td style="width: 40%">APR:(%)</td><td><fmt:formatNumber value="${amortizeloan.APR}" pattern="###,###,###.########"/></td></tr>
                               <tr><td style="width: 40%">Number of Years:</td><td>${amortizeloan.numberOfYears}</td></tr>
                                <tr><td style="width: 40%">Loan Type:</td><td>${amortizeloan.loanType}</td></tr>
                                <tr><td style="width: 40%">Loan Denomination:</td><td>${amortizeloan.loanDenomination}</td></tr>
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
         
