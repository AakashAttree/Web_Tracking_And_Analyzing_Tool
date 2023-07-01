<jsp:include page="/jsp/header.jsp"></jsp:include>
<%
String companyId = (String)request.getParameter("compId");
%>
<jsp:include page="/jsp/common/showdashboard.jsp" flush="true">
	<jsp:param value="<%=companyId %>" name="compId"/>
</jsp:include>

<jsp:include page="/jsp/footer.jsp"></jsp:include>