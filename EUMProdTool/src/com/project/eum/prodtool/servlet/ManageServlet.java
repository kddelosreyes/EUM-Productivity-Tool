package com.project.eum.prodtool.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.eum.prodtool.model.Report;
import com.project.eum.prodtool.model.column.ReportColumn;
import com.project.eum.prodtool.service.ActivityFieldService;
import com.project.eum.prodtool.service.ActivityService;
import com.project.eum.prodtool.service.ActivityTypeService;
import com.project.eum.prodtool.service.AnalystLoginService;
import com.project.eum.prodtool.service.AnalystService;
import com.project.eum.prodtool.service.FormFieldService;
import com.project.eum.prodtool.service.ReportService;
import com.project.eum.prodtool.utils.DateTimeUtils;
import com.project.eum.prodtool.utils.PasswordUtils;
import com.project.eum.prodtool.utils.ReportGeneratorUtils;
import com.project.eum.prodtool.view.TabDetails_Activity;
import com.project.eum.prodtool.view.TabDetails_Analyst;
import com.project.eum.prodtool.view.TabDetails_Attendance;
import com.project.eum.prodtool.view.TabDetails_Home;
import com.project.eum.prodtool.view.TabDetails_Report;
import com.project.eum.prodtool.view.TabDetails_Team;

/**
 * Servlet implementation class Manage
 */
@WebServlet("/ManageServlet")
public class ManageServlet extends AppServlet {
	private static final long serialVersionUID = 1L;
	
