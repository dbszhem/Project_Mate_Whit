package com.web.root.mybatis.kakao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.web.root.kakao.dto.KakaoMapLatLngDTO;
import com.web.root.kakao.dto.ProgramMapDTO;
import com.web.root.kakaoPay.dto.KakaoPaymentApproveResultDTO;

public interface KakaoMapper {

	public void registKakaoPaymentApproveResult(KakaoPaymentApproveResultDTO kakaoPaymentApproveResultDTO);

	public int selectKakaoPaymentApproveCount();

	public List<KakaoPaymentApproveResultDTO> selectKakaoPaymentApproveList(@Param("start") int start, @Param("end") int end);

	public void cancelCheck(@Param("tid") String tid);

	public void insertProgramMapInfo(ProgramMapDTO programMapDTO);

	public List<ProgramMapDTO> selectProgramMapInfo();
	
	// 진욱 추가 ----------------------------------------------------------------------
	public int selectKakaoPaymentApproveCountByCancelRequest();
	
	public List<KakaoPaymentApproveResultDTO> selectKakaoPaymentApproveListByCancelRequest(@Param("start") int start, @Param("end") int end);

	// 진욱 끝 ----------------------------------------------------------------------

}
