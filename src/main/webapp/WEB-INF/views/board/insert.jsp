<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>


<html>
  <head>
    <title>간단한 게시판</title>
	<meta charset='utf-8'>
	<script language="javascript">
	   function check()
	   {
	       for(var i=0; i<document.input.elements.length; i++)
		   {
		      if(document.input.elements[i].value == "")
			  {
			     alert("모든 값을 입력 하셔야 합니다. ");
				 return false;
			  }
		   }
		   document.input.submit();
       }
	</script>
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
  </head>
  <body onload="input.name.focus()">
    <center>
	   <hr width="600" size="2" noshade>
	      <h2>Simple Board with Servlet</h2>
		  <a href='list.do'>글목록</a>
	   <hr width="600" size="2" noshade>
	</center>
	<form name="input" method="post" action="insert.do">
	   <table border="1" width="600" align="center"  cellpadding="3" cellspacing="1">
	      <tr>
		     <td width="30%" align="center">글쓴이</td>
			 <td align="center"><input type="text" name="writer" size="60"></td>
		  </tr>
		  <tr>
		     <td align="center">이메일</td>
			 <td align="center"><input type="text" name="email" size="60"></td>
		  </tr>
          <tr>
		     <td align="center">글제목</td>
			 <td align="center"><input type="text" name="subject" size="60"></td>
		  </tr>
		  <tr>
		     <td align="center">글내용</td>
			 <td align="center"><textarea name="content" rows="5" cols="53"></textarea></td>
		  </tr>
		  <tr>
		     <td colspan="2" align="center">
			    <input type="button" value="전송" onclick="check()">
				<input type="reset" value="다시입력">
			 </td>
		  </tr>
	   </table>
	   <br>
	   <hr width="600" size="2" noshade>
	</form>
  </body>
</html>