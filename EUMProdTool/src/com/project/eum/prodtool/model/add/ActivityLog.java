package com.project.eum.prodtool.model.add;

import java.time.LocalTime;

public class ActivityLog {
	
	private String name;
	private String activity;
	private LocalTime time;
	private String type;
	
	public ActivityLog(String name, String activity, LocalTime time, String type) {
		this.name = name;
		this.activity = activity;
		this.time = time;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getActivity() {
		return activity;
	}

	public LocalTime getTime() {
		return time;
	}

	public String getType() {
		return type;
	}
	
}
