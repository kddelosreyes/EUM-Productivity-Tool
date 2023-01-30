package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.Team;
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
	
}
