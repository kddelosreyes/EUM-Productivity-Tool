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
				<div class="col-8">
					<h5><b>Reports</b></h5>
				</div>
				<div class="col-4">
					<div class="form-floating mb-3">
						<select	class="form-select" name="report_template" style="display: inline">
							<c:forEach items="${report_templates}" var="reportTemplate">
								<option value="${reportTemplate.id}">${reportTemplate.name}</option>
							</c:forEach>
						</select>
						<label for="report_template">Template</label>
					</div>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-12">
					<form id="generate_report_form" action="<%=request.getContextPath()%>/#" method="post">
						<input type="hidden" name="command" value="CREATE_REPORT" />
						<div class="row">
							<div class="col">
								<div class="form-floating mb-3">
									<input type="text" class="form-control" id="report_name"
										name="report_name" placeholder="Report Name">
									<label for="report_name">Report Name</label>
								</div>
							</div>
							<div class="col">
								<div class="form-floating mb-3">
									<select	class="form-select" id="report_type" name="report_type" style="display: inline">
										<c:forEach items="${report_types}" var="reportType">
											<option value="${reportType.id}">${reportType.name}</option>
										</c:forEach>
									</select>
									<label for="report_type">Report Type</label>
								</div>
							</div>
							<div class="col">
								<div class="form-floating mb-3">
									<select	class="form-select" id="report_team" name="report_team" style="display: inline">
										<c:forEach items="${main_teams}" var="team">
											<option value="${team.id}">${team.name}</option>
										</c:forEach>
									</select>
									<label for="report_team">Team</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<div class="form-floating mb-3">
									<input type="text" class="form-control" id="report_start_date"
										name="report_start_date" placeholder="Start Date">
									<label for="report_start_date">Start Date</label>
								</div>
							</div>
							<div class="col">
								<div class="form-floating mb-3">
									<input type="text" class="form-control" id="report_end_date"
										name="report_end_date" placeholder="End Date">
									<label for="report_end_date">End Date</label>
								</div>
							</div>
							<div class="col">
								<div class="form-floating mb-3">
									<input type="text" class="form-control" id="template_name"
										name="template_name" placeholder="Template Name">
									<label for="template_name">Template Name</label>
								</div>
							</div>
						</div>
						<div class="form-floating mb-3">
							<button type="button" class="btn btn-success" id="save_as_template" value="SAVE_AS_TEMPLATE"><i class="bi bi-check-lg"></i> Save as Template</button>
							<button type="button" class="btn btn-primary" id="generate_report" value="GENERATE"><i class="bi bi-gear"></i> Generate</button>
							<button type="button" class="btn btn-danger" id="clear_report" data-bs-dismiss="modal"><i class="bi bi-x-lg"></i> Clear</button>
						</div>
					</form>
				</div>
			</div>
			<br><br>
			<table id="reports_table" class="table table-striped" style="width:100%">
				<thead>
					<tr>
						<th>Name</th>
						<th>Report Type</th>
						<th>Start Date</th>
						<th>End Date</th>
						<th>Team</th>
						<th>Generation Date</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${reports}" var="report">
						<c:url var="download_report" value="manage">
							<c:param name="command" value="DOWNLOAD_REPORT" />
							<c:param name="report_id" value="${report.uuid}" />
						</c:url>
						<c:url var="archive_report" value="manage">
							<c:param name="command" value="ARCHIVE_REPORT" />
							<c:param name="report_id" value="${report.uuid}" />
						</c:url>
						<tr>
							<td>${report.name}</td>
							<td>${report.reportType.name}</td>
							<td>${report.fromDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"))}</td>
							<td>${report.toDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"))}</td>
							<td>${report.team.name}</td>
							<td>${report.generationDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm a"))}</td>
							<td>
								<a href="${download_report}" class="btn btn-outline-success btn-sm" role="button" aria-pressed="true" data-toggle="tooltip" title="Download"><i class="bi bi-download"></i></a>
								<a href="${archive_report}" class="btn btn-outline-danger btn-sm" role="button" aria-pressed="true" data-toggle="tooltip" title="Archive"><i class="bi bi-archive"></i></a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<th>Name</th>
						<th>Report Type</th>
						<th>Start Date</th>
						<th>End Date</th>
						<th>Team</th>
						<th>Generation Date</th>
						<th>Actions</th>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>

</html>