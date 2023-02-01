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
<link rel="stylesheet" href="common/css/jquery.dataTables.css">
<link rel="stylesheet" href="common/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="common/css/styles.css">
<link rel="stylesheet" href="common/css/sticky-footer-navbar.css">
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
				<div class="col-9">
					<div class="card">
						<h5 class="card-header"><b>Activities</b></h5>
						<div class="card-body d-flex">
							<form action="<%=request.getContextPath()%>/home" method="post">
							<input type="hidden" name="analyst_id" value="${analyst.id}" />
							<div class="input-group">
								<select	class="form-select" name="activity_id" style="width: fit-content; display: inline">
									<c:forEach items="${activities}" var="activity">
										<option value="${activity.id}">${activity.name}</option>
									</c:forEach>
								</select>
								<c:choose>
									<c:when test="${is_any_pending or not empty attendance.timeOut}">
										<button type="submit" class="btn btn-outline-primary" disabled name="command" value="START_ACTIVITY">
											<i class="bi bi-play"></i> Start Activity
										</button>
									</c:when>
									<c:otherwise>
										<button type="submit" class="btn btn-outline-primary" name="command" value="START_ACTIVITY">
											<i class="bi bi-play"></i> Start Activity
										</button>
									</c:otherwise>
								</c:choose>
								<c:if test="${(empty attendance.timeOut) and not (has_pending)}">
									<input type="hidden" name="attendance_id" value="${attendance.id}" />
									<button type="submit" class="btn btn-outline-danger" name="command" value="TIME_OUT">
										<i class="bi bi-hourglass-bottom"></i> Clock Out
									</button>
								</c:if>
							</div>
						</form>
						</div>
					</div>
					<!--<div class="d-flex align-items-center p-3 my-3 text-white bg-purple rounded shadow-sm">-->
					</div>
				<div class="col"></div>
			</div>
			<br>
			<div class="row">
				<div class="col"></div>
				<div class="col-9">
					<div class="card">
						<h5 class="card-header"><b>Tasks List</b></h5>
						<div class="card-body">
							<table id="analyst_activity" class="table table-striped" style="width:100%;">
								<thead>
						            <tr>
						                <th>Activity</th>
						                <th>Productive</th>
						                <th>Other Productive</th>
						                <th>Non-Productive</th>
						                <th>Minutes</th>
						                <th>Actions</th>
						            </tr>
						        </thead>
						        <tbody>
						        	<c:forEach items="${analyst_activities}" var="analystActivity">
						        		<c:url var="view_details" value="home">
											<c:param name="command" value="VIEW_ACTIVITY" />
											<c:param name="analyst_activity_id" value="${analystActivity.id}" />
										</c:url>
						        		<c:url var="end_link" value="home">
											<c:param name="command" value="END_ACTIVITY" />
											<c:param name="analyst_activity_id" value="${analystActivity.id}" />
										</c:url>
						                <tr style="border-top-left-radius: 10px;">
						                	<td>${analystActivity.activity.name}</td>
											<c:choose>
											    <c:when test="${analystActivity.activity.activityTypeId == 1}">
											    	<td>${analystActivity.startTime.format(DateTimeFormatter.ofPattern("hh:mm a"))} - ${analystActivity.endTime.format(DateTimeFormatter.ofPattern("hh:mm a"))}</td>
											    	<td></td>
											    	<td></td>
											    </c:when>
											    <c:when test="${analystActivity.activity.activityTypeId == 2}">
											    	<td></td>
											    	<td>${analystActivity.startTime.format(DateTimeFormatter.ofPattern("hh:mm a"))} - ${analystActivity.endTime.format(DateTimeFormatter.ofPattern("hh:mm a"))}</td>
											    	<td></td>
											    </c:when>
											    <c:otherwise>
											    	<td></td>
											    	<td></td>
											    	<td>${analystActivity.startTime.format(DateTimeFormatter.ofPattern("hh:mm a"))} - ${analystActivity.endTime.format(DateTimeFormatter.ofPattern("hh:mm a"))}</td>
											    </c:otherwise>
											</c:choose>
											<td>${analystActivity.minutes}</td>
											<td>
												<div class="input-group">
													<c:if test="${analystActivity.activity.activityTypeId == 1}">
														<a href="${view_details}" class="btn btn-outline-success btn-sm" role="button" aria-pressed="true">View</a>
													</c:if>
													<c:if test="${empty analystActivity.endTime}">
														<a href="${end_link}" class="btn btn-outline-danger btn-sm" role="button" aria-pressed="true">End</a>
													</c:if>
												</div>
											</td>
										</tr>
									</c:forEach>
						        </tbody>
						        <tfoot>
					            <tr>
					                <th>Activity</th>
						            <th>Productive</th>
						            <th>Other Productive</th>
						            <th>Non-Productive</th>
						            <th>Minutes</th>
						            <th>Actions</th>
					            </tr>
			        			</tfoot>
							</table>
						</div>
					</div>
				</div>
				<div class="col"></div>
			</div>
			<br>
			<div class="row">
				<div class="col"></div>
				<div class="col-9">
					<div class="card">
						<h5 class="card-header"><b>Summary</b></h5>
						<div class="card-body">
							<div class="row">
								<div class="col-4">
									<div class="alert alert-secondary" role="alert">
										<small>Shift Schedule</small><br>
										<h5>${shift_schedule.startTime.format(DateTimeFormatter.ofPattern("hh:mm a"))} - ${shift_schedule.endTime.format(DateTimeFormatter.ofPattern("hh:mm a"))}</h5>
									</div>
								</div>
								<div class="col">
									<div class="alert alert-success" role="alert">
										<small>Clock In</small><br>
										<h5>${attendance.timeIn.format(DateTimeFormatter.ofPattern("hh:mm a"))}</h5>
									</div>
								</div>
								<div class="col">
									<div class="alert alert-danger" role="alert">
										<small>Clock Out</small><br>
										<c:if test="${empty attendance.timeOut}">
											<h5>-</h5>
										</c:if>
										<c:if test="${not empty attendance.timeOut}">
											<h5>${attendance.timeOut.format(DateTimeFormatter.ofPattern("hh:mm a"))}</h5>
										</c:if>
									</div>
								</div>
								<div class="col-3"></div>
							</div>
							<div class="row">
								<div class="col-12">
									<div class="card text-center">
										<div class="card-body">
											<h3 class="card-text">
												<b>${time_summary.totalWorkString}</b>
											</h3>
										</div>
										<div class="card-footer bg-primary text-white">Total Work</div>
									</div>
								</div>
							</div>
							<br>
							<div class="row">
								<div class="col-4">
									<div class="card text-center">
										<div class="card-body">
											<h3 class="card-text">
												<b>${time_summary.totalProductiveString}</b>
											</h3>
										</div>
										<div class="card-footer bg-success text-white">Productive</div>
									</div>
								</div>
								<div class="col-4">
									<div class="card text-center">
										<div class="card-body">
											<h3 class="card-text">
												<b>${time_summary.totalNeutralString}</b>
											</h3>
										</div>
										<div class="card-footer bg-warning text-dark">Other
											Productive</div>
									</div>
								</div>
								<div class="col-4">
									<div class="card text-center">
										<div class="card-body">
											<h3 class="card-text">
												<b>${time_summary.totalNonProductiveString}</b>
											</h3>
										</div>
										<div class="card-footer bg-danger text-white">Non-Productive</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col"></div>
			</div>
			<br>
		</div>
	<main>

	<footer class="footer text-dark">
	    <jsp:include page="footer.jsp" />
	</footer>
	
	<script src="common/js/jquery-3.5.1.js"></script>
	<script src="common/js/bootstrap.min.js"></script>
	<script src="common/js/dataTables.bootstrap5.min.js"></script>
	<script src="common/js/jquery.dataTables.min.js"></script>
	
	<script>
	$(document).ready(function () {
	    $('#analyst_activity').DataTable({
	    	"ordering" : false,
	    	"bFilter" : false
	    });
	});
	</script>
</body>
</html>