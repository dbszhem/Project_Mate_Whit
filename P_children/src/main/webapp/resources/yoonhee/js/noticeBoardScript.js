/**
 * 
 */
 
 // 공지사항 게시글 수정하기
 function noticeBoardModifyFormCheck(){
	if(document.noticeBoardModifyForm.category.value == ""){
		alert("카테고리를 선택해주세요");
		document.noticeBoardModifyForm.category.focus();
	}
	else if(document.noticeBoardModifyForm.title.value == ""){
		alert("제목을 입력해주세요");
		document.noticeBoardModifyForm.title.focus();
	}
	else if(document.noticeBoardModifyForm.content.value == ""){
		alert("내용을 입력해주세요");
		document.noticeBoardModifyForm.content.focus();
	}
	else {
		document.noticeBoardModifyForm.submit();
	}
 }
 
 
  // 공지사항 게시글 작성
 function noticeBoardWriteFormCheck(){
	if(document.noticeBoardWriteForm.category.value == ""){
		alert("카테고리를 선택해주세요");
		document.noticeBoardWriteForm.category.focus();
	}
	else if(document.noticeBoardWriteForm.content.value == ""){
		alert("제목을 입력해주세요");
		document.noticeBoardWriteForm.title.focus();
	}
	else if(document.noticeBoardWriteForm.content.value == ""){
		alert("내용을 입력해주세요");
		document.noticeBoardWriteForm.content.focus();
	}
	else {
		document.noticeBoardWriteForm.submit();
	}
 }
 
 
 
 // 공지사항 카테고리 분류
 /*
 function noticeCategoryDivision(){
 	let noticeCategorySelect = document.getElementById('noticeCategoryDivision');
 	let noticeCategoryOption = noticeCategorySelect.options[noticeCategorySelect.selectedIndex].value;
 	
 	console.log(noticeCategoryOption);
 }
 */
 
 
 
 
 
 
 
 