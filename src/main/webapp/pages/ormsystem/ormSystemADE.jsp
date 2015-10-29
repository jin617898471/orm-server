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
	<form class="ui-form clearfix" id="systemADEForm"  data-widget="validator">
		<div class="ui-form-container">
			<div class="ui-form-2item">
				<div class="ui-form-item-column clearfix">
					<div class="ui-form-item-col">
						<label class="ui-label">系统名称:</label>
						<input class="ui-input ui-input-w190" name="systemName" id="systemName" value="${OrmSystem.systemName}">
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
					<div class="ui-form-item-col">
						<label class="ui-label">系统标示符:</label>
						<input class="ui-input ui-input-w190" name="systemCode" id="systemCode" value="${OrmSystem.systemCode}">
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
				</div>
				<div class="ui-form-item-have2col">
					<label class="ui-label">系统描述:</label>
					<div class="ui-textarea-container">
					    <div class="ui-textarea-border" style="height:80px;">
						    <textarea class="ui-textarea" name="systemDesc" id="systemDesc">${OrmSystem.systemDesc}</textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ui-form-fixed">
			<div class="ui-form-message">
			    <i class="iconfont msgiconfont"></i>
				<span class = "msgText"></span>
			</div>
			<div class="ui-form-toolbar">
				<input type="hidden" class="easyui-validatebox" name="systemId"value="${OrmSystem.systemId}"/>
				<input type="hidden" class="easyui-validatebox" name="validSign"value="Y"/>
				<input type="hidden" id = "sign" value = "${sign}">
				<input type="submit" class="ui-button " id="btnSave" value="保存">
				<input type="button" class="ui-button " id="btnCancel" value="关闭">
			</div>
		</div>
	</form>
</body>
	<script type="text/javascript" src="resources/commons/js/seajs/sea-debug.js" ></script>
    <script type="text/javascript" src="resources/commons/js/seajs/sea-config-debug.js"></script>
	<script>
		seajs.use("<%=basePath%>orm/resource/ormsystem/js/ormSystemADE");
	</script>
</html>