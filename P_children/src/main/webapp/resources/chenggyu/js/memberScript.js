
/* memberScript.js */

// 입력정보 확인
function checkModify(){
	 if(document.member_modify.pwd.value != document.member_modify.ck_pwd.value){
		alert("비밀번호를 확인하세요");
		document.member_modify.ck_pwd.focus();
	}
	else if(document.member_modify.re_pwd.value != document.member_modify.re_pwd_ck.value){
		alert("새로운 비밀번호가 일치하지 않습니다");
		document.member_modify.re_pwd.focus();
	}
	else if(document.member_modify.email.nickname == ""){
		alert("닉네임을 확인하세요");
		document.member_modify.nickname.focus();
	}
	else if(document.member_modify.phone.value == ""){
		alert("휴대전화 번호를 확인하세요");
		document.member_modify.phone.focus();
	}
	else if(document.member_modify.addr.value == ""){
		alert("주소를 입력하세요..");
		document.member_modify.addr.focus();
	}
	else {
		document.member_modify.submit();
	}
}

function leave(){
	if(document.member_leave.pwd.value == ""){
		alert("비밀번호를 확인해주세요");
		document.memberLoginForm.pwd.focus();
	}
	else{
		window.location.href = "${contextPath }/root/index" ;
		document.member_modify.submit();
	}
}	

