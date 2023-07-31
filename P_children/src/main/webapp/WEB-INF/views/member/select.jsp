<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>select</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/login.css?v=0" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/body.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function userSelect(){
	const select = document.memberLoginForm.userSelect.value;	

	location.href = "${contextPath}/member/emailCheck?userSelect="+select;
	
}
</script>
<script>
function get_main(event) {
	  document.getElementById('main').innerText = 
	    event.target.id;
	}
</script>
</head>
<body>

	<c:import url="../default/header.jsp"/>
	
	<section>
		<div class="form-box">
			<form name="memberLoginForm" action="${contextPath}/member/userCheck" method="post" >
				<h2>MemberShip</h2>
				<div class="select">
				<input type="radio" name="userSelect"  id="메이트위드가 직접 경험해보고 선정한 알찬 프로그램을 즐기며 일상을 행복으로 채워보세요." value="member"  onclick="get_main(event)" ><label for="member"><a>일반</a></label>
				<input type="radio" name="userSelect"  id="반려 메이트와 함께하는 즐거움을 여러 사람들에게 알리며 행복을 나눠보세요." value="host" onclick="get_main(event)" ><label for="host"><a>호스트</a></label>
				</div>
				<br>
				<div class="main" id='main'>
				</div>
				<div class="register">
					<p><a onclick="userSelect()">회원가입</a></p>
				</div>
			</form>
		</div>
	</section>

	<c:import url="../default/footer.jsp"/>

</body>
</html>