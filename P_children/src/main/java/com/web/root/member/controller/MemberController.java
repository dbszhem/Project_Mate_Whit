package com.web.root.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.web.root.board.service.BoardForProgramService;
import com.web.root.kakao.resource.KakaoDeveloper;
import com.web.root.kakaoPay.dto.ItemDTO;
import com.web.root.member.dto.HostCertifyDTO;
import com.web.root.member.dto.MemberDTO;
import com.web.root.member.service.HostCertificationService;
import com.web.root.member.service.MemberService;
import com.web.root.session.name.MemberSession;

@Controller
@RequestMapping("member")
public class MemberController implements MemberSession, KakaoDeveloper{
	
	@Autowired
	private MemberService ms;
	
	@Autowired	// kakaoPay 지불 완료 후 해당 프로그램 보드로 이동
	BoardForProgramService bfps;
	
	@Autowired
	private HostCertificationService hcs; 
	
	//============================ 임청규 ===========================================
	
	// 회원정보 
	public void userInfo(String id, Model model){
		ms.userInfo(id, model);
	} 
	
	// 개인정보 관리자정보 
	@RequestMapping("member_information")
	public String member_information( Model model, HttpServletRequest request, HttpSession session) {
		String id = (String) session.getAttribute(LOGIN);
		MemberDTO dto = ms.member_information(id);
		
		model.addAttribute("dto", dto);
		return "chenggyu/member_information";
	}
	
	// 개인정보 수정
	@RequestMapping("member_modify")
	public String member_modify(HttpSession session,Model model) {
		
		String id = (String) session.getAttribute(LOGIN);
		MemberDTO dto = ms.member_information(id);
		model.addAttribute("dto", dto);
		
		//=== 0621_최윤희 추가: 주소 나누기
		String str = dto.getAddr();
		
		String data[] = str.split("/");
        
		// 주소 출력 확인
        //for(int i=0 ; i<data.length ; i++)
        //{
        //    System.out.println("data["+i+"] : "+data[i]);
        //}
        String zonecode = data[3];
        String addr1 = data[0];
        String addr2 = data[1];
        String addr3 = data[2];
        
        model.addAttribute("zonecode", zonecode);
        model.addAttribute("addr1", addr1);
        model.addAttribute("addr2", addr2);
        model.addAttribute("addr3", addr3);
		//=== 0621_최윤희 끝
		
		return "chenggyu/member_modify";
	}
	
	// 개인정보 수정 처리
	@PostMapping("modify_save")
	@ResponseBody
	public void modify_save(HttpServletRequest request) {
		ms.modify_save(request);
	}
	
	// 회원 탈퇴
	@RequestMapping("member_leave")
	public String member_leave(HttpSession session,Model model) {
		String id = (String) session.getAttribute(LOGIN);
		MemberDTO dto = ms.member_information(id);
		model.addAttribute("dto", dto);
		return "chenggyu/member_leave";
	}
	
	// 회원 탈퇴 처리
	@PostMapping("member_leave_save")
	@ResponseBody
	public void member_leave_save(HttpServletRequest request, HttpSession session) {
		ms.member_leave_save(request);
		if(session.getAttribute("loginUser") != null) {
			session.invalidate();  // 아이디 세션 무효화
		}
	}
	
	// 회원 게시물 X
	@RequestMapping("member_board")
	public String member_board(Model model, HttpSession session, @RequestParam(value="num", required = false, defaultValue="1" )int num ) {
		String id = (String) session.getAttribute(LOGIN);
		MemberDTO dto = ms.member_information(id);
		ms.member_board(model, num);
		model.addAttribute("dto", dto);
		return "chenggyu/member_board";
	}
	
	// 관리자 정보
	@RequestMapping("manager_information")
	public String manager_information(HttpSession session, HttpServletRequest request, Model model) {
		String id = (String) session.getAttribute(LOGIN);
		MemberDTO dto = ms.member_information(id);
		model.addAttribute("dto", dto);
		return "chenggyu/manager_information";
	}
	
