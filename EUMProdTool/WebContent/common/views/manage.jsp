<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<link rel="stylesheet" href="common/css/jquery-ui.css">
<link rel="stylesheet" href="common/css/jquery-ui.multidatespicker.css">
<link rel="stylesheet" href="common/css/bootstrap-icons.css">
</head>
<body onload=displayClock(); class="d-flex flex-column h-100 bg-purple">
	<header>
		<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
			<div class="container-fluid">
				<a class="navbar-brand" href="<%=request.getContextPath()%>/manage">EUM Productivity Tool</a>
				<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
					data-bs-target="#navbarCollapse" aria-controls="navbarCollapse"
					aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarCollapse">
					<ul class="nav nav-pills me-auto mb-2 mb-md-0">
						<li class="nav-item" role="presentation">
							<button class="nav-link active" id="pills-home"
								data-bs-toggle="pill" data-bs-target="#pills-home-div"
								type="button" role="tab" aria-controls="pills-home-div"
								aria-selected="false"><i class="bi bi-house"></i> Home</button>
						</li>
						<li class="nav-item" role="presentation">
							<button class="nav-link" id="pills-entities-activities"
								data-bs-toggle="pill" data-bs-target="#pills-entities-activities-div"
								type="button" role="tab" aria-controls="pills-entities-activities-div"
								aria-selected="false"><i class="bi bi-activity"></i> Activities</button>
						</li>
						<li>
							<button class="nav-link" id="pills-entities-analysts"
								data-bs-toggle="pill" data-bs-target="#pills-entities-analysts-div"
								type="button" role="tab" aria-controls="pills-entities-analysts-div"
								aria-selected="false"><i class="bi bi-person"></i> Analysts</button>
						</li>
						<li>
							<button class="nav-link" id="pills-entities-teams"
								data-bs-toggle="pill" data-bs-target="#pills-entities-teams-div"
								type="button" role="tab" aria-controls="pills-entities-teams-div"
								aria-selected="false"><i class="bi bi-people"></i> Teams</button>
						</li>
						<li>
							<button class="nav-link" id="pills-attendance-attendance"
								data-bs-toggle="pill" data-bs-target="#pills-attendance-attendance-div"
								type="button" role="tab" aria-controls="pills-attendance-attendance-div"
								aria-selected="false" aria-expanded="true"><i class="bi bi-calendar"></i> Attendance</button>
						</li>
						<li>
							<button class="nav-link" id="pills-attendance-shift-schedule"
								data-bs-toggle="pill" data-bs-target="#pills-attendance-shift-schedule-div"
								type="button" role="tab" aria-controls="pills-attendance-shift-schedule-div"
								aria-selected="false" aria-expanded="true"><i class="bi bi-calendar-week"></i> Shift Schedule</button>
						</li>
						<li>
							<button class="nav-link" id="pills-attendance-leaves"
								data-bs-toggle="pill" data-bs-target="#pills-attendance-leaves-div"
								type="button" role="tab" aria-controls="pills-attendance-leaves-div"
								aria-selected="false" aria-expanded="true"><i class="bi bi-calendar-x"></i> Leaves</button>
						</li>
						<li class="nav-item" role="presentation">
							<button class="nav-link" id="pills-reports"
								data-bs-toggle="pill" data-bs-target="#pills-reports-div"
								type="button" role="tab" aria-controls="pills-reports-div"
								aria-selected="false"><i class="bi bi-file-earmark-spreadsheet"></i> Reports</button>
						</li>
					</ul>
					<c:url var="logout" value="login">
						<c:param name="command" value="LOGOUT" />
					</c:url>
					<div class="btn-group d-flex">
						<a href="${logout}" class="btn btn-primary" role="button" aria-pressed="true"><i class="bi bi-box-arrow-left"></i> Logout</a>
					</div>
				</div>
			</div>
		</nav>
	</header>
	
	<main class="flex-shrink-0">
		<br>
		<div class="container" style="padding-bottom 100px; min-height:100%;">
			<div class="row">
				<div class="col">
					<div class="alert alert-success text-center" id="date_time">
						Date and Time
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<div class="tab-content" id="pills-tabContent">
						<div class="tab-pane fade show active" id="pills-home-div" role="tabpanel"
							aria-labelledby="pills-home" tabindex="0">
							<jsp:include page="_home.jsp" />
						</div>
						<div class="tab-pane fade" id="pills-entities-activities-div" role="tabpanel"
							aria-labelledby="#pills-entities-activities" tabindex="0">
							<jsp:include page="_activity.jsp" />
						</div>
						<div class="tab-pane fade" id="pills-entities-analysts-div" role="tabpanel"
							aria-labelledby="#pills-entities-analysts" tabindex="0">
							<jsp:include page="_analyst.jsp" />
						</div>
						<div class="tab-pane fade" id="pills-entities-teams-div" role="tabpanel"
							aria-labelledby="#pills-entities-teams" tabindex="0">
							<jsp:include page="_team.jsp" />
						</div>
						<div class="tab-pane fade" id="pills-attendance-attendance-div" role="tabpanel"
							aria-labelledby="#pills-attendance-attendance" tabindex="0">
							<jsp:include page="_attendance.jsp" />
						</div>
						<div class="tab-pane fade" id="pills-attendance-shift-schedule-div" role="tabpanel"
							aria-labelledby="#pills-attendance-shift-schedule" tabindex="0" style="width:100%;">
							<jsp:include page="_shiftschedule.jsp" />
						</div>
						<div class="tab-pane fade" id="pills-attendance-leaves-div" role="tabpanel"
							aria-labelledby="#pills-attendance-leaves" tabindex="0">
							Attendance / Leaves
						</div>
						<div class="tab-pane fade" id="pills-reports-div" role="tabpanel"
							aria-labelledby="#pills-reports" tabindex="0">
							<jsp:include page="_report.jsp" />
						</div>
					</div>
				</div>
			</div>
			<br>
		</div>
		
		<div class="toast-container border-0 position-fixed bottom-0 end-0 p-3">
			<div id="liveToastError" class="toast border-0" role="alert" aria-live="assertive" aria-atomic="true" data-delay="500" data-autohide="true">
				<div class="toast-header">
					<i class="bi bi-exclamation-triangle"></i><strong class="me-auto">&nbsp; Error!</strong>
					<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
				</div>
				<div class="toast-body" id="toast_error_message">
					Message
				</div>
			</div>
		</div>
		
		<div class="toast-container border-0 position-fixed bottom-0 end-0 p-3">
			<div id="liveToastSuccess" class="toast border-0" role="alert" aria-live="assertive" aria-atomic="true" data-delay="500" data-autohide="true">
				<div class="toast-header">
					<i class="bi bi-exclamation-triangle"></i><strong class="me-auto">&nbsp; Success!</strong>
					<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
				</div>
				<div class="toast-body" id="toast_success_message">
					Message
				</div>
			</div>
		</div>
	</main>
	
	<footer class="footer text-dark">
	    <jsp:include page="footer.jsp" />
	</footer>
	
	<script src="common/js/jquery-3.5.1.js"></script>
	<script src="common/js/popper.min.js"></script>
	<script src="common/js/bootstrap.bundle.min.js"></script>
	<script src="common/js/bootstrap.min.js"></script>
	<script src="common/js/dataTables.bootstrap5.min.js"></script>
	<script src="common/js/jquery.dataTables.min.js"></script>
	<script src="common/js/jquery-ui.js"></script>
	<script src="common/js/jquery-ui.multidatespicker.js"></script>
	<script src="common/js/moment.js"></script>
	
	<script>
		function displayClock() {
			var x = new Date();
			document.getElementById('date_time').innerHTML = x;
			mytime = setTimeout('displayClock()', 1000);
		}

		$(document).ready(function() {
			const toastError = document.getElementById('liveToastError');
			const toastSuccess = document.getElementById('liveToastSuccess');
			
			<%
				String lastPage = (String) session.getAttribute("last_page");
				String message = (session.getAttribute("message") == null ? "" : (String) session.getAttribute("message"));
				String errorMessage = (session.getAttribute("error_message") == null ? "" : (String) session.getAttribute("error_message"));
			%>
			
			var jsLastPage = '<%= lastPage %>';
			var jsMessage = '<%= message %>';
			var jsErrorMessage = '<%= errorMessage %>';
			console.log(jsLastPage);
			
			if (jsLastPage === 'REPORT') {
				$("#pills-reports").click();
			} else if (jsLastPage === 'ANALYST') {
				$("#pills-entities-analysts").click();
			} else if (jsLastPage === 'ACTIVITY') {
				$("#pills-entities-activities").click();
			} else if (jsLastPage === 'TEAM') {
				$("#pills-entities-teams").click();
			} else if (jsLastPage === 'SHIFT_SCHEDULE') {
				$("#pills-attendance-shift-schedule").click();
			}
			
			if (jsMessage) {
				showSuccessMessage(jsMessage);
			} else if (jsErrorMessage) {
				showErrorMessage(jsErrorMessage);
			}
			
			<%
				session.removeAttribute("last_page");
				session.removeAttribute("message");
				session.removeAttribute("error_message");
			%>
			
			/*
			 *	Attendance
			 */
			$('#attendance_table').DataTable({
				"ordering" : false,
				autoWidth : true,
				"bFilter" : false
			});
			
			/*
			 * Analysts
			 */
			$('#analysts_table').DataTable({
				"ordering" : false,
				autoWidth : true
			});
			
			clearField($('#analyst_first_name'));
			clearField($('#analyst_last_name'));
			clearField($('#analyst_role'));
			clearField($('#analyst_email'));
			
			$("#save_analyst").click(function() {
				var firstNameField = $('#analyst_first_name');
				var lastNameField = $('#analyst_last_name');
				var roleField = $('#analyst_role');
				var emailField = $('#analyst_email');
				
				var isValidFirstName = isValid(firstNameField);
				var isValidLastName = isValid(lastNameField);
				var isValidRole = isValid(roleField);
				var isValidEmail = isValid(emailField);
				
				var middleName = "";
				if ($('#analyst_middle_initial')) {
					middleName = $('#analyst_middle_initial').val();
				}
				
				if (isValidFirstName && isValidLastName && isValidRole && isValidEmail) {
					$.ajax({
						type : "POST",
						url : "manage",
						data : {
							command : "CREATE_ANALYST",
							first_name : firstNameField.val(),
							middle_name : middleName,
							last_name: lastNameField.val(),
							role : roleField.val(),
							email : emailField.val()
						},
						success : function(responseText) {
							if (responseText) {
								window.location.href = "${pageContext.request.contextPath}/manage";
							} else {
								window.location.href = "${pageContext.request.contextPath}/error";
							}
				        }
					});
				}
			});
			
	        $('#analysts_table').on('click', 'button.edit_analyst',function (ele) {
	        	console.log('Clicked.');
	            //the <tr> variable is use to set the parentNode from "ele
	            var tr = ele.target.parentNode.parentNode.parentNode;

	            //I get the value from the cells (td) using the parentNode (var tr)
	            var firstName = tr.cells[0].textContent;
	            var middleInitial = tr.cells[1].textContent;
	            var lastName = tr.cells[2].textContent;
	            var role = tr.cells[3].textContent;
	            var email = tr.cells[4].textContent;

	            $('#analyst_first_name').val(firstName);
				$('#analyst_last_name').val(lastName);
				$('#analyst_middle_initial').val(middleInitial);
				$('#analyst_role').val(role);
				$('#analyst_email').val(email);
				$("#analyst_email").prop('disabled', true);
	        });
			
	        $('#create_new_analyst').on('hidden.bs.modal', function (e) {
	        	clearField($('#analyst_first_name'));
				clearField($('#analyst_last_name'));
				clearField($('#analyst_role'));
				clearField($('#analyst_email'));
				
				$("#analyst_email").prop('disabled', false);
				
				$('#analyst_first_name').val('');
				$('#analyst_middle_initial').val('');
				$('#analyst_last_name').val('');
				$('#analyst_role').val('');
				$('#analyst_email').val('');
	        });
	        
			/*
			 *	Activity
			 */
			$('#activity_types_table').DataTable({
				"ordering" : false,
				autoWidth : true,
				"bFilter" : false
			});
			
			$('#activities_table').DataTable({
				"ordering" : false,
				autoWidth : true
			});
			
			$('#fields_table').DataTable({
				"ordering" : false,
				autoWidth : true
			});
			
			$('#activity_field_map_table').DataTable({
				"ordering" : false,
				autoWidth : true
			});
			
			/*
			 *	Team
			 */
			$('#teams_table').DataTable({
				"ordering" : false,
				autoWidth : true
			});
			
			$('#analyst_team_table').DataTable({
				"ordering" : false,
				autoWidth : true
			});
			
			$('#team_activity_table').DataTable({
				"ordering" : false,
				autoWidth : true
			});
			
			/*
			 *	Shift Schedules
			 */
			$('#shift_schedules_table').DataTable({
				"ordering" : false,
				autoWidth : true
			});
			
			var analystShiftScheduleTable = $('#analyst_shift_schedules_table').DataTable({
				"ordering" : false,
				 autoWidth : true
			});
			
			$('#shift_schedule_start_time').change(function() {
				checkNightShift();
			});
			
			$('#shift_schedule_end_time').change(function() {
				checkNightShift();
			});
			
			function checkNightShift() {
				var startTime = $('#shift_schedule_start_time');
				var endTime = $('#shift_schedule_end_time');
				
				if (startTime.val() && endTime.val()) {
					var startDate = new Date("1/1/1900 " + startTime.val());
					var endDate = new Date("1/1/1900 " + endTime.val());
					if (startDate > endDate) {
						$("#shift_schedule_night_shift").prop('checked', true);
					} else {
						$("#shift_schedule_night_shift").prop('checked', false);
					}
				}
			}
			
			var data = {
					values : ${analyst_shift_schedlues_gson}
			};
			
			console.log(data.values);
			
			$('#analyst_shift_schedule_start_date').multiDatesPicker({
				minDate : 0
			});
			$('#analyst_shift_schedule_start_date').keypress(function (evt) {
				return isNumeric(evt);
			});
			
			$('#shift_schedules').multiDatesPicker({
				numberOfMonths: [2,2],
				defaultDate: new Date()
			});
			
			$('#analyst_shift_schedule_analyst').change(function() {
				var analyst_id = this.value;
				var dates = [];
				data.values.forEach(function(item) {
					if (item.analystId == analyst_id) {
						var fromDate = item.fromDate;
						dates.push(new Date(fromDate.year, fromDate.month - 1, fromDate.day));
					}
				});
				$('#analyst_shift_schedule_start_date').multiDatesPicker('resetDates', 'disabled');
				$('#analyst_shift_schedule_start_date').multiDatesPicker('resetDates', 'picked');
				if (Array.isArray(dates) && dates.length) {
					$('#analyst_shift_schedule_start_date').multiDatesPicker({
						addDisabledDates : dates
					});
				}
			});
			
			$('#analyst_shift_schedule_analyst_selector').change(function() {
				var analyst_id = this.value;
				analystShiftScheduleTable.clear().draw();
				if (analyst_id == -1) {
					$('#shift_schedules').multiDatesPicker('resetDates', 'disabled');
					$('#shift_schedules').multiDatesPicker('resetDates', 'picked');
				} else {
					var dates = [];
					var counter = 1;
					data.values.forEach(function(item) {
						if (item.analystId == analyst_id) {
							console.log(item);
							var fromDate = item.fromDate;
							dates.push(new Date(fromDate.year, fromDate.month - 1, fromDate.day));
							
							analystShiftScheduleTable
								.row
								.add([
									counter,
									item.analyst.firstName + " " + item.analyst.lastName,
									item.fromDate.year + "-" + String("0" + item.fromDate.month).slice(-2) + "-" + ("0" + item.fromDate.day.toString()).slice(-2),
									item.shiftSchedule.name,
									'<a href="${pageContext.request.contextPath}/manage?command=EDIT_SHIFT_SCHEDULE&analyst_shift_schedule_id=' + item.id + '" class="btn btn-outline-success btn-sm" role="button" aria-pressed="true"><i class="bi bi-pencil"></i></a>'
								])
								.draw(false);
							counter++;
						}
					});
					$('#shift_schedules').multiDatesPicker('resetDates', 'disabled');
					$('#shift_schedules').multiDatesPicker('resetDates', 'picked');
					if (Array.isArray(dates) && dates.length) {
						$('#shift_schedules').multiDatesPicker({
							addDates : dates
						});
					}
				}
			});
			
			$('#analyst_shift_schedule_end_date').datepicker();
			$('#analyst_shift_schedule_end_date').change(function() {
				var startDate = $('#analyst_shift_schedule_start_date');
				var endDate = $('#analyst_shift_schedule_end_date');
				if (startDate.val()) {
					var startDateStr = startDate.val();
					var endDateStr = endDate.val();
					
					var dateStart = new Date(startDateStr);
					var dateEnd = new Date(endDateStr);
					
					console.log(dateStart + " " + dateEnd);
					
					if (dateStart > dateEnd) {
						$("#save_analyst_shift_schedule").prop('disabled', true);
						addClass(startDate, "is-invalid");
						addClass(endDate, "is-invalid");
					} else {
						$("#save_analyst_shift_schedule").prop('disabled', false);
						removeClass(startDate, "is-invalid");
						removeClass(endDate, "is-invalid");
					}
				}
			});
			
			/*
			 *	Report
			 */
			$("#report_start_date").datepicker();
			$("#report_start_date").keypress(function (evt) {
				return isNumeric(evt);
			});
			
			$("#report_end_date").datepicker();
			$("#report_end_date").keypress(function (evt) {
				return isNumeric(evt);
			});
			
			$('#reports_table').DataTable({
				"ordering" : false,
			});
			
			$("#generate_report").click(function() {
				clearField($('#template_name'));
				validateReportFields();
			});
			
			$("#save_as_template").click(function() {
				validateTemplateReportFields();
			});
			
			$("#clear_report").click(function() {
				clearFields();
			});
			
			$("#report_name").focusout(function() {
				validateReportName();
			});
			
			function validateReportFields() {
				var reportNameErrorMessage = validateReportName();
				var datesErrorMessage = validateDates();
				
				const messages = [reportNameErrorMessage,
					datesErrorMessage];
				
				if (isAnyMessageEmpty(messages)) {
					createToastMessage(messages);
				} else {
					generateReport();
				}
			}
			
			function generateReport() {
				$.ajax({
					type : "POST",
					url : "manage",
					data : {
						command 			: "GENERATE_REPORT",
						report_name 		: $("#report_name").val(),
						report_type_id		: $("#report_type").val(),
						team_id				: $("#report_team").val(),
						report_start_date	: $("#report_start_date").val(),
						report_end_date		: $("#report_end_date").val()
					},
					success : function(responseText) {
						if (responseText) {
							window.location.href = "${pageContext.request.contextPath}/manage";
						} else {
							window.location.href = "${pageContext.request.contextPath}/error";
						}
			        }
				});
			}
			
			function validateTemplateReportFields() {
				var reportNameErrorMessage = validateReportName();
				var datesErrorMessage = validateDates();
				var templateNameErrorMessage = validateTemplateName();
				
				const messages = [reportNameErrorMessage,
					datesErrorMessage,
					templateNameErrorMessage];
				
				if (isAnyMessageEmpty(messages)) {
					createToastMessage(messages);
				}
			}
			
			function clearFields() {
				clearField($('#report_name'));
				clearField($('#template_name'));
				clearField($('#report_start_date'));
				clearField($('#report_end_date'));
				
				$("select#report_type").prop('selectedIndex', 0);
				$("select#report_team").prop('selectedIndex', 0);
			}
			
			function isAnyMessageEmpty(messages) {
				for (var idx = 0; idx < messages.length; idx++) {
					if (messages[0]) {
						return true;
					}
				}
				return false;
			}
			
			function clearField(f) {
				f.val("");
				removeClass(f, "is-invalid");
			}
			
			function validateReportName() {
				var reportName = $('#report_name');
				if (!isValid(reportName)) {
					return "Report name is required.";
				} else {
					return "";
				}
			}
			
			function validateDates() {
				var reportStartDate = $('#report_start_date');
				var reportEndDate = $('#report_end_date');
				
				var isValidStartDate = isValid(reportStartDate);
				var isValidEndDate = isValid(reportEndDate);
				
				if (isValidStartDate && isValidEndDate) {
					var start = Date.parse(reportStartDate.val());
					var end = Date.parse(reportEndDate.val());
					if (start > end) {
						addClass(reportStartDate, "is-invalid");
						addClass(reportEndDate, "is-invalid");
						
						return "Start date must be on or before end date.";
					} else {
						removeClass(reportStartDate, "is-invalid");
						removeClass(reportEndDate, "is-invalid");

						return "";
					}
				} else {
					return "Date fields are required.";
				}
			}
			
			function validateTemplateName() {
				var templateName = $('#template_name');
				if (!isValid(templateName)) {
					return "Template name is required.";
				} else {
					return "";
				}
			}
			
			function isValid(f) {
				var isValid = false;
				if (f.val()) {
					removeClass(f, "is-invalid");
					isValid = true;
				} else {
					addClass(f, "is-invalid");
				}
				return isValid;
			}
			
			function addClass(f, c) {
				f.addClass(c);
			}
			
			function removeClass(f, c) {
				f.removeClass(c);
			}
			
			function isNumeric(evt) {
				var charCode = evt.charCode || evt.keyCode;
				console.log(charCode);
				if (charCode >= 47 && charCode <= 57) {
					return false;
				}
			}
			
			function createToastMessage(messages) {
				var errorMessage = "";
				for (var idx = 0; idx < messages.length; idx++) {
					var message = messages[idx];
					if (errorMessage === "") {
						errorMessage = message;
					} else {
						if (message !== "") {
							errorMessage += ("<br>" + message);
						}
					}
				}
				
				showErrorMessage(errorMessage);
			}
			
			function showErrorMessage(message) {
				$("#toast_error_message").html(message);
				const toastMessage = new bootstrap.Toast(toastError);
				toastMessage.show();
			}
			
			function showSuccessMessage(message) {
				$("#toast_success_message").html(message);
				const toastMessage = new bootstrap.Toast(toastSuccess);
				toastMessage.show();
			}
		});
	</script>
</body>
</html>