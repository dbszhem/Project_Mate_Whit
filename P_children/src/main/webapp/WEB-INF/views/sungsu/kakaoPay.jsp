<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath }"/>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결제 페이지</title>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css?v=2" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/chenggyu/login.css?v=2" rel="stylesheet" type="text/css">
</head>
<body>

	<c:import url="../default/header.jsp"/>
	
	<section>
		<div class="form-box_">
		<form action="${path }/member/kakaoPay">
				<div class="inputbox">
					프로그램명 <input type="text" id="itemName" name="item_name" value="${title }" readonly/>									
				</div>
				<div class="inputbox">
					상품 수량 <input type="text" id="quantity" name="quantity" value="${quantity }" readonly/>
				</div>
				<div class="inputbox">
					상품 비과세 총액 <input type="text" id="totalAmount" name="total_amount" value="${total_amount }" readonly/>
				</div>
				<div class="inputbox">
					상품 비과세 금액 <input type="text" id="taxFreeAmount" name="tax_free_amount" value="${tax_free_amount }" readonly/>
				</div>
				<input type="hidden" name="write_no" value="${write_no }">
	       		 <input type="hidden" name="num" value="${num }">								
				 <input type="submit" id="payment" name="payment"  class="but_1" value="결제하기"/>
			</form>
		</div>
	</section>
	
   <c:import url="../default/footer.jsp"/>
   
</body>
</html>



