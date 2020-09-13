<%--
  Created by IntelliJ IDEA.
  User: Garima
  Date: 4/23/2019
  Time: 10:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row justify-content-center">
    <div class="card col-10 col-md-8 cardBody">
        <div class="card-header">
            <h5>${message}</h5>
        </div>
        <div class="card-block">
            <form name="equityForm" id="equityform" action="/calculateEquity" method="POST" onsubmit='if(equityForm.loanType.value == ""){ alert("Please enter Loan Type"); loanForm.loanAmt.focus(); return false;}'>
                <div class="form-group">
                    <label for="loanId">Loan Id:*</label>
                    <input class="form-control resetMe" type="number" name="loanId" value="${loanId}"
                           id="loanId" required="true">
                </div>
                <div class="form-group">
                    <label for="valuationDate">Loan Start Date:*</label>
                    <input class="form-control resetMe" type="text" name="loanStartDate" value="${loanStartDate}"
                           id="loanStartDate" required="true">
                </div>
                <div class="form-group">
                    <label for="valuationDate">Valuation Date:*</label>
                    <input class="form-control resetMe" type="text" name="valuationDate" value="${valuationDate}"
                           id="valuationDate" required="true">
                </div>
                <input type="submit" class="btn btn-default float-left" value="Submit"/>
                <input  type= "button" class="btn btn-default float-right"  value="Reset" onclick="resetForm()"/>

            </form>
        </div>
    </div>
</div>

<c:if test="${(not empty userEmail) and ((Plan == '9.99') or (Plan == '19.99'))}">
<div>
    <iframe src="${linkUrl}" style="width:100%;  height:600px" frameborder="2">
    </iframe>
</div>
</c:if>
