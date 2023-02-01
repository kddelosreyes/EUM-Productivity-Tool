package com.project.eum.prodtool.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.model.Analyst;
import com.project.eum.prodtool.model.AnalystLogin;
import com.project.eum.prodtool.model.column.AnalystLoginColumn;
import com.project.eum.prodtool.model.field.AnalystLoginField;
import com.project.eum.prodtool.service.ActivityService;
import com.project.eum.prodtool.service.AnalystLoginHistoryService;
import com.project.eum.prodtool.service.AnalystLoginService;
import com.project.eum.prodtool.service.AnalystService;
import com.project.eum.prodtool.service.AttendanceService;
import com.project.eum.prodtool.service.ShiftScheduleService;
import com.project.eum.prodtool.utils.PasswordUtils;

/**
 * @author khdelos
 *
 */
/**
 * Servlet implementation class HomeServlet
 */

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final ActivityService activityService = new ActivityService();
	private final AnalystLoginService analystLoginService = new AnalystLoginService();
	private final AnalystService analystService = new AnalystService();
	private final AnalystLoginHistoryService analystLoginHistoryService = new AnalystLoginHistoryService();
	private final AttendanceService attendanceService = new AttendanceService();
	private final ShiftScheduleService shiftScheduleService = new ShiftScheduleService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String command = request.getParameter("command");

		if (command == null) {
			command = "DEFAULT";
		}

		switch (command) {
		case "DEFAULT":
			defaultAction(request, response);
			break;
		case "LOGIN":
			HttpSession session = request.getSession(false);
			if (session != null && session.getAttribute("analyst") != null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/home");
				dispatcher.forward(request, response);
			} else {
				loginUser(request, response);
			}
			break;
		case "LOGOUT":
			logoutUser(request, response);
			break;
		default:
			defaultAction(request, response);
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
		if (session == null) {
		    session = request.getSession();
		}
		request.getRequestDispatcher("common/views/login.jsp").forward(request, response);
	}
	
	protected void logoutUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Expires", "0");
		response.setDateHeader("Expires", -1);
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	protected void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (username.isEmpty() || password.isEmpty()) {
	        response.getWriter().write("Some fields are empty.");
		} else {
			if (username.equals("manager") && password.equals("manager")) {
				HttpSession session = request.getSession(false);
				setSessionDetails(session, new Analyst(), new AnalystLogin());
				response.getWriter().write("MANAGER-LOGIN");
			} else {
				username = username + "@indracompany.com";
				try {
					Entity entity = analystLoginService.getEntityByColumn(AnalystLoginColumn.USERNAME, username);
					
					if (entity == null) {
				        response.getWriter().write("Username does not exists.");
					} else {
						AnalystLogin analystLogin = (AnalystLogin) entity;
						boolean isVerified = PasswordUtils.verifyUserPassword(password,
								(String) analystLogin.get(AnalystLoginField.PASSWORD),
								(String) analystLogin.get(AnalystLoginField.SALT));
						if (isVerified) {
							if (analystLogin.getIsLocked()) {
								HttpSession session = request.getSession(false);
								Integer analystId = (Integer) analystLogin.get(AnalystLoginField.ANALYST_ID);
								Analyst analyst = (Analyst) analystService.getEntityById(analystId);
								setSessionDetails(session, analyst, analystLogin);
								
								response.getWriter().write("CHANGE-PASSWORD");
							} else {
								Integer analystId = (Integer) analystLogin.get(AnalystLoginField.ANALYST_ID);
								Entity shiftSchedule = shiftScheduleService.getShiftScheduleByAnalystId(analystId);
								
								if (shiftSchedule == null) {
									response.getWriter().write("No active shift schedule.");
								} else {
									if (analystService.hasTeam(analystId)) {
										HttpSession session = request.getSession(false);
										Analyst analyst = (Analyst) analystService.getEntityById(analystId);
										
										analystLoginHistoryService.insertNewAnalystLoginHistory((Integer) analystLogin.get(AnalystLoginField.ID));
										if (!attendanceService.hasAttendanceForToday(analystId)) {
											attendanceService.insertNewAttendanceForAnalystId(analystId);
										}
										
										setSessionDetails(session, analyst, analystLogin);
										response.getWriter().write("ANALYST-LOGIN");
									} else {
										response.getWriter().write("Analyst does not belong to a team.");
									}
								}
							}
						} else {
					        response.getWriter().write("Username and password does not match!");
						}
					}
				} catch(SQLException exception) {
					exception.printStackTrace();
				}
			}
		}
	}
	
	private void setSessionDetails(HttpSession session, Analyst analyst, AnalystLogin analystLogin) {
		session.setAttribute("analyst", analyst);
		session.setAttribute("analyst_login", analystLogin);
	}
}
