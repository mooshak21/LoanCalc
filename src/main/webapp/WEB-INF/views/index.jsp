<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="robots" content="index, follow">
    <meta name="keywords" content="Loan, Loan, insight, Loan-insight, loan-insight-calculator ,
    Ecommerce, Online Store, Buy goods">
    <meta name="description" content="Loan insight calculator gives you an online platform that to perform your loans and loan calculation effectively">
    <title>Loan Insight Calculator</title>

    <link href="https://fonts.googleapis.com/css?family=Poppins&display=swap" rel="stylesheet">

    <!--Import Google Icon Font-->
          <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
          <!--Import materialize.css-->
          <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
          <link type="text/css" rel="stylesheet" href="css/index.css"  media="screen,projection"/>
          <script type="text/javascript" src="css/jquery.min.js"></script>

  </head>
  <body>

    <header>
    
      <nav>
        <div class="nav-wrapper">
         <a href="#!" class="brand-logo">Loan Insight Calculator</a>
         <a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>
         <ul class="right hide-on-med-and-down">
         	<li class="nav-item">
              <input type="hidden" name="hdnMennuMinimal" value="Yes">
              <!-- <input type="hidden" name="hdnUserEmail" value="${userEmail}"> -->
		<input type="hidden" name="hdnUserEmail" value="gagan@loaninsight.net">
              <!--<input type="hidden" name="hdnUserPlan" value="${Plan}">-->
		<input type="hidden" name="hdnUserPlan" value="19.99">
              <input type="hidden" name="hdnUserPref" value="${UserPreference}">
             </li>	
           <li><a href="">HOME</a></li>
           <li><a href="/loansearchask">SEARCH LOAN</a></li>
           <li><a href="/createloan">ENTER LOAN</a></li>
           <li><a href="/loanamortizeask">AMORTIZE LOAN</a></li>
            <li><a href="/loanpreferenceviewask">REGISTER</a></li>
           <li><a href="/pricing">PRICING</a></li>
           <li><a href="/login">LOGIN</a></li>
           <li><a href="/logout">LOGOUT</a></li>
         </ul>
        </div>
      </nav>

      <ul class="sidenav" id="mobile-demo">
        <li><a href="">HOME</a></li>
        <li><a href="/loansearchask">SEARCH LOAN</a></li>
         <li><a href="/createloan">ENTER LOAN</a></li>
        <li><a href="/loanamortizeask">AMORTIZE LOAN</a></li>
         <li><a href="/pricing">PRICING</a></li>
        <li><a href="/loanpreferenceviewask">REGISTER</a></li>
        <li><a href="/login">LOGIN</a></li>
        <li><a href="/logout">LOGOUT</a></li>
      </ul>
    </header>

    <section class="loansegment">
      <div class="slider">
        <ul class="slides">
          <li>
            <img src="css/a1.jpg"> <!-- random image -->
            <div class="caption left-align">
              <h3>Giving you the <b>FREEDOM</b> </h3>
              <h5 class="light grey-text text-lighten-3">Enter loan for loan amortization schedule</h5>
            </div>
          </li>
          <li>
            <img src="css/b1.jpg"> <!-- random image -->
            <div class="caption left-align">
              <h3>The <b>SOLUTION</b> you seek </h3>
              <h5 class="light grey-text text-lighten-3">Auto Loan, Student Loan, Home Loan and more</h5>
            </div>
          </li>
          <li>
            <img src="css/c1.jpg"> <!-- random image -->
            <div class="caption left-align">
              <h3><b>CALCULATE</b> your loan payments </h3>
              <h5 class="light grey-text text-lighten-3">Based on interest and principal of monthly payment</h5>
            </div>
          </li>
          <li>
            <img src="css/d1.jpg"> <!-- random image -->
            <div class="caption left-align">
              <h3><b>SUBSCRIBE</b> to various our plan </h3>
              <h5 class="light grey-text text-lighten-3">Free, Lite, Premium</h5>
            </div>
          </li>
        </ul>
      </div>


      <div class="loan">
          <div class="loans">
          <!-- Admin button to be displayed if admin is logged In -->
            <c:if test="${(not empty userEmail) and (UserPreference == 'Admin')}">
             <p  class='btn dropdown-trigger' data-target='dropdown100'> Admin</p>
						<ul id='dropdown100' class='dropdown-content'>
                         <li><a href="/siteoffersask">Site Offers</a></li>
                         <li><a href="/searchSiteoffersask">Search Site Offers</a></li>
                         <li><a href="/updatesiteoffersask">Update Site Offers</a></li>
                         <li><a href="/externalLinksask">Equity External Calculator</a></li>
                         <li><a href="/updateExternalLinksask">Update Equity External Calculator</a></li>
                        </ul>
             </c:if>
      
              <!-- Lite & Premium Only -->
             <c:if test="${(not empty userEmail) and ((Plan == '9.99') or (Plan == '19.99'))}">
            	<p class="btn dropdown-trigger" data-target='dropdown300'>Lite</p>
                     <ul id='dropdown300' class='dropdown-content'>
                       <li><a href="/quickview">Quick View Loan</a></li>
                        <li><a href="/loanpayoffask">Payoff Loan</a></li>
                       <li><a href="/loanviewask">View Loans</a></li>
                       <li><a href="/payment">Payment</a></li>
                        
                    </ul>
             </c:if>
             
             <!-- Premium Only -->
             <c:if test="${(not empty userEmail) and (Plan == '19.99')}">
             	<p class="btn dropdown-trigger" data-target='dropdown300'>Premium</p>
                     <ul id='dropdown300' class='dropdown-content'>
                      
                       <li><a href="/aggregateloanask">Aggregate Loan</a></li>
                       <li><a href="/aggregateloanreportask">Aggregate Loan Report</a></li>
                    </ul>
               
             </c:if>
               
          </div>
      </div>
    </section>

    <script type="text/javascript" src="css/materialize.min.js"></script>
    <script type="text/javascript">
      $(document).ready(function(){
        $('.sidenav').sidenav();
        $('.slider').slider({
          'indicators': false,
          'duration': 500,
        });
        $('.dropdown-trigger').dropdown({
            'closeOnClick': true,
           'coverTrigger': false,
           'hover': true
          });

      });
    </script>
  </body>
</html>



