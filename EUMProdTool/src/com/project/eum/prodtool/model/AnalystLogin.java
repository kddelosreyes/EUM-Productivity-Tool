package com.project.eum.prodtool.model;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;

public class AnalystLogin extends Entity {

	private Integer analystId;
	private String username;
	private String password;
	private String salt;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private Boolean isLocked;
	private Integer attempt;
	private Integer securityQuestion;
	private String securityAnswer;
	
	public AnalystLogin() {
		super();
	}
	
	public AnalystLogin(Integer id, String uuid) {
		super(id, uuid);
	}
	
	public AnalystLogin(Integer id, Integer analystId, String username,
			String password, String salt, LocalDateTime createdDate,
			LocalDateTime updatedDate, Boolean isLocked, Integer attempt,
			String uuid) {
		this(id, uuid);
		this.analystId = analystId;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.isLocked = isLocked;
		this.attempt = attempt;
	}
	
	@Override
	public Class<?> getEntityClass() {
		return AnalystLogin.class;
	}

	public Integer getAnalystId() {
		return analystId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public Integer getAttempt() {
		return attempt;
	}
	
	public Integer getSecurityQuestion() {
		return securityQuestion;
	}
	
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	
}
