package com.web.root.mybatis.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.web.root.board.dto.BoardDTO;
import com.web.root.board.dto.BoardDibsDTO;
import com.web.root.board.dto.BoardRepDTO;
import com.web.root.board.dto.NoticeBoardDTO;
import com.web.root.board.dto.PaidProgramInfoDTO;
import com.web.root.board.dto.ProgramBoardDTO;
import com.web.root.qna.dto.QnaDTO;
import com.web.root.qna.dto.Qna_RepDTO;

public interface BoardMapper {
	
	//============================ 주진욱 시작 ===========================================
	
	public List<BoardDTO> boardAllList(@Param("s") int start, @Param("e") int end);
	
	public int selectBoardCount();
	
	public int writeSave(BoardDTO dto);
	
	public BoardDTO contentView(BoardDTO dto);
	
	public void hitplus(BoardDTO dto);
	
	public int modifySave(BoardDTO dto);
	
	public int modifySaveWithFile(BoardDTO dto);
	
	public int modifyToDeleteFileName(@Param("file_name") String file_name);
	
	public int deleteBoard(int write_no);
	
	public int selectBoardCountByCategory(@Param("category") String category);
	
	public List<BoardDTO> boardAllListByCategory(@Param("category") String category, @Param("s") int start, @Param("e") int end);
	
	// 게시판 게시글 카테고리 수
	public int boardCountCategory(Map<String, String> map); 

	// 게시판 게시글 카테고리+검색 => 요청 내용들에 따라 해당 리스트들 불러오기
	public List<BoardDTO> boardSearchFormCountList(Map<String, String> map);
	
	// 카카오 페이 승인 시 프로그램 결재 완료 후 승인 대기로 이동(신청한 완료 뒤 : 신청한 인원 < 총원 ) 
	public int paidProgramContentView(@Param("write_no") int write_no);	
	// 카카오 페이 승인 시 프로그램 결재 완료 후 승인 대기로 이동(신청한 완료 뒤 : 신청한 인원 = 총원 )
	public int lastPaidProgramContentView(@Param("write_no") int write_no);	
	
	// 댓글 기능 ------------------------------------------------
	
	public int addReply(Map<String, Object> map);
	
	public List<BoardRepDTO> getRepList(int write_group);
	
	public int deleteReply(BoardRepDTO dto);
	
	public int updateReply(Map<String, String> map);
	
	// 답글(대댓글) 기능 -----------------------------------------------
	public List<BoardRepDTO> getReCommentList(int reply_no);
	
	public int addReComment(Map<String, Object> map);
	
	public int updateReComment(Map<String, String> map);
	
	// 프로그램 보드 -----------------------------------------------
	public int writeSaveForProgram(ProgramBoardDTO programBoardDTO);
	
	public int selectProgramBoardCount();
	
	public List<ProgramBoardDTO> programBoardAllList(@Param("s") int start, @Param("e") int end);
	
	public ProgramBoardDTO programContentView(ProgramBoardDTO programBoardDTO);
	
	public int programHitplus(ProgramBoardDTO programBoardDTO);
	
	public int modifySaveForProgram(ProgramBoardDTO programBoardDTO);
	
	public int modifySaveForProgramWithFile(ProgramBoardDTO programBoardDTO);
	
	public int deleteProgram(int write_no);
	
	// 게시판 게시글 카테고리 수
	public int programBoardCountCategory(Map<String, String> map); 

	// 게시판 게시글 카테고리+검색 => 요청 내용들에 따라 해당 리스트들 불러오기
	public List<ProgramBoardDTO> programBoardSearchFormCountList(Map<String, String> map);
	
	// 호스트의 마이페이지에서 프로그램 결재 취소 요청 보내기 기능
	public int paidCancelRequestByTid(@Param("tid") String tid);
	
	// 관리자 마이페이지에서 결재 취소시 paidProgramInfo 삭제 기능
	public void deletePaidProgramInfoByTid(@Param("tid") String tid);
	
	// 관리자 마이페이지에서 결재 취소시 programBoard에 정보 전닳하기 위한 paidProgramInfo 가져오기 기능
	public PaidProgramInfoDTO getPaidProgramInfoByTid(@Param("tid") String tid);
	
