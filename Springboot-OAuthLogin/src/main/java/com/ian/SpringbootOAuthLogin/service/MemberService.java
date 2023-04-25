package com.ian.SpringbootOAuthLogin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ian.SpringbootOAuthLogin.domain.Member;
import com.ian.SpringbootOAuthLogin.repository.MemberRepository;

/**
 * 회원 가입 시 검증을 위한 Service
 */
@Service
public class MemberService {
	private final MemberRepository repository;

	@Autowired
	public MemberService(MemberRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * 회원 아이디와 인증여부로 인증된 회원인지 검증 
	 * @param userId 회원 아이디
	 * @return Optional:회원 Domain
	 */
	public Optional<Member> isValidMember(String userId) {
		return repository.findByUserIdAndVerifiedUser(userId, "Y");
	}
	
	
}
