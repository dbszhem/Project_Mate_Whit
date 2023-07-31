package com.web.root.board.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class NoticeBoardDTO {

	// 속성(property)
		private int write_no;
		private String category;
		private String title;
		private String content;
		private String savedate;
		private int hit;
		private String file_name;
		private String id;
		private String keyword;		// DB 컬럼에는 없음
		private int start;			// DB 컬럼에는 없음
		private int end;			// DB 컬럼에는 없음
		
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
//		public void setSavedate(String savedate) {
//			this.savedate = savedate;
//		}
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
		
		
		public String getKeyword() {
			return keyword;
		}
		public void setKeyword(String keyword) {
			this.keyword = keyword;
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
		
		
		
		
}
