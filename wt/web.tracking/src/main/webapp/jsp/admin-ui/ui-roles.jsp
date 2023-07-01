<jsp:include page="/jsp/header.jsp"></jsp:include>
<%
String companyId = (String)session.getAttribute("compId");
String companyName = (String)session.getAttribute("compName");
%>
<script type="text/javascript">

$(document).ready(function(){
	  $("#pages").jqGrid({
		  datatype: "json",
		   url:'/company/'+companyId+'/roles/get',
		   	colNames:[
		   	          "roleId",
		   	          "compId",
		   	          "Role Name",
		   	          "Description"
		   	         ],
		   	colModel:[
			   	        {name:'roleId',index:'roleId', width:100,hidden:true,editable:true},
			   	        {name:'compId',index:'compId', width:100,hidden:true,editable:true},
						{name:'roleName',index:'roleName', width:100,hidden:false,editable:true},
						{name:'description',index:'description', width:100,hidden:false,editable:true}
					],
		   	rowNum:10,
		   	rowTotal: 50,
		   	rowList:[10,20,30],
		   	pager: '#ptoolbarCompany',
		   	sortname: 'roleId',
		   	width:$(document).width()-30,
		   	height:$(document).height()-300,
		   	loadonce: true,
		    viewrecords: true,
		    sortorder: "desc",
		    editurl: '/company/'+companyId+'/roles/post', // this is dummy existing url
		    caption:"Accounts"
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