<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="/jsp/header.jsp" flush="true">
	<jsp:param value="login" name="page"></jsp:param>
</jsp:include>
<%--<form name="f" action="/login" method="POST">
<table>
	<tbody><tr><td>User:</td><td><input type="text" name="username" value=""></td></tr>
	<tr><td>Password:</td><td><input type="password" name="password"></td></tr>
	<tr><td colspan="2"><input name="submit" type="submit" value="Login"></td></tr>
</tbody></table>
</form>
--%>
<div>&nbsp;</div>
<div class="container text-center"
	style="margin-top: 30px; align-content: center;">
	<div class="row">
		<div class="col-sm-3" style=""></div>
		<div class="col-sm-6" style="">
			<form class="form-horizontal" action="/login" method="post">
				<div class="form-group">
					<label class="control-label col-sm-4" for="username">User
						Name:</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" name="username"
							id="username" placeholder="Enter user name">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-4" for="pwd">Password:</label>
					<div class="col-sm-6">
						<input type="password" class="form-control" name="password"
							id="password" placeholder="Enter password">
					</div>
				</div>
				<%--   <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      <div class="checkbox">
        <label><input type="checkbox"> Remember me</label>
      </div>
    </div>
  </div> --%>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-6">
						<button type="submit" class="btn btn-default">Login</button>
					</div>
				</div>
			</form>
		</div>
		<div class="col-sm-3" style=""></div>
	</div>
	<div>&nbsp;</div>
</div>
<jsp:include page="/jsp/footer.jsp"></jsp:include>