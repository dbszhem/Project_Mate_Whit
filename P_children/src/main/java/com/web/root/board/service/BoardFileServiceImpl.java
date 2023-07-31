package com.web.root.board.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.root.member.dto.HostCertifyDTO;
import com.web.root.mybatis.board.BoardMapper;


@Service
public class BoardFileServiceImpl implements BoardFileService {

	@Autowired
	BoardMapper boardMapper;

	
	//============================ 주진욱 시작 ===========================================
	
	@Override
	public String getMessage(HttpServletRequest request, String msg, String url) {
		String message = null;
		String path = request.getContextPath();
		message = "<script>alert('" + msg + "');";
		message += "location.href='"+ path + url + "';</script>";
		return message;
	}

	@Override
	public String saveFile(MultipartFile file) {
		
		// 파일명에 날짜 붙이기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
		Calendar calendar = Calendar.getInstance();
		String sysFileName = sdf.format(calendar.getTime());
		sysFileName += file.getOriginalFilename();
		
		// 파일 객체 생성 : 파일 경로를 갖음
		File saveFile = new File(IMAGE_REPO + "/" + sysFileName);
		
		try {
			// 폴더에 실제로 파일 전송
			file.transferTo(saveFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysFileName;
	}

	@Override
	public void deleteFile(String file_name) {
		// TODO Auto-generated method stub
		File deletefile = new File(IMAGE_REPO + "\\" + file_name);
		deletefile.delete();
		boardMapper.modifyToDeleteFileName(file_name);
		
	}
	
	//============================ 주진욱 끝 ===========================================
		
	
	
	//============================ 최윤희 시작 ===========================================
	
	// 파일명에 날짜 붙여서 저장
	@Override
	public String noticeBoardSaveFile(MultipartFile file) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
		Calendar calendar = Calendar.getInstance();
		String sysFileName = sdf.format(calendar.getTime());
		sysFileName += file.getOriginalFilename();
		
		// 파일 객체 생성 : 파일 경로
		File saveFile = new File(IMAGE_NOTICE_REPO + "/" + sysFileName);
		
		try {
			// 폴더에 실제로 파일 전송
			file.transferTo(saveFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysFileName; // 날짜+파일 이름 리턴
	}
	
	
	// 작성 결과 메시지 리턴
	@Override
	public String getNoticeBoardMessage(HttpServletRequest request, String msg, String url) {
		String message = null;
		String path = request.getContextPath();
		message = "<script>alert('" + msg + "');";
		message += "location.href='"+ path + url + "';</script>";
		return message;
	}
	
	
	// 파일 삭제
	@Override
	public void noticeBoardDeleteFile(String file_name) {
		File deletefile = new File(IMAGE_NOTICE_REPO + "\\" + file_name);
		deletefile.delete();
	}
	
	//============================ 최윤희 끝 ===========================================
	
	
	
	
	
}
