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
	<title>系统管理</title>
	<link rel="stylesheet" type="text/css" href="resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="resources/commons/css/widget.css">
    <link rel="stylesheet" type="text/css" href="resources/commons/css/theme.css">
    <link rel="stylesheet" type="text/css" href="orm/authority/system/css/systemManage.css" />
</head>
<body>
	<div class="user-condition">
		<ul class="clearfix">
			<li class="condition-items">
				<label class="items-label">系统名称：</label>
				<input type="text" class="orm-input items-inps" rule-op="like" rule-field="systemName" />
			</li>
			<li class="condition-items">
				<label class="items-label">系统代码：</label>
				<input type="text" class="orm-input items-inps" rule-op="like" rule-field="systemCode" />
			</li>
		</ul>
	</div>
	<div class="user-btns">
		<div class="fr">
			<input type="button" class="orm-button user-btns-check opt-seach" value="查询" />
			<input type="button" class="orm-button opt-reset" value="重置" />
		</div>
	</div>
	<div class="manage-tab">
		<div class="tab-title">
			<span class="tab-operate">
				<span class="operate-spans opt-deletes">
					<img src="orm/authority/system/css/imgs/code-ico1.png" alt="" class="operate-spans-ico" />
					<span>批量删除</span>
				</span>
				<span class="operate-spans">
					<img src="orm/authority/system/css/imgs/code-ico2.png" alt="" class="operate-spans-ico" />
					<span>刷新</span>
				</span>
				<span class="operate-spans opt-fadd">
					<img src="orm/authority/system/css/imgs/code-ico3.png" alt="" class="operate-spans-ico" />
					<span>新增</span>
				</span>
			</span>
			<span class="tab-title-text">系统管理</span>
		</div>
		<div class="tab-grid">
			<table id="grid-table"></table>
		</div>
	</div>

	<script src="resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	seajs.use("${basePath}orm/authority/system/js/systemManage.js");
    </script>
	
</body>
</html>