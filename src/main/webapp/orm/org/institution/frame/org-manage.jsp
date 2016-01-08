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
	<title>机构管理</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>orm/org/institution/css/org-manage.css" />
	<style>
		.ztree span{
			font:14px/1.5 "Microsoft Yahei",tahoma,arial,"Hiragino Sans GB",\5b8b\4f53;
		}
	</style>
</head>
<body>
	<div class="main-repeat layout-left-center">
		<div class="col-main">
			<div class="col-sub">
				<div class="org-title">
					<img src="<%=basePath%>orm/css/imgs/org-title.png" alt="" class="org-title-img" />
					<span>机构组织关系</span>
				</div>
				<ul id="tree" class="ztree" style="width:287px;overflow-y:auto;overflow-x:auto"></ul>
			</div>
			<div class="main-wrap">
				<div class="wrap-title">
					<img src="<%=basePath%>orm/css/imgs/org.png" alt="" class="wrap-title-img" />
					<span>浙江久拓科技有限公司</span>
				</div>
				<div class="org-tabs">
					<ul class="org-tabs-nav">
						<li>基本信息</li>
						<li>下属机构</li>
					</ul>
					<div class="org-tabs-content">
						<div class="hidden content-panel">
							<div class="basic">
								<form id="org-info">
									<ul class="basic-list">
										<li class="basic-list-items">
											<label class="list-items-label">机构名称：</label>
											<input type="text" class="ui-input list-items-inp" name="orgName" value="" >
										</li>
										<li class="basic-list-items">
											<label class="list-items-label">机构简称：</label>
											<input type="text" id="org-name-short" class="ui-input list-items-inp" name="orgNameShort" value="" />
										</li>
										<li class="basic-list-items">
											<label class="list-items-label">机构代码：</label>
											<input type="text" class="ui-input list-items-inp" name="orgCode" value="" >
										</li>

										<li class="basic-list-items">
											<label class="list-items-label">所属地域：</label>
											<input type="text" class="ui-input list-items-inp" name="orgArea" value="" />
										</li>
										<li class="basic-list-items">
											<label class="list-items-label">联系电话：</label>
											<input type="text" class="list-items-inp" name="orgPhone" value="" />
										</li>
										<li class="basic-list-items">
											<label class="list-items-label">联系人：</label>
											<input type="text" class="list-items-inp" name="orgLinkman" value="" />
										</li>
										<li class="basic-list-items">
											<label class="list-items-label">机构邮箱：</label>
											<input type="text" class="list-items-inp" name="orgEmail" value="" />
										</li>
										<li class="basic-list-items">
											<label class="list-items-label">机构网站：</label>
											<input type="text"  class="list-items-inp" name="orgWeburl" value="" />
										</li>
										<li class="basic-list-items fullw">
											<label class="list-items-label">机构邮编：</label>
											<input type="text" class="list-items-inp" name="orgPostcode" value="" />
										</li>
										<li class="basic-list-items fullw">
											<label class="list-items-label">机构地址：</label>
											<input type="text" class="list-items-inp inp-address" name="orgAddress" value="" />
										</li>
									</ul>
									<div class="basic-other"></div>
									<div class="basic-operate">
										<div class="operate-btns">
											<input type="button" class="operate-btns-items" id="save" value="保存" />
											<!--<input type="button" class="operate-btns-items" value="取消" />-->
										</div>
										<div class="operate-status">
											<i class="iconfont">&#xf00a0;</i>
											<span class="status-message"></span>
										</div>
<!-- 										<input type="hidden" name="orgId"> -->
									</div>
								</form>
							</div>
						</div>
						<div class="hidden content-panel">
							<div class="subordinate">
								<div class="sub-title">
									<span class="sub-operate">
										<i class="iconfont">&#xe641;</i>
										<span>新增</span>
									</span>
									<span class="sub-title-text">下属机构列表</span>
								</div>
								<div class="sub-grid">
									<table id="grid-table"></table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="<%=basePath%>resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="<%=basePath%>resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	seajs.use("<%=basePath%>orm/org/institution/js/org-manage.js");
    </script>
	
</body>
</html>