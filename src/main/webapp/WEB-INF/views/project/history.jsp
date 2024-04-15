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
                    <th>Stock Code 1</th>
                    <th>Stock Code 2</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Analysis Result</th>
                    <th>ID</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <!-- Iterate over the history list -->
                <c:forEach items="${history}" var="history">
                    <tr>
                        <td>${history.stock_code1}</td>
                        <td>${history.stock_code2}</td>
                        <td>${history.start_date}</td>
                        <td>${history.end_date}</td>
                        <td><a href='#'>${history.analysis_result}</a></td>
                        <td>${history.id}</td>
                        <td>
                            <form action="history.do" method="post">
                                <input type="hidden" name="analysis_result" value="${history.analysis_result}">
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