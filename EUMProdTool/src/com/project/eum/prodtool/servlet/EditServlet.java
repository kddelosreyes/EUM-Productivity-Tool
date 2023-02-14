package com.project.eum.prodtool.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.service.AnalystShiftScheduleService;
import com.project.eum.prodtool.service.ShiftScheduleService;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/EditServlet")
public class EditServlet extends AppServlet {
	private static final long serialVersionUID = 1L;
	
	private final AnalystShiftScheduleService analystShiftScheduleService = new AnalystShiftScheduleService();
	private final ShiftScheduleService shiftScheduleService = new ShiftScheduleService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
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
			
			if (command == null) {
				command = "DEFAULT";
			}
			
			switch (command) {
			case "DEFAULT":
				defaultAction(request, response);
				break;
			case "SAVE_ANALYST_SHIFT_SCHEDULE":
				saveAnalystShiftSchedule(request, response);
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
		Integer analystShiftScheduleId = (Integer) request.getAttribute("analyst_shift_schedule_id");
		String view = (String) request.getAttribute("view");
		
		try {
			Entity entity = null;
			
			if (view.equals("ANALYST_SHIFT_SCHEDULE")) {
				List<Entity> shiftSchedules = shiftScheduleService.getAll();
				entity = analystShiftScheduleService.getEntityById(analystShiftScheduleId);
				request.setAttribute("view", "ANALYST_SHIFT_SCHEDULE");
				request.setAttribute("title", "Analyst Shift Schedule");
				request.setAttribute("shift_schedules", shiftSchedules);
			}
			
			request.setAttribute("entity", entity);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("common/views/edit.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException exception) {
			handleException(request, response, exception, true);
		}
	}
	
	protected void saveAnalystShiftSchedule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("analyst_shift_schedule_id"));
		Integer shiftScheduleId = Integer.parseInt(request.getParameter("analyst_shift_schedule_shift"));
		
		try {
			int affectedRows = analystShiftScheduleService.saveAnalystShiftSchedule(id, shiftScheduleId);
			
			if (affectedRows > 0) {
				response.sendRedirect(request.getContextPath() + "/manage");
			}
		} catch (SQLException exception) {
			handleException(request, response, exception, true);
		}
	}

}
