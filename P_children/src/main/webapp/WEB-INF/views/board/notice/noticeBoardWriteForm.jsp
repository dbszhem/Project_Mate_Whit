<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 공지사항 작성</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script src="${contextPath }/resources/yoonhee/js/noticeBoardScript.js?v=1"></script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=1" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/page.css?v=1" rel="stylesheet" type="text/css">
<script type="text/javascript">
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
</script>
    <script>
        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function(e) {
                    var preview = document.getElementById("preview");
                    preview.src = e.target.result;
                    document.getElementById("preview_tr").style.display = ""; // 이미지 미리보기 행 보이기
                };

                reader.readAsDataURL(input.files[0]);
            } else {
                document.getElementById("preview_tr").style.display = "none"; // 이미지 미리보기 행 숨기기
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
 th { background-color: #C19681; 
 width: auto;}
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
  	width: 100%;
    border: none;
    padding: 0px 25px;
  }
#notice_category{
	border: none;
	width: 100%;
	text-align: center;
}
#preview_tr{
	
}
a{
	text-decoration-line: none;
}
</style>
</head>
</head>
<body>

	<c:import url="../../default/header.jsp"/>
	
		<section ><!-- body -->
			<div class="form-box-list"> <!--  container  -->
			<div class="title" >공지사항 작성</div>
			<form name="noticeBoardWriteForm" action="${contextPath }/board/notice/noticeBoardWriteSave" enctype="multipart/form-data" method="post">
			<table>
				<tr>
					<th>제목</th>
						<td><input type="text" name="title"><input type="hidden" name="id" value="${user }" readonly></td>
					<th>구분</th>
						<td>
						<div class="selcet">				
							<select name="category" id="notice_category">
								<option value="" selected> 구분 선택 </option>
								<option value="noticeGeneral">일반</option>
								<option value="noticeEvent">이벤트</option>
								<option value="noticeProduct">상품</option>
								<option value="noticeDeliveryDelay">배송지연</option>
							</select>
							</div>
						</td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3"><textarea name="content" rows="10" cols="50"></textarea></td>
				</tr>
				<tr>
					<th >이미지</th>
					<td ><input type="file" name="file" onchange="readURL(this)"/></td>
				</tr>
		        <tr id="preview_tr" style="display: none">
		            <td colspan="4"><img src="" id="preview" width="500px" height="250px"></td>
		        </tr>
			</table>
			</form>
						<input type="button" onclick="noticeBoardWriteFormCheck()" value="완료" class="but_1">
			<ul class="menu">
				<li><a href="">메뉴</a>
					<ul class="submenu_">
						<hr>
						<c:choose>
							<c:when test="${notice_category != null}">
								<li><a
									href="${contextPath }/board/notice/noticeSearchForm?num=${num}&notice_category=${notice_category }&notice_searchCategory=${notice_searchCategory}&notice_searchKeyword=${notice_searchKeyword}">목록</a></li>
							</c:when>
							<c:otherwise>
								<li><a
									href="${contextPath }/board/notice/noticeBoardAllList?num=${num}">목록</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</li>
			</ul>
		</div>
	</section>
	
	<c:import url="../../default/footer.jsp"/>

</body>
</html>