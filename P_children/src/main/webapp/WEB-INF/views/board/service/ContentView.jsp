<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 문의</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function valueCheck() {
		
		if (document.write_form.content.value == "") {
			alert('내용 을 입력해주세요');
			document.write_form.content.focus();
		} else {
			
			if(!confirm('답변을 작성하시겠습니까?')){
				return false;
			} else {
				//$("#write_form").submit();
				 $.ajax({
						url : "manager_write_save",
						type : "POST",
						data : $("#write_form").serialize(),
						success : function(data) {
							$("#count").text(data)
							console.log("성공");
							window.location.href = "${contextPath }/board/ContentView?write_no="+${qna_dto.write_no};
						},
						error : function() {
							console.log("실패");
						}

					})
			}
			
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
  border-bottom:1px solid white;
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
		<div class="title" >문의 내용</div>
		<form >
		<input type="hidden"  name="id" id="id" value="${id}">
		<table>
			<tr>
				<th>제목</th>
				<td><input type="text"  name="title" id="title" value="${qna_dto.title}" readonly="readonly"></td>
			</tr>
			<tr>
				<th >내용</th>
				<td ><textarea name="content" id="content" readonly="readonly">${qna_dto.content}</textarea></td>
			</tr>
		</table>
		</form>
		<br>
		<!--  일반 유저의 경우만 보이는 문의답변창-->
		<c:if test="${info.grade != admin}"> 
			<c:if test="${qna_rep_dto.content != null}"> 
				<div class="title" >문의 답변</div>
				<form name="write_form"  id="write_form" action="${contextPath }/board/manager_write_save"  method="post">
				<input type="hidden"  name="id" id="id" value="${id}">
				<input type="hidden" name="write_no" value="${qna_dto.write_no}">
				<table>
					<c:if test="${info.grade != admin}">
						<tr>
							<th >내용</th>
							<td ><textarea name="content" id="content"  readonly>${qna_rep_dto.content }</textarea></td>
						</tr>
					</c:if>
				</table>
				</form>
			</c:if>
		</c:if>
		<!--  관리자인 경우만 보이는 문의 답변창-->
		<c:if test="${info.grade == admin}">
			<div class="title" >문의 답변</div>
			<form name="write_form"  id="write_form" action="${contextPath }/board/manager_write_save"  method="post">
			<input type="hidden"  name="id" id="id" value="${id}">
			<input type="hidden" name="write_no" value="${qna_dto.write_no}">
			<table>
				<tr>
					<th >내용</th>
					<td ><textarea name="content" id="content" <c:if test="${qna_rep_dto.content != null}">readonly</c:if>>${qna_rep_dto.content }</textarea></td>
				</tr>		
			</table>
				<c:if test="${qna_rep_dto.content == null}">
					<input type="button" value="확인" onclick="valueCheck()" class="but_1">				
				</c:if>
			</form>
		</c:if>
		<c:if test="${info.grade != admin}">
				<ul class="menu">
			      <li>
			        <a href="#">메뉴</a>
			        <ul class="submenu_">
			         <hr>	          
			          	<li><a href="${contextPath }/board/member_qna">목록</a></li>
			        </ul>
			      	</li>
			    </ul> 	
			   </c:if>
		<c:if test="${info.grade == admin}">
				<ul class="menu">
			      <li>
			        <a href="#">메뉴</a>
			        <ul class="submenu_">
			         <hr>	          
			          	<li><a href="${contextPath }/board/manager_qna">목록</a></li>
			        </ul>
			      	</li>
			    </ul> 	
			   </c:if>
			</div>
	</section>


		<c:import url="../../default/footer.jsp"/>

</body>
</html>