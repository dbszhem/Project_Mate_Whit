<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 비밀번호 찾기 결과</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script src="${contextPath}/resources/yoonhee/js/memberLoginScript.js?v=3"></script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/login.css?v=2" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/body.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function userUpdatePwd(){
    let id = $("#id").val();
    let newPwd = $("#newPwd").val();
    console.log(id);
    console.log(newPwd);
    
    if(!id == "" && !newPwd == ""){
       $.ajax({
          url: "userUpdatePwd",
          type: "post",
          data: {"id" : id, "newPwd" : newPwd},
          success: function(code){
             location.href='/root/index';
             alert("비밀번호가 변경되었습니다.");
          },
          error: function(){
             alert("비밀번호 변경이 실패되었습니다.");
          }   
       });
    }
    
 }
</script>
</head>
<body>

	<c:import url="../default/header.jsp"/>
	
	<section>
		<div class="form-box">
			<form name="findUserPwdResult" id="pwddd" action="${contextPath }/member/userUpdatePwd" method="post">
         <input type="hidden" id="id" name="id" value="${findUserPwd.id }"> 
         <input type="hidden" id="pwd" name="pwd" value="${findUserPwd.pwd }">
				<h2>비밀번호 찾기 결과</h2>
				<c:if test="${findUserPwd != null }">
				<div class="main_1_2" id='main_1_2'>
					<label for="">아이디  ${findUserPwd.id }</label>
				</div>
				<div class="inputbox">
					<div class="ion-icon">
					<i class='bx bx-lock-alt' ></i>
					</div>
					<input type="text"  id="newPwd" name="newPwd"   required="required">
					<label for="">새 비밀 번호 </label>
					<input type="hidden" id="newPwd_value">
				</div>
				<div class="inputbox">
					<div class="ion-icon">
					<i class='bx bxs-lock-alt' ></i>
					</div>
					<input type="password" id="newRePwd" name="newRePwd" required="required">
					<label for="">새 비밀번호 확인</label>

				</div>
				<input type="button"  class="but_1" onclick="userUpdatePwdCheck()" value="확인">
				</c:if>
			</form>
		</div>
	</section>

	<c:import url="../default/footer.jsp"/>


</body>
</html>