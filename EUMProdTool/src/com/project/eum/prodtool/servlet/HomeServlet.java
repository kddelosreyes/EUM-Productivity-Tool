package com.project.eum.prodtool.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.model.Activity;
import com.project.eum.prodtool.model.Analyst;
import com.project.eum.prodtool.model.AnalystActivity;
import com.project.eum.prodtool.model.AnalystActivityFieldDetail;
import com.project.eum.prodtool.model.Attendance;
import com.project.eum.prodtool.model.ShiftSchedule;
import com.project.eum.prodtool.model.add.TimeSummary;
import com.project.eum.prodtool.model.field.AnalystActivityField;
import com.project.eum.prodtool.model.field.AnalystField;
import com.project.eum.prodtool.service.ActivityService;
import com.project.eum.prodtool.service.AnalystActivityFieldDetailService;
import com.project.eum.prodtool.service.AnalystActivityService;
import com.project.eum.prodtool.service.AttendanceService;
import com.project.eum.prodtool.service.ShiftScheduleService;

/**
 * @author khdelos
 *
 */
/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final ActivityService activityService = new ActivityService();
	private final AnalystActivityService analystActivityService = new AnalystActivityService();
	private final AttendanceService attendanceService = new AttendanceService();
	private final ShiftScheduleService shiftScheduleService = new ShiftScheduleService();
	private final AnalystActivityFieldDetailService analystActivityFieldDetailService = new AnalystActivityFieldDetailService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		
		System.out.println(session == null);
		if (session == null || session.getAttribute("analyst") == null) {
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Expires", "0");
			response.setDateHeader("Expires", -1);
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			
			String command = request.getParameter("command");
			
			if (command == null || command.equals("LOGIN")) {
				command = "DEFAULT";
			}
			
			switch (command) {
			case "DEFAULT":
				defaultAction(request, response);
				break;
			case "START_ACTIVITY":
				startActivity(request, response);
				break;
			case "END_ACTIVITY":
				endActivity(request, response);
				break;
			case "VIEW_ACTIVITY":
				viewActivity(request, response);
				break;
			case "TIME_OUT":
				timeOut(request, response);
				break;
			case "UPDATE_ATTENDANCE_STATUS":
				updateAttendanceStatus(request, response);
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
		HttpSession session = request.getSession(false);
		
		System.out.println(session == null);
		if (session == null) {
			response.sendRedirect(request.getContextPath() + "/login");
		} else {
			Analyst analyst = (Analyst) session.getAttribute("analyst");
			
			if (analyst == null) {
				if (session != null) {
					session.invalidate();
				}
				response.sendRedirect(request.getContextPath() + "/login");
			} else {
				System.out.println("Analyst Id: " + analyst.getId());
				
				try {
					Integer analystId = (Integer) analyst.get(AnalystField.ID);
					
					ShiftSchedule shiftSchedule = (ShiftSchedule) shiftScheduleService.getShiftScheduleByAnalystId(analystId);
					Attendance attendance = null;
					if (shiftSchedule.getIsNightShift()) {
						if (attendanceService.hasAttendanceForYesterdayNoOut(analystId)) {
							attendance = (Attendance) attendanceService.getAttendanceForYesterday(analystId);
						} else if (attendanceService.hasAttendanceForYesterday(analystId)) {
							attendance = (Attendance) attendanceService.getAttendanceForToday(analystId);							
						}
					} else {
						attendance = (Attendance) attendanceService.getAttendanceForToday(analystId);
					}
					
					if (attendance == null) {
						attendance = (Attendance) attendanceService.getLatestAttendance(analystId);
					}
					
					List<Activity> activities = activityService.getActivitiesByAnalystId((Integer) analyst.get(AnalystField.ID));
					request.setAttribute("activities", activities);
					
					List<AnalystActivity> analystActivities = null;
					
					if (shiftSchedule.getIsNightShift()) {
						analystActivities = analystActivityService.getTodaysActivitiesByAnalystId(analystId, attendance.getTimeIn(), attendance.getTimeOut());
					} else {
						analystActivities = analystActivityService.getTodaysActivitiesByAnalystId(analystId);
					}
					
					if (!analystActivities.isEmpty()) {
						List<Integer> ids = analystActivities.stream()
								.map(a -> a.getId())
								.collect(Collectors.toList());
						String inIds = ids.stream()
								.map(a -> Integer.toString(a))
								.collect(Collectors.joining(","));
						
						List<Entity> fieldDetails = analystActivityFieldDetailService.getRemarksFromAnalystActivityIds(inIds);
						
						for (AnalystActivity analystActivity : analystActivities) {
							AnalystActivityFieldDetail fieldDetail = (AnalystActivityFieldDetail) fieldDetails.stream()
									.filter(a -> ((AnalystActivityFieldDetail)a).getAnalystActivityId() == analystActivity.getId())
									.findAny()
									.orElse(null);
							
							if (fieldDetail != null) {
								analystActivity.set(AnalystActivityField.remarks, fieldDetail.getValue());
							}
						}
					}
					
					Boolean isAnyPending = analystActivityService.isAnyPendingActivityByAnalystId(analystId);
					
					long totalWorkingMinutes = analystActivities.stream()
							.mapToLong(a -> a.getMinutes()).sum();
					
					long totalProductiveMinutes = analystActivities.stream()
							.filter(a -> a.getActivity().getActivityTypeId() == 1)
							.mapToLong(a -> a.getMinutes()).sum();
					
					long totalNeutralMinutes = analystActivities.stream()
							.filter(a -> a.getActivity().getActivityTypeId() == 2)
							.mapToLong(a -> a.getMinutes()).sum();
					
					long totalNonProductiveMinutes = analystActivities.stream()
							.filter(a -> a.getActivity().getActivityTypeId() == 3)
							.mapToLong(a -> a.getMinutes()).sum();
					
					TimeSummary timeSummary = new TimeSummary();
					timeSummary.setTotalWork(totalWorkingMinutes);
					timeSummary.setTotalProductive(totalProductiveMinutes);
					timeSummary.setTotalNeutral(totalNeutralMinutes);
					timeSummary.setTotalNonProductive(totalNonProductiveMinutes);
					
					AnalystActivity analystActivity = analystActivities
							.stream()
							.filter(a -> a.getEndTime() == null)
							.findAny()
							.orElse(null);
					
					boolean hasPending = analystActivity != null;
					
					request.setAttribute("analyst_activities", analystActivities);
					request.setAttribute("is_any_pending", isAnyPending);
					request.setAttribute("time_summary", timeSummary);
					request.setAttribute("attendance", attendance);
					request.setAttribute("has_pending", hasPending);
					request.setAttribute("shift_schedule", shiftSchedule);
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("common/views/home.jsp");
					dispatcher.forward(request, response);
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		}
	}
	
	protected void startActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		Integer analystId = Integer.parseInt(request.getParameter("analyst_id"));
		Integer activityId = Integer.parseInt(request.getParameter("activity_id"));
		
		request.setAttribute("analyst_id", analystId);
		request.setAttribute("activity_id", activityId);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view");
		dispatcher.forward(request, response);
	}
	
	protected void endActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer analystActivityId = Integer.parseInt(request.getParameter("analyst_activity_id"));
		
		try {
			int affectedRows = analystActivityService.updateAnalystActivityEndTimeById(analystActivityId);
			
			if (affectedRows == 0) {
				throw new SQLException("Affected rows = 0");
			} else {
				System.out.println("Affected Rows: " + affectedRows);
				response.sendRedirect(request.getContextPath() + "/home");
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}
	
	protected void viewActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer analystActivityId = Integer.parseInt(request.getParameter("analyst_activity_id"));
		
		request.setAttribute("analyst_activity_id", analystActivityId);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view");
		dispatcher.forward(request, response);
	}
	
	protected void timeOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer attendanceId = Integer.parseInt(request.getParameter("attendance_id"));
		
		try {
			int affectedRows = attendanceService.updateAnalystAttendance(attendanceId);
			
			if (affectedRows == 0) {
				throw new SQLException("Affected rows = 0");
			} else {
				System.out.println("Affected Rows: " + affectedRows);
				response.sendRedirect(request.getContextPath() + "/home");
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}
	
	protected void updateAttendanceStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer attendanceId = Integer.parseInt(request.getParameter("attendance_id"));
		String status = request.getParameter("status");
		
		try {
			int affectedRows = attendanceService.updateAttendanceStatus(attendanceId, status);
			
			if (affectedRows == 0) {
				throw new SQLException("Affected rows = 0");
			} else {
				System.out.println("Affected Rows: " + affectedRows);
				response.getWriter().write("SUCCESS");
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}

}
