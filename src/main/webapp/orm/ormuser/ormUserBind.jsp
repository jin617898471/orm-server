<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%
	/************************ jsp 头 模 块 ***************************/
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE unspecified PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
<base href="<%=basePath%>">
<style></style>
</head>
<body>
	<form class="ui-form clearfix" id="UserADEForm" data-widget="validator">
		<div class="ui-form-container">
			<div class="ui-form-2item">
				<div class="ui-form-item-column clearfix">
					<div class="ui-form-item-2col">
						<label class="ui-label">用户名称:</label>
						<input class="ui-input ui-input-w190 userAcctCn" name="userAcctCn" value="${OrmUser.userAcctCn}">
					</div>
				</div>
			</div>
		</div>
		<div class="ui-form-placeholder">
		</div>
		<div class="ui-form-fixed">
			<div class="ui-form-message">
				<i class="iconfont msgiconfont"></i>
				<span class = "msgText"></span>
			</div>
			<div class="ui-form-toolbar">
				<input type="submit" class="ui-button " id="btnSave" value="添加">
<!-- 			 	<input type="button" class="ui-button " id="btnCancel" value="关闭">					 -->
			</div>
		</div>
	<input type="hidden" name = "sign" value = "${sign}">
	<input type="hidden" name = "userId" value = "${OrmUser.userId}">
	<input type="hidden" name = "oserial" value = "${OrmUser.oserial}">
	<input type="hidden" name = "orgId" value = "${orgId}">
	</form>
</body>
<script type="text/javascript" src="resources/commons/js/seajs/sea-debug.js" ></script>
<script type="text/javascript" src="resources/commons/js/seajs/sea-config-debug.js"></script>
<script type="text/javascript">
	seajs.use( "<%=basePath%>orm/organization/ormuser/js/ormUserBind" );
</script>
</html>