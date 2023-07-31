package com.web.root.mybatis.mypageBoard;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.web.root.board.dto.BoardDTO;
import com.web.root.board.dto.PaidProgramInfoDTO;
import com.web.root.board.dto.ProgramBoardDTO;
import com.web.root.member.dto.HostCertifyDTO;

public interface MypageBoardMapper {

	//============================ 최윤희 시작 ===========================================
	
	// (커뮤니티) 마이페이지 해당 아이디 작성 글 페이지 총 카운트
	public int mypageBoardAllCount(String id);
	
	// (커뮤니티) 마이페이지 내가 작성한 페이지 페이징 ex. 1~5 페이지
	public List<BoardDTO> mypageBoardList(BoardDTO myboardDTO);	
	
	// (프로그램) 마이페이지 해당 아이디 작성 글 페이지 총 카운트
	public int mypageBoardProgramAllCount(String id);
	
	// (프로그램) 마이페이지 내가 작성한 페이지 페이징 ex. 1~5 페이지
	public List<ProgramBoardDTO> mypageBoardProgramList(ProgramBoardDTO myboardProgramDTO);

	
	
	// host 인증신청 작성
	public int hostCertificationWriteSave(HostCertifyDTO hostCertifyDTO);
	
	// host 인증 신청하고 대기중 표시 G로 업데이트
	public void hostCertificationCertified(HostCertifyDTO hostCertifyDTO);
	
	// (관리자 로그인) 신청 1~10 신청 목록 전부 가져오기
	public List<HostCertifyDTO> hostCertificationApplyList(@Param("start") int start, @Param("end") int end);
	
	// (관리자 로그인) host 인증 신청 전체 카운트 가져오기
	public int hostCertificationApplyCount();
	
	// (관리자 로그인) host 인증 신청 수락
	public int hostCertificationOk(int certify_no);
	
	// host 인증 신청이 수락되면 member 테이블 certified 값 변경 Y
	public void hostCertificationMemberOk(String id);
	
	// (관리자 로그인) host 인증 신청 거절
	public int hostCertificationNo(int certify_no);
	
	// host 인증 신청이 거절되면 member 테이블 certified 값 변경 N
	public void hostCertificationMemberNo(String id);
	
	// host 인증 신청 거절하면 DB에서 file_name 이름 nan 으로 변경 (임시로 갖고있기)
	//public void hostCertificationDBfile_nameDelete(@Param("id") String id, @Param("certify_no") int certify_no);

	
	//============================ 최윤희 끝 ===========================================
	
	// ======================================= 박성수 시작 =================================================
	
	public int selectPaidProgramInfoCount();

	public ArrayList<PaidProgramInfoDTO> selectPaidProgramList(@Param("id") String id, @Param("start") int start, @Param("end") int end);

	public int updateCancelRequest(@Param("id") String id, @Param("num") int write_no);
	
	
	// ======================================= 박성수 끝 =================================================
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
