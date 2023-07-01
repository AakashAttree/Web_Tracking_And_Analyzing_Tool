<jsp:include page="/jsp/header.jsp"></jsp:include>
<%
String companyId = request.getParameter("compId");
String companyName = request.getParameter("compName");
String userType = request.getParameter("userType");

boolean isSA = userType != null && userType.equalsIgnoreCase("sa") ? true: false;

%>
<script type="text/javascript">

$(document).ready(function(){
	  $("#pages").jqGrid({
		  datatype: "json",
		   url:'/company/<%=companyId%>/config/get?masterConfigId=NIL',

		   	colNames:[
		   	          "id",
		   	          "masterConfigId",
		   	          "companyId",
		   	          "Name",
		   	          "Value",
		   	          "Description"
		   	         ],
		   	colModel:[
			   	        {name:'id',index:'id', width:100,hidden:true,editable:true},
			   	        {name:'masterConfigId',index:'masterConfigId', width:100, hidden:true, editable:false},
						{name:'companyId',index:'companyId', width:100,hidden:true,editable:false},
						{name:'name',index:'name', width:100,hidden:false,editable:true},
						{name:'value',index:'value', width:100,hidden:false,editable:true},
						{name:'description',index:'description', width:100,hidden:false,editable:true}
					],
		   	rowNum:10,
		   	rowTotal: 50,
		   	rowList:[10,20,30],
		   	pager: '#ptoolbarCompany',
		   	sortname: 'name',
		   	width:$(document).width()-30,
		   	height:$(document).height()-300,
		   	loadonce: true,
		    viewrecords: true,
		    sortorder: "desc",
		    editurl: '/company/<%=companyId%>/config/post?masterConfigId=NIL', // this is dummy existing url
		    caption:"Company Configurations",
			multiselect: false,
			subGrid: true,
			subGridRowExpanded: function(subgrid_id, row_id) {
				// we pass two parameters
				// subgrid_id is a id of the div tag created whitin a table data
				// the id of this elemenet is a combination of the "sg_" + id of the row
				// the row_id is the id of the row
				// If we wan to pass additinal parameters to the url we can use
				// a method getRowData(row_id) - which returns associative array in type name-value
				// here we can easy construct the flowing
				var subgrid_table_id, pager_id;
				subgrid_table_id = subgrid_id+"_t";
				pager_id = "p_"+subgrid_table_id;
				console.log(row_id);
				console.log(subgrid_id);
				var getSubGridURL= '/company/<%=companyId%>/config/get?masterConfigId='+row_id;
				var editSubGridURL= '/company/<%=companyId%>/config/post?masterConfigId='+row_id;
				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
				jQuery("#"+subgrid_table_id).jqGrid({
					url:getSubGridURL,
					editurl: editSubGridURL,
					datatype: "json",
					colNames: [
					   	          "id",
					   	          "masterConfigId",
					   	          "companyId",
					   	          "Name",
					   	          "Value",
					   	          "Description"
					   	         ],
					colModel: [
						 {name:'id',index:'id', width:100,hidden:true,editable:true},
			   	        {name:'masterConfigId',index:'masterConfigId', width:100, hidden:true, editable:false},
						{name:'companyId',index:'companyId', width:100,hidden:true,editable:false},
						{name:'name',index:'name', width:100,hidden:false,editable:true},
						{name:'value',index:'value', width:100,hidden:false,editable:true},
						{name:'description',index:'description', width:100,hidden:false,editable:true}
					],
				   	rowNum:20,
				   	pager: pager_id,
				   	sortname: 'num',
				    sortorder: "asc",
				    height: '100%'
				});
				jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:true,add:true,del:true})
			},
			subGridRowColapsed: function(subgrid_id, row_id) {
				// this function is called before removing the data
				//var subgrid_table_id;
				//subgrid_table_id = subgrid_id+"_t";
				//jQuery("#"+subgrid_table_id).remove();
			}
		});
		 jQuery("#pages").jqGrid('navGrid','#ptoolbarCompany',{
			 del:true,
			 add:true,
			 edit:true,
			 search:true
		 },
		 {
			 width:$(document).width()-30,
			 height:$(document).height()-300,
			 afterSubmit : function(response, postdata) 
			 { 
				 console.log(response);
				 if(response.status == 200){
					 console.log("Response : 200");
					 window.location.reload(true);
				 }
				 return [true];
			 }
		 },
		 {
			 afterSubmit : function(response, postdata) 
			 { 
				 if(response.status == 200){
					 console.log("Response : 200");
					 window.location.reload(true);
				 }
				 return [true];
			 }
		 },
		 {
			 afterSubmit : function(response, postdata) 
			 { 
				 console.log(response);
				 return [true];
			 }
		 },
		 {
			 afterSubmit : function(response, postdata) 
			 { 
				 console.log(response);
				 return [true];
			 }
		 },
		 {
			 afterSubmit : function(response, postdata) 
			 { 
				 console.log(response);
				 return [true];
			 }
		 }
		 ); 
		//jQuery("#pages").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true});
	  
		
		
});

</script>
<table id="pages" width="100%" align="center"></table>
<div id="ptoolbarCompany"></div>
<jsp:include page="/jsp/footer.jsp"></jsp:include>