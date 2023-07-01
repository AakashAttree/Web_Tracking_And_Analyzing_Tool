<jsp:include page="/jsp/header.jsp"></jsp:include>
<%
String companyId = (String)request.getParameter("compId");
String companyName = (String)request.getParameter("compName");

String companyVariableURL = "/company-variales/"+companyId;



%>
<script type="text/javascript">

$(document).ready(function(){
	  $("#pages").jqGrid({
		  datatype: "json",
		   url:'<%=companyVariableURL%>',
		   	colNames:[
					"id",
					"compId",
					"Name",
					"Request Parameter",
					"Default Value",
					"Include In Report",
					"Description"
		   	         ],
		   	colModel:[
			   	        {name:'id',index:'id', width:100,hidden:true,editable:true},
			   	        {name:'compId',index:'compId', width:100,hidden:true,editable:true},
			   	     	{name:'name',index:'name', width:100,editable:true},
			   	  		{name:'requestParameter',index:'requestParameter', width:100,editable:true},
			   			{name:'defaultValue',index:'defaultValue', width:100,editable:true},
			   			{name:'includeInReport',index:'includeInReport', width:100,editable:true},
			   			{name:'description',index:'description', width:100,editable:true},
					],
		   	rowNum:10,
		   	rowTotal: 50,
		   	rowList:[10,20,30],
		   	pager: '#ptoolbarCompany',
		   	sortname: 'firstName',
		   	width:$(document).width()-30,
		   	height:$(document).height()-300,
		   	loadonce: true,
		    viewrecords: true,
		    sortorder: "desc",
		    editurl: '<%=companyVariableURL %>', // this is dummy existing url
		    caption:"Variables"
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