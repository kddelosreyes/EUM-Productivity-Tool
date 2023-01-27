package com.project.eum.prodtool.model.add;

public class AttendanceCounts {

	private Integer totalActiveAnalysts;
	private Integer noLoginToday;
	private Integer loginToday;
	private Integer onLeave;
	private Integer presentOnTime;
	private Integer presentLate;
	private Integer presentOutOfSchedule;
	
	public AttendanceCounts(Integer totalActiveAnalysts, Integer noLoginToday, Integer loginToday,
			Integer onLeave, Integer presentOnTime, Integer presentLate,
			Integer presentOutOfSchedule) {
		this.totalActiveAnalysts = totalActiveAnalysts;
		this.noLoginToday = noLoginToday;
		this.loginToday = loginToday;
		this.onLeave = onLeave;
		this.presentOnTime = presentOnTime;
		this.presentLate = presentLate;
		this.presentOutOfSchedule = presentOutOfSchedule;
	}

	public Integer getTotalActiveAnalysts() {
		return totalActiveAnalysts;
	}

	public Integer getNoLoginToday() {
		return noLoginToday;
	}

	public Integer getLoginToday() {
		return loginToday;
	}
	
	public Integer getOnLeave() {
		return onLeave;
	}

	public Integer getPresentOnTime() {
		return presentOnTime;
	}

	public Integer getPresentLate() {
		return presentLate;
	}

	public Integer getPresentOutOfSchedule() {
		return presentOutOfSchedule;
	}
	
}
