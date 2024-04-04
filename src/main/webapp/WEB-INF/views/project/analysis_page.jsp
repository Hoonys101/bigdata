<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>기업 및 시각화</title>
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

    .button-container {
      display: flex;
      justify-content: space-between;
      align-items: center;
      flex-wrap: wrap; /* 버튼이 한 줄에 모두 들어가지 않을 경우 여러 줄로 나누어 표시 */
      gap: 20px; /* 버튼 간의 간격 설정 */
    }

    .button-container button {
      padding: 15px 30px; /* 수정된 부분: 버튼의 패딩을 크게 조정 */
      font-size: 16px; /* 수정된 부분: 버튼의 글꼴 크기를 조정 */
      background-color: #007bff;
      color: #fff;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }

    .button-container button:hover {
      background-color: #0056b3;
    }

    section {
      margin-top: 20px;
    }

    header {
      margin-bottom: 30px;
      font-size: 24px;
      font-weight: bold;
    }
</style>
</head>
<body>
<header style="text-align: center;">
  <a href="home.do" class="button">홈</a>
</header>
<div class="button-container">
  <button onclick="window.location.href='list1.do'">첫번째 기업 검색</button>
  <button onclick="window.location.href='list2.do'">두번째 기업 검색</button>
</div>

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
