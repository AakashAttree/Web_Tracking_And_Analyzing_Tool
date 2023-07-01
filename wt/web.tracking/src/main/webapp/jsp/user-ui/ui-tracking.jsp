<jsp:include page="/jsp/header.jsp"></jsp:include>
<%
	String companyId = (String) session.getAttribute("compId");
%>

<script type="text/javascript">

$(document).ready(function(){
	  $("#pages").jqGrid({
		  datatype: "json",
		   url:'/gettrackinginfo/<%=companyId%>',
		   	colNames:[
		   	          'Id',
		   	          'Page Name',
		   	          'Script',
		   	          'Created By',
		   	          'Created On',
		   	          'companyId'
		   	          <%--
		   	          ,
		   	          'variablesIds' --%>
		   	         ],
		   	colModel:[
						{name:'id',index:'id', width:100,hidden:true,editable:false},
						{name:'pageName',index:'pageName', width:100,hidden:false,editable:true,editrules: {required: true}},
						{name:'script',index:'script', width:100,hidden:false,editable:true, edittype:'textarea'},
						{name:'createdBy',index:'createdBy', width:100, editable:false},
						{name:'createdOn',index:'createdOn', width:100, editable:false},
						{name:'companyId',index:'companyId', width:100, editable:true}
						<%--,
						{name:'variablesIds',index:'variablesIds', width:100, editable:true, edittype:'select', editoptions: {multiple:true,dataUrl:'/company/<%=companyId%>/get/variables-select'}}
					--%>
					],
		   	rowNum:10,
		   	rowTotal: 50,
		   	rowList:[10,20,30],
		   	pager: '#ptoolbarCompany',
		   	sortname: 'Id',
		   	width:$(document).width()-30,
		   	height:$(document).height()-300,
		   	loadonce: true,
		    viewrecords: true,
		    sortorder: "desc",
		    editurl: '/saveclient/<%=companyId%>', // this is dummy existing url
		    caption:"Pages"
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