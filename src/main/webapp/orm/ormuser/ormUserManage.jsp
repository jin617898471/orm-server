<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理主页</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>orm/organization/ormuser/css/ormUserManage.css">
<base href="<%=basePath%>">
</head>
<body>
<div class="main">
		<div class="ui-grid-row">
            <div class="ui-grid-24">
            	<div class="ui-box">
            		<div class="ui-box-head">
                        <div class="ui-box-head-container">
                            <h3 class="ui-box-head-title">用户管理</h3>
                        </div>
                    </div>
                    <div class="ui-box-container">
                    	<div class="ui-box-content ui-box-no-padding">
                    		<div class="search-box">
                    			<form action="" class="search-form">
                    				<div class="search-form-2item">
										<div class="search-form-item-col">
											<label class="ui-label">用户账号:</label>
											<input class="ui-input ui-input-w190" name="u.USER_ACCT" data-rule-op="like">
										</div>
										<div class="search-form-item-col">
											<label class="ui-label">用户名称:</label>
											<input class="ui-input ui-input-w190" name="u.USER_ACCT_CN" data-rule-op="like">
										</div>
									</div>
									<div class="search-form-2item">
										<div class="search-form-item-col">
											<label class="ui-label">电话号码:</label>
											<input class="ui-input ui-input-w190" name="u.USER_MOBILE" data-rule-op="like">
										</div>
										<div class="search-form-item-col">
											<label class="ui-label">组织机构:</label>
											<div class="ui-select-trigger  O_SERIAL"   style="width: 188px">
										        <span data-role="trigger-content"></span>        
										       	<i class="iconfont" title="下三角形">&#xf0044;</i>
										       	<input name = "" type = "hidden" />
										    </div>
										</div>
									</div>
                    			</form>
                    			<input name ="SERIAL" type = "hidden" />
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
                    			<table id="tblResult" class = "ys_table_result"></table>
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
		seajs.use("../../../orm/organization/ormuser/js/ormUserManage");
	</script>
</body>
</html>