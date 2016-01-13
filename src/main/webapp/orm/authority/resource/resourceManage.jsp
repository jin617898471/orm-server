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
	<title>资源管理</title>
	<link rel="stylesheet" type="text/css" href="resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="resources/commons/css/widget.css">
    <link rel="stylesheet" type="text/css" href="resources/commons/css/theme.css">
    <link rel="stylesheet" type="text/css" href="orm/commons/css/Manage.css" />
    <link rel="stylesheet" type="text/css" href="orm/authority/resource/css/resourceManage.css" />
    <script src="authority/system/js" ></script>
    <script src="orm/userinfo.js" ></script>
</head>
<body>
	<div class="main-repeat layout-left-center">
		<div class="col-main">
			<div class="col-sub">
				<div class="colsub-container">
					<div class="code-title">
						<img src="orm/commons/css/imgs/code-title.png" alt="" class="code-title-img" />
						<span>资源管理</span>
						<ul id="tree" class="ztree" style="width:259px;overflow-y:auto;overflow-x:auto;height:538px"></ul>
					</div>
				</div>
			</div>
			<div class="main-wrap">
				<div class="main-container">
					<div class="ui-scroll">
						<div class="ui-scroll-container">
							<div class="code-cond clearfix">
								<input type="text" class="orm-input code-inps" style="display:none;" rule-op="equal" rule-field="parentResId"  />
								<input type="text" class="orm-input code-inps" style="display:none;" rule-op="equal" rule-field="systemId"  />
								<ul>
									<li>
										<label class="code-label">资源名称：</label>
										<input type="text" class="orm-input code-inps" rule-op="like" rule-field="resourceName"  />
									</li>
									<li>
										<label class="code-label">资源代码：</label>
										<input type="text" class="orm-input code-inps" rule-op="like" rule-field="resourceCode"   />
									</li>
								</ul>
							</div>
							<div class="code-operate">
								<div class="operate-btns">
									<input type="button" class="orm-button btns-items btns-items-check opt-seach" value="查询" />
									<input type="button" class="orm-button btns-items opt-reset" value="重置" />
								</div>
							</div>
							<div class="manage-tab">
								<div class="tab-title">
									<span class="tab-operate">
										<span class="operate-spans opt-deletes">
											<img src="orm/commons/css/imgs/code-ico1.png" alt="" class="operate-spans-ico" />
											<span>批量删除</span>
										</span>
										<span class="operate-spans opt-reflash">
											<img src="orm/commons/css/imgs/code-ico2.png" alt="" class="operate-spans-ico" />
											<span>刷新</span>
										</span>
										<span class="operate-spans opt-fadd">
											<img src="orm/commons/css/imgs/code-ico3.png" alt="" class="operate-spans-ico" />
											<span>新增</span>
										</span>
									</span>
									<span class="tab-title-text">代码管理</span>
								</div>
								<div class="tab-grid">
									<table id="grid-table"></table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	seajs.use("${basePath}orm/authority/resource/js/resourceManage.js");
    </script>
	
</body>
</html>