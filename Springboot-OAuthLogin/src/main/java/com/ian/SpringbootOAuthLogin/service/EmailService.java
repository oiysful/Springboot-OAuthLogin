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

@Service
public class EmailService {
	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine templateEngine;
	
	public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
	}
	
	public String sendMail(EmailMessage emailMessage, String type) {
		String authNum = createCode();
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
			mimeMessageHelper.setFrom("IAN");
			mimeMessageHelper.setTo(emailMessage.getTo());
			mimeMessageHelper.setSubject(emailMessage.getSubject());
			mimeMessageHelper.setText(setContext(authNum, type), true);
			
			javaMailSender.send(mimeMessage);
			
			return authNum;
		} catch (MessagingException e) {
			throw new RuntimeException("메일 전송 : 실패");
		}
	}
	
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
	
	private String setContext(String code, String type) {
		Context context = new Context();
		context.setVariable("code", code);
		
		return templateEngine.process(type, context);
	}
	
}
