package com.web.root.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.root.board.dto.BoardDTO;
import com.web.root.board.dto.PaidProgramInfoDTO;
import com.web.root.board.dto.ProgramBoardDTO;
import com.web.root.board.service.BoardForProgramService;
import com.web.root.board.service.BoardService;
import com.web.root.member.service.MemberService;
import com.web.root.member.service.MypageBoardService;
import com.web.root.session.name.MemberSession;

@Controller
@RequestMapping("mypageBoard")
public class MypageBoardController implements MemberSession {

	@Autowired
	private MypageBoardService mbs;
	
	@Autowired
	private MemberService ms;
	
	@Autowired
	private BoardService bs;
	
	// 진욱 추가 (06/19)	-----------------
	@Autowired
	private BoardForProgramService bfps;
	// 진욱 추가 끝(06/19)	-----------------
	
	// (커뮤니티) 마이페이지 작성한 게시글 목록
	@RequestMapping("write/mypageBoardWriteList")
	public String mypageBoardWriteList(HttpSession session, Model m,
									  @RequestParam(value="num", required = false, defaultValue="1") int num,
									  HttpServletRequest request) {

		// 세션에 저장된 아이디 값 가져오기 (해당 로그인 아이디값이 저장된다.)
		// 이것 때문에 url에 아이디 표시안할 수 있다. (@RequestParm 하면 주소에 담아야하는것같습니다??)
		// ex) syso하면 yoonhee 해당 아이디가 출력됨
		String id = (String)session.getAttribute(LOGIN);
		
		// 로그인을 했을 때 아이디 정보값을 가져온다.
		if(id != null) {  
			ms.userInfo(id, m); // 회원정보 저장 ex. info.id
		}
		
		//=== 마이페이지 관련 정보
		BoardDTO boardCheckNum = new BoardDTO();
		// 마이페이지 커뮤니티 게시글 -> 제목클릭 -> board contentViewf -> 다시 글목록 클릭 시 마이페이지 게시글로 오도록 정보값 지정
		m.addAttribute("myBoardCheckNum", boardCheckNum.getBoardNum());
		//====
		
		mbs.mypageBoardCommunityWriteList(id, m, num); 	// 커뮤니티 게시판 페이징, 카운트 얻는 mbs: 아이디, 모델, 페이지번호 넘겨줌
		
		m.addAttribute("num", num); 		 			// 페이지 번호 저장
		return "mypageBoard/write/mypageBoardWriteList";
	}
	
	
	// (프로그램) 마이페이지 작성한 게시글 목록 (member, host 구분)
	@RequestMapping("write/mypageBoardProgramWriteList")
	public String mypageBoardProgramWriteList(HttpSession session, Model m,
									  		  @RequestParam(value="num", required = false, defaultValue="1") int num,
									  		  HttpServletRequest request) {

		// 세션에 저장된 아이디 값 가져오기 (해당 로그인 아이디값이 저장된다.)
		// 이것 때문에 url에 아이디 표시안할 수 있다. (@RequestParm 하면 주소에 담아야하는것같습니다??)
		// ex) syso하면 yoonhee 해당 아이디가 출력됨
		String id = (String)session.getAttribute(LOGIN);
		
		// 로그인을 했을 때 아이디 정보값을 가져온다.
		if(id != null) {  
			ms.userInfo(id, m); // 회원정보 저장 ex. info.id
		}
		
		//=== 마이페이지 관련 정보
		ProgramBoardDTO programBoardCheckNum = new ProgramBoardDTO();
		// 마이페이지 커뮤니티 게시글 -> 제목클릭 -> board contentViewf -> 다시 글목록 클릭 시 마이페이지 게시글로 오도록 정보값 지정
		m.addAttribute("programBoardCheckNum", programBoardCheckNum.getProgramBoardNum());
		//===
		
		mbs.mypageBoardProgramWriteList(id, m, num); // 프로그램 게시판 페이징, 카운트 얻는 mbs: 아이디, 모델, 페이지번호 넘겨줌
	    
		m.addAttribute("num", num); 		 		 // 페이지 번호 저장
		
		return "mypageBoard/write/mypageBoardProgramWriteList";
	}
	
	// ======================================= 박성수 시작 =================================================
	@GetMapping("write/paidProgramInfoList")
	public String myPagePaidProgramInfoList(
			Model model,
			HttpSession session,
			@RequestParam(value = "pg_num", required = false, defaultValue = "1") int pg_num) {
		
		String id = (String)session.getAttribute(LOGIN);
		mbs.selectPaidProgramList(id, pg_num, model);
		
		return "mypageBoard/write/myPagePaidProgramList";
	}
	
	@PostMapping("write/cancelRequest")
	@ResponseBody
	public String cancelRequest(HttpServletRequest request) {
		String result = mbs.updateCancelRequest(request);
		return result;
	}
	
	
	// ======================================= 박성수 시작 =================================================
	// ======================================= 주진욱 시작 =================================================
	// 내가 찜한 게시글 보기 페이지로 이동
	@RequestMapping("mypageDibsBoard")
	public String myDibsBoard(HttpSession session, HttpServletRequest request, Model model, @RequestParam(value="num", required = false, defaultValue="1") int num ) {
		
		// 로그인값 불러오기
		String id = (String) session.getAttribute(LOGIN);
		if(id == null) { // 비로그인인 경우
			model.addAttribute("id", id);
		} else {	// 일반 로그인인 경우
			ms.userInfo(id, model);
		}
		// 로그인 유저 grade 확인을 위한 "admin" 모델에 추가하기
		model.addAttribute("admin", ADMIN);
		
		// 내가 찜한 게시판 가져오기
		bs.myDibsBoardAllList(model, num, request, id);
		
		return "mypageBoard/write/mypageDibsBoard";
	}
	
	@RequestMapping("mypageDibsProgramBoard")
	public String myDibsProgramBoard(HttpSession session, HttpServletRequest request, Model model, @RequestParam(value="num", required = false, defaultValue="1") int num ) {
		
		// 로그인값 불러오기
		String id = (String) session.getAttribute(LOGIN);
		if(id == null) { // 비로그인인 경우
			model.addAttribute("id", id);
		} else {	// 일반 로그인인 경우
			ms.userInfo(id, model);
		}
		// 로그인 유저 grade 확인을 위한 "admin" 모델에 추가하기
		model.addAttribute("admin", ADMIN);
		
		// 내가 찜한 게시판 가져오기
		bfps.myDibsProgramBoardAllList(model, num, request, id);
		
		return "mypageBoard/write/mypageDibsProgramBoard";
	}
	
	// 
	@PostMapping(value="write/paidCancelRequest/{tid}", produces="application/json; charset=utf-8")
	@ResponseBody
	public int addReply(@PathVariable("tid") String tid) {
		// System.out.println(tid + " 받았습니다~!");
		int result = bfps.paidCancelRequestByTid(tid);
		return result;
	}
	
	// ======================================= 주진욱 끝 =================================================
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}











