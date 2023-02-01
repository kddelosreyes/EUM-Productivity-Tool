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
							<h5 class="card-title"><b>${activity_name}</b></h5>
						</div>
						<div class="card-body">
							<form action="<%=request.getContextPath()%>/view" method="post">
								<input type="hidden" name="analyst_activity_id" value="${analyst_activity.id}" />
								<c:forEach items="${fields}" var="field">
									<c:choose>
										<c:when test="${field.type == 'text' or field.type == 'date'}">
											<div class="form-floating mb-3">
												<c:if test="${field.isRequired}">
													<input type="text" class="form-control" 
													  	id="${field.type}"
													  	name="${analyst_activity.id}_${field.id}_${field.type}"
													  	value="${field.value}" placeholder="${field.name}" required>
												</c:if>
												<c:if test="${not field.isRequired}">
													<input type="text" class="form-control" 
													  	id="${field.type}"
													  	name="${analyst_activity.id}_${field.id}_${field.type}"
													  	value="${field.value}" placeholder="${field.name}">
												</c:if>
											  	<label for="${analyst_activity.id}_${field.id}_${field.type}">${field.name}</label>
											</div>
										</c:when>
										<c:when test="${field.type == 'time'}">
											<div class="form-floating mb-3">
												<c:if test="${field.isRequired}">
													<input type="time" class="form-control" 
													  	id="${field.type}"
													  	name="${analyst_activity.id}_${field.id}_${field.type}"
													  	value="${field.value}" placeholder="${field.name}" required>
												</c:if>
												<c:if test="${not field.isRequired}">
													<input type="time" class="form-control" 
													  	id="${field.type}"
													  	name="${analyst_activity.id}_${field.id}_${field.type}"
													  	value="${field.value}" placeholder="${field.name}">
												</c:if>
											  	<label for="${analyst_activity.id}_${field.id}_${field.type}">${field.name}</label>
											</div>
										</c:when>
										<c:when test="${field.type == 'textarea'}">
											<div class="form-floating mb-3">
												<textarea type="text" class="form-control" 
												  	id="${field.type}"
												  	name="${analyst_activity.id}_${field.id}_${field.type}"
												  	value="${field.value}"
												  	style="height: 100px">
												 </textarea>
											  	<label for="${analyst_activity.id}_${field.id}_${field.type}">${field.name}</label>
											</div>
										</c:when>
									</c:choose>
								</c:forEach>
								<div class="row">
									<div class="col">
										<div class="form-floating mb-3">
									    	<input type="text" class="form-control"
									    		name="start_time"
									    		value="${analyst_activity.startTime.format(DateTimeFormatter.ofPattern('hh:mm a'))}" readonly>
									    	<label for="start_time">Start Time</label>
									    </div>
									</div>
								    <div class="col">
										<div class="form-floating mb-3">
											<c:choose>
												<c:when test="${not empty analyst_activity.endTime}">
													<input type="text" class="form-control"
											    		name="end_time"
											    		value="${analyst_activity.endTime.format(DateTimeFormatter.ofPattern('hh:mm a'))}" readonly>
												</c:when>
												<c:when test="${empty analyst_activity.endTime}">
													<input type="text" class="form-control"
											    		name="end_time" value="" readonly>
												</c:when>
											</c:choose>
									    	<label for="end_time">End Time</label>
									    </div>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="form-floating mb-3">
									    	<input type="text" class="form-control"
									    		name="created_date"
									    		value="${analyst_activity.createdDate.format(DateTimeFormatter.ofPattern('MMM dd, YYYY hh:mm a'))}" readonly>
									    	<label for="start_time">Created Date</label>
									    </div>
									</div>
								    <div class="col">
										<div class="form-floating mb-3">
									    	<input type="text" class="form-control"
									    		name="updated_date"
									    		value="${analyst_activity.updatedDate.format(DateTimeFormatter.ofPattern('MMM dd, YYYY hh:mm a'))}" readonly>
									    	<label for="start_time">Updated Date</label>
									    </div>
									</div>
								</div>
								<div class="form-floating">
									<button type="submit" class="btn btn-primary" name="command" value="SAVE">
										<i class="bi bi-check-lg"></i> Save
									</button>
									<c:if test="${not is_new}">
										<button type="submit" class="btn btn-danger" name="command" value="BACK">
											<i class="bi bi-arrow-left"></i> Back
										</button>
									</c:if>
								</div>
							</form>
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
		});
	</script>
</body>
</html>