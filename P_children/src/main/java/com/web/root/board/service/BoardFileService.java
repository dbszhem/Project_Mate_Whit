package com.web.root.board.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.web.root.member.dto.HostCertifyDTO;

public interface BoardFileService {
	
	//============================ 주진욱 시작 ===========================================

	// 파일 저장 위치
	public static final String IMAGE_REPO = "C:\\mateImage";
	
	public String getMessage(HttpServletRequest request, String msg, String url);
	
	public String saveFile(MultipartFile file);
	
	public void deleteFile(String file_name);
	
	//============================ 주진욱 끝 ===========================================
	
	
	
	//============================ 최윤희 시작 ===========================================
	
	// 공지 게시판 파일 저장 위치
	public static final String IMAGE_NOTICE_REPO = "C:\\mateImage";
	
	// 파일 이름 저장
	public String noticeBoardSaveFile(MultipartFile file);
	
	// 메시지 출력
	public String getNoticeBoardMessage(HttpServletRequest request, String msg, String url);
	
	// 파일 삭제
	public void noticeBoardDeleteFile(String file_name);
	
	//============================ 최윤희 끝 ===========================================
}
