<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- history.html -->
<html>
<head>
    <meta charset="UTF-8">
    <title>History</title>
    <style>
         body {
         background-image: url("/img/data-analsis-tool-img-3-1030x644.png");
                 font-family: Arial, sans-serif;
                 background-color: #f4f4f4;
                 margin: 0;
                 padding: 0;
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
                         .header {
                             text-align: center;
                             padding: 20px 0;
                         }
                         .home-button {
                             background-color: #007bff;
                             color: #fff;
                             border: none;
                             border-radius: 5px;
                             padding: 10px 20px;
                             cursor: pointer;
                             margin: 30px auto 10px; /* 가운데 정렬 */
                             display: block; /* 블록 요소로 설정하여 가운데 정렬 */
                             transition: background-color 0.3s ease; /* hover 시 색 변화를 위한 transition 추가 */
                         }

                         .home-button:hover {
                             background-color: #0056b3; /* hover 시 색 변화 */
                         }
                         .container {
                             width: 80%;
                             margin: 0 auto;
                         }
                         table {
                             border-collapse: collapse;
                             width: 100%;
                             margin-top: 20px;
                             background-color: white;
                         }
                         th, td {
                             border: 1px solid #dddddd;
                             text-align: left;
                             padding: 8px;
                         }
                         th {
                             background-color: #f2f2f2;
                         }
                     </style>

</head>
<body>

        <div class="header">
        <button class="home-button" onclick="location.href='home.do'">Home</button>
        </div>
        <div class="container">
                <h2 style="text-align: center;">히스토리</h2>
         </div>

    <h3 style="text-align: center;">제외기간분석</h3>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>기업 1</th>
                        <th>기업 2</th>
                        <th>제외기업</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Report</th>
                        <th>Action</th>
                        <th>삭제</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterate over the history list -->
                    <c:forEach items="${serviceUsages}" var="serviceUsage">
                        <tr>
                            <form action="analysis_page3.do" method="post">
                                <input type="hidden" name="exclusionperiodHistory_seq" value="${serviceUsage.exclusionperiodHistory_seq}">
                                <input type="hidden" name="id" value="${serviceUsage.id}">
                                <input type="hidden" name="stock_code1" value="${serviceUsage.stock_code1}">
                                <input type="hidden" name="stock_code2" value="${serviceUsage.stock_code2}">
                                <input type="hidden" name="stock_code3" value="${serviceUsage.stock_code3}">
                                <input type="hidden" name="name1" value="${serviceUsage.name1}">
                                <input type="hidden" name="name2" value="${serviceUsage.name2}">
                                <input type="hidden" name="name3" value="${serviceUsage.name3}">
                                <input type="hidden" name="start_date" value="${serviceUsage.start_date}">
                                <input type="hidden" name="end_date" value="${serviceUsage.end_date}">
                                <input type="hidden" name="report" value="${serviceUsage.report}">
                                <td>${serviceUsage.id}</td>
                                <td>${serviceUsage.name1}</td>
                                <td>${serviceUsage.name2}</td>
                                <td>${serviceUsage.name3}</td>
                                <td>${serviceUsage.start_date}</td>
                                <td>${serviceUsage.end_date}</td>

                                <td>${serviceUsage.report}</td>
                                <td>
                                    <input type="submit" value="분석 페이지로">
                                </td>
                                </form>
                            <td>

                                <a href='exclusionperiodHistoryDel.do?exclusionperiodHistory_seq=${serviceUsage.exclusionperiodHistory_seq}'>삭제</a>
                            </td>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>
    <h3 style="text-align: center;">제외분기</h3>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>기업 1</th>
                        <th>기업 2</th>
                        <th>제외기업</th>
                        <th>Report</th>
                        <th>Action</th>
                        <th>삭제</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterate over the history list -->
                    <c:forEach items="${serviceUsages1}" var="serviceUsage1">
                        <tr>
                            <form action="analysis_page4.do" method="post">
                                <input type="hidden" name="excludedquarterHistory_seq" value="${serviceUsage1.excludedquarterHistory_seq}">
                                <input type="hidden" name="id" value="${serviceUsage1.id}">
                                <input type="hidden" name="stock_code1" value="${serviceUsage1.stock_code1}">
                                <input type="hidden" name="stock_code2" value="${serviceUsage1.stock_code2}">
                                <input type="hidden" name="stock_code3" value="${serviceUsage1.stock_code3}">
                                <input type="hidden" name="name1" value="${serviceUsage1.name1}">
                                <input type="hidden" name="name2" value="${serviceUsage1.name2}">
                                <input type="hidden" name="name3" value="${serviceUsage1.name3}">
                                <input type="hidden" name="start_date" value="${serviceUsage1.start_date}">
                                <input type="hidden" name="end_date" value="${serviceUsage1.end_date}">
                                <input type="hidden" name="report" value="${serviceUsage1.report}">
                                <input type="hidden" name="resultcount" value="${serviceUsage1.resultcount}">
                                <td>${serviceUsage1.id}</td>
                                <td>${serviceUsage1.name1}</td>
                                <td>${serviceUsage1.name2}</td>
                                <td>${serviceUsage1.name3}</td>
                                <td>연동된 결과값의 수 : ${serviceUsage1.resultcount}</td>


                                <td>
                                    <input type="submit" value="분석 페이지로">
                                </td>
                                </form>
                            <td>

                                <a href='historyDel.do?excludedquarterHistory_seq=${serviceUsage1.excludedquarterHistory_seq}'>삭제</a>
                            </td>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>
            <footer>
                <p> &copy; 2024 빅 데이터 분석 플랫폼 - 데이터로 더 나은 미래를 만듭니다.

                </p>
                </footer>

</body>
</html>
