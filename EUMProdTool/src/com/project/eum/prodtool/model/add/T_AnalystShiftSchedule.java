package com.project.eum.prodtool.model.add;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class T_AnalystShiftSchedule {

	private Integer analystId;
	private String name;
	private String role;
	private String shiftName;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDate startDate;
	private Boolean isNightShift;
	private Integer analystShiftScheduleId;
	
	private String shift;
	
	public T_AnalystShiftSchedule(Integer analystId, String name, String role,
			String shiftName, LocalTime startTime, LocalTime endTime,
			LocalDate startDate, Boolean isNightShift, Integer analystShiftScheduleId) {
		super();
		this.analystId = analystId;
		this.name = name;
		this.role = role;
		this.shiftName = shiftName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startDate = startDate;
		this.analystShiftScheduleId = analystShiftScheduleId;
	}

	public Integer getAnalystId() {
		return analystId;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}
	
	public String getShiftName() {
		return shiftName;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public LocalDate getStartDate() {
		return startDate;
	}
	
	public Boolean getIsNightShift() {
		return isNightShift;
	}
	
	public Integer getAnalystShiftScheduleId() {
		return analystShiftScheduleId;
	}
	
	public String getShift() {
		shift = shiftName + " (" + startTime.format(DateTimeFormatter.ofPattern("hh:mm a")) + "-" 
				+ endTime.format(DateTimeFormatter.ofPattern("hh:mm a"))+ ")";
		
		return shift;
	}	
	
}
