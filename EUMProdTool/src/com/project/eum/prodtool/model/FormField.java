package com.project.eum.prodtool.model;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;

public class FormField extends Entity {

	private String name;
	private String type;
	private Boolean isRequired;
	private LocalDateTime createdDate;
	private String value;
	
	public FormField() {
		super();
	}
	
	public FormField(Integer id, String uuid) {
		super(id, uuid);
	}
	
	public FormField(Integer id, String name, String type,
			LocalDateTime createdDate, String uuid) {
		super(id, uuid);
		this.name = name;
		this.type = type;
		this.createdDate = createdDate;
	}
	
	@Override
	public Class<?> getEntityClass() {
		return FormField.class;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
	public Boolean getIsRequired() {
		return isRequired;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	public String getValue() {
		return value;
	}

}
