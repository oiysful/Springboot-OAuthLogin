package com.ian.SpringbootOAuthLogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 로그아웃 시 로그인 화면으로 전송하는 Controller
 */
@Controller
public class LoginController {
	
	@PostMapping("/logout")
	public String logout() {
		return "login";
	}
}
