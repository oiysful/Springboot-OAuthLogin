package com.ian.SpringbootOAuthLogin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ian.SpringbootOAuthLogin.domain.EmailMessage;
import com.ian.SpringbootOAuthLogin.dto.EmailPostDto;
import com.ian.SpringbootOAuthLogin.dto.EmailResponseDto;
import com.ian.SpringbootOAuthLogin.dto.MemberJoinDto;
import com.ian.SpringbootOAuthLogin.service.EmailService;
import com.ian.SpringbootOAuthLogin.service.RegisterMemberService;

@Controller
@RequestMapping("/send-mail")
public class EmailController {
	
	private final EmailService emailService;
	private final RegisterMemberService memberService;

	public EmailController(EmailService emailService, RegisterMemberService memberService) {
		this.emailService = emailService;
		this.memberService = memberService;
	}
	
	@PostMapping("/join")
	public ResponseEntity<String> sendJoinMail(@RequestBody MemberJoinDto memberJoinDto) {
		try {
			memberService.validateDuplicateMember(memberJoinDto.getEmail());
			
			EmailMessage emailMessage = new EmailMessage(memberJoinDto.getEmail(), "회원가입 인증 코드");
			
			String code = emailService.sendMail(emailMessage, "email");
			
			memberService.beforeVerifyJoin(memberJoinDto.getEmail(), memberJoinDto.getPw(), code);
			
			return ResponseEntity.ok("인증 전송 성공");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("인증 전송 실패 : 중복 회원");
		}
	}
	
	@PostMapping("/password")
	public ResponseEntity<EmailResponseDto> sendPasswordMail(@RequestBody EmailPostDto emailPostDto) {
		EmailMessage emailMessage = new EmailMessage(emailPostDto.getEmail(), "임시 비밀번호 발급");
		
		emailService.sendMail(emailMessage, "password");
		
		return ResponseEntity.ok().build();
	}
}
