<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


    <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-header">
                <h5 >${message}</h5>
            </div>
            <div  id="message1" name="message1">

            </div>
            <div class="card-block">
                <form name="aggregateLoanReportForm1" id="aggregateLoanReportForm1" action="/aggregateloanreport" method="POST"
                      onsubmit='if(aggregateLoanReportForm1.loanId.value == "" && aggregateLoanReportForm1.loanAmt.value == ""  && aggregateLoanReportForm1.numOfYears.value == "" && aggregateLoanReportForm1.lender.value == "" && aggregateLoanReportForm1.state.value == "" && aggregateLoanReportForm1.APR.value == ""){ $("#message1").html("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); aggregateLoanReportForm1.loanAmt.focus(); return false;} else { $("#message1").html("");}'>
                    <div class="form-group row">
                        <label for="loanId">Loan Id:</label>
                        <input class="form-control resetMe" type="number" name="loanId" value="${loanId}"
                               id="loanId">
                        <input type="hidden" name="loanAggId" value="${loanAggId}"
                               id="loanAggId">
                    </div>
                    <div class="form-group row">
                        <label for="loanAmt">Loan Amount:</label>
                        <input class="form-control resetMe" type="number" name="loanAmt" value="${loanAmt}" min="1"
                               max="9999999999" id="loanAmt">
                    </div>

                    <div class="form-group row">
                        <label for="numberOfYears">Number of Years:</label>
                        <input class="form-control resetMe" type="number" name="numOfYears" value="${numberOfYears}" min="1"
                               max="100" id="numberOfYears">
                    </div>

                    <div class="form-group row">
                        <label for="lender">Lender:</label>
                        <input class="form-control resetMe" type="text" name="lender" value="${lender}" id="lender">
                    </div>

                    <div class="form-group row">
                        <label for="state">State: </label>
                        <input class="form-control resetMe" type="text" name="state" value="${state}" id="state">
                    </div>

                    <div class="form-group row">
                        <label for="APR">Annual Interest Rate: </label>
                        <input class="form-control resetMe" type="number" name="APR" value="${APR}" min="0" max="100"
                               step="0.01" id="APR">
                    </div>

                    <button type="submit" class="btn btn-default float-left">Search</button>
                    <input  type= "button" class="btn btn-default float-right"  value="Reset" onclick="resetForm()"/>
                </form>
            </div>
            <c:if test="${loanAggId != null}">
                <a onclick="generateReportMobile(${loanAggId});">Generate Report</a>
            </c:if>

        </div>
        <div id="jasperReportMobile"></div>
    </div>


<script type="text/javascript">

    function resetForm() {
        $( document ).ready(function() {
            $(".resetMe").val("");
        });
    }

    function generateReportMobile(value){
        $.ajax({
            url:"/generateReport?loanAggId="+ value,
            type: 'GET',
            success: function(html){
                $("#jasperReportMobile").empty();
                $("#jasperReportMobile").append(html);
            }
        });
    }
</script>


