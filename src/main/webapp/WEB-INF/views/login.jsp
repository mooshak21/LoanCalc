
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
                       <input class="form-control resetMe" type="email" name="email" value="${userEmail}" id="email" required="true">
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
		<div>
			     <br>1.	Loan Amortization Calculator
<br>
This calculator provides a way to calculate amortization of loan for the following loan types – Auto Loan, Student Loan, Home Loan.
<br>
In loan amortization schedule, the principal increases and interest reduces over the amortization time period achieving full amortization at the end of the loan period.
<br>
The loan amortization schedule can be viewed online in web browser and mobile browser and email sent to the user.
<br>
On the dashboard, the loans are displayed in a pie chart showing the percentage of amounts of loans for the user.
<br>
On the dashboard, different links are displayed to do research on loans from different banks based on region selected by the user.
<br>
There is an option to last view of stored loan based on preference of the event of sending email to user. This is available in Lite plan.
<br>
The user can enroll in annual plan of Free/Lite/Premium plans.
<br>
Depending on these plan enrolled, the features are displayed and restricted/enhanced.
<br>
To get training on the features, go to YouTube videos for Loan Insight Online.
<br>
Training Videos: 
<br><a href="https://www.youtube.com/watch?v=V-aJhYUHxX8" target="_blank">FREE Desktop Version</a>
<br><a href="https://www.youtube.com/watch?v=A8NvgCLwQzA" target="_blank">LITE Desktop Version</a>
<br><a href="https://www.youtube.com/watch?v=U5NGKdDCL_0" target="_blank">PREMIUM Desktop Version</a>
<br><a href="https://www.youtube.com/watch?v=GTGQ0WTPdqE" target="_blank">FREE Mobile	 Version</a>
<br><a href="https://www.youtube.com/watch?v=6RNsRzdQyiA" target="_blank">LITE Mobile Version</a>
<br><a href="https://www.youtube.com/watch?v=Qux1eCqiPuo" target="_blank">PREMIUM Mobile Version</a>
<br><br>
2.	Loan Aggregation and Consolidation Calculator
<br>
This calculator provides a way to consolidate the payments of the loans of different loan types – Auto Loan, Student Loan and Home Loan.
<br>
The user can enroll in annual plan of Premium plan.
<br>
Depending on these plan enrolled, the features are displayed and restricted/enhanced.
<br>
To get training on the features, go to YouTube videos for Loan Insight Online.
<br>
Training Videos: 
<br><a href="https://www.youtube.com/watch?v=V-aJhYUHxX8" target="_blank">FREE Desktop Version</a>
<br><a href="https://www.youtube.com/watch?v=A8NvgCLwQzA" target="_blank">LITE Desktop Version</a>
<br><a href="https://www.youtube.com/watch?v=U5NGKdDCL_0" target="_blank">PREMIUM Desktop Version</a>
<br><a href="https://www.youtube.com/watch?v=GTGQ0WTPdqE" target="_blank">FREE Mobile	 Version</a>
<br><a href="https://www.youtube.com/watch?v=6RNsRzdQyiA" target="_blank">LITE Mobile Version</a>
<br><a href="https://www.youtube.com/watch?v=Qux1eCqiPuo" target="_blank">PREMIUM Mobile Version</a>
<br><br>
3.	Equity Calculator
<br>
This calculator provides a way to calculate the equity of the loan of loan type either Auto Loan or Home Loan.
<br>
It calculates the equity based on the home/auto value and home/auto loan amoritization.
<br>
The equity schedule can be viewed online in web browser and mobile browser.
<br>
This option is available in Lite and Premium plans.
<br>
Link: <a href="https://www.loaninsight.online" target="_blank"><img src="views/loaninsightonline.jpg" alt="LoanInsightOnline" height="140" width="140"></img></a>
<a href="https://play.google.com/store/apps/details?id=online.loaninsight.loaninsight&hl=en" target="_blank"><img src="views/google-play.png" alt="LoanInsightOnline" width="140"></img></a>
<a href="https://apps.apple.com/in/app/ayushiloaninsight/id1449417267" target="_blank"><img src="views/apple-app.png" alt="LoanInsightOnline" width="140"></img></a>
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
