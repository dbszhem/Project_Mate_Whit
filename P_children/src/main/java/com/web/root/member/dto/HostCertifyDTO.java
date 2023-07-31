package com.web.root.member.dto;

/*
CREATE TABLE hostCertify(
certify_no NUMBER(10) PRIMARY KEY,
id NVARCHAR2(30),
file_name NVARCHAR2(100),
savedate DATE DEFAULT SYSDATE
);
*/

public class HostCertifyDTO {
	private int certify_no;
	private String id;
	private String file_name;
	private String state;
	private String savedate;
	
	public int getCertify_no() {
		return certify_no;
	}
	public void setCertify_no(int certify_no) {
		this.certify_no = certify_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getSavedate() {
		return savedate;
	}
	public void setSavedate(String savedate) {
		this.savedate = savedate;
	}
	
}
