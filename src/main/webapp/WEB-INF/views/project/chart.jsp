<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>분석 결과 리포트</title>
<style>
body {
      <%--background-image: url("/img/data-analsis-tool-img-3-1030x644.png")--%>
       background-color: white;
    }
.inline-img {
        display: inline-block;
        width: 20%; /* 한 줄에 5개의 이미지를 표시하기 위해 100%를 5등분하여 각 이미지의 너비를 설정 */
        height: 350px; /* 이미지의 높이를 자동으로 조정하여 가로 세로 비율을 유지 */
        margin-right: -10px; /* 이미지 간격을 없애기 위해 음수 마진을 사용 */
    }
    .image-container {
            width: 100%; /* 부모 요소의 너비에 따라 이미지 컨테이너의 너비를 조정 */
            text-align: center; /* 이미지를 가운데 정렬 */
        }
    .header {
             text-align: center;
             padding: 20px 0;
             top: 0;

         }
        .home-button {
         background-color: #007bff;
          color: #fff;
          border: none;
          border-radius: 5px;
          padding: 10px 20px;
          cursor: pointer;
          transition: background-color 0.3s ease;
    }
        .home-button:hover {
            background-color: #0056b3; /* hover 시 색 변화 */
        }

</style>
<script src="https://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<body>
<div class="header">
        <button class="home-button" onclick="location.href='home.do'">Home</button>
</div>
<h3 style="text-align: center;">회사 1: ${company1}  회사 2: ${company2}</h3>
 <div style="text-align: left;">
     <span style="color: blue;">ㅡ : </span>
     <span>${company1}</span>
 </div>
 <div style="text-align: left;">
     <span style="color: orange;">ㅡ : </span>
     <span>${company2}</span>
 </div>
<div style="font-size: 18px; font-weight: bold; color: #333; text-align: center;">결과값 : ${report}</div>
<div class="image-container">
<c:forEach items="${plots}" var="plot" varStatus="loop">
    <img src="${plot}" alt="그래프" class="inline-img" >
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
          legend: { position: 'bottom' },
          annotations: {
            textStyle: {
              fontSize: 12,
              color: '#555' // 텍스트 색상 지정
            }
          }
        };

      // 차트 생성 및 그리기
      var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
      // 차트를 그리고 크기를 조정하는 함수
        function drawChartWithResize() {
          chart.draw(data, options);
          // 그래프를 그리고 난 후 자동으로 크기를 조정
          setTimeout(function() {
                chart.draw(data, options);
                var boundingBox = document.getElementById('chart_div').getBoundingClientRect();
                var newWidth = boundingBox.width + 500; // 100 픽셀의 여유 공간을 추가하여 이미지 크기를 키움
                document.getElementById('chart_div').style.width = newWidth + 'px';
              }, 100);
            }

        // 창 크기가 변경될 때마다 그래프를 다시 그리고 크기를 조정
        window.addEventListener('resize', drawChartWithResize);

        // 초기 그래프 그리기
        drawChartWithResize();
      }

  </script>


  <!-- 그래프가 표시될 div 요소 -->
  <div id="chart_div" style="width: 120%; height: 400px; position: relative; left: -8%"></div>
<h3 style="text-align: center;">회사 1: ${company2}  회사 2: ${company1}</h3>
 <div style="text-align: left;">
     <span style="color: blue;">ㅡ : </span>
     <span>${company2}</span>
 </div>
 <div style="text-align: left;">
     <span style="color: orange;">ㅡ : </span>
     <span>${company1}</span>
 </div>
   <div style="font-size: 18px; font-weight: bold; color: #333; text-align: center;">결과값 : ${report2}</div>
   <div class="image-container">
   <c:forEach items="${plots2}" var="plot" varStatus="loop">
       <img src="${plot}" alt="그래프" class="inline-img" >
   </c:forEach>
   </div>
  <div id="chart_div2" style="width: 120%; height: 400px; position: relative; left: -8%"></div>
<script>
var dataArray2 =[
    ['weeks', 'correlation'],
    <c:forEach items="${dataList2}" var="data" varStatus="loop">
        ['${loop.index}', ${data[1]}]<c:if test="${!loop.last}">,</c:if>
    </c:forEach>
];

// Google Charts API 초기화
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawSecondChart); // 두 번째 차트를 그리는 콜백 함수 설정

// 두 번째 차트를 그리는 함수
function drawSecondChart() {
    // 데이터 생성
    var data = google.visualization.arrayToDataTable(dataArray2);
    // 옵션 설정
    var options = {
        title: '분석결과',
        curveType: 'function',
        legend: { position: 'bottom' },
        annotations: {
            textStyle: {
                fontSize: 12,
                color: '#555' // 텍스트 색상 지정
            }
        }
    };

    // 차트 생성 및 그리기
    var chart2 = new google.visualization.LineChart(document.getElementById('chart_div2'));

    // 차트를 그리고 크기를 조정하는 함수
    function drawSecondChartWithResize() {
        chart2.draw(data, options);
        // 그래프를 그리고 난 후 자동으로 크기를 조정
        setTimeout(function() {
            chart2.draw(data, options);
            var boundingBox = document.getElementById('chart_div2').getBoundingClientRect();
            var newWidth = boundingBox.width + 500; // 100 픽셀의 여유 공간을 추가하여 이미지 크기를 키움
            document.getElementById('chart_div2').style.width = newWidth + 'px';
        }, 100);
    }

    // 창 크기가 변경될 때마다 그래프를 다시 그리고 크기를 조정
    window.addEventListener('resize', drawSecondChartWithResize);

    // 초기 그래프 그리기
    drawSecondChartWithResize();
}
</script>
</body>

</html>
