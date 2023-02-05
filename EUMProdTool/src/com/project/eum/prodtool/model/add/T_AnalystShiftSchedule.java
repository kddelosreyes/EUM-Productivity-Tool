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
	private LocalDate endDate;
	private String restDays;
	private Boolean isNightShift;
	
	private String shift;
	private String restDayString;
	
	public T_AnalystShiftSchedule(Integer analystId, String name, String role,
			String shiftName, LocalTime startTime, LocalTime endTime,
			LocalDate startDate, LocalDate endDate, String restDays,
			Boolean isNightShift) {
		super();
		this.analystId = analystId;
		this.name = name;
		this.role = role;
		this.shiftName = shiftName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.restDays = restDays;
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

	public LocalDate getEndDate() {
		return endDate;
	}

	public String getRestDays() {
		return restDays;
	}
	
	public Boolean getIsNightShift() {
		return isNightShift;
	}
	
	public String getShift() {
		shift = shiftName + " (" + startTime.format(DateTimeFormatter.ofPattern("hh:mm a")) + "-" 
				+ endTime.format(DateTimeFormatter.ofPattern("hh:mm a"))+ ")";
		
		return shift;
	}
	
	public String getRestDayString() {
		restDayString = restDays == null ? "" : restDays;
		
		restDays = restDayString.replaceAll("Su", "Sunday")
						.replaceAll("M", "Monday")
						.replaceAll("Tu", "Tuesday")
						.replaceAll("W", "Wednesday")
						.replaceAll("Th", "Thursday")
						.replaceAll("F", "Friday")
						.replaceAll("Sa", "Saturday");
		
		return restDays;
	}
	
	
	
}
