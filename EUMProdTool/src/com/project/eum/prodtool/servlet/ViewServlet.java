package com.project.eum.prodtool.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.eum.prodtool.base.Entity;
import com.project.eum.prodtool.model.Activity;
import com.project.eum.prodtool.model.AnalystActivity;
import com.project.eum.prodtool.model.AnalystActivityFieldDetail;
import com.project.eum.prodtool.model.FormField;
import com.project.eum.prodtool.model.column.AnalystActivityFieldDetailColumn;
import com.project.eum.prodtool.model.field.ActivityField;
import com.project.eum.prodtool.model.field.AnalystActivityField;
import com.project.eum.prodtool.model.field.FormFieldField;
import com.project.eum.prodtool.service.ActivityService;
import com.project.eum.prodtool.service.AnalystActivityFieldDetailService;
import com.project.eum.prodtool.service.AnalystActivityService;
import com.project.eum.prodtool.service.FormFieldService;

/**
 * Servlet implementation class ViewServlet
 */
@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final ActivityService activityService = new ActivityService();
	private final AnalystActivityService analystActivityService = new AnalystActivityService();
	private final FormFieldService formFieldService = new FormFieldService();
	private final AnalystActivityFieldDetailService analystActivityFieldDetailService = new AnalystActivityFieldDetailService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewServlet() {
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
			case "SAVE":
				saveAction(request, response);
				break;
			case "BACK":
				backAction(request, response);
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
		Integer analystActivityId = (Integer) request.getAttribute("analyst_activity_id");
		Integer analystId = (Integer) request.getAttribute("analyst_id");
		Integer activityId = (Integer) request.getAttribute("activity_id");
		
		try {
			AnalystActivity analystActivity = new AnalystActivity();
			Activity activity = null;
			List<FormField> fields = null;
			
			if (analystActivityId == null) {				
				LocalDateTime now = LocalDateTime.now();
				analystActivity.set(AnalystActivityField.id, 0)
						.set(AnalystActivityField.startTime, now)
						.set(AnalystActivityField.createdDate, now)
						.set(AnalystActivityField.updatedDate, now);
				activity = (Activity) activityService.getEntityById(activityId);
				fields = formFieldService.getFormFieldsByActivityId(activityId);
			} else {
				analystActivity = (AnalystActivity) analystActivityService.getEntityById(analystActivityId);
				activity = (Activity) activityService.getEntityById((Integer) analystActivity.get(AnalystActivityField.activityId));
				fields = formFieldService.getFormFieldsByActivityId((Integer) analystActivity.get(AnalystActivityField.activityId));
				analystId = analystActivity.getAnalystId();
				activityId = analystActivity.getActivityId();
			}
						
			List<Entity> fieldValues = analystActivityFieldDetailService.getEntitiesByColumn(AnalystActivityFieldDetailColumn.ANALYST_ACTIVITY_ID, analystActivityId);
			
			if (!fieldValues.isEmpty()) {
				for (Entity fieldValue : fieldValues) {
					AnalystActivityFieldDetail detail = (AnalystActivityFieldDetail) fieldValue;
					
					FormField field = fields.stream().filter(f -> detail.getFieldId() == f.getId()).findAny().orElse(null);
					if (field != null) {
						field.set(FormFieldField.value, detail.getValue());
					}
				}
			}
			
			request.setAttribute("activity_name", (String) activity.get(ActivityField.NAME));
			request.setAttribute("analyst_activity", analystActivity);
			request.setAttribute("fields", fields);
			request.setAttribute("analyst_id", analystId);
			request.setAttribute("activity_id", activityId);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("common/views/view.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}
	
	protected void saveAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer analystActivityId = Integer.parseInt(request.getParameter("analyst_activity_id"));
		Integer analystId = Integer.parseInt(request.getParameter("analyst_id"));
		Integer activityId = Integer.parseInt(request.getParameter("activity_id"));
		
		try {
			AnalystActivity analystActivity = null;
			List<FormField> fields = null;
			Integer trueKey = null;
			
			if (analystActivityId == 0) {
				trueKey = analystActivityService.insertNewAnalystActivity(analystId, activityId);
				fields = formFieldService.getFormFieldsByActivityId(activityId);
			} else {
				analystActivity = (AnalystActivity) analystActivityService.getEntityById(analystActivityId);
				fields = formFieldService.getFormFieldsByActivityId((Integer) analystActivity.get(AnalystActivityField.activityId));
			}
			
			Map<Integer, String> fieldValues = new HashMap<Integer, String>();
			
			String remarksValue = "";
			for (FormField field : fields) {
				Integer fieldId = (Integer) field.get(FormFieldField.id);
				String fieldType = (String) field.get(FormFieldField.type);
				
				String value = request.getParameter(analystActivityId + "_" + fieldId + "_" + fieldType);
				fieldValues.put(fieldId, value);
				
				if (field.getName().equals("Remarks")) {
					remarksValue = value; 
				}
			}
			System.out.println(fieldValues);
			
			Set<Integer> keys = fieldValues.keySet();
			Iterator<Integer> keyIterator = keys.iterator();
			if (trueKey != null) {
				analystActivityId = trueKey;
			}
			
			boolean isValid = true;
			while (keyIterator.hasNext()) {
				Integer fieldId = keyIterator.next();
				String value = fieldValues.get(fieldId);
				
				int affectedRows = analystActivityFieldDetailService.updateActivityFieldValue(analystActivityId, fieldId, value);
				if (affectedRows == 0) {
					affectedRows = analystActivityFieldDetailService.insertActivityFieldValue(analystActivityId, fieldId, value);
					if (affectedRows == -1) {
						isValid = false;
					}
				}
			}
			
			if (isValid) {
				analystActivityService.updateActivityRemarks(analystActivityId, remarksValue);
				backAction(request, response);
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}

	protected void backAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		session.removeAttribute("record_inserted_successfully");
		session.removeAttribute("inserted_id");
		
		response.sendRedirect(request.getContextPath() + "/home");
	}
	
}
