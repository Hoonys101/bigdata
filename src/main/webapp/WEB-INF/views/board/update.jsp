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
<a href='list.do'>글목록</a>
<hr width='600' size='2' noshade>
<form name='f' method='post' action='update.do?seq=${board.seq}'>
<input type='hidden' name='seq' value='${board.seq}'>
<input type='hidden' name='writer' value='${board.writer}'>
<table border='1' width='600' align='center' cellpadding='3' cellspacing='1'><tr>
<td width='30%' align='center'>글쓴이</td>
<td align='center'><input type='text' name='aa' size='60' value='${board.writer}' disabled></td>
</tr>
<tr>
<td width='30%' align='center'>이메일</td>
<td align='center'><input type='text' name='email' size='60' value='${board.email}'></td>
</tr>
<tr>
<td width='30%' align='center'>글제목</td>
<td align='center'><input type='text' name='subject' size='60' value='${board.subject}'></td>
</tr>
<tr>
<td width='30%' align='center'>글내용</td>
<td align='center'><textarea name='content' rows='5' cols='53'>${board.content}</textarea></td>
</tr>
<tr>
<td colspan='2' align='center'>
<input type='submit' value='수정'>
</td>
</tr>
</table>
</form>
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
