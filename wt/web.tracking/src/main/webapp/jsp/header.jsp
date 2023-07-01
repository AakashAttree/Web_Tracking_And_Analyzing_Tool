<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<%
String resourcePrefix = request.getContextPath();
boolean isLoginPage = request.getParameter("page") != null && request.getParameter("page").equalsIgnoreCase("login")?true:false;

%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Console</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=resourcePrefix %>/script/jquerygrid/themes/redmond/jquery-ui-1.8.1.custom.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=resourcePrefix %>/script/jquerygrid/themes/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=resourcePrefix %>/script/jquerygrid/themes/ui.multiselect.css" />

<script src="<%=resourcePrefix %>/script/jquery-1.10.2.js"></script>
<script src="<%=resourcePrefix %>/script/jquery-migrate-1.2.1.js"></script>
<script
	src="<%=resourcePrefix %>/script/jquerygrid/js/jquery-ui-1.7.1.custom.min.js"></script>


<script src="<%=resourcePrefix %>/script/jquerygrid/js/jquery.layout.js"
	type="text/javascript"></script>
<script
	src="<%=resourcePrefix %>/script/jquerygrid/js/i18n/grid.locale-en.js"
	type="text/javascript"></script>


<%-- <script src="<%=resourcePrefix %>/script/jquerygrid/js/jquery.jqGrid.min.js"
	type="text/javascript"></script> --%>
<script src="<%=resourcePrefix %>/script/jqGrid.min.js"
	type="text/javascript"></script>
<%-- <script src="<%=resourcePrefix %>/script/jquerygrid/js/src/grid.inlinedit.js"
	type="text/javascript"></script> --%>

<script
	src="<%=resourcePrefix %>/script/jquerygrid/js/ui.multiselect.js"
	type="text/javascript"></script>

<script
	src="<%=resourcePrefix %>/script/jquerygrid/js/jquery.tablednd.js"
	type="text/javascript"></script>
<script
	src="<%=resourcePrefix %>/script/jquerygrid/js/jquery.contextmenu.js"
	type="text/javascript"></script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
}

/* Add a gray background color and some padding to the footer */
footer {
	background-color: #f2f2f2;
	padding: 25px;
}

.carousel-inner img {
	width: 100%; /* Set width to 100% */
	margin: auto;
	min-height: 200px;
}

/* Hide the carousel text when the screen is less than 600 pixels wide */
@media ( max-width : 600px) {
	.carousel-caption {
		display: none;
	}
}
</style>

</head>
<body>


	<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Logo</a>
		</div>
		<% if(!isLoginPage){%>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav">
				<li class="active"><a href="/default">Home</a></li>
				<!--  <li><a href="#">About</a></li>
        <li><a href="#">Projects</a></li>
        <li><a href="#">Contact</a></li> -->
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/logout"><span
						class="glyphicon glyphicon-log-in"></span>&nbsp;Logout</a></li>
			</ul>
		</div>
		<% }%>
	</div>
	</nav>