<%@ page isErrorPage="true" language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
				<div class="col-2"></div>
				<div class="col-8">
					<h3>An error occured. Please contact support or administrator.</h3>  
  
					<p>
						<%= exception %>
					</p> 
				</div>
				<div class="col-2"></div>
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
						window.location.href = "http://localhost:8080/EUMProdTool/manage";
					} else if (responseText === 'ANALYST-LOGIN') {
						window.location.href = "http://localhost:8080/EUMProdTool/home";
					} else if (responseText === 'CHANGE-PASSWORD') {
						window.location.href = "http://localhost:8080/EUMProdTool/changepassword";
					} else {
						$("#feedback").css("visibility", "visible");
						$("#feedback").html("<i class='bi bi-exclamation-triangle'></i> " + responseText);
					}
		        }
			});
		});
		
		$("#forgot_password").click(function() {
			window.location.href = "http://localhost:8080/EUMProdTool/changepassword";
		});
	});
	</script>
</body>
</html>