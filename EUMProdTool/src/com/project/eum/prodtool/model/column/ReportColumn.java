package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum ReportColumn implements Column {
	
	ID,
	NAME,
	REPORT_TYPE_ID,
	FROM_DATE,
	TO_DATE,
	TEAM_ID,
	GENERATION_DATE,
	IS_ARCHIVE,
	UUID;

	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}

}
