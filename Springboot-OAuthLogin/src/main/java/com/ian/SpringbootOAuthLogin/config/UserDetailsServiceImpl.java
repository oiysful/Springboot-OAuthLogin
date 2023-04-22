package com.ian.SpringbootOAuthLogin.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ian.SpringbootOAuthLogin.domain.Member;
import com.ian.SpringbootOAuthLogin.service.MemberService;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final MemberService memberService;
	
	public UserDetailsServiceImpl(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public UserDetails loadUserByUsername(String insertedUserId) throws UsernameNotFoundException {
		Optional<Member> findOne = memberService.isValidMember(insertedUserId);
		Member member = findOne.orElseThrow(() -> new UsernameNotFoundException("로그인 실패 : 인증되지 않은 회원"));
		
		return User.builder()
					.username(member.getUserId())
					.password(member.getPw())
					.roles(member.getRoles())
					.build();
	}
}
