package com.ian.SpringbootOAuthLogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ian.SpringbootOAuthLogin.domain.Member;
import com.ian.SpringbootOAuthLogin.repository.MemberRepository;

import jakarta.transaction.Transactional;

/**
 * 회원 가입을 위한 Service
 */
@Service
public class RegisterMemberService {
	private final PasswordEncoder encoder;
	private final MemberRepository repository;
	
	@Autowired
	public RegisterMemberService(PasswordEncoder encoder, MemberRepository repository) {
		this.encoder = encoder;
		this.repository = repository;
	}
	
	/**
	 * 메일 인증이 완료되기 전 회원 정보와 전송한 인증코드 저장하는 메소드
	 * @param userId 회원 아이디
	 * @param pw 회원 비밀번호
	 * @param verifyCode 메일로 전송한 인증 코드
	 * @return 저장된 회원 Sequence 번호(PRIMARY KEY)
	 */
	public Long beforeVerifyJoin(String userId, String pw, String verifyCode) {
		Member member = Member.createUser(userId, pw, verifyCode, encoder);
		repository.save(member);
		
		return member.getId();
	}
	
	/**
	 * SpringDataJPA Dirty Check를 통한 회원 인증 검증 및 완료 업데이트 메소드
	 * @param userId 회원 아이디
	 * @param verifyCode 인증 코드
	 */
	@Transactional
	public void afterVerifyUpdate(String userId, String verifyCode) {
		repository.findByUserId(userId).ifPresent(m -> {
			checkVerifyCode(m, verifyCode);
			m.verifyUpdateUser();
		});
	}
	
	/**
	 * 회원 가입 시 중복 회원 검증하는 메소드
	 * @param email 회원 아이디
	 */
	public void validateDuplicateMember(String email) {
		repository.findByUserId(email)
		.ifPresent(m -> {
			throw new IllegalStateException("회원 가입 실패 : 중복 회원");
		});
	}
	
	/**
	 * DB에 저장되어 있는 메일 인증 코드와 입력 받은 인증 코드를 비교 검증하는 메소드
	 * @param member DB에 저장되어 있는 회원 Domain
	 * @param verifyCode 입력 받은 인증 코드
	 */
	private void checkVerifyCode(Member member, String verifyCode) {
		if(verifyCode.equals(member.getVerifyCode())) return;
		throw new IllegalAccessError("회원 가입 실패 : 인증 코드 불일치");
	}
}
