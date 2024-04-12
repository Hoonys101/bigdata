<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chart Page</title>
</head>
<body>
    <h1>Chart</h1>

    <%-- 이미지 파일의 경로 생성 --%>
    <img src="/img/plots/${stock_code1}_${stock_code2}_${start_date}_${end_date}.png" alt="Chart">

</body>
</html>
