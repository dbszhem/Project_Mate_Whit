package com.web.root.board.service;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.root.board.dto.BoardDTO;
import com.web.root.board.dto.BoardDibsDTO;
import com.web.root.board.dto.BoardRepDTO;
import com.web.root.board.dto.NoticeBoardDTO;
import com.web.root.board.dto.PaidProgramInfoDTO;
import com.web.root.mybatis.board.BoardMapper;
import com.web.root.qna.dto.QnaDTO;
import com.web.root.qna.dto.Qna_RepDTO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper mapper;
	
	@Autowired
	BoardFileService bfs;
	

	//============================ 주진욱 시작 ===========================================

	@Override
	public void boardAllList(Model model, int num, HttpServletRequest request) {
		
		int pageLetter = 10; // 한 페이지 당 글 목록수
		int allCount= mapper.selectBoardCount(); // 전체 글수
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
		List<BoardDTO> boardList = mapper.boardAllList(start, end);
		
		// 각 게시판 마다 속한 좋아요 숫자를 불러온다
		for (BoardDTO boardDTO : boardList) {
			boardDTO.setDibsCount(mapper.getdibsNumByWriteNo(boardDTO.getWrite_no()));
			//System.out.println("좋아요 숫자 :" + mapper.getdibsNumByWriteNo(boardDTO.getWrite_no()));
		}
		
	
		// 정보 담기
		model.addAttribute("repeat", repeat);
		model.addAttribute("boardList", boardList);
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("block", block);
		model.addAttribute("totalPage", totalPage);
	}
	

	@Override
	public String writeSave(MultipartHttpServletRequest mul, HttpServletRequest request) {

		BoardDTO dto = new BoardDTO();
		dto.setId(mul.getParameter("id"));
		dto.setTitle(mul.getParameter("title"));
		dto.setContent(mul.getParameter("content"));
		dto.setCategory(mul.getParameter("category"));
		MultipartFile file = mul.getFile("file");
		
		if(file.getSize() != 0) {	// 이미지가 있는지 확인
			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
//			Calendar calendar = Calendar.getInstance();
//			String sysFileName = sdf.format(calendar.getTime());
//			sysFileName += file.getOriginalFilename();
//			
//			// 실제 폴더에 파일 저장
//			File saveFile = new File(IMAGE_REPO+"/"+sysFileName);
//			
//			// DB에 파일 이름 정보 저장
			
			dto.setFile_name(bfs.saveFile(file));
			
		} else {
			dto.setFile_name("nan");
		}
		
		int result = 0;
		try {
			result = mapper.writeSave(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String msg, url;
		if(result == 1) {
			msg = "새글이 등록 되었습니다";
			url = "/board/boardAllList";
		} else {
			msg = "글등록 실패~";
			url = "/board/writeForm";
		}
		return bfs.getMessage(request, msg, url);
	}


	@Override
	public BoardDTO contentView(Model model, HttpServletRequest request) {
		
		int write_no = Integer.parseInt(request.getParameter("write_no"));
		
		BoardDTO dto = new BoardDTO();
		dto.setWrite_no(write_no);
		
		return mapper.contentView(dto);
	}

	@Override
	public void hitplus(BoardDTO dto) {
		
		mapper.hitplus(dto);
		
	}

	@Override
	public String modifySave(MultipartHttpServletRequest mul, HttpServletRequest request) {

		// form 에서 받은 정보 DTO에 담기
		BoardDTO dto = new BoardDTO();
		dto.setId(mul.getParameter("id"));
		dto.setCategory(mul.getParameter("category"));
		dto.setTitle(mul.getParameter("title"));
		dto.setContent(mul.getParameter("content"));
		String no = mul.getParameter("write_no");
		dto.setWrite_no(Integer.parseInt(no));
		MultipartFile file = mul.getFile("file");

		int result = 0;	// modify 성공 여부 확인값
		
		// 수정파일 존재시 file_name 설정 및 실제 파일 저장
		if(file.getSize() != 0) {	// 이미지가 있는지 확인
			
			dto.setFile_name(bfs.saveFile(file));
			result = mapper.modifySaveWithFile(dto);
			
		} else {
			dto.setFile_name("nan");
			result = mapper.modifySave(dto);
		}
		// DB에서 Modify 실행
		
		
		// 실패
		String msg, url;
		if(result == 1) {
			msg = "글이 수정 되었습니다";
			url = "/board/boardAllList?num="+ request.getParameter("num");
		} else {
			msg = "글수정 실패~";
			url = "/board/modifyForm?write_no=" + dto.getWrite_no() + "&num=" + request.getParameter("num");
		}
		return bfs.getMessage(request, msg, url);
	}
	
	public String deleteBoard(Model model, HttpServletRequest request) {
		
		int result = 0;
		int write_no = Integer.parseInt(request.getParameter("write_no"));
		result = mapper.deleteBoard(write_no);
		
		String msg, url;
		if(result == 1) {
			msg = "글이 삭제 되었습니다";
			url = "/board/boardAllList";
			// 선생님은 이자리에 bfs.delete(image_file_name); 을 넣으셨다.
		} else {
			msg = "글삭제 실패~";
			url = "/board/contentView?write_no=" + write_no;
		}
		return bfs.getMessage(request, msg, url);
	}
	
	@Override
	public void selectingCategory(Model model,String category, int num) {
		
		int pageLetter = 10; // 한 페이지 당 글 목록수
		int allCount= mapper.selectBoardCountByCategory(category); // 전체 글수
		//System.out.println("allCount 는!!" + allCount);
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

	
		model.addAttribute("repeat", repeat);
		model.addAttribute("boardList", mapper.boardAllListByCategory(category,start, end));
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("block", block);
		model.addAttribute("totalPage", totalPage);	 
	}
	
	// 자유게시판 카테고리 + 검색 조회
	@Override
	public void boardSearchForm(String board_category, String board_searchCategory, String board_searchKeyword,
			Model model, int num) {
		List<BoardDTO> boardDTOList = new ArrayList<BoardDTO>(); // board 검색에 따라 List 담기
		
		// 카테고리 전체를 선택할 때 요청값을 "%%"로 변환 -> 쿼리문 like 사용한 검색을 위해서
		if(board_category.equals("total") || board_category == null) {
			board_category = "%%";
		}

		// 검색 키워드를 입력하지 않았을때 빈 요청값을 "%%"로 변환 -> 쿼리문 like 사용한 검색을 위해서
		if(board_searchKeyword.equals("") || board_searchKeyword == null) {
			board_searchKeyword = "%%"; 
		}
		
		
		Map<String, String> map = new HashMap<String, String>();	// Page Count(*)의 크기를 담는 DTO (각 검색 카테고리별로)	
		map.put("category", board_category); 			// 카테고리 옵션 저장
		map.put("keyword",board_searchKeyword);  		// 검색 키워드 저장
		
		
		// 검색 카테고리 선택  -> 값 저장
		if(board_searchCategory.equals("title")) { // 제목으로 검색 
			map.put("title",board_searchKeyword); 		// 제목열에 키워드 값 저장
			map.put("content", "%%"); 					// 나머지 전체 셋팅
			map.put("id","%%"); 						// 나머지 전체 셋팅
			
		}else if(board_searchCategory.equals("content")) { // 내용으로 검색
			
			map.put("content", board_searchKeyword); 	// 내용열에 키워드 값 저장	
			map.put("title","%%");					 	// 나머지 전체 셋팅
			map.put("id","%%"); 						// 나머지 전체 셋팅
			
		}else if(board_searchCategory.equals("id")) {  // 작성자로 검색
			
			map.put("id", board_searchKeyword);			// 아이디열에 키워드 값 저장
			map.put("title", "%%");						// 나머지 전체 셋팅
			map.put("content", "%%"); 					// 나머지 전체 셋팅
		}

		
		int pageLetter = 10;  // 한 페이지 당 글 목록수
		int allCount = mapper.boardCountCategory(map); // 카테고리가 ex)제목 등 해당 목록 전체 수 
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
		boardDTOList = mapper.boardSearchFormCountList(map);       
			
		model.addAttribute("repeat", repeat);
		model.addAttribute("boardList", boardDTOList); // 시작과 끝 페이지 안에서 내용 가져오기
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("block", block);
		model.addAttribute("totalPage", totalPage);
		
	}
	
	

	// 댓글 기능 ---------------------------------------------------------
	



	@Override
	public int addReply(Map<String, Object> map) {
		int result = mapper.addReply(map);
		return result;
	}

	@Override
	public List<BoardRepDTO> getRepList(int write_group) {
		// TODO Auto-generated method stub
		return mapper.getRepList(write_group);
	}


	@Override
	public String deleteReply(HttpServletRequest request) {
		BoardRepDTO dto = new BoardRepDTO();
		dto.setReply_no(Integer.parseInt(request.getParameter("reply_no")));
		dto.setWrite_group(Integer.parseInt(request.getParameter("write_group")));
		
		System.out.println(dto.getReply_no() + " , " + dto.getWrite_group());
		int su = mapper.deleteReply(dto);
		
		// contentView page 페이징 번호
		String num = request.getParameter("num");
		
		String msg, url;
		if(su == 1) {
			msg = "댓글이 삭제 되었습니다";
			url = "/board/contentView?write_no=" + request.getParameter("write_group") + "&num=" + num;
			// 선생님은 이자리에 bfs.delete(image_file_name); 을 넣으셨다.
		} else {
			msg = "댓글 삭제 실패~";
			url = "/board/contentView?write_no=" + request.getParameter("write_group") + "&num=" + num;
		}
		return bfs.getMessage(request, msg, url);
		
	}


	@Override
	public String updateReply(HttpServletRequest request) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("write_no", request.getParameter("write_no"));
		map.put("updateReply_no", request.getParameter("updateReply_no"));
		map.put("updateContent", request.getParameter("updateContent"));
		map.put("id", request.getParameter("id"));
		
		// contentView page 페이징 번호
		String num = request.getParameter("num");
		
		int su = mapper.updateReply(map);
		String write_noStr = (String) map.get("write_no");
		int write_no = Integer.parseInt(write_noStr);
	
		String msg, url;
		if(su == 1) {
			msg = "댓글이 수정 되었습니다";
			url = "/board/contentView?write_no=" + write_no + "&num=" + num;
		} else {
			msg = "댓글 수정 실패~";
			url = "/board/contentView?write_no=" + write_no + "&num=" + num;
		}
		return bfs.getMessage(request, msg, url);
	}


	// 대댓글 기능 -----------------------------------------------------------------------
	@Override
	public List<BoardRepDTO> getReCommentList(int reply_no) {
		return mapper.getReCommentList(reply_no);
	}


	@Override
	public int addReComment(Map<String, Object> map) {
		return mapper.addReComment(map);
	}


	@Override
	public String updateReComment(HttpServletRequest request) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("write_no", request.getParameter("write_no"));
		map.put("updateReply_no", request.getParameter("updateReCommentReply_no"));
		map.put("updateContent", request.getParameter("updateReCommentContent"));
		map.put("id", request.getParameter("id"));

		String num = request.getParameter("num");
		
		int su = mapper.updateReComment(map);
		String write_noStr = (String) map.get("write_no");
		int write_no = Integer.parseInt(write_noStr);
		
		
		String msg, url;
		if(su == 1) {
			msg = "대댓글이 수정 되었습니다";
			url = "/board/contentView?write_no=" + write_no + "&num=" + num;
		} else {
			msg = "대댓글 수정 실패~";
			url = "/board/contentView?write_no=" + write_no + "&num=" + num;
		}
		return bfs.getMessage(request, msg, url);
	}

	
	// 찜하기 기능 -----------------------------------------------------------------------
	
	// 찜하기 객체 불러오기 기능(유저 아이디와 보드 넘버로)
	@Override
	public BoardDibsDTO getDibsByIdWriteNo(Map<String, Object> map) {
		return mapper.getDibsByIdWriteNo(map);
	}
	
	// 찜하기 토글 기능
	@Override
	public int toggleDibs(Map<String, Object> map) {
		
		// 처음으로 하트 누른 것인지 확인 ( 0: 처음이다, 1: 누른 적이 있다 )
		int dibsExsistance = mapper.dibsExsistance(map);
		
		// 처음 하트를 누르는 경우
		if(dibsExsistance == 0) { 
			int insertResult = mapper.insertDibs(map); // dibs 테이블에 dib_state 1 로 생성한다
			return insertResult;
		}
		
		// 누른 적이 있는 경우
		if(dibsExsistance == 1) {
			BoardDibsDTO boardDibsDTO = getDibsByIdWriteNo(map); // 찜하기 객체 불러오기
			
			if(boardDibsDTO.getDibs_state() == 1) { // 1: 이미 찜한 경우 일 때
				map.put("dibs_state", 0);
				mapper.updateDib(map); // dib_sate를 0으로 수정한다
				return 0;
			} else {	// 0 : 찜하고 취소한 경우
				map.put("dibs_state", 1);
				mapper.updateDib(map); // dib_sate를 1로 수정한다
				return 1;
			}
			
		}
		
		return dibsExsistance;
	}
	
	// MyPage board List 기능
	@Override
	public void myDibsBoardAllList(Model model, int num, HttpServletRequest request, String id) {
		
		// 한 페이지 정보 설정
		int pageLetter = 10; // 한 페이지 당 글 목록수
		int allCount= mapper.selectMyDibsBoardCount(id); // 내가 찜한 전체 글수
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
		model.addAttribute("boardList", mapper.myDibsBoardAllList(start, end, id));
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("block", block);
		model.addAttribute("totalPage", totalPage);
	}
	
	// 게시판이 받은 찜의 갯수 가져오기
	@Override
	public int getdibsNumByWriteNo(int write_no) {
		return mapper.getdibsNumByWriteNo(write_no);
	}
	
	//============================ 주진욱 끝 ===========================================
	
	
	
	
	
	//============================ 최윤희 시작 ===========================================
	
	@Override
	public void noticeBoardAllList(Model model, int num, HttpServletRequest request) {
		
		int pageLetter = 10;  // 한 페이지 당 글 목록수
		int allCount = mapper.selectNoticeBoardCount(); // DB에 담겨있는 전체 글 수
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
		model.addAttribute("noticeBoardList", mapper.noticeBoardAllList(start, end)); // 시작과 끝 페이지 안에서 내용 가져오기
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("block", block);
		model.addAttribute("totalPage", totalPage);
		
	}

	// 공지사항 게시글 보기
	public NoticeBoardDTO noticeBoardContentView(HttpServletRequest request) {
		
		int write_no = Integer.parseInt(request.getParameter("write_no"));  // 요청온 글 번호를 받고
		
		NoticeBoardDTO noticeBoardDTO = new NoticeBoardDTO();
		noticeBoardDTO.setWrite_no(write_no);  // NoticeBoardDTO 안에 글번호 저장
		
		return mapper.noticeBoardContentView(noticeBoardDTO);
	}
	
	// 조회수 증가
	@Override
	public void noticeBoardHitplus(NoticeBoardDTO noticeBoardDTO) {
		mapper.noticeBoardHitplus(noticeBoardDTO);
	}
	
	// 공지사항 게시글 작성
	@Override
	public String noticeBoardWriteSave(MultipartHttpServletRequest mul, HttpServletRequest request) {
		
		NoticeBoardDTO noticeBoardDTO = new NoticeBoardDTO();
		// 요청온 내용 저장
		noticeBoardDTO.setId(mul.getParameter("id"));				// 작성자(아이디) 저장
		noticeBoardDTO.setCategory(mul.getParameter("category"));	// 카테고리 선택
		noticeBoardDTO.setTitle(mul.getParameter("title"));			// 제목 저장
		noticeBoardDTO.setContent(mul.getParameter("content"));		// 내용 저장
		MultipartFile file = mul.getFile("file");					// 파일 저장
		
		// 요청받은 파일이 있으면
		if(file.getSize() != 0) {
			noticeBoardDTO.setFile_name(bfs.noticeBoardSaveFile(file));
		} else {
			noticeBoardDTO.setFile_name("nan");
		}
		
		int result = 0;
		try {
			result = mapper.noticeBoardWriteSave(noticeBoardDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String msg, url;
		if(result == 1) {
			msg = "공지사항 게시글이 등록되었습니다.";
			url = "/board/notice/noticeBoardAllList";
		} else {
			msg = "게시글 등록이 실패하였습니다.";
			url = "/board/notice/noticeBoardWriteForm";
		}
		return bfs.getNoticeBoardMessage(request, msg, url);

	}
		
		
	// 공지사항 게시글 수정 작성
	@Override
	public String noticeBoardModifySave(MultipartHttpServletRequest mul, HttpServletRequest request) {
		
		// noticeBoardModifyForm 에서 받은 정보 DTO에 담기
		NoticeBoardDTO noticeBoardDTO = new NoticeBoardDTO();
		noticeBoardDTO.setTitle(mul.getParameter("title"));			// 수정 타이틀 저장
		noticeBoardDTO.setCategory(mul.getParameter("category"));	// 수정 카테고리 저장
		noticeBoardDTO.setContent(mul.getParameter("content"));		// 수정 내용 저장
		noticeBoardDTO.setWrite_no(Integer.parseInt(mul.getParameter("write_no")));  // 수정 글번호
		MultipartFile file = mul.getFile("file");  					// 기존 파일에서 수정 파일 저장

		int result = 0;	// 수정 성공 여부 확인
		
		// 수정파일 존재시 file_name 설정 및 실제 파일 저장
		if(file.getSize() != 0) {	// 이미지가 있는지 확인
			noticeBoardDTO.setFile_name(bfs.noticeBoardSaveFile(file));
			result = mapper.noticeBoardModifySaveWithFile(noticeBoardDTO);
			
		} else {
			noticeBoardDTO.setFile_name("nan");
			result = mapper.noticeBoardModifySaveWithFile(noticeBoardDTO);
		}
		
		// 성공, 실패에 따라 url + msg 반환
		String msg, url;
		if(result == 1) {
			msg = "공지사항 게시글이 수정되었습니다";
			url = "/board/notice/noticeBoardAllList";
		} else {
			msg = "게시글 수정이 실패하였습니다.";
			url = "/board/notice/noticeBoardModifyForm?write_no=" + noticeBoardDTO.getWrite_no();
		}
		return bfs.getNoticeBoardMessage(request, msg, url);
	}

	
	// 공지사항 게시글 삭제
	@Override
	public String noticeBoardDelete(HttpServletRequest request) {
		
		int result = 0;
		
		int write_no = Integer.parseInt(request.getParameter("write_no"));
		
		result = mapper.noticeBoardDelete(write_no);  // 삭제 성공, 실패 값 받기
		
		// 삭제 성공, 실패 url + 문자열 반환
		String msg, url;
		if(result == 1) {
			msg = "공지 게시글이 삭제되었습니다.";
			url = "/board/notice/noticeBoardAllList";
			// 선생님은 이자리에 bfs.delete(image_file_name); 을 넣으셨다.
		} else {
			msg = "공지 게시글 삭제가 실패되었습니다.";
			url = "/board/notice/noticeBoardContentView?write_no=" + write_no;
		}
		return bfs.getNoticeBoardMessage(request, msg, url);
	}
	
	
	
	// 공지사항 카테고리 + 검색 조회
	@Override
	public void noticeSearchForm(String notice_category, String notice_searchCategory, String notice_searchKeyword, Model m, int num) {	
		
		List<NoticeBoardDTO> noticeBoardDTO = new ArrayList<NoticeBoardDTO>(); // notice 검색에 따라 List 담기
		
		// 카테고리 전체를 선택할 때 요청값을 "%%"로 변환 -> 쿼리문 like 사용한 검색을 위해서
		if(notice_category.equals("noticeAll") || notice_category == null) {
			notice_category = "%%";
		}

		// 검색 키워드를 입력하지 않았을때 빈 요청값을 "%%"로 변환 -> 쿼리문 like 사용한 검색을 위해서
		if(notice_searchKeyword.equals("") || notice_searchKeyword == null) {
			notice_searchKeyword = "%%"; 
		}
		
		
		NoticeBoardDTO notice_pageDTO = new NoticeBoardDTO();	// Page Count(*)의 크기를 담는 DTO (각 검색 카테고리별로)	
		notice_pageDTO.setCategory(notice_category); 			// 카테고리 옵션 저장
		notice_pageDTO.setKeyword(notice_searchKeyword);  		// 검색 키워드 저장
		
		
		// 검색 카테고리 선택  -> 값 저장
		if(notice_searchCategory.equals("title")) { // 제목으로 검색 
			notice_pageDTO.setTitle(notice_searchKeyword); 	// 제목열에 키워드 값 저장
			notice_pageDTO.setContent("%%"); 				// 나머지 전체 셋팅
			notice_pageDTO.setId("%%"); 					// 나머지 전체 셋팅
			
		}else if(notice_searchCategory.equals("content")) { // 내용으로 검색
			
			notice_pageDTO.setContent(notice_searchKeyword); // 내용열에 키워드 값 저장	
			notice_pageDTO.setTitle("%%");					 // 나머지 전체 셋팅
			notice_pageDTO.setId("%%");						 // 나머지 전체 셋팅
			
		}else if(notice_searchCategory.equals("id")) {  // 작성자로 검색
			
			notice_pageDTO.setId(notice_searchKeyword);		// 아이디열에 키워드 값 저장
			notice_pageDTO.setTitle("%%");					// 나머지 전체 셋팅
			notice_pageDTO.setContent("%%");				// 나머지 전체 셋팅
		}

		
		int pageLetter = 10;  // 한 페이지 당 글 목록수
		int allCount = mapper.noticeBoardCountCategory(notice_pageDTO); // 카테고리가 ex)제목 등 해당 목록 전체 수 
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
		notice_pageDTO.setStart(start);  // 시작 저장
		notice_pageDTO.setEnd(end);		 // 끝 저장
		   
		// 상단에 만들어둔 List 변수에 내용들을 담아 리스트 불러오기
		noticeBoardDTO = mapper.noticeSearchFormCountList(notice_pageDTO);       
			
		m.addAttribute("repeat", repeat);
		m.addAttribute("noticeBoardList", noticeBoardDTO); // 시작과 끝 페이지 안에서 내용 가져오기
		m.addAttribute("endPage", endPage);
		m.addAttribute("startPage", startPage);
		m.addAttribute("block", block);
		m.addAttribute("totalPage", totalPage);

		
	}
			
	//============================ 최윤희 끝 ===========================================
	
	// 청규
	
	// 문의 관리
	@Override
	public void manager_qna(Model model, int num) {
		int pageLetter = 10; 
		int allCount = mapper.selectQnaCount_manager(); 
		int repeat = allCount/pageLetter;  
		if(allCount % pageLetter != 0)
			repeat += 1;
		int end = num * pageLetter;
		int start = end + 1 - pageLetter;
		model.addAttribute("repeat", repeat);
		model.addAttribute("qnaList", mapper.manager_qna(start, end));		
		
	}




	// 나의 문의
	@Override
	public void member_qna(Model model, int num, String id) {
		
		int pageLetter = 10; 
		int allCount = mapper.selectQnaCount_member(id); 
		int repeat = allCount/pageLetter;  
		if(allCount % pageLetter != 0)
			repeat += 1;
		int end = num * pageLetter;
		int start = end + 1 - pageLetter;
		model.addAttribute("repeat", repeat);
		model.addAttribute("qnaList", mapper.member_qna(start, end, id));				
		
	}
	

	//  문의 작성
	public void member_write_save(HttpServletRequest request) {
		QnaDTO dto = new QnaDTO();
		dto.setId(request.getParameter("id"));
		dto.setTitle(request.getParameter("title"));
		dto.setContent(request.getParameter("content"));
		
		mapper.member_write_save(dto);
	}
	
	//  문의 답변 작성
	public void manager_write_save(HttpServletRequest request, Model model) {
		int write_no = Integer.parseInt(request.getParameter("write_no"));
		Qna_RepDTO dto = new Qna_RepDTO();
		dto.setContent(request.getParameter("content"));
		dto.setWrite_group(write_no);
	
		mapper.manager_write_save(dto);
		model.addAttribute("qna_reply", dto);
	}
	
	
	// 글 확인
	@Override
	public QnaDTO contentView_qna(Model model, HttpServletRequest request) {
		
		int write_no = Integer.parseInt(request.getParameter("write_no"));
		
		QnaDTO dto = new QnaDTO();
		dto.setWrite_no(write_no);
		
		return mapper.contentView_qna(dto);
	}

	@Override
	public Qna_RepDTO contentView_rep_qna(Model model, HttpServletRequest request) {
		
		int write_group = Integer.parseInt(request.getParameter("write_no"));
		
		Qna_RepDTO dto = new Qna_RepDTO();
		dto.setWrite_group(write_group);
		
		return mapper.contentView_rep_qna(dto);
	}


	@Override
	public void qna_state(HttpServletRequest request) {
		int write_no = Integer.parseInt(request.getParameter("write_no"));
		mapper.qna_state(write_no);
		
	}
	
	
}
