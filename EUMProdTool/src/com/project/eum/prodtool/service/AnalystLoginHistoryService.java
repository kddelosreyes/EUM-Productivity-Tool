package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.service.constants.Table;

public class AnalystLoginHistoryService extends Service {

	@Override
	public String getTableName() {
		return Table.ANALYST_LOGIN_HISTORY.toString();
	}
	
	@Override
	public Entity getResultSetEntity(ResultSet entity) throws SQLException {
		return null;
	}
	
	public Integer insertNewAnalystLoginHistory(Integer analystLoginId) throws SQLException {
		Query query = new Query("INSERT INTO analyst_login_history(analyst_login_id, uuid) "
				+ "VALUES(?1, uuid())");
		query.params(analystLoginId);
		
		int affectedRows = executeUpdate(query.getQuery());
		return affectedRows;
	}
	
}
