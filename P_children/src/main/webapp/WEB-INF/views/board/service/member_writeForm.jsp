<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 문의 작성</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=2" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function valueCheck() {
		
		if (document.write_form.title.value == "") {
			alert('제목을 입력해주세요');
			document.write_form.title.focus();
		} else if (document.write_form.content.value == "") {
			alert('내용을 입력해주세요');
			document.write_form.content.focus();
		} else {
			$.ajax({
						url : "member_write_save",
						type : "POST",
						data : $("#write_form").serialize(),
						success : function(data) {
							$("#count").text(data)
							console.log("성공");
							window.location.href = "${contextPath }/board/member_qna";
						},
						error : function() {
							console.log("실패");
						}

					})
		}
	}
</script>
<style type="text/css">
table             { 
  border-	spacing: 1; 
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
 th { background-color: #C19681; }
  tbody tr     { 
  height:40px; 
  border-bottom:1px solid white ;
    &:last-child  { border:0; }
  }
textarea{
	padding: 25px;
  	width: 100%;
  	height: 250px;
	resize: none;
	border: none;
  }
 input{
 	padding-left: 25px;
	width: 100%;
	border: none;
 }
</style>
</head>
<body>

		<c:import url="../../default/header.jsp"/>
		
	<section ><!-- body -->
		<div class="form-box-list"> <!--  container  -->
		<div class="title" >문의 작성</div>
		<form name="write_form"  id="write_form" action="${contextPath }/board/member_write_save"  method="post">
		<input type="hidden"  name="id" id="id" value="${id}">
		<table>
			<tr>
				<th>제목</th>
				<td><input type="text"  name="title" id="title" maxlength="100"></td>
			</tr>
			<tr>
				<th >내용</th>
				<td ><textarea name="content" id="content" maxlength="100"></textarea></td>
			</tr>
				<tr>
					<th colspan="3">
						<input type="button" value="확인" onclick="valueCheck()" class="but_1">
					</th>
				</tr>
		</table>
		</form>
			<ul class="menu">
			      <li>
			        <a href="#">메뉴</a>
			        <ul class="submenu_">
			         <hr>	          
			          	<li><a href="${contextPath }/board/member_qna">이전</a></li>
			        </ul>
			      	</li>
			    </ul> 	
		</div>
	</section>


		<c:import url="../../default/footer.jsp"/>

</body>
</html>