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
                <form name="loanSearchForm" id="form" action="/aggregateloan" method="POST"
                      onsubmit='if(loanSearchForm.loanAmt.value == ""  && loanSearchForm.numOfYears.value == "" && loanSearchForm.lender.value == "" && loanSearchForm.state.value == "" && loanSearchForm.airVal.value == ""){ alert("Please enter at least Loan Amount, Number of Years, Lender, State, APR"); loanSearchForm.loanAmt.focus(); return false;}'>
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
                    <input type="button" value="Clear" class="btn btn-default" onClick="clearFields()">
                </form>

            </div>
        </div>
    </div>
</c:if>
<c:if test="${loanEntries1 != null  || loanEntries2 != null }">
    <form name="loanAggregateForm" id="form1" action="/updateaggregate" method="POST"
          onsubmit='if(loanAggregateForm.name.value == ""  && loanAggregateForm.type.value == "" && loanAggregateForm.email.value == "" && loanAggregateForm.startDate.value == "" && loanAggregateForm.term.value == ""){ alert("Please enter at least Name, Loan Type, Email, Start Date, Loan Term"); loanAggregateForm.name.focus(); return false;}'>
    <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">

            <div class="card-header">
                <h5>${message}</h5>
            </div>
            <div class="card-block">

                    <div class="form-group row" style="display: none;">
                        <label for="loanAggId">Loan Agg Id</label>
                        <input class="form-control" type="text" name="loanAggId" value="${loanAggId}" id="loanAggId">
                    </div>

                    <div class="form-group row">
                        <label for="name">Loan Name</label>
                        <input class="form-control" type="text" name="name" value="${name}" id="name">
                    </div>

                    <div class="form-group row">
                        <label for="type">Loan Type</label>
                        <select class="form-control" name="type" id="type">
                            <option value="">Choose a Loan Type</option>
                            <option value="Student Loan">Student Loan</option>
                            <option value="Auto Loan">Auto Loan</option>
                            <option value="Home Loan">Home Loan</option>
                        </select>
                    </div>

                    <div class="form-group row">
                        <label for="email">Loanee Email Address</label>
                        <input class="form-control" type="text" name="email" value="${email}" id="email">
                    </div>

                    <div class="form-group row">
                        <label for="startDate">Start Date </label>
                        <input class="form-control" type="text" name="startDate" value="${startDate}" required="true"
                               id="startDate">
                    </div>

                    <div class="form-group row">
                        <label for="term">Loan Term</label>
                        <input class="form-control" type="number" name="term" value="${term}" min="0" max="100" step="1"
                               id="term" readonly="readonly">
                    </div>

                    <input class="form-control" name="loanIds" type="hidden" id="loanIds">
                    <input class="form-control" name="loansId" type="hidden" id="loansId">

                    <button type="submit" class="btn btn-default">Save Aggregation</button>
                    <input type="button" value="Clear" class="btn btn-default" onClick="clearFields()">
                    <button onclick="location.href='/aggregateloanask'" type="button" class="btn btn-default">Search</button>

                <script>
                    function clearFields() {
                        var elmLength = document.getElementById('form1').elements.length;
                        for (i = 0; i < elmLength; i++) {
                            var typ = document.getElementById('form1').elements[i].type;
                            if (typ == "text") {
                                document.getElementById('form1').elements[i].value = "";
                            }
                            else if (typ == "date") {
                                document.getElementById('form1').elements[i].value = "";
                            } else if (typ == "number") {
                                document.getElementById('form1').elements[i].value = "";
                            }
                        }
                        return false;
                    }
                </script>
            </div>
        </div>

    </div>

    <style>
        thead tr th,thead tr td{
            text-align: center;
        }

    </style>
    <div class="row">
        <div class="col-sm-5 table-responsive">
                <table class="table table-bordered" id="table1">
                    <thead class="thead-default">
                        <tr>
                            <th>Sel</th>
                            <th>Loan Id</th>
                            <th>Amount</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entry" items="${loanEntries1}">
                        <tr>
                            <td><input  value="${entry.loanId}" name="loans" type="checkbox" class="checkbox"></td>
                            <td><c:out value="${entry.loanId}"/></td>
                            <td><c:out value="${entry.amount}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
        </div>
        <div class="col-sm-2" style="padding-top: 50px;">
                <p class="text-center">
                    <input class="btn btn-sm btn-primary" type="button" id="aggregate" value="aggregate" onclick="aggregate1()">
                </p>
                <p class="text-center">
                    <input class="btn btn-sm btn-primary" type="button" id="deaggregate" value="deaggregate" onclick="deaggregate1()">
                </p>
        </div>
        <div class="col-sm-5 table-responsive">
                        <table id="table2" class="table table-bordered">
                            <thead class="thead-default">
                            <tr>
                                <th>Sel</th>
                                <th>Loan Id</th>
                                <th>Amount</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="entry" items="${loanEntries2}">
                                <tr>
                                    <td><input value="${entry.loanId}" name="loan" type="checkbox" class="checkbox"></td>
                                    <td><c:out value="${entry.loanId}"/></td>
                                    <td><c:out value="${entry.amount}"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
        </div>
    </div>

    </form>

