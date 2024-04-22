<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>기업 및 시각화</title>
</head>
<style>
  body {
    background-image: url("/img/data-analsis-tool-img-3-1030x644.png");
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0;
    text-align: center; /* 수정된 부분: body의 텍스트를 가운데 정렬 */
  }
  /* 추가된 스타일 */
  .container {
    margin: 0 auto;
    margin-top: 50px;
    max-width: 600px;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  select {
    width: 100%;
    padding: 10px;
    margin-bottom: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
  }

  button {
    display: block;
    width: 100%; /* 버튼의 너비를 50%로 설정합니다 */
    padding: 15px 30px;
    margin: 20px auto 0; /* 위쪽 여백을 20px로 설정하고 수평 중앙 정렬합니다 */
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

  .button-container {
    margin-top: 20px;
  }
  .header {
       text-align: center;
       padding: 20px 0;
       top: 0;

       position: fixed;
   }
  .home-button {
   background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    width: 100px;
}


  .home-button:hover {
      background-color: #0056b3; /* hover 시 색 변화 */
  }
  form {
      max-width: 600px;
      margin: 0 auto;
      padding: 20px;
      background-color: #fff;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
    </style>
<body>
<div class="header">
        <button class="home-button" onclick="location.href='home.do'">Home</button>
        <h2>분기분석</h2>
</div>

<form action="analysis_page2.do" method="post">
    <select name="stock_code1">
        <option value="">Select Company 1</option>
        <c:forEach items="${list}" var="availableData">
            <option value="${availableData.stock_code}">
                ${availableData.nation}, ${availableData.db_name}, ${availableData.sector}, ${availableData.name}
                ${stock_code1}
            </option>
        </c:forEach>
    </select>
    <select name="stock_code2">
            <option value="">Select Company 2</option>
            <c:forEach items="${list}" var="availableData">
                <option value="${availableData.stock_code}"">
                    ${availableData.nation}, ${availableData.db_name}, ${availableData.sector}, ${availableData.name}
                </option>
            </c:forEach>
        </select>
        <div class="#">
            <button onclick="window.location.href='chart.do'">적용</button>
          </div>
        </div>
        </form>

</body>
</html>