package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.AnalystLogin;
import com.project.eum.prodtool.model.field.AnalystLoginField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class AnalystLoginService extends Service {

	@Override
	public String getTableName() {
		return Table.ANALYST_LOGIN.toString();
	}
	
	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		Integer analystId = resultSet.getInt("analyst_id");
		String username = resultSet.getString("username");
		String password = resultSet.getString("password");
		String salt = resultSet.getString("salt");
		LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp("created_date"));
		LocalDateTime updatedDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp("updated_date"));
		Boolean isLocked = resultSet.getInt("is_locked") == 1;
		Integer attempt = resultSet.getInt("attempt");
		Integer securityQuestion = resultSet.getInt("security_question");
		String securityAnswer = resultSet.getString("security_answer");
		String uuid = resultSet.getString("uuid");
		
		Entity entity = new AnalystLogin()
				.set(AnalystLoginField.ID, id)
				.set(AnalystLoginField.ANALYST_ID, analystId)
				.set(AnalystLoginField.USERNAME, username)
				.set(AnalystLoginField.PASSWORD, password)
				.set(AnalystLoginField.SALT, salt)
				.set(AnalystLoginField.CREATED_DATE, createdDate)
				.set(AnalystLoginField.UPDATED_DATE, updatedDate)
				.set(AnalystLoginField.IS_LOCKED, isLocked)
				.set(AnalystLoginField.ATTEMPT, attempt)
				.set(AnalystLoginField.SECURITY_QUESTION, securityQuestion)
				.set(AnalystLoginField.SECURITY_ANSWER, securityAnswer)
				.set(AnalystLoginField.UUID, uuid);
		
		return entity;
	}
	
	public Integer insertNewAnalystLogin(Integer analystId, String username, String password,
			String salt, Integer isLocked, Integer attempt) throws SQLException {
		String sqlQuery = "INSERT INTO " + getTableName() + "(analyst_id, username, password, salt, is_locked, attempt, uuid) "
				+ "VALUES(?1, ?2, ?3, ?4, ?5, ?6, uuid())";
		
		Query query = new Query(sqlQuery);
		query.params(analystId, username, password, salt, isLocked, attempt);
		
		int key = executeUpdate(query.getQuery());
		return key;
	}
	
	public Integer updateAnalystLoginPassword(Integer analystLoginId, String password) throws SQLException {
		String sqlQuery = "UPDATE " + getTableName() + " "
				+ "SET password = ?1, "
				+ "is_locked = ?2 "
				+ "WHERE id = ?3";
		
		Query query = new Query(sqlQuery);
		query.params(password, 0, analystLoginId);
		
		int key = executeUpdate(query.getQuery());
		return key;
	}
	
	public Integer updateAnalystLoginPassword(Integer analystLoginId, String password, Integer securityQuestion, String securityAnswer) throws SQLException {
		String sqlQuery = "UPDATE " + getTableName() + " "
				+ "SET password = ?1, "
				+ "is_locked = ?2, "
				+ "security_question = ?3, "
				+ "security_answer = ?4 "
				+ "WHERE id = ?5";
		
		Query query = new Query(sqlQuery);
		query.params(password, 0, securityQuestion, securityAnswer, analystLoginId);
		
		int key = executeUpdate(query.getQuery());
		return key;
	}
	
}
