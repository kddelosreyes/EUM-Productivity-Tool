package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum ActivityColumn implements Column {

	ID,
	NAME,
	ACTIVITY_TYPE_ID,
	UUID,
	CREATED_DATE;
	
	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}
	
}
