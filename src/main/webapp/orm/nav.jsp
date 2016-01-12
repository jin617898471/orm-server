<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>组织权限管理系统</title>
	<link rel="stylesheet" type="text/css" href="../resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="../resources/commons/css/widget.css">
    <link rel="stylesheet" type="text/css" href="css/theme.css" />
    <link rel="stylesheet" type="text/css" href="css/nav.css" />
    <script src="../orm/userinfo.js" ></script>
</head>
<body>
	<div class="header">
		<div class="header-container">
			<a class="logo">
				<img src="css/imgs/logo.png" alt="" />
			</a>
			<div class="nav-more" id="nav-more">
				<span>更多应用</span>
				<span class="nav-more-ico ico-arrow"></span>
				<div class="sub-nav-container-white">
					<div class="sub-nav-content">
						<ul class="subnav-items">
							<li class="subnav-items-title">综合监管</li>
							<li class="subnav-items-item">
								<img src="css/imgs/more-1.png" alt="" />
								<span>联网联控系统</span>
							</li>
							<li class="subnav-items-item">
								<img src="css/imgs/more-2.png" alt="" />
								<span>数据质量分析系统</span>
							</li>
							<li class="subnav-items-item">
								<img src="css/imgs/more-3.png" alt="" />
								<span>出租管理平台系统</span>
							</li>
						</ul>
						<ul class="subnav-items">
							<li class="subnav-items-title">运行分析</li>
							<li class="subnav-items-item">
								<img src="css/imgs/more-1.png" alt="" />
								<span>联网联控系统</span>
							</li>
							<li class="subnav-items-item">
								<img src="css/imgs/more-2.png" alt="" />
								<span>数据质量分析系统</span>
							</li>
							<li class="subnav-items-item">
								<img src="css/imgs/more-3.png" alt="" />
								<span>出租管理平台系统</span>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="user-info">
				<a class="user-info-item">
					<i class="iconfont">&#xe662;</i>
					<span>帮助</span>
				</a>
				<a class="user-info-item">
					<i class="iconfont">&#xf0051;</i>
					<span>退出</span>
				</a>
				<a class="user-info-item user-info-admin">
					<span>超级管理员</span>
					<img class="ico-arrow" src="css/imgs/ico-arrow.png" alt="" />
					<div class="spinner">
						<ul>
							<li class="spinner-item">
								<span class="items-ico"></span>
								<span>个人设置</span>
							</li>
						</ul>
					</div>
				</a>
			</div>
		</div>
	</div>
	<div class="main">
		<div class="main-repeat layout-left-center">
			<div class="col-main">
				<div class="col-sub">
					<div class="sidebar-btn" active="0">
						<div class="sidebar-btn-ico"></div>
					</div>
					<ul class="sidebar-unfold">
						<li class="sidebar-items" resource-code="org">
							<div class="items-sec">
								<span class="items-sec-ico ico-arrow"></span>
								<span>组织管理</span>
							</div>
							<div class="items-sub">
								<ul>
									<li class="items-sub-list items-sub-list-active" name="inst" resource-code="institution2">
										<span class="sidebar-icons sideicon-1"></span>
										<span>机构管理</span>
									</li>
									<li class="items-sub-list" name="dep" resource-code="department">
										<span class="sidebar-icons sideicon-2"></span>
										<span>部门管理</span>
									</li>
									<li class="items-sub-list" name="user" resource-code="user">
										<span class="sidebar-icons sideicon-4"></span>
										<span>用户管理</span>
									</li>
								</ul>
							</div>
						</li>
						<li class="sidebar-items" resource-code="authority">
							<div class="items-sec">
								<span class="items-sec-ico ico-arrow"></span>
								<span>权限管理</span>
							</div>
							<div class="items-sub">
								<ul>
<!-- 									<li class="items-sub-list"> -->
<!-- 										<span class="sidebar-icons sideicon-5"></span> -->
<!-- 										<span>安全策略管理</span> -->
<!-- 									</li> -->
									<li class="items-sub-list" resource-code="system">
										<span class="sidebar-icons sideicon-6"></span>
										<span>系统管理</span>
									</li>
									<li class="items-sub-list" resource-code="code">
										<span class="sidebar-icons sideicon-7"></span>
										<span>代码管理</span>
									</li>
									<li class="items-sub-list" resource-code="resource">
										<span class="sidebar-icons sideicon-8"></span>
										<span>功能管理</span>
									</li>
									<li class="items-sub-list" resource-code="role">
										<span class="sidebar-icons sideicon-9"></span>
										<span>角色管理</span>
									</li>
									<li class="items-sub-list" resource-code="authorize">
										<span class="sidebar-icons sideicon-10"></span>
										<span>授权管理</span>
									</li>
								</ul>
							</div>
						</li>
					</ul>
					<ul class="sidebar-fold">
						<li class="sidebar-items" resource-code="institution">
							<div class="items-sec" title="组织管理">
								<span class="items-sec-ico ico-arrow"></span>
							</div>
							<div class="items-sub">
								<ul>
									<li class="items-sub-list" resource-code="institution">
										<span class="sidebar-icons sideicon-1"></span>
										<div class="sublist-tips">
											<img src="css/imgs/side-tip-arr.png" alt="" class="sublist-tips-arrow" />
											<span>机构管理</span>
										</div>
									</li>
									<li class="items-sub-list" resource-code="department">
										<span class="sidebar-icons sideicon-2"></span>
										<div class="sublist-tips">
											<img src="css/imgs/side-tip-arr.png" alt="" class="sublist-tips-arrow" />
											<span>部门管理</span>
										</div>
									</li>
