package com.ian.SpringbootOAuthLogin.dto;

/**
 * 이메일 전송에 대한 응답 DTO
 */
public class EmailResponseDto {

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
