package com.project.eum.prodtool.model;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;

public class AnalystActivityFieldDetail extends Entity {

	private Integer analystActivityId;
	private Integer fieldId;
	private String value;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	public AnalystActivityFieldDetail() {
		super();
	}
	
	@Override
	public Class<?> getEntityClass() {
		return AnalystActivityFieldDetail.class;
	}

	public Integer getAnalystActivityId() {
		return analystActivityId;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public String getValue() {
		return value;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

}
