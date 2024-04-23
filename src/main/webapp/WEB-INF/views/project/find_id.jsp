<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>아이디 찾기</title>
    <style>
    .header {
             text-align: center;
             padding: 20px 0;
             top: 0;


         }
        .home-button {
         background-color: #007bff;
          color: #fff;
          border: none;
          border-radius: 5px;
          padding: 10px 20px;
          cursor: pointer;

    }


        .home-button:hover {
            background-color: #0056b3; /* hover 시 색 변화 */
        }
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 80%;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #333;
        }
        form {
            max-width: 400px;
            margin: 0 auto;
        }
        label {
            font-weight: bold;
            color: #666;
        }
        input[type="text"],
        input[type="email"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button[type="submit"] {
            background-color: #007bff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button[type="submit"]:hover {
            background-color: #0056b3;
        }
        .center {
            text-align: center;
          }
    </style>
</head>
<body>
<div class="header">
        <button class="home-button" onclick="location.href='home.do'">Home</button>

</div>
    <div class="container">
        <h1>아이디 찾기</h1>
        <% if (session.getAttribute("message") != null) { %>
            <div class="modal" id="myModal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <p><c:out value="${sessionScope.message}"/></p>
                </div>
            </div>
            <% session.removeAttribute("message"); %>
        <% } %>
        <form action="find_id.do" method="post">
            <label for="user_name">이름:</label><br>
            <input type="text" id="user_name" name="user_name" required><br>
            <label for="birth_date">생년월일:</label><br>
            <div class="input-group">
                <input type="date" id="birth_date" name="birth_date" onchange="convertDateFormat()" required>
                <span class="input-group-addon">
                    <i class="fa fa-calendar"></i>
                </span>
            </div><br>
            <label for="email">이메일:</label><br>
            <input type="email" id="email" name="email" required><br><br>
            <div class="center">
                <button type="submit">아이디 찾기</button>
              </div>
        </form>
    </div>

    <!-- 스크립트 추가 -->
    <script>
        var modal = document.getElementById("myModal");
        var span = document.getElementsByClassName("close")[0];

        window.onload = function() {
            var modalMessage = "<c:out value='${sessionScope.message}'/>";
            if (modalMessage.trim() !== "") {
                document.querySelector(".modal-content p").innerText = modalMessage;
                modal.style.display = "block";
                setTimeout(function() {
                    modal.style.display = "none";
                }, 5000);
            }
        };

        span.onclick = function() {
            modal.style.display = "none";
        };

        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        };
    document.getElementById("findIdForm").addEventListener("submit", function (event) {
                event.preventDefault(); // 기본 제출 동작을 중지합니다.

                // AJAX 요청을 보냅니다.
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "find_id.do", true);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            var response = JSON.parse(xhr.responseText);
                            if (response === "success") {
                                alert("Email successfully sent.");
                            } else {
                                alert("Failed to send email.");
                            }
                        } else {
                            console.error("Request failed");
                        }
                    }
                };
                // 폼 데이터를 JSON 형식으로 변환하여 전송합니다.
                var formData = {
                    userName: document.getElementById("user_name").value,
                    birthDate: document.getElementById("birth_date").value,
                    email: document.getElementById("email").value
                };
                xhr.send(JSON.stringify(formData));
            });
            document.addEventListener("DOMContentLoaded", function() {
                // 현재 날짜를 YYYYMMDD 형식으로 변환하여 날짜 입력 필드에 설정
                const today = new Date();
                const year = today.getFullYear();
                const month = String(today.getMonth() + 1).padStart(2, "0");
                const day = String(today.getDate()).padStart(2, "0");
                const formattedDate = `${year}-${month}-${day}`;
                document.getElementById("birth_date").value = formattedDate;
            });

        </script>
</body>
</html>
