<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>기업 및 시각화</title>
<style>
   /* CSS 스타일은 여기에 작성하세요 */
   /* 스타일 예시 */
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
      width: 100%;
      padding: 15px 30px;
      margin-top: 20px;
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

}


    .home-button:hover {
        background-color: #0056b3; /* hover 시 색 변화 */
    }
</style>
</head>
<body>
<div class="header">
        <button class="home-button" onclick="location.href='home.do'">Home</button>
        <h2>분석</h2>
</div>
<div class="container">
  <form action="analysis_page.do" method="post">
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
            <option value="">Select Company 3</option>
            <c:forEach items="${list}" var="availableData">
                <option value="${availableData.stock_code}"">
                    ${availableData.nation}, ${availableData.db_name}, ${availableData.sector}, ${availableData.name}
                </option>
            </c:forEach>
        </select>
    <select name="stock_code3">
                <option value="">Select Company 4</option>
                <c:forEach items="${list}" var="availableData">
                    <option value="${availableData.stock_code}"">
                        ${availableData.nation}, ${availableData.db_name}, ${availableData.sector}, ${availableData.name}
                    </option>
                </c:forEach>
            </select>
    <select name="stock_code4">
                <option value="">Select Company 2</option>
                <c:forEach items="${list}" var="availableData">
                    <option value="${availableData.stock_code}"">
                        ${availableData.nation}, ${availableData.db_name}, ${availableData.sector}, ${availableData.name}
                    </option>
                </c:forEach>
            </select>

  <section style="text-align: center; color: black;">
    <h2>날짜 선택</h2>
    <div>
      <label for="start_date" style="color: black;">시작 날짜:</label>
      <input type="date" id="start_date" name="start_date" min="2012-01-01" max="2022-01-01">
    </div>
    <div>
      <label for="end_date" style="color: black;">종료 날짜:</label>
      <input type="date" id="end_date" name="end_date" min="2012-01-01" max="2022-01-01">
    </div>
  </section>
  <div class="button-container">
    <button onclick="window.location.href='chart.do'">적용</button>
  </div>
</div>
</form>
<script>
function redirectToGraphPage(company) {
  // 세션 스코프에 기업 정보 저장
  sessionStorage.setItem('selectedCompany', company);
  // 그래프 페이지로 이동
  window.location.href = 'graph_page.html';
}

// 시작 날짜를 선택할 때마다 종료 날짜의 최소값을 업데이트하는 함수
document.getElementById("start_date").addEventListener("change", setEndDateMin);

function setEndDateMin() {
  let startDate = document.getElementById("start_date").value;
  let endDateInput = document.getElementById("end_date");

  // 시작 날짜로부터 두 달 뒤의 날짜를 계산
  let startDateObj = new Date(startDate);
  startDateObj.setMonth(startDateObj.getMonth() + 2);
  let endDateMin = startDateObj.toISOString().slice(0, 10); // YYYY-MM-DD 형식으로 변환

  // 종료 날짜의 최소값 설정
  endDateInput.min = endDateMin;
}
</script>

</body>
</html>
