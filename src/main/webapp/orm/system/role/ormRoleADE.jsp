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
	<form class="ui-form clearfix" id="roleADEForm" data-widget="validator">
		<div class="ui-form-container">
			<div class="ui-form-2item">
				<div class="ui-form-item-column clearfix">
					<div class="ui-form-item-col">
						<label class="ui-label">中文名称:</label>
						<input class="ui-input ui-input-w190" name="roleNameCn" value="${OrmRole.roleNameCn}" style = "width:188px">
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
					<div class="ui-form-item-col">
						<label class="ui-label">英文名称:</label>
						<input class="ui-input ui-input-w190" name="roleNameEn"	value="${OrmRole.roleNameEn}" style = "width:188px">
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
				</div>
				<div class="ui-form-item-column clearfix" id="roleSystem" style = "display:none">
					<div class="ui-form-item-col " >
						<label class="ui-label">所属系统:</label>
						<a class="ui-select-trigger systemId" style = "width:188px">
            				<span data-role="trigger-content"></span>          
            				<i class="iconfont blue" title="下三角形">&#xf0044;</i>
        				</a>
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
					<!-- <div class="ui-form-item-col"  style="display: none">
						<label class="ui-label">角色类型:</label>
						<a class="ui-select-trigger ui-select-trigger-none roleType" style = "width:188px">
            				<span data-role="trigger-content"></span>          
            				<i class="iconfont blue" title="下三角形">&#xf0044;</i>
        				</a>
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div> -->
				</div>
				<!-- <div class="ui-form-item-column clearfix"  style="display: none">
					<div class="ui-form-item-col">
						<label class="ui-label">是否反向角色:</label>
						<a class="ui-select-trigger ui-select-trigger-none roleNegativeFlag" style = "width:188px">
            				<span data-role="trigger-content"></span>          
            				<i class="iconfont blue" title="下三角形">&#xf0044;</i>
        				</a>
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
					<div class="ui-form-item-col"  style="display: none">
						<label class="ui-label">所属机构:</label>
						<div class="ui-select-trigger org"   style="width: 188px">
				        <span data-role="trigger-content"></span>        
				       	<i class="iconfont" title="下三角形">&#xf0044;</i>
				    	</div>
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
				</div> -->
				<%-- <div class="ui-form-item-column clearfix">
					<div class="ui-form-item-col">
						<label class="ui-label">英文名称:</label>
						<input class="ui-input ui-input-w190" name="roleNameEn"	value="${OrmRole.roleNameEn}" style = "width:188px">
					</div>
				</div> --%>
				<div class="ui-form-item-have2col">
					<label class="ui-label">描述信息:</label>
					<div class="ui-textarea-container">
					    <div class="ui-textarea-border" style="width:92.5%;height:80px;">
						    <textarea class="ui-textarea" name="roleDesc">${OrmRole.roleDesc}</textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ui-form-fixed">
			<div class="ui-form-message">
				<i class="iconfont msgiconfont"></i>
				<span class = "msgText"></span>
<!-- 				<div class="iconfonthide" style="display: none"> -->
<!-- 					<i class="iconfont succeed">&#xf007b;</i> -->
<!-- 				</div> -->
<!-- 				<div class="iconfonthide2"  style = "display:none"> -->
<!-- 					<i class="ui-tiptext-icon iconfont" style="color: red">&#xf009f;</i> -->
<!-- 					<span class = "msgText" style="color: red"></span> -->
<!-- 				</div> -->
			</div>
			<div class="ui-form-toolbar">
				<input type="hidden" id = "sign" value = "${sign}">
				<input type="hidden" id = "roleOrgId" value = "${OrmRole.roleOrgId}">
				<input type="hidden" name = "roleType" id = "roleType" value = "${OrmRole.roleType}">
				<input type="hidden" id = "systemId" value = "${OrmRole.systemId}">
				<input type="hidden" name = "validSign" value = "Y">
				<input type="hidden" name = "roleId" value = "${OrmRole.roleId}">
				<input type="submit" class="ui-button " id="btnSave" value="保存">
				<input type="button" class="ui-button " id="btnCancel" value="关闭">
			</div>
		</div>
	</form>
</body>
	<script type="text/javascript" src="resources/commons/js/seajs/sea-debug.js" ></script>
	<script type="text/javascript" src="resources/commons/js/seajs/sea-config-debug.js"></script>
	<script type="text/javascript">
		seajs.use("<%=basePath%>orm/system/role/js/ormRoleADE");
	</script>
</html>