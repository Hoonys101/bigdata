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
        .error-box {
                    background-color: #ffebee; /* 배경색 */
                    border: 1px solid #ffcdd2; /* 테두리 */
                    border-radius: 5px; /* 테두리 둥글게 */
                    padding: 10px 15px; /* 내부 여백 */
                    margin-top: 10px; /* 위쪽 마진 */
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 그림자 */
                }

                .error-message {
                    color: #f44336; /* 텍스트 색상 */
                    margin: 0; /* 마진 초기화 */
                    font-size: 14px; /* 폰트 크기 */
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

        // 회원가입 성공 시 메시지 창을 띄우는 함수
        function showSuccessMessage() {
            alert("회원가입이 성공적으로 완료되었습니다!");
                    window.location.href = "home.do";
        }

        // 폼 서브밋 이벤트를 감지하여 처리
        document.querySelector("form").addEventListener("submit", function(event) {
                // 폼이 실제로 제출되지 않도록 기본 이벤트를 중지
                event.preventDefault();

                // 여기서 서버로 폼 데이터를 전송하고, 응답을 처리한다고 가정
                var formData = new FormData(document.querySelector("form"));
                fetch("join.do", {
                    method: "POST",
                    body: formData
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert("회원가입이 성공적으로 완료되었습니다!");
                        window.location.href = "home.do"; // 회원가입 성공 시 home.do로 리다이렉트
                    } else {
                        alert(data.message); // 서버에서 받은 실패 메시지 표시
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                });

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
        </table>
         <c:if test="${not empty error}">
             <div class="error-box">
                 <p class="error-message">${error}</p>
             </div>
         </c:if>
        <div class="foot">
            <button type="button" class="btn-cancel" onclick="goBack()">취소</button>
            <button type="submit" onclick="showSuccessMessage()" class="btn-join">회원가입</button>
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
