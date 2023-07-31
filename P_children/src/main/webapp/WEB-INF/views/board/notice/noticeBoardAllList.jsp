<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 공지게시판</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script src="${contextPath }/resources/yoonhee/js/noticeBoardScript.js?v=1"></script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=2" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/page.css?v=4" rel="stylesheet" type="text/css">
<script type="text/javascript">
	// 게시글 삭제 문구 알림창
	function noticeBoardDeleteConfirm(write_no, file_name) {
		
		if(!confirm('삭제하시겠습니까?')){
			return false;
		} else {
			location.href='${contextPath }/board/notice/noticeBoardDelete?write_no='+write_no +'&file_name='+file_name;
		}
		
	}
	
	// 게시글 수정 문구 알림
	function noticeBoardModifyConfirm(write_no) {
		
		if(!confirm('수정하시겠습니까?')){
			return false;
		} else {
			location.href='${contextPath }/board/notice/noticeBoardModifyForm?write_no='+write_no;
		}
		
	}
	
	
	// 카테고리 값 전달
	function noticeCategoryChange(){
		document.categorySelect.submit();
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
#notice_category {
	margin: 5px;
}
#notice_searchCategory{
	margin: 5px;
}
#notice_searchKeyword{
	margin: 5px;
}
#search_btn{
	margin: 5px;
}
</style>
</head>
<body>

	<c:import url="../../default/header.jsp"/>
	
		<section ><!-- body -->
			<div class="form-box-list"> <!--  container  -->
			<div class="title" >공지 게시판</div>
			<table >
			<thead>
				<tr>
						<th> 분 류 </th>
						<th> 제 목</th>
						<th> 작성자</th>						
						<th>작성일</th>
						<th>조회수</th>
						<c:if test ="${info.grade == admin}">
						<th>관리자 권한</th>
						</c:if>
				</tr>
			</thead>
			<c:choose>
				<c:when test="${empty noticeBoardList}"> <!-- ${boardList.size() == 0} -->
					<tr>
						<td colspan="6">등록된 글이 없습니다.</td>
					</tr>	
				</c:when>
				<c:otherwise>
					<c:forEach var="noticeBoardDTO" items="${noticeBoardList }">
						<tr>
							<td class="valueHiddenView" id="write_no">${noticeBoardDTO.write_no}</td><!-- 값만 받아오기 위해 숨김 -->
							<td class="valueHiddenView" id="file_name">${noticeBoardDTO.file_name }</td><!-- 값만 받아오기 위해 숨김 -->
							<!-- 카테고리 구분 -->
							<td>
								<c:if test="${noticeBoardDTO.category == 'noticeGeneral' }">
								 일반 
								</c:if>
								<c:if test="${noticeBoardDTO.category == 'noticeEvent' }">
								 이벤트 
								</c:if>
								<c:if test="${noticeBoardDTO.category == 'noticeProduct' }">
								 상품 
								</c:if>
								<c:if test="${noticeBoardDTO.category == 'noticeDeliveryDelay' }">
								 배송지연 
								</c:if>
							</td>
							<c:choose>
								<c:when test="${notice_category == null}">
									<td><a href="${contextPath }/board/notice/noticeBoardContentView?write_no=${noticeBoardDTO.write_no}&num=${num}">${noticeBoardDTO.title }</a></td>
								</c:when>
								<c:otherwise>
									<td><a href="${contextPath }/board/notice/noticeBoardContentView?write_no=${noticeBoardDTO.write_no}&num=${num}&notice_category=${notice_category }&notice_searchCategory=${notice_searchCategory}&notice_searchKeyword=${notice_searchKeyword}">${noticeBoardDTO.title }</a></td>
								</c:otherwise>
							</c:choose>
							<td>${noticeBoardDTO.id }</td>
							<td>${noticeBoardDTO.savedate }</td>
							<td>${noticeBoardDTO.hit }</td>
							<c:if test ="${info.grade == admin}">
								<td>
									<button onclick="noticeBoardDeleteConfirm('${noticeBoardDTO.write_no}','${noticeBoardDTO.file_name }')">삭제</button>
									<button onclick="noticeBoardModifyConfirm('${noticeBoardDTO.write_no }')">수정</button>
								</td>
							</c:if>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			</table>
			<!--  관리자 글 작성 -->
			<div class="button">
				<c:if test="${info.grade == admin}">
					<input type="button" value="글작성" onclick="location.href='noticeBoardWriteForm'" class="but_2">
				</c:if>			
			</div>		
			<!-- 검색 -->
			<div  class="page_wrap">
			<form name="noticeSearchForm" action="${contextPath }/board/notice/noticeSearchForm" method="get">
				<select name="notice_category" id="notice_category">
					<option value="noticeAll" <c:if test="${notice_category == 'noticeAll' }">selected</c:if>>전체</option>
					<option value="noticeGeneral" <c:if test="${notice_category == 'noticeGeneral' }">selected</c:if>>일반</option>
					<option value="noticeEvent" <c:if test="${notice_category == 'noticeEvent' }">selected</c:if>>이벤트</option>
					<option value="noticeProduct" <c:if test="${notice_category == 'noticeProduct' }">selected</c:if>>상품</option>
					<option value="noticeDeliveryDelay" <c:if test="${notice_category == 'noticeDeliveryDelay' }">selected</c:if>>배송지연</option>
				</select>
				<select name="notice_searchCategory" id="notice_searchCategory">
					<option value="title" <c:if test="${notice_searchCategory == 'title' }">selected</c:if>>제목</option>
					<option value="content" <c:if test="${notice_searchCategory == 'content' }">selected</c:if>>내용</option>
					<option value="id" <c:if test="${notice_searchCategory == 'id' }">selected</c:if>>작성자</option>
				</select>
				<input type="text" id="notice_searchKeyword" name="notice_searchKeyword" value="${notice_searchKeyword }">
				<input type="submit" id="search_btn" value="검색">
			</form>
			</div>
			<!-- 페이징 -->
			<c:if test="${notice_category == null}">
			<div  class="page_wrap">
					<div class="page_nation">
							<c:if test="${startPage > block }">
								<a href="${contextPath }/board/notice/noticeBoardAllList?num=${startPage-1 }" class="arrow prev"> 이전 </a>
							</c:if>
							<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
								<c:if test="${i == num}">
									<a href="${contextPath }/board/notice/noticeBoardAllList?num=${i }" class="active"> ${i } </a>
								</c:if>
								<c:if test="${i != num}">
									<a href="${contextPath }/board/notice/noticeBoardAllList?num=${i }" class="no_active"> ${i } </a>
								</c:if>
							</c:forEach>		
							<c:if test="${endPage < totalPage }">
								<a href="${contextPath }/board/notice/noticeBoardAllList?num=${endPage+1 }" class="arrow next" > 다음 </a>
							</c:if>
					</div>
				</div>
			</c:if>
					<!-- 검색 페이징 -->
					<c:if test="${notice_category != null }">
					<div  class="page_wrap">
						<div class="page_nation">	
								<c:if test="${startPage > block }">
									<a href="${contextPath }/board/notice/noticeSearchForm?num=${startPage-1 }&notice_category=${notice_category }&notice_searchCategory=${notice_searchCategory}&notice_searchKeyword=${notice_searchKeyword}" class="arrow prev"> 이전 </a>
								</c:if>
								<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
									<c:if test="${i == num}">
										<a href="${contextPath }/board/notice/noticeSearchForm?num=${i }&notice_category=${notice_category }&notice_searchCategory=${notice_searchCategory}&notice_searchKeyword=${notice_searchKeyword}" class="active"> ${i } </a>
									</c:if>
									<c:if test="${i != num}">
										<a href="${contextPath }/board/notice/noticeSearchForm?num=${i }&notice_category=${notice_category }&notice_searchCategory=${notice_searchCategory}&notice_searchKeyword=${notice_searchKeyword}" class="no_active"> ${i } </a>
									</c:if>
								</c:forEach>
								<c:if test="${endPage < totalPage }">
									<a href="${contextPath }/board/notice/noticeSearchForm?num=${endPage+1 }&notice_category=${notice_category }&notice_searchCategory=${notice_searchCategory}&notice_searchKeyword=${notice_searchKeyword}" class="arrow next" > 다음 </a>
								</c:if>
						</div>
					</div>	
				</c:if>			
		</div>
	</section>
	
	<c:import url="../../default/footer.jsp"/>
		
</body>
</html>