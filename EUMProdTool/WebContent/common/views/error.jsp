<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
						<%
							out.print((String) session.getAttribute("error_message"));
							session.removeAttribute("error_message");
						%>
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
</body>
</html>