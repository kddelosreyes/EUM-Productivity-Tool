package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum ActivityTypeColumn implements Column {

	ID,
	TYPE,
	UUID;
	
	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}
	
}
