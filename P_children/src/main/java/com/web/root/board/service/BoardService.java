package com.web.root.board.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.root.board.dto.BoardDTO;
import com.web.root.board.dto.BoardDibsDTO;
import com.web.root.board.dto.BoardRepDTO;
import com.web.root.board.dto.NoticeBoardDTO;
import com.web.root.board.dto.PaidProgramInfoDTO;
import com.web.root.qna.dto.QnaDTO;
import com.web.root.qna.dto.Qna_RepDTO;


public interface BoardService {
	
	//============================ 주진욱 시작 ===========================================
	
	//public static final String IMAGE_REPO = "C:\\0900_JAVA_jjw\\6. Spring\\image-repo";
	//public void boardAllList(Model model);
	
	public void boardAllList(Model model, int num, HttpServletRequest request);
	
	public String writeSave(MultipartHttpServletRequest mul, 
			HttpServletRequest request);
	
	public BoardDTO contentView(Model model, HttpServletRequest request);
	
	public void hitplus(BoardDTO dto);
	
	public String modifySave(MultipartHttpServletRequest mul, 
			HttpServletRequest request);
	
	public String deleteBoard(Model model, HttpServletRequest request);
	
	public void selectingCategory(Model model,String category, int num);
	
	public void boardSearchForm(String board_category,String board_searchCategory,String board_searchKeyword,Model model,int num);
	// 댓글 기능 ----------------------------------------------------------
	public int addReply(Map<String, Object> map);
	
	public List<BoardRepDTO> getRepList(int write_group);
	
	public String deleteReply(HttpServletRequest request);
	
	public String updateReply(HttpServletRequest request);

	// 답글(대댓글) 기능 -------------------------------------------------
	public List<BoardRepDTO> getReCommentList(int reply_no);
	
	public int addReComment(Map<String, Object> map);
	
	public String updateReComment(HttpServletRequest request);
	
	// 찜하기 기능 -------------------------------------------------
	public int toggleDibs(Map<String, Object> map);
	
	public BoardDibsDTO getDibsByIdWriteNo(Map<String, Object> map);
	
	public void myDibsBoardAllList(Model model, int num, HttpServletRequest request, String id);
	
	public int getdibsNumByWriteNo(int write_no);
		
	//============================ 주진욱 끝 ===========================================
	
	
	
	
	//============================ 최윤희 시작 ===========================================
	
	// 공지사항 게시판 리스트
	public void noticeBoardAllList(Model model, int num, HttpServletRequest request); 
	
	// 공지사항 게시글 보기
	public NoticeBoardDTO noticeBoardContentView(HttpServletRequest request);
	
	// 조회수 증가	
	public void noticeBoardHitplus(NoticeBoardDTO noticeBoardDTO);
	
	// 공지사항 게시글 작성
	public String noticeBoardWriteSave(MultipartHttpServletRequest mul, HttpServletRequest request); 
	
	// 공지사항 게시글 수정
	public String noticeBoardModifySave(MultipartHttpServletRequest mul, HttpServletRequest request);
	
	// 공지사항 게시글 삭제
	public String noticeBoardDelete(HttpServletRequest request);
		
	// 공지사항 카테고리 + 검색 조회
	public void noticeSearchForm(@Param("notice_category") String notice_category, 
								 @Param("notice_searchCategory") String notice_searchCategory, 
								 @Param("notice_searchKeyword") String notice_searchKeyword, Model m, int num);
	
	//============================ 최윤희 끝 ===========================================
	
	// 청규
	
	// 문의 관리
	public void manager_qna(Model model, int num);
	
	// 문의 게시판
	public void member_qna(Model model, int num, String id);
	
	// 글 저장
	public void member_write_save(HttpServletRequest request);
	
	public QnaDTO contentView_qna(Model model, HttpServletRequest request);
	
	public Qna_RepDTO contentView_rep_qna(Model model, HttpServletRequest request);
	
	public void  manager_write_save(HttpServletRequest request, Model model);
	
	public void qna_state(HttpServletRequest request);
	
}

