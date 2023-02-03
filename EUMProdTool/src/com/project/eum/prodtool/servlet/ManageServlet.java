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
import com.project.eum.prodtool.service.AnalystTeamService;
import com.project.eum.prodtool.service.FormFieldService;
import com.project.eum.prodtool.service.ReportService;
import com.project.eum.prodtool.service.TeamActivityService;
import com.project.eum.prodtool.service.TeamService;
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
	private final TeamService teamService = new TeamService();
	private final AnalystTeamService analystTeamService = new AnalystTeamService();
	private final TeamActivityService teamActivityService = new TeamActivityService();
       
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
			case "ACTIVATE_ANALYST":
				activateAnalyst(request, response);
				break;
			case "DEACTIVATE_ANALYST":
				deactivateAnalyst(request, response);
				break;
			case "CREATE_TEAM":
				createTeam(request, response);
				break;
			case "CREATE_ANALYST_TEAM":
				createAnalystTeam(request, response);
				break;
			case "CREATE_TEAM_ACTIVITY":
				createTeamActivity(request, response);
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
		request.setAttribute("active_analysts", teamTabDetails.getAnalysts());
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
			session.setAttribute("message", "Successfully generated a new report.");
			
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
			session.setAttribute("message", "Successfully archived the report.");
			
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
					session.setAttribute("message", "Successfully created a new analyst.");
					
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
		String activityTypeName = request.getParameter("activity_type_name");
		try {
			int key = activityTypeService.insertNewActivityType(activityTypeName);
			
			if (key > 0) {
				HttpSession session = request.getSession(false);
				session.setAttribute("last_page", "ACTIVITY");
				session.setAttribute("message", "Successfully created a new activity type.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on creating activity type.");
			}
		} catch (SQLException exception) {
			HttpSession session = request.getSession(false);
			session.setAttribute("last_page", "ACTIVITY");
			session.setAttribute("error_message", "There is already an activity type with existing name '" + activityTypeName + "'.");
			
			response.sendRedirect(request.getContextPath() + "/manage");
		}  catch (Exception exception) {
			handleException(request, response, exception, true);
		}
	}
	
	private void createActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("activity_name");
		Integer typeId = Integer.parseInt(request.getParameter("activity_type"));
		try {			
			int key = activityService.insertNewActivity(name, typeId);
			
			if (key > 0) {
				HttpSession session = request.getSession(false);
				session.setAttribute("last_page", "ACTIVITY");
				session.setAttribute("message", "Successfully created a new activity.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on creating activity.");
			}
		} catch (SQLException exception) {
			HttpSession session = request.getSession(false);
			session.setAttribute("last_page", "ACTIVITY");
			session.setAttribute("error_message", "There is already an activity with existing name '" + name + "'.");
			
			response.sendRedirect(request.getContextPath() + "/manage");
		} catch (Exception exception) {
			handleException(request, response, exception, true);
		}
	}
	
	private void createField(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("field_name");
		String type = request.getParameter("field_type");
		Integer isRequired = Integer.parseInt(request.getParameter("is_required"));
		try {			
			int key = formFieldService.insertNewField(name, type, isRequired);
			
			if (key > 0) {
				HttpSession session = request.getSession(false);
				session.setAttribute("last_page", "ACTIVITY");
				session.setAttribute("message", "Successfully created a new field.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on creating field.");
			}
		} catch (SQLException exception) {
			HttpSession session = request.getSession(false);
			session.setAttribute("last_page", "ACTIVITY");
			session.setAttribute("error_message", "There is already a field with existing name '" + name + "'.");
			
			response.sendRedirect(request.getContextPath() + "/manage");
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
				session.setAttribute("message", "Successfully created a new activity-field mapping.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on creating a new activity-field mapping.");
			}
		} catch (SQLException exception) {
			HttpSession session = request.getSession(false);
			session.setAttribute("last_page", "ACTIVITY");
			session.setAttribute("error_message", "There is already an existing activity-field mapping.");
			
			response.sendRedirect(request.getContextPath() + "/manage");
		}  catch (Exception exception) {
			handleException(request, response, exception, true);
		}
	}
	
	private void activateAnalyst(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String analystId = request.getParameter("analyst_id");
			
			int affectedRows = analystService.updateAnalystActive(analystId, 1);
			
			if (affectedRows > 0) {
				HttpSession session = request.getSession(false);
				session.setAttribute("last_page", "ANALYST");
				session.setAttribute("message", "Analyst is activated.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on activating the analyst.");
			}
		} catch (Exception exception) {
			handleException(request, response, exception, true);
		}
	}
	
	private void deactivateAnalyst(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String analystId = request.getParameter("analyst_id");
			
			int affectedRows = analystService.updateAnalystActive(analystId, 0);
			
			if (affectedRows > 0) {
				HttpSession session = request.getSession(false);
				session.setAttribute("last_page", "ANALYST");
				session.setAttribute("message", "Analyst is deactivated.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on deactivating the analyst.");
			}
		} catch (Exception exception) {
			handleException(request, response, exception, true);
		}
	}
	
	private void createTeam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String teamName = request.getParameter("create_team_team_name");
		String teamType = request.getParameter("create_team_type");
		try {			
			int affectedRows = teamService.insertNewTeam(teamName, teamType);
			
			if (affectedRows > 0) {
				HttpSession session = request.getSession(false);
				session.setAttribute("last_page", "TEAM");
				session.setAttribute("message", "Successfully created a new team.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on creating a team.");
			}
		} catch (SQLException exception) {
			HttpSession session = request.getSession(false);
			session.setAttribute("last_page", "TEAM");
			session.setAttribute("error_message", "There is already a team with an existing name '" + teamName + "'.");
			
			response.sendRedirect(request.getContextPath() + "/manage");
		} catch (Exception exception) {
			handleException(request, response, exception, true);
		}
	}
	
	private void createAnalystTeam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer analystId = Integer.parseInt(request.getParameter("analyst_team_analyst"));
		Integer teamId = Integer.parseInt(request.getParameter("analyst_team_team"));
		try {			
			int affectedRows = analystTeamService.insertNewAnalystTeam(analystId, teamId);
			
			if (affectedRows > 0) {
				HttpSession session = request.getSession(false);
				session.setAttribute("last_page", "TEAM");
				session.setAttribute("message", "Successfully created a new analyst-team mapping.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on creating a new analyst-team mapping.");
			}
		} catch (SQLException exception) {
			HttpSession session = request.getSession(false);
			session.setAttribute("last_page", "TEAM");
			session.setAttribute("error_message", "There is already an existing analyst-team mapping.");
			
			response.sendRedirect(request.getContextPath() + "/manage");
		} catch (Exception exception) {
			handleException(request, response, exception, true);
		}
	}
	
	private void createTeamActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer teamId = Integer.parseInt(request.getParameter("team_activity_team"));
		Integer activityId = Integer.parseInt(request.getParameter("team_activity_activity"));
		try {			
			int affectedRows = teamActivityService.insertNewTeamActivity(teamId, activityId);
			
			if (affectedRows > 0) {
				HttpSession session = request.getSession(false);
				session.setAttribute("last_page", "TEAM");
				session.setAttribute("message", "Successfully creating a new team-activity mapping.");
				
				response.sendRedirect(request.getContextPath() + "/manage");
			} else {
				throw new RuntimeException("There is an error on creating a new team-activity mapping.");
			}
		} catch (SQLException exception) {
			HttpSession session = request.getSession(false);
			session.setAttribute("last_page", "TEAM");
			session.setAttribute("error_message", "There is already an existing team-activity mapping.");
			
			response.sendRedirect(request.getContextPath() + "/manage");
		} catch (Exception exception) {
			handleException(request, response, exception, true);
		}
	}

}
