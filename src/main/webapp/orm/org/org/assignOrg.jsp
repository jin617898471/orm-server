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
<title>Document</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>orm/org/org/css/CodeManager.css">
<base href="<%=basePath%>">
<script type="text/javascript" src="orm/OrmJsObj.js" ></script>
<style>
.main {
    width: 690px;
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
    height:690px;
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
    height:690px;
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
						<div class="ui-form-2item rule-content">
							<div class="role-left">
								<div>
									<ul class="ztree" id="tree" style="overflow-y:auto;overflow-x:auto;height:380px"></ul>
								</div>
							</div>
							<div class="role-right">
                    			<div class="main-container">
									<div class="org-header">
										<i class="org-line"></i>
										<h4>组织权限</h3>
									</div>
									<dl id="checkedGlobalOrgAuth">
									</dl>
								</div>
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
	var assignObjId = "0001";
	var assignObj = "user";
	seajs.use( "<%=basePath%>orm/org/org/js/assignOrg" );
</script>
</body>
</html>