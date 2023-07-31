	
<!-- footer -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>footer</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/footer.css?v=1" rel="stylesheet" type="text/css">
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'> 
</head>
<body>

	<footer class="footer">
		<a href="${contextPath }/index" class="logo"><i class='bx bxs-dog'></i><span>Mate With. </span>서울시 강남 역삼동</a>
		<a>대표 : Mate With</a> &nbsp; <a>사업자 등록번호 : 000-00-00000</a>
		<br>
		<a>대표 번호 : +00)0-000-0000 </a> &nbsp; <a>팩스 번호 : +00)0-000-0000</a> &nbsp; <a>홈페이지 : Mate With.web.com</a>
		<br>
		<a>Copyright &copy; P_children.All Rights Reserved</a>
	</footer>
	
</body>
</html>