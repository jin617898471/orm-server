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
	<title>授权管理</title>
	<link rel="stylesheet" type="text/css" href="resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="resources/commons/css/widget.css">
    <link rel="stylesheet" type="text/css" href="resources/commons/css/theme.css">
<!--     <link rel="stylesheet" type="text/css" href="orm/commons/css/Manage.css" /> -->
    <link rel="stylesheet" type="text/css" href="orm/authority/authorize/css/authorizeManage.css" />
    <script src="authority/system/js" ></script>
</head>
<body>
	<div class="main-repeat layout-left-center">
		<div class="col-main">
			<div class="col-sub">
				<div class="colsub-container">
					<div class="ui-select-trigger ui-select-trigger-none" id="systemId">
						<span data-role="trigger-content"></span>
						<span class="trigger-ico"></span>
					</div>
					<div class="role-list">
						<div class="role-list-title">角色列表：</div>
						<div class="role-list-items">
							<div class="ui-scroll">
								<div class="ui-scroll-container">
									<div class="role-list-content">
										<ul id="roleList">
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
					
				</div>
			</div>
			<div class="main-wrap">
				<div class="main-container">
					<div class="main-title">
						<img src="orm/commons/css/imgs/person.png" alt="" class="main-title-ico" />
						<span id="roleName"></span>
					</div>
					<ul id="tree" class="ztree" style="width:259px;overflow-y:auto;overflow-x:auto;height:538px"></ul>
				</div>
				<div class="subadd-bottom" style="
					    border-top: 1px solid #e1e3e5;
					    padding: 10px;
					    position: fixed;
					    bottom: 0;
					    left: 308px;
					    right: 20px;
					">
					<div class="subadd-bottom-operate" style="
					    float: right;
					    height: 32px;
					">
						<input type="button" class="operate-items items-save" value="保存" style="
						    width: 80px;
						    height: 32px;
						    border-radius: 2px;
						    border: 0;
						    background: #298cef;
						    color: #ffffff;
						    font-size: 14px;
						">
					</div>
					<div class="subadd-bottom-status" style="display:none;">
						<i class="iconfont" style="
						    color: #34cb89;
						    margin: 0 5px;
						    font-size: 24px;
						">&#xf00a0;</i>
						<span>保存成功！</span>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	seajs.use("${basePath}orm/authority/authorize/js/authorizeManage.js");
    </script>
	
</body>
</html>