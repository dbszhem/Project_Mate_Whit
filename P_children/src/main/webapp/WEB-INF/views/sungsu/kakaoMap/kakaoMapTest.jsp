<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Kakao 지도 시작하기</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body>
	<div id="map" style="width: 500px; height: 400px;"></div>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1859b62b2237a4f3505f28f840501ac6"></script>
	<script>
		const container = document.getElementById('map');
		const options = {
			center : new kakao.maps.LatLng(37.5448262908682, 127.03915242535567),
			level : 10
		};

		const map = new kakao.maps.Map(container, options);
		
		const clusterer = new kakao.maps.MarkerClusterer({
	        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
	        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
	        minLevel: 10 // 클러스터 할 최소 지도 레벨 
	    });
		
		
	</script>
	
	
	<form action="kakaoMapGetLatLng" method="post">
		<div class="input-box" >
			<span class="details">주소</span>
			<input type="text" id="addr2" name="addr2" placeholder="상세주소" oninput="deleteSpan_addr2()">
			<span id="addr_"></span>
		</div>
		<div class="input-box-addr">
		</div><br>
		<input type="submit" value="좌표 찾기">
	</form>
	<br>
	<a href="kakaoMapLatLng_json">프로그램 좌표에 뿌리기</a>
</body>
</body>
</html>





