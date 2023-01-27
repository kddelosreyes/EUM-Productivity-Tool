package com.project.eum.prodtool.view;

import java.sql.SQLException;
import java.util.List;

import com.project.eum.prodtool.model.add.ActivityLog;
import com.project.eum.prodtool.model.add.AttendanceCounts;
import com.project.eum.prodtool.model.add.AttendanceLog;
import com.project.eum.prodtool.service.ActivityService;
import com.project.eum.prodtool.service.AttendanceService;

public class TabDetails_Home {

	private final AttendanceService attendanceService = new AttendanceService();
	private final ActivityService activityService = new ActivityService();
	
	private AttendanceCounts attendanceCounts;
	private List<AttendanceLog> attendanceLogs;
	private List<ActivityLog> activityLogs;
	
	public TabDetails_Home() {
		try {
			attendanceCounts = attendanceService.getAttendancesCount();
			attendanceLogs = attendanceService.getAttendanceLogs();
			activityLogs = activityService.getActivityLogs();
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}
	
	public AttendanceCounts getAttendanceCounts() {
		return attendanceCounts;
	}
	
	public List<AttendanceLog> getAttendanceLogs() {
		return attendanceLogs;
	}
	
	public List<ActivityLog> getActivityLogs() {
		return activityLogs;
	}
	
}
