<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homework1</title>
    <link rel="stylesheet" type="text/css" href="/css/app.css">
    <style>
        .join-title {
            text-align: center;
        }
    </style>
</head>
<body>
<div>
    <h1 class="join-title">회원가입</h1>
</div>
<div class="form-wrap">
    <form action="join.do" method="post">
        <table class="join-table">
            <tr class="join-tr">
                <th class="join-th"> <label for="id">ID</label></th>
                <td><input type="text" id="id" name="id"></td>
            </tr>
            <tr class="join-tr">
                <th class="join-th"><label for="pwd">비밀번호</label></th>
                <td><input type="password" id="pwd" name="pwd"></td>
            </tr>
            <tr class="join-tr">
                <th class="join-th"><label for="user_name">이름</label></th>
                <td><input type="text" id="user_name" name="user_name"></td>
            </tr>
            <tr class="join-tr">
                <th class="join-th"><label for="email">Email</label></th>
                <td><input type="text" id="email" name="email"></td>
            </tr>
            <%--<tr class="join-tr">
                <th class="join-th"><label for="birth_date">생년월일</label></th>
                <td>
                    <select id="birth_year" name="birthYear">
                        <!-- 생년월일 연도 옵션 추가 -->
                        <% for (int year = 1925; year <= 2025; year++) { %>
                            <option value="<%= year %>"><%= year %></option>
                        <% } %>
                    </select> 년
                    <select id="birth_month" name="birthMonth">
                        <!-- 생년월일 월 옵션 추가 -->
                        <% for (int month = 1; month <= 12; month++) { %>
                            <option value="<%= String.format("%02d", month) %>"><%= String.format("%02d", month) %></option>
                        <% } %>
                    </select> 월
                    <select id="birth_date" name="birthDate">
                        <!-- 생년월일 일 옵션 추가 -->
                        <% for (int date = 1; date <= 31; date++) { %>
                            <option value="<%= String.format("%02d", date) %>"><%= String.format("%02d", date) %></option>
                        <% } %>
                    </select> 일
                </td>
            </tr>--%>
            <tr class="join-tr">
                <th class="join-th"><label for="birth_date">생년월일</label></th>
                <td><input type="date" id="birth_date" name="birth_date"></td>
            </tr>
            <tr class="join-tr">
                <th class="join-th"><label for="gender">성별</label></th>
                <td>
                    <input type="radio" id="male" name="gender" value="남성">
                    <label for="male">남성</label>
                    <input type="radio" id="female" name="gender" value="여성">
                    <label for="female">여성</label>
                </td>
            </tr>
            <tr class="join-tr">
                <th class="join-th"><label for="signup_date">가입일자</label></th>
                <td><input type="date" id="signup_date" name="signup_date"></td>
            </tr>
        </table>
        <div class="foot">
            <button type="button" class="btn-cancle">취소</button>
            <button type="submit" class="btn-join">회원가입</button>
        </div>
    </form>
</div>
</body>
</html>
