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
<!DOCTYPE unspecified PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
<base href="<%=basePath%>">
<style></style>
</head>
<body>
	<form class="ui-form clearfix" id="UserADEForm" data-widget="validator">
		<div class="ui-form-title ">
			<h5 class="ui-form-title-borderL">基本信息</h5>
		</div>
		<div class="ui-form-container">
			<div class="ui-form-2item">
				<div class="ui-form-item-column clearfix">
					<div class="ui-form-item-col">
						<label class="ui-label">用户名称:</label>
						<input class="ui-input ui-input-w190 userAcctCn" name="userName" value="${OrmUser.userName}">
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
					<div class="ui-form-item-col">
						<label class="ui-label">用户账号:</label>
						<input class="ui-input ui-input-w190 userAcct" name="userAcct"	value="${OrmUser.userAcct}">
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
				</div>
				<div class="ui-form-item-have2col">
					<label class="ui-label">组织机构:</label>
					<div class="ui-select-trigger  oserial"   style="width: 358px">
				        <span data-role="trigger-content"></span>        
				       	<i class="iconfont" title="下三角形">&#xf0044;</i>
				       	<input name = "oserial" type = "hidden" />
				    </div>
					<span class="ui-form-required">*</span>
					<div class="ui-form-explain"></div>
				</div>
			</div>
		</div>
		<div class="ui-form-title">
			<h5 class="ui-form-title-borderL">扩展信息</h5>
		</div>
		<div class="ui-form-container">
			<div class="ui-form-2item">
				<div class="ui-form-item-col" style = "display:none">
						<label class="ui-label">用户账号类型:</label>
						<a class="ui-select-trigger ui-select-trigger-none userAcctType" style = "width:188px">
            				<span data-role="trigger-content"></span>          
            				<i class="iconfont blue" title="下三角形">&#xf0044;</i>
        				</a>
        				<span class="ui-form-required">*</span>
        				<div class="ui-form-explain"></div>  
				</div> 
				<div class="ui-form-item-col">
						<label class="ui-label">性别:</label>
						<a class="ui-select-trigger userSex" style = "width:178px">
            				<span data-role="trigger-content"></span>          
            				<i class="iconfont blue" title="下三角形">&#xf0044;</i>
        				</a>
				</div>
				<div class="ui-form-item-col">
					<label class="ui-label">用户生日:</label>
					<input class="ui-input ui-input-w190" name="userBirth" value="${OrmUser.userBirth}"  style="ime-mode:disabled">
					<div class="ui-form-explain"></div>
				</div>
				<div class="ui-form-item-col">
					<label class="ui-label">办公电话:</label>
					<input class="ui-input ui-input-w190" name="userTel" value="${OrmUser.userTel}" >
					<div class="ui-form-explain"></div>
				</div>
				<div class="ui-form-item-col">
					<label class="ui-label">手机号码:</label>
					<input class="ui-input ui-input-w190" name="userMobile" value="${OrmUser.userMobile}" >
					<div class="ui-form-explain"></div>
				</div>
				<div class="ui-form-item-col">
					<label class="ui-label">电子邮箱:</label>
					<input class="ui-input ui-input-w190" name="userEmail" value="${OrmUser.userEmail}" >
					<div class="ui-form-explain"></div>
				</div>
				<div class="ui-form-item-have2col" style = "display:none">
					<input class="ui-input ui-input-w190" name="validSign" value="Y" >
					<div class="ui-form-explain"></div>
				</div>
				<div class="ui-form-item-have2col" style = "display:none">
					<input class="ui-input ui-input-w190" name="userId" value="${OrmUser.userId}" >
					<span class="ui-form-required">*</span>
					<div class="ui-form-explain"></div>
				</div>
			</div>
		</div>
		<div class="ui-form-placeholder">
		</div>
		<div class="ui-form-fixed">
			<div class="ui-form-message">
				<i class="iconfont msgiconfont"></i>
				<span class = "msgText"></span>
<!-- 				<div class="iconfonthide"  style = "display:none"> -->
<!-- 					<i class="iconfont succeed">&#xf007b;</i> -->
<!-- 				</div> -->
<!-- 				<div class="iconfonthide2"  style = "display:none"> -->
<!-- 					<i class="ui-tiptext-icon iconfont" style="color: red">&#xf009f;</i> -->
<!-- 					<span class = "msgText" style="color: red"></span> -->
<!-- 				</div> -->
			</div>
			<div class="ui-form-toolbar">
				<input type="submit" class="ui-button " id="btnSave" value="保存">
			 	<input type="button" class="ui-button " id="btnCancel" value="关闭">					
			</div>
		</div>
	</form>
	<input type="hidden" id = "sign" value = "${sign}">
	<input type="hidden" id = "userSex" value = "${OrmUser.userSex}">
<%-- 	<input type="hidden" id = "oserial" value = "${OrmUser.oserial}"> --%>
</body>
<script type="text/javascript" src="resources/commons/js/seajs/sea-debug.js" ></script>
<script type="text/javascript" src="resources/commons/js/seajs/sea-config-debug.js"></script>
<script type="text/javascript">
	seajs.use( "<%=basePath%>orm/org/user/js/ormUserADE" );
</script>
</html>