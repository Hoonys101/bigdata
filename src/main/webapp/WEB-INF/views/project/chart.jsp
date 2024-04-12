<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>분석 결과 리포트</title>
<style>
.inline-img{
display: inline-block;
margin-right:10px;
}
</style>
//<script src="https://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<body>
<div>"${report}"</div>
<div>
<c:forEach items="${plots}" var= "plot" varStatus="loop">
<img src="${pageContext.request.contextPath}/../${plot}" alt="그래프" class="inline-img">
</c:forEach>

</div>
<script type="text/javascript">
var dataArray =[
    ['weeks', 'correlation'],
    <c:forEach items="${dataList}" var="data" varStatus="loop">
                    ['${loop.index}', ${data[1]}]<c:if test="${!loop.last}">,</c:if>
                </c:forEach>
    ];
    // Google Charts API 초기화
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    // 차트를 그리는 함수
    function drawChart() {
      // 데이터 생성
      var data = google.visualization.arrayToDataTable(dataArray);

      // 옵션 설정
      var options = {
        title: '분석결과',
        curveType: 'function',
        legend: { position: 'bottom' }
      };

      // 차트 생성 및 그리기
      var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
      chart.draw(data, options);
    }
  </script>


  <!-- 그래프가 표시될 div 요소 -->
  <div id="chart_div" style="width: 900px; height: 500px;"></div>
</body>
</html>
