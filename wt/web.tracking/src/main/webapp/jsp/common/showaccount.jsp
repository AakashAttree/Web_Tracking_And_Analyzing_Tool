
<%
  String companyId = request.getParameter("compId");
			String companyName = request.getParameter("compName");
			String userType = request.getParameter("userType");

			boolean isSA = userType != null && userType.equalsIgnoreCase("sa") ? true : false;
%>
<script type="text/javascript">

$(document).ready(function(){
	  $("#pages").jqGrid({
		  datatype: "json",
		   url:'/company/<%=companyId%>/account/get',
		   	colNames:[
		   	          "id",
		   	          "compId",
		   	          "First Name",
		   	          "Last Name",
		   	          "User Name",
		   	          "Password",
		   	          "Email",
		   	          "Mobile",
		   	          "Administrator",
		   	          "User",
		   	       	  "createdBy",
		   	          "modifiedBy",
		   	          "createdTS",
		   	          "modifiedTS"
		   	         ],
		   	colModel:[
			   	        {name:'id',index:'id', width:100,hidden:true,editable:true},
			   	        {name:'compId',index:'compId', width:100, hidden:true, editable:true},
						{name:'firstName',index:'firstName', width:100,hidden:false,editable:true},
						{name:'lastName',index:'lastName', width:100,hidden:false,editable:true},
						{name:'userName',index:'userName', width:100,hidden:false,editable:true},
						{name:'password',index:'password', width:100,hidden:false,editable:true},
						{name:'email',index:'email', width:100,hidden:false,editable:true},
						{name:'mobile',index:'mobile', width:100,hidden:false,editable:true},
			   	        {name:'adminrole',index:'adminrole', width:100,hidden:<%=!isSA%>,editable:<%=isSA%>,edittype:'checkbox',editoptions:{value:"Yes:No",defaultValue:"No"}},
			   	        {name:'userrole',index:'userrole', width:100,hidden:false,editable:true,edittype:'checkbox',editoptions:{value:"Yes:No",defaultValue:"No"}},
			   	     	{name:'createdBy',index:'createdBy', width:100,hidden:true,editable:true},
			   	  		{name:'modifiedBy',index:'modifiedBy', width:100,hidden:true,editable:true},
			   			{name:'createdTS',index:'createdTS', width:100,hidden:true,editable:true},
			   			{name:'modifiedTS',index:'modifiedTS', width:100,hidden:true,editable:true}
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
		    editurl: '/company/<%=companyId %>/account/post', // this is dummy existing url
							caption : "Accounts"
						});
				jQuery("#pages").jqGrid('navGrid', '#ptoolbarCompany', {
					del : true,
					add : true,
					edit : true,
					search : true
				}, {
					width : $(document).width() - 30,
					height : $(document).height() - 300,
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

			});
</script>
<table id="pages" width="100%" align="center"></table>
<div id="ptoolbarCompany"></div>