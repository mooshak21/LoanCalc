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
            <form name="equityForm" id="equityform" action="/equityHistory" method="POST" onsubmit='if(equityForm.loanId.value == ""){ alert("Please enter Loan Id"); loanForm.loanAmt.focus(); return false;}'>
                <div class="form-group">
                    <label for="loanId">Loan Id:*</label>
                    <input class="form-control resetMe" type="number" name="loanId" value="${loanId}"
                           id="loanId" required="true">
                </div>
                <input type="submit" class="btn btn-default float-left" value="Submit"/>
                <input  type= "button" class="btn btn-default float-right"  value="Reset" onclick="resetForm()"/>

            </form>
        </div>
        <div class="table-responsive">
            <table class="table table-bordered" id="table1">
                <thead class="thead-default">
                <tr>
                    <th>Equity Value ($)</th>
                    <th>Valuation Date</th>
                    <th>Equity Percent (%)</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${equityHistory}">
                <tr>
                    <c:forEach items="${entry}" var="map">
                            <td><c:out value="${map.value}"/></td>
                    </c:forEach>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
