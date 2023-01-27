package com.project.eum.prodtool.model.add;

import java.time.LocalTime;

public class AttendanceLog {

	private String name;
	private LocalTime time;
	private String type;
	
	public AttendanceLog(String name, LocalTime time, String type) {
		this.name = name;
		this.time = time;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public LocalTime getTime() {
		return time;
	}

	public String getType() {
		return type;
	}	
	
}
