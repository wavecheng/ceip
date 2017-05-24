<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/vnd.microsoft.icon" href="${contextPath}/resources/img/icon-favicon.png">
    
    <title>CEIP Data Dashboard</title>    
	<link rel="stylesheet" media="screen" href="${contextPath}/resources/css/bootstrap.min.css">
	<link rel="stylesheet" media="screen" href="${contextPath}/resources/css/site.css">
  </head>

<script src="${contextPath}/resources/jquery.min.js"></script>
<script src="${contextPath}/resources/bootstrap.min.js"></script>
<script src="${contextPath}/resources/echarts.common.min.js"></script>

 <body>
    <div class="jumbotron black">
      <div class="container text-center masterhead">
        <h1>CEIP Data Dashboard</h1>
      </div>
    </div>

    <div class="container text-center">
      <!-- Example row of columns -->
      <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-5 boxer dash-box-div">
          <a href="upm/" class="nounderline">         
		  <img src="${contextPath}/resources/img/upm.png" class="image-middle" />
		  <h2>UPM </h2>	
		  </a>
        </div>

		<div class="col-md-1"></div>
        <div class="col-md-5 boxer dash-box-div">
          <a href="sr/" class="nounderline">        
          <img src="${contextPath}/resources/img/sr.png" class="image-middle" />
          <h2>Session Recording </h2>
           </a>
        </div>
      </div>  
      
      <footer class="footer page-end">
        <p class="text-muted">&copy; 2017 Citrix Nanjing</p>
      </footer>
      
    </div> 
    
</body>
</html>
