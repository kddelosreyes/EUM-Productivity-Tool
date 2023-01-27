package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum AnalystActivityFieldDetailColumn implements Column {

	ID,
	ANALYST_ACTIVITY_ID,
	FIELD_ID,
	VALUE,
	CREATED_DATE,
	UPDATED_DATE,
	UUID;
	
	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}
	
}
