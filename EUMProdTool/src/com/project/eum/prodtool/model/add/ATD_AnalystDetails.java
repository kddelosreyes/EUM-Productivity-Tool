package com.project.eum.prodtool.model.add;

import java.time.LocalTime;

import com.project.eum.prodtool.model.Analyst;
import com.project.eum.prodtool.model.Attendance;
import com.project.eum.prodtool.model.ShiftSchedule;
import com.project.eum.prodtool.utils.DateTimeUtils;

public class ATD_AnalystDetails {

	private Analyst analyst;
	private ShiftSchedule shiftSchedule;
	private Attendance attendance;
	private String remarks;
	
	public ATD_AnalystDetails(Analyst analyst, ShiftSchedule shiftSchedule, Attendance attendance) {
		this.analyst = analyst;
		this.shiftSchedule = shiftSchedule;
		this.attendance = attendance;
		
		if (shiftSchedule == null) {
			remarks = "No Schedule";
		} else {
			if (attendance == null) {
				remarks = "No Login";
			} else {
				LocalTime attendanceTimeIn = attendance.getTimeIn().toLocalTime();
				if (DateTimeUtils.isInBetweenExclusive(attendanceTimeIn, shiftSchedule.getStartTime(), shiftSchedule.getEndTime())) {
					remarks = "Late";
				} else if (DateTimeUtils.isTimeOnOrBefore(attendanceTimeIn, shiftSchedule.getStartTime())) {
					remarks = "Present";
				} else {
					remarks = "Out of Schedule";
				}
			}
		}
	}

	public Analyst getAnalyst() {
		return analyst;
	}

	public ShiftSchedule getShiftSchedule() {
		return shiftSchedule;
	}

	public Attendance getAttendance() {
		return attendance;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
}
