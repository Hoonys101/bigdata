<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>홈페이지</title>
<style>
  /* 스타일링을 위한 예시 CSS */
  body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
  }
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

  main {
    padding: 20px;
  }
  section {
    background-color: #f0f0f0;
    padding: 5px;
    margin-bottom: 10px;
  }
  article {
    background-color: #ddd;
    padding: 5px;
    margin-bottom: 10px;
  }
  aside {
    background-color: #ccc;
    padding: 5px;
  }
  footer {
    background-color: #333;
    color: #fff;
    padding: 10px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
  }
   /* 이미지 스타일링을 위한 CSS */
  .center {
    display: flex;
    justify-content: center;
  }
  .center img {
    width: 200px;
    height: auto;
  }
   .c {
    display: flex;
	gap: 10px; /* 요소 사이 간격 추가 */
	width: 50%; /* 원하는 너비 값 */
    margin: 0 auto; /* 수평 가운데 정렬 */
    display: inline-block; /* 요소를 한 줄로 배치 */
    margin: 0 10px; /* 각 요소 사이의 여백 조정 */
  }

body {
            background-image: url("/img/1602.png"); /* 배경 이미지 설정 */
            background-size: cover; /* 배경 이미지를 컨테이너에 맞게 조정 */
            background-position: center; /* 배경 이미지를 가운데 정렬 */

        }
     input , nav a {
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
 .analyze-section {
   text-align: center;
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
     background-color: #007bff;
     padding: 15px 30px;
     border-radius: 10px;
     display: inline-block;
     margin-bottom: 20px;
     box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
     text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
 }

</style>
</head>
<body>
<header class="a">
  <h1>빅데이터 분석 플랫폼</h1>
</header>
    <nav>
        <c:if test = "${ empty sessionScope.id}">
                <div class="welcome-message">로그인이 필요합니다.</div>
                <div>
                    <a href="login.do">로그인</a>|
                    <a href="join.do">회원가입</a>|
                    <a href="#">아이디찾기</a>|
                    <a href="#">비밀번호찾기</a>
                </div>
            </c:if>
            <c:if test="${not empty sessionScope.id }">
                <div class="welcome-message">${sessionScope.id}님 환영합니다.</div>
                <div>
                <%--<a href="#">내 정보</a>|--%>
                <a href="logout.do" id="logout-link">로그아웃</a>|
               <a href="withdraw.do?id=${sessionScope.id}" id="withdraw-link">회원탈퇴</a>
               </div>
                <div class="analyze-buttons">
                        <a href="analysis_page.do" class="analyze-button">분석하기</a>|
                        <a href="history.do" class="analyze-button">히스토리</a>|
                        <a href="add2.do" class="analyze-button">기업추가</a>
                    </div>
            </c:if>
    </nav>


    <main style="display: flex; justify-content: center;">


</main>
<footer>
    <p>&copy; &copy; 2024 빅 데이터 분석 플랫폼</p>
</footer>
<script>
    // 로그아웃 메시지 표시
    var logoutLink = document.getElementById('logout-link');
    logoutLink.addEventListener('click', function() {
        alert('로그아웃이 되었습니다.');
    });

    // 회원탈퇴 메시지 표시
    var withdrawLink = document.getElementById('withdraw-link');
    withdrawLink.addEventListener('click', function() {
        alert('회원탈퇴가 되었습니다.');
    });
</script>
</body>
</html>
