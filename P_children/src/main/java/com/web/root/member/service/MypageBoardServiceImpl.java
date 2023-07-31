package com.web.root.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.web.root.board.dto.BoardDTO;
import com.web.root.board.dto.PaidProgramInfoDTO;
import com.web.root.board.dto.ProgramBoardDTO;
import com.web.root.mybatis.board.BoardMapper;
import com.web.root.mybatis.mypageBoard.MypageBoardMapper;

@Service
public class MypageBoardServiceImpl implements MypageBoardService {

	@Autowired
	private MypageBoardMapper mapper;
	
	// 진욱 추가 (06/19) 시작 -------------------------------------------------
	@Autowired
	private BoardMapper boardMapper;
	
	// 진욱 추가 (06/19) 끝 --------------------------------------------------
	
	
	//============================ 최윤희 시작 ===========================================
	
	// 마이페이지 작성글 목록 (커뮤니티)
	@Override
	public void mypageBoardCommunityWriteList(String id, Model m, int num) {
		
		List<BoardDTO> myboardListPageDTO = new ArrayList<BoardDTO>();
		BoardDTO myboardDTO = new BoardDTO();
		
		int pageLetter = 10;  // 한 페이지 당 글 목록수
		int allCount = mapper.mypageBoardAllCount(id); // 해당 id DB에 담겨있는 전체 글 수
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
		
		myboardDTO.setId(id);
		myboardDTO.setStart(start);
		myboardDTO.setEnd(end);
		
		myboardListPageDTO = mapper.mypageBoardList(myboardDTO);
		
		m.addAttribute("repeat", repeat);
		m.addAttribute("mypageBoardList", myboardListPageDTO); // 시작과 끝 페이지 안에서 내용 가져오기
		m.addAttribute("endPage", endPage);
		m.addAttribute("startPage", startPage);
		m.addAttribute("block", block);
		m.addAttribute("totalPage", totalPage);
	}
	
	
	// 마이페이지 작성글 목록 (프로그램: host만)
	@Override
	public void mypageBoardProgramWriteList(String id, Model m, int num) {
		
		List<ProgramBoardDTO> myboardProgramListPageDTO = new ArrayList<ProgramBoardDTO>();	// 목록 담기
		ProgramBoardDTO myboardProgramDTO = new ProgramBoardDTO();
		
		int pageLetter = 10;  // 한 페이지 당 글 목록수
		int allCount = mapper.mypageBoardProgramAllCount(id); // 해당 id DB에 담겨있는 전체 글 수
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
		
		myboardProgramDTO.setId(id);
		myboardProgramDTO.setStart(start);
		myboardProgramDTO.setEnd(end);
		
		myboardProgramListPageDTO = mapper.mypageBoardProgramList(myboardProgramDTO);
		
		// 진욱 추가 (06/19) 시작 -------------------------------------------------
		for (ProgramBoardDTO programBoardDTO : myboardProgramListPageDTO) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("write_no", programBoardDTO.getWrite_no());
			programBoardDTO.setPaidProgramInfoDTO(boardMapper.paidProgramInfoByWriteNo(map));
			List<PaidProgramInfoDTO> ListOfPaidProgramInfoDTO = programBoardDTO.getPaidProgramInfoDTO();
			//for (PaidProgramInfoDTO paidProgramInfoDTO : ListOfPaidProgramInfoDTO) {
			//	System.out.println(paidProgramInfoDTO.getId());
			//}
			
		}
		
		// 진욱 추가 (06/19) 끝 --------------------------------------------------
		m.addAttribute("repeat", repeat);
		m.addAttribute("mypageBoardProgramList", myboardProgramListPageDTO ); // 시작과 끝 페이지 안에서 내용 가져오기
		m.addAttribute("endPage", endPage);
		m.addAttribute("startPage", startPage);
		m.addAttribute("block", block);
		m.addAttribute("totalPage", totalPage);
		
	}
	
	//============================ 최윤희 끝 ===========================================
	
	
	// ======================================= 박성수 시작 =================================================
	
	@Override
	public void selectPaidProgramList(String id, int pg_num, Model model) {
		// num = 현재 페이지
				int pageLetter = 5; // 한 페이지 당 글 목록수
				int allCount= mapper.selectPaidProgramInfoCount(); // 전체 글수
				int repeat = allCount/pageLetter; // 마지막 페이지 번호
				if(allCount % pageLetter != 0)
					repeat += 1;
				int end = pg_num * pageLetter; // start ~ end -> 각 페이지에 불러올 글을 위한 쿼리용 숫자.
				int start = end +1 - pageLetter;
				
				// 페이징
				int totalPage = (allCount - 1)/pageLetter + 1;
				int block = 3;
				int startPage = (pg_num - 1)/block*block + 1;
				int endPage = startPage + block - 1;
				if (endPage > totalPage) endPage = totalPage;
			
				model.addAttribute("repeat", repeat);
				model.addAttribute("paidProgramList", mapper.selectPaidProgramList(id, start, end));
				model.addAttribute("endPage", endPage);
				model.addAttribute("startPage", startPage);
				model.addAttribute("block", block);
				model.addAttribute("totalPage", totalPage);
	}
	
	@Override
	public String updateCancelRequest(HttpServletRequest request) {
		String result = "";
		String id = request.getParameter("id");
		int write_no = Integer.parseInt(request.getParameter("write_no"));
		int res = mapper.updateCancelRequest(id, write_no);
		
		if(res == 1) {
			result = "ok";
		} else {
			result = "false";
		}
		
		return result;
	}
	
	// ======================================= 박성수 끝 =================================================
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}













