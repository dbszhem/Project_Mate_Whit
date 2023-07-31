<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 프로그램 게시판</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=5" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/page.css?v=6" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function deleteConfirm(write_no, file_name){
		
		if(!confirm('삭제하시겠습니까?')){
			return false;
		} else {
			location.href='${contextPath}/programBoard/deleteProgram?write_no=' + write_no + '&file_name=' + file_name;
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
    th{
    background-color: #C19681;
    }

  thead tr  { 
    height:40px;
    background:#A996DB;
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
#programBoard_state {
	margin: 5px;
}
#programBoard_searchCategory{
	margin: 5px;
}
#programBoard_searchKeyword{
	margin: 5px;
}
#search_btn{
	margin: 5px;
}
.map{
	text-align: right;
	margin: 10px;
}
.map button {
	width: 15%;
	height: 30px;
    background: linear-gradient( 25deg, #C19681 50%, #A16F63);
    color: black;
    border:none;
    border-radius:10px;	
}
</style>
</head>
<body>

	<c:import url="../default/header.jsp"/>
	
		<section ><!-- body -->
			<div class="form-box-list"> <!--  container  -->
			<div class="title" >프로그램 게시판</div>
			<div class="map">
			<button id="programMap" onclick="location.href='kakaoMapLatLng_json'">지도에서 프로그램 찾기</button>
			</div>
			<table >
			<thead>
				<tr>
						<th> 번 호 </th>
						<th> 작성자</th>
						<th> 제 목</th>						
						<th>작성일</th>
						<th>예약 현황 ( 신청인원 / 총원 )</th>
						<th>조회수</th>
						<th><img width="18px" height="18px" src="https://cdn-icons-png.flaticon.com/512/138/138533.png?w=826&t=st=1686704293~exp=1686704893~hmac=6f355d28e7dbaf3380f00e77d046efe85cf73ab4f5d2adcf464457a3b814b714"></th>
						<c:if test ="${info.grade == admin}">
						<th>관리자 권한</th>
						</c:if>
				</tr>
			</thead>
			<c:choose>
				<c:when test="${empty programBoardList}"> <!-- ${boardList.size() == 0} -->
					<tr>
						<td colspan="7">등록된 글이 없습니다.</td>
					</tr>	
				</c:when>
				<c:otherwise>
					<c:forEach var="dto" items="${programBoardList }">
							<tr>
								<td>${dto.write_no }</td>
								<td>${dto.id }</td>                                       
								<td>
									<c:choose>
										<c:when test="${programBoard_state == null}">
											<a href="${contextPath }/programBoard/programContentView?write_no=${dto.write_no}&num=<%=request.getParameter("num")%>">${dto.title }</a>
										</c:when>
										<c:otherwise>
											<a href="${contextPath }/programBoard/programContentView?write_no=${dto.write_no}&num=<%=request.getParameter("num")%>&programBoard_state=${programBoard_state }&programBoard_searchCategory=${programBoard_searchCategory}&programBoard_searchKeyword=${programBoard_searchKeyword}">${dto.title }</a>
										</c:otherwise>
									</c:choose>
								</td>
								<td>${dto.savedate }</td>
								<td>${dto.state } &nbsp; ${dto.currentRegisterCount } / ${dto.totalRegisterCount }</td>
								<td>${dto.hit }</td>
								<td>${dto.dibsCount }</td>
								<c:if test ="${info.grade == admin}">
									<td><button onclick="deleteConfirm('${dto.write_no}', '${dto.mateImage }')">삭제</button></td>
								</c:if>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			</table>
			<!--  host 글작성 -->
			<div class="button">
				<c:if test="${info.userSelect == 'host'}">
					<input type="button" value="글작성" onclick="location.href='${contextPath }/programBoard/writeFormForProgram'" class="but_2">
				</c:if>			
			</div>
					
			<!-- 검색 -->
			<div class="page_wrap">
				<form name="programBoardSearchForm" action="${contextPath }/programBoard/programBoardSearchForm" method="get">
					<select name="programBoard_state" id="programBoard_state">
						<option value="total" <c:if test="${programBoard_state == 'total' }">selected</c:if>>전체</option>
	               		<option value="예약가능" <c:if test="${programBoard_state == '예약가능' }">selected</c:if>>예약 가능</option> <!-- 초기값 -->
	               		<option value="승인대기" <c:if test="${programBoard_state == '승인대기' }">selected</c:if>>승인 대기</option> <!-- 승인대기는 필요없을 것 같습니다..? -->
	               		<option value="결재완료" <c:if test="${programBoard_state == '결재완료' }">selected</c:if>>결재 완료</option> <!-- 자신이 결재 했을 때 받는 것은 결재 완료 -->
	               		<option value="예약완료" <c:if test="${programBoard_state == '예약완료' }">selected</c:if>>예약 완료</option> <!-- 참여 인원이 꽈 차면 예약 완료 --> 
					</select>
					<select name="programBoard_searchCategory" id="programBoard_searchCategory">
						<option value="title" <c:if test="${programBoard_searchCategory == 'title' }">selected</c:if>>제목</option>
						<option value="content" <c:if test="${programBoard_searchCategory == 'content' }">selected</c:if>>내용</option>
						<option value="mateName" <c:if test="${programBoard_searchCategory == 'mateName' }">selected</c:if>>강아지 이름</option>
						<option value="id" <c:if test="${programBoard_searchCategory == 'id' }">selected</c:if>>작성자</option>
					</select>
					<input type="text" id="programBoardSearchForm_searchKeyword" name="programBoard_searchKeyword" value="${programBoard_searchKeyword }">
					<input type="submit" id="search_btn" value="검색">
				</form>
			</div>
			<!-- 페이징 -->
			<c:if test="${programBoard_state == null}">
			<div  class="page_wrap">
					<div class="page_nation">
							<c:if test="${startPage > block }">
								<a href="programBoardAllList?num=${startPage-1 }" class="arrow prev"> 이전 </a>
							</c:if>
							<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
								<c:if test="${i == num}">
									<a href="programBoardAllList?num=${i }" class="active"> ${i } </a>
								</c:if>
								<c:if test="${i != num}">
									<a href="programBoardAllList?num=${i }" class="no_active"> ${i }  </a>
								</c:if>
							</c:forEach>
							<c:if test="${endPage < totalPage }">
								<a href="programBoardAllList?num=${endPage+1 }" class="active"> 다음 </a>
							</c:if>
					</div>
				</div>
			</c:if>
			<!-- 검색 페이징-->
			<c:if test="${programBoard_state != null }">
					<div  class="page_wrap">
						<div class="page_nation">	
										<c:if test="${startPage > block }">
											 <a href="${contextPath }/programBoard/programBoardSearchForm?num=${startPage-1 }&programBoard_state=${programBoard_state }&programBoard_searchCategory=${programBoard_searchCategory}&programBoard_searchKeyword=${programBoard_searchKeyword}" class="arrow prev"> 이전 </a> 
										</c:if>
										<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
											<c:if test="${i == num}">
												 <a href="${contextPath }/programBoard/programBoardSearchForm?num=${i }&programBoard_state=${programBoard_state }&programBoard_searchCategory=${programBoard_searchCategory}&programBoard_searchKeyword=${programBoard_searchKeyword}" class="active"> ${i } </a> 
											</c:if>
											<c:if test="${i != num}">
												 <a href="${contextPath }/programBoard/programBoardSearchForm?num=${i }&programBoard_state=${programBoard_state }&programBoard_searchCategory=${programBoard_searchCategory}&programBoard_searchKeyword=${programBoard_searchKeyword}" class="no_active"> ${i } </a> 
											</c:if>
										</c:forEach>
										<c:if test="${endPage < totalPage }">
											 <a href="${contextPath }/programBoard/programBoardSearchForm?num=${endPage+1 }&programBoard_state=${programBoard_category }&programBoard_searchCategory=${programBoard_searchCategory}&programBoard_searchKeyword=${programBoard_searchKeyword}" class="arrow next"> 다음 </a> 
								</c:if>
						</div>
					</div>
			</c:if>
		</div>
	</section>

	<c:import url="../default/footer.jsp"/>

</body>
</html>