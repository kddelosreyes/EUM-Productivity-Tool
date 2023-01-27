package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum ReportTypeColumn implements Column {

	ID,
	NAME,
	QUERY,
	UUID;
	
	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}
	
}
