package com.project.eum.prodtool.model.field;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.project.eum.prodtool.base.EntityField;

public enum ShiftScheduleField implements EntityField {

	id(Integer.class),
	name(String.class),
	startTime(LocalTime.class),
	endTime(LocalTime.class),
	createdDate(LocalDateTime.class),
	updatedDate(LocalDateTime.class),
	uuid(String.class);

	private final Class<?> dataType;
	
	ShiftScheduleField(Class<?> dataType) {
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
