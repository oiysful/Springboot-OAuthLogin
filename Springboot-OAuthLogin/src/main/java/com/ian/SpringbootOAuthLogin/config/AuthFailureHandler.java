package com.ian.SpringbootOAuthLogin.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 로그인 실패 Handler
 */
@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override 
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String errorMessage;
		
		// 각 Exception 별 메세지 지정
		if (exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "내부 시스템 오류";
		} else if (exception instanceof UsernameNotFoundException) {
			errorMessage = "등록되지 않은 회원";
		} else if(exception instanceof BadCredentialsException) {
			errorMessage = "로그인 정보를 확인하세요";
		} else if (exception instanceof AuthenticationCredentialsNotFoundException) {
			errorMessage = "인증 요청 거부";
		} else {
			errorMessage = "알 수 없는 오류";
		}
		
		// URL parameter로 전달 중 한글 깨짐을 방지하기 위해 UTF-8로 urlEncode
		errorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
		// 실패 처리 URL지정
		setDefaultFailureUrl("/auth/fail?error=true&exception="+errorMessage);
		// SimpleUrlAuthenticationFailureHandler의 onAuthenticationFailure 메소드 기능 사용
		super.onAuthenticationFailure(request, response, exception);
	}
	
}
