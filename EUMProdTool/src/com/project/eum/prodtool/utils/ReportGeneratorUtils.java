package com.project.eum.prodtool.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.model.Analyst;
import com.project.eum.prodtool.model.AnalystActivity;
import com.project.eum.prodtool.model.Attendance;
import com.project.eum.prodtool.model.Report;
import com.project.eum.prodtool.model.ReportType;
import com.project.eum.prodtool.model.ShiftSchedule;
import com.project.eum.prodtool.model.add.TimeSummary;
import com.project.eum.prodtool.model.field.ReportField;
import com.project.eum.prodtool.model.field.ReportTypeField;
import com.project.eum.prodtool.service.AnalystActivityService;
import com.project.eum.prodtool.service.AnalystService;
import com.project.eum.prodtool.service.AttendanceService;
import com.project.eum.prodtool.service.ReportService;
import com.project.eum.prodtool.service.ShiftScheduleService;

public class ReportGeneratorUtils {
	
	private static final String REPORT_ATTENDANCE = "Attendance";
	private static final String REPORT_PRODUCTIVITY = "Productivity";
	private static final String REPORT_ACTIVITY = "Activity";
	private static final String REPORT_LEAVES = "Leaves";

	public static void generateReport(HttpServletRequest request, HttpServletResponse response, Report report) {
		ReportType reportType = (ReportType) report.get(ReportField.reportType);
		String reportTypeName = (String) reportType.get(ReportTypeField.name);
		
		String queryJson = (String) reportType.get(ReportTypeField.query);
		
		JsonObject jsonObject = new JsonParser().parse(queryJson).getAsJsonObject();
		JsonArray jsonArrayQuery = jsonObject.getAsJsonArray("queries");
		
		List<ReportQuery> reportQueries = new ArrayList<>();
		for (JsonElement jsonElement : jsonArrayQuery) {
		    JsonObject queryObject = jsonElement.getAsJsonObject();
		    
		    Integer id = queryObject.get("id").getAsInt();
		    String query = queryObject.get("query").getAsString();
		    
		    reportQueries.add(new ReportQuery(id, query));
		}
		
		switch (reportTypeName) {
			case REPORT_ATTENDANCE:
				generateAttendanceReport(request, response, report, reportQueries);
				break;
			case REPORT_PRODUCTIVITY:
				generateProductivityReport(request, response, report, reportQueries);
				break;
			case REPORT_ACTIVITY:
				break;
			case REPORT_LEAVES:
				break;
		}
	}
	
	private static void generateAttendanceReport(HttpServletRequest request, HttpServletResponse response, Report report, List<ReportQuery> reportQueries) {
		try {
			ReportService reportService = new ReportService();
			AnalystService analystService = new AnalystService();
			AttendanceService attendanceService = new AttendanceService();
			ShiftScheduleService shiftScheduleService = new ShiftScheduleService();
			
			String analystsQuery = reportQueries.get(0).getQuery();
			ResultSet analystResult = reportService.getDataFromQuery(analystsQuery, report.getTeamId());
			
			List<Entity> activeAnalysts = new ArrayList<>();
			while (analystResult.next()) {
				activeAnalysts.add(analystService.getResultSetEntity(analystResult));
			}
			System.out.println(activeAnalysts);
			
			String attendanceQuery = reportQueries.get(1).getQuery();
			ResultSet attendanceResult = reportService.getDataFromQuery(
					attendanceQuery,
					DateTimeUtils.toSqlDateString(report.getFromDate()),
					DateTimeUtils.toSqlDateString(report.getToDate()),
					report.getTeamId());
			
			List<Entity> attendances = new ArrayList<>();
			while (attendanceResult.next()) {
				attendances.add(attendanceService.getResultSetEntity(attendanceResult));
			}
			System.out.println(attendances);
			
			Map<Analyst, Map<LocalDate, String>> attendanceMap = new HashMap<>();
			
			LocalDate startDate = report.getFromDate(), endDate = report.getToDate();
			for (Entity analystEntity : activeAnalysts) {
				Analyst analyst = (Analyst) analystEntity;

				ShiftSchedule shiftSchedule = (ShiftSchedule) shiftScheduleService.getShiftScheduleByAnalystId(analyst.getId());
				
				List<Entity> analystAttendance = attendances.stream()
						.filter(attendance -> ((Attendance) attendance).getAnalystId() == analyst.getId())
						.collect(Collectors.toList());
				
				Map<LocalDate, String> attendanceRemarks = new HashMap<>();
				for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1)) {
					final LocalDate currentDate = date;
					if (shiftSchedule == null) {
						attendanceRemarks.put(currentDate, "NS");
					} else {
						Entity attendanceEntity = analystAttendance.stream()
								.filter(att -> ((Attendance) att).getCreatedDate().toLocalDate().equals(currentDate))
								.findAny()
								.orElse(null);
						
						Attendance attendance = (Attendance) attendanceEntity;
						
						if (DateTimeUtils.isWeekend(date)) {
							attendanceRemarks.put(currentDate, "WOFF");
						} else {
							if (attendance == null) {
								attendanceRemarks.put(currentDate, "A");
							} else {
								LocalTime timeIn = attendance.getTimeIn().toLocalTime();
								LocalTime timeOut = attendance.getTimeOut() == null ? null : attendance.getTimeOut().toLocalTime();
								
								LocalTime shiftStart = shiftSchedule.getStartTime();
								LocalTime shiftEnd = shiftSchedule.getEndTime();
								
								StringBuilder remarks = new StringBuilder("P");
								
								if (timeIn.isBefore(shiftStart) || timeIn.equals(shiftStart)) {
									// Punctual
								} else if (timeIn.isAfter(shiftStart) && (timeIn.isBefore(shiftEnd) || timeIn.equals(shiftEnd))) {
									remarks.append(" (L)");
								} else if (timeIn.isAfter(shiftEnd)) {
									remarks.append(" (OOS)");
								} else {
									remarks.append(" (-)");
								}
								
								remarks.append("\n");
								remarks.append(DateTimeUtils.formatTime(timeIn) + "-" + DateTimeUtils.formatTime(timeOut));
								
								attendanceRemarks.put(currentDate, remarks.toString());
							}
						}
					}
				}
				
				attendanceMap.put(analyst, attendanceRemarks);
			}
			
			Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
			
