package com.project.eum.prodtool.model.field;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.EntityField;

public enum FormFieldField implements EntityField {
	
	id(Integer.class),
	name(String.class),
	type(String.class),
	isRequired(Boolean.class),
	createdDate(LocalDateTime.class),
	value(String.class);

	
	private final Class<?> dataType;
	
	FormFieldField(Class<?> dataType) {
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
