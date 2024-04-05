<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<meta charset='utf-8'>
<style>
	table,th,td{border:1px solid black;border-collapse:collapse}th,td{padding:5px}a{text-decoration:none}
</style>
<center>
<hr width='600' size='2' noshade>
<h2>Simple Board with Spring</h2>
&nbsp;&nbsp;&nbsp;<a href='insert.do'>글쓰기</a>&nbsp;&nbsp;&nbsp;<a href='../'>인덱스</a>
<hr width='600' size='2' noshade>
</center>
<table border='1' width='600' align='center' cellpadding='2'>
<tr>
	<th align='center' width='10%'>
		글번호
	</th>
	<th align='center' width='15%'>
		작성자
	</th>
	<th align='center' width='30%'>
		이메일
	</th>
	<th align='center' width='30%'>
		글제목
	</th>
	<th align='center' width='15%'>
		날짜
	</th>
</tr>
                <c:forEach items="${list}"  var="board">
				<tr>
					<td align='center'>${board.seq}</td>
					<td align='center'>${board.writer}</td>
					<td align='center'>${board.email}</td>
					<td align='center'>
						<a href='content.do?seq=${board.seq}'>${board.subject}</a>
					</td>
					<td align='center'>${board.rdate}</td>
				</tr>
        </c:forEach>
       <c:if test="${empty list}">
		<tr>
			<td align='center' colspan='5'>표시할 데이터가 없습니다.</td>
		</tr>
         </c:if>
</table>
<hr width='600' size='2' noshade>


