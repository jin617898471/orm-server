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
	<title>新增下属机构dialog</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>orm/org/department/css/dialog-subadd.css" />
</head>
<body>
	<div class="subadd-container">
		<form id="orgForm">
			<ul>
				<li class="subadd-items subadd-items-required">
					<label class="subadd-items-label">机构名称：</label>
					<input type="text" class="subadd-items-inp" name="orgName" value="${org.orgName}"/>
					<span class="subadd-items-star">*</span>
					<span class="items-error-text">请输入机构名称！</span>
				</li>
				<li class="subadd-items subadd-items-required">
					<label class="subadd-items-label">机构代码：</label>
					<input type="text" class="subadd-items-inp" name="orgCode" value="${org.orgCode}"/>
					<span class="subadd-items-star">*</span>
					<span class="items-error-text">请输入机构代码！</span>
				</li>
				<li class="subadd-items">
					<label class="subadd-items-label">联系电话：</label>
					<input type="text" class="subadd-items-inp" name="orgPhone" value="${org.orgPhone}"/>
				</li>
				<li class="subadd-items">
					<label class="subadd-items-label">联系人：</label>
					<input type="text" class="subadd-items-inp" name="orgLinkman" value="${org.orgLinkman}"/>
				</li>
				<li class="subadd-items">
					<label class="subadd-items-label">机构邮箱：</label>
					<input type="text" class="subadd-items-inp" name="orgEmail" value="${org.orgEmail}"/>
				</li>
			</ul>
			<input type="hidden" name="parentOrgId" value="${parentOrgId}"/>
			<input type="hidden" name="orgId" value="${org.orgId}"/>
			<input type="hidden" name="orgType" value="O"/>
			<input type="hidden" name="sign" value="${sign}"/>
			<input type="hidden"  name="orgColumns" value="orgName,orgNameShort,orgCode,orgArea,orgPhone,orgLinkman,orgEmail,orgWeburl,orgPostcode,orgAddress" />
		</form>
		<div class="subadd-bottom">
			<div class="subadd-bottom-operate">
				<input type="button" class="operate-items items-save" value="保存" />
				<input type="button" class="operate-items" value="取消" id="btnCancel" />
			</div>
			<div class="subadd-bottom-status">
				<i class="iconfont">&#xf00a0;</i>
				<span>保存成功！</span>
			</div>
		</div>
	</div>

	<script src="<%=basePath%>resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="<%=basePath%>resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	seajs.use("<%=basePath%>orm/org/department/js/dep-AE.js");
    </script>
	
</body>
</html>