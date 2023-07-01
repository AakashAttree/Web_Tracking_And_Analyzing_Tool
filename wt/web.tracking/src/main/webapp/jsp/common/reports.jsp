
<%
  String companyId = request.getParameter("compId");
String companyName = request.getParameter("compName");
%>

<script type="text/javascript">

$(document).ready(function(){
	  $("#report").jqGrid({
		  datatype: "json",
		   url:'/report/session-cluster/<%=companyId%>',
		   	colNames:[
		   		"id",
		   		"contactsInSession",
		   		"sessionId",
		   		"numberOfRequests",
		   		"averageRequestTime",
		   		"totalDurationOfSession",
		   		"numberOfDistinctPageVisit",
		   		"numberOfDistinctActivities",
		   		"knownContact",
		   		"hasSocialMediaId",
		   		"cluster",
		   		"customer"
		   	         ],
		   	colModel:[
		   		{name:'id',index:'id', width:100,hidden:true,editable:true},
		   		{name:'contactsInSession',index:'contactsInSession', width:100,hidden:false,editable:false},
		   		{name:'sessionId',index:'sessionId', width:100,hidden:true,editable:true},
		   		{name:'numberOfRequests',index:'numberOfRequests', width:100,hidden:false,editable:false},
		   		{name:'averageRequestTime',index:'averageRequestTime', width:100,hidden:false,editable:false},
		   		{name:'totalDurationOfSession',index:'totalDurationOfSession', width:100,hidden:false,editable:false},
		   		{name:'numberOfDistinctPageVisit',index:'numberOfDistinctPageVisit', width:100,hidden:false,editable:false},
		   		{name:'numberOfDistinctActivities',index:'numberOfDistinctActivities', width:100,hidden:false,editable:false},
		   		{name:'knownContact',index:'knownContact', width:100,hidden:false,editable:false},
		   		{name:'hasSocialMediaId',index:'hasSocialMediaId', width:100,hidden:false,editable:false},
		   		{name:'cluster',index:'cluster', width:100,hidden:false,editable:false},
		   		{name:'customer',index:'customer', width:100, hidden:false, editable:true, edittype:'checkbox', editoptions:{value:"true:false",  defaultValue: "false" } } 
					],
		   	rowNum:10,
		   	rowTotal: 50,
		   	rowList:[10,20,30],
		   	pager: '#ptoolbarCompany',
		   	sortname: 'numberOfRequests',
		   	loadonce: true,
		    viewrecords: true,
		    sortorder: "desc",
		    editurl: '/report/session-cluster/<%=companyId%>', // this is dummy existing url
							caption : "Session Cluster Report"
						});
				jQuery("#report").jqGrid('navGrid', '#ptoolbarCompany', {
					del : true,
					add : false,
					edit : true,
					search : true
				}, {
				
					afterSubmit : function(response, postdata) {
						console.log(response);
						if (response.status == 200) {
							console.log("Response : 200");
							window.location.reload(true);
						}
						return [ true ];
					}
				}, {
					afterSubmit : function(response, postdata) {
						if (response.status == 200) {
							console.log("Response : 200");
							window.location.reload(true);
						}
						return [ true ];
					}
				}, {
					afterSubmit : function(response, postdata) {
						console.log(response);
						return [ true ];
					}
				}, {
					afterSubmit : function(response, postdata) {
						console.log(response);
						return [ true ];
					}
				}, {
					afterSubmit : function(response, postdata) {
						console.log(response);
						return [ true ];
					}
				});
				//jQuery("#pages").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true});
				jQuery("#report").navGrid("#ptoolbarCompany").navButtonAdd("#ptoolbarCompany",{ caption:"Predict", buttonicon:"ui-icon-newwin", 
					onClickButton:function(cellvalue){
						var rowId = jQuery("#report").jqGrid('getGridParam','selrow');  
						if(rowId == null || rowId == undefined){
							alert("Please select a row");
							return false;
						}
						var rowData = jQuery("#report").getRowData(rowId);
						requestData = {};
						requestData.numberOfRequests=rowData['numberOfRequests'];
						requestData.averageRequestTime=rowData['averageRequestTime'];
						requestData.totalDurationOfSession=rowData['totalDurationOfSession'];
						requestData.numberOfDistinctPageVisit=rowData['numberOfDistinctPageVisit'];
						requestData.numberOfDistinctActivities=rowData['numberOfDistinctActivities'];
						requestData.knownContact=rowData['knownContact'];
						requestData.hasSocialMediaId=rowData['hasSocialMediaId'];
						requestData.cluster=rowData['cluster'];
						$.ajax({
							url: "/prediction/customer",
							data: JSON.stringify(requestData),
							dataType : "json",
							contentType: "application/json",
					        method: "post",
					        success: function(result){
						        console.log(result);
					            alert("Prediction for future customer for selected data is "+result);
					        }
						});
					}, position: "last", title:"", cursor: "pointer"} );
				
			});
</script>
<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<table id="report"></table>
			<div id="ptoolbarCompany"></div>
		</div>
	</div>
</div>
