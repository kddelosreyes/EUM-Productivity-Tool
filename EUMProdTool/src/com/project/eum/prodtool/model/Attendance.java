package com.project.eum.prodtool.model;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;

public class Attendance extends Entity {

	private Integer analystId;
	private LocalDateTime timeIn;
	private LocalDateTime timeOut;
	private String remarks;
	private String status;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	public Attendance() {
		super();
	}
	
	@Override
	public Class<?> getEntityClass() {
		return Attendance.class;
	}

	public Integer getAnalystId() {
		return analystId;
	}

	public LocalDateTime getTimeIn() {
		return timeIn;
	}

	public LocalDateTime getTimeOut() {
		return timeOut;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public String getStatus() {
		return status;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

}
