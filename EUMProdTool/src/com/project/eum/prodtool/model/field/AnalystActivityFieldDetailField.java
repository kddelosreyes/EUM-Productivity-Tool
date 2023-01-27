package com.project.eum.prodtool.model.field;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.EntityField;

public enum AnalystActivityFieldDetailField implements EntityField {

	id(Integer.class),
	analystActivityId(Integer.class),
	fieldId(Integer.class),
	value(String.class),
	createdDate(LocalDateTime.class),
	updatedDate(LocalDateTime.class),
	uuid(String.class);
	
	private final Class<?> dataType;
	
	AnalystActivityFieldDetailField(Class<?> dataType) {
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
