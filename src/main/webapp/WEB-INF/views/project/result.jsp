<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- history.html -->
<html>
<head>
    <meta charset="UTF-8">
    <title>chart</title>
    <style>
        .container {
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
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
    </style>

</head>
<body>

<div class="header">
    <button class="home-button" onclick="location.href='home.do'">Home</button>
</div>
<div class="container">
    <h2 style="text-align: center;">결과</h2>
</div>

<table>
    <thead>
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
        <!-- 각 ServiceUsage 객체에 대한 반복문 -->
        <c:forEach items="${serviceUsages}" var="serviceUsages">
                <td>${serviceUsages.id}</td>
                <td>${serviceUsages.stock_code1}</td>
                <td>${serviceUsages.stock_code2}</td>
                <td>${serviceUsages.start_date}</td>
                <td>${serviceUsages.end_date}</td>
                <td>${serviceUsages.report}</td>
                <td>
                    <form action="analysis_page.do" method="post">
                        <input type="hidden" name="serviceusage_seq" value="${serviceUsages.serviceusage_seq}">
                        <input type="hidden" name="id" value="${serviceUsages.id}">
                        <input type="hidden" name="stock_code1" value="${serviceUsages.stock_code1}">
                        <input type="hidden" name="stock_code2" value="${serviceUsages.stock_code2}">
                        <input type="hidden" name="start_date" value="${serviceUsages.start_date}">
                        <input type="hidden" name="end_date" value="${serviceUsages.end_date}">
                        <input type="hidden" name="report" value="${serviceUsages.report}">
                        <input type="submit" value="분석 페이지로">
                    </form>
                </td>
                <td>
                <form action="resultDel.do" method="post">
                        <input type="hidden" name="serviceusage_seq" value="${serviceUsage.serviceusage_seq}">
                        <input type="hidden" name="id" value="${serviceUsages.id}">
                        <input type="hidden" name="stock_code1" value="${serviceUsages.stock_code1}">
                        <input type="hidden" name="stock_code2" value="${serviceUsages.stock_code2}">
                        <input type="hidden" name="start_date" value="${serviceUsages.start_date}">
                        <input type="hidden" name="end_date" value="${serviceUsages.end_date}">
                        <input type="hidden" name="report" value="${serviceUsages.report}">
                        <input type="submit" value="삭제">
                    </form>
                    <%--<a href='resultDel.do?serviceUsages.serviceusage_seq=${serviceUsages.serviceusage_seq}'>삭제</a>--%>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
