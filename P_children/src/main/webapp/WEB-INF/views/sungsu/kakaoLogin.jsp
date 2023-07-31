<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카카오 로그인 페이지</title>
<style>
#kakao-login-btn-img {
	width: 308px;
	height: 40px;
	border-radius: 40px;
	position: absolute;
	left: 45px;
	top: 320px;	
}
</style>
</head>
<body>
	<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.2.0/kakao.min.js"
		integrity="sha384-x+WG2i7pOR+oWb6O5GV5f1KN2Ko6N7PTGPS7UlasYWNxZMKQA63Cj/B2lbUmUfuC"
		crossorigin="anonymous"></script>
	<script>
		Kakao.init('50cc79dc82404d34d4da829c82e31cd2');  // 사용하려는 앱의 RestAPI 키 입력
	</script>

	<a id="kakao-login-btn" href="javascript:loginWithKakao()"> <img id="kakao-login-btn-img"
		src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
		alt="카카오 로그인 버튼" />
	</a>
	<p id="token-result"></p>

	<script>
		function loginWithKakao() {
			Kakao.Auth.authorize({
				redirectUri : 'http://localhost:8080/root/member/kakaoCode',
			});
		}
	</script>
</body>
</html>