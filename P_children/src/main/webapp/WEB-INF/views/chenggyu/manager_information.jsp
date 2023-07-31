
<!-- manager_information -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 관리자 정보</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=4" rel="stylesheet" type="text/css">
</head>
<body>
		<c:import url="../default/header.jsp"/>
		
		<section><!-- body -->
		<div class="form-box"> <!--  container  -->
		<div class="title">관리자 정보</div>
		<form >
			<div class="user-details">
				<div class="input-box">
					<span class="details">아이디</span>
					<input type="text"  value="${dto.id }" readonly="readonly">	
				</div>
				<div class="input-box">
					<span class="details">닉네임</span>
					<input type="text" value="${dto.nickname }" readonly="readonly" >
				</div>
				<div class="input-box">
					<span class="details">휴대번호</span>
					<input type="text" value="${dto.phone }" readonly="readonly">
				</div>
				<div class="input-box">
					<span class="details">이메일</span>
					<input type="text" value="${dto.email }" readonly="readonly">
				</div>
				<div class="input-box">
					<span class="details">등급</span>
					<input type="text" value="${dto.grade }" readonly="readonly">
				</div>
				<div class="input-box_addr" >
					<span class="details_addr">주소</span>
					<input type="text" value="${dto.addr }"  readonly="readonly">
				</div>
			</div>
			<ul class="menu">
				<li><a href="">메뉴</a>
					<ul class="submenu_manger">
						<hr>
							<li><a href="${contextPath }/member/manager_memberList">회원 관리</a></li>
						<hr>
							<li><a href="${contextPath }/board/manager_qna">문의 관리</a></li>
						<hr>
							<li><a href="${contextPath }/member/kakaoPaymentApproveList">카카오페이 관리</a></li>
						<hr>
							<li><a href="${contextPath }/mypageBoard/certified/hostCertificationApplyList">Host 신청 관리</a></li>														
					</ul>
				</li>
			</ul>
		</div>
	</section>
	
	<c:import url="../default/footer.jsp"/>

</body>
</html>