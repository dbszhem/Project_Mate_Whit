package com.web.root.board.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.root.board.dto.PaidProgramInfoDTO;
import com.web.root.board.dto.ProgramBoardDTO;

public interface BoardForProgramService {

	public String writeSaveForProgram(MultipartHttpServletRequest mul, HttpServletRequest request);
	
	public void programBoardAllList(Model model, int num, HttpServletRequest request);
	
	public ProgramBoardDTO programContentView(Model model, HttpServletRequest request);
	
	public void programHitplus(ProgramBoardDTO programBoardDTO);
	
	public String modifySaveForProgram(MultipartHttpServletRequest mul, HttpServletRequest request);
	
	public String deleteProgram(Model model, HttpServletRequest request);
	
	public String paidProgramContentView(HttpServletRequest request, HttpSession session);

	public String kakaoPayFail(HttpServletRequest request);
	
	// 프로그램 게시판 카테고리 + 검색 조회
	public void programBoardSearchForm(String programBoard_state,String programBoard_searchCategory,String programBoard_searchKeyword,Model model,int num);

	// 프로그램 게시판 결재 보드 관련 기능 -----------------------------------
	public List<PaidProgramInfoDTO> paidProgramInfoByWriteNo(Map<String, Object> map);
	
	public List<PaidProgramInfoDTO> paidProgramInfoById(String id);
	
	// 프로그램 게시판 찜하기 관련 기능 ---------------------------------------
	
	public void myDibsProgramBoardAllList(Model model, int num, HttpServletRequest request, String id);
	
	// 호스트의 마이페이지에서 프로그램 결재 취소 요청 보내기 기능
	public int paidCancelRequestByTid(String tid);
	
	
	//========================== 윤희 시작 보류(확인 후 삭제될 수 있습니다.) =============================
	
	// 마이페이지 프로그램 게시글 정보 필요
	public ProgramBoardDTO programBoardInformation(String id);
	
	//========================== 윤희 끝 ===============================
	
	}
