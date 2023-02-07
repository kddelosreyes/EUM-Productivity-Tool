package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.AnalystActivityFieldDetail;
import com.project.eum.prodtool.model.column.AnalystActivityFieldDetailColumn;
import com.project.eum.prodtool.model.field.AnalystActivityFieldDetailField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class AnalystActivityFieldDetailService extends Service {

	@Override
	public String getTableName() throws SQLException {
		return Table.ANALYST_ACTIVITY_FIELD_DETAIL.toString();
	}

	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(AnalystActivityFieldDetailColumn.ID.getColumnName());
		Integer analystActivityId = resultSet.getInt(AnalystActivityFieldDetailColumn.ANALYST_ACTIVITY_ID.getColumnName());
		Integer fieldId = resultSet.getInt(AnalystActivityFieldDetailColumn.FIELD_ID.getColumnName());
		String value = resultSet.getString(AnalystActivityFieldDetailColumn.VALUE.getColumnName());
		LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AnalystActivityFieldDetailColumn.CREATED_DATE.getColumnName()));
		LocalDateTime updatedDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AnalystActivityFieldDetailColumn.UPDATED_DATE.getColumnName()));
		String uuid = resultSet.getString(AnalystActivityFieldDetailColumn.UUID.getColumnName());
		
		Entity entity = new AnalystActivityFieldDetail()
				.set(AnalystActivityFieldDetailField.id, id)
				.set(AnalystActivityFieldDetailField.analystActivityId, analystActivityId)
				.set(AnalystActivityFieldDetailField.fieldId, fieldId)
				.set(AnalystActivityFieldDetailField.value, value)
				.set(AnalystActivityFieldDetailField.createdDate, createdDate)
				.set(AnalystActivityFieldDetailField.updatedDate, updatedDate)
				.set(AnalystActivityFieldDetailField.uuid, uuid);
				
		return entity;
	}

	public Integer insertActivityFieldValue(Integer analystActivityId, Integer fieldId, String value) throws SQLException {
		Query query = new Query("INSERT INTO analyst_activity_field_detail(analyst_activity_id, field_id, value, uuid) "
				+ "VALUES(?1, ?2, ?3, uuid())");
		query.params(analystActivityId, fieldId, value);
		
		int returnedKey = executeUpdate(query.getQuery());
		return returnedKey;
	}
	
	public Integer updateActivityFieldValue(Integer analystActivityId, Integer fieldId, String value) throws SQLException {
		Query query = new Query("UPDATE analyst_activity_field_detail "
				+ "SET value = ?1 "
				+ "WHERE analyst_activity_id = ?2 "
				+ "AND field_id = ?3");
		query.params(value, analystActivityId, fieldId);
		
		int affectedRows = executeUpdate(query.getQuery());
		return affectedRows;
	}
	
	public List<Entity> getRemarksFromAnalystActivityIds(String ids) throws SQLException {
		List<Entity> entities = new ArrayList<>();
		
		String sql = "SELECT * "
				+ "FROM analyst_activity_field_detail "
				+ "WHERE analyst_activity_id IN (" + ids +") "
				+ "		AND field_id = 5";
		Query query = new Query(sql);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		while (resultSet.next()) {
			entities.add(getResultSetEntity(resultSet));
		}
		
		return entities;
	}
	
}
