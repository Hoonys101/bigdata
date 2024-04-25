<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-Vydh3tL5HYuJyxCCPpflS68BikTc0sQwtLsXG0zwR2KbdqPJTlFz8nIG4ZMbZIzZtFZ6MqCWcY7WpK6dckfz9w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>홈페이지</title>
<style>
  /* 스타일링을 위한 예시 CSS */
  header {
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
       background-color: rgba(0, 0, 0, 0.5); /* 투명 배경 추가 */
          padding: 5px 10px; /* 배경에 여백 추가 */
      color: white;
      padding: 10px;
      text-align: center;
      font-weight: bold; /* 글꼴 굵기 설정 */
          font-size: 32px; /* 글꼴 크기 설정 */
    }
  body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-image: url("/img/1602.png"); /* 배경 이미지 설정 */
    background-size: cover; /* 배경 이미지를 컨테이너에 맞게 조정 */
    background-position: center; /* 배경 이미지를 가운데 정렬 */

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
   /* 이미지 스타일링을 위한 CSS */

     nav a {
            display: inline-block;
                padding: 10px 20px; /* 링크 안의 내용과 주변 여백 설정 */
                background-color: #007bff; /* 배경색 설정 */
                color: #fff; /* 글자색 설정 */
                text-decoration: none; /* 밑줄 제거 */
                border-radius: 5px; /* 둥근 테두리 설정 */
                margin: 0 10px; /* 각 링크 사이 여백 설정 */

        }
        nav {
             text-align: right; /* 링크 요소를 오른쪽으로 정렬 */
            color: #black;
            padding: 10px;
            text-align: center;
          }
  nav a:hover {
      background-color: #0056b3; /* 호버 시 배경색 변경 */
  }


 .analyze-button {
   display: inline-block;
   padding: 10px 20px;
   background-color: #4CAF50; /* 버튼 배경색 */
   color: white;
   text-decoration: none;
   border-radius: 5px;
   transition: background-color 0.3s; /* 호버 효과를 위한 전환 */
 }
.analyze-buttons {
    margin-top: 20px; /* 변경 가능한 간격 크기 */
}
 .analyze-button:hover {
   background-color: #45a049; /* 호버 시 배경색 변경 */
 }
 .welcome-message {
     font-size: 28px;
     font-weight: bold;
     color: #fff;
     background-color: #A0D3F9;
     padding: 15px 30px;
     border-radius: 10px;
     display: inline-block;
     margin-bottom: 20px;
     box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
     text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
 }
 .container {
     max-width: 400px;
     margin: 0 auto;
     padding: 20px;
     border: 1px solid #ccc;
     border-radius: 5px;
     background-color: #f9f9f9;
     margin-top: 15px;
   }
   h2 {
     text-align: center;
   }
   .login-box {
     width: 100%;
     max-width: 300px;
     padding: 50px;
     background-color: #fff;
     border-radius: 5px;
     box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
     text-align: center;

   }
 input-group {
     margin-bottom: 15px;
   }

   .input-group label {
     display: block;
     margin-bottom: 5px;
     margin-top: 15px;
   }

   .input-group input[type="text"],
   .input-group input[type="password"] {
     width: 95%;
     padding: 8px;
     font-size: 16px;
     border-radius: 5px;
     border: 1px solid #ccc;
   }

   input[type="submit"] {
     width: 100%;
     padding: 10px;
     font-size: 16px;
     color: #fff;
     background-color: #007bff;
     border: none;
     border-radius: 5px;
     cursor: pointer;
     margin-top: 15px;
   }

   .links {
     text-align: center;
      margin-top: 15px;
   }

   .links a {
     color: #007bff;
     text-decoration: none;
     margin-top: 15px;
   }

   .links a:hover {
     text-decoration: underline;
   }

   .links a:not(:last-child) {
     margin-right: 5px; /* 링크 사이의 간격 조정 */
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
<header>
  <h1>빅데이터 분석 플랫폼</h1>
</header>

        <c:if test = "${ empty sessionScope.id}">
                <div class="container">
                    <h2>로그인</h2>
                    <form action="login.do" method="post" onsubmit="return validateForm()">
                      <div class="input-group">
                        <label for="id">아이디:</label>
                        <input type="text" id="id" name="id" placeholder="아이디">
                      </div>
                      <div class="input-group">
                        <label for="pwd">비밀번호:</label>
                        <input type="password" id="pwd" name="pwd" placeholder="비밀번호">
                      </div>
                      <input type="submit" value="로그인">
                      <c:if test="${not empty error}">
                                        <div class="error-box">
                                            <p class="error-message">${error}</p>
                                        </div>
                                    </c:if>
                    </form>
                    <div class="links">
                      <a href="join.do">회원가입</a>|
                      <a href="find_id.do">아이디찾기</a>|
                      <a href="find_pwd.do">비밀번호찾기</a>
                    </div>
                  </div>
            </c:if>
            <nav>
            <c:if test="${not empty sessionScope.id }">
                <div class="welcome-message">${sessionScope.id}님 환영합니다.</div>
                <div>
                <%--<a href="#">내 정보</a>|--%>
                <a href="logout.do" id="logout-link">로그아웃</a>|
               <a href="withdraw.do?id=${sessionScope.id}" id="withdraw-link">회원탈퇴</a>
               </div>
                <div class="analyze-buttons">
                        <a href="history.do" class="analyze-button">히스토리</a>|
                        <a href="add2.do" class="analyze-button">기업추가</a>
                    </div>
                <div class="analyze-buttons">
                        <a href="analysis_page3.do" class="analyze-button">제외분석</a>|
                        <a href="analysis_page.do" class="analyze-button">기간분석</a>
                    </div>
                    <div class="analyze-buttons">
                        <a href="analysis_page4.do" class="analyze-button">제외분기분석</a>|
                        <a href="analysis_page2.do" class="analyze-button">분기분석</a>

                        </div>
            </c:if>
    </nav>




<footer>
    <p><img src="/img/platform_logo.png" alt="Platform Logo" height="40px"> &copy; 2024 빅 데이터 분석 플랫폼 - 데이터로 더 나은 미래를 만듭니다.

    </p>
</footer>
<script>
    // 로그아웃 메시지 표시
    var logoutLink = document.getElementById('logout-link');
    if (logoutLink) {
        logoutLink.addEventListener('click', function() {
            alert('로그아웃이 되었습니다.');
        });
    }

    // 회원탈퇴 메시지 표시
    var withdrawLink = document.getElementById('withdraw-link');
    if (withdrawLink) {
        withdrawLink.addEventListener('click', function() {
            alert('회원탈퇴가 되었습니다.');
        });
    }
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

</script>
</body>
</html>
