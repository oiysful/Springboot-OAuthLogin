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
	
	public Optional<Member> findOne(String userId) {
		return repository.findByUserId(userId);
	}
	
	public boolean isValidMember(String userId, String pw) {
		Optional<Member> member = findOne(userId);
		
        if (member.isPresent()) {
            return member.get().getPw().equals(pw);
        }
        return false;
	}
	
}
