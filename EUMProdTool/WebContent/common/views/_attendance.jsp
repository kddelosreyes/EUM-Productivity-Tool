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
			<h5><b>Attendance / Attendance</b></h5>
			<br>
			<table id="attendance_table" class="table table-striped" style="width:100%">
				<thead>
					<tr>
						<th>Name</th>
						<th>Role</th>
						<th>Time In/Time Out</th>
						<th>Shift Schedule</th>
						<th>Remarks</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${analystDetails}" var="analystDetail">
						<tr>
							<td>${analystDetail.analyst.firstName} ${analystDetail.analyst.lastName}</td>
							<td>${analystDetail.analyst.role}</td>
							<td>${analystDetail.attendance.timeIn.format(DateTimeFormatter.ofPattern("hh:mm a"))} / ${analystDetail.attendance.timeOut.format(DateTimeFormatter.ofPattern("hh:mm a"))}</td>
							<td>${analystDetail.shiftSchedule.name}</td>
							<td>
								<c:choose>
									<c:when test="${analystDetail.remarks == 'No Schedule'}">
										<span class="label rounded-pill bg-danger text-white">
									</c:when>
									<c:when test="${analystDetail.remarks == 'No Login'}">
										<span class="label rounded-pill bg-danger text-white">
									</c:when>
									<c:when test="${analystDetail.remarks == 'Late'}">
										<span class="label rounded-pill bg-warning text-white">
									</c:when>
									<c:when test="${analystDetail.remarks == 'Present'}">
										<span class="label rounded-pill bg-success text-white">
									</c:when>
									<c:when test="${analystDetail.remarks == 'Out of Schedule'}">
										<span class="label rounded-pill bg-danger text-white">
									</c:when>
								</c:choose>
								${analystDetail.remarks}
								</span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<th>Name</th>
						<th>Role</th>
						<th>Time In/Time Out</th>
						<th>Shift Schedule</th>
						<th>Remarks</th>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>

</html>