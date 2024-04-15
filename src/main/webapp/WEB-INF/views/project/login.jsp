<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>로그인</title>
<style>
  body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f0f0f0;
  }

  .container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
  }

  .login-box {
    width: 100%;
    max-width: 400px;
    padding: 20px;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    text-align: center;
  }

  input[type="text"],
  input[type="password"],
  input[type="submit"] {
    width: 100%;
    padding: 10px;
    margin-bottom: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
  }

  input[type="submit"] {
    background-color: #007bff;
    color: #fff;
    cursor: pointer;
  }

  input[type="submit"]:hover {
    background-color: #0056b3;
  }

  .home-btn {
    width: 100%;
    padding: 10px;
    margin-top: 20px;
    background-color: #ccc;
    border: none;
    border-radius: 5px;
    color: #333;
    cursor: pointer;
  }

  .home-btn:hover {
    background-color: #999;
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
</head>
<body>

<div class="container">
  <div class="login-box">
    <h2>로그인</h2>
    <form id="loginForm" method="post" onsubmit="return validateForm()">
      <input type="text" id="id" name="id" placeholder="아이디">
      <input type="password" id="pwd" name="pwd" placeholder="비밀번호">
      <input type="submit" value="로그인">
      <c:if test="${not empty error}">
                  <div class="error-box">
                      <p class="error-message">${error}</p>
                  </div>
              </c:if>
    </form>
    <button class="home-btn" onclick="goToHome()">메인으로</button>
  </div>
</div>

<script>
function validateForm() {
  var id = document.getElementById('id').value;
  var pwd = document.getElementById('pwd').value;

  if (id.trim() === '') {
    alert('아이디를 입력해주세요.');
    return false;
  }

  if (pwd.trim() === '') {
    alert('비밀번호를 입력해주세요.');
    return false;
  }

  return true;
}

function goToHome() {
  window.location.href = 'home.do'; // 메인 페이지 URL로 변경
}
</script>

</body>
</html>
