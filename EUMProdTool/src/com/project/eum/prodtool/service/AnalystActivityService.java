package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.Activity;
import com.project.eum.prodtool.model.AnalystActivity;
import com.project.eum.prodtool.model.column.AnalystActivityColumn;
import com.project.eum.prodtool.model.field.AnalystActivityField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class AnalystActivityService extends Service {

	private final ActivityService activityService = new ActivityService();
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public String getTableName() throws SQLException {
		return Table.ANALYST_ACTIVITY.toString();
	}

	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(AnalystActivityColumn.ID.getColumnName());
		Integer analystId = resultSet.getInt(AnalystActivityColumn.ANALYST_ID.getColumnName());
		Integer activityId = resultSet.getInt(AnalystActivityColumn.ACTIVITY_ID.getColumnName());
		LocalDateTime startTime = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AnalystActivityColumn.START_TIME.getColumnName()));
		LocalDateTime endTime = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AnalystActivityColumn.END_TIME.getColumnName()));
		String remarks = resultSet.getString(AnalystActivityColumn.REMARKS.getColumnName());
		LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AnalystActivityColumn.CREATED_DATE.getColumnName()));
		LocalDateTime updatedDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AnalystActivityColumn.UPDATED_DATE.getColumnName()));
		
		Activity activity = (Activity) activityService.getEntityById(activityId);
		
		Entity entity = new AnalystActivity()
				.set(AnalystActivityField.id, id)
				.set(AnalystActivityField.analystId, analystId)
				.set(AnalystActivityField.activityId, activityId)
				.set(AnalystActivityField.activity, activity)
				.set(AnalystActivityField.startTime, startTime)
				.set(AnalystActivityField.endTime, endTime)
				.set(AnalystActivityField.remarks, remarks)
				.set(AnalystActivityField.createdDate, createdDate)
				.set(AnalystActivityField.updatedDate, updatedDate);
		
		return entity;
	}
	
	public Integer insertNewAnalystActivity(Integer analystId, Integer activityId) throws SQLException {
		Query query = new Query("INSERT INTO analyst_activity(analyst_id, activity_id, start_time) "
				+ "VALUES(?1, ?2, current_timestamp())");
		query.params(analystId, activityId);
		
		int affectedRows = executeUpdate(query.getQuery());
		return affectedRows;
	}
	
	public Integer updateAnalystActivityEndTimeById(Integer analystActivityId) throws SQLException {
		Query query = new Query("UPDATE analyst_activity "
				+ "SET end_time = current_timestamp() "
				+ "WHERE id = ?1");
		query.params(analystActivityId);
		
		int affectedRows = executeUpdate(query.getQuery());
		return affectedRows;
	}
	
	public List<AnalystActivity> getTodaysActivitiesByAnalystId(Integer analystId) throws SQLException {
		List<AnalystActivity> analystActivities = new ArrayList<AnalystActivity>();
		
		Date date = new Date();
		String stringDate = formatter.format(date);
		
		Query query = new Query("SELECT * "
				+ "FROM analyst_activity "
				+ "WHERE analyst_id = ?1 "
				+ "AND date_format(start_time, '%Y-%m-%d') = ?2 "
				+ "ORDER BY start_time");
		query.params(analystId, stringDate);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		while (resultSet.next()) {
			analystActivities.add((AnalystActivity) getResultSetEntity(resultSet));
		}
		
		return analystActivities;
	}
	
	public List<AnalystActivity> getTodaysActivitiesByAnalystId(Integer analystId, LocalDateTime startTime, LocalDateTime endTime) throws SQLException {
		List<AnalystActivity> analystActivities = new ArrayList<AnalystActivity>();
		
		String sql = "";
		
		if (endTime == null) {
			sql = "SELECT * "
					+ "FROM analyst_activity "
					+ "WHERE analyst_id = ?1 "
					+ "AND start_time > ?2 "
					+ "ORDER BY start_time";
		} else {
			sql = "SELECT * "
					+ "FROM analyst_activity "
					+ "WHERE analyst_id = ?1 "
					+ "AND start_time BETWEEN ?2 AND ?3 "
					+ "ORDER BY start_time";
		}
		
		Query query = new Query(sql);
		
		if (endTime == null) {
			query.params(analystId, DateTimeUtils.toSqlDateTimeString(startTime));
		} else {
			query.params(analystId, DateTimeUtils.toSqlDateTimeString(startTime), DateTimeUtils.toSqlDateTimeString(endTime));
		}
		
		ResultSet resultSet = executeQuery(query.getQuery());
		while (resultSet.next()) {
			analystActivities.add((AnalystActivity) getResultSetEntity(resultSet));
		}
		
		return analystActivities;
	}
	
	public Boolean isAnyPendingActivityByAnalystId(Integer analystId) throws SQLException {
		Date date = new Date();
		String stringDate = formatter.format(date);
		
		Query query = new Query("SELECT * "
				+ "FROM analyst_activity "
				+ "WHERE analyst_id = ?1 "
				+ "AND date_format(start_time, '%Y-%m-%d') = ?2 "
				+ "AND end_time IS NULL "
				+ "ORDER BY start_time");
		query.params(analystId, stringDate);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		return resultSet.next();
	}

}
