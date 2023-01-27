package com.project.eum.prodtool.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.base.Service;
import com.project.eum.prodtool.model.ReportType;
import com.project.eum.prodtool.model.column.ReportTypeColumn;
import com.project.eum.prodtool.model.field.ReportTypeField;
import com.project.eum.prodtool.service.constants.Table;

public class ReportTypeService extends Service {

	@Override
	public String getTableName() throws SQLException {
		return Table.REPORT_TYPE.toString();
	}

	@Override
	public Entity getResultSetEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(ReportTypeColumn.ID.getColumnName());
		String name = resultSet.getString(ReportTypeColumn.NAME.getColumnName());
		String query = resultSet.getString(ReportTypeColumn.QUERY.getColumnName());
		String uuid = resultSet.getString(ReportTypeColumn.UUID.getColumnName());
		
		Entity reportType = new ReportType()
				.set(ReportTypeField.id, id)
				.set(ReportTypeField.name, name)
				.set(ReportTypeField.query, query)
				.set(ReportTypeField.uuid, uuid);
		
		return reportType;
	}

}
