package com.ian.SpringbootOAuthLogin.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

/**
 * 회원 정보 Domain
 */
@Entity
@DynamicInsert
@DynamicUpdate
@SequenceGenerator(
		name = "MEMBER_ID_SEQ_GENERATOR",
		sequenceName = "MEMBER_ID_SEQ",
		initialValue = 1,
		allocationSize = 1)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_ID_SEQ_GENERATOR")
    private Long id;

    @Column(unique = true)
    private String userId;
    private String pw;
    private String roles;
    private String verifyCode;
    private String verifiedUser;

    private Member(Long id, String userId, String pw, String verifyCode, String roleUser, String verifiedUser) {
        this.id = id;
        this.userId = userId;
        this.pw = pw;
        this.roles = roleUser;
        this.verifyCode = verifyCode;
		this.verifiedUser = verifiedUser;
    }

    protected Member() {}

    public static Member createUser(String userId, String pw, String verifyCode, PasswordEncoder passwordEncoder) {
        return new Member(null, userId, passwordEncoder.encode(pw), verifyCode, "USER", null);
    }
    
    public void verifyUpdateUser() {
    	this.verifyCode = null;
    	this.verifiedUser = "Y";
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPw() {
        return pw;
    }
    
    public String getRoles() {
        return roles;
    }

	public String getVerifyCode() {
		return verifyCode;
		
	}
	public String getVerifiedUser() {
		return verifiedUser;
		
	}

}