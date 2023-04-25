package com.ian.SpringbootOAuthLogin.service;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.ian.SpringbootOAuthLogin.domain.EmailMessage;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * 메일 인증 처리 Service
 */
@Service
public class EmailService {
	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine templateEngine;
	
	public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
	}
	
	/**
	 * 메일 전송 메소드
	 * @param emailMessage 수신인, 제목 정보를 담고 있는 객체
	 * @param type	회원가입, 비밀번호 초기화 구분을 위한 구분
	 * @return	전송한 인증코드
	 */
	public String sendMail(EmailMessage emailMessage, String type) {
		// 인증 코드 생성
		String authNum = createCode();
		 
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
			mimeMessageHelper.setFrom("IAN");	//발신인
			mimeMessageHelper.setTo(emailMessage.getTo());	//수신인
			mimeMessageHelper.setSubject(emailMessage.getSubject());	//메일 제목 
			mimeMessageHelper.setText(setContext(authNum, type), true);	//메일 본문 : Template Engine에 전달할 context
			
			// 메일 전송
			javaMailSender.send(mimeMessage);
			
			// 생성된 인증 코드 return : DB 저장
			return authNum;
		} catch (MessagingException e) {
			throw new RuntimeException("메일 전송 : 실패");
		}
	}
	
	/**
	 * 인증 코드 생성 메소드
	 * @return 회원가입, 비밀번호 초기화에 사용할 인증코드
	 */
	public static String createCode() {
		final int lLimit = 48;
		final int rLimit = 122;
		final int targetStrLength = 16;
		Random random = new Random();
		
		return random.ints(lLimit, rLimit + 1)
						.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
						.limit(targetStrLength)
						.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
						.toString();
	}
	
	/**
	 * 메일 본문 : Template Engine에 전달할 객체 생성 메소드
	 * @param code 인증 코드
	 * @param type 회원가입, 비밀번호 초기화 구분
	 * @return
	 */
	private String setContext(String code, String type) {
		Context context = new Context();
		context.setVariable("code", code);
		
		return templateEngine.process(type, context);
	}
	
}
