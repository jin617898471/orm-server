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
	<title>新增人员dialog</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/theme.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>orm/org/department/css/dialog-addperson.css" />
</head>
<body>
	<div class="add-container">
		<div class="add-tabs">
			<ul class="add-tabs-nav">
				<li>账号信息</li>
				<li>个人信息</li>
				<li>工作信息</li>
				<li>组织信息</li>
			</ul>
			<div class="add-tabs-content">
				<div class="hidden content-panel">
					<!--账号信息-->
					<div class="info-container">
						<ul>
							<li class="info-items">
								<label class="info-label">账户名称：</label>
								<input type="text" class="info-inps inps-name" />
							</li>
							<li class="info-items">
								<label class="info-label">账户密码：</label>
								<span class="info-password">
									<input type="password" class="info-inps inps-pwd-p" />
									<input type="text" class="info-inps inps-pwd-t hidden">
									<span class="password-ico" active="0"></span>
								</span>
							</li>
						</ul>
					</div>
				</div>
				<div class="hidden content-panel">
					<!--个人信息-->
					<div class="info-container">
						<ul>
							<li class="info-items">
								<label class="info-label">中文姓名：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">英文名称：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">性别：</label>
								<span class="info-items-sexes">
									<input type="radio" name="sex" class="sexes-radio" id="sexes-male" />
									<label for="sexes-male">男</label>
								</span>
								<span class="info-items-sexes">
									<input type="radio" name="sex" class="sexes-radio" id="sexes-female" />
									<label for="sexes-female">女</label>
								</span>
							</li>
							<li class="info-items">
								<label class="info-label">生日：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">证件类型：</label>
								<div class="ui-select-trigger ui-select-trigger-none w218" id="sel-certype">
									<span data-role="trigger-content"></span>
									<span class="trigger-ico"></span>
								</div>
							</li>
							<li class="info-items">
								<label class="info-label">证件号码：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">手机号码：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">邮箱：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">QQ：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">微博：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">家庭地址：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">家庭邮编：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">家庭电话：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">政治面貌：</label>
								<div class="ui-select-trigger ui-select-trigger-none w218" id="sel-political">
									<span data-role="trigger-content"></span>
									<span class="trigger-ico"></span>
								</div>
							</li>
						</ul>
					</div>
				</div>
				<div class="hidden content-panel">
					<!--工作信息-->
					<div class="info-container">
						<ul>
							<li class="info-items">
								<label class="info-label">员工编号：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">员工代码：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">员工状态：</label>
								<div class="ui-select-trigger ui-select-trigger-none w218" id="employee-status">
									<span data-role="trigger-content"></span>
									<span class="trigger-ico"></span>
								</div>
							</li>
							<li class="info-items">
								<label class="info-label">员工职级：</label>
								<div class="ui-select-trigger ui-select-trigger-none w218" id="employee-level">
									<span data-role="trigger-content"></span>
									<span class="trigger-ico"></span>
								</div>
							</li>
							<li class="info-items">
								<label class="info-label">入职日期：</label>
								<span class="info-items-times">
									<input type="text" class="info-inps times-entry" />
									<i class="iconfont times-entry-ico">&#xf0042;</i>
								</span>
							</li>
							<li class="info-items">
								<label class="info-label">离职日期：</label>
								<span class="info-items-times">
									<input type="text" class="info-inps times-quit" />
									<i class="iconfont times-quit-ico">&#xf0042;</i>
								</span>
							</li>
							<li class="info-items">
								<label class="info-label">办公地址：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">办公邮编：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">办公电话：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">办公传真：</label>
								<input type="text" class="info-inps" />
							</li>
							<li class="info-items">
								<label class="info-label">办公邮箱：</label>
								<input type="text" class="info-inps" />
							</li>
						</ul>
					</div>
				</div>
				<div class="hidden content-panel">
					<!--组织信息-->
					<div class="info-container">
						<ul class="clearfix info-organize">
							<li class="info-items">
								<label class="info-label">所属机构：</label>
								<div class="ui-select-trigger ui-select-trigger-none w218" id="sel-org">
									<span data-role="trigger-content"></span>
									<span class="trigger-ico"></span>
								</div>
							</li>
							<li class="info-items">
								<label class="info-label">所属部门：</label>
								<div class="ui-select-trigger ui-select-trigger-none w218" id="sel-dep">
									<span data-role="trigger-content"></span>
									<span class="trigger-ico"></span>
								</div>
							</li>
							<li class="info-items">
								<label class="info-label">所属岗位：</label>
								<div class="ui-select-trigger ui-select-trigger-none w218" id="sel-post">
									<span data-role="trigger-content"></span>
									<span class="trigger-ico"></span>
								</div>
							</li>
							<li class="info-items">
								<input type="button" class="org-delete" value="删除该组织" />
							</li>
						</ul>
						<div class="organize-add">
							<i class="iconfont">&#xf00bd;</i>
							<span>添加组织</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="dialog-operate">
		<div class="operate-btns">
			<input type="button" class="btns-items" value="确定" />
			<input type="button" class="btns-items" value="取消" />
		</div>
	</div>

	<script src="<%=basePath%>resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="<%=basePath%>resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	seajs.use("<%=basePath%>orm/org/department/js/dialog-addperson.js");
    </script>
	
</body>
</html>