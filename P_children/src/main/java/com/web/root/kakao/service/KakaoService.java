package com.web.root.kakao.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.web.root.kakao.dto.ProgramMapDTO;

public interface KakaoService {

	void createLatLngJson(Model model, HttpSession session);

	ProgramMapDTO getKakaoMapLatLng(String addrMerge, ProgramMapDTO programMapDTO);

}
