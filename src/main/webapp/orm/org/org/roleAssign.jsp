<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/************************ jsp 头 模 块 ***************************/
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	request.setAttribute("basePath", basePath);
%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>分配角色</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>orm/org/org/css/CodeManager.css">
<base href="<%=basePath%>">
<style>
.main {
    width: 670px;
}
.ui-label {
    margin: 5px 2px 0 14px;
}
.ui-form-2item .ui-form-item-col {
    width: 44%;
}
.search-bar {
    text-align: right;
    padding-right: -10px;
    height: 40px;
    line-height: 40px;
    float: left;
    margin-top: -8px;
    width: 10%;
}
.role-left {
    float: left;
    width: 300px;
    height:670px;
    overflow:auto;
    margin-left: 10px;
}
.role-opt {
    float: left;
    width: 50px;
    margin-top:250px;
}
.role-right {
    float: right;
    width: 280px;
    height:670px;
    margin-right: -10px;
    overflow:auto;
}
.rule-content .ui-label{
	width:125px;
    margin-left: 0px;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
}
.role-opt input{
    float: left;
    margin-left: 2px;
    width: 40px;
    height: 30px;
}
.content .ui-form-item-have2col{
        margin-bottom: 1px;
}
.d_over{
	background-color:rgb(200,200,200);
}
.d_click{
	background-color:rgb(200,200,200);
}
.d_lock{
	background-color:rgb(240,240,240);
}
.content:hover ,.content div:hover ,.content label:hover {
	cursor:pointer
}
.role-opt input:hover {
	cursor:pointer
}
</style>
</head>
<body>
	<div class="main">
		<div class="ui-grid-row">
            <div class="ui-grid-17">
                <form class="ui-form clearfix" data-widget="validator">
					<div class="ui-form-container">
						<div class="ui-form-2item">
							<div class="ui-form-item-col">
								<label class="ui-label org-title">角色名称:</label>
								<input class="ui-input ui-input-w190" id="roleName" value="" />
							</div>
							<div class="ui-form-item-col">
								<label class="ui-label org-title">所属系统:</label>
<!-- 								<input class="ui-input " value="" /> -->
								<a class="ui-select-trigger systemId" style="width: 188px;" id="systemId" >
		            				<span data-role="trigger-content"></span>          
		            				<i class="iconfont blue" title="下三角形">&#xf0044;</i>
		        				</a> 
							</div>
							<div class="search-bar">
                    			<a class="ui-button ui-button-search searchCode">
                    				<i class="iconfont">&#xf004c;</i>
                    				查询
                    			</a>
                    		</div>
						</div>
						<div class="ui-form-2item rule-content">
							<div class="role-left">
								<div class="ui-form-item-have2col">
									<label class="ui-label org-title">角色名称</label>
									<label class="ui-label org-title">所属系统</label>
								</div>
								<div class="content" >
								<script id="roleTemplate" type="text/html">  
									{{each list}}
										<div class="ui-form-item-have2col {{if $value.lock}}d_lock{{/if}}" id="{{$value.roleId}}" systemId="{{$value.systemId}}" maptype="{{$value.mapType}}">
											<label class="ui-label" title="{{$value.roleName}}" >{{$value.roleName}}</label>
											<label class="ui-label" title="{{$value.systemName}}">{{$value.systemName}}</label>
										</div>
									{{/each}}
   								</script>  
 								</div>
							</div>
							<div class="role-opt">
								<input type="button" class="selectrole" value="选择&gt;" /><br><br><br><br>
								<input type="button" class="cancelrole" value="取消&lt;" />
							</div>
							<div class="role-right">
                    			<div class="ui-form-item-have2col">
									<label class="ui-label org-title">角色名称</label>
									<label class="ui-label org-title">所属系统</label>
								</div>
								<div class="content" ></div>
                    		</div>
						</div>
					</div>
				</form>
           	</div>
        </div>
	</div>
<script type="text/javascript" src="resources/commons/js/seajs/sea-debug.js" ></script>
<script type="text/javascript" src="resources/commons/js/seajs/sea-config-debug.js"></script>
<script>
	var assignObjId = "${id}";
	var assignObj = "${type}";
	eval("var "+assignObj+"Id='"+assignObjId+"'");
	seajs.use( "<%=basePath%>orm/org/org/js/roleAssign" );
</script>
</body>
</html>