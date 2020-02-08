<script type="text/javascript" language="JavaScript">
<!--
//--------------------------------
// This code compares two fields in a form and submit it
// if they're the same, or not if they're different.
//--------------------------------
function checkPasswords(theForm) {
    if (theForm.password.value != theForm.confirmpassword.value)
    {
        alert('Those passwords don\'t match!');
        return false;
    } else {
        return true;
    }
}
//-->
</script> 
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-130593255-2"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-130593255-2');
</script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

   <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-header">
              <h5>${message}</h5>
            </div>
            <div class="card-block">
                <form name="loanForm" id="loanform" action="/vieweditpreferences" method="POST" onsubmit='return checkPasswords(this);'>
                   
                   <div class="form-group">
                       <label for="loanAmount">Loan Amount:</label>
                        <input class="form-control resetMe" type="number" name="loanAmt" value="${Amount}" min="1" max="9999999999" id="loanAmount">
                   </div>

                   <div class="form-group">
                       <label for="numberOfYears">Number of Years:</label>
                       <input class="form-control resetMe" type="number" name="numOfYears" value="${NumberOfYears}" min="1" max="100" id="numberOfYears">
                   </div>

                   <div class="form-group">
                       <label for="lender">Lender:</label>
                       <input class="form-control resetMe" type="text" name="lender" value="${Lender}" id="lender">
                   </div>
                   <div class="form-group">
                       <label for="state">State: </label>
                        <input class="form-control resetMe" type="text" name="state" value="${State}" id="state">
                   </div>

                   <div class="form-group">
                       <label for="interestRate">Annual Interest Rate: </label>
                       <input class="form-control resetMe" type="number" name="airVal" value="${AIR}" min="0" max="100" step="0.01" id="interestRate">
                   </div>

                   <div class="form-group">
                       <label for="locationPreference">Location Preference: </label>
		       <input class="form-control resetMe" type="text" name="locationPreference" value="${Location}" id="locationPreference">
                   </div>

                   <div class="form-group">
                       <label for="wsPreference">Web Service Preference: </label>
		       <input class="form-control resetMe" type="text" name="webServicePreference" value="${WebService}" id="wsPreference">
                   </div>

                    <div class="form-group">
                       <label for="rtPreference">Risk Tolerance Preference: </label>
		       <input class="form-control resetMe" type="number" name="riskTolerancePreference" value="${RiskTolerance}" min="0" max="100" step="1" id="rtPreference">
                   </div>

                   <div class="form-group">
                       <label for="thPreference">Time Horizon Preference: </label>
		       <input class="form-control resetMe" type="number" name="timeHorizonPreference" value="${TimeHorizon}" min="0" max="100" step="0.01" id="thPreference">
                   </div>

                    <div class="form-group">
                        <label for="usPreference">User Preference: </label>
                        <input class="form-control resetMe" type="text" name="userPreference" value="${User}" id="usPreference">
                    </div>
                   
                   <div class="form-group">
                       <label for="email">Email:*</label>
                       <input class="form-control resetMe" type="email" name="email" value="${userEmail}" id="email" required="true">
                   </div>

                   <div class="form-group">
                       <label for="password">Password:*</label>
                       <input class="form-control resetMe" type="password" name="password" value="" id="password" required="true">
                   </div>
                   
                   <div class="form-group">
                       <label for="confirmpassword">Confirm Password:*</label>
                       <input class="form-control resetMe" type="password" name="confirmpassword" value="" id="confirmpassword" required="true">
                   </div>
                   
                    <div class="form-group">
                        <label for="reminderfreq">Reminder Frequency: </label>
                        <select  class="form-control resetMe" id="reminderfreq" name="reminderfreq">
                            <c:if test="${reminderFrequency eq 'NoRemind'}">
                                <option value="NoRemind" selected>No Remind</option>
                                <option value="Weekly">Weekly</option>
                                <option value="Monthly">Monthly</option>
                                <option value="Quarterly" >Quarterly</option>
                                <option value="Semi-Annually" >Semi-Annually</option>
                                <option value="Annually" >Annually</option>
                            </c:if>
                            <c:if test="${reminderFrequency eq 'Weekly'}">
                                <option value="NoRemind">No Remind</option>
                                <option value="Weekly" selected>Weekly</option>
                                <option value="Monthly">Monthly</option>
                                <option value="Quarterly" >Quarterly</option>
                                <option value="Semi-Annually" >Semi-Annually</option>
                                <option value="Annually" >Annually</option>
                            </c:if>
                            <c:if test="${reminderFrequency eq 'Monthly'}">
                                 <option value="NoRemind">No Remind</option>
                                <option value="Weekly" >Weekly</option>
                                <option value="Monthly" selected>Monthly</option>
                                <option value="Quarterly" >Quarterly</option>
                                <option value="Semi-Annually" >Semi-Annually</option>
                                <option value="Annually" >Annually</option>
                            </c:if>
                            <c:if test="${reminderFrequency eq 'Quarterly'}">
                                 <option value="NoRemind">No Remind</option>
                                <option value="Weekly" >Weekly</option>
                                <option value="Monthly" >Monthly</option>
                                <option value="Quarterly" selected>Quarterly</option>
                                <option value="Semi-Annually" >Semi-Annually</option>
                                <option value="Annually" >Annually</option>
                            </c:if>
                             <c:if test="${reminderFrequency eq 'Semi-Annually'}">
                                 <option value="NoRemind">No Remind</option>
                                <option value="Weekly" >Weekly</option>
                                <option value="Monthly" >Monthly</option>
                                <option value="Quarterly" >Quarterly</option>
                                <option value="Semi-Annually" selected>Semi-Annually</option>
                                <option value="Annually" >Annually</option>
                            </c:if>
                            <c:if test="${reminderFrequency eq 'Annually'}">
                                 <option value="NoRemind">No Remind</option>
                                <option value="Weekly" >Weekly</option>
                                <option value="Monthly" >Monthly</option>
                                <option value="Quarterly" >Quarterly</option>
                                <option value="Semi-Annually">Semi-Annually</option>
                                <option value="Annually" selected>Annually</option>
                            </c:if>
                            <c:if test="${reminderFrequency eq ''}">
                                <option value="NoRemind" selected>No Remind</option>
                                <option value="Weekly" >Weekly</option>
                                <option value="Monthly" >Monthly</option>
                                <option value="Quarterly" >Quarterly</option>
                                <option value="Semi-Annually">Semi-Annually</option>
                                <option value="Annually">Annually</option>
                            </c:if>
                        </select>
                    </div>
                   
                   <div class="form-group">
                        <label for="Plan">Plan: </label>
                        <select  class="form-control resetMe" id="Plan" name="Plan">
                            <c:if test="${Plan eq ''}">
                                <option value="0.0" selected>Free</option>
                                <option value="9.99">Lite</option>
                                <option value="19.99">Premium</option>
                            </c:if>
                            <c:if test="${Plan eq '0.0'}">
                                <option value="0.0" selected>Free</option>
                                <option value="9.99">Lite</option>
                                <option value="19.99">Premium</option>
                            </c:if>
                            <c:if test="${Plan eq '9.99'}">
                                <option value="0.0">Free</option>
                                <option value="9.99" selected>Lite</option>
                                <option value="19.99">Premium</option>
                            </c:if>
                            <c:if test="${Plan eq '19.99'}">
                                <option value="0.0">Free</option>
                                <option value="9.99">Lite</option>
                                <option value="19.99" selected>Premium</option>
                            </c:if>
                            
                        </select>
                    </div>
		<div class="card-block">
		<p>Do you want to <a href="/login">Login</a> or <a href="/createloan">Enter Loan</a>?</p>
                </div>   

                  <input type="submit" class="btn btn-default float-left" value="Submit"/>
                  <input  type= "button" class="btn btn-default float-right"  value="Reset" onclick="resetForm()"/>
		<div class="card-block">
				<p>Cookie Policy:</p>
				<textarea id="cookiepolicy" rows="4" cols="100">
