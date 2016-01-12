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
	<title>用户管理</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/theme.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>orm/org/user/css/user-manage.css" />
</head>
<body>
	<div class="user-condition">
		<form id="searchFrom">
			<ul class="clearfix">
				<li class="condition-items">
					<label class="items-label">用户名称：</label>
					<input type="text" class="orm-input items-inps" name="userName"/>
				</li>
				<li class="condition-items">
					<label class="items-label">登录账户：</label>
					<input type="text" class="orm-input items-inps" name="userAcct"/>
				</li>
				<li class="condition-items">
					<label class="items-label">用户状态：</label>
					<input type="text" class="orm-input items-inps" name="userStatus"/>
				</li>
			</ul>
		</form>
	</div>
	<div class="user-btns">
		<div class="fr">
			<input type="button" id="search" class="orm-button user-btns-check" value="查询" />
			<input type="button" id="reset" class="orm-button" value="重置" />
		</div>
	</div>
	<div class="manage-tab">
		<div class="tab-title">
			<span class="tab-operate">
				<span class="operate-spans">
					<img src="<%=basePath%>orm/org/user/css/imgs/code-ico1.png" alt="" class="operate-spans-ico" />
					<span>批量删除</span>
				</span>
				<span class="operate-spans" id="refresh">
					<img src="<%=basePath%>orm/org/user/css/imgs/code-ico2.png" alt="" class="operate-spans-ico" />
					<span>刷新</span>
				</span>
				<span class="operate-spans" id="addUser">
					<img src="<%=basePath%>orm/org/user/css/imgs/code-ico3.png" alt="" class="operate-spans-ico" />
					<span>新增</span>
				</span>
			</span>
			<span class="tab-title-text">用户信息管理</span>
		</div>
		<div class="tab-grid">
			<table id="grid-table"></table>
		</div>
	</div>

	<script src="<%=basePath%>resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="<%=basePath%>resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	seajs.use("<%=basePath%>orm/org/user/js/user-manage.js");
    </script>
	
</body>
</html>