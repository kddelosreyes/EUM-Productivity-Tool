package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum FormFieldColumn implements Column {

	ID,
	NAME,
	TYPE,
	IS_REQUIRED,
	CREATED_DATE;
	
	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}
	
}
