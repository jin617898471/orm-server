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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>orm/org/department/css/tabs-common.css" />
	<style type="text/css">
		.tabRepeat{
			width: 100%;
		}
	</style>
</head>
<body>
	<div class="wrap-title">
		<img src="<%=basePath%>/orm/css/imgs/department.png" alt="" class="wrap-title-img" />
		<span>${org.orgName}</span>
	</div>
	<div class="dep-tabs">
		<ul class="dep-tabs-nav">
			<li>基本信息</li>
			<li>下属部门</li>
			<li>下属岗位</li>
			<li>下属人员</li>
			<li>代码信息</li>
		</ul>
		<div class="dep-tabs-content">
			<div class="hidden content-panel">
				<div class="basic">
					<form id="org-info">
						<ul class="basic-list">
							<li class="basic-list-items">
								<label class="list-items-label">部门名称：</label>
								<input type="text" class="ui-input list-items-inp" name="orgName" value="${org.orgName}" >
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">部门代码：</label>
								<input type="text" class="ui-input list-items-inp" name="orgCode" value="${org.orgCode}" >
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">联系电话：</label>
								<input type="text" class="list-items-inp" name="orgPhone" value="${org.orgPhone}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">联系人：</label>
								<input type="text" class="list-items-inp" name="orgLinkman" value="${org.orgLinkman}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">部门邮箱：</label>
								<input type="text" class="list-items-inp" name="orgEmail" value="${org.orgEmail}" />
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
							<input type="hidden"  name="orgColumns" value="orgName,orgCode,orgPhone,orgLinkman,orgEmail" />
							<input type="hidden" id="orgId" name="orgId"  value="${orgId}">
						</div>
					</form>
				</div>
			</div>
			<div class="hidden content-panel">
				<div class="subordinate" id="addDepDiv">
					<div class="sub-title">
						<span class="sub-operate" id="addDep">
							<i class="iconfont">&#xe641;</i>
							<span>新增</span>
						</span>
						<span class="sub-title-text">下属部门列表</span>
					</div>
					<div class="sub-grid">
						<table id="dep-table"></table>
					</div>
				</div>
			</div>
			<div class="hidden content-panel">
				<div class="subordinate" id="addPostDiv">
					<div class="sub-title">
						<span class="sub-operate" id="addPost">
							<i class="iconfont">&#xe641;</i>
							<span>新增</span>
						</span>
						<span class="sub-title-text">下属岗位列表</span>
					</div>
					<div class="sub-grid">
						<table id="post-table"></table>
					</div>
				</div>
			</div>
			<div class="hidden content-panel">
				<div class="subordinate" id="addEmpDiv">
					<div class="sub-title">
						<span class="sub-operate" id="addEmp">
							<i class="iconfont">&#xe641;</i>
							<span>新增</span>
						</span>
						<span class="sub-title-text">下属人员列表</span>
					</div>
					<div class="sub-grid">
						<table id="emp-table"></table>
					</div>
				</div>
			</div>
			<div class="hidden content-panel">
				<div class="code-layout">
					<div class="col-main">
						<div class="col-sub">
							<ul id="tree" class="ztree" style="width:245px;overflow-y:auto;overflow-x:auto"></ul>
						</div>
						<div class="main-wrap">
							<div class="codeinf-sec" active="0">
								<div class="codeinf-title">
									<span>全局系统</span>
									<span class="title-ico"></span>
								</div>
								<div class="codeinf-detail">
									<dl>
										<dt>有效性标记：</dt>
										<dd>有效、无效</dd>
										<dt>性别：</dt>
										<dd>未知、男、女</dd>
										<dt>行业类别代码：</dt>
										<dd>交通运输、应对气候变化、其他行业</dd>
										<dt>行政区划代码：</dt>
										<dd>浙江省、杭州市、宁波市、温州市、义乌市、衢州市、舟山市、台州市、丽水市、北京市、江西省、上饶市</dd>
										<dt>公司产品组：</dt>
										<dd>交通运输产品组、应对气候变化组、产品组</dd>
									</dl>
								</div>
							</div>
							<div class="codeinf-sec" active="0">
								<div class="codeinf-title">
									<span>组织机构管理系统</span>
									<span class="title-ico"></span>
								</div>
								<div class="codeinf-detail">
									<dl>
										<dt>正式/临时标识：</dt>
										<dd>正式、临时</dd>
										<dt>组织类型：</dt>
										<dd>部门、机构、岗位</dd>
									</dl>
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
    	seajs.use("<%=basePath%>orm/org/department/js/dep-tab.js");
    </script>
	
</body>
</html>