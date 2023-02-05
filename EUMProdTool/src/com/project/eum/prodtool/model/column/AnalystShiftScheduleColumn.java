package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum AnalystShiftScheduleColumn implements Column {
	
	ID,
	ANALYST_ID,
	SHIFT_SCHEDULE_ID,
	FROM_DATE,
	TO_DATE,
	REST_DAYS,
	CREATED_DATE,
	UPDATED_DATE;

	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}

}
