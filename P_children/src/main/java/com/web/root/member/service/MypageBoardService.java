package com.web.root.member.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface MypageBoardService {

	// (커뮤니티) 마이페이지 작성글 목록(아이디, 모델, 페이지번호)
	public void mypageBoardCommunityWriteList(String id, Model m, int num);
	
	// (프로그램) 마이페이지 작성글 목록(아이디, 모델, 페이지번호) 
	public void mypageBoardProgramWriteList(String id, Model m, int num);

	// ======================================= 박성수 시작 =================================================
	public void selectPaidProgramList(String id, int pg_num, Model model);

	public String updateCancelRequest(HttpServletRequest request);
	
	
	// ======================================= 박성수 시작 =================================================
	
	
	
	
}
