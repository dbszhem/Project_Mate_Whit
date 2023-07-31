
<!-- manager_board -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>manager_board</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css" rel="stylesheet" type="text/css">
<style type="text/css">
table             { 
  border-spacing: 1; 
  border-collapse: collapse; 
  background:white;
  border-radius:6px;
  overflow:hidden;
  max-width:1000px; 
  width:100%;
  margin:0 auto;
  position:relative;
  }
    td,th           { padding-left:8px}

  thead tr  { 
    height:50px;
    background:#A996DB;
    font-size:16px;
  }
  
  tbody tr     { 
  height:48px; 
  border-bottom:1px solid #E3F1D5 ;
    &:last-child  { border:0; }
  }
  
  td,th           { text-align:left;
    &.l           { text-align:right }
    &.c           { text-align:center }
    &.r           { text-align:center }
  }
</style>
</head>
<body>

	<c:import url="../default/header.jsp"/>

	<section class="main-landing">
	<div class="main_box">
		<div class="text-section">
			<h2>게시물 관리</h2>
			<p>관리자 정보 > 게시물 관리</p>
		</div>
		<div class="table-member">
		<table class="member">
			<tr>
				<th>글 번호</th>
				<th>아이디</th>
				<th>제목</th>
				<th>작성일</th>
				<th>조회수</th>
				<th>파일이름</th>
				<th>관리</th>
			</tr>
			<c:if test="${boardList.size() == 0 }">
				<tr>
					<th colspan="7">등록된 글이 없습니다.</th>
				</tr>
			</c:if>
			<c:forEach var="dto" items="${boardList }">
				<tr>
					<th>${dto.write_no }</th>
					<th>${dto.id }</th>
					<th>${dto.title }</th>
					<th>${dto.savedate }</th>
					<th>${dto.hit }</th>
					<th>${dto.file_name }</th>
					<th><input type="button" value="삭제"></th>
				</tr>
			</c:forEach>
		</table>
		</div>


		<div class="under-section">
			<c:forEach var="num" begin="1" end="${repeat }">
				<a href="manager_board?num=${num }">[ ${num } ]</a>
			</c:forEach>
		</div>

		<a href="/root/member/manager_memberList">회원 목록 관리</a> 
		<a href="/root/member/manager_board">게시물 관리</a> 
		<a href="/root/member/manager_qna">1대1문의</a>
	</div>
	</section>

	<c:import url="../default/footer.jsp"/>


</body>
</html>