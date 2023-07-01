<jsp:include page="/jsp/header.jsp"></jsp:include>
<%
String companyId = (String)request.getParameter("compId");
String companyName = (String)request.getParameter("compName");

%>

<jsp:include page="/jsp/common/showaccount.jsp">
	<jsp:param value="<%=companyId %>" name="compId" />
	<jsp:param value="<%=companyName %>" name="compName" />
	<jsp:param value="sa" name="userType" />
</jsp:include>

<jsp:include page="/jsp/footer.jsp"></jsp:include>