	private final ReportService reportService = new ReportService();
	private final AnalystService analystService = new AnalystService();
	private final AnalystLoginService analystLoginService = new AnalystLoginService();
	private final ActivityTypeService activityTypeService = new ActivityTypeService();
	private final ActivityService activityService = new ActivityService();
	private final FormFieldService formFieldService = new FormFieldService();
	private final ActivityFieldService activityFieldService = new ActivityFieldService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("analyst") == null) {
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Expires", "0");
			response.setDateHeader("Expires", -1);
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			String command = request.getParameter("command");
			
			if (command == null) {
				command = "DEFAULT";
			}
			
			switch (command) {
			case "DEFAULT":
				defaultAction(request, response);
				break;
			case "GENERATE_REPORT":
				generateReport(request, response);
				break;
			case "DOWNLOAD_REPORT":
				downloadReport(request, response);
				break;
			case "ARCHIVE_REPORT":
				archiveReport(request, response);
				break;
			case "CREATE_ANALYST":
				createAnalyst(request, response);
				break;
			case "CREATE_ACTIVITY_TYPE":
				createActivityType(request, response);
				break;
			case "CREATE_ACTIVITY":
				createActivity(request, response);
				break;
			case "CREATE_FIELD":
				createField(request, response);
				break;
			case "CREATE_ACTIVITY_FIELD_MAP":
				createActivityFieldMap(request, response);
				break;
			default:
				defaultAction(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void defaultAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * Home - Start
		 * */
		TabDetails_Home homeTabDetails = new TabDetails_Home();
		request.setAttribute("attendance_counts", homeTabDetails.getAttendanceCounts());
		request.setAttribute("attendance_logs", homeTabDetails.getAttendanceLogs());
		request.setAttribute("activity_logs", homeTabDetails.getActivityLogs());
		/*
		 * Home - End
		 * */
		
		/*
		 * Activity - Start
		 * */
		TabDetails_Activity activityTabDetails = new TabDetails_Activity();
		request.setAttribute("activity_types", activityTabDetails.getActivityTypes());
		request.setAttribute("activities", activityTabDetails.getActivities());
		request.setAttribute("activity_fields", activityTabDetails.getFormFields());
		request.setAttribute("activity_field_map", activityTabDetails.getActivityFieldMapping());
		/*
		 * Activity - End
		 * */
		
		/*
		 * Analyst - Start
		 * */
		TabDetails_Analyst analystTabDetails = new TabDetails_Analyst();
		request.setAttribute("analysts", analystTabDetails.getAnalysts());
		/*
		 * Analyst - End
		 * */
		
		/*
		 * Team - Start
		 * */
		TabDetails_Team teamTabDetails = new TabDetails_Team();
		request.setAttribute("teams", teamTabDetails.getTeams());
		request.setAttribute("analyst_teams", teamTabDetails.getAnalystTeams());
		request.setAttribute("team_activities", teamTabDetails.getActivityTeams());
		/*
		 * Team - End
		 * */
		
		/*
		 * Attendance - Start
		 * */
		TabDetails_Attendance attendanceTabDetails = new TabDetails_Attendance();
		request.setAttribute("analystDetails", attendanceTabDetails.getAnalystDetails());
		/*
		 * Attendance - End
		 * */
		
		/*
		 * Report - Start
		 * */
		TabDetails_Report reportTabDetails = new TabDetails_Report();
		request.setAttribute("report_types", reportTabDetails.getReportTypes());
		request.setAttribute("main_teams", reportTabDetails.getTeams());
		request.setAttribute("reports", reportTabDetails.getReports());
		/*
		 * Report - End
		 * */
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("common/views/manage.jsp");
		dispatcher.forward(request, response);
	}
	
	private void generateReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String reportName = request.getParameter("report_name");
		Integer reportTypeId = Integer.parseInt(request.getParameter("report_type_id"));

		Date startDate = DateTimeUtils.toDateFormat(request.getParameter("report_start_date"), DateTimeUtils.MMDDYYYY);
		Date endDate = DateTimeUtils.toDateFormat(request.getParameter("report_end_date"), DateTimeUtils.MMDDYYYY);
		
		Integer teamId = Integer.parseInt(request.getParameter("team_id"));
		
		try {
			int returnedKey = reportService.insertReportDetails(
					reportName, reportTypeId, DateTimeUtils.toSqlDateString(startDate),
					DateTimeUtils.toSqlDateString(endDate), teamId);
			
			HttpSession session = request.getSession(false);
			session.setAttribute("last_page", "REPORT");
			session.setAttribute("message", "Report generated successfully.");
			
			response.getWriter().write(Integer.toString(returnedKey));
		} catch(SQLException exception) {
			handleException(request, response, exception, false);
		}
	}
	
	private void downloadReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String reportUuid = request.getParameter("report_id");
			
			Report report = (Report) reportService.getEntityByColumn(ReportColumn.UUID, reportUuid);
			ReportGeneratorUtils.generateReport(request, response, report);
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}
	
	private void archiveReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			String uuid = request.getParameter("report_id");
			int affectedColumn = reportService.updateReportToArchiveById(uuid);
			
			HttpSession session = request.getSession(false);
			session.setAttribute("last_page", "REPORT");
			session.setAttribute("message", "Report successfully archived.");
			
			response.sendRedirect(request.getContextPath() + "/manage");
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
	}
	
	private void createAnalyst(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String firstName = request.getParameter("first_name");
			String middleName = request.getParameter("middle_name");
			String lastName = request.getParameter("last_name");
			String role = request.getParameter("role");
			String email = request.getParameter("email");
			
			int analystId = analystService.insertNewAnalyst(firstName, middleName, lastName, role);
			if (analystId > 0) {
				String salt = PasswordUtils.getSaltvalue(36);
				String password = PasswordUtils.generateSecurePassword(email, salt);
				int returnedKey = analystLoginService.insertNewAnalystLogin(analystId, email, password, salt, 1, 0);
				
				if (returnedKey > 0) {
					HttpSession session = request.getSession(false);
					session.setAttribute("last_page", "ANALYST");
					session.setAttribute("message", "Analyst successfully created.");
					
					System.out.println("Created analyst and account.");
					
					response.getWriter().write(Integer.toString(returnedKey));
				} else {
					throw new RuntimeException("There is an error on adding analyst.");
				}
			}
		} catch (Exception exception) {
			handleException(request, response, exception, false);
		}
	}
	
	private void createActivityType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String activityTypeName = request.getParameter("activity_type_name");
			int key = activityTypeService.insertNewActivityType(activityTypeName);
			
			if (key > 0) {
				HttpSession session = request.getSession(false);
				session.setAttribute("last_page", "ACTIVITY");
				session.setAttribute("message", "Activity type successfully created.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on creating activity type.");
			}
		} catch (Exception exception) {
			handleException(request, response, exception, true);
		}
	}
	
	private void createActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name = request.getParameter("activity_name");
			Integer typeId = Integer.parseInt(request.getParameter("activity_type"));
			
			int key = activityService.insertNewActivity(name, typeId);
			
			if (key > 0) {
				HttpSession session = request.getSession(false);
				session.setAttribute("last_page", "ACTIVITY");
				session.setAttribute("message", "Activity successfully created.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on creating activity.");
			}
		} catch (Exception exception) {
			handleException(request, response, exception, true);
		}
	}
	
	private void createField(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name = request.getParameter("field_name");
			String type = request.getParameter("field_type");
			Integer isRequired = Integer.parseInt(request.getParameter("is_required"));
			
			int key = formFieldService.insertNewField(name, type, isRequired);
			
			if (key > 0) {
				HttpSession session = request.getSession(false);
				session.setAttribute("last_page", "ACTIVITY");
				session.setAttribute("message", "Field successfully created.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on creating field.");
			}
		} catch (Exception exception) {
			handleException(request, response, exception, true);
		}
	}
	
	private void createActivityFieldMap(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Integer activityId = Integer.parseInt(request.getParameter("map_activity_value"));
			Integer fieldId = Integer.parseInt(request.getParameter("map_field_value"));
			
			int key = activityFieldService.insertNewActivityField(activityId, fieldId);
			
			if (key > 0) {
				HttpSession session = request.getSession(false);
				session.setAttribute("last_page", "ACTIVITY");
				session.setAttribute("message", "Activity-field mapping successfully created.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on creating a new activity-field mapping.");
			}
		} catch (Exception exception) {
			handleException(request, response, exception, true);
		}
	}

}
