package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum AnalystActivityColumn implements Column {

	ID,
	ANALYST_ID,
	ACTIVITY_ID,
	START_TIME,
	END_TIME,
	REMARKS,
	CREATED_DATE,
	UPDATED_DATE;
	
	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}
	
}
