
/* board writeForm 입력 확인 */
function checkBoardWrite() {
	if(document.boardWriteForm.title.value == ""){
		alert("제목을 입력해주세요");
		document.boardWriteForm.title.focus();
	}
	else if(document.boardWriteForm.category.value == ""){
		alert("카테고리를 입력해주세요");
		document.boardWriteForm.category.focus();
	}
	else if(document.boardWriteForm.content.value == ""){
		alert("내용을 입력해주세요");
		document.boardWriteForm.content.focus();
	}
	else if(document.boardWriteForm.file.value == ""){
		if(!confirm("파일 첨부를 하지 않았지만 제출하시겠습니까?")){
			document.boardWriteForm.file.focus();
			return false;
		} else {
			document.boardWriteForm.submit();
		}
		
	}
	else {
		document.boardWriteForm.submit();
	}
	
}

/* board modifyForm 입력 확인 */
function checkBoardModify() {
	if(document.boardModifyForm.title.value == ""){
		alert("제목을 입력해주세요");
		document.boardModifyForm.title.focus();
	}
	else if(document.boardModifyForm.category.value == ""){
		alert("카테고리를 입력해주세요");
		document.boardModifyForm.category.focus();
	}
	else if(document.boardModifyForm.content.value == ""){
		alert("내용을 입력해주세요");
		document.boardModifyForm.content.focus();
	}
	else if(document.boardModifyForm.file.value == ""){
		if(!confirm("파일을 수정하지 않고 제출하시겠습니까?")){
			document.boardModifyForm.file.focus();
			return false;
		} else {
			document.boardModifyForm.submit();
		}
	}
	else {
		document.boardModifyForm.submit();
	}
	
}

/* program writeForm 입력 확인 */
function checkProgramWrite() {
	if(document.programWriteForm.title.value == ""){
		alert("프로그램 이름을 입력해주세요");
		document.programWriteForm.title.focus();
	}
	else if(document.programWriteForm.petKind.value == ""){
		alert("견종을 선택해주세요");
		document.programWriteForm.petKind.focus();
	}
	else if(document.programWriteForm.mateName.value == ""){
		alert("강아지 이름을 입력해주세요");
		document.programWriteForm.mateName.focus();
	}
	else if(document.programWriteForm.position1.value == ""){
		alert("위치1을 입력해주세요");
		document.programWriteForm.position1.focus();
	}
	else if(document.programWriteForm.position2.value == ""){
		alert("위치2를 입력해주세요");
		document.programWriteForm.position2.focus();
	}
	else if(document.programWriteForm.content.value == ""){
		alert("내용을 입력해주세요");
		document.programWriteForm.content.focus();
	}
	else if(document.programWriteForm.startDate.value == ""){
		alert("시작 날짜를 입력해주세요");
		document.programWriteForm.startDate.focus();
	}
	else if(document.programWriteForm.endDate.value == ""){
		alert("종료 날짜를 입력해주세요");
		document.programWriteForm.endDate.focus();
	}
	else if(document.programWriteForm.startTime.value == ""){
		alert("시작 시간을 입력해주세요");
		document.programWriteForm.startTime.focus();
	}
	else if(document.programWriteForm.endTime.value == ""){
		alert("종료 시간을 입력해주세요");
		document.programWriteForm.endTime.focus();
	}
	else if(document.programWriteForm.priceForProgram.value == ""){
		alert("프로그램 비용을 입력해주세요");
		document.programWriteForm.priceForProgram.focus();
	}
	else if(document.programWriteForm.file.value == ""){
		if(!confirm("강아지 사진을 업로드하지 않고 제출하시겠습니까?")){
			document.programWriteForm.file.focus();
			return false;
		} else {
			document.programWriteForm.submit();
		}
	}
	else {
		document.programWriteForm.submit();
	}	
}

/* program modifyForm 입력 확인 */
function checkProgramModify() {
	if(document.modifyProgramForm.title.value == ""){
		alert("프로그램 이름을 입력해주세요");
		document.modifyProgramForm.title.focus();
	}
	else if(document.modifyProgramForm.petKind.value == ""){
		alert("견종을 선택해주세요");
		document.modifyProgramForm.petKind.focus();
	}
	else if(document.modifyProgramForm.mateName.value == ""){
		alert("강아지 이름을 입력해주세요");
		document.modifyProgramForm.mateName.focus();
	}
	else if(document.modifyProgramForm.position1.value == ""){
		alert("위치1을 입력해주세요");
		document.modifyProgramForm.position1.focus();
	}
	else if(document.modifyProgramForm.position2.value == ""){
		alert("위치2를 입력해주세요");
		document.modifyProgramForm.position2.focus();
	}
	else if(document.modifyProgramForm.content.value == ""){
		alert("내용을 입력해주세요");
		document.modifyProgramForm.content.focus();
	}
	else if(document.modifyProgramForm.startDate.value == ""){
		alert("시작 날짜를 입력해주세요");
		document.modifyProgramForm.startDate.focus();
	}
	else if(document.modifyProgramForm.endDate.value == ""){
		alert("종료 날짜를 입력해주세요");
		document.modifyProgramForm.endDate.focus();
	}
	else if(document.modifyProgramForm.startTime.value == ""){
		alert("시작 시간을 입력해주세요");
		document.modifyProgramForm.startTime.focus();
	}
	else if(document.modifyProgramForm.endTime.value == ""){
		alert("종료 시간을 입력해주세요");
		document.modifyProgramForm.endTime.focus();
	}
	else if(document.modifyProgramForm.priceForProgram.value == ""){
		alert("프로그램 비용을 입력해주세요");
		document.modifyProgramForm.priceForProgram.focus();
	}
	else if(document.modifyProgramForm.state.value == ""){
		alert("현황을 입력해주세요");
		document.modifyProgramForm.state.focus();
	}
	else if(document.modifyProgramForm.file.value == ""){
		if(!confirm("강아지 사진을 수정하지 않고 제출하시겠습니까?")){
			document.modifyProgramForm.file.focus();
			return false;
		} else {
			document.modifyProgramForm.submit();
		}
	}
	else {
		document.modifyProgramForm.submit();
	}	
}