package com.project.eum.prodtool.model.column;

import com.project.eum.prodtool.base.Column;

public enum AnalystLoginColumn implements Column {

	ID,
	ANALYST_ID,
	USERNAME,
	PASSWORD,
	SALT,
	CREATED_DATE,
	UPDATED_DATE,
	IS_LOCKED,
	ATTEMPT,
	SECURITY_QUESTION,
	SECURITY_ANSWER,
	UUID;
	
	@Override
	public String getColumnName() {
		return this.toString().toLowerCase();
	}
	
}