COOKIES POLICY 
 
This Cookies Policy forms part of our general Privacy Policy. 
 
This Cookies Policy will give you clear and explicit information about the technologies we use on our website, as well as your rights to restrict this use. 
 
1.	Introduction 
 
1.1. Our website uses cookies to manage our users’ use of the website and to understand how people use our website in order for us to improve it. 
1.2. By ticking the box of our popup Cookies Policy window or by continuing browsing our site, you hereby consent to our use of cookies as defined in this Cookies Policy. If you do not consent to the use of Cookies, you may block or disable them using your browser settings, the opt-out links set out in this Cookies Policy. However, blocking all cookies may have a negative impact on your use of this website. 
 
2.	Cookies 
 
2.1. A cookie is small text file that is placed on your computer upon your visit on certain websites. The text file can be read by the website will help identifying you when you return on the website. 
 
2.2. There are two types of cookies: 
a)	‘persistent’ cookies remain on your computer and will remain valid until either the user deletes it or upon its expiry date, 
b)	‘session cookies’ are deleted as soon as the web browser is closed. 
 
2.3. Cookies can be used for technical reasons of the website, for analytics and for connecting with third party sites 
 
3.	Our Cookies 
 
3.1. We use session cookies that are deleted as soon as the web browser is closed. 
 
