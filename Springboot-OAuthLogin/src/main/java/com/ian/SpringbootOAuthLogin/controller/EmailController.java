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

/**
 * 회원가입, 비밀번호 초기화를 위한 메일 전송 Controller
 */
@Controller
@RequestMapping("/send-mail")
public class EmailController {
	
	private final EmailService emailService;
	private final RegisterMemberService memberService;

	public EmailController(EmailService emailService, RegisterMemberService memberService) {
		this.emailService = emailService;
		this.memberService = memberService;
	}
	
	/**
	 * 회원 가입 시 처리하는 메소드
	 * @param memberJoinDto 회원 가입 정보 DTO
	 * @return ResponseEntity(responseCode, message)
	 */
	@PostMapping("/join")
	public ResponseEntity<String> sendJoinMail(@RequestBody MemberJoinDto memberJoinDto) {
		try {
			// 중복 가입 검증
			memberService.validateDuplicateMember(memberJoinDto.getEmail());
			// 메일 전송 객체 생성
			EmailMessage emailMessage = new EmailMessage(memberJoinDto.getEmail(), "회원가입 인증 코드");
			// 메일 전송
			String code = emailService.sendMail(emailMessage, "email");
			// DB에 인증 코드 저장(메일 인증 완료 후 DB 인증코드 삭제)
			memberService.beforeVerifyJoin(memberJoinDto.getEmail(), memberJoinDto.getPw(), code);
			
			return ResponseEntity.ok("인증 전송 성공");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("인증 전송 실패 : 중복 회원");
		}
	}
	
	/**
	 * [구현 필요] 비밀번호 초기화 메소드
	 * @param emailPostDto 이메일 전송할 내용 DTO
	 * @return ResponseEntity(responseCode)
	 */
	@PostMapping("/password")
	public ResponseEntity<EmailResponseDto> sendPasswordMail(@RequestBody EmailPostDto emailPostDto) {
		EmailMessage emailMessage = new EmailMessage(emailPostDto.getEmail(), "임시 비밀번호 발급");
		
		emailService.sendMail(emailMessage, "password");
		
		return ResponseEntity.ok().build();
	}
}
