
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<link rel="stylesheet" href="/css/restyles.css" />

    <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-header">
              <h5>Find Calculator Link</h5>
            </div>
            <div class="card-block">
                 <form name="externalLinkForm" id="linkform" action="/updateSearchExternalLinks" method="GET" onsubmit='if(externalLinkForm.loanType.value == ""){ alert("Please enter loan type"); externalLinkForm.loanType.focus(); return false;}'>

                     <div class="form-group">
                         <label for="loanType">Loan Type:</label>
                         <select class="form-control resetMe" name="loanType" required="true" id="loanType" >
                             <option value="">Choose a Loan Type</option>
                             <option value="Auto Loan"  ${equityExternalCalculator.loanType == 'Auto Loan' ? 'selected' : ''}>Auto Loan</option>
                             <option value="Home Loan" ${equityExternalCalculator.loanType == 'Home Loan' ? 'selected' : ''}>Home Loan</option>
                         </select>
                     </div>

                     <div class="form-group">
                         <label for="region">Region:</label>
                         <select class="form-control resetMe" name="region" required="true" id="region" >
                             <option value="">Choose Region</option>
                             <option value="USA"  ${equityExternalCalculator.region == 'USA' ? 'selected' : ''}>USA</option>
                             <option value="Mex"  ${equityExternalCalculator.region == 'Mex' ? 'selected' : ''}>Mexico</option>
                             <option value="Can"  ${equityExternalCalculator.region == 'Can' ? 'selected' : ''}>Canada</option>
                             <option value="Euro"  ${equityExternalCalculator.region == 'Euro' ? 'selected' : ''}>Europe</option>
                             <option value="India"  ${equityExternalCalculator.region == 'India' ? 'selected' : ''}>India</option>
                             <option value="Japan" ${equityExternalCalculator.region == 'Japan' ? 'selected' : ''}>Japan</option>
                             <option value="China"  ${equityExternalCalculator.region == 'China' ? 'selected' : ''}>China</option>
                             <option value="UK"  ${equityExternalCalculator.region == 'UK' ? 'selected' : ''}>UK</option>
                             <option value="South Africa" ${equityExternalCalculator.region == 'South Africa' ? 'selected' : ''}>South Africa</option>
                         </select>
                     </div>
                     <input type="submit" class="btn btn-default float-left" value="Submit"/>
                     <input  type= "button" class="btn btn-default float-right"  value="Reset" onclick="resetForm()"/>

                 </form>
            </div>
        </div>
    </div>

<div class="row justify-content-center">
    <div class="card col-10 col-md-8 cardBody">
        <div class="nisl_add_div">
            <div class="table-responsive">
                <table class="table table-bordered" id="table1">
                    <thead class="thead-default">
                    <tr>
                        <th width="120px">External Calculator URL</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entry" items="${equityExternalCalculators}">
                        <tr>
                            <td><a href="/updateExternalLinks?externalCalculatorId=${entry.externalCalculatorId}"/><c:out value="${entry.linkUrl}"/></td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
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
