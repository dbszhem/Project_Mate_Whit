package com.web.root.member.dto;

public class MemberDTO {
	
	private String id;
	private String pwd;
	private String nickname;
	private String phone;
	private String email;
	private String addr;
	private String grade;
	private String userSelect;
	private String mateName;
	private String mateBreed;
	private String certified;
	
	public MemberDTO() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getUserSelect() {
		return userSelect;
	}
	public void setUserSelect(String userSelect) {
		this.userSelect = userSelect;
	}

	public String getMateName() {
		return mateName;
	}

	public void setMateName(String mateName) {
		this.mateName = mateName;
	}

	public String getMateBreed() {
		return mateBreed;
	}

	public void setMateBreed(String mateBreed) {
		this.mateBreed = mateBreed;
	}

	public String getCertified() {
		return certified;
	}

	public void setCertified(String certified) {
		this.certified = certified;
	}

	
	
}
