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
          <ul class="nav navbar-nav">
            <li><a href="#"></a></li>
            <li><a href="../upm/">UPM</a></li>
            <li class="active"><a href="#">Session Recording</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
	        <li><a href="mailto:bo.chen@citrix.com">Report Problem</a></li>
	      </ul>
        </div> 
      </div>
    </nav>

    <div class="container-fluid text-center">
      <div class="page-header">
        <h1>Session Recording CEIP Dashboard</h1><span>Data updated @ ${lastUpdateTime}</span>
      </div>

	<div class="row">
	   <div class="col-md-12 padding-bottom">
	     <div id="customerChart" class="chart-full"></div>
	   </div>			
	</div>
    
    <div class="row">
       <div class="col-md-1"></div>
       <div class="col-md-5 padding-bottom">
      	 <div id="countryChart" class="chart"></div>
  	  </div>
  	  <div class="col-md-1"></div>
      <div class="col-md-5 padding-bottom">
      	 <div id="versionChart" class="chart"></div>
  	  </div>

      <div class="col-md-1"></div>
      <div class="col-md-5 padding-bottom">
      	 <div id="osChart" class="chart"></div>
  	  </div>
  	  <div class="col-md-1"></div>
      <div class="col-md-5 padding-bottom">
      	 <div id="deploySizeChart" class="chart"></div>
  	  </div>
 	  </div>
 	  	
      <div class="row">
      <div class="col-md-12 padding-bottom">
      	 <div id="recordingTypeChart" class="chart-full"></div>
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
var osChart = echarts.init(document.getElementById('osChart'));
var versionChart = echarts.init(document.getElementById('versionChart'));
var deploySizeChart = echarts.init(document.getElementById('deploySizeChart'));
var recordingTypeChart = echarts.init(document.getElementById('recordingTypeChart'));

var option = {
    title: {
        text: ' Customer Count',
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
        name: 'Cumulative count',
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
        subtext: '',
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
    dataZoom: [],
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

osChart.setOption({
    title : {
        text: ' Server OS',
        subtext: '',
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
  		  	  <c:forEach items="${osData}" var="m" >
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
        text: ' Version',
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

deploySizeChart.setOption({
    title : {
        text: ' Deploy Size',
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
  		  	  <c:forEach items="${deploySizeData}" var="m" >
		  	  	{name: '${m.key}',value:${m.value}},
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


recordingTypeChart.setOption({
    title : {
        text: ' Recording Type ',
        x:'center'
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {           
            type : 'shadow'
        }
    },
    legend: {
        data:[
            'Application number','Desktop number'
        ],
        bottom:'10%'
    },
    calculable : true,
    grid: {borderWidth:0,y2:100},
    xAxis : [
        {
            type : 'category',
            splitLine: {show:false},
            axisLine:{show:true},
            splitArea:{show:false},
            axisTick:{show:true},
            data : [
    		 <c:forEach items="${recordingTypeData}" var="m" >
  		  	  	'${m.month}',
  		  	  </c:forEach>
            ]
        },
    ],
    yAxis : [
        {
            type : 'value',
            splitLine: {show:false},
            axisLine:{show:true},
            splitArea:{show:false},
            axisTick:{show:true},
            axisLabel:{formatter:'{value}'}
        }
    ],
    series : [
        {
            name:'Application number',
            type:'bar',
            stack:'total',
            itemStyle: {
                normal: {
                    barBorderRadius: 0, 
                    color: "rgba(230,19,16,0.5)", 
                    label: {
                        show: true, 
                        textStyle: {
                            "color": "rgba(0,0,0,1)"
                        }, 
                        position: "insideBottom",
                        formatter : function(p) {
	                         return p.value > 0 ? (p.value ): '';
	                      }
                    }
                }
            }, 
            data:[
       		 <c:forEach items="${recordingTypeData}" var="m" >
		  	  	${m.recordingNum},
		  	  </c:forEach>
            ]
        },
        {
            name:'Desktop number',
            type:'bar',
            stack:'total',
            itemStyle:{ normal: {
                color: "rgba(51,204,112,1)", 
                barBorderRadius: 0, 
                label: {
                    show: true, 
                    position: "top",
                    formatter : function(p) {
                       return p.value > 0 ? (
                               + p.value + '')
                               : '';
                      }
                }
              }
            },
            data:[
          		 <c:forEach items="${recordingTypeData}" var="m" >
 		  	  	${m.desktopNum},
 		  	  </c:forEach>
            	]
        },
    ]
});

</script>
</html>
