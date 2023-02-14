package com.project.eum.prodtool.model.field;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.project.eum.prodtool.base.EntityField;
import com.project.eum.prodtool.model.Analyst;
import com.project.eum.prodtool.model.ShiftSchedule;

public enum AnalystShiftScheduleField implements EntityField {

	id(Integer.class),
	analystId(Integer.class),
	analyst(Analyst.class),
	shiftScheduleId(Integer.class),
	shiftSchedule(ShiftSchedule.class),
	fromDate(LocalDate.class),
	toDate(LocalDate.class),
	restDays(String.class),
	createdDate(LocalDateTime.class),
	updatedDate(LocalDateTime.class);
	
	private final Class<?> dataType;
	
	AnalystShiftScheduleField(Class<?> dataType) {
		this.dataType = dataType;
	}
	
	@Override
	public String getFieldName() {
		return this.toString();
	}

	@Override
	public Class<?> getDataType() {
		return dataType;
	}	
	
}
