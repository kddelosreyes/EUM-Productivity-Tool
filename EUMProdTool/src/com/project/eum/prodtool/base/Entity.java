package com.project.eum.prodtool.base;

import java.lang.reflect.Field;

import com.google.gson.GsonBuilder;

/**
 * @author khdelos
 *
 */
public abstract class Entity {

	private Integer id;
	private String uuid;
	
	public Entity() {
		super();
	}

	public Entity(Integer id) {
		this.id = id;
	}
	
	public Entity(Integer id, String uuid) {
		this(id);
		this.uuid = uuid;
	}
	
	public abstract Class<?> getEntityClass();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Entity set(EntityField entityField, Object value) {
        String variableName = entityField.getFieldName();

        try {
            Field field = (isIdentifier(entityField) ? getEntityClass().getSuperclass() : getEntityClass()).getDeclaredField(variableName);
            field.setAccessible(true);
            field.set(this, value);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exc) {
            exc.printStackTrace();
        }

        return this;
    }
	
	public Entity set(String entityField, Object value) {
        String variableName = entityField;

        try {
            Field field = (isIdentifier(entityField) ? getEntityClass().getSuperclass() : getEntityClass()).getDeclaredField(variableName);
            field.setAccessible(true);
            field.set(this, value);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exc) {
            exc.printStackTrace();
        }

        return this;
    }
	
	public Object get(EntityField entityField) {
        String variableName = entityField.getFieldName();

        try {
            Field field = (isIdentifier(entityField) ? getEntityClass().getSuperclass() : getEntityClass()).getDeclaredField(variableName);
            field.setAccessible(true);
            return field.get(this);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exc) {
            exc.printStackTrace();
        }

        return null;
    }
	
	private boolean isIdentifier(EntityField entityField) {
        return entityField.getFieldName().equals("id") ||
        		entityField.getFieldName().equals("displayValue") ||
        		entityField.getFieldName().equals("uuid");
    }
	
	private boolean isIdentifier(String entityField) {
        return entityField.equals("id") ||
        		entityField.equals("displayValue") ||
        		entityField.equals("uuid");
    }
	
	@Override
	public String toString() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
	
}
