<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="/jsp/header.jsp"></jsp:include>
<%
String pageRequest = request.getParameter("page");
String urlOfPage = "ui-account";
if(pageRequest != null){
	if(pageRequest.equals("dashboard")){
		urlOfPage = "dashboard";
	}
	else if(pageRequest.equals("report")){
		urlOfPage = "ui-report";
	}
	else if(pageRequest.equals("tracking")){
		urlOfPage = "ui-tracking";
	}
	else if(pageRequest.equals("addvariable")){
		urlOfPage = "companyvariable";
	}
	else if(pageRequest.equals("config")){
		urlOfPage = "ui-company-config";
	}
}
%>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		url : "/getcompanies",
		method : "get",
		success : function(
				result) {
			$("#select").html(result);
		}
	});
	$("#Submit").click(function(){
		var value = $("#compId").val();
		var text = $("#compId").text();
		window.location = '<%=urlOfPage%>?compId='+value+'&compName='+text;
	});
	
});
</script>
<div class="container text-center">
	<!-- <form>
	<div id="select"></div>
	<input type="button" name="Submit" value="Submit" id="Submit">
</form> -->

	<div class="row" style="margin-top: 30px;">
		<form class="form-horizontal">
			<div class="form-group">
				<label class="control-label col-sm-2" for="compId">Select
					Company:</label>
				<div class="col-sm-10" id="select">
					<!-- <input type="email" class="form-control" id="email" placeholder="Enter email"> -->
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" id="Submit" class="btn btn-default">Submit</button>
				</div>
			</div>
		</form>
	</div>


</div>
<jsp:include page="/jsp/footer.jsp"></jsp:include>