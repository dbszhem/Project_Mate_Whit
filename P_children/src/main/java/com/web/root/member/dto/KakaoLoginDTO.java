package com.web.root.member.dto;

public class KakaoLoginDTO {
	private String kakaoId;
	private String kakaoLoginEmail;
	
	public KakaoLoginDTO() {
		super();
	}
	
	public KakaoLoginDTO(String kakaoId, String kakaoLoginEmail) {
		this.kakaoId = kakaoId;
		this.kakaoLoginEmail = kakaoLoginEmail;
	}
	
	public String getKakaoId() {
		return kakaoId;
	}
	public void setKakaoId(String kakaoId) {
		this.kakaoId = kakaoId;
	}
	public String getKakaoLoginEmail() {
		return kakaoLoginEmail;
	}
	public void setKakaoLoginEmail(String kakaoLoginEmail) {
		this.kakaoLoginEmail = kakaoLoginEmail;
	}
	
	
}
