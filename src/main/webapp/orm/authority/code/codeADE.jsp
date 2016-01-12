<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<base href="${basePath}" />
	<link rel="stylesheet" type="text/css" href="resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="resources/commons/css/widget.css">
    <link rel="stylesheet" type="text/css" href="resources/commons/css/theme.css">
    <link rel="stylesheet" type="text/css" href="orm/commons/css/ADE.css" />
    <script src="authority/system/js" ></script>
</head>
<body>
	<div class="subadd-container">
		<input type="text" class="subadd-items-inp" style="display:none;" name="codeId" value="${ormCode.codeId}"/>
		<input type="text" class="subadd-items-inp" style="display:none;" name="parentCodeId" value="${ormCode.parentCodeId}"/>
		<input type="text" class="subadd-items-inp" style="display:none;" name="systemId" value="${ormCode.systemId}"/>
		<ul>
			<li class="subadd-items subadd-items-required">
				<label class="subadd-items-label">代码名称：</label>
				<input type="text" class="subadd-items-inp" name="codeName" value="${ormCode.codeName}"/>
				<span class="subadd-items-star">*</span>
				<span class="items-error-text">请输入代码名称！</span>
			</li>
			<li class="subadd-items subadd-items-required">
				<label class="subadd-items-label">代码值：</label>
				<input type="text" class="subadd-items-inp" name="codeValue" value="${ormCode.codeValue}"/>
				<span class="subadd-items-star">*</span>
				<span class="items-error-text">请输入代码值！</span>

			</li>
			<li class="subadd-items">
				<label class="subadd-items-label">是否权限控制代码：</label>
				<div class="ui-select-trigger ui-select-trigger-none w218" id="isRight" value="${ormCode.isRight}">
					<span data-role="trigger-content"></span>
					<span class="trigger-ico"></span>
				</div>
			</li>
		</ul>
		<div class="subadd-bottom">
			<div class="subadd-bottom-operate">
				<input type="button" class="operate-items items-save" value="保存" />
				<input type="button" class="operate-items" value="取消" />
			</div>
			<div class="subadd-bottom-status">
				<i class="iconfont">&#xf00a0;</i>
				<span>保存成功！</span>
			</div>
		</div>
	</div>

	<script src="resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	var sign="${sign}"
    	seajs.use("${basePath}orm/authority/code/js/codeADE.js");
    </script>
	
</body>
</html>