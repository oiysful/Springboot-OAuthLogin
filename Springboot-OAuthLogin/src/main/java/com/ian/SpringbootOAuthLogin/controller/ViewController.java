package com.ian.SpringbootOAuthLogin.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 로그인 화면 및 로그인 성공화면 Controller
 */
@Controller
public class ViewController {
	
	/**
	 * 로그인 화면 접근을 위한 메소드
	 * @param param	로그인 실패 시 alert 처리를 위한 parameter
	 * @param model view resolver로 로그인 실패 정보 전달할 model
	 * @return /login
	 */
	@GetMapping("/login")
	public String loginPage(@RequestParam Map<String, Object> param, Model model) {
		if (param.containsKey("error")) model.addAttribute("error", param.get("error"));
		if (param.containsKey("exception")) model.addAttribute("exception", param.get("exception"));
		
		return "login";
	}

	/**
	 * 로그인 성공 시 화면 이동을 위한 메소드
	 * @param user	로그인 성공한 회원 정보 객체
	 * @param model view resolver로 로그인 회원 정보 전달할 model
	 * @return /greeting
	 */
	@GetMapping("/greeting")
	public String greetingPage(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("loginUserId", user.getUsername());
		model.addAttribute("loginRoles", user.getAuthorities());
		return "greeting";
	}
}
