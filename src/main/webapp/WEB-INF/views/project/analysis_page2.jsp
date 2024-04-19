<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>기업 및 시각화</title>
</head>
<style>
.header {
        text-align: center;
        padding: 20px 0;
        top: 0;

        position: fixed;
    }
    body {
          background-image: url("/img/data-analsis-tool-img-3-1030x644.png");
          display: flex;
          justify-content: center;
          align-items: center;
          height: 100vh;
          margin: 0;
          text-align: center; /* 수정된 부분: body의 텍스트를 가운데 정렬 */
        }
        .home-button {
            background-color: #007bff;
             color: #fff;
             border: none;
             border-radius: 5px;
             padding: 10px 20px;
             cursor: pointer;
             transition: background-color 0.3s ease;
       }


           .home-button:hover {
               background-color: #0056b3; /* hover 시 색 변화 */
           }
           </style>
<body>


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