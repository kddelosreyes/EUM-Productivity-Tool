package com.project.eum.prodtool.model;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;

public class Team extends Entity {

	private String name;
	private String type;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	@Override
	public Class<?> getEntityClass() {
		return Team.class;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

}
