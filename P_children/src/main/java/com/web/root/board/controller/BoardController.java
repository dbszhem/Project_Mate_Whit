package com.web.root.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.root.board.dto.BoardDTO;
import com.web.root.board.dto.BoardDibsDTO;
import com.web.root.board.dto.NoticeBoardDTO;
import com.web.root.board.dto.ProgramBoardDTO;
import com.web.root.board.service.BoardFileService;
import com.web.root.board.service.BoardForProgramService;
import com.web.root.board.service.BoardService;
import com.web.root.member.dto.MemberDTO;
import com.web.root.member.service.MemberService;
import com.web.root.qna.dto.QnaDTO;
import com.web.root.qna.dto.Qna_RepDTO;
import com.web.root.session.name.MemberSession;

@Controller
@RequestMapping("board")
public class BoardController implements MemberSession{

	@Autowired
	BoardService bs;
	
	@Autowired
	BoardFileService bfs;
	
	@Autowired
	BoardForProgramService bfps;
	
	@Autowired
	MemberService ms;
	
	//============================ 주진욱 시작 ===========================================
	
	// board Read 기능: All List up 하기
	@GetMapping("boardAllList")
	public String boardAllList(HttpSession session, Model model, @RequestParam(value="num", required = false, defaultValue="1") int num, HttpServletRequest request ) {
		
		// boardList 생성
		bs.boardAllList(model, num, request);
		
		// (1-2) 로그인값 불러오기
	      String id = (String) session.getAttribute(LOGIN);
	      if(id == null) { // 비로그인인 경우
	         model.addAttribute("id", id);
	      } else {   // 일반 로그인인 경우
	         ms.userInfo(id, model);
	      }
		
		// 로그인 유저 grade 확인을 위한 "admin" 모델에 추가하기
		model.addAttribute("admin", ADMIN);
		return "/board/boardAllList"; 
	}
	
	// board Create 기능을 위한 Form 생성
	@RequestMapping("writeForm")
	public String writeForm(HttpSession session, Model model) {
		
		String id = (String) session.getAttribute(LOGIN);
		model.addAttribute("user", id);
		return "/board/writeForm";
	}
	
	// board Create 기능 : 실제 DB 작업 시행
	@PostMapping("writeSave")
	public void writeSave(Model model, MultipartHttpServletRequest mul, 
							HttpServletResponse response, 
							HttpServletRequest request) throws IOException {
		
		// board 입력 및 결과 메시지 출력
		String message = bs.writeSave(mul,request);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(message);
	}
	

