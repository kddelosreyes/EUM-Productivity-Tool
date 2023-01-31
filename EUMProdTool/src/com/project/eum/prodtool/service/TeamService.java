package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.Team;
import com.project.eum.prodtool.model.add.T_ActivityTeam;
import com.project.eum.prodtool.model.add.T_AnalystTeam;
import com.project.eum.prodtool.model.column.TeamColumn;
import com.project.eum.prodtool.model.field.TeamField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class TeamService extends Service {

	@Override
	public String getTableName() throws SQLException {
		return Table.TEAM.toString();
	}

	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(TeamColumn.ID.getColumnName());
		String name = resultSet.getString(TeamColumn.NAME.getColumnName());
		String type = resultSet.getString(TeamColumn.TYPE.getColumnName());
		LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(TeamColumn.CREATED_DATE.getColumnName()));
		LocalDateTime updateddate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(TeamColumn.UPDATED_DATE.getColumnName()));
		
		Entity team = new Team()
				.set(TeamField.id, id)
				.set(TeamField.name, name)
				.set(TeamField.type, type)
				.set(TeamField.createdDate, createdDate)
				.set(TeamField.updatedDate, updateddate);
		
		return team;
	}	
	
	public List<T_AnalystTeam> getAnalystTeam() throws SQLException {
		List<T_AnalystTeam> analystTeams = new ArrayList<>();
		
		String sql = "SELECT CONCAT(first_name, ' ', last_name) as 'analyst_name', role, GROUP_CONCAT(t.name SEPARATOR ', ') as 'team_name', ant.created_date FROM analyst a JOIN analyst_team ant ON a.id = ant.analyst_id JOIN team t ON t.id = ant.team_id WHERE a.is_active = 1 GROUP BY a.id ORDER BY a.id, t.id";
		Query query = new Query(sql);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		while (resultSet.next()) {
			String analystName = resultSet.getString("analyst_name");
			String role = resultSet.getString("role");
			String teamName = resultSet.getString("team_name");
			LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp("created_date"));
			
			analystTeams.add(new T_AnalystTeam(analystName, role, teamName, createdDate));
		}
		
		return analystTeams;
	}
	
	public List<T_ActivityTeam> getActivityTeam() throws SQLException {
		List<T_ActivityTeam> activityTeams = new ArrayList<>();
		
		String sql = "SELECT t.name as 'team_name', a.name as 'activity_name', act.type, ta.created_date FROM team t JOIN team_activity ta ON ta.team_id = t.id JOIN activity a ON a.id = ta.activity_id JOIN activity_type act ON act.id = a.activity_type_id ORDER BY t.id, a.id";
		Query query = new Query(sql);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		while (resultSet.next()) {
			String teamName = resultSet.getString("team_name");
			String activityName = resultSet.getString("activity_name");
			String type = resultSet.getString("type");
			LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp("created_date"));
			
			activityTeams.add(new T_ActivityTeam(teamName, activityName, type, createdDate));
		}
		
		return activityTeams;
	}
	
}
