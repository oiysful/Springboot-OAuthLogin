package com.ian.SpringbootOAuthLogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ian.SpringbootOAuthLogin.domain.Member;
import com.ian.SpringbootOAuthLogin.repository.MemberRepository;

import jakarta.transaction.Transactional;

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
		repository.save(member);
		
		return member.getId();
	}
	
	@Transactional
	public void afterVerifyUpdate(String userId, String verifyCode) {
		repository.findByUserId(userId).ifPresent(m -> {
			checkVerifyCode(m, verifyCode);
			m.verifyUpdateUser();
		});
	}
	
	public void validateDuplicateMember(String email) {
		repository.findByUserId(email)
		.ifPresent(m -> {
			throw new IllegalStateException("회원 가입 실패 : 중복 회원");
		});
	}
	
	private void checkVerifyCode(Member member, String verifyCode) {
		if(verifyCode.equals(member.getVerifyCode())) return;
		throw new IllegalAccessError("회원 가입 실패 : 인증 코드 불일치");
	}
}
