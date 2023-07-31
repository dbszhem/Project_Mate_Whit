
<!-- member_board -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member_board</title>
<style type="text/css">
table{
	border: 1px solid;
	border-collapse: collapse;
	height: 50px;
	width: 500px;
}
th, td {
	border: 1px solid ;
}
</style>
</head>
<body>
		
		<div align="center">
		<br>
		<h1>나의 게시글</h1>
		<br>
		<table>
			<tr>
				<th>글 번호</th><th>제목</th><th>조회수</th><th>작성일</th><th>닉네임</th>
			</tr>
			<c:if test="${my_board_list.size() == 0 }">
				<tr>
					<th colspan="7">등록된 글이 없습니다.</th>
				</tr>
			</c:if>
			<c:forEach var="dto" items="${my_board_list }">
				<tr>					
					<th> ${dto.write_no }</th>
					<th>${dto.title }</th>
					<th>${dto.hit }</th> 
					<th>${dto.savedate }</th>
					<th>${dto.id }</th>
				</tr>
			</c:forEach>
		</table>
		
			<c:forEach  var="num" begin="1" end="${repeat }">
						<a href="member_board?num=${num }">[ ${num } ]</a>
			</c:forEach>
		
		<br><br>
		<a href="/root/member/member_information ">개인 정보</a>
		
		</div>
	
	

</body>
</html>