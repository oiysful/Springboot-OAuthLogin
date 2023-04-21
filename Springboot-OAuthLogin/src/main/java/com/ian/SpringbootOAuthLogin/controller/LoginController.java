package com.ian.SpringbootOAuthLogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	@PostMapping("/logout")
	public String logout() {
		return "login";
	}
}