			// Create row for title
			List<String> dates = new ArrayList<>();
			for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1)) {
				dates.add(date.toString());
			}
			data.put(0, createRow("", "Name", dates));
			
			// Create rows for the contents
			int rowNumber = 1;
			for (Entity analystEntity : activeAnalysts) {
				Analyst analyst = (Analyst) analystEntity;
				
				Map<LocalDate, String> attendance = attendanceMap.get(analyst);
				List<String> remarks = new ArrayList<>();
				
				for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1)) {
					final LocalDate currentDate = date;
					remarks.add(attendance.get(currentDate));
				}
				String fullName = analyst.getFirstName() + " " + analyst.getLastName();
				data.put(rowNumber, createRow(rowNumber, fullName, remarks));
				rowNumber++;
			}
			
			generateExcelFile(request, response, report, data);
		} catch (SQLException | IOException exc) {
			exc.printStackTrace();
		}
	}
	
	private static void generateProductivityReport(HttpServletRequest request, HttpServletResponse response, Report report, List<ReportQuery> reportQueries) {
		try {
			ReportService reportService = new ReportService();
			AnalystService analystService = new AnalystService();
			AnalystActivityService analystActivityService = new AnalystActivityService();
			
			String analystsQuery = reportQueries.get(0).getQuery();
			ResultSet analystResult = reportService.getDataFromQuery(analystsQuery, report.getTeamId());
			
			List<Entity> activeAnalysts = new ArrayList<>();
			while (analystResult.next()) {
				activeAnalysts.add(analystService.getResultSetEntity(analystResult));
			}
			System.out.println(activeAnalysts);
			
			String analystActivityQuery = reportQueries.get(1).getQuery();
			ResultSet attendanceResult = reportService.getDataFromQuery(
					analystActivityQuery,
					DateTimeUtils.toSqlDateString(report.getFromDate()),
					DateTimeUtils.toSqlDateString(report.getToDate()),
					report.getTeamId());
			
			List<Entity> activities = new ArrayList<>();
			while (attendanceResult.next()) {
				activities.add(analystActivityService.getResultSetEntity(attendanceResult));
			}
			System.out.println(activities);
			
			Map<Analyst, Map<LocalDate, String>> productivityMap = new HashMap<>();
			
			LocalDate startDate = report.getFromDate(), endDate = report.getToDate();
			for (Entity analystEntity : activeAnalysts) {
				Analyst analyst = (Analyst) analystEntity;
				
				List<Entity> analystActivities = activities.stream()
						.filter(activity -> ((AnalystActivity) activity).getAnalystId() == analyst.getId())
						.collect(Collectors.toList());
				
				Map<LocalDate, String> productivityRemarks = new HashMap<>();
				for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1)) {
					final LocalDate currentDate = date;
					
					List<Entity> todaysActivities = analystActivities.stream()
							.filter(a -> ((AnalystActivity) a).getStartTime().toLocalDate().equals(currentDate))
							.collect(Collectors.toList());
					
					if (!todaysActivities.isEmpty()) {
						long totalWorkingMinutes = analystActivities.stream()
								.mapToLong(a -> ((AnalystActivity) a).getMinutes()).sum();
						
						long totalProductiveMinutes = analystActivities.stream()
								.filter(a -> ((AnalystActivity) a).getActivity().getActivityTypeId() == 1)
								.mapToLong(a -> ((AnalystActivity) a).getMinutes()).sum();
						
						long totalNeutralMinutes = analystActivities.stream()
								.filter(a -> ((AnalystActivity) a).getActivity().getActivityTypeId() == 2)
								.mapToLong(a -> ((AnalystActivity) a).getMinutes()).sum();
						
						long totalNonProductiveMinutes = analystActivities.stream()
								.filter(a -> ((AnalystActivity) a).getActivity().getActivityTypeId() == 2)
								.mapToLong(a -> ((AnalystActivity) a).getMinutes()).sum();
						
						TimeSummary timeSummary = new TimeSummary();
						timeSummary.setTotalWork(totalWorkingMinutes);
						timeSummary.setTotalProductive(totalProductiveMinutes);
						timeSummary.setTotalNeutral(totalNeutralMinutes);
						timeSummary.setTotalNonProductive(totalNonProductiveMinutes);
						
						productivityRemarks.put(currentDate, timeSummary.toString());
					} else {
						if (DateTimeUtils.isWeekend(currentDate)) {
							productivityRemarks.put(currentDate, "WOFF");
						}
						productivityRemarks.put(currentDate, "-");
					}
				}
				
				productivityMap.put(analyst, productivityRemarks);
			}
			
			Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
			
			// Create row for title
			List<String> dates = new ArrayList<>();
			for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1)) {
				dates.add(date.toString());
			}
			data.put(0, createRow("", "Name", dates));
			
			// Create rows for the contents
			int rowNumber = 1;
			for (Entity analystEntity : activeAnalysts) {
				Analyst analyst = (Analyst) analystEntity;
				
				Map<LocalDate, String> attendance = productivityMap.get(analyst);
				List<String> remarks = new ArrayList<>();
				
				for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1)) {
					final LocalDate currentDate = date;
					remarks.add(attendance.get(currentDate));
				}
				String fullName = analyst.getFirstName() + " " + analyst.getLastName();
				data.put(rowNumber, createRow(rowNumber, fullName, remarks));
				rowNumber++;
			}
			
			generateExcelFile(request, response, report, data);
		} catch (SQLException | IOException exc) {
			exc.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static Object[] createRow(Object... params) {
		List<Object> data = new ArrayList<>();
		for (Object param : params) {
			if (param instanceof List) {
				List objects = (List) param;
				for (int i = 0; i < objects.size(); i++) {
					data.add(objects.get(i).toString());
				}
			} else {
				data.add(param);
			}
		}
		Object[] dataArray = new Object[data.size()];
		data.toArray(dataArray);
		return dataArray;
	}
	
	private static void generateExcelFile(HttpServletRequest request, HttpServletResponse response,
			Report report, Map<Integer, Object[]> data) throws IOException {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(report.getName());
		
		Set<Integer> keys = data.keySet();
		int rowNum = 0;
		for (Integer key : keys) {
			Row row = sheet.createRow(rowNum++);
			Object[] d = data.get(key);
			int cellNum = 0;
			for (Object value : d) {
				Cell cell = row.createCell(cellNum++);
				if(value instanceof String) {
					cell.setCellValue((String) value);
				} else if(value instanceof Integer) {
					cell.setCellValue((Integer) value);
				}
				CellStyle cellStyle = cell.getSheet().getWorkbook().createCellStyle();
				cellStyle.setWrapText(true);
				cell.setCellStyle(cellStyle);
			}
		}
		
		int numberOfColumns = sheet.getRow(0).getLastCellNum();
		for (int column = 0; column < numberOfColumns; column++) {
			sheet.autoSizeColumn(column);
		}
		sheet.createFreezePane(1, 1);
		
		String fileName = report.getName() + "_" + report.getTeam().getName() + "_" + report.getFromDate() + "_" + report.getToDate();
		try {
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			workbook.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/ms-excel");
			response.setContentLength(outArray.length); 
			response.setHeader("Expires:", "0");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
			
			OutputStream outStream = response.getOutputStream();
			outStream.write(outArray);
			outStream.flush();
		} finally {
			workbook.close();
		}
	}
	
	private static class ReportQuery {
		private int id;
		private String query;
		
		public ReportQuery(int id, String query) {
			this.id = id;
			this.query = query;
		}

		public int getId() {
			return id;
		}

		public String getQuery() {
			return query;
		}
	}
	
}
