<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	


<!--            top menu on xs devices-->
         <nav class="navbar navbar-toggleable-md navbar-light bg-faded hidden-sm-up loanMenu" >
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" 
                    data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" 
                    aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon menuItemXs"></span>
            </button>
            <a class="navbar-brand menuItemXs" href="#">Menu</a>

            <div class="collapse navbar-collapse hidden-sm-up" id="navbarSupportedContent" >
              <ul class="navbar-nav mr-auto">
                
                <li class="nav-item">
                  <a class="nav-link menuItemXs" href="/" >Home</a>
                </li>

	        <li class="nav-item">
	          <a class="nav-link menuItemXs" href="/login">Login</a>
			</li>
              
            <li class="nav-item">
                  <a class="nav-link menuItemXs" href="/loanpreferenceviewask">Register</a>
            </li>

		  <li class="nav-item">
		      <a class="nav-link menuItemXs" href="/logout">Log out</a>
		  </li>
              </ul>
            </div>
          </nav>

<!--       aside menu -->
        <ul class="nav flex-column hidden-xs-down">
            <li class="nav-item">
              <a class="nav-link active" href="/" id="menuHome">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link menuItem" href="/login">Login</a>
	    </li>
            <li class="nav-item">
              <a class="nav-link menuItem" href="/loanpreferenceviewask">Register</a>
            </li>

	    <li class="nav-item">
	    	<a class="nav-link menuItem" href="/logout">Log out</a>
	    </li>
         </ul> 
