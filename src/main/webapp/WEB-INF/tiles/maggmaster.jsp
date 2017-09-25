<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${loanEntries1 == null  && loanEntries2 == null }">
    <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-header">
                <h5>${message}</h5>
            </div>
            <div class="card-block">
                <form name="loanSearchForm1" id="form" action="/aggregateloan" method="POST"
                      onsubmit='if(loanSearchForm1.loanAmt.value == ""  && loanSearchForm1.numOfYears.value == "" && loanSearchForm1.lender.value == "" && loanSearchForm1.state.value == "" && loanSearchForm1.airVal.value == ""){ alert("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); loanSearchForm1.loanAmt.focus(); return false;}'>
                    <div class="form-group row">
                        <label for="loanAmt">Loan Amount</label>
                        <input class="form-control" type="number" name="loanAmt" value="${loanAmt}" min="1"
                               max="9999999999" id="loanAmt">
                    </div>

                    <div class="form-group row">
                        <label for="numberOfYears">Number of Years</label>
                        <input class="form-control" type="number" name="numOfYears" value="${numberOfYears}" min="1"
                               max="100" id="numberOfYears">
                    </div>

                    <div class="form-group row">
                        <label for="lender">Lender</label>
                        <input class="form-control" type="text" name="lender" value="${lender}" id="lender">
                    </div>

                    <div class="form-group row">
                        <label for="state">State </label>
                        <input class="form-control" type="text" name="state" value="${state}" id="state">
                    </div>

                    <div class="form-group row">
                        <label for="interestRate">Annual Interest Rate: </label>
                        <input class="form-control" type="number" name="airVal" value="${APR}" min="0" max="100"
                               step="0.01" id="interestRate">
                    </div>

                    <button type="submit" class="btn btn-default">Search</button>
                    <input type="button" value="Reset" class="btn btn-default" onClick="clearFields()">
                </form>

            </div>
        </div>
    </div>
</c:if>

<script type="text/javascript">

    function clearFields() {
        var elmLength = document.getElementById('loanSearchForm1').elements.length;
        for (i = 0; i < elmLength; i++) {
            var typ = document.getElementById('loanSearchForm1').elements[i].type;
            if (typ == "text") {
                document.getElementById('loanSearchForm1').elements[i].value = "";
            }
            else if (typ == "date") {
                document.getElementById('loanSearchForm1').elements[i].value = "";
            } else if (typ == "number") {
                document.getElementById('loanSearchForm1').elements[i].value = "";
            }
        }
        return false;
    }
    // tell the embed parent frame the height of the content
    if (window.parent && window.parent.parent) {
        window.parent.parent.postMessage(["resultsFrame", {
            height: document.body.getBoundingClientRect().height,
            slug: "2zdsyvbv"
        }], "*")
    }
</script>


