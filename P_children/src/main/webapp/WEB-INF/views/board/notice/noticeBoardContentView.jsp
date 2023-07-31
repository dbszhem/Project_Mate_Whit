<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 공지사항</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=2" rel="stylesheet" type="text/css">
<script type="text/javascript">

	// 파일 업로드 시 img 태그에 그림 화면 보이기
	function readURL(input){
		var file = input.files[0]; // 파일 정보
		if(file != ""){
			var reader = new FileReader();
			reader.readAsDataURL(file); // 파일 정보 읽어오기
			reader.onload = function(e){ // 읽기에 성공하면 결과 표시
				$("#preview").attr("src", e.target.result)
			}
			
		}
	}
	
	// 게시글 삭제
	function noticeDeleteConfirm() {
		
		if(!confirm('삭제하시겠습니까?')){
			return false;
		} else {
			location.href='${contextPath }/board/notice/noticeBoardDelete?write_no=${noticeBoardDTO.write_no }&file_name=${noticeBoardDTO.file_name }';
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
   textarea{
  	padding: 25px;
  	width: 100%;
  	height: 250px;
	resize: none;
	border: none;
  }
    th{ 
    padding-left:8px;
    padding-right: 8px;
    text-align:center;
    }  
    td{ 
    padding-left:8px;
    padding-right: 8px;
    text-align:left;
    }  
 th { background-color: #C19681;
 width: auto;}
  tbody tr     { 
  height:40px; 
  border-bottom:1px solid white;
    &:last-child  { border:0; }
  }
  a{
	text-decoration-line: none;
}
</style>
</head>
<body onload="replyData()">

	<c:import url="../../default/header.jsp"/>
	
		<section ><!-- body -->
			<div class="form-box-list"> <!--  container  -->
			<div class="title" >공지 사항</div>
			<table>
				<tr>
					<th>제목</th>
						<td>${noticeBoardDTO.title }</td>
					<th>분류</th>
						<td>				
							<c:if test="${noticeBoardDTO.category == 'noticeGeneral' }"> 일반 </c:if>
							<c:if test="${noticeBoardDTO.category == 'noticeEvent' }"> 이벤트 </c:if>
							<c:if test="${noticeBoardDTO.category == 'noticeProduct' }"> 상품 </c:if>
							<c:if test="${noticeBoardDTO.category == 'noticeDeliveryDelay' }"> 배송지연 </c:if>
						</td>
				</tr>
				<tr>
					<th>글번호</th>
						<td>${noticeBoardDTO.write_no }</td>				
					<th>작성일</th>
						<td>${noticeBoardDTO.savedate }</td>
			   </tr>
			   	<tr>
					<th>작성자</th>
					<td colspan="3">${noticeBoardDTO.id }</td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3" ><textarea name="content" rows="10" cols="50" readonly>${noticeBoardDTO.content }</textarea></td>
				</tr>
				<tr>
					<th>이미지</th>
					<td colspan="3">
						<c:if test="${noticeBoardDTO.file_name == 'nan'}">
							<p>이미지가 없습니다...</p>
						</c:if>
						<c:if test="${noticeBoardDTO.file_name != 'nan'}">
							<img src="${contextPath }/board/notice/noticeBoardDownload?file_name=${noticeBoardDTO.file_name}" width="200px" height="200px">
						</c:if>						
					</td>
				</tr>
			</table>
				<c:if test="${info.grade != admin }">
				<ul class="menu">
			      <li>
			        <a href="#">메뉴</a>
			        <ul class="submenu_">
			         <hr>
			         <c:choose>
				         <c:when test="${notice_category != null}">
				          	<li><a href="${contextPath }/board/notice/noticeSearchForm?num=${num}&notice_category=${notice_category }&notice_searchCategory=${notice_searchCategory}&notice_searchKeyword=${notice_searchKeyword}">목록</a></li>
						 </c:when>
						<c:otherwise>			          
			          	<li><a href="${contextPath }/board/notice/noticeBoardAllList?num=${num}">목록</a></li>
			          	</c:otherwise>
			          </c:choose>	
			        </ul>
			      	</li>
			    </ul> 
				</c:if>	
				<c:if test="${info.grade == admin }">
				<ul class="menu">
			      <li>
			        <a href="#">메뉴</a>
			        <hr>
			        <ul class="submenu">
				         <c:choose>
					         <c:when test="${notice_category != null}">
					          	<li><a href="${contextPath }/board/notice/noticeSearchForm?num=${num}&notice_category=${notice_category }&notice_searchCategory=${notice_searchCategory}&notice_searchKeyword=${notice_searchKeyword}">목록</a></li>
							 </c:when>
							<c:otherwise>			          
				          	<li><a href="${contextPath }/board/notice/noticeBoardAllList?num=${num}">목록</a></li>
				          	</c:otherwise>
				          </c:choose>	
				          <hr>
				          <li><a href="${contextPath }/board/notice/noticeBoardModifyForm?write_no=${noticeBoardDTO.write_no }">수정</a></li>
				          <hr>
				          <li><a href="${contextPath }/board/notice/noticeBoardDelete?write_no=${noticeBoardDTO.write_no }&file_name=${noticeBoardDTO.file_name }">삭제</a></li>
				        </ul>
				      </li>
			    	</ul> 
				</c:if>	
		</div>
	</section>
	
	<c:import url="../../default/footer.jsp"/>

</body>
</html>