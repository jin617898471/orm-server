<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="cn.innosoft.fw.orm.client.service.LoginUserContext"%>
<%@ page import="cn.innosoft.fw.orm.client.model.TreeNode"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	request.setAttribute("basePath", basePath);
// 	String userName = (String) session.getAttribute("userName");
// 	List<TreeNode> resList = (List<TreeNode>) session.getAttribute("resList");
	String userName = "";
	List<TreeNode> resList = new ArrayList<TreeNode>();
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="${basePath}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="resources/commons/css/base.css">
	<link rel="stylesheet" type="text/css" href="resources/commons/css/widget.css">
<!-- 	<link rel="stylesheet" type="text/css" href="theme.css"> -->
	<script type="text/javascript" src="resources/commons/js/seajs/sea-debug.js"></script>
	<script type="text/javascript" src="resources/commons/js/seajs/sea-config-debug.js"></script>
	<title><sitemesh:write property='title' /></title>
	<sitemesh:write property='head' />
	<style>
		span#username {
		    color: #FFFFFF;
		    display: inline-block;
		    height: 38px;
		    line-height: 38px;
		    position: relative;
		    min-width: 90px;
		    vertical-align: top;
		    text-align: right;
		    margin-top: 2px;
		    margin-right: 5px;
		    font-weight: bold;
		}
	</style>
</head>
<body>

	<div class="header">
		<div class="header-container">
			<a class="logo">
				<i class="iconfont" style="font-size: 26px;">&#xf00c1;</i>组织机构及权限管理系统
			</a>
			<div class="user-info">
				<span id="username"><%=userName %></span>
				<a class="user-info-item" href="">
					<i class="iconfont" style="right: 10px; top: 2px">&#xf0051;</i>
				</a>
			</div>
			
			<ul class="header-nav" id="nav">
			<%
				for(int i=0;i<resList.size();i++){
					TreeNode node = resList.get(i);
					String name = node.getText();
					String url = node.getValue();
					List<TreeNode> children = node.getChild();
			%>
				<li class="nav-list">
					<a resurl="<%=basePath+url %>" class="nav-list-inner url">
						<i class="iconfont"></i><%=name %>
					</a>
					<div class="sub-nav-container-white" style="display: none;">
						<div class="sub-nav-container-shadow"></div>
						<div class="sub-nav-container-arrow"></div>
						<div class="sub-nav-content">
						<%
							if(children!=null && children.size()>0){
						%>
								<ul>
									<%
										for(int j=0;j<children.size();j++){
											TreeNode child = children.get(j);
											String cname = child.getText();
											String curl = child.getValue();
									%>
										<li class="sub-nav-list">
											<a resurl="<%=basePath+curl %>" class="sub-nav-list-inner url">
												<i class="iconfont"></i><%=cname %>
											</a>
										</li>
									<%
										}
									%>
								</ul>
						<%
							}
						%>
						</div>
					</div>
				</li>
			<%
				}
			%>
			</ul>
		</div>
	</div>

	<div class="body-container" >
		<sitemesh:write property='body' />
	</div>

</body>
<script>
	seajs.use( "${basePath}decorate" );
</script>
</html>