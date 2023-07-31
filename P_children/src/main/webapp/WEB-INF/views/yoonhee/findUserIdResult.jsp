<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 아이디 찾기 결과</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/login.css?v=2" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/body.css" rel="stylesheet" type="text/css">
</head>
<body>

	<c:import url="../default/header.jsp"/>
	
	<section>
		<div class="form-box">
			<form name="memberLoginForm" action="${contextPath}/member/userCheck" method="post" >
				<h2>아이디 찾기 결과</h2>
				<div class="inputbox_result">
				<div class="main_1" id='main_1'>
					<c:if test="${findUserId != null }">
						<label for="">가입된 아이디 ${findUserId.id }</label>
					</c:if>
					<c:if test="${findUserId == null }">
						<label for="">존재하지 않는 회원</label>
					</c:if>
					</div>
				</div>
				<button type="button"  value="로그인"  class="but_1" onclick="location.href='${contextPath }/member/memberLoginForm'">로그인</button>
				<button type="button"  value="비밀번호 찾기" class="but_2_1" onclick="location.href='${contextPath }/member/findUserPwdForm'">비밀번호 찾기</button>
			</form>
		</div>
	</section>

	<c:import url="../default/footer.jsp"/>
	
</body>
</html>