package com.ian.SpringbootOAuthLogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable()
				.authorizeHttpRequests(request ->
						request
							.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
							.requestMatchers("/join/**", "/send-mail/**", "/css/**", "/js/**", "/img/**").permitAll()
							.anyRequest().authenticated()
				)
				.formLogin(login ->
						login
							.loginPage("/login")
							.loginProcessingUrl("/login-process")
							.usernameParameter("userId")
							.passwordParameter("pw")
							.defaultSuccessUrl("/greeting", true)
							.permitAll()
				)
				.logout();
		return http.build();
	}
}
