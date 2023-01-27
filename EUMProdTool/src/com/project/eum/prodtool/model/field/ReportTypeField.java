package com.project.eum.prodtool.model.field;

import com.project.eum.prodtool.base.EntityField;

public enum ReportTypeField implements EntityField {

	id(Integer.class),
	name(String.class),
	query(String.class),
	uuid(String.class);
	
	private final Class<?> dataType;
	
	ReportTypeField(Class<?> dataType) {
		this.dataType = dataType;
	}
	
	@Override
	public String getFieldName() {
		return this.toString();
	}

	@Override
	public Class<?> getDataType() {
		return dataType;
	}	
}
