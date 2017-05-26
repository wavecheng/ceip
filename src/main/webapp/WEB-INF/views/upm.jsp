<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	
	<jsp:include page="header.jsp" ></jsp:include>

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
