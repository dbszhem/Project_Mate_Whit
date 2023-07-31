package com.web.root.member.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.root.member.dto.HostCertifyDTO;
import com.web.root.mybatis.mypageBoard.MypageBoardMapper;

@Service
public class HostCertificationServiceImpl implements HostCertificationService {

	@Autowired
	private HostCertificationFileService hostFile;
	
	@Autowired
	private MypageBoardMapper mapper;
	
	
	// host 인증 신청 글 등록
	@Override
	public String hostCertificationWriteSave(MultipartHttpServletRequest mul, HttpServletRequest request) {
		HostCertifyDTO hostCertifyDTO = new HostCertifyDTO();		
		hostCertifyDTO.setId(mul.getParameter("id"));	// 로그인한 아이디값 저장 (form에서 hidden 안에 id값 저장함)
		MultipartFile file = mul.getFile("file_name");	// 파일 저장
		
		// 첨부파일이 있으면
		if(file.getSize() != 0) {
			hostCertifyDTO.setFile_name(hostFile.hostCertificationSaveFile(file));  // 파일 이름 저장
		} else {
			hostCertifyDTO.setFile_name("nan");
		}
		
		int result = 0;
		try {
			result = mapper.hostCertificationWriteSave(hostCertifyDTO); 	// 작성 완료됐는지 결과값
			mapper.hostCertificationCertified(hostCertifyDTO);				// 완료되면 신청 대기중 표시 G로 업데이트
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String msg, url;
		if(result == 1) {
			msg = "host 인증 신청이 완료되었습니다.";
			url = "/member/member_information";
		} else {
			msg = "host 인증 신청이 실패되었습니다.";
			url = "/mypageBoard/certified/hostCertificationForm";
		}
		return hostFile.getHostCertificationMessage(request, msg, url);
	}
	
	
	// 관리자로 로그인하여 host 인증 신청목록 전부 가져오기
	@Override
	public void hostCertificationApplyList(Model model, int num) {
		
		int pageLetter = 10;  // 한 페이지 당 글 목록수
		int allCount = mapper.hostCertificationApplyCount(); // DB에 담겨있는 전체 글 수
		int repeat = allCount/pageLetter; // 마지막 페이지 번호
		if(allCount % pageLetter != 0) {
			repeat += 1;
		}
		int end = num * pageLetter;
		int start = end + 1 - pageLetter;
		
		// 페이징
		int totalPage = (allCount - 1)/pageLetter + 1;
		int block = 3;
		int startPage = (num - 1)/block * block + 1;
		int endPage = startPage + block - 1;
		if (endPage > totalPage) endPage = totalPage;
		
		model.addAttribute("repeat", repeat);
		model.addAttribute("hostApplyList", mapper.hostCertificationApplyList(start, end)); // 시작과 끝 페이지 안에서 내용 가져오기
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("block", block);
		model.addAttribute("totalPage", totalPage);
	}
	
	
	// (관리자) host 인증 신청 수락
	@Override
	public void hostCertificationOk(HttpServletRequest request, int num, Model model) {
		
		int certify_no = Integer.parseInt(request.getParameter("certify_no"));	// 글번호 저장
		String id = request.getParameter("userId");								// 작성자 저장
		
		int result = mapper.hostCertificationOk(certify_no);	// 해당 글번호 업데이트
		
		// 업데이트가 성공하면
		if(result == 1) {
			mapper.hostCertificationMemberOk(id);		// member 테이블에서 작성자 certified 결과 Y로 변경
		}
		
		// 페이징
		int pageLetter = 10;  // 한 페이지 당 글 목록수
		int allCount = mapper.hostCertificationApplyCount(); // DB에 담겨있는 전체 글 수
		int repeat = allCount/pageLetter; // 마지막 페이지 번호
		if(allCount % pageLetter != 0) {
			repeat += 1;
		}
		int end = num * pageLetter;
		int start = end + 1 - pageLetter;
		
		// 페이징
		int totalPage = (allCount - 1)/pageLetter + 1;
		int block = 3;
		int startPage = (num - 1)/block * block + 1;
		int endPage = startPage + block - 1;
		if (endPage > totalPage) endPage = totalPage;
		
		model.addAttribute("repeat", repeat);
		model.addAttribute("hostApplyList", mapper.hostCertificationApplyList(start, end)); // 시작과 끝 페이지 안에서 내용 가져오기
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("block", block);
		model.addAttribute("totalPage", totalPage);
		
	}
	
	
	// (관리자) host 인증 신청 거절
	@Override
	public void hostCertificationNo(HttpServletRequest request, int num, Model model) {
		
		int certify_no = Integer.parseInt(request.getParameter("certify_no"));	// 글번호 저장
		String id = request.getParameter("userId");								// 작성자 저장
		
		int result = mapper.hostCertificationNo(certify_no);	// 해당 글번호 업데이트
		
		// 업데이트가 성공하면 (거절)
		if(result == 1) {
			mapper.hostCertificationMemberNo(id);		// member 테이블에서 작성자 certified 결과 N로 변경
		}
		
		// 페이징
		int pageLetter = 10;  // 한 페이지 당 글 목록수
		int allCount = mapper.hostCertificationApplyCount(); // DB에 담겨있는 전체 글 수
		int repeat = allCount/pageLetter; // 마지막 페이지 번호
		if(allCount % pageLetter != 0) {
			repeat += 1;
		}
		int end = num * pageLetter;
		int start = end + 1 - pageLetter;
		
		// 페이징
		int totalPage = (allCount - 1)/pageLetter + 1;
		int block = 3;
		int startPage = (num - 1)/block * block + 1;
		int endPage = startPage + block - 1;
		if (endPage > totalPage) endPage = totalPage;
		
		model.addAttribute("repeat", repeat);
		model.addAttribute("hostApplyList", mapper.hostCertificationApplyList(start, end)); // 시작과 끝 페이지 안에서 내용 가져오기
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("block", block);
		model.addAttribute("totalPage", totalPage);
		
	}
	
	
	//(관리자) host 인증 신청 거절하고 DB에서 file_name -> nan으로 변경 (임시로 갖고있기)
//	@Override
//	public void hostCertificationDBfile_nameDelete(HttpServletRequest request) {
//		String id = request.getParameter("userId");
//		int certify_no = Integer.parseInt(request.getParameter("certify_no"));
//		mapper.hostCertificationDBfile_nameDelete(id, certify_no);
//	}

}














