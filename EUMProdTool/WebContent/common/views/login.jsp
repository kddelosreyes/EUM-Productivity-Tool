<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="error.jsp" %>  
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
					<form id="login_form" action="<%=request.getContextPath()%>/login" method="post" autocomplete="off">
						<input type="hidden" name="command" value="LOGIN" />
						<div class="form-floating mb-3">
							<input type="text" class="form-control" name="username"
								id="username" placeholder="Username" required> <label
								for="username">Username</label>
						</div>
						<div class="form-floating">
							<input type="password" class="form-control" name="password"
								id="password" placeholder="Password" required> <label
								for="password">Password</label>
						</div>
						<br>
						<div class="form-floating">
							<button type="button" class="btn btn-primary mb-3" id="login" value="Login">
								<i class="bi bi-box-arrow-in-right"></i> Login
							</button>
							<button type="button" class="btn btn-danger mb-3" id="forgot_password" value="Forgot Password">
								<i class="bi bi-unlock"></i> Forgot Password
							</button>
						</div>
						<div id="feedback" class="alert alert-danger" role="alert" style="visibility:hidden">
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
	$(document).ready(function() {
		var form = $('#login_form');
		console.log($("#username").val());
		
		$('#username').keypress(function(e) {
	        if ( e.keyCode == 13 ) {
	            $('#login').click();
	        }
	    });
		
		$('#password').keypress(function(e) {
	        if ( e.keyCode == 13 ) {
	            $('#login').click();
	        }
	    });
		
		$("#login").click(function() {
			$("#feedback").css("visibility", "hidden");
			console.log($("#username").val());
			$.ajax({
				type : "POST",
				url : "login",
				data : {
					command : "LOGIN",
					username : $("#username").val(),
					password : $("#password").val()
				},
				success : function(responseText) {
					$("#feedback").css("visibility", "hidden");
					console.log("Response Text: " + responseText);
					
					if (responseText === 'MANAGER-LOGIN') {
						window.location.href = "${pageContext.request.contextPath}/manage";
					} else if (responseText === 'ANALYST-LOGIN') {
						window.location.href = "${pageContext.request.contextPath}/home";
					} else if (responseText === 'CHANGE-PASSWORD') {
						setFieldsDefault();
						window.location.href = "${pageContext.request.contextPath}/changepassword";
					} else {
						<%
							String errorMessage = (String) session.getAttribute("error_message");
						%>
						
						var jsLastPage = '<%= errorMessage %>';
						console.log(jsLastPage);
						if (responseText) {
							$("#feedback").css("visibility", "visible");
							$("#feedback").html("<i class='bi bi-exclamation-triangle'></i> " + responseText);
						} else {
							setFieldsDefault();
							window.location.href = "${pageContext.request.contextPath}/error";
						}
					}
		        }
			});
		});
		
		$("#forgot_password").click(function() {
			window.location.href = "${pageContext.request.contextPath}/changepassword";
		});
		
		function setFieldsDefault() {
			$('#username').val('');
			$('#password').val('');
		}
	});
	</script>
</body>
</html>