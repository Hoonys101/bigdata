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
    <form id="dataForm" action="list1.do" method="post">
        <select name="stock_code" id="stock_code">
            <option value="">종목 코드 선택</option>
        </select>
        <select name="nation" id="nation">
            <option value="">국가 선택</option>
        </select>
        <select name="db_name" id="db_name">
            <option value="">DB 선택</option>
        </select>
        <select name="sector" id="sector">
            <option value="">업종 선택</option>
        </select>
        <select name="dataName" id="dataName">
            <option value="">회사명 선택</option>
        </select>
        <button type="submit">데이터 확인</button>
    </form>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            // 종목 코드 선택 시 국가 리스트 업데이트
            $("#stock_code").change(function() {
                var selectedStock = $(this).val();
                // Ajax를 통해 선택한 종목 코드에 해당하는 국가 리스트 가져오기
                $.ajax({
                    url: "getNationList.jsp",
                    method: "POST",
                    data: { stockCode: selectedStock },
                    dataType: "json",
                    success: function(data) {
                        $("#nation").empty().append("<option value=''>국가 선택</option>");
                        $.each(data, function(index, item) {
                            $("#nation").append("<option value='" + item + "'>" + item + "</option>");
                        });
                    }
                });
            });

            // 국가 선택 시 DB 리스트 업데이트
            $("#nation").change(function() {
                var selectedNation = $(this).val();
                // Ajax를 통해 선택한 국가에 해당하는 DB 리스트 가져오기
                $.ajax({
                    url: "getDBList.jsp",
                    method: "POST",
                    data: { nation: selectedNation },
                    dataType: "json",
                    success: function(data) {
                        $("#db_name").empty().append("<option value=''>DB 선택</option>");
                        $.each(data, function(index, item) {
                            $("#db_name").append("<option value='" + item + "'>" + item + "</option>");
                        });
                    }
                });
            });

            // DB 선택 시 업종 리스트 업데이트
            $("#db_name").change(function() {
                var selectedDB = $(this).val();
                // Ajax를 통해 선택한 DB에 해당하는 업종 리스트 가져오기
                $.ajax({
                    url: "getSectorList.jsp",
                    method: "POST",
                    data: { dbName: selectedDB },
                    dataType: "json",
                    success: function(data) {
                        $("#sector").empty().append("<option value=''>업종 선택</option>");
                        $.each(data, function(index, item) {
                            $("#sector").append("<option value='" + item + "'>" + item + "</option>");
                        });
                    }
                });
            });

            // 업종 선택 시 회사명 리스트 업데이트
            $("#sector").change(function() {
                var selectedSector = $(this).val();
                // Ajax를 통해 선택한 업종에 해당하는 회사명 리스트 가져오기
                $.ajax({
                    url: "getCompanyNameList.jsp",
                    method: "POST",
                    data: { sector: selectedSector },
                    dataType: "json",
                    success: function(data) {
                        $("#dataName").empty().append("<option value=''>회사명 선택</option>");
                        $.each(data, function(index, item) {
                            $("#dataName").append("<option value='" + item + "'>" + item + "</option>");
                        });
                    }
                });
            });
        });
    </script>
</body>
</html>