   // board Read 기능 : content 하나 보기
   @RequestMapping("contentView")
   public String contentView(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
	   
	   //=== 0616_최윤희 추가: 마이페이지에서 컨텐츠 게시글 -> 제목 -> 다시 글목록 눌렀을 때 마이페이지로 돌아오기위한 정보값
	   if(request.getParameter("boardNum") != null) { // boardNum이 null이 아닐 때
		   int myBoardCheckNum = Integer.parseInt(request.getParameter("boardNum")); // 마이페이지에서 제목을 눌러 정보값을 전달 받음
		   model.addAttribute("myBoardCheckNum", myBoardCheckNum); // boardCheckNum의 값은 2입니다.
		   model.addAttribute("num", request.getParameter("num"));
	   }
	   //==== 06.16_최윤희 끝
	   
      // (1) 정보 가져오기
      // (1-1) board 하나 정보 가져오기
      BoardDTO boardDTO = bs.contentView(model, request);
      
      // (1-2) 로그인값 불러오기
      String id = (String) session.getAttribute(LOGIN);
      if(id == null) { // 비로그인인 경우
         model.addAttribute("id", id);
      } else {   // 일반 로그인인 경우
         ms.userInfo(id, model);
      }
      
      //(1-3) boardDib(찜하기) 정보 가져오기
      Map<String, Object> mapForBoardDib = new HashMap<String, Object>();
      
      if(id != null) { // 로그인 했을 시 찜했는지 확인하는 정보 가져오기
         mapForBoardDib.put("id", id);
         mapForBoardDib.put("write_no", request.getParameter("write_no"));
         BoardDibsDTO boardDibsDTO = bs.getDibsByIdWriteNo(mapForBoardDib);
         
         // 정보 담기
         if(boardDibsDTO == null) {   // 찜한 적이 없을 때
            model.addAttribute("state", 0);
         } else {               // 찜한 적이 있을 때
            model.addAttribute("state", boardDibsDTO.getDibs_state());
         }
         
      }
      
      // (1-4) 게시판 찜한 숫자 가져오기
      int dibsNum = bs.getdibsNumByWriteNo(Integer.parseInt(request.getParameter("write_no"))); 
      
      
      //(1-5) 자유게시판(mypage)에서 검색하고 글보기 했을 때 검색값들 넘겨주기
      String board_category = request.getParameter("board_category");            // 카테고리 옵션 저장
      String board_searchCategory = request.getParameter("board_searchCategory");   // 검색 카테고리 옵션 저장
      String board_searchKeyword = request.getParameter("board_searchKeyword");      // 검색 키워드 저장
      //===================================
      
      //(1-6) 마이페이지 찜한 게시판에서 온 경우 : 구별자 가져오기
      String toMyDibsBoard = request.getParameter("toMyDibsBoard"); // 있으면 yes, 없으면 null이다.
      
      //(2) 정보 담기
      model.addAttribute("dto", boardDTO);      // board 정보 model에 담기
      model.addAttribute("kakaoIdCheck", id); // kakaoId 구분자 담기
      model.addAttribute("admin", ADMIN);   // admin(='gold') 정보 model에 담기 (grade 확인용)
      
      model.addAttribute("dibsNum", dibsNum);   // 이 게시판을 찜한 사람들의 숫자 담기
      model.addAttribute("toMyDibsBoard", request.getParameter("toMyDibsBoard")); // 내가찜한 게시판에서 왔을 때 돌려보내는 변수 담기
      
      model.addAttribute("board_category", board_category);          // 요청온 카테고리 옵션 저장
      model.addAttribute("board_searchCategory", board_searchCategory); // 요청온 검색 카테고리 저장
      model.addAttribute("board_searchKeyword", board_searchKeyword);    // 요청온 검색 키워드 저장
      
      model.addAttribute("toMyDibsBoard", toMyDibsBoard);	// 마이페이지 찜한 게시판에서 온 경우 구별자 저장
      
      //(3) 조회수 증가
      bs.hitplus(boardDTO);
      
      return "/board/contentView";
   }
	
	// board 파일 업로드시 이미지에 보이게 하기
	@GetMapping("download")
	public void download(@RequestParam("file_name") String file_name, HttpServletResponse response) throws Exception {
		
		response.addHeader("Content-disposition", "attachment; fileName=" +URLEncoder.encode(file_name, "utf-8"));
		File file = new File(BoardFileService.IMAGE_REPO + "\\" + file_name);
		FileInputStream in = new FileInputStream(file);
		FileCopyUtils.copy(in, response.getOutputStream());
		in.close();
		
	}
	
	// board Update Form 생성
	@RequestMapping("modifyForm")
	public String modifyForm(Model model, HttpServletRequest request) {
		
		BoardDTO dto = bs.contentView(model, request);
		
		model.addAttribute("dto", dto);
		return "/board/modifyForm";
	}
	
	// board Update 기능 : 수정사항 저장하기 (DB에 반영)
	@PostMapping("modifySave")
	public void modifySave(Model model,HttpServletRequest request, MultipartHttpServletRequest mul, HttpServletResponse response) throws IOException {
		
		MultipartFile file = mul.getFile("file");
		
		BoardDTO dto = bs.contentView(model, request);
		
		// 이전에 업로드된 이미지 파일 삭제
		bfs.deleteFile(dto.getFile_name());
		
		// board 입력 및 결과 메시지 출력
		String message = bs.modifySave(mul,request);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(message);
	
	} // modifySave() end
	
	// board Delete 기능 : (DB에 반영)
	@RequestMapping("delete")
	public void delete(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 파일 삭제
		bfs.deleteFile(request.getParameter("file_name"));

		// write_no로 DB 내용 삭제하기
		String message = bs.deleteBoard(model,request);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(message);
	}
	
