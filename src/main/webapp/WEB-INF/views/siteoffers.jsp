
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
                 <form name="siteForm" id="siteform" action="/siteoffers" method="POST" onsubmit='if(siteForm.bankName.value == ""){ alert("Please enter Bank Name"); siteForm.bankName.focus(); return false;}'>
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
                         <input class="form-control resetMe" placeholder="MM/dd/yyyy"  name="offerStartDate" value="${newsObject.offerStartDate}" id="offerStartDate" required="true">
                     </div>

                     <div class="form-group">
                         <label for="offerEndDate">Offer End Date: </label>
                         <input class="form-control resetMe" placeholder="MM/dd/yyyy" name="offerEndDate" value="${newsObject.offerEndDate}" id="offerEndDate" required="true">
                     </div>

                     <div class="form-group">
                        <label for="offerAmount">Offer Amount:</label>
                         <input class="form-control resetMe" type="number" name="offerAmount" value="${newsObject.offerAmount}" min="1" max="9999999999" id="offerAmount">
                    </div>

                    <div class="form-group">
                        <label for="offerRate">Offer Rate: </label>
                        <input class="form-control resetMe" type="number" name="offerRate" value="${newsObject.offerRate}" min="0" max="100" step="0.01" id="offerRate" >
                    </div>

                     <div class="form-group">
                         <label for="newsTitle">News Title: </label>
                         <input class="form-control resetMe" type="text" name="newsTitle" value="${newsObject.newsTitle}" min="0" max="100" step="0.01" id="newsTitle">
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
    
}
</script>