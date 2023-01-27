package com.project.eum.prodtool.model;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;

public class ActivityFieldMapping extends Entity {

	private String activityName;
	private String fieldName;
	private LocalDateTime createdDate;
	
	public ActivityFieldMapping(Integer id, String activityName, String fieldName,
			LocalDateTime createdDate) {
		super(id);
		this.activityName = activityName;
		this.fieldName = fieldName;
		this.createdDate = createdDate;
	}
	
	@Override
	public Class<?> getEntityClass() {
		return ActivityFieldMapping.class;
	}

	public String getActivityName() {
		return activityName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
}
