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
     <style type="text/css"> 
     	.ztreeIcon{ 
      		padding-top:10;
     	} 
     	.ztree li a .bgcolor{
     		    background-color:#00ff00;
     	}
     </style>
</head>
<body>
	<div class="main-repeat layout-left-center">
		<div class="col-main">
			<div class="col-sub">
				<div class="search-panel">
					<i class="iconfont">&#xf004c;</i>
					<input type="text" class="txtSearch" placeholder="输入机构名称查询" />
					<input type="hidden" id="orgId" value="${orgId}" />
				</div>
				<ul id="tree" class="ztree" style="width:287px;overflow-y:auto;overflow-x:auto"></ul>
			</div>
			<div class="main-wrap">
				<iframe id="orgTab" name="orgTab" src="" 
					frameborder="0" style="width:100%;" onload="this.height=orgTab.document.body.scrollHeight" 
					scrolling="no" ></iframe>
			</div>
		</div>
	</div>

	<script src="<%=basePath%>resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="<%=basePath%>resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	seajs.use("<%=basePath%>orm/org/department/js/dep-manage.js");
    </script>
	
</body>
</html>