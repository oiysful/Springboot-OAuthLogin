package com.ian.SpringbootOAuthLogin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ian.SpringbootOAuthLogin.dto.MemberJoinDto;
import com.ian.SpringbootOAuthLogin.service.RegisterMemberService;

@Controller
public class AuthorizationController {
	private final RegisterMemberService memberService;

	public AuthorizationController(RegisterMemberService memberService) {
		this.memberService = memberService;
	}
	
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
