
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

   <div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-header">
              <h5>${message}</h5>
            </div>
            <div class="card-block">
                <form name="loginForm" id="loginform" action="/login" method="GET">
                   <div class="form-group">
                       <label for="email">Email: </label>
                       <input class="form-control resetMe" type="email" name="email" value="${userEmail}" id="email" required="true">
			       <p>Do you want to <a href="/loanpreferenceviewask">Register</a>?</p>
		   </div>

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
