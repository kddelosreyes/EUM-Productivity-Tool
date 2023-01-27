package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum TeamColumn implements Column {
	
	ID,
	NAME,
	TYPE,
	CREATED_DATE,
	UPDATED_DATE;

	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}

}
