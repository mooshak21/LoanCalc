<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row justify-content-center">
    <div class="card col-10 col-md-8 cardBody">
        <div class="nisl_form_div">
            <div class="card-header">
                <h5>Find a Lender</h5>
            </div>
            <div class="card-block">
                <form name="siteForm" id="siteform" action="/updatesearchsiteoffers" method="GET"
                      onload='if(siteForm.region.value == ""){ alert("Please choose region"); siteForm.region.focus(); return false;}'>
                    <div class="form-group">
                        <label for="region">Region:*</label>
                        <select class="form-control resetMe" name="region" required="true" id="region">
                            <option value="">Choose Region</option>
                            <option value="USA"  ${region == 'USA' ? 'selected' : ''}>USA</option>
                            <option value="Mex"  ${region == 'Mex' ? 'selected' : ''}>Mexico</option>
                            <option value="Can"  ${region == 'Can' ? 'selected' : ''}>Canada</option>
                            <option value="Euro"  ${region == 'Euro' ? 'selected' : ''}>Europe</option>
                            <option value="India"  ${region == 'India' ? 'selected' : ''}>India</option>
                            <option value="Japan" ${region == 'Japan' ? 'selected' : ''}>Japan</option>
                            <option value="China"  ${region == 'China' ? 'selected' : ''}>China</option>
                            <option value="UK"  ${region == 'UK' ? 'selected' : ''}>UK</option>
                            <option value="South Africa" ${region == 'South Africa' ? 'selected' : ''}>South Africa
                            </option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="loanType">Loan Type:*</label>
                        <select class="form-control resetMe" name="loanType" required="true" id="loanType">
                            <option value="">Choose a Loan Type</option>
                            <option value="Student Loan"  ${loanType == 'Student Loan' ? 'selected' : ''}>Student Loan
                            </option>
                            <option value="Auto Loan"  ${loanType == 'Auto Loan' ? 'selected' : ''}>Auto Loan</option>
                            <option value="Home Loan" ${loanType == 'Home Loan' ? 'selected' : ''}>Home Loan</option>
                        </select>
                    </div>
                    <div class="btnss-div">
                        <input type="submit" class="btn btn-default float-left" value="Submit"/>
                        <input type="button" class="btn btn-default float-right" value="Reset" onclick="resetForm()"/>
                    </div>

                </form>
            </div>
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
                        <th>Bank Name</th>
                        <th>Offer Amount($)</th>
                        <th>Offer Rate (%)</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entry" items="${siteoffers}">
                        <input type="hidden" name="offerId" value="${entry.offerId}" id="offerId">
                        <tr>
                            <td><a href="/updatesiteoffers?offerId=${entry.offerId}" /><c:out value="${entry.bankName}"/></td>
                            <td><fmt:formatNumber value="${entry.offerAmount}" pattern="###,###,###.00"/></td>
                            <td><fmt:formatNumber value="${entry.offerRate}" pattern="###,###,###.###"/></td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>

        <div class="nisl_add_div">
            <div class="table-responsive">
                <table class="table table-bordered" id="table2">
                    <thead class="thead-default">
                    <tr>
                        <th width="120px">Bank Name</th>
                        <th>News article</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entry" items="${newsarticle}">
                        <tr>
                            <td><c:out value="${entry.bankName}"/></td>
                            <td><a href="/updatesiteoffers?offerId=${entry.offerId}"/>${entry.newsTitle}</td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<style media="screen">
  .nisl_form_div .card-header{
    background: purple;
    color: #fff;
    padding: 15px;
    font-size: 30px;
    font-weight: bold;
    text-transform: uppercase;
  }
  .nisl_form_div .card-block form .form-group label {
   color: purple;
   font-weight: bold; }
  .nisl_form_div .card-block form .form-group input.form-control, .card-block form .form-group select {
     border: none;
     -webkit-box-shadow: 0px 0px 8px lightgrey;
     box-shadow: 0px 0px 8px lightgrey;
     border-radius: 20px;
     height: 40px;
     color: black;
     font-family: Poppins, sans-serif; }
    .nisl_form_div .card-block form input.btn {
       background: #9f0a9f;
       color: #fff;
       font-family: Poppins, sans-serif;
       border-radius: 20px;
       margin-top: 10px; }
      .nisl_form_div .card-block form button.btn {
         background: #9f0a9f !important;
         color: #fff !important;
         font-family: Poppins, sans-serif !important;
         border-radius: 20px;
         margin-top: 10px; }
        .nisl_form_div .card-block form input.btn:hover {
           color: #fff;
           background: #4d004d; }
        .nisl_form_div .card-block form button.btn:hover {
             color: #fff;
             background: #4d004d; }

    @media screen and (max-width: 700px) {
        .card-block p {
             font-size: 12px; }
    }
</style>


<script>
    function resetForm() {
        $(document).ready(function () {
            $(".resetMe").val("");
        });
    }
</script>