	// 사용자 관리
	@RequestMapping("manager_memberList")
	public String manager_List(Model model, @RequestParam(value="num", required = false, defaultValue="1") int num) {
		ms.memberInfo(model, num);
		return "chenggyu/manager_memberList";
	}
	
	// 사용자 삭제
	@RequestMapping("delete")
	public String delete(Model model, HttpServletRequest request, @RequestParam(value="num", required = false, defaultValue="1")int num) {
		ms.deleteMember(model, request);
		ms.memberInfo(model, num);
		return "chenggyu/manager_memberList";
	}
	
	// 	게시물 관리 X
	@RequestMapping("manager_board")
	public String manager_board(Model model, @RequestParam(value="num", required = false, defaultValue="1") int num) {
		ms.manager_board(model, num);
		return "chenggyu/manager_board";
	}
	


	//============================ 임청규 끝 ===========================================
	

	
	
	//============================ 박성수 시작 ===========================================
	
	@RequestMapping("select")
	public String select() {
		return "member/select";
	}
	
	
	@RequestMapping("registForm")
	public String memberRegistFrom(@RequestParam("email") String email, Model model) {
		model.addAttribute("checkedEmail", email);
		return "member/RegistForm";
	}
	
	@PostMapping("registMember")
	public String hostRegist(MemberDTO dto, HttpServletRequest request, Model model) {
		String addrMerge = request.getParameter("addr1") + "/" 
				  +request.getParameter("addr2") + "/"
				  +request.getParameter("addr3") + "/"
				  +request.getParameter("zonecode") + "/";
		dto.setAddr(addrMerge);
		int result = ms.registHost(dto, model);
		if(result != 1) {
			model.addAttribute("registRes", "죄송합니다. 회원가입 도중에 오류가 발생했습니다. 다시 시도를 해주시고, 안되면 고객센터에 연락바랍니다.");
		}
		model.addAttribute("registRes", "환영합니다~ 지금 바로 다양한 프로그램을 체험해 보세요!!");
		return "yoonhee/memberLoginForm";
	}
	
	@RequestMapping("checkId")
	@ResponseBody
	public String check_id_host(@RequestParam("id") String id) {
		String result = ms.getHostInfo(id);
		return result;
	}
	
	@GetMapping("emailCheck")
	public String emailCheckForm(@RequestParam String userSelect, HttpServletResponse response) {
		Cookie cookie = new Cookie("userSelect", userSelect);
		response.addCookie(cookie);
		return "member/emailCheck";
	}
	
	@RequestMapping("checkEmail")
	@ResponseBody
	public String check_email(@RequestParam("email") String email) {
		String result = ms.checkEmail(email);
		return result;
	}
	
	@GetMapping("sendEmail")
	@ResponseBody
	public int sendEmail(@RequestParam("email") String email) {
		return ms.sendEmail(email);
		
	}
	
	@GetMapping("kakaoCode")
	public String getKakaoCode(
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session) throws IOException {
		if(request.getParameter("kakaoLogout") != null && request.getParameter("kakaoLogout").equals("true")) {
			String kakaoLogoutId = ms.kakaoLogout((String)session.getAttribute("kakaoAccessToken"), kakaoLogoutURL);
			if(kakaoLogoutId.equals((String)session.getAttribute(LOGIN))) {
				session.invalidate();
			}
			return "redirect:/index";
		}
		String code = request.getParameter("code");
		String token = ms.getkakaoToken(code, tokenURL);
		String kakaoId = ms.registKakaoUser(token, kakaoIdURL, session);
		
		if(kakaoId == null) {
			String message = null;
			String path = request.getContextPath();
			message = "<script>alert('로그인 실패. 재시도 해주세요.');";
			message += "location.href='"+path+"member/emailCheck"+"';</script>";
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(message);
		}
		return "redirect:/index";
	}
	
