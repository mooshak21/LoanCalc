<script>
  function loginWithReCaptcha(token) {
    alert('thanks ' + document.getElementById('field').value);
  }

  function validate(event) {
    event.preventDefault();
    if (!document.getElementById('field').value) {
      alert("You must add text to the required field");
    } else {
      grecaptcha.execute();
    }
  }

  function onload() {
    var element = document.getElementById('submit');
    element.onclick = validate;
  }

function ValidateEmail(mail) 
{
 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail.value))
  {
    return (true)
  }
    return (false)
}

</script>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

   <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-header">
              <h5>${message}</h5>
            </div>
            <div class="card-block">
                <form name="loginForm" id="loginform" action="/login" method="POST" onload="onload();">
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
				   Name:* (required) <input id="field" name="field">
     				<div id='recaptcha' class="g-recaptcha"
						class="g-recaptcha"
						data-sitekey="6LfeXDwUAAAAAJqiCnXGP9rW7H5suSjve8JBHIbx"
						data-callback="loginWithReCaptcha"
		          		data-size="invisible">
		          	</div>
                </form>
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
