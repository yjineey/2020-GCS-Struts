<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게 시 판</title>
	
<script type="text/javascript">
	function searchData() {
		var f= document.searchForm;
		f.action = "<%=cp%>/boardTest.do?method=list";
		f.submit();
	}
</script>

<style type="text/css">
table, th, td {
    border: 1px solid black;
}
table {
    border-collapse: collapse;
}
</style>

</head>
<body>
	<div id="bbsList">
		<div id="bbsList_title">
			<h3>게 시 판 목 록</h3>
		</div>
		<div id="bbsList_header">
			<div id="leftHeader">
				<form action="" name="searchForm" method="post">
					<select name="searchKey" class="selectField">
						<option value="subject">제목</option>
						<option value="name">작성자</option>
						<option value="content">내용</option>
					</select> 
					<input type="text" name="searchValue" class="textField" /> 
					<input type="button" value="검  색" class="btn2" onclick="searchData();" />
				</form>
			</div>
		</div>

		<div id="bbsList_list">
			<div id="lists">
				<br>
				<table cellpadding="0" cellspacing="0" width="60%" border="1">
					<tr>
						<td width="5%"></td>
						<td width="25%"></td>
						<td width="15%"></td>
						<td width="10%"></td>
						<td width="5%"></td>
					</tr>
					<tr bgcolor = "#CCCCCC" align="center">
						<td class="num">번호</td>
						<td class="subject">제목</td>
						<td class="name">작성자</td>
						<td class="created">작성일</td>
						<td class="hitCount">조회수</td>
					</tr>
					<c:choose>
						<c:when test="${fn:length(lists) > 0}">
							<c:forEach var="dto" items="${lists }">
								<tr align="center">
									<td class="num">${dto.num }</td>
									<td class="subject"><a
										href="${urlArticle }&num=${dto.num}">${dto.subject }</a></td>
									<td class="name">${dto.name }</td>
									<td class="created">${dto.created }</td>
									<td class="hitCount">${dto.hitCount }</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr align="center">
									<td colspan="5">등록된 게시물이 없습니다</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
			<div id="footer">
					<p><c:if test="${totalDataCount!=0 }">
							${pageIndexList }
					</c:if></p> 
				<div id="rightHeader">
					<input type="button" value="게시글 작성" class="btn2"
						onclick="javascript:location.href='<%=cp%>/boardTest.do?method=created';" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>