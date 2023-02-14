package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.ShiftSchedule;
import com.project.eum.prodtool.model.column.ShiftScheduleColumn;
import com.project.eum.prodtool.model.field.ShiftScheduleField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class ShiftScheduleService extends Service {

	@Override
	public String getTableName() throws SQLException {
		return Table.SHIFT_SCHEDULE.toString();
	}

	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(ShiftScheduleColumn.ID.getColumnName());
		String name = resultSet.getString(ShiftScheduleColumn.NAME.getColumnName());
		LocalTime startTime = DateTimeUtils.convertTimeToLocalTime(resultSet.getTime(ShiftScheduleColumn.START_TIME.getColumnName()));
		LocalTime endTime = DateTimeUtils.convertTimeToLocalTime(resultSet.getTime(ShiftScheduleColumn.END_TIME.getColumnName()));
		Boolean isNightShift = resultSet.getInt(ShiftScheduleColumn.IS_NIGHT_SHIFT.getColumnName()) == 1;
		LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(ShiftScheduleColumn.CREATED_DATE.getColumnName()));
		LocalDateTime updatedDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(ShiftScheduleColumn.UPDATED_DATE.getColumnName()));
		String uuid = resultSet.getString(ShiftScheduleColumn.UUID.getColumnName());
		
		Entity entity = new ShiftSchedule()
				.set(ShiftScheduleField.id, id)
				.set(ShiftScheduleField.name, name)
				.set(ShiftScheduleField.startTime, startTime)
				.set(ShiftScheduleField.endTime, endTime)
				.set(ShiftScheduleField.isNightShift, isNightShift)
				.set(ShiftScheduleField.createdDate, createdDate)
				.set(ShiftScheduleField.updatedDate, updatedDate)
				.set(ShiftScheduleField.uuid, uuid);
		
		return entity;
	}
	
	public Entity getShiftScheduleByAnalystId(Integer analystId) throws SQLException {
		Query query = new Query("SELECT ss.* "
				+ "FROM shift_schedule ss "
				+ "JOIN analyst_shift_schedule ass "
				+ "ON ass.shift_schedule_id = ss.id "
				+ "JOIN analyst a "
				+ "ON a.id = ass.analyst_id "
				+ "WHERE current_date() = from_date "
				+ "AND a.id = ?1");
		query.params(analystId);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		if (resultSet.next()) {
			return getResultSetEntity(resultSet);
		} else {
			return null;
		}
	}
	
	public Integer insertNewShiftSchedule(String name, String startTime, String endTime,
			Integer isNightShift) throws SQLException {
		String sql = "INSERT INTO " + getTableName() + "(name, start_time, end_time, is_night_shift, uuid) "
				+ "VALUES(?1, ?2, ?3, ?4, uuid())" ;
		Query query = new Query(sql);
		query.params(name, startTime, endTime, isNightShift);
		
		Integer key = executeUpdate(query.getQuery());
		return key;
	}

}
