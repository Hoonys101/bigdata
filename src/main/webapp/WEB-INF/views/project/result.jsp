<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- history.html -->
<html>
<head>
    <meta charset="UTF-8">
    <title>chart</title>
    <style>

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
                 <c:forEach items="${result}" var="result">
                                  ${result}
                                  </c:forEach>
                <!-- Iterate over the history list
                <c:forEach items="${serviceUsages}" var="serviceUsage">
                    <tr>
                        <form action="analysis_page2.do" method="post">
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

                            <a href='analysis_page2.do?serviceusage_seq=${serviceUsage.serviceusage_seq}'>삭제</a>
                        </td>

                    </tr>
                </c:forEach>-->
            </tbody>
        </table>
    </div>
</body>
</html>