	// 자유 게시판 카테고리 + 검색 조회
	@RequestMapping("boardSearchForm")
	public String selectingCategory(HttpServletRequest request, @RequestParam(value="num", required = false, defaultValue="1") int num,
			Model model, HttpSession session) {
		// 로그인값 불러오기
		  String id = (String) session.getAttribute(LOGIN);
		  if(id == null) { // 비로그인인 경우
			  model.addAttribute("id", id);
		  } else {   // 일반 로그인인 경우
		     ms.userInfo(id, model);
		  }
		
		String board_category = request.getParameter("board_category");				// 카테고리 옵션 저장
		String board_searchCategory = request.getParameter("board_searchCategory");	// 검색 카테고리 옵션 저장
		String board_searchKeyword = request.getParameter("board_searchKeyword");		// 검색 키워드 저장
		
		bs.boardSearchForm(board_category, board_searchCategory, board_searchKeyword, model, num); 	// 서비스에게 내용 넘겨줌
		// 서비스에서 요청을 받지않은 이유는 처음 값 
		// (카테고리: boardAll, 검색카테고리: 제목, 검색키워드: "") 을 저장하기 위해서
		// 서비스에서는 쿼리문 조회를 위해서 해당 내용들을 "%%"로 바꾸기 때문에 .jsp에서는 "%%"가 사용불가
		// 즉 서비스에서 요청내용을 받게되면 처음 쿼리문으로 전체를 "%%"로 변경 -> 다시 ""로 변환해줘야한다.

		model.addAttribute("num", num); 		// 페이지 번호 저장
		model.addAttribute("admin", ADMIN); 	// 관리자 아이디 저장
		model.addAttribute("board_category", board_category); 			// 요청온 카테고리 옵션 저장
		model.addAttribute("board_searchCategory", board_searchCategory); // 요청온 검색 카테고리 저장
		model.addAttribute("board_searchKeyword", board_searchKeyword); 	// 요청온 검색 키워드 저장
		
		model.addAttribute("admin", ADMIN); // 관리자 아이디 저장
		return "board/boardAllList";
	}
	
	// 여기서부터는 찜하기 기능
	// 찜하기 버튼 클릭시 토글 기능(자유 게시판)
	@PostMapping(value="toggleDibs/{write_no}", produces="application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> toggleDibs(@RequestBody Map<String, Object> map,@PathVariable("write_no") String write_no, HttpSession session, Model model, HttpServletRequest request) {
		int result = bs.toggleDibs(map);
		System.out.println(write_no +"입니다~!!");
		int dibsNum = bs.getdibsNumByWriteNo(Integer.parseInt(write_no));
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		returnMap.put("result", result);
		returnMap.put("changedDibsNum", dibsNum);
		
		return returnMap;
	}
	
	
	//============================ 주진욱 끝 ===========================================
	
	
	
	
	
	//============================ 최윤희 시작 ===========================================
	
	
	// 공지사항 게시판
	@RequestMapping("notice/noticeBoardAllList")						
	public String noticeBoardAllList(HttpSession session, Model model, 
									@RequestParam(value="num", required = false, defaultValue="1") int num, 
									HttpServletRequest request) {
		
		bs.noticeBoardAllList(model, num, request);
		String id = (String) session.getAttribute(LOGIN);
		
		// 로그인을 했을 때 아이디 정보값을 가져온다.
		if(id != null) {
			ms.userInfo(id, model); // 회원정보 저장
		}
		
		model.addAttribute("num",num); // 페이지 번호 저장
		
		model.addAttribute("admin", ADMIN); // 관리자 아이디 저장
		return "board/notice/noticeBoardAllList";
	}
	
