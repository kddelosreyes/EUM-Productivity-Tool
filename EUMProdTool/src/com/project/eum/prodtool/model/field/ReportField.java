package com.project.eum.prodtool.model.field;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.project.eum.prodtool.base.EntityField;
import com.project.eum.prodtool.model.ReportType;
import com.project.eum.prodtool.model.Team;

public enum ReportField implements EntityField {

	id(Integer.class),
	name(String.class),
	reportTypeId(Integer.class),
	reportType(ReportType.class),
	fromDate(LocalDate.class),
	toDate(LocalDate.class),
	teamId(Integer.class),
	team(Team.class),
	generationDate(LocalDateTime.class),
	uuid(String.class);
	
	private final Class<?> dataType;
	
	ReportField(Class<?> dataType) {
		this.dataType = dataType;
	}
	
	@Override
	public String getFieldName() {
		return this.toString();
	}

	@Override
	public Class<?> getDataType() {
		return dataType;
	}
	
}
