package com.project.eum.prodtool.model;

import com.project.eum.prodtool.base.Entity;

/**
 * @author khdelos
 *
 */
public class ActivityType extends Entity {

	private String type;
	
	public ActivityType() {
		super();
	}
	
	public ActivityType(Integer id, String uuid) {
		super(id, uuid);
	}
	
	public ActivityType(Integer id, String type, String uuid) {
		this(id, uuid);
		this.type = type;
	}
	
	@Override
	public Class<?> getEntityClass() {
		return ActivityType.class;
	}

	public String getType() {
		return type;
	}
	
}
