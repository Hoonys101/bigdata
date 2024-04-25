<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="java.util.List" %>
<%
// 세션에서 aiResult 값을 읽어옴
List<String> aiResultFromSession = (List<String>) session.getAttribute("aiResult");
%>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <meta charset="UTF-8">
    <title>Chart</title>
    <style>
        /* Common styling */
        .container {
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 50px; /* Added margin to separate header from content */
        }

        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        ul {
            list-style-type: none;
        }

        li {
            margin-bottom: 5px;
        }

        .header {
            text-align: center;
            padding: 20px 0;
            top: 0;

            width: 100%; /* Adjusted to cover the entire width */
            background-color: #ffffff; /* Added background color */
            z-index: 1; /* Ensures header stays on top */
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
            background-color: #0056b3;
        }

        /* Table styling */
        .table-container {
        width: 100%;
            max-height: 70vh; /* Set maximum height for the table container */
            overflow-y: auto; /* Enable vertical scrolling if table exceeds maximum height */
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        .fixed-header {
            position: sticky;
            top: 0;
            background-color: #f2f2f2; /* Background color for fixed header */
            z-index: 2; /* Ensure fixed header stays on top */
        }
    </style>
</head>
<body>

<div class="header">
    <button class="home-button" onclick="location.href='home.do'">Home</button>
</div>

<div class="container">

<h2 style="text-align: center;">회사 1: ${company1}  회사 2: ${company2}</h2>
<button id="ai-analysis-button" class="ai-button">AI 분석</button>

</div>
<div id="aiResult" class="aiResult" style="display: none;">
    <h3>회사 1과 회사 2의 연관성 분석 결과</h3>
    <!-- AI 분석 결과를 여기에 표시할 구조를 작성합니다. -->
    <ul>



    </ul>
    <div id="company-relationship-analysis">
        <!-- serviceUsages1 리스트의 각 요소에 대해 반복문을 사용하여 값을 가져옴 -->
        <c:forEach items="${serviceUsages1}" var="item">
            <input type="text" name="stock_code1_${item.id}" value="${item.stock_code1}">
            <input type="text" name="stock_code2_${item.id}" value="${item.stock_code2}">
        </c:forEach>
        ${aiResult}
    </div>
</div>
<div class="table-container">
    <table>
        <thead class="fixed-header">
        <tr>
            <th>ID</th>
            <th>Stock Code 1</th>
            <th>Stock Code 2</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Report</th>
            <th>Action</th>
            <th>삭제</th>
        </tr>
        </thead>
        <tbody>
            <!-- result1 테이블 표시 -->
            <c:forEach items="${serviceUsages1}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.stock_code1}</td>
                    <td>${item.stock_code2}</td>
                    <td>${item.start_date}</td>
                    <td>${item.end_date}</td>
                    <td>${item.report}</td>
                    <td>
                        <form action="analysis_page.do" method="post">
                            <input type="hidden" name="serviceusage_seq" value="${item.serviceusage_seq}">
                            <input type="hidden" name="id" value="${item.id}">
                            <input type="hidden" name="stock_code1" value="${item.stock_code1}">
                            <input type="hidden" name="stock_code2" value="${item.stock_code2}">
                            <input type="hidden" name="start_date" value="${item.start_date}">
                            <input type="hidden" name="end_date" value="${item.end_date}">
                            <input type="hidden" name="report" value="${item.report}">
                            <input type="submit" value="분석 페이지로">
                        </form>
                    </td>
                    <td>
                        <form action="resultDel.do" method="post">
                            <input type="hidden" name="serviceusage_seq" value="${item.serviceusage_seq}">
                            <input type="hidden" name="id" value="${item.id}">
                            <input type="hidden" name="stock_code1" value="${item.stock_code1}">
                            <input type="hidden" name="stock_code2" value="${item.stock_code2}">
                            <input type="hidden" name="start_date" value="${item.start_date}">
                            <input type="hidden" name="end_date" value="${item.end_date}">
                            <input type="hidden" name="report" value="${item.report}">
                            <input type="submit" value="삭제">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<div class="container">

<h2 style="text-align: center;">회사 1: ${company2}  회사 2: ${company1}</h2>
<button id="aai-analysis-button-2" class="ai-button">AI 분석</button> <!-- 수정된 아이디 사용 -->

</div>
<div id="aiResult" class="aiResult" style="display: none;">
    <h3>회사 1과 회사 2의 연관성 분석 결과</h3>
    <!-- AI 분석 결과를 여기에 표시할 구조를 작성합니다. -->
    <ul>

    </ul>
    <div id="company-relationship-analysis-2">
        <!-- serviceUsages1 리스트의 각 요소에 대해 반복문을 사용하여 값을 가져옴 -->
        <c:forEach items="${serviceUsages2}" var="item">
            <input type="text" name="stock_code1_${item.id}" value="${item.stock_code1}">
            <input type="text" name="stock_code2_${item.id}" value="${item.stock_code2}">
        </c:forEach>
    </div>
</div>
<div class="table-container">
    <table>
        <thead class="fixed-header">
        <tr>
            <th>ID</th>
            <th>Stock Code 1</th>
            <th>Stock Code 2</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Report</th>
            <th>Action</th>
            <th>삭제</th>
        </tr>
        </thead>
        <tbody>
            <!-- result2 테이블 표시 -->
            <c:forEach items="${serviceUsages2}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.stock_code1}</td>
                    <td>${item.stock_code2}</td>
                    <td>${item.start_date}</td>
                    <td>${item.end_date}</td>
                    <td>${item.report}</td>
                    <td>
                        <form action="analysis_page.do" method="post">
                            <input type="hidden" name="serviceusage_seq" value="${item.serviceusage_seq}">
                            <input type="hidden" name="id" value="${item.id}">
                            <input type="hidden" name="stock_code1" value="${item.stock_code1}">
                            <input type="hidden" name="stock_code2" value="${item.stock_code2}">
                            <input type="hidden" name="start_date" value="${item.start_date}">
                            <input type="hidden" name="end_date" value="${item.end_date}">
                            <input type="hidden" name="report" value="${item.report}">
                            <input type="submit" value="분석 페이지로">
                        </form>
                    </td>
                    <td>
                        <form action="resultDel.do" method="post">
                            <input type="hidden" name="serviceusage_seq" value="${item.serviceusage_seq}">
                            <input type="hidden" name="id" value="${item.id}">
                            <input type="hidden" name="stock_code1" value="${item.stock_code1}">
                            <input type="hidden" name="stock_code2" value="${item.stock_code2}">
                            <input type="hidden" name="start_date" value="${item.start_date}">
                            <input type="hidden" name="end_date" value="${item.end_date}">
                            <input type="hidden" name="report" value="${item.report}">
                            <input type="submit" value="삭제">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<script>
// AI 분석 버튼 클릭 이벤트 처리
document.getElementById('ai-analysis-button').addEventListener('click', function() {
    // Ajax 요청을 통해 서버에서 AI 분석 결과를 가져옵니다.
    $.ajax({
        url: '../project/ai_analysis_endpoint.do',
        method: 'POST',
        data: {
            stock_code1: $("input[name^='stock_code1_']:first").val(),
            stock_code2: $("input[name^='stock_code2_']:first").val()
        },
        success: function(response) {
            // 서버에서 받은 AI 분석 결과를 처리하여 화면에 표시합니다.
            var aiResult = response;

            // 결과를 보여줄 영역을 화면에 표시
            document.getElementById('aiResult').style.display = 'block';

            // 결과를 리스트 형태로 표시
            var resultList = document.createElement('ul');
            aiResult.forEach(function(result) {
                var listItem = document.createElement('li');
                listItem.textContent = result;
                resultList.appendChild(listItem);
            });

            // 기존 결과를 지우고 새 결과를 추가
            var analysisDiv = document.getElementById('company-relationship-analysis');
            analysisDiv.innerHTML = ''; // 기존 결과 삭제
            analysisDiv.appendChild(resultList); // 새 결과 추가
        },
        error: function(xhr, status, error) {
            // 오류 처리
            console.error('AJAX 오류:', status, error);
        }
    });
});

document.getElementById('ai-analysis-button-2').addEventListener('click', function() {
     // Ajax 요청을 통해 서버에서 AI 분석 결과를 가져옵니다.
     $.ajax({
         url: '../project/aai_analysis_endpoint.do',
         method: 'POST',
         data: {
             stock_code1: $("input[name^='stock_code1_']:first").val(),
             stock_code2: $("input[name^='stock_code2_']:first").val()
         },
         success: function(response) {
             // 서버에서 받은 AI 분석 결과를 처리하여 화면에 표시합니다.
             var aiResult = response;

             // 결과를 보여줄 영역을 화면에 표시
             document.getElementById('aiResult').style.display = 'block';

             // 결과를 리스트 형태로 표시
             var resultList = document.createElement('ul');
             aiResult.forEach(function(result) {
                 var listItem = document.createElement('li');
                 listItem.textContent = result;
                 resultList.appendChild(listItem);
             });

             // 기존 결과를 지우고 새 결과를 추가
             var analysisDiv = document.getElementById('company-relationship-analysis-2');
             analysisDiv.innerHTML = ''; // 기존 결과 삭제
             analysisDiv.appendChild(resultList); // 새 결과 추가
         },
         error: function(xhr, status, error) {
             // 오류 처리
             console.error('AJAX 오류:', status, error);
         }
     });
 });


</script>
</body>
</html>
