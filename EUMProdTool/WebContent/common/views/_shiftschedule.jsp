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
			<div class="row">
				<div class="col">
					<h5><b>Shift Schedules</b></h5>
				</div>
				<div class="col text-end">
					<form action="<%=request.getContextPath()%>/manage" method="post">
						<input type="hidden" name="command" value="DOWNLOAD_SHIFT_SCHEDULE" />
						<button type="submit" class="btn btn-primary">
							<i class="bi bi-download"></i> Download
						</button>
						<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create_shift_schedule">
							<i class="bi bi-plus-lg"></i> New
						</button>
					</form>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-12">
					<table id="shift_schedules_table" class="table table-striped" style="width:100%">
						<thead>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Start Time</th>
								<th>End Time</th>
								<th>Created Date</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="count" scope="page" value="${1}"/>
							<c:forEach items="${shift_schedules}" var="shiftSchedule">
								<tr>
									<td>${count}</td>
									<td>${shiftSchedule.name}</td>
									<td>${shiftSchedule.startTime}</td>
									<td>${shiftSchedule.endTime}</td>
									<td>${shiftSchedule.createdDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm a"))}</td>
								</tr>
								<c:set var="count" value="${count + 1}" scope="page"/>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Start Time</th>
								<th>End Time</th>
								<th>Created Date</th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>
	<br>
	<div class="card">
		<div class="card-body">
			<div class="row">
				<div class="col">
					<h5><b>Analyst Shift Schedules</b></h5>
				</div>
				<div class="col text-end">
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#upload_analyst_shift_schedule">
						<i class="bi bi-upload"></i> Upload
					</button>
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create_analyst_shift_schedule">
						<i class="bi bi-plus-lg"></i> New
					</button>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-auto">
					<div class="form-floating mb-3">
						<select	class="form-select" id="analyst_shift_schedule_analyst_selector" 
							name="analyst_shift_schedule_analyst_selector" style="display: inline">
							<option value="-1"></option>
							<c:forEach items="${active_analysts}" var="analyst">
								<option value="${analyst.id}">${analyst.firstName} ${analyst.lastName}</option>
							</c:forEach>
						</select>
						<label for="analyst_shift_schedule_analyst_selector">Analyst</label>
					</div>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-auto">
					<div class="row mb-3">
						<h6><strong>Shift Calendar</strong></h6>
					</div>
					<div id="shift_schedules"></div>
				</div>
				<div class="col">
					<table id="analyst_shift_schedules_table" class="table table-striped nowrap">
						<thead>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Date</th>
								<th>Shift Schedule</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
						<tfoot>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Date</th>
								<th>Shift Schedule</th>
								<th>Actions</th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>
	<br>
	
	<!-- Modals -->
	<div class="modal fade" id="create_shift_schedule" tabindex="-1" role="dialog" aria-labelledby="create_shift_schedule" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Create Shift Schedule</h5>
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-12">
							<form id="create_team_form" action="<%=request.getContextPath()%>/manage" method="post">
								<input type="hidden" name="command" value="CREATE_SHIFT_SCHEDULE" />
								<div class="form-floating mb-3">
									<input type="text" class="form-control" id="shift_schedule_name"
										name="shift_schedule_name" placeholder="Name" required/>
									<label for="shift_schedule_name">Name</label>
								</div>
								<div class="row">
									<div class="col">
										<div class="form-floating mb-3">
											<input type="time" class="form-control" id="shift_schedule_start_time"
												name="shift_schedule_start_time" placeholder="Start Time" required/>
											<label for="shift_schedule_start_time">Start Time</label>
										</div>
									</div>
									<div class="col">
										<div class="form-floating mb-3">
											<input type="time" class="form-control" id="shift_schedule_end_time"
												name="shift_schedule_end_time" placeholder="End Time" required/>
											<label for="shift_schedule_end_time">End Time</label>
										</div>
									</div>
								</div>
								<div class="form-check form-switch">
									<input type="checkbox" class="form-check-input" id="shift_schedule_night_shift"
										name="shift_schedule_night_shift" value="1" placeholder="Is Night Shift?" onclick="return false;">
									<label class="form-check-label" for="shift_schedule_night_shift">Is Night Shift?</label>
								</div>
								<br>
								<div class="form-floating mb-3">
									<button type="submit" class="btn btn-primary" id="save_shift_schedule" value="SAVE"><i class="bi bi-check-lg"></i> Save</button>
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal"><i class="bi bi-x-lg"></i> Close</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="create_analyst_shift_schedule" tabindex="-1" role="dialog" aria-labelledby="create_analyst_shift_schedule" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Create Analyst - Shift Schedule Mapping</h5>
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-12">
							<form id="create_analyst_team_form" action="<%=request.getContextPath()%>/manage" method="post" autocomplete="off">
								<input type="hidden" name="command" value="CREATE_ANALYST_SHIFT_SCHEDULE" />
								<div class="form-floating mb-3">
									<select	class="form-select" id="analyst_shift_schedule_analyst" 
										name="analyst_shift_schedule_analyst" style="display: inline">
										<option value="-1"></option>
										<c:forEach items="${active_analysts}" var="analyst">
											<option value="${analyst.id}">${analyst.firstName} ${analyst.lastName}</option>
										</c:forEach>
									</select>
									<label for="analyst_shift_schedule_analyst">Analyst</label>
								</div>
								<div class="form-floating mb-3">
									<select	class="form-select" id="analyst_shift_schedule_shift" 
										name="analyst_shift_schedule_shift" style="display: inline">
										<option value="-1"></option>
										<c:forEach items="${shift_schedules}" var="shiftSchedule">
											<option value="${shiftSchedule.id}">${shiftSchedule.name} (${shiftSchedule.startTime} - ${shiftSchedule.endTime})</option>
										</c:forEach>
									</select>
									<label for="analyst_shift_schedule_shift">Shift Schedule</label>
								</div>
								<div class="row">
									<div class="col">
										<div class="form-floating mb-3">
											<input type="text" class="form-control" id="analyst_shift_schedule_start_date"
												name="analyst_shift_schedule_start_date" placeholder="Start Date" required/>
											<label for="analyst_shift_schedule_start_date">Start Date</label>
										</div>
									</div>
								</div>
								<div class="form-floating mb-3">
									<button type="submit" class="btn btn-primary" id="save_analyst_shift_schedule" value="SAVE"><i class="bi bi-check-lg"></i> Save</button>
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal"><i class="bi bi-x-lg"></i> Close</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="upload_analyst_shift_schedule" tabindex="-1" role="dialog" aria-labelledby="upload_analyst_shift_schedule" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Upload Analyst - Shift Schedule Mapping</h5>
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-12">
							<form action="<%=request.getContextPath()%>/manage" method="post" enctype="multipart/form-data" autocomplete="off">
								<input type="hidden" name="command" value="UPLOAD_ANALYST_SHIFT_SCHEDULE" />
								<div class="input-group">
									<input type="file" class="form-control" name="file_input" id="file_input" aria-describedby="inputGroupFileAddon04" aria-label="Upload">
									<button class="btn btn-primary" type="submit" id="inputGroupFileAddon04">Upload</button>
								</div>
								<br>
								<div class="form-floating mb-3">
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal"><i class="bi bi-x-lg"></i> Close</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</html>