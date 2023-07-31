<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<title>카카오페이 결제 승인 목록 페이지</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=1" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/page.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function choosePage() {
		
		if($("#categoryOfKakaoPayment").val() =='카카오페이관리') {
			location.href="${contextPath}/member/kakaoPaymentApproveList"
		}
		if($("#categoryOfKakaoPayment").val() =='결재취소요청관리') {
			location.href = "${contextPath}/member/kakaoPaymentCancelRequestList";
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

  thead tr  { 
    height:40px;
    background: #C19681;
    font-size:16px;
  }
  
  tbody tr     { 
  height:40px; 
  border-bottom:1px solid white;
    &:last-child  { border:0; }
  }
</style>
</head>
<body>

	<c:import url="../default/header.jsp"/>
	
		<section ><!-- body -->
		<div class="form-box-list"> <!--  container  -->
		<div class="title" >카카오페이 관리</div>
		<select name="categoryOfKakaoPayment" id="categoryOfKakaoPayment" onchange="choosePage()">
			<option value="카카오페이관리">카카오페이관리</option>
			<option value="결재취소요청관리">결재취소요청관리</option>
		</select>
		<table>
		<thead>
			<tr>
					<th>cid</th>
					<th>tid</th>
					<th>item_name</th>
					<th>quantity</th>
					<th>approved_at</th>
			</tr>
		</thead>
				<c:if test="${kakaoPaymAppList.size() == 0 }">
					<tr>
						<th colspan="5">없음</th>
					</tr>
				</c:if>
			<c:forEach var="KPAL" items="${kakaoPaymAppList }">
					<tr>
						<td>${KPAL.cid }</td>
						<td><a href="kakaoPaymentOrderInfo?cid=${KPAL.cid }&tid=${KPAL.tid }">${KPAL.tid }</a><span  style="color:red;">${KPAL.cancelCheck }</span></td>
						<td>${KPAL.item_name }</td>
						<td>${KPAL.quantity }</td>
						<td>${KPAL.approved_at }</td>
					</tr>
				</c:forEach>
		</table>
			<div class="page_wrap">
			   <div class="page_nation">
			      <!-- <a class="arrow pprev" href="#"></a> -->
			      	<c:if test="${startPage > block }">
							<a href="kakaoPaymentApproveList?num=${startPage-1 }"   class="arrow prev"> 이전 </a>
						</c:if>
						<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
							<c:if test="${i == num}">
								<a href="kakaoPaymentApproveList?num=${i }"  class="active"> ${i } </a>
							</c:if>
							<c:if test="${i != num}">
								<a href="kakaoPaymentApproveList?num=${i }" class="no_active"> ${i } </a>
							</c:if>
						</c:forEach>
						<c:if test="${endPage < totalPage }">
							<a href="kakaoPaymentApproveList?num=${endPage+1 }" class="arrow next" > 다음 </a>
						</c:if>
			      <!-- <a class="arrow nnext" href="#"></a> -->
			   </div>
			</div>		
			<ul class="menu">
				<li><a href="">메뉴</a>
					<ul class="submenu_">
						<hr>
						<li><a href="${contextPath }/member/manager_information">이전</a></li>
					</ul>
				</li>
			</ul>	
		</div>
	</section>
		
	<c:import url="../default/footer.jsp"/>
	
</body>
</html>