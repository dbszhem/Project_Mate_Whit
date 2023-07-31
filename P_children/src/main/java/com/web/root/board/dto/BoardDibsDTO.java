package com.web.root.board.dto;

/*
CREATE TABLE dibs(
id NVARCHAR2(15) NOT NULL,
write_no NUMBER(10) NOT NULL,
dibs_no NUMBER(10) NOT NULL,
dibs_state NUMBER(2) DEFAULT 1,
CONSTRAINT fk_test FOREIGN KEY(id) REFERENCES member(id) ON DELETE CASCADE,
CONSTRAINT fk_test2 FOREIGN KEY(write_no) REFERENCES board2(write_no) ON DELETE CASCADE
);
 */
public class BoardDibsDTO {

	// 속성 property
	private String id;
	private int write_no;
	private int dibs_no;
	private int dibs_state;
	
	// 생성자
	public BoardDibsDTO() {
		super();
	}
	
	public BoardDibsDTO(String id, int write_no, int dibs_no, int dibs_state) {
		super();
		this.id = id;
		this.write_no = write_no;
		this.dibs_no = dibs_no;
		this.dibs_state = dibs_state;
	}
	
	
	// getter와 setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getWrite_no() {
		return write_no;
	}
	public void setWrite_no(int write_no) {
		this.write_no = write_no;
	}
	public int getDibs_no() {
		return dibs_no;
	}
	public void setDibs_no(int dibs_no) {
		this.dibs_no = dibs_no;
	}
	public int getDibs_state() {
		return dibs_state;
	}
	public void setDibs_state(int dibs_state) {
		this.dibs_state = dibs_state;
	}
	
}
