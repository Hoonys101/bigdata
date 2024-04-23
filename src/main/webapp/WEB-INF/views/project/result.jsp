<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
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
<div class="table-container">

<h2 style="text-align: center;">회사 1: ${company2}  회사 2: ${company1}</h2>
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

</body>
</html>
