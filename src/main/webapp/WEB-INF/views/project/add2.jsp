<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>데이터 선택</title>
<style>
body {
font-family: Arial, sans-serif;
background-color: #f4f4f4;
margin: 0;
padding: 0;
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
select, button {
display: block;
width: 100%;
padding: 10px;
margin-bottom: 20px;
border: 1px solid #ccc;
border-radius: 5px;
font-size: 16px;
}

button {
background-color: #4caf50;
color: white;
border: none;
cursor: pointer;
}
button:hover {
background-color: #45a049;
}
</style>
</head>
<body>
<h2>데이터 선택</h2>
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

<!-- 종목 코드 선택 창
<select name="stock_code" id="stock_code" onchange="loadSectors()">
    <option value="">종목 코드 선택</option>
</select>-->

<!-- 업종 선택 창 -->
<select name="sector" id="sector" onchange="loadCompanies()">
<option value="">업종 선택</option>
<!-- 이 부분은 JavaScript를 사용하여 동적으로 채워질 것입니다. -->
</select>

<!-- 회사명 선택 창 -->
<select name="name" id="name">
<option value="">회사명 선택</option>
<!-- 이 부분은 JavaScript를 사용하여 동적으로 채워질 것입니다. -->
</select>


<!-- 종목 코드 선택 창 -->
<select name="stock_code" >
    <option value="">종목 코드 선택</option>
</select>

<button type="submit">데이터 확인</button>
</form>

<!-- jQuery 라이브러리 추가 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
// 국가 선택 시 DB 목록 가져오기
function loadDBNames() {
$.ajax({
   url: "../project/url.do",
   method: "POST",
   data: { selectedNation:"KR", action:"getDb" },
   dataType: "json",
   success: function(data) {
       $("#db_name").empty().append("<option value=''>DB 선택</option>");
       $.each(data, function(index, item) {
           $("#db_name").append("<option value='" + item + "'>" + item + "</option>");
       });
   }
});
}
// db 선택 시 stock 가져오기
function loadStockCodes() {
    var selectedStock_code = $("#db_name").val();
    $.ajax({
        url: "../project/url.do",
        method: "POST",
        data: JSON.stringify({ action: "getStockCode", db_name: selectedStock_code }),
        dataType: "json",
        success: function(data) {
            $("#stock_code").empty().append("<option value=''>종목 코드 선택</option>");
            $.each(data, function(index, item) {
                $("#stock_code").append("<option value='" + item + "'>" + item + "</option>");
            });
        }
    });
}
// stock 선택 시 업종 가져오기
function loadSectors() {
var selectedSector = $("#stock_code").val();
$.ajax({
url: "../project/url.do",
method: "POST",
data: JSON.stringify({ action: "getSector", stock_code: selectedSector }),
dataType: "json",
success: function(data) {
    $("#sector").empty().append("<option value=''>업종 선택</option>");
    $.each(data, function(index, item) {
        $("#sector").append("<option value='" + item + "'>" + item + "</option>");
    });
}
});
// 업종 선택 시 회사명 가져오기
    function loadCompanies() {
        var selectedName = $("#sector").val();
        $.ajax({
            url: "../project/url.do",
            method: "POST",
            data:JSON.stringify({ action: "getName", sector: selectedName }),
            dataType: "json",
            success: function(data) {
                $("#name").empty().append("<option value=''>회사명 선택</option>");
                $.each(data, function(index, item) {
                    $("#name").append("<option value='" + item + "'>" + item + "</option>");
                });
            }
        });
        }
            </script>
        </body>
        </html>