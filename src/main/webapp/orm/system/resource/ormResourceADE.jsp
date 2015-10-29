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
<!doctype html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>orm/resource/ormresource/css/ResourcesManager.css">
<base href="<%=basePath%>">
<style></style>
</head>
<body>
	<form class="ui-form clearfix" id="ResourceADEForm" data-widget="validator">
		<div class="ui-form-container">
			<div class="ui-form-2item">
				<div class="ui-form-item-column clearfix">
					<div class="ui-form-item-col">
						<label class="ui-label">中文名称:</label>
						<input class="ui-input ui-input-w190" id="resourceNameCn" name="resourceNameCn" value="${OrmResource.resourceNameCn}">
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
					<div class="ui-form-item-col">
						<label class="ui-label">类型:</label>
						<a class="ui-select-trigger ui-select-trigger-none resourceType" style = "width:188px">
	            				<span data-role="trigger-content"></span>          
	            				<i class="iconfont blue" title="下三角形">&#xf0044;</i>
	        			</a>
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
				</div>
				<div class="ui-form-item-column clearfix">
					<div class="ui-form-item-col">
						<label class="ui-label">所属系统:</label>
						<a class="ui-select-trigger ui-select-trigger-none systemId" style = "width:188px">
	            				<span data-role="trigger-content"></span>          
	            				<i class="iconfont blue" title="下三角形">&#xf0044;</i>
	        			</a>
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
					<div class="ui-form-item-col">
						<label class="ui-label">URL:</label>
						<input class="ui-input ui-input-w190"  name ="resourceUrl" value="${OrmResource.resourceUrl}">
					</div>
				</div>
				<div class="ui-form-item-column clearfix">
					<div class="ui-form-item-col">
						<label class="ui-label">英文名称:</label>
						<input class="ui-input ui-input-w190"  name ="resourceNameEn" value="${OrmResource.resourceNameEn}">
					</div>
					<div class="ui-form-item-col">
						<label class="ui-label">包名:</label>
						<input class="ui-input ui-input-w190"  name ="resourcePackage" value="${OrmResource.resourcePackage}">
					</div>
				</div>
				<div class="ui-form-item-column clearfix">
					<div class="ui-form-item-col">
						<label class="ui-label">关键字:</label>
						<input class="ui-input ui-input-w190"  name ="resourceTag" value="${OrmResource.resourceTag}">
					</div>
				</div>
				<div class="ui-form-item-have2col">
					<label class="ui-label">资源描述:</label>
					<div class="ui-textarea-container">
					    <div class="ui-textarea-border" style="width:92.5%;height:80px;">
						    <textarea class="ui-textarea" name="resourceDesc">${OrmResource.resourceDesc}</textarea>
						</div>
					</div>
				</div>
				<div class="ui-form-item-column clearfix" style = "display:none">
					<div class="ui-form-item-col">
						<label class="ui-label">presId:</label>
						<input class="ui-input ui-input-w190"  name ="presId" value="${OrmResource.presId}">
					</div>
					<div class="ui-form-item-col">
						<label class="ui-label">resourceId:</label>
						<input class="ui-input ui-input-w190"  name ="resourceId" value="${OrmResource.resourceId}">
					</div>
					<div class="ui-form-item-col">
						<label class="ui-label">validSign:</label>
						<input class="ui-input ui-input-w190"  name ="validSign" value="Y">
					</div>
					<div class="ui-form-item-col">
						<label class="ui-label">resourceSerial:</label>
						<input class="ui-input ui-input-w190"  name ="resourceSerial" value="0000">
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
				<input type="submit" class="ui-button " id="btnSave" value="保存">
			 	<input type="button" class="ui-button " id="btnCancel" value="关闭">					
			</div>
		</div>
	</form>
	<input type="hidden" id = "sign" value = "${sign}">
	<input type="hidden" id = "resourceType" value = "${OrmResource.resourceType}">
	<input type="hidden" id = "systemId" value = "${OrmResource.systemId}">
</body>
<script type="text/javascript" src="resources/commons/js/seajs/sea-debug.js" ></script>
<script type="text/javascript" src="resources/commons/js/seajs/sea-config-debug.js"></script>
<script type="text/javascript">
	seajs.use( "<%=basePath%>orm/resource/ormresource/js/ormResourceADE" );
</script>
</html>