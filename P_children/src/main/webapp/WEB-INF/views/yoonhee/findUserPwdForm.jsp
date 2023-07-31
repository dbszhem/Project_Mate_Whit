<!-- findUserPwdForm.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 비밀번호 찾기</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/login.css?v=2" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/body.css?v=1" rel="stylesheet" type="text/css">
<script src="${contextPath}/resources/yoonhee/js/memberLoginScript.js?v=1"></script>
<script>
	
//인증코드 전송
function sendEmail(){
	let email = $("#input_email").val();

	if(email == ""){
		alert("이메일을 입력해 주세요!");
		$("#input_email").focus();
		return;
	}
	$.ajax({
		url: "sendFindPwdEmailCode",
		type: "get",
		data: {"email" : email},
		success: function(code){
			alert("인증코드가 전송되었습니다. 입력한 메일주소를 확인해 주세요." );
			$("#email_code").val(code);
		},
		error: function(){
			alert("Error");
		}	
	});
}

// 인증코드 체크
	function CheckCode(){
	let code = $("#input_code").val();
	let check = $("#email_code").val();
	
	if(code === check && code != ""){
		$("#result").text("인증 성공");
		alert("인증성공! 새 비밀번호 수정 페이지로 이동합니다.");
		$("#findUserPwdForm").submit();
	} else {
		$("#result").text("인증 실패");
		$("#input_code").focus();
	}
} 
	
</script>
</head>
<body>

	<c:import url="../default/header.jsp"/>
	
	<section>
		<div class="form-box">
			<form id="findUserPwdForm" name="userPwdForm" action="${contextPath}/member/findUserPwd" method="post">
				<h2>비밀번호 찾기</h2>
				<div class="inputbox">
					<div class="ion-icon">
					<i class='bx bx-user'></i>
					</div>
					<input type="text" name="findUserId" required="required">
					<label for="">아이디</label>
				</div>
				<div class="inputbox">
					<div class="ion-icon">
					<i class='bx bx-envelope' onclick="sendEmail()"></i>
					</div>
					<input type="text" id="input_email" name="email" required="required">
					<label for="">이메일</label>
				</div> 
				<div class="inputbox">
					<div class="ion-icon">
					<i class='bx bxs-envelope'></i>
					</div>
					<input type="text" id="input_code" name="code" required="required">
					<label for="">인증코드</label>
				</div>
				<div class="main" id='main'>
						<span id="result"></span>
				</div>
					<input type="hidden" id="email_code" >
				<input type="button" id="findPwdBtn" class="but_1" value="비밀번호 찾기" onclick="findUserPwdCheck()">
				
			</form>
		</div>
	</section>

	<c:import url="../default/footer.jsp"/>

</body>
</html>