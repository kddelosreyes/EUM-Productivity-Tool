<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="common/css/bootstrap.min.css">
<link rel="stylesheet" href="common/css/jquery.dataTables.css">
<link rel="stylesheet" href="common/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="common/css/styles.css">
<link rel="stylesheet" href="common/css/sticky-footer-navbar.css">
</head>
<body>
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
					<button class="btn btn-primary dropdown-toggle" type="button"
						id="account_drop_down" data-bs-toggle="dropdown"
						data-bs-display="static" aria-expanded="false">Welcome, ${analyst.firstName}!</button>
					<ul class="dropdown-menu dropdown-menu-lg-end"">
						<li>
							<a href="#" class="dropdown-item">
								<i class="bi bi-person"></i> My Profile
							</a>
						</li>
						<li>
							<a href="#" class="dropdown-item">
								<i class="bi bi-calendar3"></i> My Calendar
							</a>
						</li>
						<li>
							<a href="${logout}" class="dropdown-item">
								<i class="bi bi-box-arrow-left"></i> Logout
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</nav>

	<script src="common/js/jquery-3.5.1.js"></script>
	<script src="common/js/popper.min.js"></script>
</body>
</html>