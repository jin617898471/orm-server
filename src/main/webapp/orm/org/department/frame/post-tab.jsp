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
		<img src="<%=basePath%>/orm/css/imgs/ajob.png" alt="" class="wrap-title-img" />
		<span>${org.orgName}</span>
	</div>
	<div class="dep-tabs">
		<ul class="dep-tabs-nav">
			<li>基本信息</li>
			<li>下属人员</li>
			<li>权限配置</li>
			<li>权限计算</li>
		</ul>
		<div class="dep-tabs-content">
			<div class="hidden content-panel">
				<div class="basic">
					<form id="org-info">
						<ul class="basic-list">
							<li class="basic-list-items">
								<label class="list-items-label">机构名称：</label>
								<input type="text" class="ui-input list-items-inp" name="orgName" value="${org.orgName}" >
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">机构代码：</label>
								<input type="text" class="ui-input list-items-inp" name="orgCode" value="${org.orgCode}" >
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
							<input type="hidden"  name="orgColumns" value="orgName,orgCode" />
							<input type="hidden" id="orgId" name="orgId" value="${orgId}">
						</div>
					</form>
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
				<div class="jur-condition">
					<input type="button" class="jur-btn" value="查询" />
					<div class="condition-items">
						<ul>
							<li>
								<label class="items-label">角色名称：</label>
								<input type="text" class="items-txtName" id="roleName" />
							</li>
							<li>
								<label class="items-label">所属系统：</label>
								<div class="ui-select-trigger ui-select-trigger-none" id="cond-system">
									<span data-role="trigger-content" id="system"></span>
									<span class="trigger-ico"></span>
								</div>
							</li>
						</ul>
					</div>
				</div>
				<div class="select clearfix">
					<div class="col-w50">
						<div class="col-sub-left">
							<div class="col-title">待选角色：</div>
							<div class="col-thead">
								<table>
									<thead>
										<tr>
											<th class="w39">选择</th>
											<th class="w89">角色名称</th>
											<th>所属系统</th>
										</tr>
									</thead>
								</table>
							</div>
							<div class="col-tbody" id="col-tbody-wait">
								<div class="ui-scroll">
									<div class="ui-scroll-container">
										<table>
											<tbody>
												<tr>
													<td class="w39 text-center"><input type="checkbox" class="chk-wait" /></td>
													<td class="w79">总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-operate">
						<div class="operate-items">
							<span>选择</span>
							<span class="items-sel sel-1"></span>
						</div>
						<div class="operate-items">
							<span>选择全部</span>
							<span class="items-sel sel-2"></span>
						</div>
						<div class="operate-items">
							<span class="items-sel sel-3"></span>
							<span>取消</span>
						</div>
						<div class="operate-items last">
							<span class="items-sel sel-4"></span>
							<span>取消全部</span>
						</div>
					</div>
					<div class="col-w50">
						<div class="col-sub-right">
							<div class="col-title">已选角色：</div>
							<div class="col-thead">
								<table>
									<thead>
										<tr>
											<th class="w39">选择</th>
											<th class="w89">角色名称</th>
											<th>所属系统</th>
										</tr>
									</thead>
								</table>
							</div>
							<div class="col-tbody" id="col-tbody-already">
								<div class="ui-scroll">
									<div class="ui-scroll-container">
										<table>
											<tbody>
												<tr>
													<td class="w39 text-center"><input type="checkbox" class="chk-wait" /></td>
													<td class="w79">总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
												<tr>
													<td class="text-center"><input type="checkbox" class="chk-wait" /></td>
													<td>总经理</td>
													<td>组织权限管理系统</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="hidden content-panel">
				<h1>权限计算</h1>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="<%=basePath%>resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	seajs.use("<%=basePath%>orm/org/department/js/post-tab.js");
    </script>
	
</body>
</html>