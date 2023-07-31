<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 작성한 게시글 보기</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=3" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/page.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
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
 #write_no{
 	display: none;
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
		<div class="title" >게시판 게시글</div>
		<table>
			<!-- 제목 칼럼 -->
		<thead>	
			<tr>
				<th>구 분</th>
				<th>제 목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>		
			</tr>
		</thead>			
			<!-- // 제목 칼럼 끝 -->
			
			<!-- 내용 시작 -->
				<c:choose>
					<c:when test="${empty mypageBoardList}">
						<tr>
							<td colspan="5">등록된 글이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="mypageBoardList" items="${mypageBoardList}">
							<tr>
								<td class="valueHiddenView" id="write_no">${mypageBoardList.write_no}</td> <!-- 숨기는 값 -->
								<td class="valueHiddenView" id="file_name">${mypageBoardList.file_name}</td> <!-- 숨기는 값 -->
								<td>
									<c:if test="${mypageBoardList.category == 'informationSharing' }">
									 정보 공유 
									</c:if>
									<c:if test="${mypageBoardList.category == 'friendshipPromotion' }">
									 친목 도모 
									</c:if>
									<c:if test="${mypageBoardList.category == 'petSneak' }">
									 펫 간식 
									</c:if>
									<c:if test="${mypageBoardList.category == 'smallChat' }">
									 잡담 
									</c:if>
									<c:if test="${mypageBoardList.category == 'lookForPetFriend' }">
									 펫프랜드 구합니다 
									</c:if>
									<c:if test="${mypageBoardList.category == 'BeingPetFriend' }">
									 펫프랜드 합니다 
									</c:if>
								</td>
								<td><a href="${contextPath }/board/contentView?write_no=${mypageBoardList.write_no}&num=${num}&boardNum=${myBoardCheckNum }">${mypageBoardList.title }</a></td>
								<td>${mypageBoardList.id}</td>
								<td>${mypageBoardList.savedate}</td>
								<td>${mypageBoardList.hit}</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
		</table>	
		<!-- 테이블 끝 -->
		
			<div class="page_wrap">
			   <div class="page_nation">
						<c:if test="${startPage > block }">
							<a href="${contextPath }/mypageBoard/write/mypageBoardWriteList?num=${startPage-1 }" class="arrow prev"> 이전 </a>
						</c:if>
						<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
							<c:if test="${i == num}">
								<a href="${contextPath }/mypageBoard/write/mypageBoardWriteList?num=${i }" class="active"> ${i } </a>
							</c:if>
							<c:if test="${i != num}">
								<a href="${contextPath }/mypageBoard/write/mypageBoardWriteList?num=${i }" class="no_active"> ${i } </a>
							</c:if>
						</c:forEach>
						<c:if test="${endPage < totalPage }">
							 <a href="${contextPath }/mypageBoard/write/mypageBoardWriteList?num=${endPage+1 }" class="arrow next" > 다음 </a>
						</c:if>
					</div>
				</div>
			<c:if test="${info.userSelect == 'host' }">
				<input type="button" value="프로그램 작성글"onclick="location.href='${contextPath }/mypageBoard/write/mypageBoardProgramWriteList'" class="but_1">			
			</c:if>
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