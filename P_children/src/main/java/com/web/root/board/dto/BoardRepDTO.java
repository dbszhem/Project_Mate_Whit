package com.web.root.board.dto;

import java.sql.Timestamp;

/*
CREATE TABLE reply (
reply_no NUMBER(10) PRIMARY KEY,
depth NUMBER(2),
cGroup NUMBER(10),
id VARCHAR2(20),
content VARCHAR2(300),
write_group NUMBER(10),
write_date DATE DEFAULT SYSDATE
);
 */
public class BoardRepDTO {

	
	// 속성(property)
	private int reply_no;
	private int depth;
	private int cGroup;
	private String id;
	private String content;
	private int write_group;
	private Timestamp write_date;
	
	// 생성자
	public BoardRepDTO() {
		super();
	}
	
	public BoardRepDTO(int reply_no, int depth, int cGroup, String id, String content, int write_group, Timestamp write_date) {
		super();
		this.reply_no = reply_no;
		this.depth = depth;
		this.cGroup = cGroup;
		this.id = id;
		this.content = content;
		this.write_group = write_group;
		this.write_date = write_date;
	}
	
	// getter와 setter
	public int getReply_no() {
		return reply_no;
	}

	public void setReply_no(int reply_no) {
		this.reply_no = reply_no;
	}
	
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public int getcGroup() {
		return cGroup;
	}

	public void setcGroup(int cGroup) {
		this.cGroup = cGroup;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getWrite_group() {
		return write_group;
	}

	public void setWrite_group(int write_group) {
		this.write_group = write_group;
	}

	public Timestamp getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Timestamp write_date) {
		this.write_date = write_date;
	}
	
	
	
	
	
	
}
