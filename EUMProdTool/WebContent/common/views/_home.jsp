<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.*" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<div class="card">
		<div class="card-body">
			<h5><b>Attendance Summary</b></h5>
			<div class="row">
				<div class="col-4">
					<div class="card text-center">
						<div class="card-body">
							<h3 class="card-text">
								<b>${attendance_counts.totalActiveAnalysts}</b>
							</h3>
						</div>
						<div class="card-footer bg-primary text-white">Total Active Analysts</div>
					</div>
				</div>
				<div class="col-4">
					<div class="card text-center">
						<div class="card-body">
							<h3 class="card-text">
								<b>${attendance_counts.noLoginToday}</b>
							</h3>
						</div>
						<div class="card-footer bg-danger text-white">Absent</div>
					</div>
				</div>
				<div class="col-4">
					<div class="card text-center">
						<div class="card-body">
							<h3 class="card-text">
								<b>${attendance_counts.loginToday}</b>
							</h3>
						</div>
						<div class="card-footer bg-success text-white">Present</div>
					</div>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-3">
					<div class="card text-center">
						<div class="card-body">
							<h3 class="card-text">
								<b>${attendance_counts.onLeave}</b>
							</h3>
						</div>
						<div class="card-footer bg-danger text-white">On Leave</div>
					</div>
				</div>
				<div class="col-3">
					<div class="card text-center">
						<div class="card-body">
							<h3 class="card-text">
								<b>${attendance_counts.presentOnTime}</b>
							</h3>
						</div>
						<div class="card-footer bg-success text-white">On Time</div>
					</div>
				</div>
				<div class="col-3">
					<div class="card text-center">
						<div class="card-body">
							<h3 class="card-text">
								<b>${attendance_counts.presentLate}</b>
							</h3>
						</div>
						<div class="card-footer bg-warning text-white">Late</div>
					</div>
				</div>
				<div class="col-3">
					<div class="card text-center">
						<div class="card-body">
							<h3 class="card-text">
								<b>${attendance_counts.presentOutOfSchedule}</b>
							</h3>
						</div>
						<div class="card-footer bg-danger text-white">Out of Schedule</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<div class="card">
		<div class="card-body">
			<h5><b>Shift Schedules</b></h5>
			<div class="row">
				<div class="col-12">
					To Do
				</div>
			</div>
		</div>
	</div>
	<br>
	<div class="card">
		<div class="card-body">
			<h5><b>Attendance Logs</b></h5>
			<div class="row">
				<div class="col-12">
					<c:if test="${not empty attendance_logs}">
						<ol class="list-group">
							<c:forEach items="${attendance_logs}" var="attendanceLog">
								<li class="list-group-item d-flex justify-content-between align-items-start">
									<div class="ms-2 me-auto">
										<small>${attendanceLog.time.format(DateTimeFormatter.ofPattern("hh:mm a"))}</small>
										<div class="fw-bold">
											${attendanceLog.name} has already logged.
										</div>
								    </div>
								    <c:if test="${attendanceLog.type == 'IN'}">
								    	<span class="badge bg-success rounded-pill">${attendanceLog.type}</span>
								    </c:if>
								    <c:if test="${attendanceLog.type == 'OUT'}">
								    	<span class="badge bg-danger rounded-pill">${attendanceLog.type}</span>
								    </c:if>
								</li>
							</c:forEach>
						</ol>
					</c:if>
					<c:if test="${empty attendance_logs}">
						<div class="text-center">
							<small>No attendance logs for today</small>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<br>
	<div class="card">
		<div class="card-body">
			<h5><b>Activity Logs</b></h5>
			<div class="row">
				<div class="col-12">
					<c:if test="${not empty activity_logs}">
						<ol class="list-group">
							<c:forEach items="${activity_logs}" var="activityLog">
								<li class="list-group-item d-flex justify-content-between align-items-start">
									<div class="ms-2 me-auto">
										<small>${activityLog.time.format(DateTimeFormatter.ofPattern("hh:mm a"))}</small>
										<div class="fw-bold">
											${activityLog.name} has ${fn:toLowerCase(activityLog.type)}ed ${activityLog.activity}.
										</div>
								    </div>
								    <c:if test="${activityLog.type == 'START'}">
								    	<span class="badge bg-success rounded-pill">${activityLog.type}</span>
								    </c:if>
								    <c:if test="${activityLog.type == 'END'}">
								    	<span class="badge bg-danger rounded-pill">${activityLog.type}</span>
								    </c:if>
								</li>
							</c:forEach>
						</ol>
					</c:if>
					<c:if test="${empty activity_logs}">
						<div class="text-center">
							<small>No activity logs for today</small>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>

</html>