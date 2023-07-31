
<!-- board/myDibsBoard.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 내가 찜한 게시글</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=2" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/page.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function chooseBoard() {
		
		if($("#selectBoard").val() =='내가찜한게시글') {
			location.href="${contextPath}/mypageBoard/mypageDibsBoard?num=1"
		}
		if($("#selectBoard").val() =='내가찜한프로그램게시글') {
			location.href = "${contextPath}/mypageBoard/mypageDibsProgramBoard?num=1";
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
  .select{
  	border-radius: 10px;
  	text-align: right;
  	margin: 5px;
  }
</style>
</head>
<body>

	<c:import url="../../default/header.jsp"/>
	
	<section>
		<div class="form-box-list"> 
			<div class="title" >찜한 프로그램</div>
			<div class="select">
			<select name="selectBoard" id="selectBoard" onchange="chooseBoard()">
				<option value="내가찜한프로그램게시글" checked>내가찜한프로그램게시글</option>
				<option value="내가찜한게시글">내가찜한게시글</option>
			</select>
			<!-- 내가 찜한 프로그램 게시판 -->
			</div>
			<div class="wrap board_table myDibsProgramBoard">
				<table class="table table-striped">
					<thead>			
						<tr>
							<th width="70px"> 번 호 </th>
							<th width="100px"> ID </th>
							<th width="200px"> 제 목 </th>
							<th width="200px"> 날 짜 </th>
							<th width="200px"> 예약현황 <br>(현재 신청인원 / 총원) </th>
							<th width="70px"> 조회수 </th>
							<th width="70px"> <img width="30px" height="30px" src="https://cdn-icons-png.flaticon.com/512/138/138533.png?w=826&t=st=1686704293~exp=1686704893~hmac=6f355d28e7dbaf3380f00e77d046efe85cf73ab4f5d2adcf464457a3b814b714"> </th>
							<c:if test ="${info.grade == admin}">
											<th>관리자 권한</th>
							</c:if>
						</tr>
					</thead>
					<c:choose>
						<c:when test="${empty programBoardList}"> <!-- ${programBoardList.size() == 0} -->
							<tr>
								<td colspan="7">등록된 글이 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="dto" items="${programBoardList }">
								<tr>
									<td>${dto.write_no }</td>
									<td>${dto.id }</td>                                       
									<td><a href="${contextPath }/programBoard/programContentView?write_no=${dto.write_no}&num=<%=request.getParameter("num")%>&toMyDibsProgramBoard=yes">${dto.title }</a></td>
									<td>${dto.savedate }</td>
									<td>${dto.state } &nbsp; ${dto.currentRegisterCount } / ${dto.totalRegisterCount }</td>
									<td>${dto.hit }</td>
									<td>${dto.dibsCount }</td>
									<c:if test ="${info.grade == admin}">
										<td><button onclick="deleteConfirm('${dto.write_no}', '${dto.mateImage }')">삭제</button></td>
									</c:if>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</table>
								
						<!-- 페이징 -->
					<div class="page_wrap">
					   <div class="page_nation">						
								<c:if test="${startPage > block }">
										 <a href="${contextPath }/mypageBoard/mypageDibsProgramBoard?num=${startPage-1 }" class="arrow prev"> 이전 </a> 
									</c:if>
									<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
										<c:if test="${i == num}">
											 <a href="${contextPath }/mypageBoard/mypageDibsProgramBoard?num=${i }" class="active"> ${i } </a> 
										</c:if>
										<c:if test="${i != num}">
											 <a href="${contextPath }/mypageBoard/mypageDibsProgramBoard?num=${i }" class="active"> ${i } </a> 
										</c:if>
									</c:forEach>
									<c:if test="${endPage < totalPage }">
										 <a href="${contextPath }/mypageBoard/mypageDibsProgramBoard?num=${endPage+1 }" class="arrow next"> 다음 </a> 
									</c:if>
							</div>
						</div>
						<!-- 페이징 끝 -->
					<ul class="menu">
						<li><a href="">메뉴</a>
							<ul class="submenu_">
								<hr>
								<li><a href="${contextPath }/member/member_information">이전</a></li>
							</ul>
						</li>
					</ul>							
			</div>
		</div>
	</section>
	
	<c:import url="../../default/footer.jsp"/>
	
</body>
</html>