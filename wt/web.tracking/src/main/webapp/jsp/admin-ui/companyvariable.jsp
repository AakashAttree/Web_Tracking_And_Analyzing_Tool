<jsp:include page="/jsp/header.jsp"></jsp:include>
<%
String companyId = (String)session.getAttribute("compId");
String companyName = (String)session.getAttribute("compName");
String companyVariableURL = "/company/"+companyId+"/get/variables";
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
					"Description",
					"Is Unique Id",
					"Type"
		   	         ],
		   	colModel:[
			   	        {name:'id',index:'id', width:100,hidden:true,editable:true},
			   	        {name:'compId',index:'compId', width:100,hidden:true,editable:true},
			   	     	{name:'name',index:'name', width:100,editable:true},
			   	  		{name:'requestParameter',index:'requestParameter', width:100,editable:true},
			   			{name:'defaultValue',index:'defaultValue', width:100,editable:true},
			   			{name:'includeInReport',index:'includeInReport', width:100,editable:true,edittype:'checkbox',editoptions:{value:"Yes:No",defaultValue:"No"}},
			   			{name:'description',index:'description', width:100,editable:true},
			   			{name:'uniqueId',index:'uniqueId', width:100,editable:true,edittype:'checkbox',editoptions:{value:"Yes:No",defaultValue:"No"}},
			   			{name:'type',index:'type', width:100,editable:true,edittype:'select',editoptions:{value:"email:Email;"}}
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
		    editurl: "/company/<%=companyId %>/edit/variables", // this is dummy existing url
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