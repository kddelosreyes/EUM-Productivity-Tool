package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.service.constants.Table;

public class AnalystTeamService extends Service {

	@Override
	public String getTableName() throws SQLException {
		return Table.ANALYST_TEAM.toString();
	}

	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		return null;
	}
	
	public Integer insertNewAnalystTeam(Integer analystId, Integer teamId) throws SQLException {
		String sql = "INSERT INTO " + getTableName() + " (analyst_id, team_id, isactive) "
				+ "VALUES(?1, ?2, ?3)";
		Query query = new Query(sql);
		query.params(analystId, teamId, 1);
		
		int key = executeUpdate(query.getQuery());
		return key;
	}

}
