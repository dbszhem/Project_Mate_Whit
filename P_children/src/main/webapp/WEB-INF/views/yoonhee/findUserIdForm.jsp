<!-- fineUserIdForm -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 아이디 찾기</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script src="${contextPath}/resources/yoonhee/js/memberLoginScript.js?v=2"></script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/body.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/login.css?v=2" rel="stylesheet" type="text/css">
</head>
<body>

	<c:import url="../default/header.jsp"/>
	
		<section>
		<div class="form-box">
			<form name="findUserIdForm" action="${contextPath}/member/findUserId" method="post">
				<h2>아이디 찾기</h2>
				<div class="inputbox">
					<div class="ion-icon">
					<i class='bx bx-envelope' ></i>
					</div>
					<input type="text"  name="findUserEmail"  required="required">
					<label for="">이메일</label>
				</div>
				<div class="inputbox">
					<div class="ion-icon">
					<i class='bx bx-phone' ></i>
					</div>
					<input type="text" name="findUserPhone" required="required">
					<label for="">전화번호</label>
				</div>
				<input type="button" class="but_1" value="아이디 찾기" onclick="findUserIdCheck()">
			</form>
		</div>
	</section>
	
	<c:import url="../default/footer.jsp"/>
			
</body>
</html>