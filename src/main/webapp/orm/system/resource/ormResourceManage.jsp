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
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Document</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>orm/system/resource/css/ResourcesManager.css">
<base href="<%=basePath%>">
</head>
<body>
	<div class="main">
		<div class="ui-grid-row">
			<div class="ui-grid-7">
				<div class="ui-box">
					<div class="ui-box-head">
                        <div class="ui-box-head-container">
                            <h3 class="ui-box-head-title">资源管理</h3>
                        </div>
                    </div>
                    <div class="ui-box-container" style="min-height:540px;">
                    	<div class="ui-box-content ui-box-no-padding">
                    		<ul id="tree" class="ztree" style="width:259px;overflow-y:auto;overflow-x:auto;height:538px"></ul>
                    	</div>
                    </div>
				</div>
           	</div>
            <div class="ui-grid-17">
            	<div class="ui-box">
            		<div class="ui-box-head">
                        <div class="ui-box-head-container">
                            <h3 class="ui-box-head-title">资源管理</h3>
                        </div>
                    </div>
                    <div class="ui-box-container">
                    	<div class="ui-box-content ui-box-no-padding">
                    		<div class="search-box">
                    			<form action="" class="search-form">
                    				<div class="search-form-2item">
										<div class="search-form-item-col">
											<label class="ui-label">中文名称:</label>
											<input class="ui-input ui-input-w110" name="resourceNameCn"  style="width: 130px" data-rule-op="like">
										</div>
										<div class="search-form-item-col">
											<label class="ui-label">URL:</label>
											<input class="ui-input ui-input-w110" name="resourceUrl"  style="width: 130px" data-rule-op="like">
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
                    		<div class="ui-datagrid">
                    			<table id="tblResult">
                    				
                    			</table>
                    		</div>
                    		<div class="ui-manager-tip resMsg" style = "display:none">
								<div class="ui-form-message">
									<i class="iconfont showColor"></i>
									<span class = "Msg"></span>
								</div>
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
	seajs.use( "<%=basePath%>orm/system/resource/js/ormResourceManage" );
</script>
</body>
</html>