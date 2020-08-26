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
           <li><a href="#" target="_parent">HOME</a></li>

         </ul>
        </div>
      </nav>
    	
      <ul class="sidenav" id="mobile-demo">
           <li><a href="#" target="_parent">HOME</a></li>

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
              <h3><b>SUBSCRIBE</b> to our various plans </h3>
              <h5 class="light grey-text text-lighten-3">Free, Lite, Premium</h5>
            </div>
          </li>
        </ul>
      </div>


      <div class="loan">
          <div class="loans">
            <p  class='btn dropdown-trigger' data-target='dropdown400'> Free</p>
	<ul id='dropdown400' class='dropdown-content'>
           <li>Search Loan</li>
	   <li>Enter Loan</li>
           <li>Amortize Loan</li>
            <li>Register</li>
           <li>Pricing</li>
 	</ul>
              <!-- Admin Only -->
             <p  class='btn dropdown-trigger' data-target='dropdown100'> Admin</p>
			<ul id='dropdown100' class='dropdown-content'>
                         <li>Site Offers</li>
                         <li>Search Site Offers</li>
                         <li>Update Site Offers</li>
                         <li>Equity External Calculator</li>
                         <li>Update Equity External Calculator</li>
                        </ul>
      
              <!-- Lite & Premium Only -->
            	<p class="btn dropdown-trigger" data-target='dropdown200'>Lite</p>
                     <ul id='dropdown200' class='dropdown-content'>
                       <li>Quick View Loan</li>
                        <li>Payoff Loan</li>
                       <li>View Loans</li>
                       <li>Payment</li>
                        
                    </ul>
             
             <!-- Premium Only -->
             	<p class="btn dropdown-trigger" data-target='dropdown300'>Premium</p>
                     <ul id='dropdown300' class='dropdown-content'>
                        <li>Quick View Loan</li>
                        <li>Payoff Loan</li>
                       <li>View Loans</li>
                       <li>Payment</li>                                      
                       <li>Aggregate Loan</li>
                       <li>Aggregate Loan Report</li>
                    </ul>
               
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



