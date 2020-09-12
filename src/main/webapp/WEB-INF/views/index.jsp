<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
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

    <header id='healer'>

      <nav>
        <div class="nav-wrapper">
         <a href="#!" class="brand-logo">Loan Insight Calculator</a>
         <a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>
         <ul class="right hide-on-med-and-down">
          <li class="nav-item">
              <input type="hidden" name="hdnMennuMinimal" value="Yes">
              <input type="hidden" name="hdnUserEmail" value="${userEmail}">
              <input type="hidden" name="hdnUserPlan" value="${Plan}">
              <input type="hidden" name="hdnUserPref" value="${UserPreference}">
             </li>
	 <c:if test="${(sessionScope['loginStatus'] == 'Y')}">
           <li><a href="/home">HOME</a></li>
	 </c:if>
 	 <c:if test="${(sessionScope['loginStatus'] == 'N')}">
           <li><a href="/">HOME</a></li>
	 </c:if>
	<c:if test="${(empty sessionScope['loginStatus'])}">
           <li><a href="/">HOME</a></li>
	 </c:if>
           <li><a href="/loansearchask">SEARCH LOAN</a></li>
           <li><a href="/createloan">ENTER LOAN</a></li>
           <li><a href="/loanamortizeask">AMORTIZE LOAN</a></li>
           <li><a href="http://www.ayushisoftware.com/loaninsight.php?selmenubaritem=services&selmenuitem=loan">ABOUT</a></li>
           <li><a href="/loanpreferenceviewask">REGISTER</a></li>
           <li><a href="/pricing">PRICING</a></li>
	 <c:if test="${(not empty sessionScope['loginStatus'])}">
          <li><a id="menulogin1">LOGIN</a></li>
	 </c:if>
	 <c:if test="${(empty sessionScope['loginStatus'])}">
           <li><a href="/logout">LOGOUT</a></li>
	 </c:if>
         </ul>
        </div>
      </nav>

      <ul class="sidenav" id="mobile-demo">
	 <c:if test="${(sessionScope['loginStatus'] == 'Y')}">
           <li><a href="/home">HOME</a></li>
	 </c:if>
 	 <c:if test="${(sessionScope['loginStatus'] == 'N')}">
           <li><a href="/">HOME</a></li>
	 </c:if>
	<c:if test="${(empty sessionScope['loginStatus'])}">
           <li><a href="/">HOME</a></li>
	 </c:if>
        <li><a href="/loansearchask">SEARCH LOAN</a></li>
         <li><a href="/createloan">ENTER LOAN</a></li>
        <li><a href="/loanamortizeask">AMORTIZE LOAN</a></li>
         <li><a href="/pricing">PRICING</a></li>
       <li><a href="http://www.ayushisoftware.com/loaninsight.php?selmenubaritem=services&selmenuitem=loan">ABOUT</a></li>
       <li><a href="/loanpreferenceviewask">REGISTER</a></li>
	 <c:if test="${(not empty sessionScope['loginStatus'])}">
          <li><a id="menulogin2">LOGIN</a></li>
	 </c:if>
	 <c:if test="${(empty sessionScope['loginStatus'])}">
           <li><a href="/logout">LOGOUT</a></li>
	 </c:if>
      </ul>
    </header>

    <!-- Messaging Deliverer -->
    <div class='alertmessage'>
      <div id='message'></div>
    </div>


     <section class="login">
        <div class="loginform">
            <ul class="collapsible">
              <li>
                <div class="collapsible-header"><i class="material-icons">edit</i>Login</div>
                <div class="collapsible-body">

                  <form class="col s12 m12" id="loginform" action="/loginfromlaunch" method='post'>
                    <div class="boxed">
                      <div class="row">
                        <div class="input-field col s12">
                          <input placeholder="Email" id="email" name="email" type="email" class="browser-default validate" required>
                          <label for="email">Email<span class="red-text">*</span></label>
                         </div>

                      </div>
                      <div class="row">
                        <div class="input-field col s12">
                          <input placeholder="password" name="password" id="password" type="password" class="browser-default validate" required>
                          <label for="password">Password<span class="red-text">*</span></label>
                         </div>
                      </div>

                      <ul>
                        <li><p>Do you want to <a href="/loanpreferenceviewask">Register</a> or have you <a href="/resetpasswordask">reset password</a></p></li>
                        <li><p>or have you <a href="/forgetpasswordask">forgotten your password?</a></p></li>
                      </ul>

                      <div class="">
                        <button type="submit" name="submit" class='btn'> Submit</button>
                        <button type="reset" class='btn' id="loginbtnclear">Clear</button>
                      </div>
                    </div>

                    <div class="facebook">
                     <a class="btn facebookbutton" href="#" onclick="messenger('Facebook Login success')">Login With Facebook</a>
                     <a class="btn sms" id='sms'> SMS verification</a>
                   </div>
                  </form>


                   <form class="smsverify" method="post">
                     <h3>SMS Verification</h3>
                     <p>Enter your phone number, and we will send you a one-time code to secure your account</p>

                    <div class="row">
                      <div class="input-field col s12 m12">
                        <span>
                          <label for="phone">Phone Number </label>
                          <input placeholder="Enter cell Number" name="phone"  type="tel" class="browser-default validate" required>
                        </span>
                        <span>
                          <button type="submit" name="submit" class='btn'> Send Me code</button>
                        </span>
                       </div>
                    </div>
                    <div class="row">
                      <div class="input-field col s12 m12">
                        <span>
                          <label for="phone">Enter Code </label>
                          <input placeholder="Enter code" name="smsverify"  type="text" class="browser-default validate" required>
                        </span>
                        <span>
                          <button type="submit" name="submit" class='btn'>verify</button>
                        </span>
                       </div>
                    </div>
                     <p id="smscancel" class='btn'> Cancel</p>
                  </form>
                </div>
              </li>
            </ul>
        </div>
    </section>





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
          <!-- Admin button to be displayed if admin is logged In -->
            <c:if test="${(not empty sessionScope['userEmail']) and (sessionScope['UserPreference'] == 'Admin')}">
             <p  class='btn dropdown-trigger' data-target='dropdown100'> Admin</p>
            <ul id='dropdown100' class='dropdown-content'>
                         <li><a href="/siteoffersask">Site Offers</a></li>
                         <li><a href="/searchSiteoffersask">Search Site Offers</a></li>
                         <li><a href="/updatesiteoffersask">Update Site Offers</a></li>
                         <li><a href="/externalLinksask">Equity External Calculator</a></li>
                         <li><a href="/updateExternalLinksask">Update Equity External Calculator</a></li>
                        </ul>
             </c:if>

              <!-- LiteOnly -->
             <c:if test="${(not empty sessionScope['userEmail']) and (sessionScope['Plan'] == '9.99')}">
              <p class="btn dropdown-trigger" data-target='dropdown200'>Lite</p>
                     <ul id='dropdown200' class='dropdown-content'>
                       <li><a href="/quickview">Quick View Loan</a></li>
                        <li><a href="/loanpayoffask">Payoff Loan</a></li>
                       <li><a href="/loanviewask">View Loans</a></li>
                       <li><a href="/payment">Payment</a></li>

                    </ul>
            </c:if>

                  <!-- Premium Only -->
             <c:if test="${(not empty sessionScope['userEmail']) and (sessionScope['Plan'] == '19.99')}">
              <p class="btn dropdown-trigger" data-target='dropdown300'>Premium</p>
                     <ul id='dropdown300' class='dropdown-content'>
                       <li><a href="/quickview">Quick View Loan</a></li>
                        <li><a href="/loanpayoffask">Payoff Loan</a></li>
                       <li><a href="/loanviewask">View Loans</a></li>
                       <li><a href="/payment">Payment</a></li>
                       <li><a href="/aggregateloanask">Aggregate Loan</a></li>
                       <li><a href="/aggregateloanreportask">Aggregate Loan Report</a></li>
                    </ul>

             </c:if>
          </div>
      </div>
    </section>


 <style media="screen">

