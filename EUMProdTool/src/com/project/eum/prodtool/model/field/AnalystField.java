package com.project.eum.prodtool.model.field;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.EntityField;

/**
 * @author khdelos
 *
 */
public enum AnalystField implements EntityField {

	ID("id", Integer.class),
	UUID("uuid", String.class),
	FIRST_NAME("firstName", String.class),
	MIDDLE_NAME("middleName", String.class),
	LAST_NAME("lastName", String.class),
	ROLE("role", String.class),
	IS_ACTIVE("isActive", Boolean.class),
	CREATED_DATE("createdDate", LocalDateTime.class),
	UPDATED_DATE("updatedDate", LocalDateTime.class);
	
	private final String fieldName;
	private final Class<?> dataType;
	
	AnalystField(String fieldName, Class<?> dataType) {
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
