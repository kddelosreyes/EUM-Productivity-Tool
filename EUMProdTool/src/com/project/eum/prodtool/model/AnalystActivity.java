package com.project.eum.prodtool.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.utils.DateTimeUtils;

/**
 * @author khdelos
 *
 */
public class AnalystActivity extends Entity {

	private Integer analystId;
	private Integer activityId;
	private Activity activity;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String remarks;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	private Long minutes;
	private String timeMinutes;
	
	public AnalystActivity() {
		super();
	}
	
	public AnalystActivity(Integer id, String uuid) {
		super(id, uuid);
	}
	
	public AnalystActivity(Integer id, Integer analystId, Integer activityId,
			LocalDateTime startTime, LocalDateTime endTime, String remarks,
			LocalDateTime createdDate, LocalDateTime updatedDate, String uuid) {
		this(id, uuid);
		this.analystId = analystId;
		this.activityId = activityId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.remarks = remarks;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
	
	@Override
	public Class<?> getEntityClass() {
		return AnalystActivity.class;
	}

	public Integer getAnalystId() {
		return analystId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public Activity getActivity() {
		return activity;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public Long getMinutes() {
		this.minutes = DateTimeUtils.minutesInBetween(startTime, endTime);
		
		return minutes;
	}
	
	public String getTimeMinutes() {
		return LocalTime.MIN.plus( 
			    Duration.ofMinutes(getMinutes())
			).toString();
	}

}
