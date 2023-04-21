package com.ian.SpringbootOAuthLogin.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/greeting")
	public String greetingPage(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("loginUserId", user.getUsername());
		model.addAttribute("loginRoles", user.getAuthorities());
		return "greeting";
	}
}
