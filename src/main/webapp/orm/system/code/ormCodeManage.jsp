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
<link rel="stylesheet" type="text/css" href="<%=basePath%>orm/system/code/css/CodeManager.css">
<base href="<%=basePath%>">
</head>
<body>
	<div class="main">
		<div class="ui-grid-row">
			<div class="ui-grid-7">
				<div class="ui-box">
					<div class="ui-box-head">
                        <div class="ui-box-head-container">
                            <h3 class="ui-box-head-title">代码管理</h3>
                        </div>
                    </div>
                    <div class="ui-box-container" style="min-height:540px;">
                    	<div class="ui-box-content ui-box-no-padding">
                    		<ul id="tree" class="ztree" style="width:259px;overflow-y:auto;overflow-x:auto;height:538px"></ul>
                    	</div>
                    </div>
				</div>
           	</div>
            <div class="ui-grid-17 CodeIdx">
            	<div class="ui-box">
            		<div class="ui-box-head">
                        <div class="ui-box-head-container">
                            <h3 class="ui-box-head-title">代码管理</h3>
                        </div>
                    </div>
                    <div class="ui-box-container">
                    	<div class="ui-box-content ui-box-no-padding">
                    		<div class="search-box">
                    			<form action="" class="search-formCodeIdx">
                    				<div class="search-form-2item">
										<div class="search-form-item-col">
											<label class="ui-label">代码集中文名:</label>
											<input class="ui-input ui-input-w110" name="cidxNameCn" style="width: 130px" data-rule-op="like">
										</div>
										<div class="search-form-item-col">
											<label class="ui-label">代码集编号:</label>
											<input class="ui-input ui-input-w110" name="cidxSerial" style="width: 130px" data-rule-op="like">
										</div>
									</div>
									<div class="search-form-2item">
										<div class="search-form-item-col">
											<label class="ui-label">代码集拼音:</label>
											<input class="ui-input ui-input-w110" name="cidxNamePinyin" style="width: 130px" data-rule-op="like">
										</div>
									</div>
                    			</form>
                    		</div>
                    		<div class="search-bar">
                    			<a class="ui-button ui-button-search searchCodeidx">
                    				<i class="iconfont">&#xf004c;</i>
                    				查询
                    			</a>
                    			<a class="ui-button ui-button-reset  resetCodeidx">
                    				<i class="iconfont">&#xf0059;</i>
                    				重置
                    			</a>
                    			<a class="ui-button ui-button-refresh refreshCodeidx">
                    				<i class="iconfont">&#xf004b;</i>
                    				刷新
                    			</a>
                    			<a class="ui-button ui-button-add addCodeidx">
                    				<i class="iconfont">&#xf00a2;</i>
                    				新增
                    			</a>
                    			<a class="ui-button ui-button-4 ui-button-delete deleteCodeidx">
                    				<i class="iconfont">&#xf0097;</i>
                    				批量删除
                    			</a>
                    		</div>
                    		<div class="ui-datagrid">
                    			<table id="tblResultCodeidx">
                    				
                    			</table>
                    		</div>
                    		<div class="ui-manager-tip codeIdxMsg" style = "display:none">
								<div class="ui-form-message">
									<i class="iconfont showColor"></i>
									<span class = "Msg"></span>
								</div>
							</div>
                    	</div>
                    </div>
            	</div>
           	</div>
           	<div class="ui-grid-17 Code" style = "display:none">
            	<div class="ui-box">
            		<div class="ui-box-head">
                        <div class="ui-box-head-container">
                            <h3 class="ui-box-head-title">代码管理</h3>
                        </div>
                    </div>
                    <div class="ui-box-container">
                    	<div class="ui-box-content ui-box-no-padding">
                    		<div class="search-box">
                    			<form action="" class="search-formCode">
                    				<div class="search-form-2item">
										<div class="search-form-item-col">
											<label class="ui-label">代码中文名:</label>
											<input class="ui-input ui-input-w110" name="codeValueCn" data-rule-op="like">
										</div>
										<div class="search-form-item-col">
											<label class="ui-label">代码编号:</label>
											<input class="ui-input ui-input-w110" name="codeValue" data-rule-op="like">
										</div>
									</div>
									<div class="search-form-2item">
										<div class="search-form-item-col">
											<label class="ui-label">代码拼音:</label>
											<input class="ui-input ui-input-w110" name="codeValuePinyin" data-rule-op="like">
										</div>
									</div>
                    			</form>
                    		</div>
                    		<div class="search-bar">
                    			<a class="ui-button ui-button-search searchCode">
                    				<i class="iconfont">&#xf004c;</i>
                    				查询
                    			</a>
                    			<a class="ui-button ui-button-reset resetCode">
                    				<i class="iconfont">&#xf0059;</i>
                    				重置
                    			</a>
                    			<a class="ui-button ui-button-refresh refreshCode">
                    				<i class="iconfont">&#xf004b;</i>
                    				刷新
                    			</a>
                    			<a class="ui-button ui-button-add addCode">
                    				<i class="iconfont">&#xf00a2;</i>
                    				新增
                    			</a>
                    			<a class="ui-button ui-button-4 ui-button-delete deleteCode">
                    				<i class="iconfont">&#xf0097;</i>
                    				批量删除
                    			</a>
                    		</div>
                    		<div class="ui-datagrid">
                    			<table id="tblResultCode">
                    				
                    			</table>
                    		</div>
                    		<div class="ui-manager-tip codeMsg" style = "display:none">
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
	seajs.use( "<%=basePath%>orm/system/code/js/ormCodeManage" );
</script>
</body>
</html>