package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.ActivityType;
import com.project.eum.prodtool.model.column.ActivityTypeColumn;
import com.project.eum.prodtool.model.field.ActivityTypeField;
import com.project.eum.prodtool.service.constants.Table;

public class ActivityTypeService extends Service {

	@Override
	public String getTableName() throws SQLException {
		return Table.ACTIVITY_TYPE.toString();
	}

	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(ActivityTypeColumn.ID.getColumnName());
		String type = resultSet.getString(ActivityTypeColumn.TYPE.getColumnName());
		String uuid = resultSet.getString(ActivityTypeColumn.UUID.getColumnName());
		
		Entity entity = new ActivityType()
				.set(ActivityTypeField.ID, id)
				.set(ActivityTypeField.TYPE, type)
				.set(ActivityTypeField.UUID, uuid);
		
		return entity;
	}
	
	public Integer insertNewActivityType(String name) throws SQLException {
		String sql = "INSERT INTO " + getTableName() + " (type, uuid) "
				+ "VALUES (?1, uuid())";
		Query query = new Query(sql);
		query.params(name);
		
		int key = executeUpdate(query.getQuery());
		return key;
	}

}
