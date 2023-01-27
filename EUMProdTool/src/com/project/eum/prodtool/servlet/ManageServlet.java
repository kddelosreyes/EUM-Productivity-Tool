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
import com.project.eum.prodtool.service.ReportService;
import com.project.eum.prodtool.utils.DateTimeUtils;
import com.project.eum.prodtool.utils.ReportGeneratorUtils;
import com.project.eum.prodtool.view.TabDetails_Activity;
import com.project.eum.prodtool.view.TabDetails_Attendance;
import com.project.eum.prodtool.view.TabDetails_Home;
import com.project.eum.prodtool.view.TabDetails_Report;

/**
 * Servlet implementation class Manage
 */
@WebServlet("/ManageServlet")
public class ManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final ReportService reportService = new ReportService();
       
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
		} catch(SQLException exc) {
			exc.printStackTrace();
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

}
