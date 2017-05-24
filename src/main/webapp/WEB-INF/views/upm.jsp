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
    
    <title>UPM - CEIP Data Dashboard</title>    
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
          <ul class="nav navbar-nav">
            <li><a href="#"></a></li>
            <li class="active"><a href="#">UPM</a></li>
            <li><a href="../sr/">Session Recording</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
	        <li><a href="mailto:bo.chen@citrix.com">Report Problem</a></li>
	      </ul>
        </div> 
      </div>
    </nav>

    <div class="container-fluid text-center">
      <div class="page-header">
        <h1>UPM CEIP Dashboard</h1><span>Data updated @ ${lastUpdateTime}</span>
      </div>

    <div class="row">
       <div class="col-md-1"></div>
       <div class="col-md-5 padding-bottom">
      	 <div id="osnameChart" class="chart"></div>
  	  </div>
  	  <div class="col-md-1"></div>
      <div class="col-md-5 padding-bottom">
      	 <div id="versionChart" class="chart"></div>
  	  </div>

      <div class="col-md-1"></div>
      <div class="col-md-5 padding-bottom">
      	 <div id="serviceChart" class="chart"></div>
  	  </div>
  	  <div class="col-md-1"></div>
      <div class="col-md-5 padding-bottom">
      	 <div id="migrateProfileChart" class="chart"></div>
  	  </div>
 
      <div class="col-md-1"></div>
      <div class="col-md-5 padding-bottom">
      	 <div id="profileHandlingChart" class="chart"></div>
  	  </div>
  	  
      <footer class="footer page-end pull-right">
        <p class="text-muted">&copy; 2017 Citrix Nanjing</p>
      </footer>
      
    </div>   
    
</body>
<script src="${contextPath}/resources/jquery.min.js"></script>
<script src="${contextPath}/resources/bootstrap.min.js"></script>
<script src="${contextPath}/resources/echarts.common.min.js"></script>
<script>

var osnameChart = echarts.init(document.getElementById('osnameChart'));
var versionChart = echarts.init(document.getElementById('versionChart'));
var serviceChart = echarts.init(document.getElementById('serviceChart'));
var migrateProfileChart = echarts.init(document.getElementById('migrateProfileChart'));
var profileHandlingChart = echarts.init(document.getElementById('profileHandlingChart'));

osnameChart.setOption({
    title : {
        text: ' OS Information',
        subtext: '',
        x:'center'
    },

    tooltip : {
        trigger: 'item',
        formatter: "{b} <br/> {c} ({d}%)"
    },
    series : [
        {
            name: '',
            type: 'pie',
            radius : '75%',
            center: ['50%', '60%'],
            data:[
  		  	  <c:forEach items="${osname}" var="m" >
		  	  	{name: '${m.name}',value:${m.cnt}},
		  	  </c:forEach>
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
});


versionChart.setOption({
    title : {
        text: 'UPM Version',
        x:'center'
    },

    tooltip : {
        trigger: 'item',
        formatter: "{b} <br/> {c} ({d}%)"
    },
    series : [
        {
            name: '',
            type: 'pie',
            radius : '75%',
            center: ['50%', '60%'],
            data:[
  		  	  <c:forEach items="${version}" var="m" >
		  	  	{name: '${m.name}',value:${m.cnt}},
		  	  </c:forEach>
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
});

serviceChart.setOption({
    title : {
        text: ' Service Status',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{b} <br/> {c} ({d}%)"
    },
    series : [
        {
            name: '',
            type: 'pie',
            radius : '75%',
            center: ['50%', '60%'],
            data:[
  		  	  <c:forEach items="${service}" var="m" >
		  	  	{name: '${m.name}',value:${m.cnt}},
		  	  </c:forEach>
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
});

migrateProfileChart.setOption({
    title : {
        text: 'Local Profile Conflict Handling',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{b} <br/> {c} ({d}%)"
    },
    series : [
        {
            name: '',
            type: 'pie',
            radius : '75%',
            center: ['50%', '60%'],
            data:[
  		  	  <c:forEach items="${migrateProfile}" var="m" >
		  	  	{name: '${m.name}',value:${m.cnt}},
		  	  </c:forEach>
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
});

profileHandlingChart.setOption({
    title : {
        text: 'Migration of Existing Profile',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{b} <br/> {c} ({d}%)"
    },
    series : [
        {
            name: '',
            type: 'pie',
            radius : '75%',
            center: ['50%', '60%'],
            data:[
  		  	  <c:forEach items="${profileHandling}" var="m" >
		  	  	{name: '${m.name}',value:${m.cnt}},
		  	  </c:forEach>
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
});

</script>
</html>
