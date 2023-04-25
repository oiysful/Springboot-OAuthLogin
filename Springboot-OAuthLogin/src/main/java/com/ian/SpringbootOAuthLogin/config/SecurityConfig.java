package com.ian.SpringbootOAuthLogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {

	/**
	 * BCryptPasswordEncoder를 사용하여 회원 비밀번호 인코딩 지정
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * <pre>
	 * SpringSecurity에 대한 설정
	 *   - 로그인 실패, 회원가입 메일 전송, view static resources 관련 경로 접근 허용
	 *   - 그 외 접근 시 인증 필요
	 *   - 로그인 페이지 URL 지정 : login
	 *   - 로그인 인증 처리 URL 지정 : login-process
	 *   - 로그인 아이디 파라미터 지정 : userId(login.html > input name) 
	 *   - 로그인 비밀번호 파라미터 지정 : pw(login.html > input name)
	 *   - 로그인 성공 시 URL 지정 : greeting
	 *   - 로그인 실패 Handler 지정 : AuthFailureHandler
	 *   - 기본 로그아웃 설정
	 *  </pre>
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable()
				.authorizeHttpRequests(request ->
						request
							.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
							.requestMatchers("/auth/fail", "/join/**", "/send-mail/**", "/css/**", "/js/**", "/img/**").permitAll()
							.anyRequest().authenticated()
				)
				.formLogin(login ->
						login
							.loginPage("/login")
							.loginProcessingUrl("/login-process")
							.usernameParameter("userId")
							.passwordParameter("pw")
							.defaultSuccessUrl("/greeting", true)
							.failureHandler(new AuthFailureHandler())
							.permitAll()
				)
				.logout();
		return http.build();
	}
}
