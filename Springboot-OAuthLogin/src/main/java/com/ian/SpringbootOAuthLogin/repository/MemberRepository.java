package com.ian.SpringbootOAuthLogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ian.SpringbootOAuthLogin.domain.Member;

/**
 * Spring Data JPA 사용을 위한 Interface
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
	
	// 회원아이디로 SELECT
	Optional<Member> findByUserId(String userId);
	
	// 회원아이디, 메일 인증 여부로 SELECT
	Optional<Member> findByUserIdAndVerifiedUser(String userId, String verifiedUser);
	
}
