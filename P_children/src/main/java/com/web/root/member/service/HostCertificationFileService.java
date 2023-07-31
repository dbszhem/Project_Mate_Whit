package com.web.root.member.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

public interface HostCertificationFileService {

	// host 파일 저장 위치
	public static final String IMAGE_HOST_CERTIFICATION_REPO = "C:\\Users\\92779\\git\\P_Children\\P_children\\src\\main\\webapp\\resources\\image\\hostCertificationDocument";
	// 진욱 임시 수정 (06/21)
	//public static final String IMAGE_HOST_CERTIFICATION_REPO = "C:\\Users\\ju\\git\\P_Children\\P_children\\src\\main\\webapp\\resources\\image\\hostCertificationDocument";
	// 임시 수정 끝
	// host 인증 파일 저장
	public String hostCertificationSaveFile(MultipartFile file);
	
	// host 인증 파일 됐는지 메시지 출력
	public String getHostCertificationMessage(HttpServletRequest request, String msg, String url);
	
	// host 인증 신청 거절하면 제출서류 삭제
	//public void hostCertificationDeleteFile(String file_name);
	
}
