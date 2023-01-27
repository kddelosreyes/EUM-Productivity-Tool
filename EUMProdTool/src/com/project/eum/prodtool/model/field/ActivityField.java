package com.project.eum.prodtool.model.field;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.EntityField;
import com.project.eum.prodtool.model.ActivityType;

public enum ActivityField implements EntityField {

	ID("id", Integer.class),
	UUID("uuid", String.class),
	NAME("name", String.class),
	ACTIVITY_TYPE_ID("activityTypeId", Integer.class),
	ACTIVITY_TYPE("activityType", ActivityType.class),
	CREATED_DATE("createdDate", LocalDateTime.class);
	
	private final String fieldName;
	private final Class<?> dataType;
	
	ActivityField(String fieldName, Class<?> dataType) {
		this.fieldName = fieldName;
		this.dataType = dataType;
	}
	
    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public Class<?> getDataType() {
        return dataType;
    }
	
}
