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
    
    <title>Session Recording - CEIP Data Dashboard</title>    
	<link rel="stylesheet" media="screen" href="${contextPath}/resources/css/bootstrap.min.css">
	<link rel="stylesheet" media="screen" href="${contextPath}/resources/css/site.css">
  </head>

<script src="${contextPath}/resources/jquery.min.js"></script>
<script src="${contextPath}/resources/bootstrap.min.js"></script>
<script src="${contextPath}/resources/echarts.common.min.js"></script>

<body>
 <nav class="navbar navbar-inverse navbar-static-top purple">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" style="color:white;" href="../">CEIP Data Dashboard</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav" >
            <li><a href="#" ></a></li>
            <li class="${upmActive}"><a href="../upm/" style="color:white;">UPM</a></li>
            <li class="${srActive}"><a href="../sr/" style="color:white;">Session Recording</a></li>
            <li class="${lstrassActive}"><a href="../ltsrass/" style="color:white;">LTSR Assistant</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
	        <li><a href="mailto:bo.chen@citrix.com">Report Problem</a></li>
	      </ul>
        </div> 
      </div>
    </nav>
