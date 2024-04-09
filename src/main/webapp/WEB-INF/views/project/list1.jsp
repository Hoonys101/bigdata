<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>첫 번째 기업 선택</title>
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
    <h2>첫 번째 기업 선택</h2>
    <form action="list1.do" method="post">
        <select name="db_name">
            <option value="">회사명 선택</option>
            <c:forEach items="${db_name}" var="db_name">
                <option value="${db_name}">${db_name}</option>
            </c:forEach>
        </select>
        <select name="stock_code">
                    <option value="">회사명 선택</option>
                    <c:forEach items="${stock_code}" var="stock_code">
                        <option value="${stock_code}">${stock_code}</option>
                    </c:forEach>
                </select>
        <button type="submit">기업 선택 완료</button>
    </form>
</body>
</html>
