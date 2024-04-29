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
  .center {
      position: fixed; /* 고정 위치로 설정 */
      top: calc(15% - 10px); /* 상단 여백을 화면의 1/3 지점으로 설정 (50px은 center 요소의 높이에 따라 조절) */
      left: 50%; /* 좌우 중앙 정렬 */
      transform: translateX(-50%); /* 좌우 중앙 정렬을 위한 보정 */
      text-align: center; /* 텍스트를 가운데 정렬 */
  }
h2 {
    text-align: center;
    margin-top: 50px;
}
form {
    width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
.form-wrapper {
    max-width: 600px;
    width: 90%;
    margin: 0 auto;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow-y: auto; /* 세로 스크롤이 내용이 넘칠 때만 표시 */
    max-height: 400px; /* 최대 높이 설정 */
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
               width: 200px;
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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
      function validateAndSubmit() {
        var form = document.getElementById('myForm');
        var selectedOption = form.querySelector('input[name="option"]:checked');
        if (selectedOption) {
          var db_name = selectedOption.getAttribute('data-custom-db_name');
          var stock_code = selectedOption.getAttribute(
            'data-custom-stock_code'
          );

          if (db_name === '' || stock_code === '') {
            alert(
              '기업명 또는 stock_code는 직접 입력할 수 없습니다. 라디오 버튼 선택을 통해 입력해주세요.'
            );
            return false;
          } else {
            return true;
          }
        }
      }

      // hidden input 값을 업데이트하는 함수
      function updateHiddenInputs(selectedOption) {
        var db_name = selectedOption.getAttribute('data-custom-db_name');
        var stock_code = selectedOption.getAttribute('data-custom-stock_code');
        document.getElementById('db_name_hidden').value = db_name;
        document.getElementById('stock_code_hidden').value = stock_code;
      }

      $(function () {
        $('#stock_code_or_name').on('keyup', function () {
          $.ajax({
            url: '../project/add_by_name_or_code.do',
            type: 'GET',
            data: { stock_code_or_name: $('#stock_code_or_name').val() },
            success: function (data) {
              var html = '';
              if (!data||data.length == 0) {
                html += '<tr>';
                html +=
                  "<td colspan='4' align='center'>그런 이름또는 코드를 가진 기업은 없음</td>";
                html += '</tr>';
              } else {
                html +=
                                                  "<form action= 'add2.do' id='myForm' method='post' onsubmit='return validateAndSubmit();'>";
                html +="<div class='form-wrapper'>"
                html += "<table border='1' width='100%'>";
                html += '<tr>';
                html += '<th>stock_code</th>';
                html += '<th>기업명</th>';
                html += '<th>db_name</th>';
                html += '<th>select one</th></tr>';

                $.each(data, function (index, AvailableData) {
                  html += '<tr>';
                  html += "<td align='center'>" + AvailableData.stock_code + '</td>';
                  html += "<td align='center'>" + AvailableData.name + '</td>';
                  html += "<td align='center'>" + AvailableData.db_name + '</td>';
                  html += "<td align='center'>";
                  html +=
                    "<input type='radio' name='option' value='" +
                    index +
                    "' data-custom-stock_code='" +
                    AvailableData.stock_code +
                    "' data-custom-db_name='" +
                    AvailableData.db_name +
                    "' onchange='updateHiddenInputs(this)'></td>";
                  html += '</tr>';
                });



              }
              html += '</table>';
              html += '</div>';
              html += '<div class="button-container">';
              html +=
                '<input type="hidden" name="db_name" id="db_name_hidden">';
              html +=
                '<input type="hidden" name="stock_code" id="stock_code_hidden">';
              html +=
                '  <input type="submit" class="button-apply" value="기업추가" />';
              html += '</div>';
              html += '</form>';

              $('#container_by_stock_code_or_name').html(html);
            },
            error: function (data) {
              $('#container').html('존재하지않는 stock_code');
            },
          });
        });
      });
    </script>
  </head>
  <body>
    <div class="header">
      <button class="home-button" onclick="location.href='home.do'">
        Home
      </button>
      <h2>기업추가</h2>
    </div>

    <center>
    <div class="center">
      <h2>기업명 또는 stock_code 검색</h2>

      stock_code_or_name:
      <input type="text" name="stock_code_or_name" id="stock_code_or_name" />
      <br />

      <div id="container_by_stock_code_or_name"></div>
      </div>
    </center>
  </body>
</html>
