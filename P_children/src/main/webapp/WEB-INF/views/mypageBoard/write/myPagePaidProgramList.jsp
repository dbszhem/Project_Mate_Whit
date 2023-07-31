<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 내가 결제한 프로그램</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=1" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/page.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script type="text/javascript">
function cancel_request(id, write_no) {
	if(confirm("프로그램 결제 취소를 신청하시겠습니까?")){
		$.ajax({
			url: "cancelRequest",
			type: "post",
			data: {
				"id" : id,
				"write_no" : write_no
				},
			success: function(result){
				if(result == 'ok'){
					alert('결제 취소 신청이 완료되었습니다. 페이지를 새로고침 해주세요.');
					$('#newPg').text('페이지를 새로고침 해주세요.');
				} else {
					alert("Error! 고객센터로 문의해 주세요.");
				}
			},
			error: function(){
				alert("Error");
			}
			
		});

	}
	
} 
</script>
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
  border-bottom:1px solid #C19681;
    &:last-child  { border:0; }
  }
</style>
</head>
<body>

	<c:import url="../../default/header.jsp"/>
	
		<section ><!-- body -->
		<div class="form-box-list"> <!--  container  -->
		<div class="title" >결제한 프로그램</div>
		<table >
		<thead>
			<tr>
					<th>프로그램 이름</th>
					<th>바로가기</th>
					<th>결제 취소 신청</th>
			</tr>
		</thead>
				<c:if test="${paidProgramList.size() == 0 }">
					<tr>
						<th colspan="3">없음</th>
					</tr>
				</c:if>
			<c:forEach var="PP" items="${paidProgramList}">
					<tr>
						<td>${PP.title}</td>
						<td><button onclick="location.href='${contextPath}/programBoard/programContentView?write_no=${PP.write_no }&num=${PP.num }'">프로그램 보기</button></td>
						<c:if test="${PP.cancel_request == 'N' }">
							<td><button onclick="cancel_request('${PP.id}', '${PP.write_no }')">결제 취소 신청하기</button> &nbsp; <span id="newPg"></span></td>
						</c:if>
						<c:if test="${PP.cancel_request == 'Y' }">
							<td><span>결제 취소 신청 완료 (취소가 되면 결제한 프로그램이 현재 페이지에서 사라집니다.)</span></td>
						</c:if>
					</tr>
			</c:forEach>
		</table>
			<div class="page_wrap">
			   <div class="page_nation">
			      <!-- <a class="arrow pprev" href="#"></a> -->
			      	<c:if test="${startPage > block }">
							<a href="paidProgramInfoList?pg_num=${startPage-1 }"   class="arrow prev"> 이전 </a>
						</c:if>
						<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
							<c:if test="${i == pg_num}">
								<a href="paidProgramInfoList?pg_num=${i }"  class="active"> ${i } </a>
							</c:if>
							<c:if test="${i != pg_num}">
								<a href="paidProgramInfoList?pg_num=${i }" class="active"> ${i } </a>
							</c:if>
						</c:forEach>
						<c:if test="${endPage < totalPage }">
							<a href="paidProgramInfoList?pg_num=${endPage+1 }" class="arrow next" > 다음 </a>
						</c:if>
			      <!-- <a class="arrow nnext" href="#"></a> -->
			   </div>
			</div>
			<ul class="menu">
				<li><a href="">메뉴</a>
					<ul class="submenu_">
						<hr>
						<li><a href="${contextPath }/member/member_information">이전</a></li>
					</ul>
				</li>
			</ul>			
						
		</div>
	</section>
	
	
		
	<c:import url="../../default/footer.jsp"/>
	
</body>
</html>