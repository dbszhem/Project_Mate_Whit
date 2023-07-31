<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath }"/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>email 인증 페이지</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/login.css?" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/body.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script>
	//이메일 중복체크
	$(function(){
		$("#input_email").keyup(function(){
			let email = $("#input_email").val();
			
			$.ajax({
				url: "checkEmail",
				type: "get",
				data: {"email" : email},
				success: function(data){
					if(data === "OK"){
						$("#email_").text("사용가능한 이메일");
					} else {
						$("#email_").text("중복된 이메일");
					}
				},
				error: function(){
					//alert("Error");
				}
			});
		});
	});  
	
	// 인증코드 전송
	function sendEmail(){
		if($("#email_").text() == "중복된 이메일"){
			$("#input_email").focus();
			return;
		}
		
		const regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		let email = $("#input_email").val();
		
		if(!email == ""){
			if(!regExp.test(email)){
				alert("이메일 형식이 맞지 않습니다. 다시 확인해 주세요.");
				return;
			}
			
			$.ajax({
				url: "sendEmail",
				type: "get",
				data: {"email" : email},
				success: function(code){
					alert("인증코드가 전송되었습니다. 입력한 메일주소를 확인해 주세요." );
					$("#email_code").val(code);
				},
				error: function(){
					//alert("Error");
				}	
			});
		} else {
			alert("이메일을 입력해 주세요!");
			$("#input_email").focus();
		}
	}
		
	
	// 인증코드 체크
	function CheckCode(){
		let code = $("#input_code").val();
		let check = $("#email_code").val();
		
		if(code === check && code != ""){
			alert("인증 성공! 회원가입 페이지로 이동합니다.");
			$("#emailCheckForm").submit();
		} else {
			$("#result").html("&nbsp; 인증코드 불일치");
			$("#input_code").focus();
		}
	}
	
	
</script>
</head>
<body>
	
	<c:import url="../default/header.jsp"/>
	
		<section>
		<div class="form-box">
			<form action="${path }/member/registForm" method="get" id="emailCheckForm">
				<h2>Email</h2>
				<div class="inputbox">
					<div class="ion-icon">
					<i class='bx bx-envelope' onclick="sendEmail()"></i>
					</div>
					<input type="email"  id="input_email"  name="email"  required="required" >
					<label for="">이메일</label>
				</div>
				<div class="main" id='main'>
					<span id="email_"></span>
				</div>
				<div class="inputbox">
					<div class="ion-icon">
					<i class='bx bx-comment' ></i>
					</div>
					<input type="text" id="input_code" name="code" required="required">
					<label for="">인증코드</label>
				</div>
				<div class="main" id='main'>
						<span id="result"></span>
				</div>
					<input type="hidden" id="email_code" >
				<input  type="button"  value="확인" class="but_1"  onclick="CheckCode()">
			</form>
		</div>
	</section>
	
		<c:import url="../default/footer.jsp"/>
	
</body>
</html>
































