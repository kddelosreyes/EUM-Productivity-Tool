<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	response.setHeader("Pragma","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Expires","0");
	response.setDateHeader("Expires",-1);
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EUM Productivity Tool</title>

<link rel="stylesheet" href="common/css/bootstrap.min.css">
<link rel="stylesheet" href="common/css/styles.css">
<link rel="stylesheet" href="common/css/sticky-footer-navbar.css">
<link rel="stylesheet" href="common/css/bootstrap-icons.css">
</head>
<body>
	<main>
		<div class="container">
			<div class="row">
				<div class="col"></div>
				<div class="col">
					<form id="login_form" action="<%=request.getContextPath()%>/login" method="post">
						<input type="hidden" name="command" value="CHANGE_PASSWORD" />
						<div class="form-floating mb-3">
							<input type="text" class="form-control" name="username"
								id="username" placeholder="Username" required> <label
								for="username">Username</label>
						</div>
						<div class="form-floating mb-3">
							<input type="password" class="form-control" name="password"
								id="password" placeholder="New Password" required>
							<label for="password">New Password</label>
						</div>
						<div class="form-floating mb-3">
							<input type="password" class="form-control" name="confirm_password"
								id="confirm_password" placeholder="Confirm New Password" required>
							<label for="confirmpassword">Confirm New Password</label>
						</div>
						<div class="form-floating mb-3">
							<input type="text" class="form-control" name="captcha"
								id="captcha" placeholder="Code" disabled>
							<label for="captcha">Code</label>
						</div>
						<!-- <div class="restart">
							<a href="" onclick="createCaptcha()">Change</a>
						</div>-->
						<div class="form-floating mb-3">
							<input type="text" class="form-control" name="confirm_captcha"
								id="confirm_captcha" placeholder="Confirm Code" required>
							<label for="confirmpassword">Confirm Code</label>
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
							<i class="bi bi-check-lg"></i> Success! <a href="http://localhost:8080/EUMProdTool/">Please click here to login.</a>
						</div>
					</form>
				</div>
				<div class="col"></div>
			</div>
		</div>
	</main>
	
	<footer id="footer" class="footer text-dark">
	    <jsp:include page="footer.jsp" />
	</footer>
	
	<script src="common/js/jquery-3.5.1.js"></script>
	<script src="common/js/bootstrap.bundle.min.js"></script>
	<script src="common/js/bootstrap.min.js"></script>

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
				$("#feedback")
						.html(
								"<i class='bi bi-exclamation-triangle'></i> Code must be filled.");
				return false;
			} else if (validateCaptcha > 0 || recaptcha.length > 6) {
				$("#feedback").css("visibility", "visible");
				$("#feedback")
						.html(
								"<i class='bi bi-exclamation-triangle'></i> Wrong captcha.");
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

			$('#password').keypress(function(e) {
				if (e.keyCode == 13) {
					$('#confirm').click();
				}
			});
			
			$('#confirm_password').keypress(function(e) {
				if (e.keyCode == 13) {
					$('#confirm').click();
				}
			});
			
			$('#confirm_code').keypress(function(e) {
				if (e.keyCode == 13) {
					$('#confirm').click();
				}
			});
			
			$('#back').click(function() {
				window.location.href = "http://localhost:8080/EUMProdTool/";
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
								$("#confirm_code").val('');
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