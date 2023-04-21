package com.ian.SpringbootOAuthLogin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ian.SpringbootOAuthLogin.domain.Member;
import com.ian.SpringbootOAuthLogin.repository.MemberRepository;

@Service
public class RegisterMemberService {
	private final PasswordEncoder encoder;
	private final MemberRepository repository;
	
	@Autowired
	public RegisterMemberService(PasswordEncoder encoder, MemberRepository repository) {
		this.encoder = encoder;
		this.repository = repository;
	}
	
	public Long beforeVerifyJoin(String userId, String pw, String verifyCode) {
		Member member = Member.createUser(userId, pw, verifyCode, encoder);
		validateDuplicateMember(member);
		repository.save(member);
		
		return member.getId();
	}
	
	public void afterVerifyUpdate(String userId, String verifyCode) {
		Member member = Member.verifyUpdateUser(userId, verifyCode);
		repository.findByUserId(userId).ifPresent(m -> {
			checkVerifyCode(member);
			member.setPw(m.getPw());
			repository.save(member);
		});
	}
	
	private void validateDuplicateMember(Member member) {
		repository.findByUserId(member.getUserId())
				.ifPresent(m -> {
					throw new IllegalStateException("회원 가입 실패 : 중복 회원");
				});
	}
	
	public void validateDuplicateMember(String email) {
		repository.findByUserId(email)
		.ifPresent(m -> {
			throw new IllegalStateException("회원 가입 실패 : 중복 회원");
		});
	}
	
	private void checkVerifyCode(Member member) {
		repository.findByUserId(member.getUserId())
				.ifPresent(m -> {
					if (m.getVerifyCode().equals(member.getVerifyCode())) return;
					else throw new RuntimeException("회원 가입 실패 : 인증 코드 불일치");
				});
	}
}
