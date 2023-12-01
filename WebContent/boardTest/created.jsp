<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");//한글깨짐 방지
%>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//ttd HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.ttd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게 시 판</title>
<script type="text/javascript" src="<%=cp%>/boartdest/js/util.js"></script>

<script type="text/javascript">
	function sendIt(){
		var f = document.myForm;
		
		str = f.subject.value;
		str = str.trim();
		if(!str){
			alert("\n제목을 입력하세요");
			f.subject.focus();return;
		}
		f.subject.value = str;
		
		str = f.name.value;
		str = str.trim();
		if(!str){
			alert("\n이름을 입력하세요");
			f.name.focus();return;
		}
		f.name.value = str;
		
	<%--	if(f.email.value){
			if(!isValidEmail(f.email.value)){
				alert("\n정상적인 E-mail을 입력하세요");
				f.email.focus();
				return;
			}
		}--%>
		
		str = f.content.value;
		str = str.trim();
		if(!str){
			alert("\n내용을 입력하세요");
			f.content.focus();return;
		}
		f.content.value = str;

		f.action = "<%=cp%>/boardTest.do";
		f.submit();
	}
	<%--function isValidEmail(email) {
		if.....
		return true;
		return false;
	}--%>
</script>


	

<!-- 		str = f.pwd.value;
		str = str.trim();
		if(!str){
			alert("\n패스워드를 입력하세요");
			f.pwd.focus();return;
		}
		f.pwd.value = str;
 -->
</head>
<body>
	<div id="bbs">
		<div id="bbs_title">
			<H3>게 시 글 작 성</H3>
		</div>
		<form action="" name="myForm" method="post">
			<div>
				<!-- update일때 필요한 값 -->
				<input type="hidden" name="method" value="created_ok"> 
				<input type="hidden" name="num" value="${dto.num }" /> <input
					type="hidden" name="pageNum" value="${pageNum }" /> <input
					type="hidden" name="searchKey" value="${searchKey }"> <input
					type="hidden" name="searchValue" value="${searchValue }">
				<!-- 이 창이 create창인지 update창인지 구분하기 위해 사용 -->
				<input type="hidden" name="mode" value="${mode }" />

				<!-- 글작성 -->
				<c:if test="${mode=='save' }">
					<table>
						<tr>
							<td align="center" bgcolor="#CCCCCC">제&nbsp;&nbsp;&nbsp;목</td>
							<td><input type="text" name="subject" size="95"
								maxlength="100" class="boxTF" placeholder=" 제목을 입력해주세요" /></td>
						</tr>
						<tr>
							<td align="center" bgcolor="#CCCCCC">작성자</td>
							<td><input type="text" name="name" size="95" maxlength="100"
								class="boxTF" placeholder=" 작성자를 입력해주세요" /></td>
						</tr>
						<tr>
							<td align="center" bgcolor="#CCCCCC">E-Mail</td>
							<td><input type="text" name="email" size="95"
								maxlength="100" class="boxTF" placeholder=" 이메일을 입력해주세요" /></td>
						</tr>
						<tr>
							<td align="center" bgcolor="#CCCCCC">내&nbsp;&nbsp;&nbsp;용</td>
							<td><textarea rows="12" cols="100" name="content"
									class="boxTA" placeholder=" 내용을 입력해주세요"></textarea></td>
						</tr>
						<tr>
							<td align="center" bgcolor="#CCCCCC">패스워드</td>
							<td><input type="password" name="pwd" size="95"
								maxlength="100" class="boxTF" placeholder=" 패스워드를 입력해주세요" /></td>
						</tr>
					</table><br>
					<input type="button" value="등록하기" class="btn2" onclick="sendIt();" />
					<input type="reset" value="다시입력" class="btn2"
						onclick="document.myForm.subject.focus();" />
					<input type="button" value="작성취소" class="btn2"
						onclick="javascript:location.href='<%=cp%>/boardTest.do?method=list';" />
				</c:if>


				<!-- 글 수정 -->
				<c:if test="${mode=='updateok' }">
					<table>
						<tr>
							<td align="center" bgcolor="#CCCCCC">제&nbsp;&nbsp;&nbsp;목</td>
							<td><input type="text" name="subject" size="95"
								value="${dto.subject}" maxlength="100" class="boxTF"
								placeholder=" 제목을 입력해주세요" /></td>
						</tr>
						<tr>
							<td align="center" bgcolor="#CCCCCC">작성자</td>
							<td><input type="text" name="name" size="95" maxlength="100"
								value="${dto.name}" class="boxTF" placeholder=" 작성자를 입력해주세요" /></td>
						</tr>
						<tr>
							<td align="center" bgcolor="#CCCCCC">E-Mail</td>
							<td><input type="text" name="email" size="95"
								maxlength="100" value="${dto.email}" class="boxTF"
								placeholder=" 이메일을 입력해주세요" /></td>
						</tr>
						<tr>
							<td align="center" bgcolor="#CCCCCC">내&nbsp;&nbsp;&nbsp;용</td>
							<td><textarea rows="12" cols="100" name="content"
									class="boxTA" placeholder=" 내용을 입력해주세요">${dto.content}</textarea></td>
						</tr>
						<tr>
							<td align="center" bgcolor="#CCCCCC">패스워드</td>
							<td><input type="password" name="pwd" size="95"
								value="${dto.pwd}" maxlength="100" class="boxTF"
								placeholder=" 패스워드를 입력해주세요" /></td>
						</tr>
					</table>
					<br>
					<input type="button" value="수정하기" class="btn2" onclick="sendIt();" />
					<input type="button" value="수정취소" class="btn2"
						onclick="javascript:location.href='<%=cp%>/boardTest.do?method=list';" />
				</c:if>
			</div>
		</form>
	</div>
</body>

</html>