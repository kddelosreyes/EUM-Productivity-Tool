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
		<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
			<div class="container-fluid">
				<a class="navbar-brand" href="<%=request.getContextPath()%>/home">EUM Productivity Tool</a>
				<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
					data-bs-target="#navbarCollapse" aria-controls="navbarCollapse"
					aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarCollapse">
					<ul class="navbar-nav me-auto mb-2 mb-md-0">
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
	<br>
	<main>
		<div class="container">
			<div class="row">
				<div class="col-3"></div>
				<div class="col-6">
					<div class="card">
						<h5 class="card-header"><b>User Details</b></h5>
						<div class="card-body">
							<div class="row">
								<div class="col-5">
									<div class="form-floating mb-3">
										<input type="text" class="form-control" name="firstname"
											id="firstname" placeholder="First Name" 
											value="${analyst.firstName}" readonly> <label
											for="firstname">First Name</label>
									</div>
								</div>
								<div class="col-2">
									<div class="form-floating mb-3">
										<input type="text" class="form-control" name="middlename"
											id="middlename" placeholder="MI" 
											value="${analyst.middleName}" readonly> <label
											for="middlename">MI</label>
									</div>
								</div>
								<div class="col-5">
									<div class="form-floating mb-3">
										<input type="text" class="form-control" name="lastname"
											id="lastname" placeholder="Last Name" 
											value="${analyst.lastName }" readonly> <label
											for="lastname">Last Name</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-6">
									<div class="form-floating mb-3">
										<input type="text" class="form-control" name="role"
											id="role" placeholder="Role" 
											value="${analyst.role}" readonly> <label
											for="role">Role</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-6">
									<div class="form-floating mb-3">
										<input type="text" class="form-control" name="created_date"
											id="created_date" placeholder="Created Date" 
											value="${analyst.createdDate}" readonly> <label
											for="created_date">Created Date</label>
									</div>
								</div>
								<div class="col-6">
									<div class="form-floating mb-3">
										<input type="text" class="form-control" name="updated_date"
											id="updated_date" placeholder="Created Date" 
											value="${analyst.updatedDate}" readonly> <label
											for="updated_date">Updated Date</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<div class="form-floating mb-3">
										<input type="text" class="form-control" name="application_entity_id"
											id="application_entity_id" placeholder="Application Entity Id" 
											value="${analyst.uuid}" readonly> <label
											for="application_entity_id">Application Entity Id</label>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-3"></div>
			</div>
			<br>
			<div class="row">
				<div class="col-3"></div>
				<div class="col-6">
					<div class="card">
						<h5 class="card-header"><b>Change Password</b></h5>
						<div class="card-body">
							<form id="login_form" action="<%=request.getContextPath()%>/login" method="post">
								<input type="hidden" name="command" value="CHANGE_PASSWORD" />
								<div class="row">
									<div class="col">
										<div class="form-floating mb-3">
											<input type="text" class="form-control" name="username"
												id="username" placeholder="Username" 
												value="${fn:split(analyst_login.username, '@')[0] }" readonly> <label
												for="username">Username</label>
										</div>
									</div>
									<div class="col">
										<div class="form-floating mb-3">
											<input type="text" class="form-control" name="email_address"
												id="email_address" placeholder="Email Address" 
												value="${analyst_login.username }" readonly> <label
												for="email_address">Email Address</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="form-floating mb-3">
											<input type="password" class="form-control" name="password"
												id="password" placeholder="New Password" required>
											<label for="password">New Password</label>
										</div>
									</div>
									<div class="col">
										<div class="form-floating mb-3">
											<input type="password" class="form-control" name="confirm_password"
												id="confirm_password" placeholder="Confirm New Password" required>
											<label for="confirmpassword">Confirm New Password</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="form-floating mb-3">
											<input type="text" class="form-control" name="captcha"
												id="captcha" placeholder="Code" disabled>
											<label for="captcha">Code</label>
										</div>
									</div>
									<div class="col">
										<div class="form-floating mb-3">
											<input type="text" class="form-control" name="confirm_captcha"
												id="confirm_captcha" placeholder="Confirm Code" required>
											<label for="confirm_captcha">Confirm Code</label>
										</div>
									</div>
								</div>
								<br>
								<div class="form-floating">
									<button type="button" class="btn btn-primary mb-3" id="confirm" value="confirm">
										<i class="bi bi-check-lg"></i> Confirm
									</button>
									<button type="button" class="btn btn-danger mb-3" id="back" value="back">
										<i class="bi bi-arrow-left"></i> Back
									</button>
								</div>
								<div id="feedback" class="alert alert-danger" role="alert" style="visibility:hidden">
								</div>
								<div id="link" class="alert alert-success" role="alert" style="visibility:hidden">
									<i class="bi bi-check-lg"></i> Success! <a href="http://localhost:8080/EUMProdTool/home">You have successfully changed your password.</a>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="col-3"></div>
			</div>
			<br>
		</div>
	</main>

	<footer class="footer text-dark">
	    <jsp:include page="footer.jsp" />
	</footer>
	
	<script src="common/js/bootstrap.bundle.min.js"></script>
	<script src="common/js/bootstrap.min.js"></script>
	<script src="common/js/jquery-3.5.1.js"></script>
	<script src="common/js/popper.min.js"></script>
	
	<script type="text/javascript">

		let captcha = new Array();
		createCaptcha();

		function createCaptcha() {
			const activeCaptcha = document.getElementById("captcha");
			for (q = 0; q < 9; q++) {
				if (q % 2 == 0) {
					captcha[q] = String.fromCharCode(Math
							.floor(Math.random() * 26 + 65));
				} else {
					captcha[q] = Math.floor(Math.random() * 10 + 0);
				}
			}
			theCaptcha = captcha.join("");
			activeCaptcha.value = theCaptcha;
		}

		function validateCaptcha() {
			const errCaptcha = document.getElementById("errCaptcha");
			const reCaptcha = document.getElementById("confirm_captcha");
			recaptcha = reCaptcha.value;
			console.log(recaptcha + " " + captcha);
			let validateCaptcha = 0;
			for (var z = 0; z < 6; z++) {
				if (recaptcha.charAt(z) != captcha[z]) {
					validateCaptcha++;
				}
			}
			
			if (recaptcha == "") {
				$("#feedback").css("visibility", "visible");
				$("#feedback").html("<i class='bi bi-exclamation-triangle'></i> Code must be filled.");
				return false;
			} else if (validateCaptcha > 0 || recaptcha.length > 9) {
				$("#feedback").css("visibility", "visible");
				$("#feedback").html("<i class='bi bi-exclamation-triangle'></i> Confirmation code is wrong.");
				return false;
			} else {
				return true;
			}
		}

		$(document).ready(function() {
			var form = $('#login_form');
			console.log($("#username").val());

			$('#username').keypress(function(e) {
				if (e.keyCode == 13) {
					$('#confirm').click();
				}
			});

			$('#password').keyup(function(e) {
				if (e.keyCode == 13) {
					$('#confirm').click();
				} else {
					let strongPassword = new RegExp('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])(?=.{8,})');
					var password = $('#password');
					var confirmPassword = $('#confirm_password');
					password.removeClass("is-invalid");
					password.removeClass("is-valid");
					confirmPassword.removeClass("is-invalid");
					confirmPassword.removeClass("is-valid");
					
					console.log(password.val());
					
					if(strongPassword.test(password.val())) {
						password.addClass("is-valid");
					} else {
						if (password.val().length > 0) {
							password.addClass("is-invalid");
						} else {
							password.removeClass("is-invalid");
						}
					}
					
					if (confirmPassword.val().length > 0) {
						console.log(confirmPassword.val() + " " + password.val());
						if (password.val() === confirmPassword.val()) {
							console.log(true);
							confirmPassword.addClass("is-invalid");
						} else {
							console.log(false);
							confirmPassword.addClass("is-valid");
						}
					}
				}
			});
			
			$('#confirm_password').keyup(function(e) {
				if (e.keyCode == 13) {
					$('#confirm').click();
				} else {
					var password = $('#password');
					var confirmPassword = $('#confirm_password');
					
					confirmPassword.removeClass("is-invalid");
					confirmPassword.removeClass("is-valid");
					
					console.log(confirmPassword.val());
					
					if (password.val() === confirmPassword.val() && confirmPassword.val().length > 0) {
						confirmPassword.addClass("is-valid");
					} else {
						if (confirmPassword.val().length > 0) {
							confirmPassword.addClass("is-invalid");
						} else {
							confirmPassword.removeClass("is-invalid");
						}
					}
				}
			});
			
			$('#confirm_captcha').keypress(function(e) {
				if (e.keyCode == 13) {
					$('#confirm').click();
				}
			});
			
			$('#back').click(function() {
				window.location.href = "http://localhost:8080/EUMProdTool/home";
			});

			$("#confirm").click(function() {
				$("#feedback").css("visibility", "hidden");
				if (validateCaptcha()) {
					$.ajax({
						type : "POST",
						url : "changepassword",
						data : {
							command : "CHANGE_PASSWORD",
							username : $("#username").val(),
							password : $("#password").val(),
							confirm_password: $("#confirm_password").val(),
						},
						success : function(responseText) {
							$("#feedback").css("visibility", "hidden");
							$("#link").css("visibility", "hidden");
							console.log("Response Text: " + responseText);
							if (responseText === 'SUCCESS') {
								$("#link").css("visibility", "visible");
								$("#username").val('');
								$("#password").val('');
								$("#confirm_password").val('');
								$("#confirm_captcha").val('');
								
								$("#password").removeClass("is-invalid");
								$("#password").removeClass("is-valid");
								$("#confirm_password").removeClass("is-invalid");
								$("#confirm_password").removeClass("is-valid");
								createCaptcha();
							} else {
								$("#feedback").css("visibility", "visible");
								$("#feedback").html("<i class='bi bi-exclamation-triangle'></i> " + responseText);
							}
					    }
					});
				}
			});
		});
	</script>
</body>
</html>