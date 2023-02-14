package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.Analyst;
import com.project.eum.prodtool.model.AnalystShiftSchedule;
import com.project.eum.prodtool.model.ShiftSchedule;
import com.project.eum.prodtool.model.add.T_AnalystShiftSchedule;
import com.project.eum.prodtool.model.column.AnalystShiftScheduleColumn;
import com.project.eum.prodtool.model.field.AnalystShiftScheduleField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class AnalystShiftScheduleService extends Service {

	private AnalystService analystService = new AnalystService();
	private ShiftScheduleService shiftScheduleService = new ShiftScheduleService();
	
	@Override
	public String getTableName() throws SQLException {
		return Table.ANALYST_SHIFT_SCHEDULE.toString();
	}

	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(AnalystShiftScheduleColumn.ID.getColumnName());
		Integer analystId = resultSet.getInt(AnalystShiftScheduleColumn.ANALYST_ID.getColumnName());
		Integer shiftScheduleId = resultSet.getInt(AnalystShiftScheduleColumn.SHIFT_SCHEDULE_ID.getColumnName());
		LocalDate fromDate = DateTimeUtils.convertDateToLocalDate(resultSet.getDate(AnalystShiftScheduleColumn.FROM_DATE.getColumnName()));
		java.sql.Date toDateSql = resultSet.getDate(AnalystShiftScheduleColumn.TO_DATE.getColumnName());
		LocalDate toDate = toDateSql == null ? null : DateTimeUtils.convertDateToLocalDate(toDateSql);
		String restDays = resultSet.getString(AnalystShiftScheduleColumn.REST_DAYS.getColumnName());
		LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AnalystShiftScheduleColumn.CREATED_DATE.getColumnName()));
		LocalDateTime updatedDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AnalystShiftScheduleColumn.UPDATED_DATE.getColumnName()));
		
		Analyst analyst = (Analyst) analystService.getEntityById(analystId);
		ShiftSchedule shiftSchedule = (ShiftSchedule) shiftScheduleService.getEntityById(shiftScheduleId);
		
		Entity entity = new AnalystShiftSchedule()
				.set(AnalystShiftScheduleField.id, id)
				.set(AnalystShiftScheduleField.analystId, analystId)
				.set(AnalystShiftScheduleField.analyst, analyst)
				.set(AnalystShiftScheduleField.shiftScheduleId, shiftScheduleId)
				.set(AnalystShiftScheduleField.shiftSchedule, shiftSchedule)
				.set(AnalystShiftScheduleField.fromDate, fromDate)
				.set(AnalystShiftScheduleField.toDate, toDate)
				.set(AnalystShiftScheduleField.restDays, restDays)
				.set(AnalystShiftScheduleField.createdDate, createdDate)
				.set(AnalystShiftScheduleField.updatedDate, updatedDate);
		
		return entity;
	}
	
	public List<Entity> getCurrentShiftSchedules() throws SQLException {
		List<Entity> shiftSchedules = new ArrayList<>();
		
		String sqlQuery = "SELECT ass.* "
				+ "FROM analyst a "
				+ "JOIN analyst_shift_schedule ass ON a.id = ass.analyst_id "
				+ "JOIN shift_schedule ss ON ss.id = ass.shift_schedule_id "
				+ "WHERE current_date() BETWEEN from_date AND to_date";
		
		Query query = new Query(sqlQuery);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		
		while (resultSet.next()) {
			shiftSchedules.add(getResultSetEntity(resultSet));
		}
		
		return shiftSchedules;
	}
	
	public List<Entity> getShiftSchedulesByAnalystId(Integer analystId) throws SQLException {
		List<Entity> analystShiftSchedules = new ArrayList<>();
		
		String sql = "SELECT * "
				+ "FROM analyst_shift_schedule "
				+ "WHERE analyst_id = ?1";
		Query query = new Query(sql);
		query.params(analystId);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		while (resultSet.next()) {
			analystShiftSchedules.add(getResultSetEntity(resultSet));
		}
		
		return analystShiftSchedules;
	}
	
	public List<T_AnalystShiftSchedule> getAllShiftSchedulesForCurrentQuarter() throws SQLException {
		List<T_AnalystShiftSchedule> shiftSchedules = new ArrayList<>();
		
		LocalDate myLocal = LocalDate.now();
		int quarter = myLocal.get(IsoFields.QUARTER_OF_YEAR);
		int year = myLocal.getYear();
		
		String sql = "SELECT analyst_id, CONCAT(first_name, ' ', last_name) as 'analyst_name', role,"
				+ "		ss.name, start_time, end_time,"
				+ "		from_date, ss.is_night_shift, ass.id "
				+ "FROM analyst_shift_schedule ass "
				+ "		JOIN analyst a "
				+ "			ON a.id = ass.analyst_id "
				+ "		JOIN shift_schedule ss "
				+ "			ON ss.id = ass.shift_schedule_id "
				+ "WHERE quarter(from_date) = ?1 "
				+ "		OR quarter(from_date) = ?2 "
				+ "		AND year(from_date) = ?3 "
				+ "		AND a.is_active = 1 "
				+ "ORDER BY analyst_id, from_date";
		Query query = new Query(sql);
		query.params(quarter, quarter, year);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		
		while (resultSet.next()) {
			Integer analystId = resultSet.getInt("analyst_id");
			String name = resultSet.getString("analyst_name");
			String role = resultSet.getString("role");
			String shiftName = resultSet.getString("name");
			LocalTime startTime = DateTimeUtils.convertTimeToLocalTime(resultSet.getTime("start_time"));
			LocalTime endTime = DateTimeUtils.convertTimeToLocalTime(resultSet.getTime("end_time"));
			LocalDate fromDate = DateTimeUtils.convertDateToLocalDate(resultSet.getDate("from_date"));
			Boolean isNightShift = resultSet.getInt("is_night_shift") == 1;
			Integer analystShiftScheduleId = resultSet.getInt("id");
			
			shiftSchedules.add(
					new T_AnalystShiftSchedule(
							analystId, name, role, 
							shiftName, startTime, endTime,
							fromDate, isNightShift, analystShiftScheduleId
							)
					);
		}
		
		return shiftSchedules;
	}
	
	public Integer insertNewAnalystShiftSchedule(Integer analystId, Integer shiftScheduleId, Date fromDate) throws SQLException {
		String sql = "INSERT INTO " + getTableName() + " (analyst_id, shift_schedule_id, from_date) "
				+ "VALUES(?1, ?2, ?3)";
		Query query = new Query(sql);
		query.params(analystId, shiftScheduleId, DateTimeUtils.toSqlDateString(fromDate));
		
		Integer key = executeUpdate(query.getQuery());
		return key;
	}
	
	public Integer saveAnalystShiftSchedule(Integer id, Integer shiftScheduleId) throws SQLException {
		String sql = "UPDATE " + getTableName() 
				+ " SET shift_schedule_id = ?1 "
				+ "WHERE id = ?2";
		
		Query query = new Query(sql);
		query.params(shiftScheduleId, id);
		
		Integer key = executeUpdate(query.getQuery());
		return key;
	}

}