	// 제품 결제 form
	@GetMapping("kakaoPayBtn")
	public String kakaoPayBtn(HttpServletRequest request, Model model) {
		String total_amount = request.getParameter("total_amount");
		int tax_free_amount = Integer.parseInt(total_amount)/10;
		
		model.addAttribute("title", request.getParameter("title"));
		model.addAttribute("quantity", request.getParameter("quantity"));
		model.addAttribute("total_amount", request.getParameter("total_amount"));
		model.addAttribute("write_no", request.getParameter("write_no"));
		model.addAttribute("num", request.getParameter("num"));
		model.addAttribute("tax_free_amount", tax_free_amount);
		return "sungsu/kakaoPay";
	}
	
	// 카카오 페이 결제 준비
	@GetMapping("kakaoPay")
	public RedirectView kakaoPayCode(ItemDTO itemDTO, HttpSession session, HttpServletRequest request) {
		String kakaoPayRequestURL = ms.readyKakaoPay(ADMIN_KEY, CONTENT_TYPE, KAKAO_PAY_READY_URL, itemDTO, session, request);	
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(kakaoPayRequestURL);
		System.out.println("kakaoPay 준비 완료!");
		return redirectView;
	}
	
	// 카카오 페이 결제 실패
	@RequestMapping("fail")
	public String kakaoPayFail() {
		return "sungsu/kakaoPayFail";
	}
	
	// 카카오페이 결제 승인 리스트
	@RequestMapping("kakaoPaymentApproveList")
	public String paymentOrder(
			Model model,
			@RequestParam(value = "num", required = false, defaultValue = "1") int num,
			HttpServletRequest request) {
		ms.getkakaoPaymentApproveList(num, request, model);
		return "sungsu/kakaoPaymentApproveList";
	}
	
	// 카카오페이 결제 상세 조회
	@GetMapping("kakaoPaymentOrderInfo")
	public String kakaoPaymentOrder(Model model, HttpServletRequest request) {
		ms.selectKakaoPaymentOrderInfo(KAKAO_PAYMENT_ORDER_URL, ADMIN_KEY,model, request);
		
		// 진욱(06/20) 추가 시작 ----------
		model.addAttribute("toKakaoPaymentCancelRequestList", request.getParameter("toKakaoPaymentCancelRequestList"));
		model.addAttribute("numForToKakaoPaymentCancelRequestList", request.getParameter("num"));
		
		// 진욱(06/20) 추가 끝 ------------
		return "sungsu/kakaoPaymentOrderInfo";
	}
	
	// 카카오페이 결제 취소
		@GetMapping("kakaoPaymentCancel")
		public RedirectView kakaoPaymentCancel(HttpServletRequest request, Model model) {
			ms.kakaoPaymentCancel(KAKAO_PAYMENT_CANCEL_URL, ADMIN_KEY, CONTENT_TYPE, request, model);
			
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("http://localhost:8080/root/member/kakaoPaymentApproveList?num=1");
			return redirectView;
		}
	

	
	
	//============================ 박성수 끝 ===========================================
	
	
	//============================ 최윤희 시작 ===========================================
	
	
	// 로그인 폼 
	@RequestMapping("memberLoginForm")
	public String memberLoginForm() {
		return "yoonhee/memberLoginForm";
	}

		
	// 로그인 시 아이디 체크
	@RequestMapping("userCheck")
	public String userCheck(HttpServletRequest request, Model m, HttpServletResponse response, HttpSession session) {
		int result = 0;

		result = ms.userCheck(request); // 요청값 전달

		if(result == 1) {  // 아이디를 성공적으로 찾았으면
			String id = request.getParameter("id");  // 입력받은 아이디 저장

			session.setAttribute(LOGIN, id);  // 아이디 세션 저장
			
			userInfo(id, m);  // 회원 정보 담은 메소드
			MemberDTO dto = (MemberDTO)m.getAttribute("info");  // userInfo(id, m) 에서 받은 속성 "info"에 유저 정보가 담김
			session.setAttribute("grade", dto.getGrade());
		
			String checked = request.getParameter("testChek");  // 아이디 기억하기 체크박스 true/false
			
			// 체크박스 값에 따라 쿠키 생성, 삭제
			if(checked.equals("true")) {
	            Cookie cookie = new Cookie("CookieId", request.getParameter("id"));
	            response.addCookie(cookie);
			} else {
	            Cookie cookie = new Cookie("CookieId", null);
	            cookie.setMaxAge(0);
	            response.addCookie(cookie);
			}
			return "chenggyu/index";  // 로그인 성공
		} else {
			m.addAttribute("userCheckFalse", "id"); // false 값 담기위해 model 사용
			return "yoonhee/memberLoginForm";  // 로그인 실패
		}
	}
	
