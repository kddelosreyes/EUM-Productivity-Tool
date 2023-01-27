package com.project.eum.prodtool.model.field;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.EntityField;
import com.project.eum.prodtool.model.Activity;

public enum AnalystActivityField implements EntityField {
	
	id(Integer.class),
	analystId(Integer.class),
	activityId(Integer.class),
	activity(Activity.class),
	startTime(LocalDateTime.class),
	endTime(LocalDateTime.class),
	remarks(String.class),
	createdDate(LocalDateTime.class),
	updatedDate(LocalDateTime.class);

	private final Class<?> dataType;
	
	AnalystActivityField(Class<?> dataType) {
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
