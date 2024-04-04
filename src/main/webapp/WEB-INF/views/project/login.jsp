<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="/css/style_2.css">
</head>

<body>
    <section class="container">
        <header>
            <h1>로그인 또는 회원가입</h1>
        </header>
        <div class="login">
            <div>
                <h2 ></h2>
                <form action="login.do" method="post">
                    <label for="id"></label>
                    <input type="text" name="id" id="id" placeholder="아이디" required><br>
                    <label for="pwd"></label>
                    <input type="password" name="pwd" id="pwd" placeholder="비밀번호" required>
                    <div class="check_wrap">
                        <input type="checkbox" id="check"><label for="check"></label>
                        <span class="check_txt">로그인 상태 유지</span>
                    </div>
            </div>
            <ul>
                <li><button>로그인</button></li>
                <ul class="login_txt">
                    <li><a href="#">회원가입</a></li>
                    <li><a href="#">아이디/비밀번호 찾기</a></li>
                </ul>
                </form>
                <%-- 오류 메시지가 있다면 출력 --%>
                    <c:if test="${not empty error}">
                        <p style="color: red;">${error}</p>
                    </c:if>
                <span class="or">또는</span>
                <li><a class="btn btn1" href="https://www.google.co.kr/">구글 계정으로 로그인</a></li>
                <li><a class="btn btn2" href="https://ko-kr.facebook.com/">페이스북 계정으로 로그인</a></li>
                <li><a class="btn btn3" href="https://www.naver.com/">네이버 계정으로 로그인</a></li>
                <li><a class="btn btn4" href="https://www.kakaocorp.com/page/">카카오톡 계정으로 로그인</a></li>
            </ul>
        </div>
        <a href="#" class="close">닫기</a>
    </section>
</body>

</html>