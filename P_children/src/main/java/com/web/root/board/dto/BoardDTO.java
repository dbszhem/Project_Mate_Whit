package com.web.root.board.dto;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/*
CREATE TABLE board(
write_no NUMBER(10) PRIMARY KEY,
category VARCHAR2(100),
title VARCHAR2(100),
content VARCHAR2(300),
savedate DATE DEFAULT SYSDATE,
hit NUMBER(10) DEFAULT 0,
file_name VARCHAR2(100),
id VARCHAR2(20) NOT NULL,
);
 */

public class BoardDTO {
	
	
	// 속성(property)
	private int write_no;
	private String category;
	private String title;
	private String content;
	private String savedate;
	private int hit;
	private String file_name;
	private String id;
	
	//==== 06.16 윤희 추가 =====
	private int boardNum = 2;	// DB에 컬럼 없음
	private int start;			// DB에 컬럼 없음
	private int end;			// DB에 컬럼 없음
	//==== 06.16 윤희 끝 =====
	
	//==== 06.16 진욱 추가 =====
	private int dibsCount;		// DB에 컬럼 없음
	//==== 06.14 진욱 끝 =====
	
	// 생성자
	public BoardDTO() {
		super();
	}
	
	public BoardDTO(int write_no, String category, String title, String content, String savedate, int hit, String file_name, String id,int dibsCount) {
		super();
		this.write_no = write_no;
		this.category = category;
		this.title = title;
		this.content = content;
		this.savedate = savedate;
		this.hit = hit;
		this.file_name = file_name;
		this.id = id;
		this.dibsCount =dibsCount;
	}

	// getter와 setter
	public int getWrite_no() {
		return write_no;
	}
	public void setWrite_no(int write_no) {
		this.write_no = write_no;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setSavedate(Timestamp savedate) {
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		this.savedate = format.format(savedate);
	}
	public String getSavedate() {
		return savedate;
	}
//	public void setSavedate(String savedate) {
//		this.savedate = savedate;
//	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	//==== 06.15 윤희 추가 =====

	public int getBoardNum() {
		return boardNum;
	}
	
	public void setBoardNum(int boardNum) {
		this.boardNum = 2;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	//==== 윤희 끝 =====

	//==== 06.16 진욱 추가 =====
	public int getDibsCount() {
		return dibsCount;
	}

	public void setDibsCount(int dibsCount) {
		this.dibsCount = dibsCount;
	}
	//==== 진욱 끝 =====	
	
	
	

}
