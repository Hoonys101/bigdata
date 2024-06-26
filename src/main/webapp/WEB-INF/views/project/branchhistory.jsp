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
         <h3 style="text-align: center;">분기분석1</h3>

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
                    <c:forEach items="${serviceUsages2}" var="serviceUsage2">
                        <tr>
                            <form action="analysis_page3.do" method="post">
                                <input type="hidden" name="serviceusage_seq" value="${serviceUsage2.serviceusage_seq}">
                                <input type="hidden" name="id" value="${serviceUsage2.id}">
                                <input type="hidden" name="stock_code1" value="${serviceUsage2.stock_code1}">
                                <input type="hidden" name="stock_code2" value="${serviceUsage2.stock_code2}">
                                <input type="hidden" name="stock_code3" value="${serviceUsage2.stock_code3}">
                                <input type="hidden" name="name1" value="${serviceUsage2.name1}">
                                <input type="hidden" name="name2" value="${serviceUsage2.name2}">
                                <input type="hidden" name="name3" value="${serviceUsage2.name3}">
                                <input type="hidden" name="start_date" value="${serviceUsage2.start_date}">
                                <input type="hidden" name="end_date" value="${serviceUsage2.end_date}">
                                <input type="hidden" name="report" value="${serviceUsage2.report}">
                                <td>${serviceUsage2.id}</td>
                                <td>${serviceUsage2.name1}</td>
                                <td>${serviceUsage2.name2}</td>
                                <td>${serviceUsage2.name3}</td>
                                <td>${serviceUsage2.start_date}</td>
                                <td>${serviceUsage2.end_date}</td>
                                <td>${serviceUsage2.report}</td>
                                <td>
                                    <input type="submit" value="분석 페이지로">
                                </td>
                                </form>
                            <td>

                                <a href='historyDel.do?serviceusage_seq=${serviceUsage.serviceusage_seq}'>삭제</a>
                            </td>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>
    <h3 style="text-align: center;">분기분석2</h3>

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
                    <c:forEach items="${serviceUsages3}" var="serviceUsage3">
                        <tr>
                            <form action="analysis_page3.do" method="post">
                                <input type="hidden" name="serviceusage_seq" value="${serviceUsage3.serviceusage_seq}">
                                <input type="hidden" name="id" value="${serviceUsage3.id}">
                                <input type="hidden" name="stock_code1" value="${serviceUsage3.stock_code1}">
                                <input type="hidden" name="stock_code2" value="${serviceUsage3.stock_code2}">
                                <input type="hidden" name="stock_code3" value="${serviceUsage3.stock_code3}">
                                <input type="hidden" name="name1" value="${serviceUsage3.name1}">
                                <input type="hidden" name="name2" value="${serviceUsage3.name2}">
                                <input type="hidden" name="name3" value="${serviceUsage3.name3}">
                                <input type="hidden" name="start_date" value="${serviceUsage3.start_date}">
                                <input type="hidden" name="end_date" value="${serviceUsage3.end_date}">
                                <input type="hidden" name="report" value="${serviceUsage3.report}">

                                <td>${serviceUsage3.id}</td>
                                <td>${serviceUsage3.name1}</td>
                                <td>${serviceUsage3.name2}</td>
                                <td>${serviceUsage3.name3}</td>
                                <td>${serviceUsage3.start_date}</td>
                                <td>${serviceUsage3.end_date}</td>
                                <td>${serviceUsage3.report}</td>
                                <td>
                                    <input type="submit" value="분석 페이지로">
                                </td>
                                </form>
                            <td>

                                <a href='historyDel.do?serviceusage_seq=${serviceUsage.serviceusage_seq}'>삭제</a>
                            </td>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>
            <footer>
                <p>> &copy; 2024 빅 데이터 분석 플랫폼 - 데이터로 더 나은 미래를 만듭니다.

                </p>
                </footer>
</body>
</html>
