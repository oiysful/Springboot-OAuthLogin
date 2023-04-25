package com.ian.SpringbootOAuthLogin.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {
	
	@GetMapping("/login")
//	public String loginPage(@RequestParam Map<String, Object> param, RedirectAttributes redirect) {
//		if(param.containsKey("error") && param.containsKey("exception")) {
//			redirect.addFlashAttribute("error", param.get("error"));
//			redirect.addFlashAttribute("exception", param.get("exception"));
//		}
//		return "login";
//	}
	public String loginPage(@RequestParam Map<String, Object> param, Model model) {
		if(param.containsKey("error")) model.addAttribute("error", param.get("error"));
		if(param.containsKey("exception")) model.addAttribute("exception", param.get("exception"));
		return "login";
	}
	
	@GetMapping("/greeting")
	public String greetingPage(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("loginUserId", user.getUsername());
		model.addAttribute("loginRoles", user.getAuthorities());
		return "greeting";
	}
}
