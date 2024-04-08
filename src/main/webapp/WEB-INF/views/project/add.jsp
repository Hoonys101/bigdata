<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>데이터 선택</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        h2 {
            text-align: center;
            margin-top: 50px;
        }

        form {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        select, button {
            display: block;
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }

        button {
            background-color: #4caf50;
            color: white;
            border: none;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>

</head>
<body>
    <h2>데이터 선택</h2>
    <form action="list1.do" method="post"> <!-- 선택한 결과를 보여줄 페이지로 이동합니다. -->
        <%--<select name="stock_code" id="stock_code" onchange="loadIndustries()">
            <option value="">종목 코드 선택</option>
            <c:forEach items="${stock_code}" var="stock_code">
                <option value="${stock_code}">${stock_code}</option>
            </c:forEach>
        </select>--%>
        <select name="nation" id="nation" onchange="loadStockCodes()">
            <option value="">국가 선택</option>
            <c:forEach items="${nation}" var="nation">
                <option value="${nation}">${nation}</option> <!-- 국가 코드를 선택값으로 사용합니다. -->
            </c:forEach>
        </select>
        <select name="db_name">
            <option value="">DB</option>
            <c:forEach items="${db_name}" var="db_name">
            <option value="${db_name}">${db_name}</option> <!-- 회사 ID를 선택값으로 사용합니다. -->
            </c:forEach>
        </select>

        <select name="sector" id="sector">
            <option value="">업종 선택</option>
            <c:forEach items="${sector}" var="sector">
                <option value="${sector}">${sector}</option>
            </c:forEach>
        </select>
        <select name="name">
            <option value="">회사명 선택</option>
            <c:forEach items="${name}" var="name">
                <option value="${name}">${name}</option> <!-- 회사 ID를 선택값으로 사용합니다. -->
            </c:forEach>
        </select>
        <button type="submit">데이터 확인</button>
    </form>
</body>
</html>
