package com.web.root.member.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.web.root.kakaoPay.dto.ItemDTO;
import com.web.root.member.dto.MemberDTO;
import com.web.root.member.dto.MemberDTO;


public interface MemberService {
	
	//============================ 임청규 시작 ===========================================
	// 회원 정보 저장
	public void userInfo(String id, Model model);
	
	// 회원정보
	public MemberDTO member_information(String id);
	
	// 회원정보 수정
	public void modify_save(HttpServletRequest request);
	
	// 회원 탈퇴
	public void member_leave_save(HttpServletRequest request);
	
	// 회원 삭제
	public void deleteMember(Model model, HttpServletRequest request);
	
	// 게시판 회원정보
	public void memberInfo(Model model, int num);
	
	// 게시판관리 추가 예정
	public void manager_board(Model model, int num);
	
	// 회원게시물 추가 예정
	public void member_board(Model model, int num);

	//============================ 임청규 끝 ===========================================
	
	
	//============================ 박성수 시작 ===========================================
	
	public int registHost(MemberDTO dto, Model model);

	public String getHostInfo(String id);
	
	String checkEmail(String email);
	
	int sendEmail(String email);
	
	public String getkakaoToken(String code, String tokenurl) throws IOException;
	
	public String registKakaoUser(String token, String kakaoidurl, HttpSession session) throws IOException;
	
	public String kakaoLogout(String token, String kakaologouturl);
	
	public String readyKakaoPay(String adminKey, String contentType, String kakaoPayReadyUrl, ItemDTO itemDTO, HttpSession session, HttpServletRequest request);	
	
	public void kakaoPaymentApprove(String kakaoPaymentApproveUrl, String adminKey, String pg_token, HttpSession session);
	
	public void getkakaoPaymentApproveList(int num, HttpServletRequest request, Model model);
			 
	public void selectKakaoPaymentOrderInfo(String kakaoPaymentOrderUrl, String adminKey, Model model, HttpServletRequest request);
	
	public void kakaoPaymentCancel(String kakaoPaymentCancelUrl, String adminKey, String contentType,
			HttpServletRequest request, Model model);
	
	
	//============================ 박성수 끝 ===========================================

	
	
	
	
	//============================ 최윤희 시작 ===========================================
	// 유저 확인
	public int userCheck(HttpServletRequest request);
	
	// 아이디 찾기
	public int findUserId(HttpServletRequest request, Model model);
	
	// 비밀번호 찾기
	public int findUserPwd(HttpServletRequest request, Model model);
	
	// 비밀번호 찾기 메일 인증
	public int sendFindPwdEmailCode(String email);
	
	// 비밀번호 수정
	public void userUpdatePwd(MemberDTO dto);
	
	
	
	
	
	
	
	//============================ 최윤희 끝 ===========================================
	
	
	
	
	
	//============================ 주진욱 시작 ==========================================
	// 관리자 페이지: 결재 취소 요청 정보 가져오기(tid, cancel_request 기준으로)
	public void getkakaoPaymentApproveListByCancelRequest(int num, HttpServletRequest request, Model model);
	//============================ 주진욱 끝 ===========================================
	
}
