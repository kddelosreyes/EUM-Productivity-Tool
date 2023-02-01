package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.service.constants.Table;

public class ActivityFieldService extends Service {

	@Override
	public String getTableName() throws SQLException {
		return Table.ACTIVITY_FIELD.toString();
	}

	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		return null;
	}
	
	public Integer insertNewActivityField(Integer activityId, Integer fieldId) throws SQLException  {
		String sql = "INSERT INTO " + getTableName() + " (activity_id, field_id) "
				+ "VALUES(?1, ?2)";
		Query query = new Query(sql);
		query.params(activityId, fieldId);
		
		int key = executeUpdate(query.getQuery());
		return key;
	}

}
