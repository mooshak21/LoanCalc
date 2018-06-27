
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
                <c:if test="${empty newsObject}">
                    <form name="siteForm" id="siteform" action="/updatesiteoffers" method="POST" onsubmit='if(siteForm.bankName.value == ""){ alert("Please enter Bank Name"); siteForm.bankName.focus(); return false;}'>
                </c:if>
                <c:if test="${not empty newsObject}">
                    <form name="siteForm" id="siteform" action="/updatesiteoffrevalues" method="POST" onsubmit='if(siteForm.bankName.value == ""){ alert("Please enter Bank Name"); siteForm.bankName.focus(); return false;}'>
                </c:if>
                    <input type="hidden" name="offerId" value="${newsObject.offerId}" id="offerId">
                     <div class="form-group">
                         <label for="bankName">Bank Name: </label>
                         <input class="form-control resetMe" type="text" name="bankName" value="${newsObject.bankName}" id="bankName" required="true">
                     </div>

                     <div class="form-group">
                         <label for="linkUrl">Offer URL</label>
                         <input class="form-control resetMe" type="text" name="linkUrl" value="${newsObject.linkUrl}" min="1" max="100" id="linkUrl" required="true">
                     </div>

                     <div class="form-group">
                         <label for="newsType">News Type:</label>
                         <select class="form-control resetMe" name="newsType" required="true" id="newsType" >
                         <option value="">Choose News Type</option>
                         <option value="Bank Offer" ${newsObject.newsType == 'Bank Offer' ? 'selected' : ''}>Bank Offer</option>
                         <option value="News Site"  ${newsObject.loanType == 'News Site' ? 'selected' : ''}>News Site</option>
                     </select>
                     </div>
                     <div class="form-group">
                         <label for="loanType">Loan Type:</label>
                         <select class="form-control resetMe" name="loanType" required="true" id="loanType" >
                             <option value="">Choose a Loan Type</option>
                             <option value="Student Loan"  ${newsObject.loanType == 'Student Loan' ? 'selected' : ''}>Student Loan</option>
                             <option value="Auto Loan"  ${newsObject.loanType == 'Auto Loan' ? 'selected' : ''}>Auto Loan</option>
                             <option value="Home Loan" ${newsObject.loanType == 'Home Loan' ? 'selected' : ''}>Home Loan</option>
                         </select>
                     </div>

                     <div class="form-group">
                         <label for="region">Region:</label>
                         <select class="form-control resetMe" name="region" required="true" id="region" >
                             <option value="">Choose Region</option>
                             <option value="USA"  ${newsObject.region == 'USA' ? 'selected' : ''}>USA</option>
                             <option value="India"  ${newsObject.region == 'India' ? 'selected' : ''}>India</option>
                             <option value="Japan" ${newsObject.region == 'Japan' ? 'selected' : ''}>Japan</option>
                             <option value="China"  ${newsObject.region == 'China' ? 'selected' : ''}>China</option>
                             <option value="UK"  ${newsObject.region == 'UK' ? 'selected' : ''}>UK</option>
                             <option value="South Africa" ${newsObject.region == 'South Africa' ? 'selected' : ''}>South Africa</option>
                         </select>
                     </div>

                     <div class="form-group">
                         <label for="offerStartDate">Offer Start Date: </label>
                         <input class="form-control resetMe" type="text" name="offerStartDate" value="${offerStartDate}" id="offerStartDate" >
                     </div>

                     <div class="form-group">
                         <label for="offerEndDate">Offer End Date: </label>
                         <input class="form-control resetMe" type="text" name="offerEndDate" value="${offerEndDate}" id="offerEndDate">
                     </div>

                     <div class="form-group">
                        <label for="offerAmount">Offer Amount:</label>
                         <input class="form-control resetMe" type="number" name="offerAmount" value="${newsObject.offerAmount}" min="1" max="9999999999" id="offerAmount">
                    </div>

                    <div class="form-group">
                        <label for="offerRate">Offer Rate: </label>
                        <input class="form-control resetMe" type="number"  onfocus="this.oldvalue = ${newsObject.offerRate};" onchange="onChangeTest(this);this.oldvalue = ${newsObject.offerRate};" name="offerRate" value="${newsObject.offerRate}" min="0" max="100" step="0.01" id="offerRate" >
                    </div>

                     <div class="form-group">
                         <label for="newsTitle">News Title: </label>
                         <input class="form-control resetMe" type="text" name="newsTitle" value="${newsObject.newsTitle}" min="0" max="100" step="0.01" id="newsTitle">
                     </div>

                        <c:if test="${empty newsObject}">
                            <input type="submit" class="btn btn-default float-left" value="Search"/>
                        </c:if>
                        <c:if test="${not empty newsObject}">
                         <input type="submit" class="btn btn-default float-left" value="Update"/>
                        </c:if>
                        <c:if test="${empty newsObject}">
                        <input  type= "button" class="btn btn-default float-right"  value="Reset" onclick="resetForm()"/>
                         </c:if>
                        <c:if test="${not empty newsObject}">
                        <input  type= "submit" class="btn btn-default float-right"  value="Delete" onclick="setDeleteValue()"/>
                        </c:if>

                 </form>
            </div>
        </div>
    </div>
<div id="welcomeDiv"  style="display:none;" >
    <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-block">
                <h5 align="center"> Changed Values </h5>
                <table class="table table-hover table-bordered">
                    <tr><td style="width: 40%">Column Name:</td><td style="width: 40%">Old Value:</td><td style="width: 40%">New Value:</td></tr>
                    <tr><td style="width: 40%">Bank Name: </td><td>${newsObject.bankName}</td><td>document.getElementById("bankName").value</td></tr>
                    <tr><td style="width: 40%">Offer URL:</td><td>${newsObject.linkUrl}</td><td></td></tr>
                    <tr><td style="width: 40%">News Type:</td><td>${newsObject.newsType}</td></tr>
                    <tr><td style="width: 40%">Loan Type:</td><td>${newsObject.loanType}</td></tr>
                    <tr><td style="width: 40%">Region:</td><td>${newsObject.region}</td></tr>
                    <tr><td style="width: 40%">Offer Start Date:</td><td>${newsObject.offerStartDate}</td></tr>
                    <tr><td style="width: 40%">Offer End Date:</td><td>${newsObject.offerEndDate}</td></tr>
                    <tr><td style="width: 40%">Offer Amount:</td><td>${newsObject.offerAmount}</td></tr>
                    <tr><td style="width: 40%">Offer Rate:</td><td>${newsObject.offerRate}</td></tr>
                    <tr><td style="width: 40%">News Title:</td><td>${newsObject.newsTitle}</td></tr>
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
function resetForm() {
    $(document).ready(function () {
        $(".resetMe").val("");
    });
}

function setDeleteValue() {
    var x = confirm("Are you sure you want to delete?");
    if (x) {
        var offerId = document.getElementById("offerId").value;
        $.ajax({
            url: "/deletesiteoffer?offerId=" + offerId,
            type: 'DELETE',
            cache: false,
            success: function (html) {
                alert("Deleted Successfully");
                resetForm();
                window.location = 'updatesiteoffersask';
            }
        });
    }
}
</script>