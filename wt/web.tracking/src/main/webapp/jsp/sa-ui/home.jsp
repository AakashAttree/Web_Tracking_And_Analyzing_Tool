<%@page import="web.tracking.utility.WebUtils"%>
<%@page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page
	import="org.springframework.security.core.context.SecurityContext"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="/jsp/header.jsp"></jsp:include>
<%
WebUtils.setCompanyInfo(session);
%>

<div class="container text-center">
	<div class="row">
		<div class="col-sm-6" style="">
			<a href="/sa/ui-company" style="text-decoration: none;">
				<div class="well" style="margin-top: 30px; background-color: white;">
					<p>
					<h3>Company Profile</h3>
					</p>
				</div>
			</a>
		</div>
		<div class="col-sm-6">
			<a href="/sa/showaccounts" style="text-decoration: none;"><div
					class="well" style="margin-top: 30px; background-color: white;">
					<p>
					<h3>Create User</h3>
					</p>
				</div></a>
		</div>

	</div>
	<div class="row">
		<div class="col-sm-6">
			<a href="/sa/showaccounts?page=dashboard"
				style="text-decoration: none;"><div class="well"
					style="margin-top: 30px; background-color: white;">
					<p>
					<h3>View Dashboard</h3>
					</p>
				</div></a>
		</div>
		<div class="col-sm-6">
			<a href="/sa/showaccounts?page=config" style="text-decoration: none;"><div
					class="well" style="margin-top: 30px; background-color: white;">
					<p>
					<h3>Company Configuration</h3>
					</p>
				</div></a>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6">
			<a href="/sa/showaccounts?page=report" style="text-decoration: none;"><div
					class="well" style="margin-top: 30px; background-color: white;">
					<p>
					<h3>View Reports</h3>
					</p>
				</div></a>
		</div>
		<div class="col-sm-6">&nbsp;</div>
	</div>
</div>
<br>

<jsp:include page="/jsp/footer.jsp"></jsp:include>