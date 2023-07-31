
<!-- board/modifyForm.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 자유게시판 글 수정하기</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script src="${contextPath}/resources/jinwook/js/boardScript.js?v=1"></script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=3" rel="stylesheet" type="text/css">
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
        window.onload = function() {
            var fileInput = document.getElementsByName("file")[0];
            var previewTr = document.getElementById("preview_tr");
            var noticeBoardDTOFileName = "${noticeBoardDTO.file_name}";

            if (noticeBoardDTOFileName !== "nan") {
                previewTr.style.display = ""; // 이미지 미리보기 행 보이기
            }

            fileInput.addEventListener("change", function() {
                if (fileInput.files && fileInput.files[0]) {
                    previewTr.style.display = ""; // 이미지 미리보기 행 보이기
                } else {
                    previewTr.style.display = "none"; // 이미지 미리보기 행 숨기기
                }
            });

            // 파일 선택 취소 시 동작
            fileInput.addEventListener("click", function() {
                fileInput.value = null; // 파일 선택 초기화
                previewTr.style.display = "none"; // 이미지 미리보기 행 숨기기
            });
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
#category{
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
<body>

	<c:import url="../default/header.jsp"/>
	
	<section ><!-- body -->
		<div class="form-box-list"> <!--  container  -->
			<div class="title" >자유게시판 글 수정</div>
				<form name="boardModifyForm" action="${contextPath }/board/modifySave?num=<%=request.getParameter("num") %>" enctype="multipart/form-data" method="post">
					<table>
						<tr>
							<th>제목</th>
								<td><input type="text" name="title" value="${dto.title }"><input type="hidden" name="write_no" value="${dto.write_no }"/></td>
							<th>분류 </th>
								<td>
								<div class="selcet">				
									<select name="category" id="category">
											<option value="informationSharing"> 정보 공유 </option>
											<option value="friendshipPromotion"> 친목 도모 </option>
											<option value="petSneak"> 펫 간식 </option>
											<option value="smallChat"> 잡담 </option>
											<option value="lookForPetFriend"> 펫프랜드 구합니다 </option>
											<option value="BeingPetFriend"> 펫프랜드 합니다 </option>
										</select>
									</div>
								</td>
						</tr>
						<tr>
							<th>내용</th>
							<td colspan="3"><textarea name="content" rows="10" cols="50">${dto.content }</textarea></td>
						</tr>
				        <tr>
				            <th>이미지</th>
				            <td><input type="file" name="file" onchange="readURL(this)"></td>
				        </tr>
				        <tr id="preview_tr" style="display: none;">
				            <td colspan="4"><img src="${contextPath }/board/download?file_name=${dto.file_name}" id="preview" width="100px" height="100px"></td>
				        </tr>
					</table>
					</form>
								<input type="button" value="수정하기" onclick="checkBoardModify()" value="완료" class="but_1"/>
						<ul class="menu">
					      <li>
					        <a href="">메뉴</a>
					        <ul class="submenu_">
						         <hr>
						          	<li><a href="javascript:history.back();">이전</a></li>
						        </ul>
						      </li>
					    </ul> 
				</div>
	</section>

	<c:import url="../default/footer.jsp"/>
	
</body>
</html>