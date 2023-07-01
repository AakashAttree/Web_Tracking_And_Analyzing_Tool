
<%@page import="java.time.LocalDate"%>
<%
  	String companyId = (String) request.getParameter("compId");
	LocalDate dateFrom = LocalDate.now();
%>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<script type="text/javascript">
var companyId = '<%=companyId%>';
var option = 'daterange.<%=dateFrom.getYear() %>-<%=dateFrom.getMonthValue() %>-<%=dateFrom.getDayOfMonth() %>.<%=dateFrom.getYear() %>-<%=dateFrom.getMonthValue() %>-<%=dateFrom.getDayOfMonth() %>';
	$(document).ready(function() {
		if(option != "daterange"){
			$("#daterange").hide();
		}
		/* $(window).on("resize", function() {
			window.location.reload(true);
		}); */
		$("input[type='radio'][name='options']").change(function() {
			option = $(this).val();
			if(option == "daterange"){
				$("#daterange").show();
				
			}
			else{
				$("#daterange").hide();
				generateReport(option);
			}
			
		});
		$('input[name="daterange"]').daterangepicker({
		    opens: 'left'
		  }, function(start, end, label) {
		    console.log("A new date selection was made: " + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD'));
		    option = 'daterange.'+start.format('YYYY-MM-DD')+'.'+ end.format('YYYY-MM-DD');
		    generateReport(option);
		  });
		
		generateReport(option);
		
	});
</script>
<script type="text/javascript" src="../../script/web-tracking-chart.js"></script>
<div class="container">
	<div class="row">
		<div class="col-sm-12" style="">
			<span>Filters</span>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12" style="">
			<div class="btn-group btn-group-toggle" data-toggle="buttons">
				<!-- <label class="btn btn-secondary btn-dark"> <input
					type="radio" name="options" id="option1" autocomplete="off"
					value="today"> Today's Report
				</label> <label class="btn btn-secondary"> <input type="radio"
					name="options" id="option2" autocomplete="off" value="currentweek">
					Last 7 Days
				</label>  --><label class="btn btn-secondary"> <input type="radio"
					name="options" id="option6" autocomplete="off" value="daterange">Date
					Range
				</label> <label> <input type="text"
					name="daterange"
					value="01/01/<%=dateFrom.getYear() %> - 01/15/<%=dateFrom.getYear() %>" />
				</label>
			</div>

		</div>

	</div>

	<div class="row">
		<div class="col-sm-12">
			<br>
		</div>
	</div>

	<div class="row">
		<div class="col-sm-12" style="">
			<br>
		</div>
	</div>

	<div class="row">
		<div class="col-sm-4" style="">
			<div class="well" style="margin-top: 30px; background-color: white;">
				<div id="activity-count"></div>
			</div>
		</div>
		<div class="col-sm-8" style=""></div>
	</div>

	<div class="row">
		<div class="col-sm-6" style="">
			<div class="well" style="margin-top: 30px; background-color: white;">
				<div id="Activity_trends"></div>
			</div>
		</div>
		<div class="col-sm-6" style="">
			<div class="well" style="margin-top: 30px; background-color: white;">
				<div id="Activity_Chart"></div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6" style="">
			<div class="well" style="margin-top: 30px; background-color: white;">
				<div id="Contact_Activity_Chart"></div>
			</div>
		</div>
		<div class="col-sm-6" style="">
			<div class="well" style="margin-top: 30px; background-color: white;">
				<div id="social_media_activity_trend"></div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6" style="">
			<div class="well" style="margin-top: 30px; background-color: white;">
				<div id="unknown_contact_activity_trend"></div>
			</div>
		</div>
		<div class="col-sm-6" style="">
			<div class="well" style="margin-top: 30px; background-color: white;">
				<div id="known_contact_activity_trend"></div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12" style="">
			<div class="well" style="margin-top: 30px; background-color: white;">
				<div id="GEO_Chart"></div>
			</div>
		</div>
	</div>

</div>