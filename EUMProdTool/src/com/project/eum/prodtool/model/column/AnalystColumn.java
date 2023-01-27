package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum AnalystColumn implements Column {
	
	ID,
	FIRST_NAME,
	MIDDLE_NAME,
	LAST_NAME,
	ROLE,
	IS_ACTIVE,
	CREATED_DATE,
	UPDATED_DATE,
	UUID;

	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}

}
