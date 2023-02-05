package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum ShiftScheduleColumn implements Column {
	
	ID,
	NAME,
	START_TIME,
	END_TIME,
	IS_NIGHT_SHIFT,
	CREATED_DATE,
	UPDATED_DATE,
	UUID;

	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}

}
