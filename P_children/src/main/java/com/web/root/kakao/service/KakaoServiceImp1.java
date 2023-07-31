package com.web.root.kakao.service;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.web.root.kakao.dto.ProgramMapDTO;
import com.web.root.kakao.resource.KakaoDeveloper;
import com.web.root.mybatis.kakao.KakaoMapper;

@Service
public class KakaoServiceImp1 implements KakaoService, KakaoDeveloper{
	
	@Autowired
	KakaoMapper kakaoMapper;
	
	
	@Override
	public ProgramMapDTO getKakaoMapLatLng(String addrMerge, ProgramMapDTO programMapDTO) {
		ObjectMapper objectMapper = new ObjectMapper();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Authorization", "KakaoAK "+ REST_API_KEY);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(SEARCH_ADDRESS_URL)
		        .queryParam("query", addrMerge);
		URI uri = builder.build().encode().toUri();
				
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = 
					 restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		
		String x_ = "";
		String y_ = "";
		
		try {
			// 경도. longitude -> lng
			x_ = objectMapper.readTree(response.getBody())
			    .get("documents")
			    .get(0)
			    .get("address")
			    .get("x").asText();
			
			// 위도. latitude -> lat
			y_ = objectMapper.readTree(response.getBody())
				.get("documents")
				.get(0)
				.get("address")
				.get("y").asText();
		} catch (IOException e) {
			e.printStackTrace();
		}
		double x = Double.parseDouble(x_);
		double y = Double.parseDouble(y_);
		
		programMapDTO.setLat(y);
		programMapDTO.setLng(x);
		
		return programMapDTO;
//		KakaoMapLatLngDTO kakaoMapLatLngDTO = new KakaoMapLatLngDTO(y, x);
//		kakaoMapper.insertProgramLatLng(kakaoMapLatLngDTO);
		
		
	}
	
	@Override
	public void createLatLngJson(Model model, HttpSession session) {
		JSONObject programsLatLng = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		FileWriter writer = null;

		List<ProgramMapDTO> list = kakaoMapper.selectProgramMapInfo();
		List<JSONObject> dataList = new ArrayList<JSONObject>();
		
		for(ProgramMapDTO dto : list) {
			JSONObject data = new JSONObject();
			data.put("write_no", dto.getWrite_no());
			data.put("title", dto.getTitle());
			data.put("lat", dto.getLat());
			data.put("lng", dto.getLng());
			dataList.add(data);
		}
		jsonArray.addAll(dataList);
		programsLatLng.put("positions", jsonArray);
		
		
		String programsLatLng_json = new GsonBuilder() 
				     .setPrettyPrinting()
				     .create()
				     .toJson(programsLatLng);
//		session.setAttribute("programsLatLng", programsLatLng_json);
		model.addAttribute("kakaoMapLatLng", programsLatLng_json);
		
//		json 파일 생성하기
//		try {
//			writer = new FileWriter("C:\\Users\\Administrator\\git\\P_Children\\P_children\\src\\main\\webapp\\resources\\sungsu\\json\\programsLatLng.json"); 
//			writer.write(programsLatLng_json);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(writer != null) writer.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//			 	
//		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
