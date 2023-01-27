package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.Analyst;
import com.project.eum.prodtool.model.column.AnalystColumn;
import com.project.eum.prodtool.model.field.AnalystField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class AnalystService extends Service {

	@Override
	public String getTableName() {
		return Table.ANALYST.toString();
	}
	
	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(AnalystColumn.ID.getColumnName());
		String firstName = resultSet.getString(AnalystColumn.FIRST_NAME.getColumnName());
		String middleName = resultSet.getString(AnalystColumn.MIDDLE_NAME.getColumnName());
		String lastName = resultSet.getString(AnalystColumn.LAST_NAME.getColumnName());
		String role = resultSet.getString(AnalystColumn.ROLE.getColumnName());
		Boolean isActive = resultSet.getInt("is_active") == 1;
		LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AnalystColumn.CREATED_DATE.getColumnName()));
		LocalDateTime updatedDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AnalystColumn.UPDATED_DATE.getColumnName()));
		String uuid = resultSet.getString(AnalystColumn.UUID.getColumnName());
		
		Entity entity = new Analyst()
				.set(AnalystField.ID, id)
				.set(AnalystField.FIRST_NAME, firstName)
				.set(AnalystField.MIDDLE_NAME, middleName)
				.set(AnalystField.LAST_NAME, lastName)
				.set(AnalystField.ROLE, role)
				.set(AnalystField.IS_ACTIVE, isActive)
				.set(AnalystField.CREATED_DATE, createdDate)
				.set(AnalystField.UPDATED_DATE, updatedDate)
				.set(AnalystField.UUID, uuid);
		
		return entity;
	}
	
	
}
