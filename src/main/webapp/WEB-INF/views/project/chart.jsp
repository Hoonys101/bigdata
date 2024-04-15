<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>분석 결과 리포트</title>
    <style>
        .inline-img {
            display: none; /* 이미지는 숨김 처리 */
            margin-right: 10px;
            width: 300px;
        }
        #loading { /* 로딩 화면 스타일 */
            display: none;
            position: fixed;
            z-index: 9999;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }
    </style>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<body>
<div id="loading">Loading...</div> <!-- 로딩 화면 -->
<div>${report}</div>
<div id="images"> <!-- 이미지를 담을 div -->
    <c:if test="${not empty plots}">
        <c:forEach items="${plots}" var="plots" varStatus="loop">
             <img src="${plots}" alt="그래프" class="inline-img">
        </c:forEach>
    </c:if>
</div>
<!-- 그래프가 표시될 div 요소 -->
<div id="chart_div" style="width: 900px; height: 500px;"></div>

<script type="text/javascript">
    var dataArray = [
        ['Index', 'Value'],
        <c:forEach items="${dataList}" var="data" varStatus="loop">
            ['${data[0]}', ${data[1]}]<c:if test="${!loop.last}">,</c:if>
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

        // 이미지가 로드되었을 때 처리
        var images = document.getElementsByClassName('inline-img');
        for (var i = 0; i < images.length; i++) {
            images[i].style.display = 'inline-block'; // 이미지 보이기
        }
        document.getElementById('loading').style.display = 'none'; // 로딩 화면 숨기기
    }
</script>
</body>
</html>
