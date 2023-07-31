package com.web.root.member.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.root.member.dto.HostCertifyDTO;

public interface HostCertificationService {

	// (회원) host 인증 신청
	public String hostCertificationWriteSave(MultipartHttpServletRequest mul, HttpServletRequest request);
	
	// (관리자) 로그인하여 host 신청 목록 전부 가져오기
	public void hostCertificationApplyList(Model model, int num);
	
	// (관리자) host 인증 신청 수락
	public void hostCertificationOk(HttpServletRequest request, int num, Model model);
	
	// (관리자) host 인증 신청 거절
	public void hostCertificationNo(HttpServletRequest request, int num, Model model);	   
	
	// (관리자) host 인증 신청 거절하고 DB에서 file_name -> nan으로 변경 (임시로 갖고있기)
	//public void hostCertificationDBfile_nameDelete(HttpServletRequest request);

}
