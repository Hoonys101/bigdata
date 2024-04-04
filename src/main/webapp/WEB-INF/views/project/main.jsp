<div class="background text-center text-white py-5">

<main style="display: flex; justify-content: center;">
<div class="c">
  <section style="text-align: center;">
    <h2>관련기업1</h2>
    <select id="mySelect" name="mySelect">
  <option value="option1">옵션 1</option>
  <option value="option2">옵션 2</option>
  <option value="option3">옵션 3</option>
</select>
  </section>

  <section style="text-align: center;">
    <h2>관련기업2</h2>
    <select id="mySelect" name="mySelect">
  <option value="option1">옵션 1</option>
  <option value="option2">옵션 2</option>
  <option value="option3">옵션 3</option>
</select>
  </section>

  <section style="text-align: center;">
    <h2>시작날짜</h2>
    <label for="start_date">시작 날짜:</label>
	<input type="date" id="start_date" name="start_date" min="2012-01-01" max="2022-01-01">
  </section>

  <section style="text-align: center;">
    <h2>종료날짜</h2>
    <label for="end_date">종료 날짜:</label>
	<input type="date" id="end_date" name="end_date" min="2012-01-01" max="2022-01-01">
  </section>
  <script>
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

  <aside>
    <h2>적용</h2>

  </aside>
  </div>

</main>
<div class="d">
</div>
<form id="stockForm">
  <label for="stockSymbol">주식 심볼:</label>
  <input type="text" id="stockSymbol" name="stockSymbol" required>
  <button type="submit">가져오기</button>
</form>

<div id="stockData"></div>

<script>
document.getElementById("stockForm").addEventListener("submit", function(event) {
  event.preventDefault(); // 폼 제출 기본 동작 방지
  var stockSymbol = document.getElementById("stockSymbol").value.toUpperCase(); // 주식 심볼 대문자 변환
  fetchStockData(stockSymbol);
});

function fetchStockData(symbol) {
  var apiKey = "YOUR_API_KEY"; // Alpha Vantage API 키
  var apiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;

  // API에 HTTP 요청 보내기
  fetch(apiUrl)
    .then(response => response.json())
    .then(data => {
      console.log(data); // 데이터 콘솔에 출력 (개발자 도구에서 확인 가능)
      displayStockData(data);
    })
    .catch(error => {
      console.error('Error fetching stock data:', error);
      document.getElementById("stockData").innerText = "주식 데이터를 가져오는 중 오류가 발생했습니다.";
    });
}

function displayStockData(data) {
  var stockInfo = data['Global Quote'];
  var symbol = stockInfo['01. symbol'];
  var price = stockInfo['05. price'];
  var change = stockInfo['09. change'];

  var stockDataDiv = document.getElementById("stockData");
  stockDataDiv.innerHTML = `
    <h2>${symbol} 주식 정보</h2>
    <p>현재 가격: $${price}</p>
    <p>가격 변동: $${change}</p>
  `;
}
</script>
<canvas id="stockChart" width="800" height="400"></canvas>

<script>
    // Alpha Vantage API 키
    const apiKey = 'YOUR_ALPHA_VANTAGE_API_KEY';

    // 주식 데이터를 가져올 함수
    async function fetchStockData(symbol) {
        const response = await fetch(`https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=${symbol}&apikey=${apiKey}`);
        const data = await response.json();
        return data['Time Series (Daily)'];
    }

    // 그래프를 그릴 함수
    async function drawGraph(symbol) {
        const stockData = await fetchStockData(symbol);

        const dates = Object.keys(stockData).slice(0, 10); // 최근 10일간의 데이터만 사용
        const closingPrices = dates.map(date => parseFloat(stockData[date]['4. close']));

        const ctx = document.getElementById('stockChart').getContext('2d');
        const myChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: dates,
                datasets: [{
                    label: `${symbol} Stock Price`,
                    data: closingPrices,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: false
                        }
                    }]
                }
            }
        });
    }

    // 그래프 그리기
    const symbol = 'AAPL'; // 예시로 애플 주식 사용
    drawGraph(symbol);
</script>