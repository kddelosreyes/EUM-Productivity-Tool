<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.*" %>
<%@ page errorPage="error.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Expires", "0");
	response.setDateHeader("Expires", -1);
	
	if (session == null || session.getAttribute("analyst") == null) {
		response.sendRedirect(request.getContextPath() + "/");
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EUM Productivity Tool</title>

<link rel="stylesheet" href="common/css/bootstrap.min.css">
<link rel="stylesheet" href="common/css/styles.css">
<link rel="stylesheet" href="common/css/sticky-footer-navbar.css">
<link rel="stylesheet" href="common/css/jquery-ui.css">
<link rel="stylesheet" href="common/css/bootstrap-icons.css">
</head>
<body class="d-flex flex-column h-100 bg-purple">
	<header>
		<jsp:include page="header.jsp" />
	</header>
	
	<main class="flex-shrink-0">
		<br>
		<div class="container">
			<div class="row">
				<div class="col"></div>
				<div class="col">
					<div class="card">
						<div class="card-header">
							<h5 class="card-title"><b>${title}</b></h5>
						</div>
						<div class="card-body">
							<c:if test="${view == 'ANALYST_SHIFT_SCHEDULE'}">
								<form action="<%=request.getContextPath()%>/edit" method="post">
									<input type="hidden" name="command" value="SAVE_ANALYST_SHIFT_SCHEDULE" />
									<div class="form-floating mb-3">
									   	<input type="text" class="form-control" name="analyst_shift_schedule_id"
									   		value="${entity.id}" readonly>
									   	<label for="analyst_shift_schedule_id">Id</label>
									</div>
									<div class="form-floating mb-3">
									   	<input type="text" class="form-control" name="analyst_shift_schedule_analyst"
									   		value="${entity.analyst.name}" readonly>
									   	<label for="analyst_shift_schedule_analyst">Analyst</label>
								    </div>
								    <div class="form-floating mb-3">
								    	<input type="text" class="form-control" name="old_shift_schedule"
								    		value="${entity.shiftSchedule.name} (${entity.shiftSchedule.startTime} - ${entity.shiftSchedule.endTime})" readonly>
								    	<label for="old_shift_schedule">Old Shift Schedule</label>
								    </div>
								    <div class="form-floating mb-3">
								    	<select	class="form-select" id="analyst_shift_schedule_shift" 
											name="analyst_shift_schedule_shift" style="display: inline">
											<c:forEach items="${shift_schedules}" var="shiftSchedule">
												<option value="${shiftSchedule.id}">${shiftSchedule.name} (${shiftSchedule.startTime} - ${shiftSchedule.endTime})</option>
											</c:forEach>
										</select>
										<label for="analyst_shift_schedule_shift">New Shift Schedule</label>
								    </div>
								    <div class="form-floating mb-3">
								    	<input type="text" class="form-control" name="analyst_shift_schedule_date"
								    		value="${entity.fromDate.format(DateTimeFormatter.ofPattern('MMM dd, YYYY'))}" readonly>
								    	<label for="analyst_shift_schedule_date">Date</label>
								    </div>
								    <div class="row">
										<div class="col">
											<div class="form-floating mb-3">
										    	<input type="text" class="form-control"
										    		name="created_date"
										    		value="${entity.createdDate.format(DateTimeFormatter.ofPattern('MMM dd, YYYY hh:mm a'))}" readonly>
										    	<label for="created_date">Created Date</label>
										    </div>
										</div>
									    <div class="col">
											<div class="form-floating mb-3">
										    	<input type="text" class="form-control"
										    		name="updated_date"
										    		value="${entity.updatedDate.format(DateTimeFormatter.ofPattern('MMM dd, YYYY hh:mm a'))}" readonly>
										    	<label for="updated_date">Updated Date</label>
										    </div>
										</div>
									</div>
									<div class="form-floating">
										<button type="submit" class="btn btn-primary" name="command" value="SAVE">
											<i class="bi bi-check-lg"></i> Save
										</button>
										<button type="button" class="btn btn-danger" id="back" name="command" value="BACK">
											<i class="bi bi-arrow-left"></i> Back
										</button>
									</div>
								</form>
							</c:if>
						</div>
					</div>
				</div>
				<div class="col"></div>
			</div>
			<br>
		</div>
	</main>
	
	<footer class="footer text-dark">
	    <jsp:include page="footer.jsp" />
	</footer>
	
	<script src="common/js/jquery-3.5.1.js"></script>
	<script src="common/js/bootstrap.bundle.min.js"></script>
	<script src="common/js/bootstrap.min.js"></script>
	<script src="common/js/jquery-ui.js"></script>
	
	<script>
		$(function() {
			$( "#date" ).datepicker();
			
			$('#back').click(function() {
				window.location.href = "${pageContext.request.contextPath}/manage";
			});
		});
	</script>
</body>
</html>