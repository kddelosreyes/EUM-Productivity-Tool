package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.Report;
import com.project.eum.prodtool.model.ReportType;
import com.project.eum.prodtool.model.Team;
import com.project.eum.prodtool.model.column.ReportColumn;
import com.project.eum.prodtool.model.field.ReportField;
import com.project.eum.prodtool.service.constants.Table;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class ReportService extends Service {

	private final ReportTypeService reportTypeService = new ReportTypeService();
	private final TeamService teamService = new TeamService();
	
	@Override
	public String getTableName() throws SQLException {
		return Table.REPORT.toString();
	}

	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(ReportColumn.ID.getColumnName());
		String name = resultSet.getString(ReportColumn.NAME.getColumnName());
		Integer reportTypeId = resultSet.getInt(ReportColumn.REPORT_TYPE_ID.getColumnName());
		LocalDate fromDate = DateTimeUtils.convertDateToLocalDate(resultSet.getDate(ReportColumn.FROM_DATE.getColumnName()));
		LocalDate toDate = DateTimeUtils.convertDateToLocalDate(resultSet.getDate(ReportColumn.TO_DATE.getColumnName()));
		Integer teamId = resultSet.getInt(ReportColumn.TEAM_ID.getColumnName());
		LocalDateTime generationDate = DateTimeUtils.convertTimestampToLocalDateTime(resultSet.getTimestamp(ReportColumn.GENERATION_DATE.getColumnName()));
		String uuid = resultSet.getString(ReportColumn.UUID.getColumnName());
		
		ReportType reportType = (ReportType) reportTypeService.getEntityById(reportTypeId);
		Team team = (Team) teamService.getEntityById(teamId);
		
		Entity entity = new Report()
				.set(ReportField.id, id)
				.set(ReportField.name, name)
				.set(ReportField.reportTypeId, reportTypeId)
				.set(ReportField.fromDate, fromDate)
				.set(ReportField.toDate, toDate)
				.set(ReportField.teamId, teamId)
				.set(ReportField.generationDate, generationDate)
				.set(ReportField.uuid, uuid)
				.set(ReportField.reportType, reportType)
				.set(ReportField.team, team);
		
		return entity;
	}
	
	public Integer insertReportDetails(String reportName, Integer reportTypeId, String startDate,
			String endDate, Integer teamId) throws SQLException {
		
		String sqlQuery = "INSERT INTO report(name, report_type_id, from_date, to_date, team_id, uuid) "
				+ "VALUES(?1, ?2, ?3, ?4, ?5, uuid())";
		
		Query query = new Query(sqlQuery);
		query.params(reportName, reportTypeId, startDate, endDate, teamId);
		
		return executeUpdate(query.getQuery());
	}
	
	public Integer updateReportToArchiveById(String uuid) throws SQLException {
		String sqlQuery = "UPDATE report SET is_archive = 1 WHERE uuid = ?1";
		
		Query query = new Query(sqlQuery);
		query.params(uuid);
		
		return executeUpdate(query.getQuery());
	}

}
