package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.service.constants.Table;

public class TeamActivityService extends Service {

	@Override
	public String getTableName() throws SQLException {
		return Table.TEAM_ACTIVITY.toString();
	}

	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		return null;
	}
	
	public Integer insertNewTeamActivity(Integer teamId, Integer activityId) throws SQLException {
		String sql = "INSERT INTO " + getTableName() + " (team_id, activity_id) "
				+ "VALUES(?1, ?2)";
		Query query = new Query(sql);
		query.params(teamId, activityId);
		
		int key = executeUpdate(query.getQuery());
		return key;
	}

}
