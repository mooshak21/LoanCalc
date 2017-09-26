<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${loanEntries1 == null  && loanEntries2 == null }">
    <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-header">
                <h5 >Aggregate Loan</h5>
            </div>
            <div class="card-header">
                <h5 >${message}</h5>
            </div>
            <div  id="message" name="message">

            </div>
            <div class="card-block">
                <form name="loanSearchForm" id="loanSearchForm" action="/aggregateloan" method="POST"
                      onsubmit='if(loanSearchForm.loanId.value == "" && loanSearchForm.loanAmt.value == ""  && loanSearchForm.numOfYears.value == "" && loanSearchForm.lender.value == "" && loanSearchForm.state.value == "" && loanSearchForm.airVal.value == ""){ $("#message").html("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); loanSearchForm.loanAmt.focus(); return false;} else { $("#message").html("");}'>
                    <div class="form-group row">
                        <label for="loanId">Loan Id</label>
                        <input class="form-control resetMe" type="number" name="loanId" value="${loanId}"
                               id="loanId">
                    </div>
                    <div class="form-group row">
                        <label for="loanAmt">Loan Amount</label>
                        <input class="form-control resetMe" type="number" name="loanAmt" value="${loanAmt}" min="1"
                               max="9999999999" id="loanAmt">
                    </div>

                    <div class="form-group row">
                        <label for="numberOfYears">Number of Years</label>
                        <input class="form-control resetMe" type="number" name="numOfYears" value="${numberOfYears}" min="1"
                               max="100" id="numberOfYears">
                    </div>

                    <div class="form-group row">
                        <label for="lender">Lender</label>
                        <input class="form-control resetMe" type="text" name="lender" value="${lender}" id="lender">
                    </div>

                    <div class="form-group row">
                        <label for="state">State </label>
                        <input class="form-control resetMe" type="text" name="state" value="${state}" id="state">
                    </div>

                    <div class="form-group row">
                        <label for="interestRate">Annual Interest Rate: </label>
                        <input class="form-control resetMe" type="number" name="airVal" value="${APR}" min="0" max="100"
                               step="0.01" id="interestRate">
                    </div>

                    <button type="submit" class="btn btn-default">Search</button>
                    <input  type= "button" class="btn btn-default"  value="Reset" onclick="resetForm()"/>
                </form>

            </div>
        </div>
    </div>
</c:if>
<script type="text/javascript">

    function resetForm() {
        $( document ).ready(function() {
            $(".resetMe").val("");
        });
    }
</script>


