<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 지도로 프로그램 보기</title>
<script src="https://code.jquery.com/jquery-3.6.4.js"
	integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E="
	crossorigin="anonymous"></script>
</head>
<body>
	<div id="map" style="width: 100%; height: 1000px;"></div>

	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1859b62b2237a4f3505f28f840501ac6&libraries=clusterer"></script>
	<script>
	
		const map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
			center : new kakao.maps.LatLng(37.52678413940727, 126.570667), // 지도의 중심좌표
			level : 11
		// 지도의 확대 레벨 
		});
		const data = ${kakaoMapLatLng};
		

		for(let i=0 ; i<data.positions.length ; i++){
		
			let write_no = data.positions[i].write_no;
			let title = data.positions[i].title;
			let lat = data.positions[i].lat;
			let lng = data.positions[i].lng;
			
			// 마커가 표시될 위치입니다 
			let markerPosition  = new kakao.maps.LatLng(lat, lng); 

			// 마커를 생성합니다
			let marker = new kakao.maps.Marker({
			    position: markerPosition
			});

			// 마커가 지도 위에 표시되도록 설정합니다
			marker.setMap(map);

			// 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
			let iwContent = '<a href="/root/programBoard/programContentView?write_no='+write_no+'&num=1"'
						   +' style="font-size:15px; color:blue; text-decoration:none;" target="_blank">'+title+'</a>'; 
			// 진욱님 - 프로그램 제목, write_no, num 값을 programLatLng 테이블에 함께 저장해야 한다.
			
			let iwPosition = new kakao.maps.LatLng(lat, lng); //인포윈도우 표시 위치입니다

			// 인포윈도우를 생성합니다
			let infowindow = new kakao.maps.InfoWindow({
			    position : iwPosition, 
			    content : iwContent 
			});
			  
			// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
			infowindow.open(map, marker); 
		}
		
	</script>
</body>
</html>