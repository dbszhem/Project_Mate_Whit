package com.web.root.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.root.member.service.HostCertificationFileService;
import com.web.root.member.service.HostCertificationService;
import com.web.root.member.service.MemberService;
import com.web.root.session.name.MemberSession;

@Controller
@RequestMapping("mypageBoard")
public class HostCertificationController implements MemberSession {

	@Autowired
	private MemberService ms;
	
	@Autowired
	private HostCertificationService hcs;
	
	@Autowired
	private HostCertificationFileService hostFile;
	
	
	// host 인증 신청 폼
	@RequestMapping("certified/hostCertificationForm")
	public String hostCertificationForm(HttpSession session, Model model, HttpServletRequest request) {
		
		String id = (String) session.getAttribute(LOGIN);  	// 로그인 아이디 문자열로 저장
		model.addAttribute("userid", id);					// jsp에 전달하기 위해 model에 저장
		
		return "mypageBoard/certified/hostCertificationForm";
	}
	
	// 인증폼에 작성하면 정보 전송 -> 정보 DB에 저장되면 결과 메세지 출력
	@PostMapping("certified/hostCertification")
	public void hostCertification(MultipartHttpServletRequest mul,
			  						HttpServletResponse response,
			  						HttpServletRequest request) throws IOException {

		// 입력 및 결과 메시지 출력
		String message = hcs.hostCertificationWriteSave(mul, request);
		response.setContentType("text/html; charset=utf-8");  // 요청에 대한 응답 인코딩 처리
		PrintWriter out = response.getWriter(); 			  // 출력 객체 생성
		out.print(message); 								  // 메시지 출력	
	}

	
	// (관리자) 인증 신청 목록
	@RequestMapping("certified/hostCertificationApplyList")
	public String hostCertificationApplyList(HttpSession session, Model model, HttpServletRequest request,
											 @RequestParam(value="num", required = false, defaultValue="1") int num) {
		
		String id = (String) session.getAttribute(LOGIN);  // 로그인 아이디 문자열로 저장
		model.addAttribute("userid", id);				   // jsp에 전달하기 위해 model에 저장

		hcs.hostCertificationApplyList(model, num);		   // 페이징
		
		model.addAttribute("num", num);					   // 페이지 번호 저장	
		
		return "mypageBoard/certified/hostCertificationApplyList";
	}
	
	
	// (관리자) 인증 신청 자료 다운로드
	@RequestMapping("certified/hostCertificationDownload")
	public void hostCertificationDownload(@RequestParam("file_name") String file_name, 
										  HttpServletResponse response) throws Exception {
		
		response.addHeader("Content-disposition", "attachment; fileName=" + URLEncoder.encode(file_name, "utf-8"));
		File file = new File(HostCertificationFileService.IMAGE_HOST_CERTIFICATION_REPO + "\\" + file_name);
		FileInputStream in = new FileInputStream(file);
		FileCopyUtils.copy(in, response.getOutputStream());
		in.close();
	}
	
	
	// (관리자) 인증 신청 수락
	@RequestMapping("certified/hostCertificationOk")
	public String hostCertificationOk(HttpSession session, Model model, HttpServletRequest request,
									  @RequestParam(value="num", required = false, defaultValue="1") int num) {
		
		String id = (String) session.getAttribute(LOGIN);  // 로그인 아이디 문자열로 저장
		model.addAttribute("userid", id);				   // jsp에 전달하기 위해 model에 저장

		hcs.hostCertificationOk(request, num, model);	   // 인증 신청 승인
		
		return "mypageBoard/certified/hostCertificationApplyList";
	}
	
	// (관리자) 인증 신청 거절
	@RequestMapping("certified/hostCertificationNo")
	public String hostCertificationNo(HttpSession session, Model model, HttpServletRequest request,
									  @RequestParam(value="num", required = false, defaultValue="1") int num) {
		
		String id = (String) session.getAttribute(LOGIN);  // 로그인 아이디 문자열로 저장
		model.addAttribute("userid", id);				   // jsp에 전달하기 위해 model에 저장

		hcs.hostCertificationNo(request, num, model);	   // 인증 신청 거절
		
		// 인증 신청 제출 자료 삭제(파일삭제)
		//this.hostCertificationDeleteFile(request); 	   // 파일 이름 넘김
		
		// DB 테이블에도 file_name 이름 nan으로 변경
		//hcs.hostCertificationDBfile_nameDelete(request);
	
		return "mypageBoard/certified/hostCertificationApplyList";
	}
	
	// (관리자) 인증 신청 거절하면 제출자료 삭제 (임시로 갖고있기)
//	@RequestMapping("certified/hostCertificationDeleteFile")
//	public String hostCertificationDeleteFile(HttpServletRequest request) {
//		// 파일 삭제 (파일 이름값 + 글번호 넘김)
//		hostFile.hostCertificationDeleteFile(request.getParameter("file_name"));
//		
//		return "mypageBoard/certified/hostCertificationApplyList";
//	}
	
	
}