	// 결재 취소로 발생하는 programBoard 변화 반영(state ='예약 가능', currentRegisterCount -1)
	public void updateProgramBoardByCancel(@Param("write_no") int write_no);
	 
	
	// 찜하기 기능 -----------------------------------------------
	
	// 처음으로 하트 누른 것인지 확인
	public int dibsExsistance(Map<String, Object> map);
	
	// (처음으로 찜하기룰 눌렀을 떄) 찜하기 생성 기능
	public int insertDibs(Map<String, Object> map);
	
	// 찜하기 객체 불러오기 (유저 아이디, 게시판 번호로)
	public BoardDibsDTO getDibsByIdWriteNo(Map<String, Object> map);
	
	// (찜하기가 처음이 아닐 때) 찜하기 업데이트 기능
	public void updateDib(Map<String, Object> map);
	
	// 내가 찜한 게시판 수 가져오기
	public int selectMyDibsBoardCount(@Param("id") String id);
	
	// 내가 찜한 게시판 리스트 가져오기
	public List<BoardDTO> myDibsBoardAllList(@Param("s") int start,@Param("e") int end,@Param("id") String id);
	
	// 게시판이 받은 찜의 갯수 가져오기
	public int getdibsNumByWriteNo(@Param("write_no") int write_no);
	
	// 내가 찜한 프로그램게시판 리스트 가져오기
	public List<ProgramBoardDTO> myDibsProgramBoardAllList(@Param("s") int start,@Param("e") int end,@Param("id") String id);
	
	// 내가 찜한 프로그램게시판 수 가져오기
	public int selectMyDibsProgramBoardCount(@Param("id") String id);
	
	//============================ 주진욱 끝 ===========================================
	
	
	
	
	
	//============================ 최윤희 시작 ===========================================
	
	
	// DB에 담겨있는 전체 게시글 수 (전체 글 수)
	public int selectNoticeBoardCount();
	
	// 페이징
	public List<NoticeBoardDTO> noticeBoardAllList(@Param("s") int start, @Param("e") int end);
	
	// 공지사항 게시글 보기
	public NoticeBoardDTO noticeBoardContentView(NoticeBoardDTO noticeBoardDTO);
	
	// 조회수 증가
	public void noticeBoardHitplus(NoticeBoardDTO noticeBoardDTO);
	
	// NoticeBoardDTO 에 담은 내용 저장(insert)
	public int noticeBoardWriteSave(NoticeBoardDTO noticeBoardDTO);
	
	// 공지사항 게시글 수정
	public int noticeBoardModifySaveWithFile(NoticeBoardDTO noticeBoardDTO);
	
	// 공지사항 게시글 삭제
	public int noticeBoardDelete(int write_no);
	

	
	// 공지사항 게시글 카테고리+검색 => 해당 검색 카테고리리를 통해 각각 글 갯수 가져오기
	public int noticeBoardCountCategory(NoticeBoardDTO notice_pageDTO);

	// 공지사항 게시글 카테고리+검색 => 요청 내용들에 따라 해당 리스트들 불러오기
	public List<NoticeBoardDTO> noticeSearchFormCountList(NoticeBoardDTO notice_pageDTO);
	
	// 마이페이지 host 정보 얻기위함
	public ProgramBoardDTO programBoardInformation(String id);
	

	//============================ 최윤희 끝 ===========================================
	
	// 청규
	public List<QnaDTO>	manager_qna(@Param("s") int start, @Param("e") int end);
	
	public int selectQnaCount_manager();
	
	public List<QnaDTO>	member_qna(@Param("s") int start, @Param("e") int end, @Param("id") String id);
	
	public int selectQnaCount_member(@Param("id") String id);

	public void member_write_save(QnaDTO dto);
	
	public void manager_write_save(Qna_RepDTO dto);
	
	public QnaDTO contentView_qna(QnaDTO dto);
	public Qna_RepDTO contentView_rep_qna(Qna_RepDTO dto);
	
	public void qna_state(@Param("asdf") int write_no);


	
	// =============================== 성수 시작 ==================================

	public ProgramBoardDTO selectProgramInfo();

	public void insertPaidProgramInfo(PaidProgramInfoDTO paidProgramInfoDTO);

	// 진욱 추가
	public List<PaidProgramInfoDTO> paidProgramInfoByWriteNo(Map<String, Object> map);
	
	
	// =============================== 성수 끝 ==================================

	

}
