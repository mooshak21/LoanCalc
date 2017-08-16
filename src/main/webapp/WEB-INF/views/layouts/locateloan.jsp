<!DOCTYPE html>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
  <head>
    <title><tiles:getAsString name="title"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" >
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" ></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" ></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>
    
  </head>
  <body>
      
       <div class="container">
            <div class="row">
                  <div class="col loanHeader">
                     <tiles:insertAttribute name="header" />
                     <h1>${pageContext.request.contextPath}</h1>
                  </div>
            </div>
            <div class="row">
                  <div class="col-12 col-sm-3 loanMenu">
                     <tiles:insertAttribute name="menu" />
                  </div>
                  <div class="col-12 col-sm-9 loanBody">
                              <tiles:insertAttribute name="master" />	
                              <tiles:insertAttribute name="detail" />
                  </div>
            </div>
            <div class="row">
                  <div class="col loanFooter">
                     <tiles:insertAttribute name="footer" />
                  </div>
            </div>
        </div>

  </body>
</html>
