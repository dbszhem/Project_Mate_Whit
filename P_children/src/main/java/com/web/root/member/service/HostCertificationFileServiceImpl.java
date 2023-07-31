package com.web.root.member.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HostCertificationFileServiceImpl implements HostCertificationFileService {

	// host 파일 업로드 저장
	@Override
	public String hostCertificationSaveFile(MultipartFile file) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
		Calendar calendar = Calendar.getInstance();
		String sysFileName = sdf.format(calendar.getTime());
		sysFileName += file.getOriginalFilename();
		
		// 파일 객체 생성 : 파일 경로
		File saveFile = new File(IMAGE_HOST_CERTIFICATION_REPO + "/" + sysFileName);
		
		try {
			// 폴더에 실제로 파일 전송
			file.transferTo(saveFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysFileName; // 날짜+파일 이름 리턴
	}
	
	
	// host 인증 신청 결과 메세지 출력
	@Override
	public String getHostCertificationMessage(HttpServletRequest request, String msg, String url) {
		String message = null;
		String path = request.getContextPath();
		message = "<script>alert('" + msg + "');";
		message += "location.href='"+ path + url + "';</script>";
		return message;
	}
	
	
	// host 인증 신청 거절하면 제출서류 삭제 (임시로 갖고있기)
//	@Override
//	public void hostCertificationDeleteFile(String file_name) {
//		File deletefile = new File(IMAGE_HOST_CERTIFICATION_REPO + "\\" + file_name);
//		deletefile.delete();
//	}
	
}
