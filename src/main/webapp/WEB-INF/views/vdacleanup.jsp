<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<jsp:include page="header.jsp" ></jsp:include>	
	
    <div class="container-fluid text-center">
      <div class="page-header">
        <h1>VDA Cleanup Utility CEIP Dashboard</h1><span>Data updated @ ${lastUpdateTime}</span>
      </div>

	<div class="row">
	   <div class="col-md-12">
	     <div id="customerChart" class="chart-full"></div>
	   </div>			
	</div>
 
 	<div class="row">
	   <div class="col-md-12">
	     <div id="countryChart" class="chart-full"></div>
	   </div>			
	</div>
	   
    <div class="row">
       <div class="col-md-1"></div>
       <div class="col-md-5 padding-bottom">
      	 <div id="versionChart" class="chart"></div>
  	  </div>
  	  <div class="col-md-1"></div>
      <div class="col-md-5 padding-bottom">
      	 <div id="rebootTimesChart" class="chart"></div>
  	  </div>
	</div>
	
    <div class="row">
       <div class="col-md-1"></div>
       <div class="col-md-5 padding-bottom">
      	 <div id="uninstallStatusChart" class="chart"></div>
  	  </div>
  	  <div class="col-md-1"></div>
      <div class="col-md-5 padding-bottom">
      	 <div id="uninstallTypeChart" class="chart"></div>
  	  </div>
  	 </div>
  	 
  	 <div class="row"> 
	      <div class="col-md-1"></div>
	      <div class="col-md-5 padding-bottom">
	      	 <div id="runTypeChart" class="chart"></div>
	  	  </div>
	      <div class="col-md-1"></div>
	      <div class="col-md-5 padding-bottom">	  
	          <h2>Top 10 Uninstall Name</h2>	 
	      	 <table class="table table-striped text-left ">
	      	  <thead>
	      	 	<tr><th>Name</th><th>Occur Count</th></tr>
	      	 	</thead>
	      	 	<tbody>
	      	 	<c:forEach items="${uninstallTopFile}" var="m" >
			  	  	<tr><td>${m.name}</td><td>${m.cnt}</td></tr>
			  	  </c:forEach>
			  	  </tbody>
	      	 </table>
	  	  </div>
 	 </div>
 	 
  	 <div class="row"> 
	  	  <div class="col-md-1"></div>
	      <div class="col-md-5 padding-bottom">	  
	          <h2>Avg uninstall time by VDA version</h2>	 
	      	 <table class="table table-striped text-left ">
	      	  <thead>
	      	 	<tr><th>VDA Version</th><th>Occur Count</th><th>Time used(min)</th></tr>
	      	 	</thead>
	      	 	<tbody>
	      	 	<c:forEach items="${vdaCleanTimeData}" var="m" >
			  	  	<tr><td>${m[0]}</td><td>${m[1]}</td><td>${m[2]}</td></tr>
			  	  </c:forEach>
			  	  </tbody>
	      	 </table>
	  	  </div>
	  	  <div class="col-md-1"></div>
	      <div class="col-md-5 padding-bottom">	  
	          <h2>Avg uninstall time by OS type</h2>	 
	      	 <table class="table table-striped text-left ">
	      	  <thead>
	      	 	<tr><th>OS Name</th><th>Occur Count</th><th>Time used(min)</th></tr>
	      	 	</thead>
	      	 	<tbody>
	      	 	<c:forEach items="${osCleanTimeData}" var="m" >
			  	  	<tr><td>${m[0]}</td><td>${m[1]}</td><td>${m[2]}</td></tr>
			  	  </c:forEach>
			  	  </tbody>
	      	 </table>
	  	  </div>
 	 </div>
 
   	 <div class="row"> 
	  	  <div class="col-md-1"></div>
	      <div class="col-md-5 padding-bottom">	  
	          <h2>Top 20 Error ID with Failed uninstall</h2>	 
	      	 <table class="table table-striped text-left ">
	      	  <thead>
	      	 	<tr><th>Error ID</th><th>Name</th><th>Occur Count</th></tr>
	      	 	</thead>
	      	 	<tbody>
	      	 	<c:forEach items="${uninstallFailedErrorId}" var="m" >
			  	  	<tr><td>${m[0]}</td><td>${m[1]}</td><td>${m[2]}</td></tr>
			  	  </c:forEach>
			  	  </tbody>
	      	 </table>
	  	  </div>
	  	  <div class="col-md-1"></div>
	      <div class="col-md-5 padding-bottom">	  
	          <h2>Top 20 Name with Failed uninstall</h2>	 
	      	 <table class="table table-striped text-left ">
	      	  <thead>
	      	 	<tr><th>Type</th><th>Name</th><th>Occur Count</th></tr>
	      	 	</thead>
	      	 	<tbody>
	      	 	<c:forEach items="${uninstallFailedTopFile}" var="m" >
			  	  	<tr><td>${m[0]}</td><td>${m[1]}</td><td>${m[2]}</td></tr>
			  	  </c:forEach>
			  	  </tbody>
	      	 </table>
	  	  </div>
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

