package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.FormField;
import com.project.eum.prodtool.model.column.FormFieldColumn;
import com.project.eum.prodtool.model.field.FormFieldField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class FormFieldService extends Service {

	@Override
	public String getTableName() throws SQLException {
		return Table.FIELD.toString();
	}

	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(FormFieldColumn.ID.getColumnName());
		String name = resultSet.getString(FormFieldColumn.NAME.getColumnName());
		String type = resultSet.getString(FormFieldColumn.TYPE.getColumnName());
		Boolean isRequired = resultSet.getInt(FormFieldColumn.IS_REQUIRED.getColumnName()) == 1;
		LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(FormFieldColumn.CREATED_DATE.getColumnName()));
		
		Entity entity = new FormField()
				.set(FormFieldField.id, id)
				.set(FormFieldField.name, name)
				.set(FormFieldField.type, type)
				.set(FormFieldField.isRequired, isRequired)
				.set(FormFieldField.createdDate, createdDate);
		
		return entity;
	}
	
	public List<FormField> getFormFieldsByActivityId(Integer activityId) throws SQLException {
		List<FormField> entities = new ArrayList<FormField>();
		
		Query query = new Query("SELECT f.* FROM field f "
				+ "JOIN activity_field af "
					+ "ON af.field_id = f.id "
				+ "JOIN activity a "
					+ "ON a.id = af.activity_id "
				+ "WHERE a.id = ?1");
		query.params(activityId);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		
		while (resultSet.next()) {
			entities.add((FormField) getResultSetEntity(resultSet));
		}
		
		return entities;
	}
	
}
