<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>


<div class="row justify-content-center">
    <div class="card col-10 col-md-8 cardBody">
        <div class="card-header">
            <h5>${message}</h5>
        </div>
        <div class="card-block">
            <c:if test="${empty amortizeloan}">
            <form name="loanForm" id="loanform" action="/searchloan" method="POST"
                  onsubmit='if(loanForm.loanAmt.value == ""  && loanForm.numOfYears.value == "" && loanForm.lender.value == "" && loanForm.state.value == "" && loanForm.airVal.value == ""){ alert("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); loanForm.loanAmt.focus(); return false;}'>
                </c:if>
                <c:if test="${not empty amortizeloan}">
                <form name="loanForm" id="loanform" action="/updateloan" method="POST"
                      onsubmit='if(loanForm.loanAmt.value == ""  && loanForm.numOfYears.value == "" && loanForm.lender.value == "" && loanForm.state.value == "" && loanForm.airVal.value == ""){ alert("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); loanForm.loanAmt.focus(); return false;}'>
                    </c:if>
                    <div class="form-group">
                        <label for="loanAmount">Loan Amount:</label>
                        <input class="form-control resetMe" type="number" name="loanAmt" value="${amortizeloan.amount}"
                               min="1" max="9999999999" id="loanAmount">
                        <input type="hidden" name="loanId" value="${amortizeloan.loanId}" id="loanId">
                    </div>

                    <div class="form-group">
                        <label for="numberOfYears">Number of Years:</label>
                        <input class="form-control resetMe" type="number" name="numOfYears"
                               value="${amortizeloan.numberOfYears}" min="1" max="100" id="numberOfYears">
                    </div>

                    <div class="form-group">
                        <label for="lender">Lender:</label>
                        <input class="form-control resetMe" type="text" name="lender" value="${amortizeloan.lender}"
                               id="lender">
                    </div>

                    <div class="form-group">
                        <label for="state">State: </label>
                        <input class="form-control resetMe" type="text" name="state" value="${amortizeloan.state}"
                               id="state">
                    </div>

                    <div class="form-group">
                        <label for="interestRate">Annual Interest Rate: </label>
                        <input class="form-control resetMe" type="number" name="airVal" value="${amortizeloan.APR}"
                               min="0" max="100" step="0.01" id="interestRate">
                    </div>
                    <div class="form-group">
                        <label for="loanType">Loan Type:</label>
                        <select class="form-control resetMe" name="loanType" id="loanType">
                            <option value="">Choose a Loan Type</option>
                            <option value="Student Loan"  ${amortizeloan.loanType == 'Student Loan' ? 'selected' : ''}>
                                Student Loan
                            </option>
                            <option value="Auto Loan"  ${amortizeloan.loanType == 'Auto Loan' ? 'selected' : ''}>Auto
                                Loan
                            </option>
                            <option value="Home Loan" ${amortizeloan.loanType == 'Home Loan' ? 'selected' : ''}>Home
                                Loan
                            </option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="amortize">Amortize on Date:* </label>
                        <input class="form-control resetMe" type="text" name="amortizeOn" value="${amortizeOn}"
                               required="true" id="amortize">
                    </div>

                    <div class="form-group">
                        <label for="payoff"> Payoff on Date: </label>
                        <input class="form-control resetMe" type="text" name="payoffOn" value="${payoffOn}" id="payoff">
                    </div>
                    <div class="form-group">
                        <label for="email">Email: </label>
                        <input class="form-control resetMe" type="email" name="email" value="${userEmail}" id="email">
                    </div>
                    <c:if test="${loanId ne null && loanId > 0}">
                        <button type="button" class="btn btn-default float-left" style="margin-left: 5px"
                                onclick="location.href = '/quickview'">Quick View ${LoanId}</button>
                    </c:if>
                    <c:if test="${empty amortizeloan}">
                        <input type="submit" class="btn btn-default float-left" value="Submit"/>
                    </c:if>
                    <c:if test="${not empty loans}">
                        <input type="button" style="margin: 1px" class="btn btn-default float-left" value="Search" onclick="searchPage()"/>
                    </c:if>
                    <c:if test="${not empty loans}">
                        <input type="submit" class="btn btn-default float-left" value="Update"/>
                    </c:if>
                    <c:if test="${not empty loans}">
                        <input type="button" style="margin: 1px" class="btn btn-default float-right" value="Delete"
                               onclick="setDeleteValue(${amortizeloan.loanId})"/>
                    </c:if>
                        <input type="button" class="btn btn-default float-right" value="Reset" onclick="resetForm()"/>


                </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    function resetForm() {
        $(document).ready(function () {
            $(".resetMe").val("");
        });

    }

    function setDeleteValue(value) {
        var x = confirm("Are you sure you want to delete?");
        if (x) {
            $.ajax({
                url: "/deleteloan?loanId=" + value,
                type: 'DELETE',
                cache: false,
                success: function (html) {
                    alert("Deleted Successfully");
                    resetForm();
                    window.location= 'loansearchask';
                }
            });
        }
    }

    function searchPage(){
        $.ajax({
            url: "/searchloan",
            type: 'GET',
            cache: false,
            success: function (html) {
                resetForm();
                window.location = 'loansearchask';
            }
        });

    }
</script>