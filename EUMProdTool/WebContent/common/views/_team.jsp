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
					<h5><b>Teams</b></h5>
				</div>
				<div class="col">
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create_team"
						style="position: absolute; right: 16px;">
						<i class="bi bi-plus-lg"></i> New
					</button>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-12">
					<table id="teams_table" class="table table-striped nowrap" style="width:100%">
						<thead>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Type</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="count" scope="page" value="${1}"/>
							<c:forEach items="${teams}" var="team">
								<tr>
									<td>${count}</td>
									<td>${team.name}</td>
									<td>${team.type}</td>
								</tr>
								<c:set var="count" value="${count + 1}" scope="page"/>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Type</th>
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
					<h5><b>Analyst - Team Mapping</b></h5>
				</div>
				<div class="col">
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create_analyst_team"
						style="position: absolute; right: 16px;">
						<i class="bi bi-plus-lg"></i> New
					</button>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-12">
					<table id="analyst_team_table" class="table table-striped nowrap" style="width:100%">
						<thead>
							<tr>
								<th>#</th>
								<th>Analyst</th>
								<th>Role</th>
								<th>Teams</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="count" scope="page" value="${1}"/>
							<c:forEach items="${analyst_teams}" var="analystTeam">
								<tr>
									<td>${count}</td>
									<td>${analystTeam.analystName}</td>
									<td>${analystTeam.role}</td>
									<td>${analystTeam.teamName}</td>
									<c:set var="count" value="${count + 1}" scope="page"/>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>#</th>
								<th>Analyst</th>
								<th>Role</th>
								<th>Teams</th>
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
					<h5><b>Team - Activity Mapping</b></h5>
				</div>
				<div class="col">
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create_team_activity"
						style="position: absolute; right: 16px;">
						<i class="bi bi-plus-lg"></i> New
					</button>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-12">
					<table id="team_activity_table" class="table table-striped nowrap" style="width:100%">
						<thead>
							<tr>
								<th>#</th>
								<th>Team</th>
								<th>Activity</th>
								<th>Type</th>
								<th>Date Created</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="count" scope="page" value="${1}"/>
							<c:forEach items="${team_activities}" var="teamActivity">
								<tr>
									<td>${count}</td>
									<td>${teamActivity.teamName}</td>
									<td>${teamActivity.activityName}</td>
									<td>${teamActivity.type}</td>
									<td>${teamActivity.createdDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm a"))}</td>
									<c:set var="count" value="${count + 1}" scope="page"/>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>#</th>
								<th>Team</th>
								<th>Activity</th>
								<th>Type</th>
								<th>Date Created</th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<div class="modal fade" id="create_team" tabindex="-1" role="dialog" aria-labelledby="create_team" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Create Team</h5>
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-12">
							<form id="create_team_form" action="<%=request.getContextPath()%>/manage" method="post">
								<input type="hidden" name="command" value="CREATE_TEAM" />
								<div class="form-floating mb-3">
									<input type="text" class="form-control" id="create_team_team_name"
										name="create_team_team_name" placeholder="Name" required/>
									<label for="create_team_team_name">Name</label>
								</div>
								<div class="form-floating mb-3">
									<select	class="form-select" id="create_team_type" 
										name="create_team_type" style="display: inline">
										<option value="main">Main</option>
										<option value="sub">Sub</option>
									</select>
									<label for="create_team_type">Type</label>
								</div>
								<div class="form-floating mb-3">
									<button type="submit" class="btn btn-primary" id="save_team" value="SAVE"><i class="bi bi-check-lg"></i> Save</button>
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal"><i class="bi bi-x-lg"></i> Close</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="create_analyst_team" tabindex="-1" role="dialog" aria-labelledby="create_analyst_team" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Create Analyst - Team Mapping</h5>
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-12">
							<form id="create_analyst_team_form" action="<%=request.getContextPath()%>/manage" method="post">
								<input type="hidden" name="command" value="CREATE_ANALYST_TEAM" />
								<div class="form-floating mb-3">
									<select	class="form-select" id="analyst_team_analyst" 
										name="analyst_team_analyst" style="display: inline">
										<c:forEach items="${active_analysts}" var="analyst">
											<option value="${analyst.id}">${analyst.firstName} ${analyst.lastName}</option>
										</c:forEach>
									</select>
									<label for="analyst_team_analyst">Analyst</label>
								</div>
								<div class="form-floating mb-3">
									<select	class="form-select" id="analyst_team_team" 
										name="analyst_team_team" style="display: inline">
										<c:forEach items="${teams}" var="team">
											<option value="${team.id}">${team.name}</option>
										</c:forEach>
									</select>
									<label for="analyst_team_team">Team</label>
								</div>
								<div class="form-floating mb-3">
									<button type="submit" class="btn btn-primary" id="save_analyst_team" value="SAVE"><i class="bi bi-check-lg"></i> Save</button>
									<button type="button" class="btn btn-danger" data-bs-dismiss="modal"><i class="bi bi-x-lg"></i> Close</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="create_team_activity" tabindex="-1" role="dialog" aria-labelledby="create_team_activity" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Create Team - Activity Mapping</h5>
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-12">
							<form id="field_form" action="<%=request.getContextPath()%>/manage" method="post">
								<input type="hidden" name="command" value="CREATE_TEAM_ACTIVITY" />
								<div class="form-floating mb-3">
									<select	class="form-select" id="team_activity_team" 
										name="team_activity_team" style="display: inline">
										<c:forEach items="${teams}" var="team">
											<option value="${team.id}">${team.name}</option>
										</c:forEach>
									</select>
									<label for="team_activity_team">Team</label>
								</div>
								<div class="form-floating mb-3">
									<select	class="form-select" id="team_activity_activity" 
										name="team_activity_activity" style="display: inline">
										<c:forEach items="${activities}" var="activity">
											<option value="${activity.id}">${activity.name}</option>
										</c:forEach>
									</select>
									<label for="team_activity_activity">Activity</label>
								</div>
								<div class="form-floating mb-3">
									<button type="submit" class="btn btn-primary" id="save_team_activity" value="SAVE"><i class="bi bi-check-lg"></i> Save</button>
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