<!-- 									<li class="items-sub-list"> -->
<!-- 										<span class="sidebar-icons sideicon-3"></span> -->
<!-- 										<div class="sublist-tips"> -->
<!-- 											<img src="css/imgs/side-tip-arr.png" alt="" class="sublist-tips-arrow" /> -->
<!-- 											<span>工作管理</span> -->
<!-- 										</div> -->
<!-- 									</li> -->
									<li class="items-sub-list" resource-code="user">
										<span class="sidebar-icons sideicon-4"></span>
										<div class="sublist-tips">
											<img src="css/imgs/side-tip-arr.png" alt="" class="sublist-tips-arrow" />
											<span>用户管理</span>
										</div>
									</li>
								</ul>
							</div>
						</li>
						<li class="sidebar-items" resource-code="authority">
							<div class="items-sec" title="权限管理">
								<span class="items-sec-ico ico-arrow"></span>
							</div>
							<div class="items-sub">
								<ul>
<!-- 									<li class="items-sub-list"> -->
<!-- 										<span class="sidebar-icons sideicon-5"></span> -->
<!-- 										<div class="sublist-tips"> -->
<!-- 											<img src="css/imgs/side-tip-arr.png" alt="" class="sublist-tips-arrow" /> -->
<!-- 											<span>安全策略管理</span> -->
<!-- 										</div> -->
<!-- 									</li> -->
									<li class="items-sub-list" resource-code="system">
										<span class="sidebar-icons sideicon-6"></span>
										<div class="sublist-tips">
											<img src="css/imgs/side-tip-arr.png" alt="" class="sublist-tips-arrow" />
											<span>系统管理</span>
										</div>
									</li>
									<li class="items-sub-list" resource-code="code">
										<span class="sidebar-icons sideicon-7"></span>
										<div class="sublist-tips">
											<img src="css/imgs/side-tip-arr.png" alt="" class="sublist-tips-arrow" />
											<span>代码管理</span>
										</div>
									</li>
									<li class="items-sub-list" resource-code="resource">
										<span class="sidebar-icons sideicon-8"></span>
										<div class="sublist-tips">
											<img src="css/imgs/side-tip-arr.png" alt="" class="sublist-tips-arrow" />
											<span>功能管理</span>
										</div>
									</li>
									<li class="items-sub-list" resource-code="role">
										<span class="sidebar-icons sideicon-9"></span>
										<div class="sublist-tips">
											<img src="css/imgs/side-tip-arr.png" alt="" class="sublist-tips-arrow" />
											<span>角色管理</span>
										</div>
									</li>
									<li class="items-sub-list" resource-code="autorize">
										<span class="sidebar-icons sideicon-10"></span>
										<div class="sublist-tips">
											<img src="css/imgs/side-tip-arr.png" alt="" class="sublist-tips-arrow" />
											<span>授权管理</span>
										</div>
									</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
				<div class="main-wrap">
					<div class="frame-tabs">
						<ul class="frame-tabs-nav">
<!-- 							<li style="display:none"></li> -->
							<li>机构管理</li>
							
<!-- 							<li>部门管理</li> -->
						</ul>
						<div class="tabs-nav-btns">
							<button class="iconfont nav-btns-items btns-items-left">&#xf0017;</button>
							<button class="iconfont nav-btns-items btns-items-right">&#xf0018;</button>
						</div>
						<div class="frame-tabs-content">
<!-- 							<div class="hidden content-panel" style="display: none"></div> -->
							<div class="hidden content-panel">
								<iframe name="institution" src="org/institution/frame/org-manage.jsp" frameborder="0"></iframe>
							</div>
<!-- 							<div class="hidden content-panel"> -->
<!-- 								<iframe name="department" src="frame/dep-manage.jsp" frameborder="0"></iframe> -->
<!-- 							</div> -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="../resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="../resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	seajs.use("./js/nav.js");
    </script>
	
</body>
</html>