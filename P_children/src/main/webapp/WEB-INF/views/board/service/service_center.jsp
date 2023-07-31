
<!-- service_center -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 고객 센터</title>
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'> 
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=2" rel="stylesheet" type="text/css">
<style type="text/css">
img{
	float: left;
	margin-right: 10px;
}
.form-box_center{
	max-width: 750px;
	width: 100%;
	padding: 25px 30px;
	border: 2px solid #C19681;
	border-radius: 20px;
}
.form-box_center .title{
	font-size: 25px;
	font-weight: 500;
	position: relative;
	margin-bottom: 10px;
}
.pone{
 	margin-left: 150px;
 	margin-bottom: 10px;
}
.qna{
	margin-left: 150px;
	margin-bottom: 10px;
}
.qna_list{
	margin-left: 150px;
	margin-bottom: 10px;
}
.qna_list a{
	font-size:  30px;
}
</style>
</head>
<body>
	
		<c:import url="../../default/header.jsp"/>
		
		<section ><!-- body -->
			<div class="form-box_center"> <!--  container  -->
				<div class="title" >고객센터</div>
					<img src="${contextPath }/resources/chenggyu/2824016.png" width="150px" height="150px">
					<h1>안녕하세요</h1>
					<h2>무엇을 도와드릴까요 ?</h2>
					<div class="text">
						<a>평일 오전 am 8:00 ~ pm 18:00</a><br>
						<a>점심 시간 pm 12:00 ~ 13:00</a><br>
						<a>휴무 토요일, 일요일 공휴일</a><br>
						<hr>
					</div>
						<br>
					<div class="pone">
						<img src="${contextPath }/resources/chenggyu/pone.png" width="50px" height="50px">
							<a>전화상담 </a><br>
							<a>02-0000-0000</a> / <a>010-0000-0000</a>
					</div>
					<div class="qna">
						<img src="${contextPath }/resources/chenggyu/msg.png" width="50px" height="50px">
							<a>Email / 문자 상담</a><br>
							<a>0000@gmail.com</a> / <a>010-0000-0000</a>
						</div>
					<div class="qna_list">
						<img src="${contextPath }/resources/chenggyu/pngwing.com.png" width="50px" height="50px">
						<c:choose>
							<c:when test="${loginUser == null }">
								<a href="${contextPath }/member/memberLoginForm">1:1 문의</a>
							</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${info.grade == 'gold' || grade =='gold'}">
											<a href="${contextPath }/board/manager_qna">1:1 문의</a>
										</c:when>
										<c:otherwise>
											 <a href="${contextPath }/board/member_qna">1:1 문의 ${info.grade }</a>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
						</c:choose>
					</div>
			</div>
	</section>
		
		<c:import url="../../default/footer.jsp"/>

</body>
</html>