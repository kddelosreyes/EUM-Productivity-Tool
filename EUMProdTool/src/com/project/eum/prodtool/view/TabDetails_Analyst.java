package com.project.eum.prodtool.view;

import java.sql.SQLException;
import java.util.List;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.service.AnalystService;

public class TabDetails_Analyst {

	private final AnalystService analystService = new AnalystService();
	
	private List<Entity> analysts;
	
	public TabDetails_Analyst() {
		try {
			analysts = analystService.getAll();
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
	}

	public List<Entity> getAnalysts() {
		return analysts;
	}
	
}
