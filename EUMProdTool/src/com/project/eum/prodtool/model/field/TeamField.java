package com.project.eum.prodtool.model.field;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.EntityField;

public enum TeamField implements EntityField {

	id(Integer.class),
	name(String.class),
	type(String.class),
	createdDate(LocalDateTime.class),
	updatedDate(LocalDateTime.class);
	
	private final Class<?> dataType;
	
	TeamField(Class<?> dataType) {
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
