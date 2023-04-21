package com.ian.SpringbootOAuthLogin.domain;

public class EmailMessage {
	
	private String to;
	private String subject;
	private String message;
	
	public EmailMessage(String to, String subject) {
		this.to = to;
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}