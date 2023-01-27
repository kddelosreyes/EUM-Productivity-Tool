package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.AnalystShiftSchedule;
import com.project.eum.prodtool.model.ShiftSchedule;
import com.project.eum.prodtool.model.column.AnalystShiftScheduleColumn;
import com.project.eum.prodtool.model.field.AnalystShiftScheduleField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class AnalystShiftScheduleService extends Service {

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
		LocalDate toDate = DateTimeUtils.convertDateToLocalDate(resultSet.getDate(AnalystShiftScheduleColumn.TO_DATE.getColumnName()));
		LocalDateTime createdDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AnalystShiftScheduleColumn.CREATED_DATE.getColumnName()));
		LocalDateTime updatedDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(AnalystShiftScheduleColumn.UPDATED_DATE.getColumnName()));
		
		ShiftSchedule shiftSchedule = (ShiftSchedule) shiftScheduleService.getEntityById(shiftScheduleId);
		
		Entity entity = new AnalystShiftSchedule()
				.set(AnalystShiftScheduleField.id, id)
				.set(AnalystShiftScheduleField.analystId, analystId)
				.set(AnalystShiftScheduleField.shiftScheduleId, shiftScheduleId)
				.set(AnalystShiftScheduleField.shiftSchedule, shiftSchedule)
				.set(AnalystShiftScheduleField.fromDate, fromDate)
				.set(AnalystShiftScheduleField.toDate, toDate)
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

}
