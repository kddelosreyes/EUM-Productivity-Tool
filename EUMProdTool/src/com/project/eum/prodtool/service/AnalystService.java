package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.Analyst;
import com.project.eum.prodtool.model.AnalystLogin;
import com.project.eum.prodtool.model.column.AnalystColumn;
import com.project.eum.prodtool.model.column.AnalystLoginColumn;
import com.project.eum.prodtool.model.field.AnalystField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class AnalystService extends Service {

	private final AnalystLoginService analystLoginService = new AnalystLoginService();
	
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
		
		AnalystLogin analystLogin = (AnalystLogin) analystLoginService.getEntityByColumn(AnalystLoginColumn.ANALYST_ID, id);
		
		Entity entity = new Analyst()
				.set(AnalystField.ID, id)
				.set(AnalystField.FIRST_NAME, firstName)
				.set(AnalystField.MIDDLE_NAME, middleName)
				.set(AnalystField.LAST_NAME, lastName)
				.set(AnalystField.ROLE, role)
				.set(AnalystField.IS_ACTIVE, isActive)
				.set(AnalystField.CREATED_DATE, createdDate)
				.set(AnalystField.UPDATED_DATE, updatedDate)
				.set(AnalystField.UUID, uuid)
				.set(AnalystField.ANALYST_LOGIN, analystLogin);
		
		return entity;
	}
	
	public Integer insertNewAnalyst(String firstName, String middleName, String lastName,
			String role) throws SQLException {
		String sqlQuery = "INSERT INTO " + getTableName() + "(first_name, middle_name, last_name, role, is_active, uuid) "
				+ "VALUES(?1, ?2, ?3, ?4, 1, uuid())";
		
		Query query = new Query(sqlQuery);
		query.params(firstName, middleName, lastName, role);
		
		int key = executeUpdate(query.getQuery());
		return key;
	}
	
	public Integer updateAnalyst(String firstName, String middleName, String lastName,
			String role, Integer analystId) throws SQLException {
		String sqlQuery = "UPDATE " + getTableName()
				+ "SET first_name = ?1, "
				+ "middle_name = ?2, "
				+ "last_name = ?3, "
				+ "role = ?4 "
				+ "WHERE id = ?5";
		
		Query query = new Query(sqlQuery);
		query.params(firstName, middleName, lastName, role, analystId);
		
		int key = executeUpdate(query.getQuery());
		return key;
	}
	
	public Boolean hasTeam(Integer analystId) throws SQLException {
		String sql = "SELECT count(*) FROM analyst_team ant JOIN analyst a ON a.id = ant.analyst_id where a.id = ?1";
		
		Query query = new Query(sql);
		query.params(analystId);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		resultSet.next();
		return resultSet.getInt(1) > 0;
	}
	
	public Integer updateAnalystActive(String analystId, Integer isActive) throws SQLException {
		String sql = "UPDATE " + getTableName() + " "
				+ "SET is_active = ?1 "
				+ "WHERE uuid = ?2";
		Query query = new Query(sql);
		query.params(isActive, analystId);
		
		int affectedRows = executeUpdate(query.getQuery());
		return affectedRows;
	}
	
}
