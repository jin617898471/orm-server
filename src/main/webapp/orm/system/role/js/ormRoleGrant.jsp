<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
%>
<!doctype html>
<html lang="en" style="overflow-y:hidden">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>orm/authority/ormauthrole/css/ormRoleGrant.css">
    <base href="<%=basePath%>">
</head>
<body>
    <input type="hidden" id="roleId" value="${OrmRole.roleId}">
    <input type="hidden" id="roleSystemId" value="${OrmRole.roleSystemId}">
	<div class="main">
		<div  class="ui-tabs-three">
			<ul class="ui-tabs-nav">
				<li>功能权限</li>
				<li>全局组织权限</li>
				<li>全局代码权限</li>
			</ul>
			<div class="ui-tabs-content">
				<div class="hidden panel">
					<div class="layout-left-center">
						<div class="col-main">
							<div class="col-sub">
								<div>
									<ul id="fn-tree" class="ztree" style="overflow-y:auto;overflow-x:auto;height:380px"></ul>
								</div>
							</div>
							<div class="main-wrap" id="fnAuthDetails" style="visibility:hidden">
								<div class="main-container">
									<h3>特殊组织权限和代码权限设置-- <label id="clkickNodeName_lbl"></label></h3>
									<p class="explain">说明：特殊权限有别于全局权限，两者的主要区别在于全局组织权限和全局代码权限作用于该角色所具有的功能权限，而特殊组织权限和代码权限只针对特定的功能，当两者发生冲突时，以特定权限为准。</p>
									<ul class="clearfix" style="height:28px;">
										<li style="height:28px;line-height:28px;float:left;width:265px;">
											<label style="font-size:12px;float:left">组织机构:</label>
											<div class="ui-select-trigger orgAuth" id="example1" style="width: 128px;margin-left:25px;">
           										<span data-role="trigger-content"></span>        
            									<i class="iconfont" title="下三角形">&#xf0044;</i>            
        									</div>
        									<span class="org-required">*</span>
										</li>
										<li style="height:28px;line-height:28px;float:left;width:265px;">
											<label style="font-size:12px;float:left">代码机构:</label>
											<div class="ui-select-trigger codeAuth" id="example1" style="width: 128px;margin-left:25px;">
           										<span data-role="trigger-content"></span>        
            									<i class="iconfont" title="下三角形">&#xf0044;</i>            
        									</div>
        									<span class="org-required">*</span>
										</li>
									</ul>
									<h2>已分配特殊权限</h2>
									<div class="org-header">
										<i class="org-line"></i>
										<h4>特殊组织机构权限</h3>
									</div>
									<dl id="checkedSpecOrgAuth">
									</dl>
									<div class="org-header">
										<i class="org-line"></i>
										<h4>特殊代码权限</h3>
									</div>
									<dl id="checkedSpecCodeAuth">
										<dt>
											<label>行政区划:</label>
											<span>浙江省、杭州市、萧山区、宁波市</span>
										</dt>
										<dt>
											<label>营运车辆经营类型:</label>
											<span>城市出租、危险品货物运输、客运班车、旅游包车</span>
										</dt>
									</dl>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="hidden panel">
					<div class="layout-left-center">
						<div class="col-main">
							<div class="col-sub">
								<div>
									<ul id="org-tree" class="ztree" style="overflow-y:auto;overflow-x:auto;height:380px"></ul>
								</div>
							</div>
							<div class="main-wrap GlobalOrgShow">
								<div class="main-container">
									<div class="org-header">
										<i class="org-line"></i>
										<h4>全局组织权限</h3>
									</div>
									<dl id="checkedGlobalOrgAuth">
									</dl>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="hidden panel">
					<div class="layout-left-center">
						<div class="col-main">
							<div class="col-sub">
								<div>
									<ul id="encode-tree" class="ztree" style="overflow-y:auto;overflow-x:auto;height:380px"></ul>
								</div>
							</div>
							<div class="main-wrap codeGroup" id="checkedGlobalCodeAuth">
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ui-form-fixed">
			<div class="ui-form-message">
				<i class="iconfont succeed" style="display:none;">&#xf007b;</i>
				<span class="msgText"></span>
			</div>
			<div class="ui-form-toolbar">
				<input type="button" class="ui-button " id="btnView" value="查看" style="display:none;">	
				<input type="button" class="ui-button " id="btnSave" value="保存">
			 	<input type="button" class="ui-button " id="btnCancel" value="关闭">	
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=basePath%>resources/commons/js/seajs/sea-debug.js" ></script>
    <script type="text/javascript" src="<%=basePath%>resources/commons/js/seajs/sea-config-debug.js"></script>
	<script type="text/javascript">
		seajs.use( "<%=basePath%>orm/authority/ormauthrole/js/ormRoleGrant" );
	</script>
</body>
</html>