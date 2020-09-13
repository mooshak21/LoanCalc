<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-130593255-2"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-130593255-2');

function ValidateEmail(mail)
{
 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail.value))
  {
    return (true)
  }
    return (false)
}
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
                <form name="loginForm" id="loginform" action="/login" method="POST">
                   <div class="form-group">
                       <label for="email">Email:*</label>
                       <input class="form-control resetMe" type="email" name="email" value="${userEmail}" id="email" required="true" onblur="if(ValidateEmail(this)){ return;} else {this.focus();}">
		   </div>
                <div class="form-group">
                       <label for="password">Password:*</label>
                       <input class="form-control resetMe" type="password" name="password" value="${Password}" id="password" required="true">
                   </div>
			<p>Do you want to <a href="/loanpreferenceviewask">Register</a> or <a href="/resetpasswordask">reset password</a></p>
			<p>or have you <a href="/forgetpasswordask">forgotten your password</a>?</p>
                     <input type="submit" class="btn btn-default float-left" value="Submit"/>
                  <input  type= "button" class="btn btn-default float-right"  value="Reset" onclick="resetForm()"/>
                </form>
		</div>
		<div class="card-header">
				<p>Cookie Policy:</p>
				<textarea id="cookiepolicy" rows="4" cols="50">
COOKIES POLICY

This Cookies Policy forms part of our general Privacy Policy.

This Cookies Policy will give you clear and explicit information about the technologies we use on our website, as well as your rights to restrict this use.

1.	Introduction

1.1. Our website uses cookies to manage our users� use of the website and to understand how people use our website in order for us to improve it.
1.2. By ticking the box of our popup Cookies Policy window or by continuing browsing our site, you hereby consent to our use of cookies as defined in this Cookies Policy. If you do not consent to the use of Cookies, you may block or disable them using your browser settings, the opt-out links set out in this Cookies Policy. However, blocking all cookies may have a negative impact on your use of this website.

2.	Cookies

2.1. A cookie is small text file that is placed on your computer upon your visit on certain websites. The text file can be read by the website will help identifying you when you return on the website.

2.2. There are two types of cookies:
a)	�persistent� cookies remain on your computer and will remain valid until either the user deletes it or upon its expiry date,
b)	�session cookies� are deleted as soon as the web browser is closed.

2.3. Cookies can be used for technical reasons of the website, for analytics and for connecting with third party sites

3.	Our Cookies

3.1. We use session cookies that are deleted as soon as the web browser is closed.

3.2. We use �Analytical Cookies� which allow the entity that is responsible for them to monitor and analyze the behavior of web users to which they are linked. The information gathered through these cookies is used for measuring the activity of web sites, applications or platforms and for profiling user navigation on such sites, applications and platforms, in order to make improvements based on the analysis of data of users who use the service.

3.3. We use �Advertising cookies� to support our Website. We will send these advertisers information including your IP address, the time at which you visited the website, the browser you used to visit our Website, the unique ID number the cookie has given your browser, and in some cases, whether you have a Flash installed. These are
used to tailor the ads to your localization and/or general preferences, thereby tempting to prevent showing irrelevant ads or ads you may have already seen.

3.4. We use �Targeting cookies� that are used to deliver content that best suits you and your interests. It can be used for targeted advertising / offers, limiting ad serving or measuring the effectiveness of a promotional campaign. We may use these cookies to remember the sites you visit, in order to determine which e-marketing channels are more efficient and allow us to reward partners and external websites.

3.5. We will not share personal information collected from our cookies with any third party except as set out in this Cookies Policy


4.	Third Party Cookies

4.1. We use the Analytical cookies of Google Analytics service to analyze the use of our website and keep track of visitors. Google Analytics generate statistical and other information about our website�s use. Details and privacy policy of Google analytics cookies can be found here: Google Analytics

4.2. We use Advertising cookies of Google DoubleClick service to support our Website and provide users with relevant adverts. Details and privacy policy of Google DoubleClick cookies can be found here: DoubleClick


5.	Blocking or Deleting Cookies

5.1. You may refuse to accept cookies or delete cookies already stored on your computer through the settings of your web browser.
�	Google Chrome
�	Firefox
�	Internet Explorer 10
�	Safari

5.2.	Blocking and deleting cookies may have a negative impact upon the usability of our website and you might be prevented from using all the features available on our website.

