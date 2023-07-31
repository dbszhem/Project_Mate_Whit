
<!-- member_modify -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 회원정보 수정</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=1" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/body.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/regist.css?v=1" rel="stylesheet" type="text/css">
<script src="${contextPath }/resources/chenggyu/js/memberModify.js?v=1"></script>

<script>	
	window.onload = function(){
		if(${cookie.userSelect.value == "host"}){
			$("#divHost").css("display", "");
			
			function deleteSpan_mateName(){
				$("#mateName_").empty();
			}
			
			function deleteSpan_mateBreed(){
				$("#mateBreed_").empty();
			}
		}
		
	}
	
	// form 값 들어왔는지 확인.
function valueCheck() {
		let pwdCheck = document.querySelector("span#pwdCheck_");
		let new_pwd = document.querySelector("span#pwd_new");
		let nickname = document.querySelector("span#nickname_");
		let phone = document.querySelector("span#phone_");
		let email = document.querySelector("span#email_");
		let addr = document.querySelector("span#addr_");
		let mateName = document.querySelector("span#mateName_");
		let mateBreed = document.querySelector("span#mateBreed_");
		
		 if(document.form.pwdCheck.value == ""){
			pwdCheck.style.color = "red";
			pwdCheck.innerHTML = "현재 비밀번호를 입력해 주세요.";
			document.form.pwdCheck.focus();
		}
		else if(document.form.new_pwd.value == ""){
			new_pwd.style.color = "red";
			new_pwd.innerHTML = "새로운 비밀번호를 입력해주세요";
			document.form.new_pwd.focus();
		}
		else if(document.form.nickname.value == ""){
			nickname.style.color = "red";
			nickname.innerHTML = "닉네임을 입력해 주세요.";
			document.form.nickname.focus();
		}
		else if(document.form.phone.value == ""){
			phone.style.color = "red";
			phone.innerHTML = "전화번호를 입력해 주세요.";
			document.form.phone.focus();
		}
		else if(document.form.email.value == ""){
			email.style.color = "red";
			email.innerHTML = "이메일을 입력해 주세요.";
			document.form.email.focus();
		}
		else if(document.form.addr2.value == ""){
			addr.style.color = "red";
			addr.innerHTML = "상세주소를 입력해 주세요. 없으면 '없음'을 입력해 주세요.";
			document.form.addr2.focus();
		}
		else if(${dto.userSelect == "host"}){
			if(document.form.mateName.value == ""){
				mateName.style.color = "red";
				mateName.innerHTML = "메이트 이름을 입력해 주세요.";
				document.form.mateName.focus();
			}
			else if(document.form.mateBreed.value == ""){
				mateBreed.style.color = "red";
				mateBreed.innerHTML = "메이트 견종을 입력해 주세요.";
				document.form.mateBreed.focus();
			}
			else {
				$.ajax({
					url: "modify_save",
					type: "POST",
					data : $("#modfiy_form").serialize(),
					success: function(data){
						$("#count").text(data)
						console.log("성공");
						 window.location.href = "${contextPath }/member/member_information" ;
					},
					error: function(){
						console.log("실패");
					}
					
				})
			}
		}
		else{
			$.ajax({
				url: "modify_save",
				type: "POST",
				data : $("#modfiy_form").serialize(),
				success: function(data){
					$("#count").text(data)
					console.log("성공");
					 window.location.href = "${contextPath }/member/member_information" ;
				},
				error: function(){
					console.log("실패");
				}
				
			})
		}
		
	}	
</script>
</head>
<body>

	<c:import url="../default/header.jsp"/>

	<section><!-- body -->
		<div class="form-box"> <!--  container  -->
		<div class="title">개인 정보 수정</div>
		<form id="modfiy_form" name="form" action="${path }/member/member_write_save"  method="post">
			<div class="user-details">
				<div class="input-box">
					<span class="details">아이디</span>
					<input type="text" id="userid" name="id" value="${dto.id }" readonly="readonly">	
				</div>
				<div class="input-box">
					<span class="details">현재 비밀번호</span>
					<input type="hidden" id="pwd" name="pwd"  value="${dto.pwd}">
					<input type="text" id="pwdCheck" placeholder="비밀번호 재확인" >
					<span id="pwdCheck_"></span>
				</div>
				<div class="input-box">
					<span class="details">새로운 비밀번호</span>
					<input type="text" id="new_pwd" name="new_pwd" oninput="deleteSpan_pwd_new()"  placeholder="새로운비밀번호" >
					<span id="pwd_new"></span>
				</div>
				<div class="input-box">
					<span class="details">닉네임</span>
					<input type="text" id="nickname" name="nickname" oninput="deleteSpan_nickname()" value="${dto.nickname }" >
					<span id="nickname_"></span>
				</div>
				<div class="input-box">
					<span class="details">휴대번호</span>
					<input type="text" id="phone" name="phone" oninput="deleteSpan_phone()" value="${dto.phone}">
					<span id="phone_"></span>
				</div>
				<div class="input-box">
					<span class="details">이메일</span>
					<input type="text" id="email" name="email" value="${dto.email}" readonly="readonly">
					<span id="email_"></span>
				</div>
				<div class="input-box" >
					<span class="details">주소</span>
					<input type="text" id="zonecode" name="zonecode" size="5" placeholder="우편번호" value="${zonecode }">
					<input type="text" id="addr1" name="addr1" size="30" placeholder="주소" value="${addr1 }">
					<input type="text" id="addr2" name="addr2" placeholder="상세주소" oninput="deleteSpan_addr2()" value="${addr2 }">
					<input type="text" id="addr3" name="addr3" size="15" placeholder="참고항목" value="${addr3 }">
					<span id="addr_"></span>
				</div>
				<div class="input-box-addr">
					<input type="button" value="우편번호찾기" onclick="findAddr()">
				</div>
			<input type="hidden" id="userSelect" name="userSelect" value="${dto.userSelect}">
			<c:if test="${dto.userSelect == 'host' }">
				<div class="input-box">
					<span class="details">메이트 정보</span>
					<input type="text" id="mateName" name="mateName" oninput="deleteSpan_mateName()" value="${dto.mateName}"> <!-- oninput="deleteSpan_mateName()" -->
					<span id="mateName_"></span>
					<input type="text" id="mateBreed" name="mateBreed" oninput="deleteSpan_mateBreed()" value="${dto.mateBreed}"> <!-- oninput="deleteSpan_mateBreed()" -->
					<span id="mateBreed_"></span>
				</div>
				</c:if>
			</div>
				<input type="button" value="완료" onclick="valueCheck()" class="but_1">
		</form>
			<ul class="menu">
				<li><a href="">메뉴</a>
					<ul class="submenu_">
						<hr>
						<li><a href="${contextPath }/member/member_information">이전</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</section>
	
	<c:import url="../default/footer.jsp"/>

</body>
</html>