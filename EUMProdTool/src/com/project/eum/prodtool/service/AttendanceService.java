package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.Attendance;
import com.project.eum.prodtool.model.add.AttendanceCounts;
import com.project.eum.prodtool.model.add.AttendanceLog;
import com.project.eum.prodtool.model.column.AttendanceColumn;
import com.project.eum.prodtool.model.field.AttendanceField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class AttendanceService extends Service {

	@Override
	public String getTableName() {
		return Table.ATTENDANCE.toString();
	}
	
	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(AttendanceColumn.ID.getColumnName());
		Integer attendanceAnalystId = resultSet.getInt(AttendanceColumn.ANALYST_ID.getColumnName());
		LocalDateTime timeIn = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AttendanceColumn.TIME_IN.getColumnName()));
		LocalDateTime timeOut = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AttendanceColumn.TIME_OUT.getColumnName()));
		String remarks = resultSet.getString(AttendanceColumn.REMARKS.getColumnName());
		String status = resultSet.getString(AttendanceColumn.STATUS.getColumnName());
		LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AttendanceColumn.CREATED_DATE.getColumnName()));
		LocalDateTime updatedDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AttendanceColumn.UPDATED_DATE.getColumnName()));
		
		Entity attendance = new Attendance()
				.set(AttendanceField.id, id)
				.set(AttendanceField.analystId, attendanceAnalystId)
				.set(AttendanceField.timeIn, timeIn)
				.set(AttendanceField.timeOut, timeOut)
				.set(AttendanceField.remarks, remarks)
				.set(AttendanceField.status, status)
				.set(AttendanceField.createdDate, createdDate)
				.set(AttendanceField.updatedDate, updatedDate);
		
		return attendance;
		
	}
	
	public Integer insertNewAttendanceForAnalystId(Integer analystId) throws SQLException {
		Query query = new Query("INSERT INTO attendance(analyst_id, time_in, remarks) "
				+ "VALUES(?1, current_timestamp(), 'REGULAR')");
		query.params(analystId);
		
		int affectedRows = executeUpdate(query.getQuery());
		return affectedRows;
	}
	
	public boolean hasAttendanceForToday(Integer analystId) throws SQLException {
		Query query = new Query("SELECT * "
				+ "FROM attendance "
				+ "WHERE analyst_id = ?1 "
				+ "AND date_format(time_in, '%Y-%m-%d') = date_format(CURRENT_TIMESTAMP, '%Y-%m-%d') "
				+ "AND remarks = 'REGULAR'");
		query.params(analystId);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		return resultSet.next();
	}
	
	public Entity getAttendanceForToday(Integer analystId) throws SQLException {
		Query query = new Query("SELECT * "
				+ "FROM attendance "
				+ "WHERE analyst_id = ?1 "
				+ "AND date_format(time_in, '%Y-%m-%d') = date_format(CURRENT_TIMESTAMP, '%Y-%m-%d') "
				+ "AND remarks = 'REGULAR'");
		query.params(analystId);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		if (resultSet.next()) {
			return getResultSetEntity(resultSet);
		} else {
			return null;
		}
	}
	
	public boolean hasAttendanceForYesterdayNoOut(Integer analystId) throws SQLException {
		Query query = new Query("SELECT * "
				+ "FROM attendance " 
				+ "WHERE analyst_id = ?1 " 
				+ "AND date_format(time_in, '%Y-%m-%d') = date_format(date_sub(CURRENT_TIMESTAMP, interval 1 day), '%Y-%m-%d') " 
				+ "AND time_out IS NULL "
				+ "AND remarks = 'REGULAR'");
		query.params(analystId);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		return resultSet.next();
	}
	
	public boolean hasAttendanceForYesterday(Integer analystId) throws SQLException {
		Query query = new Query("SELECT * "
				+ "FROM attendance " 
				+ "WHERE analyst_id = ?1 " 
				+ "AND date_format(time_in, '%Y-%m-%d') = date_format(date_sub(CURRENT_TIMESTAMP, interval 1 day), '%Y-%m-%d') " 
				+ "AND time_out IS NOT NULL "
				+ "AND remarks = 'REGULAR'");
		query.params(analystId);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		return resultSet.next();
	}
	
	public Entity getAttendanceForYesterday(Integer analystId) throws SQLException {
		Query query = new Query("SELECT * "
				+ "FROM attendance " 
				+ "WHERE analyst_id = ?1 " 
				+ "AND date_format(time_in, '%Y-%m-%d') = date_format(date_sub(CURRENT_TIMESTAMP, interval 1 day), '%Y-%m-%d') " 
				+ "AND remarks = 'REGULAR'");
		query.params(analystId);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		if (resultSet.next()) {
			return getResultSetEntity(resultSet);
		} else {
			return null;
		}
	}
	
	public Entity getLatestAttendance(Integer analystId) throws SQLException {
		String sql = "SELECT * "
				+ "FROM attendance "
				+ "WHERE id = ( "
				+ "SELECT max(id) "
				+ "FROM attendance "
				+ "WHERE analyst_id = ?1) "
				+ "AND remarks = 'REGULAR'";
		Query query = new Query(sql);
		query.params(analystId);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		if (resultSet.next()) {
			return getResultSetEntity(resultSet);
		} else {
			return null;
		}
	}
	
	public Integer updateAnalystAttendance(Integer attendanceId) throws SQLException {
		Query query = new Query("UPDATE attendance "
				+ "SET time_out = current_timestamp() "
				+ "WHERE id = ?1");
		query.params(attendanceId);
		
		int affectedRows = executeUpdate(query.getQuery());
		return affectedRows;
	}
	
	public Integer updateAttendanceStatus(Integer attendanceId, String status) throws SQLException {
		Query query = new Query("UPDATE attendance "
				+ "SET status = ?1 "
				+ "WHERE id = ?2");
		query.params(status, attendanceId);
		
		int affectedRows = executeUpdate(query.getQuery());
		return affectedRows;
	}
	
	public List<Entity> getAttendancesForToday() throws SQLException {
		List<Entity> attendances = new ArrayList<>();
		String sqlQuery = "SELECT a.* FROM attendance a "
				+ "JOIN analyst an ON an.id = a.analyst_id "
				+ "WHERE date_format(a.time_in, '%Y-%m-%d') = date_format(CURRENT_TIMESTAMP, '%Y-%m-%d')";
		
		Query query = new Query(sqlQuery);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		while (resultSet.next()) {
			attendances.add(getResultSetEntity(resultSet));
		}
		
		return attendances;
	}
	
	public AttendanceCounts getAttendancesCount() throws SQLException {
		String sqlQuery = "SELECT "
				+ "(SELECT count(*) FROM analyst an WHERE an.is_active = 1) as TOTAL, "
				+ "(SELECT count(*) FROM analyst an "
				+ "LEFT JOIN attendance att ON an.id = att.analyst_id "
				+ "WHERE att.analyst_id IS NOT NULL  "
				+ "AND date_format(att.time_in, '%Y-%m-%d') = date_format(CURRENT_TIMESTAMP(), '%Y-%m-%d')"
				+ "AND att.remarks = 'ON_LEAVE') as 'ON_LEAVE', "
				+ "(SELECT count(*) FROM analyst an "
				+ "LEFT JOIN attendance att ON an.id = att.analyst_id "
				+ "WHERE att.analyst_id IS NOT NULL  "
				+ "AND date_format(att.time_in, '%Y-%m-%d') = date_format(CURRENT_TIMESTAMP(), '%Y-%m-%d')"
				+ "AND att.remarks = 'REGULAR') as 'LOGIN_TODAY', "
				+ "(SELECT count(*) FROM attendance a "
				+ "JOIN analyst an ON an.id = a.analyst_id "
				+ "JOIN analyst_shift_schedule ass ON an.id = ass.analyst_id " 
				+ "JOIN shift_schedule ss ON ss.id = ass.shift_schedule_id "
				+ "WHERE date_format(a.time_in, '%Y-%m-%d') = date_format(CURRENT_TIMESTAMP(), '%Y-%m-%d') " 
				+ "AND time(a.time_in) <= ss.start_time "
				+ "AND date_format(CURRENT_TIMESTAMP(), '%Y-%m-%d') = ass.from_date) as 'ON_TIME', "
				+ "(SELECT count(*) FROM attendance a "
				+ "JOIN analyst an ON an.id = a.analyst_id " 
				+ "JOIN analyst_shift_schedule ass ON an.id = ass.analyst_id " 
				+ "JOIN shift_schedule ss ON ss.id = ass.shift_schedule_id "
				+ "WHERE date_format(a.time_in, '%Y-%m-%d') = date_format(CURRENT_TIMESTAMP(), '%Y-%m-%d') " 
				+ "AND time(a.time_in) > ss.start_time AND time(a.time_in) <= ss.end_time "
				+ "AND date_format(CURRENT_TIMESTAMP(), '%Y-%m-%d') = ass.from_date) as 'LATE', "
				+ "(SELECT count(*) FROM attendance a "
				+ "JOIN analyst an ON an.id = a.analyst_id " 
				+ "JOIN analyst_shift_schedule ass ON an.id = ass.analyst_id " 
				+ "JOIN shift_schedule ss ON ss.id = ass.shift_schedule_id "
				+ "WHERE date_format(a.time_in, '%Y-%m-%d') = date_format(CURRENT_TIMESTAMP(), '%Y-%m-%d') " 
				+ "AND time(a.time_in) > ss.end_time "
				+ "AND date_format(CURRENT_TIMESTAMP(), '%Y-%m-%d') = ass.from_date) as 'OUT_OF_SCHEDULE'";
		
		Query query = new Query(sqlQuery);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		resultSet.next();
		
		Integer totalActiveAnalysts = resultSet.getInt("TOTAL");
		Integer loginToday = resultSet.getInt("LOGIN_TODAY");
		Integer noLoginToday = totalActiveAnalysts - loginToday;
		Integer onLeave = resultSet.getInt("ON_LEAVE");
		Integer presentOnTime = resultSet.getInt("ON_TIME");
		Integer presentLate = resultSet.getInt("LATE");
		Integer presentOutOfSchedule = resultSet.getInt("OUT_OF_SCHEDULE");
		
		AttendanceCounts attendanceCounts = new AttendanceCounts(
				totalActiveAnalysts, noLoginToday, loginToday,
				onLeave, presentOnTime, presentLate,
				presentOutOfSchedule);
		
		return attendanceCounts;
	}
	
	public List<AttendanceLog> getAttendanceLogs() throws SQLException {
		List<AttendanceLog> attendanceLogs = new ArrayList<>();
		String sqlQuery = "SELECT * FROM "
				+ "((SELECT a.id, concat(an.first_name, ' ' , an.last_name) as 'name', time(time_in) as 'time', 'IN' as 'type' "
				+ "FROM attendance a "
				+ "JOIN analyst an ON an.id = a.analyst_id "
				+ "WHERE date_format(a.time_in, '%Y-%m-%d') = date_format(CURRENT_TIMESTAMP, '%Y-%m-%d') "
				+ "ORDER BY a.time_in DESC) "
				+ "UNION "
				+ "(SELECT a.id, concat(an.first_name, ' ' , an.last_name) as 'name', time(time_out) as 'time', 'OUT' as 'type' "
				+ "FROM attendance a "
				+ "JOIN analyst an ON an.id = a.analyst_id "
				+ "WHERE date_format(a.time_in, '%Y-%m-%d') = date_format(CURRENT_TIMESTAMP, '%Y-%m-%d') "
				+ "AND a.time_out IS NOT NULL "
				+ "ORDER BY a.time_out DESC)) sub "
				+ "ORDER BY sub.time DESC "
				+ "LIMIT 10";
		
		Query query = new Query(sqlQuery);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		
		while (resultSet.next()) {
			String name = resultSet.getString("name");
			LocalTime time = DateTimeUtils.convertTimeToLocalTime(resultSet.getTime("time"));
			String type = resultSet.getString("type");
			
			AttendanceLog attendanceLog = new AttendanceLog(name, time, type);
			attendanceLogs.add(attendanceLog);
		}
		
		return attendanceLogs;
	}
	
	public Integer updateAttendanceStatusById(String status, Integer id) throws SQLException {
		String sql = "UPDATE attendance SET status = ?1 WHERE id = ?2";
		Query query = new Query(sql);
		query.params(status, id);
		
		Integer affectedRows = executeUpdate(query.getQuery());
		return affectedRows;
	}
}
