package com.web.root.kakao.resource;

public interface KakaoDeveloper {
	
	public static final String REST_API_KEY = "50cc79dc82404d34d4da829c82e31cd2";
	
	// 카카오 로그인 API
	public static final String tokenURL = "https://kauth.kakao.com/oauth/token";
	public static final String kakaoIdURL = "https://kapi.kakao.com/v2/user/me";
	public static final String kakaoLogoutURL = "https://kapi.kakao.com/v1/user/logout";

	
	// 카카오 페이 API
	public static final String ADMIN_KEY = "487a1c3ee2f111ea410da257fbcfadff";
	public static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
	public static final String KAKAO_PAY_READY_URL = "https://kapi.kakao.com/v1/payment/ready";
	// 카카오 페이 결제 실패
	public static final String KAKAO_PAYMENT_APPROVE_URL = "https://kapi.kakao.com/v1/payment/approve";
	// 카카오페이 결제 상세 조회
	public static final String KAKAO_PAYMENT_ORDER_URL = "https://kapi.kakao.com/v1/payment/order";
	// 카카오페이 결제 취소
	public static final String KAKAO_PAYMENT_CANCEL_URL = "https://kapi.kakao.com/v1/payment/cancel";
	
	
	// 카카오 맵
	public static final String SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";
	
	
	
	
	
	
}
