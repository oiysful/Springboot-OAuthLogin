package com.ian.SpringbootOAuthLogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ian.SpringbootOAuthLogin.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Optional<Member> findByUserId(String userId);
	
}
