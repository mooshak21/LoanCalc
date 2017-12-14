<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
  <head>
    <title><tiles:getAsString name="title"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" >
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js" ></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" ></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>
	<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
	 <script>
	 (adsbygoogle = window.adsbygoogle || []).push({ google_ad_client: "ca-pub-5699569860213511", enable_page_level_ads: true });
	 </script> 

  </head>
  <body>

       <div class="container" style="height: 100%">
            <div class="row" style="height: auto">
                  <div class="col loanHeader">
                     <tiles:insertAttribute name="header" />
                     <h1>${pageContext.request.contextPath}</h1>
                  </div>
            </div>

           <div class="row hidden-sm-up" style="min-height: 80%">
               <div class="col-12 loanMenu" style="height: 15%">
                   <tiles:insertAttribute name="menu" />
               </div>
               <div class="col-12 loanBody">
                   <tiles:insertAttribute name="mmaster" />
               </div>
           </div>

            <div class="row hidden-xs-down" style="min-height: 80%">
                  <div class="col-sm-3 loanMenu">
                     <tiles:insertAttribute name="menu" />
                  </div>
                  <div class="col-sm-9 loanBody">
                      <tiles:insertAttribute name="master" />
                  </div>
            </div>

            <div class="row" style="height: auto">
                  <div class="col loanFooter">
                     <tiles:insertAttribute name="footer" />
                  </div>
            </div>

        </div>

  </body>
</html>