	// 공지사항 게시글 보기
	@RequestMapping("notice/noticeBoardContentView")
	public String noticeBoardContentView(Model model, HttpServletRequest request, HttpSession session, 
										 HttpServletResponse response) throws Exception {
		
		//== 공지게시판에서 검색하고 글보기 했을 때 검색값들 넘겨주기
		String notice_category = request.getParameter("notice_category");				// 카테고리 옵션 저장
		String notice_searchCategory = request.getParameter("notice_searchCategory");	// 검색 카테고리 옵션 저장
		String notice_searchKeyword = request.getParameter("notice_searchKeyword");		// 검색 키워드 저장
		//===================================
		
		NoticeBoardDTO noticeBoardDTO = bs.noticeBoardContentView(request); // 요청받은 글번호로 NoticeBoardDTO 글 내용 찾아서 저장  
		String user = (String) session.getAttribute(LOGIN);  // 아이디 저장
		
		model.addAttribute("num", request.getParameter("num")); // 페이지 번호 저장 -> 글 보고 글목록 갈 때 해당 페이지로 가기위해
		model.addAttribute("noticeBoardDTO", noticeBoardDTO);  // 해당 글 번호 내용들 NoticeBoardDTO에 저장
		
		// 로그인 상태로 공지사항 게시글을 볼 때 회원 아이디와 정보 저장
		if(user != null) {
			model.addAttribute("user", user); // 회원 id 저장
			ms.userInfo(user, model); 		  // 회원 아이디로 회원 전체 정보 model에 저장(info.~)
		}
		
		model.addAttribute("admin", ADMIN); // 관리자 저장
		
		// 검색 값들 저장
		model.addAttribute("notice_category", notice_category); 			// 요청온 카테고리 옵션 저장
		model.addAttribute("notice_searchCategory", notice_searchCategory); // 요청온 검색 카테고리 저장
		model.addAttribute("notice_searchKeyword", notice_searchKeyword); 	// 요청온 검색 키워드 저장
		
		bs.noticeBoardHitplus(noticeBoardDTO); // 조회수 증가
		
		return "board/notice/noticeBoardContentView";
	}
	
	
	// 공지사항 글 작성 폼
	@RequestMapping("notice/noticeBoardWriteForm")
	public String noticeBoardWriteForm(HttpSession session, Model model) {
		
		String id = (String) session.getAttribute(LOGIN);  // 로그인 아이디 문자열로 저장
		model.addAttribute("user", id);		// jsp에 전달하기 위해 model에 저장
		
		return "board/notice/noticeBoardWriteForm";
	}
	
	
	// 공지사항 작성
	@PostMapping("notice/noticeBoardWriteSave")
	public void noticeBoardWriteSave(MultipartHttpServletRequest mul,
								  HttpServletResponse response,
								  HttpServletRequest request) throws IOException {
		// board 입력 및 결과 메시지 출력
		String message = bs.noticeBoardWriteSave(mul, request);
		response.setContentType("text/html; charset=utf-8");  // 요청에 대한 응답 인코딩 처리
		PrintWriter out = response.getWriter(); // 출력 객체 생성
		out.print(message); // 메시지 출력
	}
	
	
	// 이미지 다운로드(이미지 미리보기)
	@RequestMapping("notice/noticeBoardDownload")
	public void noticeBoardDownload(@RequestParam("file_name") String file_name, 
									HttpServletResponse response) throws Exception {
		
		response.addHeader("Content-disposition", "attachment; fileName=" + URLEncoder.encode(file_name, "utf-8"));
		File file = new File(BoardFileService.IMAGE_NOTICE_REPO + "\\" + file_name);
		FileInputStream in = new FileInputStream(file);
		FileCopyUtils.copy(in, response.getOutputStream());
		in.close();
	}
	
	
	
	
	// 공지사항 게시글 수정 폼
	@RequestMapping("notice/noticeBoardModifyForm")
	public String noticeBoardModifyForm(Model model, HttpServletRequest request) {
		NoticeBoardDTO noticeBoardDTO = bs.noticeBoardContentView(request);  // 요청온 글번호로 해당 내용 찾아서 
		
		model.addAttribute("noticeBoardDTO", noticeBoardDTO);  // 모델로 저장
		
		return "board/notice/noticeBoardModifyForm";
	}
	
	
	// 공지사항 수정 결과
	@PostMapping("notice/noticeBoardModifySave")
	public void noticeBoardModifySave(MultipartHttpServletRequest mul,
									  HttpServletRequest request,  
									  HttpServletResponse response) throws IOException {
		
		// board 입력 및 결과 메시지 출력
		String message = bs.noticeBoardModifySave(mul, request);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(message);
	}
	
	
	// 공지사항 게시글 삭제
	@RequestMapping("notice/noticeBoardDelete")
	public void noticeBoardDelete(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 파일 삭제 (파일 이름값 넘김)
		bfs.noticeBoardDeleteFile(request.getParameter("file_name"));

		// write_no넘겨서 DB 내용 삭제하기
		String message = bs.noticeBoardDelete(request);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(message);
		
	}
	
	
	// 공지사항 카테고리 + 검색 조회
	@RequestMapping(value="notice/noticeSearchForm")
	public String noticeSearchForm(HttpServletRequest request, HttpSession session, Model m,
				      			   @RequestParam(value="num", required = false, defaultValue="1") int num) {

		String notice_category = request.getParameter("notice_category");				// 카테고리 옵션 저장
		String notice_searchCategory = request.getParameter("notice_searchCategory");	// 검색 카테고리 옵션 저장
		String notice_searchKeyword = request.getParameter("notice_searchKeyword");		// 검색 키워드 저장
		
		bs.noticeSearchForm(notice_category, notice_searchCategory, notice_searchKeyword, m, num); 	// 서비스에게 내용 넘겨줌
		
		String id = (String) session.getAttribute(LOGIN);	// 아이디 세션 저장
		
		// 로그인을 했을 때 아이디 정보값을 가져온다.
		if(id != null) {
			ms.userInfo(id, m); // 회원정보 저장
		}
		
		m.addAttribute("num", num); 		// 페이지 번호 저장
		m.addAttribute("admin", ADMIN); 	// 관리자 아이디 저장
		
		// 서비스에서 요청을 받지않은 이유는 처음 값 
		// (카테고리: noticeAll, 검색카테고리: 제목, 검색키워드: "") 을 저장하기 위해서
		// 서비스에서는 쿼리문 조회를 위해서 해당 내용들을 "%%"로 바꾸기 때문에 .jsp에서는 "%%"가 사용불가
		// 즉 서비스에서 요청내용을 받게되면 처음 쿼리문으로 전체를 "%%"로 변경 -> 다시 ""로 변환해줘야한다.
		
		m.addAttribute("notice_category", notice_category); 			// 요청온 카테고리 옵션 저장
		m.addAttribute("notice_searchCategory", notice_searchCategory); // 요청온 검색 카테고리 저장
		m.addAttribute("notice_searchKeyword", notice_searchKeyword); 	// 요청온 검색 키워드 저장
		
		return "board/notice/noticeBoardAllList";
	}
	
		
		