</c:if>

<c:if test="${not empty totalAmount}">
    <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-block">
                <h5 align="center"> Loan Aggregate Summary  </h5>
                <table class="table table-hover table-bordered">
                    <tr><td style="width: 40%">Total Amount:</td><td><h4>${totalAmount}</h4></td></tr>
                    <tr><td style="width: 40%">Amount Paid:</td><td><h4>${amountPaid}</h4></td></tr>
                    <tr><td style="width: 40%">Remaining Amount:</td><td><h4>${remainingAmount}</h4></td></tr>
                    <tr><td style="width: 40%">Remaining Percent:</td><td><h4>${remainingPercent}%</h4></td></tr>
                    <tr><td style="width: 40%">Maximum Term:</td><td><h4>${maximumNumOfYears}</h4></td></tr>
                    <tr><td style="width: 40%">PayOff Date:</td><td><h4>${payoff}</h4></td></tr>
                    <tr><td style="width: 40%">Start Date:</td><td><h4>${startDate}</h4></td></tr>
                </table>
            </div>
        </div>
    </div>
</c:if>

<script type="text/javascript">
    setLoanVal();
    function setLoanVal(){
        var loan = [];
        var loans = [];
        $("#table2 input:checkbox").each(function(obj){
            loan.push($(this).val());
        })
        $("#loanIds").val(JSON.stringify(loan));
        $("#table1 input:checkbox").each(function(obj){
            loans.push($(this).val());
        })
        $("#loansId").val(JSON.stringify(loans));
    }

    function aggregate1() {
        var row = $('#table1 tbody tr td input.checkbox:checked').closest('tr').clone();
        console.log("==>", row);
        $('#table2 tbody').append(row);
        $('#table1 tbody tr td input.checkbox:checked').closest('tr').remove();
        setLoanVal();
    }

    function deaggregate1() {
        var row = $('#table2 tbody tr td input.checkbox:checked').closest('tr').clone();
        console.log("rows:", row);

//            var row = $('#table2 tbody tr td input.checkbox:checked').parent().clone();
        $('#table1 tbody').append(row);
        $('#table2 tbody tr td input.checkbox:checked').closest('tr').remove();
        setLoanVal();

    }
    function clearFields() {
        var elmLength = document.getElementById('form').elements.length;
        for (i = 0; i < elmLength; i++) {
            var typ = document.getElementById('form').elements[i].type;
            if (typ == "text") {
                document.getElementById('form').elements[i].value = "";
            }
            else if (typ == "date") {
                document.getElementById('form').elements[i].value = "";
            } else if (typ == "number") {
                document.getElementById('form').elements[i].value = "";
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

