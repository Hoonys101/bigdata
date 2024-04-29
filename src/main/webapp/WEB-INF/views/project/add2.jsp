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
     .button-apply {
               display: block;
               width: 100%;
               padding: 15px 30px;
               margin: 20px auto 0;
               background-color: #007bff;
               color: #fff;
               border: none;
               border-radius: 5px;
               cursor: pointer;
           }

           .button-apply:hover {
               background-color: #0056b3;
           }

         .button-container {
           margin-top: 20px;
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
function validateAndSubmit() {
                var nation = document.getElementsByName("nation")[0].value;
                var db_name = document.getElementsByName("db_name")[0].value;
                var sector = document.getElementsByName("sector")[0].value;
                var name = document.getElementsByName("name")[0].value;
                var stock_code = document.getElementsByName("stock_code")[0].value;

                // 기업 또는 날짜 중 하나라도 입력되지 않았을 경우 경고 메시지를 표시합니다.
                if (nation === "" || db_name === "" ||  sector === "" || name === "" || stock_code === "") {
                    alert("국가, DB, 업종, 기업명, 종목코드를 전부 입력해주세요.");
                    return false; // 폼을 제출하지 않도록 false를 반환합니다.
                } else {
                    // 폼을 제출합니다.
                    return true;
                }
            }
</script>
</head>
<body>
<div class="header">
        <button class="home-button" onclick="location.href='home.do'">Home</button>
        <h2>기업추가</h2>
</div>

<form action="add2.do" method="post" onsubmit="return validateAndSubmit();">
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


<div class="button-container">
              <input type="submit" class="button-apply" value="기업추가">
          </div>
</form>
<footer>
    <p>> &copy; 2024 빅 데이터 분석 플랫폼 - 데이터로 더 나은 미래를 만듭니다.

    </p>
    </footer>
</body>
</html>
