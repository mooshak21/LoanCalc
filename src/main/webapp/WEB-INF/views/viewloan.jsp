
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<c:if test="${not empty loans}">
            
                <jsp:include page="/WEB-INF/views/emailForm.jsp">
                    <jsp:param name="dataType" value="amortizedLoan"/>
                </jsp:include>
            
                <div class="row justify-content-center align-items-center">
                    <div class="card col-10 col-md-8 cardBody">
                        <div class="card-block">
                            <table class="table table-hover table-bordered">
                                <tr><td><% int total = ((java.util.List)request.getSession().getAttribute("loans")).size(); 
                                            int pages = total, pgIdx;
                                            for(pgIdx = 0; pgIdx < pages; pgIdx++){%>
                                                    <a href='/viewloan/<%=(pgIdx+1)%>'</a><%=(pgIdx+1)%>
                                            <%}%></td></tr>
                               <tr><td style="width: 40%">Loan Id:</td><td><a href='/viewloanexcel/${amortizeloan.loanId}'>${amortizeloan.loanId}</a></td></tr>
                               <tr><td style="width: 40%">Monthly Payment:($)</td><td><h4>${amortizeloan.monthly}</h4></td></tr>
                               <tr><td style="width: 40%">Interest Rate:(%)</td><td><h4>${amortizeloan.interestRate}</h4></td></tr>
                               <tr><td style="width: 40%">Last Interest:($)</td><td><h4>${amortizeloan.interest}</h4></td></tr>
                               <tr><td style="width: 40%">Last Principal:($)</td><td><h4>${amortizeloan.principal}</h4></td></tr>
                               <tr><td style="width: 40%">Loan Amount:($)</td><td><h4>${amortizeloan.amount}</h4></td></tr>
                               <tr><td style="width: 40%">Lender:</td><td><h4>${amortizeloan.lender}</h4></td></tr>
                               <tr><td style="width: 40%">State:</td><td><h4>${amortizeloan.state}</h4></td></tr>
                               <tr><td style="width: 40%">APR:(%)</td><td><h4>${amortizeloan.APR}</h4></td></tr>
                               <tr><td style="width: 40%">Number of Years:</td><td><h4>${amortizeloan.numberOfYears}</h4></td></tr>
                            </table>
                        </div>
                    </div>
                </div>
	  </c:if>
