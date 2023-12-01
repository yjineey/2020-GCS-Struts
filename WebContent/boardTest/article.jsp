<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//tdD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.tdd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게 시 판</title>
<style type="text/css">
table, tr {
	border: 1px solid black;
}

table {
	border-collapse: collapse;
}
</style>
</head>
<body>
	<div id="bbs">
		<div id="bbs_title">
			<H3>게 시 글 보 기</H3>
		</div>
		<div id="bbsArticle">
		  	<table cellpadding="0" cellspacing="0" width="60%">
				<tr>
					<td width="10%"></td>
					<td width="20%"></td>
					<td width="10%"></td>
					<td width="20%"></td>
				</tr>
				<tr>
					<td width="10%"></td>
					<td colspan="3" width="20%"></td>
				</tr>

				<tr align="center">
					<td bgcolor="#CCCCCC">제목</td>
					<td>${dto.subject }</td>
					<td bgcolor="#CCCCCC">작성자</td>
					<td>${dto.name }</td>
				</tr>
				<tr align="center">
					<td bgcolor="#CCCCCC">등록일</td>
					<td>${dto.created }</td>
					<td bgcolor="#CCCCCC">조회수</td>
					<td>${dto.hitCount }</td>
				</tr >
					<tr height="200">
					<td align="center" bgcolor="#CCCCCC">내 용</td>
					<td  colspan="3">&nbsp;&nbsp;${dto.content }</td>
				</tr>
								<tr>
					<td align="center" bgcolor="#CCCCCC">이전글</td>
					<td colspan="3" >&nbsp;&nbsp;<a href="${preUrl }">${preSubject }</a></td>
				</tr>
				<tr>
					<td align="center" bgcolor="#CCCCCC">다음글</td>
					<td colspan="3">&nbsp;&nbsp;<a href="${nextUrl }">${nextSubject }</a></td>
				</tr>
			</table>
		</div><br>
		<div id="bbsArticle_footer">
			<div id="leftFooter">
				<input type="button" value="수정" class="btn2"
					onclick="javascript:location.href='<%=cp%>/boardTest.do?method=created&mode=update&${paramArticle }';" />
				<input type="button" value="삭제" class="btn2"
					onclick="javascript:location.href='<%=cp%>/boardTest.do?method=deleted&${paramArticle }';" />
				<input type="button" value="리스트" class="btn2"
					onclick="javascript:location.href='${urlList}';" />
			</div>
		</div>
	</div>
</body>
</html>

