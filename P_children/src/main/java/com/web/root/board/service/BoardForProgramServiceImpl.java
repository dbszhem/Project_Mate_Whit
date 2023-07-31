package com.web.root.board.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.root.board.dto.BoardDTO;
import com.web.root.board.dto.PaidProgramInfoDTO;
import com.web.root.board.dto.ProgramBoardDTO;
import com.web.root.kakao.dto.ProgramMapDTO;
import com.web.root.kakao.service.KakaoServiceImp1;
import com.web.root.mybatis.board.BoardMapper;
import com.web.root.mybatis.kakao.KakaoMapper;
import com.web.root.session.name.MemberSession;

@Service
public class BoardForProgramServiceImpl implements BoardForProgramService, MemberSession {

	@Autowired
	BoardMapper mapper;
	
	@Autowired
	BoardFileService bfs;
	
	@Autowired
	KakaoMapper kakaoMapper;
	

	// 프로그램 입력(DB에 저장)
	@Override
	public String writeSaveForProgram(MultipartHttpServletRequest mul, HttpServletRequest request) {
		
		// form 에서 받은 data 저장
		ProgramBoardDTO programBoardDTO = new ProgramBoardDTO();
		programBoardDTO.setId(mul.getParameter("id"));
		programBoardDTO.setTitle(mul.getParameter("title"));
		programBoardDTO.setPetKind(mul.getParameter("petKind"));
		programBoardDTO.setMateName(mul.getParameter("mateName"));
		programBoardDTO.setContent(mul.getParameter("content"));
		programBoardDTO.setPosition1(mul.getParameter("position1"));
		programBoardDTO.setPosition2(mul.getParameter("position2"));
		programBoardDTO.setStartDate(mul.getParameter("startDate"));
		programBoardDTO.setStartTime(mul.getParameter("startTime"));
		programBoardDTO.setEndDate(mul.getParameter("endDate"));
		programBoardDTO.setEndTime(mul.getParameter("endTime"));
		programBoardDTO.setPriceForProgram(Integer.parseInt(mul.getParameter("priceForProgram")));
		programBoardDTO.setTotalRegisterCount(Integer.parseInt(mul.getParameter("totalRegisterCount")));
		
		// form에서 받은 이미지 파일 받기
		MultipartFile file = mul.getFile("file");
		
		if(file.getSize() != 0) {	// 이미지 파일 있을 경우
			programBoardDTO.setMateImage(bfs.saveFile(file));
		} else {
			programBoardDTO.setMateImage("nan");
		}
		
		int result = 0;
		try {
			result = mapper.writeSaveForProgram(programBoardDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String msg, url;
		if(result ==1) {
			msg = "새글이 등록되었습니다";
			url = "/programBoard/programBoardAllList";
			// =============================== 성수 시작 ==================================
			
			ProgramBoardDTO dto = mapper.selectProgramInfo();
			ProgramMapDTO programMapDTO = new ProgramMapDTO();
			programMapDTO.setWrite_no(dto.getWrite_no());
			programMapDTO.setTitle(dto.getTitle());
			
			// 프로그램 좌표값 요청하기
			KakaoServiceImp1 kakaoServiceImp1 = new KakaoServiceImp1();
			programMapDTO = kakaoServiceImp1.getKakaoMapLatLng(
					dto.getPosition1()+" "+dto.getPosition2()
				  , programMapDTO);
			kakaoMapper.insertProgramMapInfo(programMapDTO);
			
			// =============================== 성수 끝 ==================================
		} else {
			msg = "글 등록 실패...";
			url = "/programBoard/writeFormForProgram";
		}
		
		return bfs.getMessage(request, msg, url);
	}

	@Override
	public void programBoardAllList(Model model, int num, HttpServletRequest request) {
		
		int pageLetter = 10; // 한 페이지 당 글 목록수
		int allCount= mapper.selectProgramBoardCount(); // 전체 글수
		int repeat = allCount/pageLetter; // 마지막 페이지 번호
		if(allCount % pageLetter != 0)
			repeat += 1;
		int end = num * pageLetter;
		int start = end +1 - pageLetter;
		
		// 페이징
		int totalPage = (allCount - 1)/pageLetter + 1;
		int block = 3;
		int startPage = (num - 1)/block*block + 1;
		int endPage = startPage + block - 1;
		if (endPage > totalPage) endPage = totalPage;
		
		
		// 좋아요 불러오기
		List<ProgramBoardDTO> programBoardList = mapper.programBoardAllList(start, end);
		
		// 각 게시판 마다 속한 좋아요 숫자를 불러온다
		for (ProgramBoardDTO programBoardDTO : programBoardList) {
			programBoardDTO.setDibsCount(mapper.getdibsNumByWriteNo(programBoardDTO.getWrite_no()));
		}
		
		// 정보 담기
		model.addAttribute("repeat", repeat);
		model.addAttribute("programBoardList", programBoardList);
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("block", block);
		model.addAttribute("totalPage", totalPage);
		
	}

	@Override
	public ProgramBoardDTO programContentView(Model model, HttpServletRequest request) {
		int write_no = Integer.parseInt(request.getParameter("write_no"));
		
		ProgramBoardDTO programBoardDTO = new ProgramBoardDTO();
		programBoardDTO.setWrite_no(write_no);
		
		return mapper.programContentView(programBoardDTO);
	}

	@Override
	public void programHitplus(ProgramBoardDTO programBoardDTO) {
		mapper.programHitplus(programBoardDTO);
		
	}

	@Override
	public String modifySaveForProgram(MultipartHttpServletRequest mul, HttpServletRequest request) {
		
		// form 에서 받은 정보 DTO에 담기
		ProgramBoardDTO programBoardDTO = new ProgramBoardDTO();
		programBoardDTO.setWrite_no(Integer.parseInt(mul.getParameter("write_no")));
		programBoardDTO.setTitle(mul.getParameter("title"));
		programBoardDTO.setPetKind(mul.getParameter("petKind"));
		programBoardDTO.setMateName(mul.getParameter("mateName"));
		programBoardDTO.setContent(mul.getParameter("content"));
		programBoardDTO.setPosition1(mul.getParameter("position1"));
		programBoardDTO.setPosition2(mul.getParameter("position2"));
		programBoardDTO.setStartDate(mul.getParameter("startDate"));
		programBoardDTO.setStartTime(mul.getParameter("startTime"));
		programBoardDTO.setEndDate(mul.getParameter("endDate"));
		programBoardDTO.setEndTime(mul.getParameter("endTime"));
		programBoardDTO.setState(mul.getParameter("state"));
		programBoardDTO.setPriceForProgram(Integer.parseInt(mul.getParameter("priceForProgram")));
		
		MultipartFile file = mul.getFile("file");
		
		int result = 0;	// modify 성공 여부 확인값
		
		// 수정파일 존재시 file_name 설정 및 실제 파일 저장
		if(file.getSize() != 0) {	// 이미지가 있는지 확인
			
			programBoardDTO.setMateImage(bfs.saveFile(file));
			result = mapper.modifySaveForProgramWithFile(programBoardDTO);
			
		} else {
			programBoardDTO.setMateImage("nan");	// 수정 파일첨부가 없다면 file_name = nan 으로 변경
			result = mapper.modifySaveForProgramWithFile(programBoardDTO); // DB에도 값 nan으로 저장
		}
		// DB에서 Modify 실행
		

		// 실패
		String msg, url;
		if(result == 1) {
			msg = "글이 수정 되었습니다";
		url = "/programBoard/programBoardAllList?num="+ request.getParameter("num");
		} else {
			msg = "글수정 실패~";
		url = "/programBoard/modifyProgramForm?write_no=" + programBoardDTO.getWrite_no();
		}
		return bfs.getMessage(request, msg, url);
	}

	// 프로그램 삭제
	@Override
	public String deleteProgram(Model model, HttpServletRequest request) {
		
		int result = 0; // 삭제 성공유무 결과값 (1: 성공, 0: 실패)
		
		
		int write_no = Integer.parseInt(request.getParameter("write_no"));
		result = mapper.deleteProgram(write_no);
		
		System.out.println(result);
		
		String msg, url;
		//if(result == 1) {
			msg = "글이 삭제 되었습니다";
			url = "/programBoard/programBoardAllList";
//		} else {
//			msg = "글삭제 실패~";
//			url = "/programBoard/programBoardContentView?write_no=" + write_no;
//		}
		return bfs.getMessage(request, msg, url);
	}
	
	// 프로그램 게시판 카테고리 + 검색 조회
	@Override
	public void programBoardSearchForm(String programBoard_state, String programBoard_searchCategory,
			String programBoard_searchKeyword, Model model, int num) {
		List<ProgramBoardDTO> programBoardDTOList = new ArrayList<ProgramBoardDTO>(); // board 검색에 따라 List 담기
		
		// 예약 현황 카테고리에서 전체를 선택할 때 요청값을 "%%"로 변환 -> 쿼리문 like 사용한 검색을 위해서
		if(programBoard_state.equals("total") || programBoard_state == null) {
			programBoard_state = "%%";
		}

		// 검색 키워드를 입력하지 않았을때 빈 요청값을 "%%"로 변환 -> 쿼리문 like 사용한 검색을 위해서
		if(programBoard_searchKeyword.equals("") || programBoard_searchKeyword == null) {
			programBoard_searchKeyword = "%%"; 
		}
		
		
		Map<String, String> map = new HashMap<String, String>();	// Page Count(*)의 크기를 담는 DTO (각 검색 카테고리별로)	
		map.put("state", programBoard_state); 						// 예약 현황 카테고리 옵션 저장
		map.put("keyword",programBoard_searchKeyword);  			// 검색 키워드 저장
		
		
		// 검색 카테고리 선택  -> 값 저장
		if(programBoard_searchCategory.equals("title")) { // 제목으로 검색 
			map.put("title",programBoard_searchKeyword); 		// 제목열에 키워드 값 저장
			map.put("content", "%%"); 					// 나머지 전체 셋팅
			map.put("id","%%"); 						// 나머지 전체 셋팅
			map.put("mateName", "%%");					// 나머지 전체 셋팅
			
		}else if(programBoard_searchCategory.equals("content")) { // 내용으로 검색
			
			map.put("content", programBoard_searchKeyword); 	// 내용열에 키워드 값 저장	
			map.put("title","%%");					 	// 나머지 전체 셋팅
			map.put("id","%%"); 						// 나머지 전체 셋팅
			map.put("mateName", "%%");					// 나머지 전체 셋팅
			
		}else if(programBoard_searchCategory.equals("id")) {  // 작성자로 검색
			
			map.put("id", programBoard_searchKeyword);			// 아이디열에 키워드 값 저장
			map.put("title", "%%");						// 나머지 전체 셋팅
			map.put("content", "%%"); 					// 나머지 전체 셋팅
			map.put("mateName", "%%");					// 나머지 전체 셋팅
			
		}else if(programBoard_searchCategory.equals("mateName")) {  // 강아지 이름로 검색
			
			map.put("mateName", programBoard_searchKeyword);			// 아이디열에 키워드 값 저장
			map.put("id","%%"); 						// 나머지 전체 셋팅
			map.put("title", "%%");						// 나머지 전체 셋팅
			map.put("content", "%%"); 					// 나머지 전체 셋팅
		}

		
		int pageLetter = 10;  // 한 페이지 당 글 목록수
		int allCount = mapper.programBoardCountCategory(map); // 카테고리가 ex)제목 등 해당 목록 전체 수 
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
		
		// 페이징 범위 저장
		map.put("s", Integer.toString(start));  // 시작 저장
		map.put("e",Integer.toString(end));		 // 끝 저장
		   
		// 상단에 만들어둔 List 변수에 내용들을 담아 리스트 불러오기
		programBoardDTOList = mapper.programBoardSearchFormCountList(map);       
			
		model.addAttribute("repeat", repeat);
		model.addAttribute("programBoardList", programBoardDTOList); // 시작과 끝 페이지 안에서 내용 가져오기
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("block", block);
		model.addAttribute("totalPage", totalPage);
		
	}

	//카카오 페이 승인 시 프로그램 결재 완료 후 승인 대기로 이동
	@Override
	public String paidProgramContentView(HttpServletRequest request, HttpSession session) {
		
		// url 결과값 받기
		String write_no = request.getParameter("write_no");
		String num = request.getParameter("num");
		String title = request.getParameter("title");
		String paymentId = (String) session.getAttribute(LOGIN);
		String tid = (String)session.getAttribute("tid");
		
		PaidProgramInfoDTO paidProgramInfoDTO = new PaidProgramInfoDTO();
		paidProgramInfoDTO.setId(paymentId);
		paidProgramInfoDTO.setTitle(title);
		paidProgramInfoDTO.setWrite_no(Integer.parseInt(write_no));
		paidProgramInfoDTO.setNum(Integer.parseInt(num));
		paidProgramInfoDTO.setTid(tid);
		
		int result = 0; // "결재 완료 후 승인 대기" 로 변경 성공유무 결과값 (1: 성공, 0: 실패)
		
		// 받은 결과값으로 결제된 prgroamBoardDTO 정보 불러오기
		ProgramBoardDTO programBoardDTOToGat = new ProgramBoardDTO();
		programBoardDTOToGat.setWrite_no(Integer.parseInt(write_no));
		ProgramBoardDTO programBoardDTO = mapper.programContentView(programBoardDTOToGat);
		
		int IsSameCurrentWithTotal = programBoardDTO.getCurrentRegisterCount()+1; // 이번 결재가 끝나면 신청한 사람이 총원과 같은지 판단하는 숫자
		
		// DB 연동 진행
		if( IsSameCurrentWithTotal == programBoardDTO.getTotalRegisterCount()) { // 이번 신청이 끝나면 신청한 사람의 숫자와 총원이 같을 때
			result = mapper.lastPaidProgramContentView(Integer.parseInt(write_no));
		} else {	// 아직 신청할 수 있는 인원이 남았을 때
			result = mapper.paidProgramContentView(Integer.parseInt(write_no));
		}
		
		
		String msg, url;
		if(result == 1) {
			// 성수 : DB에 결제한 프로그램 정보 저장.
			mapper.insertPaidProgramInfo(paidProgramInfoDTO);
			msg = "결제가 최종완료되었습니다";
			url = "/programBoard/programContentView?write_no=" + write_no + "&num=" + num;
		} else {
			msg = "결제 결과 연동 실패!!";
			url = "/programBoard/programContentView?write_no=" + write_no + "&num=" + num;
		}
		return bfs.getMessage(request, msg, url);
	}
	
	@Override
	public String kakaoPayFail(HttpServletRequest request) {
		String write_no = request.getParameter("write_no");
		String num = request.getParameter("num");
		
		String msg = "결제 오류. 다시 시도해 주세요.";
		String url = "/programBoard/programContentView?write_no=" + write_no + "&num=" + num;
		
		return bfs.getMessage(request, msg, url);
	}
	
	// 프로그램 보드 결재 게시판 기능 관련 ------------------------
	
	// 게시판 기준 Paid ProgramInfoDTO 불러오기
	@Override
	public List<PaidProgramInfoDTO> paidProgramInfoByWriteNo(Map<String, Object> map) {
		return mapper.paidProgramInfoByWriteNo(map);
	}
	
	// host 기준 Paid ProgramInfoDTO 불러오기
	@Override
	public List<PaidProgramInfoDTO> paidProgramInfoById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	// MyPage board List 기능
	@Override
	public void myDibsProgramBoardAllList(Model model, int num, HttpServletRequest request, String id) {
		
		// 한 페이지 정보 설정
		int pageLetter = 10; // 한 페이지 당 글 목록수
		int allCount= mapper.selectMyDibsProgramBoardCount(id); // 내가 찜한 전체 글수
		int repeat = allCount/pageLetter; // 마지막 페이지 번호
		if(allCount % pageLetter != 0)
			repeat += 1;
		int end = num * pageLetter;
		int start = end +1 - pageLetter;
		
		// 페이징 정보 설정
		int totalPage = (allCount - 1)/pageLetter + 1;
		int block = 3;
		int startPage = (num - 1)/block*block + 1;
		int endPage = startPage + block - 1;
		if (endPage > totalPage) endPage = totalPage;

		// 정보 담기
		model.addAttribute("repeat", repeat);
		model.addAttribute("programBoardList", mapper.myDibsProgramBoardAllList(start, end, id));
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("block", block);
		model.addAttribute("totalPage", totalPage);
	}
	
	// 호스트의 마이페이지에서 프로그램 결재 취소 요청 보내기 기능
	@Override
	public int paidCancelRequestByTid(String tid) {
		return mapper.paidCancelRequestByTid(tid);
	}	
	
	//============================ 윤희 시작 =============================================
	


	@Override
	public ProgramBoardDTO programBoardInformation(String id) {
		
		ProgramBoardDTO programBoardDTO = mapper.programBoardInformation(id);
		return programBoardDTO;
	}
	
	
	//============================ 윤희 끝 ===============================================
	
	
	
	
	
	
	
	
}
