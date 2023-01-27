package com.project.eum.prodtool.view;

import java.sql.SQLException;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.model.ActivityFieldMapping;
import com.project.eum.prodtool.service.ActivityService;
import com.project.eum.prodtool.service.ActivityTypeService;
import com.project.eum.prodtool.service.FormFieldService;

public class TabDetails_Activity {

	private final ActivityService activityService = new ActivityService();
	private final ActivityTypeService activityTypeService = new ActivityTypeService();
	private final FormFieldService formFieldService = new FormFieldService();
	
	private List<Entity> activities;
	private List<Entity> activityTypes;
	private List<Entity> formFields;
	private List<ActivityFieldMapping> activityFieldMapping;
	
	public TabDetails_Activity() {
		try {
			activities = activityService.getAll();
			activityTypes = activityTypeService.getAll();
			formFields = formFieldService.getAll();
			activityFieldMapping = activityService.getActivityInputMappings();
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
	}

	public List<Entity> getActivities() {
		return activities;
	}

	public List<Entity> getActivityTypes() {
		return activityTypes;
	}
	
	public List<Entity> getFormFields() {
		return formFields;
	}
	
	public List<ActivityFieldMapping> getActivityFieldMapping() {
		return activityFieldMapping;
	}
	
}
