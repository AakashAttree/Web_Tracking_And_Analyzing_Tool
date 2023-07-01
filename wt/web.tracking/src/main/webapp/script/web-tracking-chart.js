/**
 * 
 */
google.charts.load('current', {
	'packages': ['geochart'],
	'mapsApiKey': 'AIzaSyD-EeIjl760IDKmd0EVCSmYtZ8iozMOXFs'
});

function generateReport(option) {
	$.ajax({
		url: "/chart/company/" + companyId + "/report/activity-trend/" + option,
		method: "get",
		contentType: "application/json",
		success: function(result) {
			console.log(result.companyActiviesTrend);
			if (result.companyActiviesTrend != null) {
				google.charts.load('current', {
					'packages': ['corechart']
				});
				google.charts
					.setOnLoadCallback(drawChart);
				function drawChart() {
					var data = google.visualization
						.arrayToDataTable(result.companyActiviesTrend);
					var options = {
						title: 'Activities'
					};
					var chart = new google.visualization.LineChart(
						document
							.getElementById('Activity_trends'));
					chart.draw(data, options);
				}
			}
		}
	});
	$.ajax({
		url: "/chart/company/" + companyId + "/report/activity/"  + option,
		method: "get",
		contentType: "application/json",
		success: function(result) {
			console.log(result.companyActivies);
			if (result.companyActivies != null) {
				google.charts.load('current', {
					'packages': ['corechart']
				});
				google.charts
					.setOnLoadCallback(drawChart);
				function drawChart() {
					var data = google.visualization
						.arrayToDataTable(result.companyActivies);
					var options = {
						title: 'Activities'
					};
					var chart = new google.visualization.PieChart(
						document
							.getElementById('Activity_Chart'));
					chart.draw(data, options);
					new google.visualization.LineChart(
						document
							.getElementById('Activity_trends'));
					chart.draw(data, options);
				}
			}
		}
	});
$.ajax({
	url: "/chart/company/" + companyId + "/report/user-activity/" + option,
	method: "get",
	contentType: "application/json",
	success: function(result) {
		if (result.endUserActivies != null) {
			google.charts.load('current', {
				'packages': ['corechart']
			});
			google.charts.setOnLoadCallback(drawChartContactActivity);
			function drawChartContactActivity() {
				var data = google.visualization.arrayToDataTable(result.endUserActivies);
				var options = {
					title: 'Contact Activities'
				};
				var chart = new google.visualization.PieChart(document
					.getElementById('Contact_Activity_Chart'));
				chart.draw(data, options);
			}
		}
	}
	});

$.ajax({
	url: "/chart/company/" + companyId + "/report/user-activity-trend-unknown/"  + option,
	method: "get",
	contentType: "application/json",
	success: function(result) {
		console.log(result.endUserActiviesTrend);
		if (result.endUserActiviesTrend != null) {
			google.charts.load('current', { 'packages': ['bar'] });
			google.charts.setOnLoadCallback(drawChart);
			function drawChart() {
				var data = google.visualization
					.arrayToDataTable(result.endUserActiviesTrend);
				var options = {
					title: 'Unknown User Activities'
				};
				var chart = new google.charts.Bar(
					document
						.getElementById('unknown_contact_activity_trend'));
				chart.draw(data, google.charts.Bar.convertOptions(options));
			}
		}
		}
	});

$.ajax({
	url: "/chart/company/" + companyId + "/report/user-activity-trend-known/" + option,
	method: "get",
	contentType: "application/json",
	success: function(result) {
		console.log(result.endUserActiviesTrend);
		if (result.endUserActiviesTrend != null) {
			google.charts.load('current', { 'packages': ['bar'] });
			google.charts.setOnLoadCallback(drawChart);
			function drawChart() {
				var data = google.visualization
					.arrayToDataTable(result.endUserActiviesTrend);
				var options = {
					title: 'Known User Activities'
				};
				var chart = new google.charts.Bar(
					document
						.getElementById('known_contact_activity_trend'));
				chart.draw(data, google.charts.Bar.convertOptions(options));
			}
		}
	}
});

$.ajax({

	url: "/chart/company/" + companyId + "/social-media-activity/" + option,
	method: "get",
	contentType: "application/json",
	success: function(result) {
		console.log(result.socialMediaTrend);
		if (result.socialMediaTrend != null) {
			google.charts.load('current', { 'packages': ['bar'] });
			google.charts.setOnLoadCallback(drawChart);
			function drawChart() {
				var data = google.visualization
					.arrayToDataTable(result.socialMediaTrend);
				var options = {
					title: 'Social Media Activities'
				};
				var chart = new google.charts.Bar(
					document
						.getElementById('social_media_activity_trend'));
				chart.draw(data, google.charts.Bar.convertOptions(options));
			}
		}
	}
});

$.ajax({
	url: "/chart/company/" + companyId + "/report/count/" + option,
	method: "get",
	contentType: "application/json",
	success: function(result) {
			google.charts.load('current', { 'packages': ['table'] });
			google.charts.setOnLoadCallback(drawTable);
			function drawTable() {
				var data = new google.visualization.DataTable();
				data.addColumn('string', 'Name');
				data.addColumn('number', 'Count');
				data.addRows([
					['Sessions', result.sessionCount],
					['Requests', result.requestCount]
				]);

				var table = new google.visualization.Table(document.getElementById('activity-count'));
				table.draw(data, { showRowNumber: true, width: '100%', height: '100%' });
			}
	}
});

$.ajax({
	url: "/chart/company/" + companyId + "/report/geo/" + option,
	method: "get",
	contentType: "application/json",
	success: function(result) {
		if (result.geoActivities != null) {
			google.charts.setOnLoadCallback(drawRegionsMap);
			function drawRegionsMap() {
				var data = google.visualization.arrayToDataTable(result.geoActivities);
				var options = {};
				var chart = new google.visualization.GeoChart(document.getElementById('GEO_Chart'));
				chart.draw(data, options);
			}
		}
	}
});
}     