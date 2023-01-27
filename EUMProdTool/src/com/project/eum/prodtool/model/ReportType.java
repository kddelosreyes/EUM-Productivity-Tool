package com.project.eum.prodtool.model;

import com.project.eum.prodtool.base.Entity;

public class ReportType extends Entity {

	private String name;
	private String query;
	
	@Override
	public Class<?> getEntityClass() {
		return ReportType.class;
	}

	public String getName() {
		return name;
	}

	public String getQuery() {
		return query;
	}

}
