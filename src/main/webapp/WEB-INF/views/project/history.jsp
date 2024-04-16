<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- history.html -->
<html>
<head>
    <meta charset="UTF-8">
    <title>History</title>
    <style>
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .container {
            width: 80%;
        }
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        .header {
            text-align: center;
            margin-bottom: 20px;
        }
        .header button {
            float: right;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>History</h2>
            <button onclick="location.href='home.do'">Home</button>
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
                <!-- Iterate over the history list -->
                <c:forEach items="${serviceUsages}" var="serviceUsage">
                    <tr>
                        <form action="analysis_page.do" method="post">
                            <input type="hidden" name="id" value="${serviceUsage.id}">
                            <input type="hidden" name="stock_code1" value="${serviceUsage.stock_code1}">
                            <input type="hidden" name="stock_code2" value="${serviceUsage.stock_code2}">
                            <input type="hidden" name="start_date" value="${serviceUsage.start_date}">
                            <input type="hidden" name="end_date" value="${serviceUsage.end_date}">
                            <input type="hidden" name="report" value="${serviceUsage.report}">
                            <td>${serviceUsage.id}</td>
                            <td>${serviceUsage.stock_code1}</td>
                            <td>${serviceUsage.stock_code2}</td>
                            <td>${serviceUsage.start_date}</td>
                            <td>${serviceUsage.end_date}</td>
                            <td>${serviceUsage.report}</td>
                            <td>
                                <input type="submit" value="분석 페이지로">
                            </td>
                        </form>
                        <td>
                            <form action="history.do" method="post">
                                <input type="hidden" name="report" value="${serviceUsage.report}">
                                <input type="submit" value="삭제">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