	//============================ 최윤희 끝 ===========================================
	
	//============================임청규 시작==========================================
	
	// 관리자 문의 관리
	@RequestMapping("manager_qna")
	public String manager_qna(Model model, @RequestParam(value="num", required = false, defaultValue="1") int num) {
		bs.manager_qna(model, num);
		return "chenggyu/manager_qna";
	}
	
	// 회원 문의
	@RequestMapping("member_qna")
	public String member_qna(HttpSession session, Model model, @RequestParam(value="num", required = false, defaultValue="1") int num) {
		String id = (String) session.getAttribute(LOGIN);
		
		bs.member_qna(model, num, id);
		return "chenggyu/member_qna";
	}
	
	// 문의글 작성
	@RequestMapping("member_writeForm")
	public String member_writeForm(HttpSession session, Model model) {
		String id = (String) session.getAttribute(LOGIN);
		model.addAttribute("id", id);
		return "board/service/member_writeForm";
	}
	
	// 문의글 저장
	@PostMapping("member_write_save")
	@ResponseBody
	public void member_write_save(HttpServletRequest request) {
		bs.member_write_save(request);
	}
	
	// 관리자 답변 작성
	@RequestMapping("manager_write_save")
	@ResponseBody
	public void manager_write_save(HttpServletRequest request, Model model, HttpSession session, RedirectAttributes rs) {
		bs.manager_write_save(request, model);
		bs.qna_state(request);
		
		String id = (String) session.getAttribute(LOGIN);
		QnaDTO qna_dto = bs.contentView_qna(model, request);
		Qna_RepDTO qna_rep_dto = bs.contentView_rep_qna(model, request);
		ms.userInfo(id, model);
		
		model.addAttribute("id", id);
		model.addAttribute("qna_dto", qna_dto);
		model.addAttribute("qna_rep_dto", qna_rep_dto);
		model.addAttribute("admin", ADMIN);
	}
	
	// 문의 내용 확인
	@RequestMapping("ContentView")
	public String ContentView(Model model, HttpServletRequest request, HttpSession session) {
		
		String id = (String) session.getAttribute(LOGIN);
		QnaDTO qna_dto = bs.contentView_qna(model, request);
		Qna_RepDTO qna_rep_dto = bs.contentView_rep_qna(model, request);
		ms.userInfo(id, model);
		
		model.addAttribute("id", id);
		model.addAttribute("qna_dto", qna_dto);
		model.addAttribute("qna_rep_dto", qna_rep_dto);
		model.addAttribute("admin", ADMIN); 
		
		return "board/service/ContentView";
	}
	
	// 고객센터 
	@RequestMapping("service_center")
	public String service_center(Model model, HttpSession session) {
		String id = (String) session.getAttribute(LOGIN);
		return "board/service/service_center";
	}
	
	//============================임청규 끝==========================================
	
}
