package com.web.root.kakao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.root.kakao.dto.ProgramMapDTO;
import com.web.root.kakao.service.KakaoService;

@Controller
@RequestMapping("kakao")
public class KakaoController {
	
	@Autowired
	KakaoService ks;
	
	@GetMapping("kakaoMapTest")
	public String kakaoMapTest() {
		return "sungsu/kakaoMap/kakaoMapTest";
	}
	
	//  입력한 주소로 위도, 경도 좌표 정보 요청 및 DB에 저장
	@RequestMapping("kakaoMapGetLatLng")
	public String getLatLng(HttpServletRequest request) {
		String addrMerge = request.getParameter("addr2"); 
		ProgramMapDTO programMapDTO = new ProgramMapDTO();
		ks.getKakaoMapLatLng(addrMerge, programMapDTO);
		return "sungsu/kakaoMap/kakaoMapResult";
	}
	
	@GetMapping("kakaoMapLatLng_json")
	public String createProgramLatLng_json(Model model, HttpSession session) {
		ks.createLatLngJson(model, session);
		return "sungsu/kakaoMap/kakaoMapResult";
	}
	
//	@GetMapping("show_programsLatLng")
//	public ResponseEntity<String> show_programsLatLng_json(HttpSession session){
//		String programsLatLng = (String)session.getAttribute("programsLatLng");
//		return new ResponseEntity<String>(programsLatLng, HttpStatus.OK);
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}































