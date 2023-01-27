package com.project.eum.prodtool.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;

public class Report extends Entity {

	private String name;
	private Integer reportTypeId;
	private ReportType reportType;
	private LocalDate fromDate;
	private LocalDate toDate;
	private Integer teamId;
	private Team team;
	private LocalDateTime generationDate;
	
	@Override
	public Class<?> getEntityClass() {
		return Report.class;
	}

	public String getName() {
		return name;
	}

	public Integer getReportTypeId() {
		return reportTypeId;
	}

	public ReportType getReportType() {
		return reportType;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public Team getTeam() {
		return team;
	}

	public LocalDateTime getGenerationDate() {
		return generationDate;
	}
	
}