	// 로그아웃 -> 인덱스 페이지로
	@GetMapping("memberLogout")
	public String memberLogout(HttpSession session) {
		if(session.getAttribute("loginUser") != null) {
			session.invalidate();
		}
		return "chenggyu/index";
	}
	
	// 아이디 찾기 폼
	@RequestMapping("findUserIdForm")
	public String findUserIdForm() {
		return "yoonhee/findUserIdForm";
	}
	
	// 아이디 찾기 -> 이메일, 휴대폰 번호
	@PostMapping("findUserId")
	public String findUserId(HttpServletRequest request, Model model) {
		int result = 0;
		result = ms.findUserId(request, model); // 입력받은 이메일, 휴대폰 번호 전달
		
		// 아이디 찾은 결과
		if(result == 1) {
			return "yoonhee/findUserIdResult";
		}
		return "yoonhee/findUserIdResult";
	}
	
	// 아이디 찾기 결과
	@RequestMapping("findUserIdResult")
	public String findUserIdResult() {
		return "yoonhee/findUserIdResult";
	}
	
	// 비밀번호 찾기 폼
	@RequestMapping("findUserPwdForm")
	public String findUserPwdForm() {
		return "yoonhee/findUserPwdForm";
	}
	
	// 비밀번호 찾기
	@PostMapping("findUserPwd")
	public String findUserPwd(HttpServletRequest request, Model model) {
		int result = 0;
		result = ms.findUserPwd(request, model);
		
		if(result == 1) {
			return "yoonhee/findUserPwdResult";  // 비밀번호 찾기 성공하면 결과 페이지로
		}
		return "yoonhee/findUserPwdForm"; // 비밀번호 찾기 실패하면 찾기 form 페이지로
	}
	
	
	// 비밀번호 찾기 메일 인증
	@GetMapping("sendFindPwdEmailCode")
	@ResponseBody
	public int sendFindPwdEmailCode(@RequestParam("email") String email) {
		return ms.sendFindPwdEmailCode(email);
	}
	
	
	// 비밀번호 찾기 결과
	@GetMapping("findUserPwdResult")
	public String findUserPwdResult() {
		return "yoonhee/findUserPwdResult";
	}
	
	
	// 비밀번호 찾기에서 비밀번호 수정
	@PostMapping("userUpdatePwd")
	@ResponseBody
	public boolean userUpdatePwd(@RequestParam("id") String id, @RequestParam("newPwd") String newPwd) {
		MemberDTO dto = new MemberDTO();
		dto.setId(id);  		// 입력받은 아이디 dto에 저장
		dto.setPwd(newPwd); 	// 입력받은 새 비밀번호 dto에 저장
		ms.userUpdatePwd(dto);

		return true;
	}

	
	
	//============================ 최윤희 끝 ===========================================
	//============================ 주진욱 시작 ==========================================
	
	// 관리자 페이지: 결재취소요청 관리페이지 불러오기
	@RequestMapping("kakaoPaymentCancelRequestList")
	public String cancelRequestList(@RequestParam(value = "num", required = false, defaultValue = "1") int num,
			HttpServletRequest request, Model model) {
		ms.getkakaoPaymentApproveListByCancelRequest(num, request, model);
		model.addAttribute("num", num);
		return "/sungsu/kakaoPaymentCancelRequestList";
	}
	
	
	//============================ 주진욱 끝 ===========================================
	
	
}
