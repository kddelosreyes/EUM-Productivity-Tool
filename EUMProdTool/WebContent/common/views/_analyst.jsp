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
					<h5><b>Analysts</b></h5>
				</div>
				<div class="col text-end">
					<form action="<%=request.getContextPath()%>/manage" method="post">
						<input type="hidden" name="command" value="DOWNLOAD_ANALYSTS" />
						<button type="submit" class="btn btn-primary">
							<i class="bi bi-download"></i> Download
						</button>
						<button type="button" class="btn btn-primary create-new-analyst" data-bs-toggle="modal" data-bs-target="#create_new_analyst">
							<i class="bi bi-plus-lg"></i> New
						</button>
					</form>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-12">
					<table id="analysts_table" class="table table-striped nowrap" style="width:100%">
						<thead>
							<tr>
								<th>First Name</th>
								<th>MI</th>
								<th>Last Name</th>
								<th>Role</th>
								<th>Email</th>
								<th>Status</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${analysts}" var="analyst">
								<c:url var="activate_analyst" value="manage">
									<c:param name="command" value="ACTIVATE_ANALYST" />
									<c:param name="analyst_id" value="${analyst.uuid}" />
								</c:url>
								<c:url var="deactivate_analyst" value="manage">
									<c:param name="command" value="DEACTIVATE_ANALYST" />
									<c:param name="analyst_id" value="${analyst.uuid}" />
								</c:url>
								<tr>
									<td>${analyst.firstName}</td>
									<td>${analyst.middleName}</td>
									<td>${analyst.lastName}</td>
									<td>${analyst.role}</td>
									<td>${analyst.analystLogin.username}</td>
									<td>
										<c:if test="${analyst.isActive}">
											<span class="badge bg-success">Active</span>
										</c:if>
										<c:if test="${not analyst.isActive}">
											<span class="badge bg-danger">Inactive</span>
										</c:if>
									</td>
									<td>
										<button type="button" class="btn btn-outline-success btn-sm edit_analyst" name="edit_analyst" role="button" aria-pressed="true" data-toggle="tooltip" title="Edit" data-bs-toggle="modal" data-bs-target="#create_new_analyst"><i class="bi bi-pencil"></i></button>
										<c:if test="${analyst.isActive}">
											<a href="${deactivate_analyst}" class="btn btn-outline-danger btn-sm" role="button" aria-pressed="true" data-toggle="tooltip" title="Deactivate"><i class="bi bi-lock"></i></a>
										</c:if>
										<c:if test="${not analyst.isActive}">
											<a href="${activate_analyst}" class="btn btn-outline-success btn-sm" role="button" aria-pressed="true" data-toggle="tooltip" title="Activate"><i class="bi bi-unlock"></i></a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>First Name</th>
								<th>MI</th>
								<th>Last Name</th>
								<th>Role</th>
								<th>Email</th>
								<th>Status</th>
								<th>Actions</th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<div class="modal fade" id="create_new_analyst" tabindex="-1" role="dialog" aria-labelledby="create_new_analyst" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Create Analyst</h5>
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-12">
							<form id="activity_type_form" action="<%=request.getContextPath()%>/#" method="post">
								<input type="hidden" name="command" value="CREATE_ANALYST" />
								<input type="hidden" class="form-control" id="analyst_id"
									name="analyst_id" required/>
								<div class="form-floating mb-3">
									<input type="text" class="form-control" id="analyst_first_name"
										name="analyst_first_name" placeholder="First Name" required/>
									<label for="analyst_first_name">First Name</label>
								</div>
								<div class="form-floating mb-3">
									<input type="text" class="form-control" id="analyst_middle_initial"
										name="analyst_middle_initial" placeholder="Middle Initial"/>
									<label for="analyst_middle_initial">Middle Initial</label>
								</div>
								<div class="form-floating mb-3">
									<input type="text" class="form-control" id="analyst_last_name"
										name="analyst_last_name" placeholder="Last Name" required/>
									<label for="analyst_last_name">Last Name</label>
								</div>
								<div class="form-floating mb-3">
									<input type="text" class="form-control" id="analyst_role"
										name="analyst_role" placeholder="Role" required/>
									<label for="analyst_role">Role</label>
								</div>
								<div class="form-floating mb-3">
									<input type="email" class="form-control" id="analyst_email"
										name="analyst_email" placeholder="Email Address" required/>
									<label for="analyst_email">Email Address</label>
								</div>
								<div class="form-floating mb-3">
									<button type="button" class="btn btn-primary" id="save_analyst" value="SAVE"><i class="bi bi-check-lg"></i> Save</button>
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