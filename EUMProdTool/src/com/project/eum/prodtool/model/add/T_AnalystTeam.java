package com.project.eum.prodtool.model.add;

import java.time.LocalDateTime;

public class T_AnalystTeam {

	private String analystName;
	private String role;
	private String teamName;
	private LocalDateTime createdDate;
	
	public T_AnalystTeam(String analystName, String role, String teamName,
			LocalDateTime createdDate) {
		this.analystName = analystName;
		this.role = role;
		this.teamName = teamName;
		this.createdDate = createdDate;
	}

	public String getAnalystName() {
		return analystName;
	}

	public String getRole() {
		return role;
	}

	public String getTeamName() {
		return teamName;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
}
