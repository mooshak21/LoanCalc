<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%><!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>

     <title>Loan Insight Calculator</title>

          <link href="https://fonts.googleapis.com/css?family=Poppins&display=swap" rel="stylesheet">

    <!--Import Google Icon Font-->
          <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
          <!--Import materialize.css-->
          <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
          <link type="text/css" rel="stylesheet" href="css/login.css"  media="screen,projection"/>
          <script type="text/javascript" src="js/jquery.min.js"></script>

  </head>
  <body>

    <section class="login">
        <div class="loginform">

            <ul class="collapsible">
              <li>
                <div class="collapsible-header"><i class="material-icons">edit</i>Login</div>
                <div class="collapsible-body">
                  <form class="col s12 m11 offset-m1 pull" action="/loginfromlaunch" method='post'>
                    <div class="row">
                      <div class="input-field col s6">
                        <input placeholder="Email" id="email" type="email" class="browser-default validate" required>
                        <label for="email">Email<span class="red-text">*</span></label>

                       </div>

                    </div>
                    <div class="row">
                      <div class="input-field col s6">
                        <input placeholder="password" name="password" id="password" type="password" class="browser-default validate" required>
                        <label for="password">password<span class="red-text">*</span></label>
                       </div>

                    </div>

                    <ul>
                      <li><p>Do you want to <a href="/loanpreferenceviewask">Register</a>or have you <a href="/resetpasswordask">reset password</a></p></li>
                      <li><p>or have you <a href="/forgetpasswordask">forgotten your password?</a></p></li>
                    </ul>

                    <div class="">
                      <button type="submit" name="submit" class='btn'> Submit</button>
                    </div>

                  </form>
                </div>
              </li>
            </ul>
        </div>
    </section>

    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript">

      $(document).ready(function(){
         $('.collapsible').collapsible({
           'accordion': false
         });
      });
    </script>
  </body>
</html>
