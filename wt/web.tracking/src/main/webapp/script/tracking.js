/**
 * This is dependent on jquery
 */
function track(obj){
	if(obj == null || obj == undefined){
		obj = {};
	}
	obj.currentURL= window.location.href;
	obj.referrURL= document.referrer;
	if(obj.optype == null || obj.optype == undefined || obj.optype == ""){
		obj.optype = "visit";
	}
	obj.id = "$%companyId%$";
	domainName = "$%company.domain.name%$";
	$.ajax({
		url : domainName+"/track",
		method : "$%request_method%$",
		data : convertRequest(obj),
		crossDomain: true,
		dataType : "$%dataType%$",
		contentType : "application/json",
		$%jsonpCallback%$
		success : function(
				result) {
			
		}
	});
}
function logResults(variableValue){
	console.log(variableValue);
	alert(variableValue);
}

function convertRequest(obj){
	if("$%dataType%$" == "jsonp"){
		var varableValue = "";
		jQuery.each(obj, function(i, val) {
			varableValue += "&"+i +"="+val;
			});
		return varableValue;
	}
	return JSON
	.stringify(obj);
}
