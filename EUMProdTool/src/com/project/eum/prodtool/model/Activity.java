package com.project.eum.prodtool.model;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;

/**
 * @author khdelos
 *
 */
public class Activity extends Entity {

	private String name;
	private Integer activityTypeId;
	private ActivityType activityType;
	private LocalDateTime createdDate;
	
	public Activity(){
		super();
	}
	
	public Activity(Integer id, String uuid) {
		super(id, uuid);
	}
	
	public Activity(Integer id, String name, Integer activityTypeId,
			String uuid) {
		this(id, uuid);
		this.name = name;
		this.activityTypeId = activityTypeId;
	}
	
	@Override
	public Class<?> getEntityClass() {
		return Activity.class;
	}

	public String getName() {
		return name;
	}

	public Integer getActivityTypeId() {
		return activityTypeId;
	}
	
	public ActivityType getActivityType() {
		return activityType;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
}