3.2. We use ‘Analytical Cookies’ which allow the entity that is responsible for them to monitor and analyze the behavior of web users to which they are linked. The information gathered through these cookies is used for measuring the activity of web sites, applications or platforms and for profiling user navigation on such sites, applications and platforms, in order to make improvements based on the analysis of data of users who use the service. 
 
3.3. We use ‘Advertising cookies’ to support our Website. We will send these advertisers information including your IP address, the time at which you visited the website, the browser you used to visit our Website, the unique ID number the cookie has given your browser, and in some cases, whether you have a Flash installed. These are 
used to tailor the ads to your localization and/or general preferences, thereby tempting to prevent showing irrelevant ads or ads you may have already seen. 
 
3.4. We use ‘Targeting cookies’ that are used to deliver content that best suits you and your interests. It can be used for targeted advertising / offers, limiting ad serving or measuring the effectiveness of a promotional campaign. We may use these cookies to remember the sites you visit, in order to determine which e-marketing channels are more efficient and allow us to reward partners and external websites. 
 
3.5. We will not share personal information collected from our cookies with any third party except as set out in this Cookies Policy 
 
 
4.	Third Party Cookies 
 
4.1. We use the Analytical cookies of Google Analytics service to analyze the use of our website and keep track of visitors. Google Analytics generate statistical and other information about our website’s use. Details and privacy policy of Google analytics cookies can be found here: Google Analytics 
 
4.2. We use Advertising cookies of Google DoubleClick service to support our Website and provide users with relevant adverts. Details and privacy policy of Google DoubleClick cookies can be found here: DoubleClick 
 

5.	Blocking or Deleting Cookies 
 
5.1. You may refuse to accept cookies or delete cookies already stored on your computer through the settings of your web browser. 
•	Google Chrome 
•	Firefox 
•	Internet Explorer 10 
•	Safari 
 
5.2.	Blocking and deleting cookies may have a negative impact upon the usability of our website and you might be prevented from using all the features available on our website. 
 
5.3.	By continuing to browse our Website, you hereby consent to this Cookies Policy regardless of your browser settings. 
 
 
6.	Contact Customer Support
  				</textarea>
            </div>
                </form>
            </div>
        </div>
    </div>
	
   <c:if test="${not empty loan}">
       <div class="row justify-content-center">
            <div class="card col-10 col-md-8 cardBody">
                <div class="card-block">
                    <table class="table table-hover table-bordered">
                      <tr><td style="width: 40%">Loan Id:<td><h4>${loan.loanId}</h4></td></tr><tr><td>Monthly Payment:</td><td><h4>${loan.monthly}</h4></td></tr>
                      <tr><td style="width: 40%">Interest Rate:</td><td><h4>${loan.interestRate}</h4></td></tr>
                      <tr><td style="width: 40%">Loan Amount:</td><td><h4>${loan.amount}</h4></td></tr>
                      <tr><td style="width: 40%">Lender:</td><td><h4>${loan.lender}</h4></td></tr>
                      <tr><td style="width: 40%">State:</td><td><h4>${loan.state}</h4></td></tr>
                      <tr><td style="width: 40%">APR:</td><td><h4>${loan.APR}</h4></td></tr>
		      <tr><td style="width: 40%">Number of Years:</td><td><h4>${loan.numberOfYears}</h4></td></tr>
                      <tr><td style="width: 40%">Loan App:</td><td><h4>${loan.loanApp.lender}</h4></td></tr>
		      <tr><td style="width: 40%">Interest:</td><td><h4>${loan.interest}</h4></td></tr>                   
                      <tr><td style="width: 40%">Principal:</td><td><h4>${loan.principal}</h4></td></tr>
                      <tr><td style="width: 40%" colspan="2"><a href='/amortizeloan?airVal=${loan.APR}&loanAmt=${loan.amount}&state=${loan.state}&lender=${loan.lender}&numOfYears=${loan.numberOfYears}&amortizeOn=01/01/2017'>Amortize Loan</a></td></tr>
                    </table>
                </div>
            </div>
        </div>
   </c:if> 

 <script>
function resetForm() {
    $( document ).ready(function() {
        $(".resetMe").val("");
    });
    
};

</script>
