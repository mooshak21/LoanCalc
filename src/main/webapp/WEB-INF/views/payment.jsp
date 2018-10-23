
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

   <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-header">
              <h5>${message}</h5>
            </div>
            <div class="card-block">
                <form name="paymentForm" id="paymentform" action="/vieweditpayment" method="POST" >

                    <div class="form-group">
                        <label for="paymentType">Payment Type: </label>
                        <select  class="form-control resetMe" id="paymentType" name="paymentType">

                                <option value="PayPal" selected>PayPal</option>
                                <option value="Stripe">Stripe</option>

                        </select>
                    </div>

                   <div class="form-group">
                       <label for="paypalAcctNum">PayPal Account Number:</label>
                       <input class="form-control resetMe"  name="paypalAcctNum" value="${payPalAccountNumber}" id="paypalAcctNum">
                   </div>

                   <div class="form-group">
                       <label for="paypalEmailAddress">PayPal Email Address:</label>
                       <input class="form-control resetMe" type="text" name="paypalEmailAddress" value="${payPalEmailAddress}" id="paypalEmailAddress">
                   </div>

                   <div class="form-group">
                       <label for="paymentStartDate">Payment Start Date: </label>
                        <input class="form-control resetMe" type="date" placeholder="dd-MM-yyyy" name="paymentStartDate" value="${paymentStartDate}" id="paymentStartDate">
                   </div>

                   <div class="form-group">
                       <label for="paymentEndDate">Payment End Date: </label>
                       <input class="form-control resetMe" type="date" placeholder="dd-MM-yyyy" name="paymentEndDate" value="${paymentEndDate}" id="paymentEndDate">
                   </div>

                   <div class="form-group">
                       <label for="paymentAmount">Payment Amount: </label>
		       <input class="form-control resetMe" type="number" name="paymentAmount" value="${paymentAmount}" id="paymentAmount">
                   </div>

                   
                    <div class="form-group">
                        <label for="paymentFrequency">Payment Frequency: </label>
                        <select  class="form-control resetMe" id="paymentFrequency" name="paymentFrequency">
                                <option value="NoRemind" selected>No Remind</option>
                                <option value="Weekly">Weekly</option>
                                <option value="Monthly">Monthly</option>
                                <option value="Quarterly" >Quarterly</option>
                                <option value="Semi-Annually" >Semi-Annually</option>
                                <option value="Annually" >Annually</option>

                        </select>
                    </div>

                    <div class="form-group">
                        <label for="balanceAmount">Balance Amount: </label>
                        <input class="form-control resetMe" type="number" name="balanceAmount" value="${balanceAmount}" id="balanceAmount">
                    </div>

                    <div class="form-group">
                        <label for="payPalAuthPersonName">PayPal Authorization Person Name :</label>
                        <input class="form-control resetMe" type="text" name="payPalAuthPersonName" value="${payPalAuthPersonName}" id="payPalAuthPersonName">
                    </div>

                    <div class="form-group">
                        <label for="payPalAuthPersonName">PayPal Password:</label>
                        <input class="form-control resetMe" type="password" name="payPalPassword" value="${payPalPassword}" id="payPalPassword">
                    </div>

		<div class="card-block">
                </div>   

                  <input type="submit" class="btn btn-default float-left" value="Submit"/>
                  <input  type= "button" class="btn btn-default float-right"  value="Reset" onclick="resetForm()"/>
                </form>
            </div>
        </div>
    </div>


 <script>
function resetForm() {
    $( document ).ready(function() {
        $(".resetMe").val("");
    });
    
};

</script>
