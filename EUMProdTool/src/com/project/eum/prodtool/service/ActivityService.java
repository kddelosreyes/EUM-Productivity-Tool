package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.Activity;
import com.project.eum.prodtool.model.ActivityFieldMapping;
import com.project.eum.prodtool.model.ActivityType;
import com.project.eum.prodtool.model.add.ActivityLog;
import com.project.eum.prodtool.model.column.ActivityColumn;
import com.project.eum.prodtool.model.field.ActivityField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class ActivityService extends Service {
	
	private final ActivityTypeService activityTypeService = new ActivityTypeService();

	@Override
	public String getTableName() {
		return Table.ACTIVITY.toString();
	}
	
	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(ActivityColumn.ID.getColumnName());
		String name = resultSet.getString(ActivityColumn.NAME.getColumnName());
		Integer activityTypeId = resultSet.getInt(ActivityColumn.ACTIVITY_TYPE_ID.getColumnName());
		String uuid = resultSet.getString(ActivityColumn.UUID.getColumnName());
		LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(ActivityColumn.CREATED_DATE.getColumnName()));
		
		ActivityType activityType = (ActivityType) activityTypeService.getEntityById(activityTypeId);
		
		Entity entity = new Activity()
				.set(ActivityField.ID, id)
				.set(ActivityField.NAME, name)
				.set(ActivityField.ACTIVITY_TYPE_ID, activityTypeId)
				.set(ActivityField.ACTIVITY_TYPE, activityType)
				.set(ActivityField.UUID, uuid)
				.set(ActivityField.CREATED_DATE, createdDate);
		
		return entity;
	}
	
	public List<Activity> getActivitiesByAnalystId(Integer analystId) throws SQLException {
		List<Activity> entities = new ArrayList<Activity>();
		
		Query query = new Query("SELECT a.* FROM " + getTableName() + " a "
				+ "join team_activity ta "
		    		+ "on ta.activity_id = a.id "
		    	+ "join team t "
		    		+ "on t.id = ta.team_id "
			    + "join analyst_team ant "
			    	+ "on ant.team_id = t.id "
			    + "join analyst an "
			    	+ "on an.id = ant.analyst_id "
				+ "where an.id = ?1");
		query.params(analystId);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		
		while (resultSet.next()) {
			entities.add((Activity) getResultSetEntity(resultSet));
		}
		
		return entities;
	}
	
	public List<ActivityLog> getActivityLogs() throws SQLException {
		List<ActivityLog> activityLogs = new ArrayList<>();
		String sqlQuery = "SELECT * FROM "
				+ "((SELECT concat(an.first_name, ' ' , an.last_name) as 'name', ac.name as 'activity', time(aa.start_time) as 'time', 'START' as 'type' "
				+ "FROM analyst an "
				+ "JOIN analyst_activity aa ON aa.analyst_id = an.id "
				+ "JOIN activity ac ON ac.id = aa.activity_id "
				+ "WHERE date_format(aa.start_time, '%Y-%m-%d') = date_format(CURRENT_TIMESTAMP, '%Y-%m-%d') "
				+ "ORDER BY aa.start_time DESC) "
				+ "UNION "
				+ "(SELECT concat(an.first_name, ' ' , an.last_name) as 'name', ac.name as 'activity', time(aa.end_time) as 'time', 'END' as 'type' "
				+ "FROM analyst an "
				+ "JOIN analyst_activity aa ON aa.analyst_id = an.id "
				+ "JOIN activity ac ON ac.id = aa.activity_id "
				+ "WHERE date_format(aa.end_time, '%Y-%m-%d') = date_format(CURRENT_TIMESTAMP, '%Y-%m-%d') "
				+ "ORDER BY aa.end_time DESC)) sub "
				+ "ORDER BY sub.time DESC "
				+ "LIMIT 10";
		
		Query query = new Query(sqlQuery);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		
		while (resultSet.next()) {
			String name = resultSet.getString("name");
			String activity = resultSet.getString("activity");
			LocalTime time = DateTimeUtils.convertTimeToLocalTime(resultSet.getTime("time"));
			String type = resultSet.getString("type");
			
			ActivityLog activityLog = new ActivityLog(name, activity, time, type);
			activityLogs.add(activityLog);
		}
		
		return activityLogs;
	}
	
	public List<ActivityFieldMapping> getActivityInputMappings() throws SQLException {
		List<ActivityFieldMapping> mapping = new ArrayList<>();
		
		String sqlQuery = "SELECT AF.id, A.name as 'activity_name', F.name as 'field_name', AF.created_date "
				+ "FROM activity_field AF "
				+ "JOIN activity A on A.id = AF.activity_id "
				+ "JOIN field F on F.id = AF.field_id "
				+ "ORDER BY A.id, F.id";
		
		Query query = new Query(sqlQuery);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		
		while (resultSet.next()) {
			Integer id = resultSet.getInt("id");
			String activityName = resultSet.getString("activity_name");
			String fieldName = resultSet.getString("field_name");
			LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp("created_date"));
			
			ActivityFieldMapping map = new ActivityFieldMapping(id, activityName, fieldName, createdDate);
			mapping.add(map);
		}
		
		return mapping;
	}
	
}