5.3.	By continuing to browse our Website, you hereby consent to this Cookies Policy regardless of your browser settings.


6.	Contact Customer Support
  				</textarea>
		</div>
		<div class="card-header">
			     <p>1.	Loan Amortization Calculator</p>
<p>
This calculator provides a way to calculate amortization of</p>
<p>loan for the following loan types: Auto Loan | Student Loan | Home Loan.</p>
<p>
In loan amortization schedule, the principal increases and interest
reduces over the amortization time period achieving full amortization at the
end of the loan period.
</p>
<p>
The loan amortization schedule can be viewed online in web browser
& mobile browser and email sent to the user.
</p>
<p>
On the dashboard, the loans are displayed in a pie chart showing the
percentage of amounts of loans for the user.
</p>
<p>
On the dashboard, different links are displayed to do research on loans from
different banks based on region selected by the user.
</p>
<p>
There is an option to last view of stored loan based on preference of the
event of sending email to user. This is available in Lite plan.
The user can enroll in annual plan of Free/Lite/Premium plans.
Depending on these plan enrolled, the features are displayed and
restricted/enhanced.
To get training on the features, go to YouTube videos for Loan Insight Online.
</p>
<p>Training Videos:</p>
<p><a href="https://www.youtube.com/watch?v=A8NvgCLwQzA" target="_blank">LITE Desktop Version</a></p></p>
<p><a href="https://www.youtube.com/watch?v=V-aJhYUHxX8" target="_blank">FREE Desktop Version</a>
<p><a href="https://www.youtube.com/watch?v=U5NGKdDCL_0" target="_blank">PREMIUM Desktop Version</a></p>
<p><a href="https://www.youtube.com/watch?v=GTGQ0WTPdqE" target="_blank">FREE Mobile	 Version</a></p>
<p><a href="https://www.youtube.com/watch?v=6RNsRzdQyiA" target="_blank">LITE Mobile Version</a></p>
<p><a href="https://www.youtube.com/watch?v=Qux1eCqiPuo" target="_blank">PREMIUM Mobile Version</a></p>
<br><br>
<p>
2.	Loan Aggregation and Consolidation Calculator</p>
<p>
This calculator provides a way to consolidate the payments of the loans of
different loan types: Auto Loan | Student Loan | Home Loan.
</p>
<p>
The user can enroll in annual plan of Premium plan.
Depending on these plan enrolled, the features are displayed and restricted/
enhanced.
To get training on the features, go to YouTube videos for Loan Insight Online.
</p>
<p>Training Videos:</p>
<p><a href="https://www.youtube.com/watch?v=V-aJhYUHxX8" target="_blank">FREE Desktop Version</a></p>
<p><a href="https://www.youtube.com/watch?v=A8NvgCLwQzA" target="_blank">LITE Desktop Version</a></p>
<p><a href="https://www.youtube.com/watch?v=U5NGKdDCL_0" target="_blank">PREMIUM Desktop Version</a></p>
<p><a href="https://www.youtube.com/watch?v=GTGQ0WTPdqE" target="_blank">FREE Mobile	 Version</a></p>
<p><a href="https://www.youtube.com/watch?v=6RNsRzdQyiA" target="_blank">LITE Mobile Version</a></p>
<p><a href="https://www.youtube.com/watch?v=Qux1eCqiPuo" target="_blank">PREMIUM Mobile Version</a></p>
<br><br>

<p>
3.	Equity Calculator
</p>
<p>
This calculator provides a way to calculate the equity of the loan of loan type
either Auto Loan or Home Loan.

It calculates the equity based on the home/auto value and home/auto loan
amoritization.

The equity schedule can be viewed online in web browser & mobile browser.
 
This option is available in Lite and Premium plans.
</p>

            </div>
        </div>
    </div>


    <style media="screen">
    	#cookiepolicy{
    		width: 100%;
        height: 200px;
    	}
    	textarea{
    		font-size: 14px!important;
    	}

    	#cookiepolicy::-webkit-scrollbar {
    	  width: 6px;
    	  height: 3px; }

    	#cookiepolicy::-webkit-scrollbar-thumb {
    	  background: black;
    	  border-radius: 2px; }

        .card-header{
          font-weight: normal!important;
          text-transform: none;
        }
        .cardBody{
          white-space: normal!important;
        }

    </style>

 <script>
function resetForm() {
    $( document ).ready(function() {
        $(".resetMe").val("");
    });

};

</script>
