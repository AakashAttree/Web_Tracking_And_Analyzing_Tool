<jsp:include page="/jsp/header.jsp"></jsp:include>

<%

String companyId = (String)session.getAttribute("compId");
String companyName = (String)session.getAttribute("compName");

%>
<jsp:include page="/jsp/common/showaccount.jsp">
	<jsp:param value="<%=companyId %>" name="compId" />
	<jsp:param value="<%=companyName %>" name="compName" />
	<jsp:param value="admin" name="userType" />
</jsp:include>


<jsp:include page="/jsp/footer.jsp"></jsp:include>