
<!-- manager_memberList -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 회원 관리</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/page.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<style type="text/css">
table             { 
  border-spacing: 1; 
  border-collapse: collapse; 
  background:white;
  border-radius:6px;
  overflow:hidden;
  width:100%;
  margin:0 auto;
  position:relative;
  }
    td,th{ 
    padding-left:8px;
    padding-right: 8px;
    text-align:center;
    }

  thead tr  { 
    height:40px;
    background:#C19681;
    font-size:16px;
  }
  
  tbody tr     { 
  height:40px; 
  border-bottom:1px solid white;
    &:last-child  { border:0; }
  }
#left{
	text-align: left;
}
</style>
</head>
<body>

	<c:import url="../default/header.jsp"/>
	
	<section ><!-- body -->
		<div class="form-box-list"> <!--  container  -->
		<div class="title" >회원 관리</div>
		<table >
		<thead>
			<tr>
					<th>아이디</th>
					<th>비밀번호</th>
					<th>닉네임</th>
					<th>휴대번호</th>
					<th>이메일</th>
					<th>주소</th>
					<th>등급</th>
					<th>관리</th>
			</tr>
		</thead>
				<c:if test="${memberList.size() == 0 }">
					<tr>
						<th colspan="8">등록된 회원 없습니다.</th>
					</tr>
				</c:if>
				<c:forEach var="member" items="${memberList }">
					<tr>
						<td>${member.id }</td>
						<td>${member.pwd }</td>
						<td>${member.nickname }</td>
						<td>${member.phone }</td>
						<td>${member.email }</td>
						<td id="left">${member.addr }</td>
						<td>${member.grade }</td>
						<td><button onclick="location.href='${contextPath}/member/delete?id=${member.id}'">삭제</button></td>
					</tr>
				</c:forEach>
		</table>
			<div class="page_wrap">
			   <div class="page_nation">
			      <!-- <a class="arrow pprev" href="#"></a> -->
					<c:forEach  var="num" begin="1" end="${repeat }">
								<a href="manager_memberList?num=${num }" class="active"> &nbsp; ${num } &nbsp;</a>
					</c:forEach>
			      <!-- <a class="arrow nnext" href="#"></a> -->
			   </div>
			</div>	
			<ul class="menu">
				<li><a href="">메뉴</a>
					<ul class="submenu_">
						<hr>
						<li><a href="${contextPath }/member/manager_information">이전</a></li>
					</ul>
				</li>
			</ul>		
		</div>
	</section>

	<c:import url="../default/footer.jsp"/>

</body>
</html>