var customerChart = echarts.init(document.getElementById('customerChart'));
var countryChart = echarts.init(document.getElementById('countryChart'));
var rebootTimesChart = echarts.init(document.getElementById('rebootTimesChart'));
var versionChart = echarts.init(document.getElementById('versionChart'));
var uninstallStatusChart = echarts.init(document.getElementById('uninstallStatusChart'));
var runTypeChart = echarts.init(document.getElementById('runTypeChart'));
var uninstallTypeChart = echarts.init(document.getElementById('uninstallTypeChart'));

var option = {
    title: {
        text: ' Customer Count',
        subtext: 'Total Record: ${totalCustomer}',
        x: 'center'
    },
    toolbox: {
        feature: {
            magicType: {
                type: ['bar', 'line']
            },
            dataView: {},
            saveAsImage: {
                pixelRatio: 2
            }
        }
    },
    tooltip: {},
    xAxis: {
        data: [
        	<c:forEach items="${customerData}" var="m" >
 	  	 		"${m.name}",
	  	  </c:forEach>
 	  	 ]
    },
    dataZoom: [
        {
            type: 'slider',
            show: true,
            xAxisIndex: [0],
            start: 0,
            end: 100
        },
    ],
    yAxis: {},
    series: [{
        name: 'Cumulative Count',
        type: 'bar',
		data:[
		  	  <c:forEach items="${customerData}" var="m" >
		  	  	 ${m.cnt},
		  	  </c:forEach>
		]
    }]
};
customerChart.setOption(option);

countryChart.setOption({
    title : {
        text: ' Deploy Country',
        subtext: 'Total Record: ${totalCustomer}',
        x:'center'
    },
    toolbox: {
        feature: {
            magicType: {
                type: ['bar', 'line']
            },
            dataView: {},
        }
    },
    tooltip: {},

    xAxis: {
        data: [
        	<c:forEach items="${countryData}" var="m" >
 	  	 		"${m.name}",
	  	  </c:forEach>
 	  	 ]
    },
    dataZoom: [
               {
                   type: 'slider',
                   show: true,
                   xAxisIndex: [0],
                   start: 0,
                   end: 100
               },
           ],
    yAxis: {},
    series: [{
        name: '',
        type: 'bar',
		data:[
		  	  <c:forEach items="${countryData}" var="m" >
		  	  	 ${m.cnt},
		  	  </c:forEach>
		]
    }]
});


versionChart.setOption({
    title : {
        text: ' VCU Version',
        subtext: 'Total Record: ${totalCount}',
        x:'center'
    },
    legend: {},
    tooltip : {
        trigger: 'item',
        formatter: "{b} <br/> {c} ({d}%)"
    },
    series : [
        {
            name: '',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
  		  	  <c:forEach items="${vcuVersionData}" var="m" >
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

rebootTimesChart.setOption({
    title : {
        text: ' Reboot Times ',
        x:'center'
    },
    legend: {},
    tooltip : {
        trigger: 'item',
        formatter: "{b} <br/> {c} ({d}%)"
    },
    series : [
        {
            name: '',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
  		  	  <c:forEach items="${rebootTimesData}" var="m" >
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

runTypeChart.setOption({
    title : {
        text: ' Running Type ',
        x:'center'
    },
    legend: {},
    tooltip : {
        trigger: 'item',
        formatter: "{b} <br/> {c} ({d}%)"
    },
    series : [
        {
            name: '',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
  		  	  <c:forEach items="${runTypeData}" var="m" >
		  	  	{name: '${m.name}',value: ${m.cnt}},
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

uninstallStatusChart.setOption({
    title : {
        text: ' Uninstall Status ',
        x:'center'
    },
    legend: {},
    tooltip : {
        trigger: 'item',
        formatter: "{b} <br/> {c} ({d}%)"
    },
    series : [
        {
            name: '',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
  		  	  <c:forEach items="${uninstallStatus}" var="m" >
		  	  	{name: '${m.name}',value: ${m.cnt}},
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


uninstallTypeChart.setOption({
    title : {
        text: ' Uninstall Type ',
        x:'center'
    },
    legend: {},
    tooltip : {
        trigger: 'item',
        formatter: "{b} <br/> {c} ({d}%)"
    },
    series : [
        {
            name: '',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
  		  	  <c:forEach items="${uninstallType}" var="m" >
		  	  	{name: '${m.name}',value: ${m.cnt}},
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
