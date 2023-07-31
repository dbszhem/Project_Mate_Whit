<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 호스트 인증 신청</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=1" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/page.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script type="text/javascript">
	/*
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
	*/
	
	// 인증 신청 자료 확인 -> 인증 신청 완료
	function hostCertificationCheck(){
		if(document.hostCertificationForm.file_name.value == ""){
			alert('인증 신청 자료를 첨부하세요');
			document.hostCertificationForm.file_name.focus();
		} else {
			if(!confirm('HOST 인증 신청을 하시겠습니까?')){
				return false;
			} else {
				document.hostCertificationForm.submit();
			}	
		}
	}
	
	
	
</script>
</head>
<body>
	
	
	<!-- 회원 정보(메이트 네임 등등) 불러와지고 인증 서류(ex. pdf파일, 이미지 등을 받아야한다.) -->
	<h1>Mate With HOST 인증 신청</h1>
	
	<h4>동물보호관리시스템 홈페이지에서 동물등록증과 함께 반려견의 정기검진 등 해당 자료를 첨부해주세요</h4>
	<p>** 해당 자료를 확인하여 인증 절차를 위한 화상 채팅 또는 반려 전문가의 방문이 있어 접수 후 약 3-5일 소요됩니다. **</p>
	<p>** 첨부파일 형식: pdf, jpg, png 파일로 첨부 가능합니다. **</p>
	<form name="hostCertificationForm" action="${contextPath}/mypageBoard/certified/hostCertification" enctype="multipart/form-data" method="post">
		<input type="hidden" name="id" value="${userid }"> <!-- 아이디값 저장 -->
		<!-- <img src="#" id="preview" width="200px" height="150px"><br><br> 미리보기 -->
		<input type="file" name="file_name" onchange="readURL(this)"/><br><br>
		<input type="button" onclick="hostCertificationCheck();" value="신청하기">
	</form>
	
	<a href="${contextPath}/member/member_information">마이페이지로 가기</a>
</body>
</html>