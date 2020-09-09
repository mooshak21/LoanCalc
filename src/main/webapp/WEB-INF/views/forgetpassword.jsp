<script>
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
<link rel="stylesheet" href="/css/restyles.css" />

   <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-header">
              <h5>${message}</h5>
            </div>
            <div class="card-block">
                <form name="loginForm" id="loginform" action="/forgetpassword" method="POST">
                   <div class="form-group">
                       <label for="email">Email:*</label>
                       <input class="form-control resetMe" type="email" name="email" value="${userEmail}" id="email" required="true" onblur="if(ValidateEmail(this)){ return;} else {this.focus();}">
		   			</div>
		   			<div class="form-group">
                       <label for="password">Password:*</label>
                       <input class="form-control resetMe" type="text" name="password" value="${password}" id="password" required="true">
                   </div>

			<p>Do you want to <a href="/loanpreferenceviewask">Register</a> or <a href="/resetpasswordask">reset password</a></p>
			<p>or have you <a href="/forgetpasswordask">forgotten your password</a>?</p>
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

};

</script>
