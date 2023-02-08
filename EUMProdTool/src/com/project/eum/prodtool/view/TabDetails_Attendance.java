package com.project.eum.prodtool.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.model.Analyst;
import com.project.eum.prodtool.model.AnalystShiftSchedule;
import com.project.eum.prodtool.model.Attendance;
import com.project.eum.prodtool.model.ShiftSchedule;
import com.project.eum.prodtool.model.add.ATD_AnalystDetails;
import com.project.eum.prodtool.model.column.AnalystColumn;
import com.project.eum.prodtool.model.field.AnalystField;
import com.project.eum.prodtool.model.field.AnalystShiftScheduleField;
import com.project.eum.prodtool.model.field.AttendanceField;
import com.project.eum.prodtool.service.AnalystService;
import com.project.eum.prodtool.service.AnalystShiftScheduleService;
import com.project.eum.prodtool.service.AttendanceService;

public class TabDetails_Attendance {

	private final AnalystService analystService = new AnalystService();
	private final AttendanceService attendanceService = new AttendanceService();
	private final AnalystShiftScheduleService analystShiftScheduleService = new AnalystShiftScheduleService();
	
	private List<ATD_AnalystDetails> analystDetails;
	
	public TabDetails_Attendance() {
		analystDetails = new ArrayList<>();
		
		try {
			List<Entity> analysts = analystService.getEntitiesByColumn(AnalystColumn.IS_ACTIVE, 1);
			List<Entity> attendances = attendanceService.getAttendancesForToday();
			List<Entity> shiftSchedules = analystShiftScheduleService.getCurrentShiftSchedules();
			
			for (Entity analyst : analysts) {
				Analyst analystEntity = (Analyst) analyst;
				Integer analystId = (Integer) analystEntity.get(AnalystField.ID);
				Attendance attendance = (Attendance) attendances.stream()
						.filter(a -> a.get(AttendanceField.analystId).equals(analystId))
						.findFirst()
						.orElse(null);
				AnalystShiftSchedule analystShiftSchedule = (AnalystShiftSchedule) shiftSchedules.stream()
						.filter(s -> s.get(AnalystShiftScheduleField.analystId).equals(analystId))
						.findFirst()
						.orElse(null);
				
				ShiftSchedule shiftSchedule = null;
				
				if (analystShiftSchedule != null) {
					shiftSchedule = (ShiftSchedule) analystShiftSchedule.get(AnalystShiftScheduleField.shiftSchedule);
				}
				
				analystDetails.add(
						new ATD_AnalystDetails(analystEntity, shiftSchedule, attendance)
						);
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}
	
	public List<ATD_AnalystDetails> getAnalystDetails() {
		return analystDetails;
	}
	
}