body .container {
width: 100% !important; }
body .container .row {
 -webkit-box-shadow: none!important;
 box-shadow: none!important; }
body .container .loanHeader {
 background: purple;
 color: #fff;
 margin-top: 0px;
 -webkit-box-shadow: 0px 4px 10px black !important;
 box-shadow: 0px 4px 10px black !important;
 z-index: 10; }
 body .container .loanHeader h2 {
   font-size: 30px;
   padding-top: 20px; }
 body .container .loanHeader .text-muted {
   color: #fff !important; }

   body .container .loanFooter {
  background: #000;
  margin-top: 0px;
  padding: 100px 30px; }
  body .container .loanFooter footer {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-orient: horizontal;
    -webkit-box-direction: normal;
    -ms-flex-direction: row;
    flex-direction: row;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -ms-flex-wrap: wrap;
      flex-wrap: wrap; }
    body .container .loanFooter footer p {
      color: #fff;
      font-size: 12px;
      max-width: 20%; }

   .login .loginform ul.collapsible li .collapsible-body form ul li p {
     margin-top: 0px;
     margin-bottom: 0px;
     font-size: 12px; }

   .login .loginform .collapsible li .collapsible-body .boxed .row {
     margin-bottom: 0px;
      box-shadow: none!important;}

   header nav ul li a {
     color: purple !important; }

   header nav ul li a:hover {
     color: #fff !important; }

   .sidenav li a {
     color: #fff !important;
     text-decoration: none !important; }

   .login .loginform .collapsible li .collapsible-body #loginform {
     display: -webkit-box;
     display: -ms-flexbox;
     display: flex; }

   .login .loginform .collapsible li .collapsible-body .boxed {
     width: 100% !important; }

   .login .loginform .collapsible li .collapsible-body .facebook {
     padding-top: 10px;
     margin-left: 10px;
     padding-top: 20px;
     width: 100% !important;
     text-align: center;
     display: -webkit-box;
     display: -ms-flexbox;
     display: flex;
     -webkit-box-orient: vertical;
     -webkit-box-direction: normal;
     -ms-flex-direction: column;
     flex-direction: column;
     -webkit-box-align: center;
     -ms-flex-align: center;
     align-items: center; }
     .login .loginform .collapsible li .collapsible-body .facebook a.facebookbutton {
       background: blue;
       border-radius: 6px;
       font-size: 12px;
       height: 45px;
       line-height: 45px;
       width: 200px;
       -webkit-transition: 0.3s;
       -o-transition: 0.3s;
       transition: 0.3s;
       font-weight: bold;
       margin-bottom: 30px; }
     .login .loginform .collapsible li .collapsible-body .facebook a.facebook:hover {
       -webkit-transform: scale(1.02);
       -ms-transform: scale(1.02);
       transform: scale(1.02);
       -webkit-transition: 0.3s;
       -o-transition: 0.3s;
       transition: 0.3s; }
     .login .loginform .collapsible li .collapsible-body .facebook a.sms {
       background: #fff;
       border-radius: 6px;
       border: 2px solid blue;
       font-size: 13px;
       font-weight: bold;
       height: 45px;
       color: blue;
       line-height: 45px;
       width: 200px;
       -webkit-transition: 0.3s;
       -o-transition: 0.3s;
       transition: 0.3s;
       margin-bottom: 30px; }
     .login .loginform .collapsible li .collapsible-body .facebook a.sms:hover {
       -webkit-transform: scale(1.02);
       -ms-transform: scale(1.02);
       transform: scale(1.02);
       -webkit-transition: 0.3s;
       -o-transition: 0.3s;
       transition: 0.3s; }

   .login .loginform .collapsible li .collapsible-body .smsverify {
     display: none; }
     .login .loginform .collapsible li .collapsible-body .smsverify h3 {
       margin-top: 0px;
       font-size: 20px;
       color: purple;
       font-weight: bold; }
     .login .loginform .collapsible li .collapsible-body .smsverify .row {
       margin-bottom: 0px !important; }
       .login .loginform .collapsible li .collapsible-body .smsverify .row .input-field {
         display: -webkit-box;
         display: -ms-flexbox;
         display: flex;
         margin: 0px; }
         .login .loginform .collapsible li .collapsible-body .smsverify .row .input-field span:last-of-type {
           padding-left: 20px; }
           .login .loginform .collapsible li .collapsible-body .smsverify .row .input-field span:last-of-type button {
             position: relative;
             top: 30px;
             padding-left: 20px; }
     .login .loginform .collapsible li .collapsible-body .smsverify #smscancel {
       background: purple; }

   .alertmessage {
     width: 100%;
     position: fixed;
     z-index: 1000;
     margin: auto;
     height: 0px;
     top: 10px;
     text-align: center; }
     .alertmessage div#message {
       background: tomato;
       border-radius: 7px;
       width: 40%;
       color: #fff;
       margin: auto;
       height: 0px;
       display: none;
       -webkit-box-shadow: 0px 10px 40px black;
       box-shadow: 0px 10px 40px black;
       top: 0; }

   @media screen and (max-width: 700px) {

     body .container .loanFooter {
    padding-top: 30px; }
    body .container .loanFooter footer {
      display: block; }
      body .container .loanFooter footer p {
        width: 100%;
        max-width: 100%;
        padding-bottom: 20px; }

     .login .loginform .collapsible li .collapsible-body #loginform {
       -webkit-box-orient: vertical !important;
       -webkit-box-direction: normal !important;
       -ms-flex-direction: column !important;
       flex-direction: column !important; }
     .login .loginform .collapsible li .collapsible-body .facebook {
       display: -webkit-box;
       display: -ms-flexbox;
       display: flex;
       -webkit-box-orient: horizontal !important;
       -webkit-box-direction: normal !important;
       -ms-flex-direction: row !important;
       flex-direction: row !important;
       -ms-flex-pack: distribute;
       justify-content: space-around;
       margin-top: 20px; } }

   @media screen and (max-width: 550px) {
     .login .loginform ul.collapsible li .collapsible-body {
       padding: 15px !important; }
       .login .loginform ul.collapsible li .collapsible-body .facebook {
         -webkit-box-orient: vertical !important;
         -webkit-box-direction: normal !important;
         -ms-flex-direction: column !important;
         flex-direction: column !important;
         margin-top: 0px;
         -webkit-box-align: start;
         -ms-flex-align: start;
         align-items: flex-start;
         margin-left: 0px; }
         .login .loginform ul.collapsible li .collapsible-body .facebook a.facebookbutton {
           margin-bottom: 5px; }
       .login .loginform ul.collapsible li .collapsible-body .smsverify .row {
         margin-bottom: 5px; }
         .login .loginform ul.collapsible li .collapsible-body .smsverify .row .input-field {
           -webkit-box-orient: vertical;
           -webkit-box-direction: normal;
           -ms-flex-direction: column;
           flex-direction: column;
           margin-top: 14px;
           margin-bottom: 14px; }
           .login .loginform ul.collapsible li .collapsible-body .smsverify .row .input-field label {
             font-size: 12px; }
           .login .loginform ul.collapsible li .collapsible-body .smsverify .row .input-field .browser-default {
             height: 35px; }
           .login .loginform ul.collapsible li .collapsible-body .smsverify .row .input-field span:last-of-type {
             padding-left: 0px; }
             .login .loginform ul.collapsible li .collapsible-body .smsverify .row .input-field span:last-of-type button {
               top: 10px;
               height: 30px;
               line-height: 30px;
               padding-left: 10px;
               padding-right: 10px;
               font-size: 11px; }
       .login .loginform ul.collapsible li .collapsible-body .smsverify h3 {
         margin-bottom: 0px;
         font-size: 15px; }
       .login .loginform ul.collapsible li .collapsible-body .smsverify p {
         font-size: 12px;
         margin-bottom: 0px; }
       .login .loginform ul.collapsible li .collapsible-body .smsverify #smscancel {
         height: 30px;
         line-height: 30px;
         padding-left: 10px;
         padding-right: 10px;
         font-size: 11px; } }

   @media screen and (max-width: 500px) {
     .loansegment .loan .loans {
       padding: 0px !important; }
     .alertmessage div#message {
       width: 80%; } }

 </style>

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

        $('.collapsible').collapsible({
            'accordion': true
          });

        $('#sms').click(()=>{
          $('.login .loginform ul li div.collapsible-body #loginform').fadeOut(1);
          $('.login .loginform ul li div.collapsible-body .smsverify').fadeIn(300);

        })
        $('#smscancel').click(()=>{
          $('.login .loginform ul li div.collapsible-body .smsverify').fadeOut(1);
          $('.login .loginform ul li div.collapsible-body #loginform').fadeIn(300);

        })
        $('#loginbtnclear').click(()=>{
          $('.collapsible').collapsible('close');
        })
        $('#menulogin1').click(()=>{
          $('.collapsible').collapsible('open');
        })
        $('#menulogin2').click(()=>{
          $('.collapsible').collapsible('open');
          $('.sidenav').sidenav('close');

        })
      });


/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
    function messenger(res){
      $('.collapsible').collapsible('close');
      $('#message').css({"padding":"40px", "height":"130px", "background": 'tomato', "color":"white"})
      $('#message').text(res)
      $('#message').slideDown(200)
      setTimeout(()=>{
         $('#message').slideUp(200)
      }, 2000)
    }


</script>
