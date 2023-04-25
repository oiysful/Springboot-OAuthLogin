package com.ian.SpringbootOAuthLogin.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ian.SpringbootOAuthLogin.dto.MemberJoinDto;
import com.ian.SpringbootOAuthLogin.service.RegisterMemberService;

@Controller
public class AuthorizationController {
	private final RegisterMemberService memberService;

	public AuthorizationController(RegisterMemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/auth/fail")
	public String loginFail(@RequestParam Map<String, Object> param, RedirectAttributes redirect) {
		
		redirect.addFlashAttribute("error", param.get("error"));
		redirect.addFlashAttribute("exception", param.get("exception"));
		
		return "redirect:/login";
	}
//	public String loginFail(@RequestParam Map<String, Object> param, Model model) {
//		
//		model.addAttribute("error", param.get("error"));
//		model.addAttribute("exception", param.get("exception"));
//		
//		return "login";
//	}
	
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody MemberJoinDto dto) {
		try {
			memberService.afterVerifyUpdate(dto.getEmail(), dto.getAuth());
			return ResponseEntity.ok("회원가입 성공");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
