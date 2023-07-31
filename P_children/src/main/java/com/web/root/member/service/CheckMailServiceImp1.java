package com.web.root.member.service;

import java.util.Random;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class CheckMailServiceImp1 implements CheckMailService {
	
	@Inject
	JavaMailSender mailSender;
	
	@Override
	public int sendEmail(String email) {
		
		Random rd = new Random();
		int code = rd.nextInt(4589362) + 49311; 

		String setFrom = "sungsu930@naver.com";
		String toMail = email;
		String title = "회원가입 인증 이메일 입니다. - i run it -";
		String content = 
		System.getProperty("line.separator")+
		System.getProperty("line.separator")+
		"안녕하세요. i run it에 함께하시는 마음 정말 감사합니다."+
		System.getProperty("line.separator")+
		System.getProperty("line.separator")+
		"인증번호는 " + code + " 입니다."+
		System.getProperty("line.separator")+
		System.getProperty("line.separator")+
		"받으신 인증번호를 홈페이지에 입력해 주시면 다음으로 넘어갑니다.";
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = 
					new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setFrom);
			messageHelper.setTo(toMail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);
			
			mailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
	
	
	
	// 비밀번호 찾기 메일 인증
	@Override
	public int sendFindPwdEmailCode(String email) {
		Random rd = new Random();
		int code = rd.nextInt(4589362) + 49311; 

		String setFrom = "sungsu930@naver.com";
		String toMail = email;
		String title = "본인확인 인증코드 발급 이메일 입니다. - i run it -";
		String content = 
		System.getProperty("line.separator")+
		System.getProperty("line.separator")+
		"안녕하세요. i run it에 함께하시는 마음 정말 감사합니다."+
		System.getProperty("line.separator")+
		System.getProperty("line.separator")+
		"본인확인 인증코드는 " + code + " 입니다."+
		System.getProperty("line.separator")+
		System.getProperty("line.separator")+
		"받으신 인증코드를 홈페이지에 입력해 주시면 다음으로 넘어갑니다.";
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = 
					new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setFrom);
			messageHelper.setTo(toMail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);
			
			mailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
