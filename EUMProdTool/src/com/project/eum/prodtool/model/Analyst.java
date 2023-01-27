package com.project.eum.prodtool.model;

import java.time.LocalDateTime;

import com.project.eum.prodtool.base.Entity;

/**
 * @author khdelos
 *
 */
public class Analyst extends Entity {

	private String firstName;
	private String middleName;
	private String lastName;
	private String role;
	private Boolean isActive;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	public Analyst() {
		super();
	}
	
	public Analyst(Integer id, String uuid) {
		super(id, uuid);
	}

	public Analyst(Integer id, String firstName, String middleName,
			String lastName, String role, Boolean isActive,
			LocalDateTime createdDate, LocalDateTime updatedDate, String uuid) {
		this(id, uuid);
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.role = role;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
	
	@Override
	public Class<?> getEntityClass() {
		return Analyst.class;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getRole() {
		return role;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
	
}
