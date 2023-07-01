<jsp:include page="/jsp/header.jsp"></jsp:include>
<script type="text/javascript">

$(document).ready(function(){
	  $("#pages").jqGrid({
		  datatype: "json",
		   url:'/getcompany',
		   	colNames:['companyId',
		   	          'Name',
		   	          'Domain Name'
		   	         ],
		   	colModel:[
						{name:'companyId',index:'companyId', width:100,hidden:true,editable:true},
						{name:'name',index:'name', width:100,hidden:false,editable:true,editrules: {required: true}},
						{name:'domainName',index:'domainName', width:100,hidden:false,editable:true,editrules: {required: true}}
					],
		   	rowNum:10,
		   	rowTotal: 50,
		   	rowList:[10,20,30],
		   	pager: '#ptoolbarCompany',
		   	sortname: 'companyId',
		   	width:$(document).width()-30,
		   	height:$(document).height()-300,
		   	loadonce: true,
		    viewrecords: true,
		    sortorder: "desc",
		    editurl: '/savecompany', // this is dummy existing url
		    caption:"Companies"
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