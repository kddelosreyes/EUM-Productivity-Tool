package com.project.eum.prodtool.model.field;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.EntityField;

public enum AnalystLoginField implements EntityField {

	ID("id", Integer.class),
	UUID("uuid", String.class),
	ANALYST_ID("analystId", Integer.class),
	USERNAME("username", String.class),
	PASSWORD("password", String.class),
	SALT("salt", String.class),
	CREATED_DATE("createdDate", LocalDateTime.class),
	UPDATED_DATE("updatedDate", LocalDateTime.class),
	IS_LOCKED("isLocked", Boolean.class),
	ATTEMPT("attempt", Integer.class),
	SECURITY_QUESTION("securityQuestion", Integer.class),
	SECURITY_ANSWER("securityAnswer", String.class);
	
	private final String fieldName;
	private final Class<?> dataType;
	
	AnalystLoginField(String fieldName, Class<?> dataType) {
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
