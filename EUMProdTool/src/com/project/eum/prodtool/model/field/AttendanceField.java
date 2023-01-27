package com.project.eum.prodtool.model.field;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.EntityField;

public enum AttendanceField implements EntityField {

	id(Integer.class),
	analystId(Integer.class),
	timeIn(LocalDateTime.class),
	timeOut(LocalDateTime.class),
	remarks(String.class),
	createdDate(LocalDateTime.class),
	updatedDate(LocalDateTime.class);
	
	private final Class<?> dataType;
	
	AttendanceField(Class<?> dataType) {
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
