package com.project.eum.prodtool.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.project.eum.prodtool.base.Entity;

public class ShiftSchedule extends Entity {

	private String name;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	public ShiftSchedule() {
		super();
	}
	
	@Override
	public Class<?> getEntityClass() {
		return ShiftSchedule.class;
	}

	public String getName() {
		return name;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

}
