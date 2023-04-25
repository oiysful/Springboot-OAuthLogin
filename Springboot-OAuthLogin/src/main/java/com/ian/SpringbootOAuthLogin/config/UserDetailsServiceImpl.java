package com.ian.SpringbootOAuthLogin.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ian.SpringbootOAuthLogin.domain.Member;
import com.ian.SpringbootOAuthLogin.service.MemberService;

/**
 * 회원 정보 처리 Component
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final MemberService memberService;
	
	public UserDetailsServiceImpl(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public UserDetails loadUserByUsername(String insertedUserId) throws UsernameNotFoundException {
		// 회원아이디와 인증여부 조회하여 입력 받은 회원 아이디 검증
		Optional<Member> findOne = memberService.isValidMember(insertedUserId);
		// 검증된 회원 정보가 없으면 Exception Throw
		Member member = findOne.orElseThrow(() -> new UsernameNotFoundException("로그인 실패 : 인증되지 않은 회원"));
		// 검증된 회원 정보가 있으면 return
		return User.builder()
					.username(member.getUserId())
					.password(member.getPw())
					.roles(member.getRoles())
					.build();
	}
}
