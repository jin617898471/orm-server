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
	<script src="<%=basePath%>authority/system/js"></script>
</head>
<body>
	<div class="wrap-title">
		<img src="<%=basePath%>/orm/css/imgs/person.png" alt="" class="wrap-title-img" />
		<span>${user.userName}</span>
	</div>
	<div class="dep-tabs">
		<ul class="dep-tabs-nav">
			<li>基本信息</li>
			<li>工作信息</li>
			<li>权限配置</li>
			<li>权限计算</li>
		</ul>
		<div class="dep-tabs-content">
			<div class="hidden content-panel">
				<div class="basic">
					<form id="user-info">
						<ul class="basic-list">
							<li class="basic-list-items">
								<label class="list-items-label">账户名称：</label>
								<input type="text" class="ui-input list-items-inp" name="userAcct" value="${user.userAcct}" >
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">中文姓名：</label>
								<input type="text" class="ui-input list-items-inp" name="userName" value="${user.userName}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">英文名称：</label>
								<input type="text" class="ui-input list-items-inp" name="userNameEng" value="${user.userNameEng}" >
							</li>

							<li class="basic-list-items">
								<label class="list-items-label">性别：</label>
								男 <input type="radio" name="userSex" value="MALE" ${user.userSex eq 'MALE'?'checked':''}/>
								女 <input type="radio" name="userSex" value="FEMALE" ${user.userSex eq 'FEMALE'?'checked':''}/>
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">证件类型：</label>
								<input type="text" class="list-items-inp" name="userCardtype" value="${user.userCardtype}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">生日：</label>
								<input type="text" class="list-items-inp" name="userBirth" value="${user.userBirth}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">手机号码：</label>
								<input type="text" class="list-items-inp" name="userMobile" value="${user.userMobile}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">证件号码：</label>
								<input type="text"  class="list-items-inp" name="userCardno" value="${user.userCardno}" />
							</li>
							<li class="basic-list-items fullw">
								<label class="list-items-label">QQ：</label>
								<input type="text" class="list-items-inp" name="empQq" value="${user.empQq}" />
							</li>
							<li class="basic-list-items fullw">
								<label class="list-items-label">邮箱：</label>
								<input type="text" class="list-items-inp inp-address" name="userEmail" value="${user.userEmail}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">家庭地址：</label>
								<input type="text" class="list-items-inp" name="empHaddress" value="${user.empHaddress}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">微博：</label>
								<input type="text" class="list-items-inp" name="empWeibo" value="${user.empWeibo}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">家庭电话：</label>
								<input type="text"  class="list-items-inp" name="empHtel" value="${user.empHtel}" />
							</li>
							<li class="basic-list-items fullw">
								<label class="list-items-label">家庭邮编：</label>
								<input type="text" class="list-items-inp" name="empHzipcode" value="${user.empHzipcode}" />
							</li>
							<li class="basic-list-items fullw">
								<label class="list-items-label">政治面貌：</label>
								<input type="text" class="list-items-inp inp-address" name="empParty" value="${user.empParty}" />
							</li>
						</ul>
						<div class="basic-other"></div>
						<div class="basic-operate">
							<div class="operate-btns">
								<input type="button" class="operate-btns-items" id="userSave" value="保存" />
								<!--<input type="button" class="operate-btns-items" value="取消" />-->
							</div>
							<div class="operate-status">
								<i class="iconfont">&#xf00a0;</i>
								<span class="status-message"></span>
							</div>
							<input type="hidden" name="userId" id="userId" value="${user.userId}" />
						</div>
						<input type="hidden" name="userColumns" value="userAcct,userName,userNameEng,userSex,userBirth,userCardtype,userCardno,userMobile,userEmail,userEmail,empQq,empWeibo,empHaddress,empHzipcode,empHtel,empParty">
					</form>
				</div>
			</div>
			<div class="hidden content-panel">
				<div class="basic">
					<form id="emp-info">
						<ul class="basic-list">
							<li class="basic-list-items">
								<label class="list-items-label">员工编号：</label>
								<input type="text" class="ui-input list-items-inp" name="empId" value="${user.empId}" >
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">员工代码：</label>
								<input type="text" class="ui-input list-items-inp" name="empCode" value="${user.empCode}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">员工状态：</label>
								<input type="text" class="ui-input list-items-inp" name="empStatus" value="${user.empStatus}" >
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">员工职级：</label>
								<input type="text" class="ui-input list-items-inp" name="empDegree" value="${user.empDegree}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">入职日期：</label>
								<input type="text" class="list-items-inp" name="empIndate" value="${user.empIndate}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">离职日期：</label>
								<input type="text" class="list-items-inp" name="empOutdate" value="${user.empOutdate}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">办公地址：</label>
								<input type="text" class="list-items-inp" name="empOaddress" value="${user.empOaddress}" />
							</li>
							<li class="basic-list-items">
								<label class="list-items-label">办公邮编：</label>
								<input type="text"  class="list-items-inp" name="empOzipcode" value="${user.empOzipcode}" />
							</li>
							<li class="basic-list-items fullw">
								<label class="list-items-label">办公电话：</label>
								<input type="text" class="list-items-inp" name="empOtel" value="${user.empOtel}" />
							</li>
							<li class="basic-list-items fullw">
								<label class="list-items-label">办公传真：</label>
								<input type="text" class="list-items-inp" name="empFaxno" value="${user.empFaxno}" />
							</li>
							<li class="basic-list-items fullw">
								<label class="list-items-label">办公邮箱：</label>
								<input type="text" class="list-items-inp" name="empOemail" value="${user.empOemail}" />
							</li>
						</ul>
						<div class="basic-other"></div>
						<div class="basic-operate">
							<div class="operate-btns">
								<input type="button" class="operate-btns-items" id="empSave" value="保存" />
								<!--<input type="button" class="operate-btns-items" value="取消" />-->
							</div>
							<div class="operate-status">
								<i class="iconfont">&#xf00a0;</i>
								<span class="status-message"></span>
							</div>
						</div>
						<input type="hidden" name="userId" value="${user.userId}" />
						<input type="hidden" name="userColumns" value="empId,empCode,empStatus,empDegree,empIndate,empOaddress,empOaddress,empOzipcode,empOtel,empFaxno,empOemail">
					</form>
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
				<ul id="tree" class="ztree" style="width:400px;overflow-y:auto;overflow-x:auto"></ul>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>resources/commons/js/seajs/sea-debug.js" ></script>
    <script src="<%=basePath%>resources/commons/js/seajs/sea-config-debug.js"></script>
    <script>
    	seajs.use("<%=basePath%>orm/org/department/js/emp-tab.js");
    </script>
	
</body>
</html>