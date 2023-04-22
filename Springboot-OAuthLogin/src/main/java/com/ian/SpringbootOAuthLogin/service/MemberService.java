package com.ian.SpringbootOAuthLogin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ian.SpringbootOAuthLogin.domain.Member;
import com.ian.SpringbootOAuthLogin.repository.MemberRepository;

@Service
public class MemberService {
	private final MemberRepository repository;

	@Autowired
	public MemberService(MemberRepository repository) {
		this.repository = repository;
	}
	
	public Optional<Member> isValidMember(String userId) {
		return repository.findByUserIdAndVerifiedUser(userId, "Y");
	}
	
	
}
