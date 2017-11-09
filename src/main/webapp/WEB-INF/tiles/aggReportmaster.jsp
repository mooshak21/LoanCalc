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
            <div  id="message" name="message"></div>
            <div class="card-block">
                <form name="aggregateLoanReportForm" id="aggregateLoanReportForm" action="/aggregateloanreport" method="POST"
                      onsubmit='if(aggregateLoanReportForm.loanId.value == "" && aggregateLoanReportForm.loanAmt.value == ""  && aggregateLoanReportForm.numOfYears.value == "" && aggregateLoanReportForm.lender.value == "" && aggregateLoanReportForm.state.value == "" && aggregateLoanReportForm.APR.value == ""){ $("#message").html("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); aggregateLoanReportForm.loanAmt.focus(); return false;} else { $("#message").html("");}'>
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
                     <div id="report" style="text-align: center;font-size: 16px;color: #4f5f6f;padding-bottom: 17px; ">Total Number of Aggregated Loans : ${NoOfLoansInRelation}</div>
                    <div id="reportMessageDiv" style="text-align: center;font-size: 20px;font-weight: bold;color: #4f5f6f;">Please wait your report is being generated..</div>
            <script>
                generateReportWeb(${loanAggId});
                function generateReportWeb(value){

                    $.ajax({
                        url:"/generateReport?loanAggId="+ value,
                        type: 'GET',
                        success: function(html){
                            $("#jasperReportWeb").empty();
                            $("#jasperReportWeb").append(html);
                            $("#reportMessageDiv").hide();
                        }
                    });
                }
            </script>
        </c:if>
            <div id="jasperReportWeb"></div>

        </div>

    </div>


<script type="text/javascript" >

    function resetForm() {
        $( document ).ready(function() {
            $(".resetMe").val("");
        });
    }



</script>


