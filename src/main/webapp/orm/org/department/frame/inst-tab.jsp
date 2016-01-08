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
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>部门管理</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>orm/org/department/css/dep-manage.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>orm/org/department/css/tabs-common.css" />
</head>
<body>
	<div class="wrap-title">
		<img src="<%=basePath%>/orm/css/imgs/ajob.png" alt="" class="wrap-title-img" />
		<span>${org.orgName}</span>
	</div>
	<div class="dep-tabs">
		<ul class="dep-tabs-nav">
			<li>基本信息</li>
			<li>下属部门</li>
			<li>下属岗位</li>
			<li>下属人员</li>
		</ul>
		<div class="dep-tabs-content">
			<div class="hidden content-panel">
				<div class="basic">
					<form id="org-info">
						<ul class="basic-list">
							<li class="basic-list-items">
								<label class="list-items-label">机构名称：</label>
								<input type="text" class="ui-input list-items-inp" name="orgName" value="${org.orgName}" >
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">机构简称：</label>
								<input type="text" id="org-name-short" class="ui-input list-items-inp" name="orgNameShort" value="${org.orgNameShort}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">机构代码：</label>
								<input type="text" class="ui-input list-items-inp" name="orgCode" value="${org.orgCode}" >
							</li>

							<li class="basic-list-items">
								<label class="list-items-label">所属地域：</label>
								<input type="text" class="ui-input list-items-inp" name="orgArea" value="${org.orgArea}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">联系电话：</label>
								<input type="text" class="list-items-inp" name="orgPhone" value="${org.orgPhone}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">联系人：</label>
								<input type="text" class="list-items-inp" name="orgLinkman" value="${org.orgLinkman}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">机构邮箱：</label>
								<input type="text" class="list-items-inp" name="orgEmail" value="${org.orgEmail}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">机构网站：</label>
								<input type="text"  class="list-items-inp" name="orgWeburl" value="${org.orgWeburl}" />
							</li>
							<li class="basic-list-items fullw">
								<label class="list-items-label">机构邮编：</label>
								<input type="text" class="list-items-inp" name="orgPostcode" value="${org.orgPostcode}" />
							</li>
							<li class="basic-list-items fullw">
								<label class="list-items-label">机构地址：</label>
								<input type="text" class="list-items-inp inp-address" name="orgAddress" value="${org.orgAddress}" />
							</li>
						</ul>
						<div class="basic-other"></div>
						<div class="basic-operate">
							<div class="operate-btns">
								<input type="button" class="operate-btns-items" id="save" value="保存" />
								<!--<input type="button" class="operate-btns-items" value="取消" />-->
							</div>
							<div class="operate-status">
								<i class="iconfont">&#xf00a0;</i>
								<span class="status-message"></span>
							</div>
							<input type="hidden" id="orgId" name="orgId" value="${orgId}">
						</div>
					</form>
				</div>
			</div>
			<div class="hidden content-panel">
				<div class="subordinate">
					<div class="sub-title">
						<span class="sub-operate">
							<i class="iconfont">&#xe641;</i>
							<span>新增</span>
						</span>
						<span class="sub-title-text">下属部门列表</span>
					</div>
					<div class="sub-grid">
						<table id="dep-table"></table>
					</div>
				</div>
			</div>
			<div class="hidden content-panel">
				<div class="subordinate">
					<div class="sub-title">
						<span class="sub-operate">
							<i class="iconfont">&#xe641;</i>
							<span>新增</span>
						</span>
						<span class="sub-title-text">下属岗位列表</span>
					</div>
					<div class="sub-grid">
						<table id="post-table"></table>
					</div>
				</div>
			</div>
			<div class="hidden content-panel">
				<div class="subordinate">
					<div class="sub-title">
						<span class="sub-operate">
							<i class="iconfont">&#xe641;</i>
							<span>新增</span>
						</span>
						<span class="sub-title-text">下属人员列表</span>
					</div>
					<div class="sub-grid">
						<table id="emp-table"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="<%=basePath%>resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	seajs.use("<%=basePath%>orm/org/department/js/inst-tab.js");
    </script>
	
</body>
</html>