package com.project.eum.prodtool.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.eum.prodtool.model.Analyst;
import com.project.eum.prodtool.model.AnalystLogin;
import com.project.eum.prodtool.model.column.AnalystLoginColumn;
import com.project.eum.prodtool.service.AnalystLoginService;
import com.project.eum.prodtool.utils.PasswordUtils;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final AnalystLoginService analystLoginService = new AnalystLoginService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getParameter("command");
		
		if (command == null) {
			command = "DEFAULT";
		}

		switch (command) {
		case "DEFAULT":
			defaultAction(request, response);
			break;
		case "CHANGE_PASSWORD":
			changePassword(request, response);
			break;
		case "DEFAULT_FROM_ANALYST":
			defaultFromAnalyst(request, response);
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
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Expires", "0");
			response.setDateHeader("Expires", -1);
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			request.getRequestDispatcher("common/views/password.jsp").forward(request, response);
		}
	}
	
	protected void defaultFromAnalyst(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Expires", "0");
			response.setDateHeader("Expires", -1);
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			Analyst analyst = (Analyst) session.getAttribute("analyst");
			AnalystLogin analystLogin = (AnalystLogin) session.getAttribute("analyst_login");
			
			request.setAttribute("analyst", analyst);
			request.setAttribute("analyst_login", analystLogin);
			
			request.getRequestDispatcher("common/views/changepassword.jsp").forward(request, response);
		}
	}
	
	protected void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			
			String username = request.getParameter("username");
			username = username + "@indracompany.com";
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirm_password");
			Integer securityQuestion = Integer.parseInt(request.getParameter("security_question"));
			String securityAnswer = request.getParameter("security_answer");
			
			AnalystLogin analystLogin = null;
			
			if (session != null && session.getAttribute("analyst_login") != null) {
				analystLogin = (AnalystLogin) session.getAttribute("analyst_login");
			} else {
				analystLogin = (AnalystLogin) analystLoginService.getEntityByColumn(AnalystLoginColumn.USERNAME, username);
			}
			
			if (username.equalsIgnoreCase(analystLogin.getUsername())) {
				if (password.equals(confirmPassword)) {
					try {
						String salt = analystLogin.getSalt();
						String newPassword = PasswordUtils.generateSecurePassword(password, salt);
						// Already changed password
						if (analystLogin.getSecurityQuestion() != null && analystLogin.getSecurityAnswer() != null) {
							if (analystLogin.getSecurityQuestion() != securityQuestion ||
									!analystLogin.getSecurityAnswer().equals(securityAnswer)) {
								response.getWriter().write("Security question and answer does not match!");
							} else {
								int affectedRows = analystLoginService.updateAnalystLoginPassword(analystLogin.getId(), newPassword);
								if (affectedRows > 0) {
									if (session != null && session.getAttribute("analyst_login") != null) {
										session.invalidate();
									}
									response.getWriter().write("SUCCESS");
								} else {
									response.getWriter().write("There is an error on changing password. Please contact administrator.");
								}
							}
						} else {
							int affectedRows = analystLoginService.updateAnalystLoginPassword(analystLogin.getId(), newPassword, securityQuestion, securityAnswer);
							if (affectedRows > 0) {
								if (session != null && session.getAttribute("analyst_login") != null) {
									session.invalidate();
								}
								response.getWriter().write("SUCCESS");
							} else {
								response.getWriter().write("There is an error on changing password. Please contact administrator.");
							}
						}
					} catch (SQLException exc) {
						exc.printStackTrace();
					}
				} else {
					response.getWriter().write("Passwords do not match!");
				}
			} else {
				response.getWriter().write("Username does not match!");
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}

}
