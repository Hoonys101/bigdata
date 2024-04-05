<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>


<meta charset='utf-8'>
<style>
table, th, td {
border: 1px solid black;
border-collapse: collapse;
}
th, td {
padding: 5px;
}
a { text-decoration:none }
</style>
<center>
<hr width='600' size='2' noshade>
<h2>Simple Board with Servlet</h2>
&nbsp;&nbsp;&nbsp;
<a href='insert.do'>글쓰기</a>
<hr width='600' size='2' noshade>
<table border='1' width='600' align='center' cellpadding='3'>
<tr>
<td width='100' align='center'>글번호</td>
<td>${board.seq}</td>
</tr>
<tr>
<td align='center'>글쓴이</td>
<td>${board.writer}</td>
</tr>
<tr>
<td align='center'>이메일</td>
<td>${board.email}</td>
</tr>
<tr>
<td align='center'>글제목</td>
<td>${board.subject}</td>
</tr>
<tr>
<td align='center'>글내용</td>
<td>${board.content}</td>
</tr>
</table>
<hr width='600' size='2' noshade>
<b>
<a  href='update.do?seq=${board.seq}'>수정</a>
|
<a href='del.do?seq=${board.seq}'>삭제</a>
|
<a href='list.do'>목록</a>
</b>
<hr width='600' size='2' noshade>
</center>
