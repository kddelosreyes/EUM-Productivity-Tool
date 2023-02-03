package com.project.eum.prodtool.view;

import java.sql.SQLException;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.model.add.T_ActivityTeam;
import com.project.eum.prodtool.model.add.T_AnalystTeam;
import com.project.eum.prodtool.model.column.AnalystColumn;
import com.project.eum.prodtool.service.AnalystService;
import com.project.eum.prodtool.service.TeamService;

public class TabDetails_Team {

	private final TeamService teamService = new TeamService();
	private final AnalystService analystService = new AnalystService();
	
	private List<Entity> teams;
	private List<T_AnalystTeam> analystTeams;
	private List<T_ActivityTeam> activityTeams;
	private List<Entity> analysts;
	
	public TabDetails_Team() {
		try {
			teams = teamService.getAll();
			analystTeams = teamService.getAnalystTeam();
			activityTeams = teamService.getActivityTeam();
			analysts = analystService.getEntitiesByColumn(AnalystColumn.IS_ACTIVE, 1);
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
	
	public List<Entity> getAnalysts() {
		return analysts;
	}
	
}
