<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 찾기</title>
    <style>
    .header {
             text-align: center;
             padding: 20px 0;
             top: 0;


         }
         footer {
               background-color: #555;
               color: #fff;
               padding: 15px;
               position: fixed;
               bottom: 0;
               width: 100%;
               text-align: center;
               font-family: Arial, sans-serif;
               font-size: 18px;
               font-weight: bold;
               text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
              background-color: rgba(0, 0, 0, 0.5); /* 투명 배경 추가 */
                 padding: 5px 10px; /* 배경에 여백 추가 */
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
        <h1>비밀번호 찾기</h1>
        <% if (session.getAttribute("message") != null) { %>
            <div class="modal" id="myModal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <p><c:out value="${sessionScope.message}"/></p>
                </div>
            </div>
            <% session.removeAttribute("message"); %>
        <% } %>
        <form action="find_pwd.do" method="post">
            <label for="user_name">이름:</label><br>
            <input type="text" id="user_name" name="user_name" required><br>
            <label for="email">이메일:</label><br>
            <input type="email" id="email" name="email" required><br><br>
            <div class="center">
                            <button type="submit">비밀번호 찾기</button>
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
                xhr.open("POST", "find_pwd.do", true);
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
                    email: document.getElementById("email").value
                };
                xhr.send(JSON.stringify(formData));
            });

        </script>
        <footer>
            <p>> &copy; 2024 빅 데이터 분석 플랫폼 - 데이터로 더 나은 미래를 만듭니다.

            </p>
            </footer>
</body>
</html>
