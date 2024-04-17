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
                <h2 style="text-align: center;">History</h2>
         </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Stock Code 1</th>
                    <th>Stock Code 2</th>
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
                        <form action="analysis_page.do" method="post">
                            <input type="hidden" name="serviceusage_seq" value="${serviceUsage.serviceusage_seq}">
                            <input type="hidden" name="id" value="${serviceUsage.id}">
                            <input type="hidden" name="stock_code1" value="${serviceUsage.stock_code1}">
                            <input type="hidden" name="stock_code2" value="${serviceUsage.stock_code2}">
                            <input type="hidden" name="start_date" value="${serviceUsage.start_date}">
                            <input type="hidden" name="end_date" value="${serviceUsage.end_date}">
                            <input type="hidden" name="report" value="${serviceUsage.report}">
                            <td>${serviceUsage.id}</td>
                            <td>${serviceUsage.stock_code1}</td>
                            <td>${serviceUsage.stock_code2}</td>
                            <td>${serviceUsage.start_date}</td>
                            <td>${serviceUsage.end_date}</td>
                            <td>${serviceUsage.report}</td>
                            <td>
                                <input type="submit" value="분석 페이지로">
                            </td>
                            </form>
                        <td>
                            <%--<form action="history.do" method="post">
                                <input type="hidden" name="serviceusage_seq" value="${serviceUsage.serviceusage_seq}">
                                <input type="submit" value="삭제">
                            </form>--%>
                            <a href='historyDel.do?serviceusage_seq=${serviceUsage.serviceusage_seq}'>삭제</a>
                        </td>
                    </tr>

                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
