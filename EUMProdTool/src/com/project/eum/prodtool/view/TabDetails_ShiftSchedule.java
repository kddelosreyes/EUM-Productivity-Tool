package com.project.eum.prodtool.view;

import java.sql.SQLException;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.model.add.T_AnalystShiftSchedule;
import com.project.eum.prodtool.service.AnalystShiftScheduleService;
import com.project.eum.prodtool.service.ShiftScheduleService;

public class TabDetails_ShiftSchedule {

	private final ShiftScheduleService shiftScheduleService = new ShiftScheduleService();
	private final AnalystShiftScheduleService analystShiftScheduleService = new AnalystShiftScheduleService();
	
	private List<Entity> shiftSchedules;
	private List<T_AnalystShiftSchedule> analystShiftSchedules;
	private List<Entity> analystShiftSchedulesJson;
	
	public TabDetails_ShiftSchedule() {
		try {
			shiftSchedules = shiftScheduleService.getAll();
			analystShiftSchedules = analystShiftScheduleService.getAllShiftSchedulesForCurrentQuarter();
			analystShiftSchedulesJson = analystShiftScheduleService.getAll();
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}
	
	public List<Entity> getShiftSchedules() {
		return shiftSchedules;
	}
	
	public List<T_AnalystShiftSchedule> getAnalystShiftSchedules() {
		return analystShiftSchedules;
	}
	
	public List<Entity> getAnalystShiftSchedulesJson() {
		return analystShiftSchedulesJson;
	}
	
}
