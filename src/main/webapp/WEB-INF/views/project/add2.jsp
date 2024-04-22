<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>데이터 선택</title>
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
h2 {
    text-align: center;
    margin-top: 50px;
}
form {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #fff;
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
} border-radius: 5px;
.button:hover {
  background-color: #0056b3; /* hover 시 색 변화 */
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
         transition: background-color 0.3s ease; /* hover 효과를 위한 transition */
     }

     .home-button:hover {
         background-color: #0056b3; /* hover 시 색 변화 */
     }

</style>
<!-- jQuery 라이브러리 추가 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
// 국가 선택 시 DB 목록 가져오기
function loadDBNames() {
    var selectedNation=$("#nation option:selected").val();
    $.ajax({
       url: "../project/url.do",
       method: "POST",
       data: { action:"getDb", selectedNation:selectedNation, selectedDb:"",selectedSection:"",selectedName:"" },
       success: function(data) {
           $("#db_name").empty().append("<option value=''>DB 선택</option>");
           $.each(data, function(index, item) {
               $("#db_name").append("<option value='" + item + "'>" + item + "</option>");
           });
           $("#sector").empty().append("<option value=''>업종 선택</option>");
           $("#name").empty().append("<option value=''>회사명 선택</option>");
           $("#stock_code").empty().append("<option value=''>종목 코드 선택</option>");
       }
   });
}
// db 선택 시 sector 가져오기
function loadSectors() {
    $.ajax({
        url: "../project/url.do",
        method: "POST",
        data: { action: "getSector", selectedNation:$("#nation").val(), selectedDb:$("#db_name").val(),selectedSection:"",selectedName:""},
        success: function(data) {
            $("#sector").empty().append("<option value=''>업종 선택</option>");
            $.each(data, function(index, item) {
                $("#sector").append("<option value='" + item + "'>" + item + "</option>");
            });
            $("#name").empty().append("<option value=''>회사명 선택</option>");
            $("#stock_code").empty().append("<option value=''>종목 코드 선택</option>");
        }
    });
}
// sector 선택 시 이름 가져오기
function loadCompanies() {
    $.ajax({
        url: "../project/url.do",
        method: "POST",
        data: { action: "getName", selectedNation:$("#nation").val(), selectedDb:$("#db_name").val(),selectedSection:$("#sector").val(),selectedName:""},
        dataType: "json",
        success: function(data) {
            $("#name").empty().append("<option value=''>기업명 선택</option>");
            $.each(data, function(index, item) {
                $("#name").append("<option value='" + item + "'>" + item + "</option>");
            });
            $("#stock_code").empty().append("<option value=''>종목 코드 선택</option>");
        }
    });
}
// 회사명 선택 시 stockCode명 가져오기

function loadStockCodes() {
    $.ajax({
        url: "../project/url.do",
        method: "POST",
        data:{ action: "getStockCode", selectedNation:$("#nation").val(), selectedDb:$("#db_name").val(),selectedSection:$("#sector").val(),selectedName:$("#name").val() },
//            dataType: "json",
        success: function(data) {
            $("#stock_code").empty();
            $.each(data, function(index, item) {
                $("#stock_code").append("<option value='" + item + "'>" + item + "</option>");
            });
        }
    });
}
</script>
</head>
<body>
<div class="header">
        <button class="home-button" onclick="location.href='home.do'">Home</button>
        <h2>기업추가</h2>
</div>

<form action="add2.do" method="post">
<!-- 국가 선택 창 -->
<select name="nation" id="nation" onchange="loadDBNames()">
<option value="">국가 선택</option>
<c:forEach items="${nation}" var="nation" >
<option value="${nation}">${nation}</option>
</c:forEach>
</select>

<!-- DB 선택 창 -->
<select name="db_name" id="db_name" onchange="loadSectors()">
<option value="">DB 선택</option>
</select>


<!-- 업종 선택 창 -->
<select name="sector" id="sector" onchange="loadCompanies()">
<option value="">업종 선택</option>
<!-- 이 부분은 JavaScript를 사용하여 동적으로 채워질 것입니다. -->
</select>

<!-- 회사명 선택 창 -->
<select name="name" id="name" onchange="loadStockCodes()">
<option value="">회사명 선택</option>
<!-- 이 부분은 JavaScript를 사용하여 동적으로 채워질 것입니다. -->
</select>

<!-- 종목 코드 선택 창 -->
<select name="stock_code" id="stock_code">
    <option value="">종목 코드 선택</option>
</select>

<button type="submit">데이터 확인</button>
</form>
</body>
</html>
