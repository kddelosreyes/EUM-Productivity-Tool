package com.project.eum.prodtool.model.add;

import java.time.LocalDateTime;

public class T_ActivityTeam {

	private String teamName;
	private String activityName;
	private String type;
	private LocalDateTime createdDate;
	
	public T_ActivityTeam(String teamName, String activityName, String type, LocalDateTime createdDate) {
		this.teamName = teamName;
		this.activityName = activityName;
		this.type = type;
		this.createdDate = createdDate;
	}

	public String getTeamName() {
		return teamName;
	}

	public String getActivityName() {
		return activityName;
	}

	public String getType() {
		return type;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
}
