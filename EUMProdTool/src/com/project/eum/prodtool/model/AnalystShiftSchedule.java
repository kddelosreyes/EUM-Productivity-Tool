package com.project.eum.prodtool.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;

public class AnalystShiftSchedule extends Entity {

	private Integer analystId;
	private Integer shiftScheduleId;
	private ShiftSchedule shiftSchedule;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	@Override
	public Class<?> getEntityClass() {
		return AnalystShiftSchedule.class;
	}

	public Integer getAnalystId() {
		return analystId;
	}

	public Integer getShiftScheduleId() {
		return shiftScheduleId;
	}

	public ShiftSchedule getShiftSchedule() {
		return shiftSchedule;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

}
