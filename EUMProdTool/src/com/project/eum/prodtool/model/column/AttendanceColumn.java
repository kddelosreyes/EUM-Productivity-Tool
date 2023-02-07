package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum AttendanceColumn implements Column {

	ID,
	ANALYST_ID,
	TIME_IN,
	TIME_OUT,
	REMARKS,
	STATUS,
	CREATED_DATE,
	UPDATED_DATE;
	
	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}
	
}
