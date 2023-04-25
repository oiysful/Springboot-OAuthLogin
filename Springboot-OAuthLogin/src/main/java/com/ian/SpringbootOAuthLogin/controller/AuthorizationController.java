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

/**
 *	회원 인증 실패 및 가입 컨트롤러
 */
@Controller
public class AuthorizationController {
	private final RegisterMemberService memberService;

	public AuthorizationController(RegisterMemberService memberService) {
		this.memberService = memberService;
	}
	
	/**
	 * 로그인 실패 Handler로부터 요청 받는 메소드 
	 * @param param		error(boolean) 에러 여부, exception(String) 에러 메세지
	 * @param redirect	/login.html로 redirect할 Attributes를 담을 객체
	 * @return	/login.html redirect
	 */
	@GetMapping("/auth/fail")
	public String loginFail(@RequestParam Map<String, Object> param, RedirectAttributes redirect) {
		
		redirect.addFlashAttribute("error", param.get("error"));
		redirect.addFlashAttribute("exception", param.get("exception"));
		
		return "redirect:/login";
	}
	
	/**
	 * 회원가입 요청 시 이메일 인증 코드 검증 후 결과 return
	 * @param dto	회원 가입 DTO
	 * @return ResponseEntity(responseCode, message)
	 */
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
