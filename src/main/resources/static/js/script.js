// script.js

// 각 행의 rowCountValue를 설정하는 함수
function setRowCountValue(rowCountValue, branchHistory_seq) {
    var element = document.querySelector('#rowCountValue' + branchHistory_seq);
    if (element) {
        element.innerText = rowCountValue;
    }
}

window.onload = function() {
    // 각 행의 rowCount 값들을 가져와서 설정
    var rowCountValues = getAllRowCountValues();
    rowCountValues.forEach(function(rowCountValue, index) {
        var branchHistory_seq = serviceUsages3[index].branchHistory_seq; // 수정된 부분
        setRowCountValue(rowCountValue, branchHistory_seq);
    });

    // 쿠키에서 rowCount 값을 가져와서 설정
    var rowCount = getCookie("rowCount");
    if (rowCount != null) {
        // 수정된 부분: getElementById 대신 querySelector 사용
        var element = document.querySelector('#rowCountValue' + rowCount);
        if (element) {
            element.innerText = rowCount;
        }
    }
};

function getCookie(name) {
    var cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        var cookies = document.cookie.split(';');
        for (var i = 0; i < cookies.length; i++) {
            var cookie = cookies[i].trim();
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}

// 각 행의 모든 rowCountValue를 가져오는 함수
function getAllRowCountValues() {
    var rowCountValues = [];
    var rowCountElements = document.querySelectorAll('span[id^="rowCountValue"]');
    rowCountElements.forEach(function(element) {
        rowCountValues.push(element.innerText);
    });
    return rowCountValues;
}
