<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

	<jsp:include page="header.jsp" ></jsp:include>
	
    <div class="container-fluid text-center">
      <div class="page-header">
        <h1>LinuxVDA CEIP Dashboard</h1><span>Data updated @ ${lastUpdateTime}</span>
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
      	 <div id="installTypeChart" class="chart"></div>
  	  </div>

      <div class="col-md-1"></div>
      <div class="col-md-5 padding-bottom">
      	 <div id="adSolutionChart" class="chart"></div>
  	  </div>
  	  <div class="col-md-1"></div>
      <div class="col-md-5 padding-bottom">
      	 <div id="osNameChart" class="chart"></div>
      	 <table class="table table-striped text-left ">
      	  <thead>
      	 	<tr><th>OS Name</th><th>Count</th></tr>
      	 	</thead>
      	 	<tbody>
      	 	<c:forEach items="${osNameData}" var="m" >
		  	  	<tr><td>${m.name}</td><td>${m.cnt}</td></tr>
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
var installTypeChart = echarts.init(document.getElementById('installTypeChart'));
var versionChart = echarts.init(document.getElementById('versionChart'));
var osNameChart = echarts.init(document.getElementById('osNameChart'));
var adSolutionChart = echarts.init(document.getElementById('adSolutionChart'));

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

installTypeChart.setOption({
    title : {
        text: ' Upgrade or New ',
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
  		  	  <c:forEach items="${installTypeData}" var="m" >
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
        text: ' VDA Version',
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
  		  	  <c:forEach items="${versionData}" var="m" >
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


adSolutionChart.setOption({
    title : {
        text: ' AD Solution Method',
        subtext: ' Total Count: ${totalCount} ',
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
  		  	  <c:forEach items="${adSolutionData}" var="m" >
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


osNameChart.setOption({
    title : {
        text: ' OS Name Version',
        subtext: ' Total count: ${totalCount} ',
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
  		  	  <c:forEach items="${osNameData}" var="m" >
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
