package com.project.eum.prodtool.model.field;

import com.project.eum.prodtool.base.EntityField;

/**
 * @author khdelos
 *
 */
public enum ActivityTypeField implements EntityField {

	ID("id", Integer.class),
	UUID("uuid", String.class),
	TYPE("type", String.class);
	
	private final String fieldName;
	private final Class<?> dataType;
	
	ActivityTypeField(String fieldName, Class<?> dataType) {
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
