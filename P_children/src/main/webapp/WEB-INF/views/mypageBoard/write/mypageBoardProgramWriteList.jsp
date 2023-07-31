<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>  
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 작성한 게시글 보기</title>
<script type="text/javascript">

	function confirmToCancel(tid){
		
		if(!confirm('취송승인하시겠습니까?')){
			return false;
		} else {
			alert('tid : ' + tid + ' 취소되었습니다~!');
			
			$.ajax({
				url: "paidCancelRequest/"+tid,
				type: "post",
				contentType: "application/json; charset=utf-8",
				success: function(data){
					alert("반환값은 " + data);
				}, // success end
				error: function() {
					alert("Error!!")
				}
				
			}) // ajax end
			return true;
		}
	}
</script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=3" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/page.css" rel="stylesheet" type="text/css">
<style type="text/css">
table             { 
  border-spacing: 1; 
  border-collapse: collapse; 
  background:white;
  border-radius:6px;
  overflow:hidden;
  max-width:800px; 
  width:100%;
  margin:0 auto;
  position:relative;
  }
    td,th           { 
    padding-left:8px;
    text-align: center;
    }

  thead tr  { 
    height:50px;
    background:#C19681;
    font-size:16px;
  }
  
  tbody tr     { 
  height:48px; 
  border-bottom:1px solid #C19681;
    &:last-child  { border:0; }
  }
 #file_name{
 	display: none;
 }
</style>
</head>
<body>

	<c:import url="../../default/header.jsp"/>

	<section><!-- body -->
		<div class="form-box"> <!--  container  -->
		<div class="title" >프로그램 게시글</div>
		<div class="valueHiddenView">${programBoardNum}</div>
	 	<!-- 프로그램 테이블 시작 -->
		<table>
			<!-- 제목 칼럼 -->
		<thead>			
			<tr>
				<th>글번호</th>
				<th>제 목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>예약현황</th>
				<th>조회수</th>
				<th>프로그램 참여 인원</th>	
			</tr>
		</thead>
			<!-- // 제목 칼럼 끝 -->
			<!-- 내용 시작 -->
			<c:choose>
				<c:when test="${empty mypageBoardProgramList}">
					<tr>
						<td colspan="7">등록된 글이 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="mypageBoardProgramList" items="${mypageBoardProgramList}">
						<tr>
							<td class="valueHiddenView" id="file_name" >${mypageBoardProgramList.mateImage}</td> <!-- 숨기는 값 -->
							<td>${mypageBoardProgramList.write_no}</td>
							<td><a href="${contextPath}/programBoard/programContentView?write_no=${mypageBoardProgramList.write_no}&num=${num}&programBoardNum=${programBoardCheckNum }">${mypageBoardProgramList.title }</a></td>
							<td>${mypageBoardProgramList.id}</td>
							<td>${mypageBoardProgramList.savedate}</td>
							<td>${mypageBoardProgramList.state}</td>
							<td>${mypageBoardProgramList.hit}</td>
							<td>${mypageBoardProgramList.currentRegisterCount} / ${mypageBoardProgramList.totalRegisterCount}</td>
						</tr>
						<tr>
							<th> id </th>
							<th colspan="5"> 결재 상황 </th>
							<th>취소 승인 버튼</th>
						</tr>
							<c:forEach var="paidProgramInfoDTO" items="${mypageBoardProgramList.paidProgramInfoDTO }">
								<tr>
									<td>${paidProgramInfoDTO.id }</td>
									<td colspan="5" id=" ${paidProgramInfoDTO.tid}">
									<c:if test="${paidProgramInfoDTO.cancel_request == 'Y'}">
			      						취소 요청
			      					</c:if>
			      					<c:if test="${paidProgramInfoDTO.cancel_request == 'N'}">
			      						결재 완료
			      					</c:if>
			      					<c:if test="${paidProgramInfoDTO.cancel_request == 'P'}">
			      						관리자 취소 승인 대기
			      					</c:if>
			      					</td>
			      					<td>
			      						<c:if test="${paidProgramInfoDTO.cancel_request == 'Y'}">
			      							<button onclick="confirmToCancel('${paidProgramInfoDTO.tid}')">취소 승인</button>
			      						</c:if>
			      						<c:if test="${paidProgramInfoDTO.cancel_request == 'N'}">
			      							<p>결재 완료한 고객입니다</p>
			      						</c:if>
			      						<c:if test="${paidProgramInfoDTO.cancel_request == 'P'}">
			      							<p> 결채 취소 완료시 삭제 예정</p>
			      						</c:if>
			      					</td>
								</tr>
							</c:forEach>
						
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>	
			<div class="page_wrap">
				 <div class="page_nation">
					<c:if test="${startPage > block }">
						<a href="${contextPath }/mypageBoard/write/mypageBoardProgramWriteList?num=${startPage-1 }"  class="arrow prev"> 이전 </a>
							</c:if>
								<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
									<c:if test="${i == num}">
										<a href="${contextPath }/mypageBoard/write/mypageBoardProgramWriteList?num=${i }" class="active"> ${i } </a>
									</c:if>
									<c:if test="${i != num}">
										<a href="${contextPath }/mypageBoard/write/mypageBoardProgramWriteList?num=${i }" class="no_active"> ${i } </a>
									</c:if>
								</c:forEach>
							<c:if test="${endPage < totalPage }">
						<a href="${contextPath }/mypageBoard/write/mypageBoardProgramWriteList?num=${endPage+1 }" class="arrow next" > 다음 </a>
					</c:if>
				</div>
			</div>
			<input type="button" value="게시판 게시글"onclick="location.href='${contextPath }/mypageBoard/write/mypageBoardWriteList'" class="but_1">			
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