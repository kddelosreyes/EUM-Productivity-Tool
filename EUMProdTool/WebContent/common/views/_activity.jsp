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
					<h5><b>Activity Types</b></h5>
				</div>
				<div class="col">
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create_activity_type"
						style="position: absolute; right: 16px;">
						<i class="bi bi-plus-lg"></i> New
					</button>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-12">
					<table id="activity_types_table" class="table table-striped nowrap" style="width:100%">
						<thead>
							<tr>
								<th>Id</th>
								<th>Name</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${activity_types}" var="activityType">
								<tr>
									<td>${activityType.id}</td>
									<td>${activityType.type}</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>Id</th>
								<th>Name</th>
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
					<h5><b>Activities</b></h5>
				</div>
				<div class="col">
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create_activity"
						style="position: absolute; right: 16px;">
						<i class="bi bi-plus-lg"></i> New
					</button>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-12">
					<table id="activities_table" class="table table-striped nowrap" style="width:100%">
						<thead>
							<tr>
								<th>Id</th>
								<th>Name</th>
								<th>Type</th>
								<th>Date Created</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${activities}" var="activity">
								<tr>
									<td>${activity.id}</td>
									<td>${activity.name}</td>
									<td>${activity.activityType.type}</td>
									<td>${activity.createdDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm a"))}</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>Id</th>
								<th>Name</th>
								<th>Type</th>
								<th>Date Created</th>
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
					<h5><b>Fields</b></h5>
				</div>
				<div class="col">
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create_field"
						style="position: absolute; right: 16px;">
						<i class="bi bi-plus-lg"></i> New
					</button>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-12">
					<table id="fields_table" class="table table-striped nowrap" style="width:100%">
						<thead>
							<tr>
								<th>Id</th>
								<th>Name</th>
								<th>Type</th>
								<th>Required</th>
								<th>Date Created</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${activity_fields}" var="field">
								<tr>
									<td>${field.id}</td>
									<td>${field.name}</td>
									<td>${field.type}</td>
									<td>${field.isRequired ? "Yes" : "No"}</td>
									<td>${field.createdDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm a"))}</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>Id</th>
								<th>Name</th>
								<th>Type</th>
								<th>Required</th>
								<th>Date Created</th>
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
					<h5><b>Activity - Field Mapping</b></h5>
				</div>
				<div class="col">
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create_activity_field_mapping"
						style="position: absolute; right: 16px;">
						<i class="bi bi-plus-lg"></i> New
					</button>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-12">
					<table id="activity_field_map_table" class="table table-striped nowrap" style="width:100%">
						<thead>
							<tr>
								<th>Id</th>
								<th>Activity</th>
								<th>Field Name</th>
								<th>Date Created</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${activity_field_map}" var="activity_field">
								<tr>
									<td>${activity_field.id}</td>
									<td>${activity_field.activityName}</td>
									<td>${activity_field.fieldName}</td>
									<td>${activity_field.createdDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm a"))}</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>Id</th>
								<th>Activity</th>
								<th>Field Name</th>
								<th>Date Created</th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<div class="modal fade" id="create_activity_type" tabindex="-1" role="dialog" aria-labelledby="create_activity_type" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Create Activity Type</h5>
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-12">
							<form id="activity_type_form" action="<%=request.getContextPath()%>/#" method="post">
								<input type="hidden" name="command" value="CREATE_ACTIVITY_TYPE" />
								<div class="form-floating mb-3">
									<input type="text" class="form-control"
										name="activity_type_name" placeholder="Name" required/>
									<label for="activity_type_name">Name</label>
								</div>
								<div class="form-floating mb-3">
									<button type="button" class="btn btn-primary" id="save_activity_type" value="SAVE"><i class="bi bi-check-lg"></i> Save</button>
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal"><i class="bi bi-x-lg"></i> Close</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="create_activity" tabindex="-1" role="dialog" aria-labelledby="create_activity" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Create Activity</h5>
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-12">
							<form id="activity_type_form" action="<%=request.getContextPath()%>/#" method="post">
								<input type="hidden" name="command" value="CREATE_ACTIVITY" />
								<div class="form-floating mb-3">
									<input type="text" class="form-control"
										name="activity_name" placeholder="Name" required/>
									<label for="activity_name">Name</label>
								</div>
								<div class="form-floating mb-3">
									<select	class="form-select" name="activity_type" style="display: inline">
										<c:forEach items="${activity_types}" var="activityType">
											<option value="${activityType.id}">${activityType.type}</option>
										</c:forEach>
									</select>
									<label for="activity_type">Type</label>
								</div>
								<div class="form-floating mb-3">
									<button type="button" class="btn btn-primary" id="save_activity" value="SAVE"><i class="bi bi-check-lg"></i> Save</button>
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal"><i class="bi bi-x-lg"></i> Close</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="create_field" tabindex="-1" role="dialog" aria-labelledby="create_field" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Create Field</h5>
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-12">
							<form id="field_form" action="<%=request.getContextPath()%>/#" method="post">
								<input type="hidden" name="command" value="CREATE_FIELD" />
								<div class="form-floating mb-3">
									<input type="text" class="form-control"
										name="field_name" placeholder="Name">
									<label for="field_name">Name</label>
								</div>
								<div class="form-floating mb-3">
									<select	class="form-select" name="field_type" style="display: inline">
										<option value="text">Text</option>
										<option value="date">Date</option>
										<option value="time">Time</option>
									</select>
									<label for="field_type">Type</label>
								</div>
								<small class="text-muted">Required</small>
								<br>
								<div class="input-group">
									<input type="radio" class="btn-check btn-outline-secondary" name="is_required" id="is_required_yes" values="1" autocomplete="off" checked>
									<label class="btn btn-outline-secondary" for="is_required_yes">Yes</label>
									
									<input type="radio" class="btn-check" name="is_required" id="is_required_no" value="0" autocomplete="off">
									<label class="btn btn-outline-secondary" for="is_required_no">No</label>
								</div>
								<br>
								<div class="form-floating mb-3">
									<button type="button" class="btn btn-primary" id="save_field" value="SAVE"><i class="bi bi-check-lg"></i> Save</button>
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal"><i class="bi bi-x-lg"></i> Close</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="create_activity_field_mapping" tabindex="-1" role="dialog" aria-labelledby="create_activity_field_mapping" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Create Activity - Field Map</h5>
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-12">
							<form id="field_form" action="<%=request.getContextPath()%>/#" method="post">
								<input type="hidden" name="command" value="CREATE_ACTIVITY_FIELD_MAP" />
								<div class="form-floating mb-3">
									<select	class="form-select" name="map_activity_value" style="display: inline">
										<c:forEach items="${activities}" var="activity">
											<option value="${activity.id}">${activity.name}</option>
										</c:forEach>
									</select>
									<label for="map_activity_value">Activity</label>
								</div>
								<div class="form-floating mb-3">
									<select	class="form-select" name="map_field_value" style="display: inline">
										<c:forEach items="${activity_fields}" var="field">
											<option value="${field.id}">${field.name}</option>
										</c:forEach>
									</select>
									<label for="map_field_value">Field</label>
								</div>
								<div class="form-floating mb-3">
									<button type="button" class="btn btn-primary" id="save_field" value="SAVE"><i class="bi bi-check-lg"></i> Save</button>
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