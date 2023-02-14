package com.project.eum.prodtool.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AppServlet
 */
@WebServlet("/AppServlet")
public class AppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public void handleException(HttpServletRequest request, HttpServletResponse response,
			Exception exception, boolean fromServlet) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		session.setAttribute("error_message", exception.getMessage());
		
		if (fromServlet) {
			response.sendRedirect(request.getContextPath() + "/error");
		}
	}
	
}
