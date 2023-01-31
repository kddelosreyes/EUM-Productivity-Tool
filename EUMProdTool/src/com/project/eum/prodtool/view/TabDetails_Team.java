package com.project.eum.prodtool.view;

import java.sql.SQLException;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.model.add.T_ActivityTeam;
import com.project.eum.prodtool.model.add.T_AnalystTeam;
import com.project.eum.prodtool.service.TeamService;

public class TabDetails_Team {

	private final TeamService teamService = new TeamService();
	
	private List<Entity> teams;
	private List<T_AnalystTeam> analystTeams;
	private List<T_ActivityTeam> activityTeams;
	
	public TabDetails_Team() {
		try {
			teams = teamService.getAll();
			analystTeams = teamService.getAnalystTeam();
			activityTeams = teamService.getActivityTeam();
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}
	
	public List<Entity> getTeams() {
		return teams;
	}
	
	public List<T_AnalystTeam> getAnalystTeams() {
		return analystTeams;
	}
	
	public List<T_ActivityTeam> getActivityTeams() {
		return activityTeams;
	}
	
}
