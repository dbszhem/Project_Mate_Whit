package com.web.root.board.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.root.session.name.MemberSession;

public class BoardInterceptor extends HandlerInterceptorAdapter implements MemberSession{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) == null && session.getAttribute("kakaoId") == null) {
			// 일반 로그인, 카카오 로그인이 아닌 경우
			//session.setAttribute(LOGIN, "noLogin");
			return true;
		} else if(session.getAttribute("kakaoId") != null) {
			// 카카오 로그인인 경우
			// 카카오 로그인 아이디 kakaoId 를 LOGIN( == loginUser)에 담는다.
			session.setAttribute(LOGIN, session.getAttribute("kakaoId"));
			return true;
		}
			// 일반 로그인인 경우
		return true;
	}
	
	

}
