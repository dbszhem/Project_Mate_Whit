
<!-- board/boardAllList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 게시판</title>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=3" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/page.css?v=4" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script type="text/javascript">
	//게시글 삭제 문구 알림창
	function deleteConfirm(write_no, file_name){
		
		if(!confirm('삭제하시겠습니까?')){
			return false;
		} else {
			location.href='${contextPath}/board/delete?write_no='+  write_no + '&file_name=' + file_name;
		}
		
	}
	
	// 로그인 요청 알림창
	function loginPlease(){
		alert("로그인 후 기능 이용 가능합니다.");
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
  .valueHiddenView {
	display: none;
}
#right{
	text-align: right;
}
#board_category {
	margin: 5px;
}
#board_searchCategory{
	margin: 5px;
}
#board_searchKeyword{
	margin: 5px;
}
#search_btn{
	margin: 5px;
}
</style>
</head>
<body onload="colorConfirm()">
	
		<c:import url="../default/header.jsp"/>
		
		<section ><!-- body -->
			<div class="form-box-list"> <!--  container  -->
				<div class="title" >자유 게시판 </div>
				<table >
				<thead>
					<tr>
							<th> 분 류</th>
							<th> 제 목 </th>
							<th>작성자</th>
							<th> 작성일 </th>
							<th> 조회수 </th>
							<th><img width="18px" height="18px"  src="https://cdn-icons-png.flaticon.com/512/138/138533.png?w=826&t=st=1686704293~exp=1686704893~hmac=6f355d28e7dbaf3380f00e77d046efe85cf73ab4f5d2adcf464457a3b814b714"></th>
							<c:if test ="${info.grade == admin}">
							<th>관리자 권한</th>
							</c:if>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${empty boardList}">
						<tr>
							<td colspan="7">등록된 글이 없습니다.</td>
						</tr>	
					</c:when>
					<c:otherwise>
							<c:forEach var="dto" items="${boardList }">
								<tr>
									<td>
										<c:if test="${dto.category == 'informationSharing' }">
										 정보 공유 
										</c:if>
										<c:if test="${dto.category == 'friendshipPromotion' }">
										 친목 도모 
										</c:if>
										<c:if test="${dto.category == 'petSneak' }">
										 펫 간식 
										</c:if>
										<c:if test="${dto.category == 'smallChat' }">
										 잡담 
										</c:if>
										<c:if test="${dto.category == 'lookForPetFriend' }">
										 펫프랜드 구합니다 
										</c:if>
										<c:if test="${dto.category == 'BeingPetFriend' }">
										 펫프랜드 합니다 
										</c:if>
									</td>
									<c:choose>
										<c:when test="${board_category == null}">
											<td><a href="${contextPath }/board/contentView?write_no=${dto.write_no}&num=<%=request.getParameter("num")%>">${dto.title }</a></td>
										</c:when>
										<c:otherwise>
											<td><a href="${contextPath }/board/contentView?write_no=${dto.write_no}&num=<%=request.getParameter("num")%>&board_category=${board_category }&board_searchCategory=${board_searchCategory}&board_searchKeyword=${board_searchKeyword}">${dto.title }</a></td>
										</c:otherwise>
									</c:choose>
									<td>${dto.id }</td>
									<td>${dto.savedate }</td>
									<td>${dto.hit }</td>
									<td>${dto.dibsCount }</td>
									<c:if test ="${info.grade == admin}">
										<td><button onclick="deleteConfirm('${dto.write_no}', '${dto.file_name }')">삭제</button></td>
									</c:if>
								</tr>
							</c:forEach>
					</c:otherwise>
				</c:choose>
				</table>
				<div class="button">
					<c:choose>
						<c:when test="${id == null && info.id == null}">
							<input type="button" value="글작성" onclick="loginPlease()" class="but_2">
						</c:when>
						<c:otherwise>
							<input type="button" value="글작성" onclick="location.href='${contextPath }/board/writeForm'" class="but_2">
						</c:otherwise>
					</c:choose>
				</div>
			<!-- 검색 -->
			<div  class="page_wrap">
			<form name="boardSearchForm" action="${contextPath }/board/boardSearchForm" method="get">
				<select name="board_category" id="board_category">
					<option value="total" <c:if test="${board_category == 'total' }">selected</c:if>>전체</option>
					<option value="informationSharing" <c:if test="${board_category == 'informationSharing' }">selected</c:if>>정보 공유</option>
					<option value="friendshipPromotion" <c:if test="${board_category == 'friendshipPromotion' }">selected</c:if>>친목 도모</option>
					<option value="petSneak" <c:if test="${board_category == 'petSneak' }">selected</c:if>>펫 간식</option>
					<option value="smallChat" <c:if test="${board_category == 'smallChat' }">selected</c:if>>잡담</option>
					<option value="lookForPetFriend" <c:if test="${board_category == 'lookForPetFriend' }">selected</c:if>>펫 프랜드 구합니다</option>
					<option value="BeingPetFriend" <c:if test="${board_category == 'BeingPetFriend' }">selected</c:if>>펫 프랜드 합니다</option>
				</select>
				<select name="board_searchCategory" id="board_searchCategory">
					<option value="title" <c:if test="${board_searchCategory == 'title' }">selected</c:if>>제목</option>
					<option value="content" <c:if test="${board_searchCategory == 'content' }">selected</c:if>>내용</option>
					<option value="id" <c:if test="${board_searchCategory == 'id' }">selected</c:if>>작성자</option>
				</select>
				<input type="text" id="board_searchKeyword" name="board_searchKeyword" value="${board_searchKeyword }">
				<input type="submit" id="search_btn" value="검색">
				</form>
			</div>				
			<!-- 페이징 -->
			<c:if test="${board_category == null}">
			<div  class="page_wrap">
					<div class="page_nation">
								<c:if test="${startPage > block }">
									<a href="${contextPath }/board/boardAllList?num=${startPage-1 }" class="arrow prev"> 이전 </a>
								</c:if>
								<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
									<c:if test="${i == num}">
										<a href="${contextPath }/board/boardAllList?num=${i }" class="active"> ${i } </a>
									</c:if>
									<c:if test="${i != num}">
										<a href="${contextPath }/board/boardAllList?num=${i }" class="no_active" > ${i } </a>
									</c:if>
								</c:forEach>
								<c:if test="${endPage < totalPage }">
									<a href="${contextPath }/board/boardAllList?num=${endPage+1 }" class="arrow next" > 다음 </a>
								</c:if>
					</div>				
				</div>
			</c:if>	
				<!-- 검색 페이징 -->
				<c:if test="${board_category != null }">
					<div  class="page_wrap">
						<div class="page_nation">
								<c:if test="${startPage > block }">
									 <a href="${contextPath }/board/boardSearchForm?num=${startPage-1 }&board_category=${board_category }&board_searchCategory=${board_searchCategory}&board_searchKeyword=${board_searchKeyword}" class="arrow prev"> 이전 </a> 
								</c:if>
								<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
									<c:if test="${i == num}">
										 <a href="${contextPath }/board/boardSearchForm?num=${i }&board_category=${board_category }&board_searchCategory=${board_searchCategory}&board_searchKeyword=${board_searchKeyword}" class="active"> ${i } </a> 
									</c:if>
									<c:if test="${i != num}">
										 <a href="${contextPath }/board/boardSearchForm?num=${i }&board_category=${board_category }&board_searchCategory=${board_searchCategory}&board_searchKeyword=${board_searchKeyword}" class="no_active"> ${i } </a> 
									</c:if>
								</c:forEach>
								<c:if test="${endPage < totalPage }">
									 <a href="${contextPath }/board/boardSearchForm?num=${endPage+1 }&board_category=${board_category }&board_searchCategory=${board_searchCategory}&board_searchKeyword=${board_searchKeyword}" class="arrow next"> 다음 </a> 
								</c:if>
						</div>
					</div>	
				</c:if>						
			</div>	
		</section>
		
		<c:import url="../default/footer.jsp"/>
	
</body>
</html>