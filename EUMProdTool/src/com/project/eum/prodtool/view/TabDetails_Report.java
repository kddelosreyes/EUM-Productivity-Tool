package com.project.eum.prodtool.view;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.model.column.ReportColumn;
import com.project.eum.prodtool.model.column.TeamColumn;
import com.project.eum.prodtool.model.field.ReportField;
import com.project.eum.prodtool.service.ReportService;
import com.project.eum.prodtool.service.ReportTypeService;
import com.project.eum.prodtool.service.TeamService;

public class TabDetails_Report {

	private final ReportService reportService = new ReportService();
	private final ReportTypeService reportTypeService = new ReportTypeService();
	private final TeamService teamService = new TeamService();
	
	private List<Entity> reportTypes;
	private List<Entity> teams;
	private List<Entity> reports;
	
	public TabDetails_Report() {
		try {
			reportTypes = reportTypeService.getAll();
			teams = teamService.getEntitiesByColumn(TeamColumn.TYPE, "main");
			reports = reportService.getEntitiesByColumn(ReportColumn.IS_ARCHIVE, 0);
			
			Collections.sort(reports, new Comparator<Entity>() {
				@Override
				public int compare(Entity first, Entity second) {
					LocalDateTime date1 = (LocalDateTime) first.get(ReportField.generationDate);
					LocalDateTime date2 = (LocalDateTime) second.get(ReportField.generationDate);
					
					return date2.compareTo(date1);
				}
			});
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
	}
	
	public List<Entity> getReportTypes() {
		return reportTypes;
	}
	
	public List<Entity> getTeams() {
		return teams;
	}
	
	public List<Entity> getReports() {
		return reports;
	}
	
}
