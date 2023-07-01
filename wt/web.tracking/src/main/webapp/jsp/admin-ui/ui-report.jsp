<jsp:include page="/jsp/header.jsp"></jsp:include>
<%
String companyId = (String)session.getAttribute("compId");
String companyName = (String)session.getAttribute("compName");
%>
<jsp:include page="/jsp/common/reports.jsp" flush="true">
	<jsp:param value="<%=companyId %>" name="compId"/>
</jsp:include>

<jsp:include page="/jsp/footer.jsp"></jsp:include>