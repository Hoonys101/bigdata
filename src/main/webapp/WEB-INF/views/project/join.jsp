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
        .btn-cancel {
            background-color: #f44336; /* 배경색 */
            color: white; /* 텍스트 색상 */
            padding: 10px 20px; /* 내부 여백 */
            border: none; /* 테두리 없음 */
            border-radius: 5px; /* 테두리 둥글게 */
            cursor: pointer; /* 커서 모양 */
            transition: background-color 0.3s; /* 배경색 변경 시 부드러운 애니메이션 적용 */
        }

        .btn-cancel:hover {
            background-color: #d32f2f; /* 호버시 배경색 변경 */
        }
    </style>
     <script>
            function validateForm() {
                var id = document.getElementById("id").value;
                var pwd = document.getElementById("pwd").value;
                var userName = document.getElementById("user_name").value;
                var email = document.getElementById("email").value;
                var birthDate = document.getElementById("birth_date").value;
                var gender = document.querySelector('input[name="gender"]:checked');

                if (id === "") {
                    alert("아이디를 입력하세요.");
                    return false;
                }
                if (pwd === "") {
                    alert("비밀번호를 입력하세요.");
                    return false;
                }
                if (userName === "") {
                    alert("이름을 입력하세요.");
                    return false;
                }
                if (email === "") {
                    alert("이메일을 입력하세요.");
                    return false;
                }
                if (birthDate === "") {
                    alert("생년월일을 입력하세요.");
                    return false;
                }
                if (!gender) {
                    alert("성별을 입력하세요.");
                    return false;
                }
                return true;
            }
        </script>
</head>
<body>
<div>
    <h1 class="join-title">회원가입</h1>
</div>
<div class="form-wrap">
    <form action="join.do" method="post" onsubmit="return validateForm();">
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
                <td><input type="text" id="signup_date" name="signup_date" readonly></td>
            <script>
                // 현재 날짜를 가져오는 함수
                function getCurrentDate() {
                    var now = new Date();
                    var year = now.getFullYear();
                    var month = String(now.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1 필요
                    var day = String(now.getDate()).padStart(2, '0');
                    return year + '-' + month + '-' + day;
                }

                // 페이지가 로드될 때 실행되는 함수
                window.onload = function() {
                    // 현재 날짜를 가져와서 입력 필드에 설정
                    document.getElementById('signup_date').value = getCurrentDate();
                };
            </script>
        </table>
        <div class="foot">
            <button type="button" class="btn-cancel" onclick="goBack()">취소</button>
            <button type="submit" class="btn-join">회원가입</button>
        </div>
    </form>
</div>
<script>
        function goBack() {
            window.history.back();
        }
    </script>
</body>
</html>
