<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

	<jsp:include page="header.jsp" ></jsp:include>
	
    <div class="container-fluid text-center">
      <div class="page-header">
        <h1>LTSR Assistant CEIP Dashboard</h1><span>Data updated @ ${lastUpdateTime}</span>
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
