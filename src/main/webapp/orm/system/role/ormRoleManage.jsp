<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>角色管理</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>orm/system/role/css/ormRoleManage.css">
	<base href="<%=basePath%>">
</head>
<body>
    <div class="main">
		<div class="ui-grid-row">
            <div class="ui-grid-24">
            	<div class="ui-box">
            		<div class="ui-box-head">
                        <div class="ui-box-head-container">
                            <h3 class="ui-box-head-title">角色管理</h3>
                        </div>
                    </div>
                    <div class="ui-box-container">
                    	<div class="ui-box-content ui-box-no-padding">
                    		<div class="search-box">
                    			<form action="" class="search-form">
                    				<div class="search-form-2item">
										<div class="search-form-item-col">
											<label class="ui-label">中文名称:</label>
											<input class="ui-input ui-input-w190" name="roleNameCn" data-rule-op="like"/>
										</div>
										<div class="search-form-item-col">
											<label class="ui-label">英文名称:</label>
											<input class="ui-input ui-input-w190" name="roleNameEn" data-rule-op="like"/>
										</div>
									</div>
									<div class="search-form-2item">
										<div class="search-form-item-col">
											<label class="ui-label">所属系统:</label>
											<a class="ui-select-trigger systemId" data-rule-op="like" style = "width:188px">
					            				<span data-role="trigger-content"></span>          
					            				<i class="iconfont blue" title="下三角形">&#xf0044;</i>
					        				</a> 
										</div>
									</div>
								</form>
                    		</div>
                    		<div class="search-bar">
                    			<a class="ui-button ui-button-search">
                    				<i class="iconfont">&#xf004c;</i>
                    				查询
                    			</a>
                    			<a class="ui-button ui-button-reset">
                    				<i class="iconfont">&#xf0059;</i>
                    				重置
                    			</a>
                    			<a class="ui-button ui-button-refresh">
                    				<i class="iconfont">&#xf004b;</i>
                    				刷新
                    			</a>
                    			<a class="ui-button ui-button-add">
                    				<i class="iconfont">&#xf00a2;</i>
                    				新增
                    			</a>
                    			<a class="ui-button ui-button-4 ui-button-delete">
                    				<i class="iconfont">&#xf0097;</i>
                    				批量删除
                    			</a>
                    		</div>
                    		<div class="datagrid-layout">
                    			<table id="tblResult"></table>
                    		</div>
                    	</div>
                    </div>
            	</div>
           	</div>
        </div>
	</div>
	<script type="text/javascript" src="resources/commons/js/seajs/sea-debug.js" ></script>
    <script type="text/javascript" src="resources/commons/js/seajs/sea-config-debug.js"></script>
	<script>
		seajs.use("<%=basePath%>orm/system/role/js/ormRoleManage");
	</script>
</html>