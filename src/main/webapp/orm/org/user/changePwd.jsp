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
<style type="text/css">
</style>
</head>
<body>
	<form class="ui-form clearfix" id="UserADEForm" data-widget="validator">
		<div class="ui-form-container">
			<div class="ui-form-1item">
				<div class="ui-form-item-column clearfix">
					<div class="ui-form-item-col userPwd">
					<label class="ui-label">新密码:</label>
					<input class="ui-input ui-input-w190 userPassword" id = "userAcctPwd" name="userPwd" type = "password" value="" >
					<span class="ui-form-required">*</span>
					<div class="ui-form-explain"></div>
				</div>
				<div class="ui-form-item-col userPwd">
					<label class="ui-label">确认新密码:</label>
					<input class="ui-input ui-input-w190 userPassword"  type = "password"  name ="password-confirmation" value="">
					<span class="ui-form-required">*</span>
					<div class="ui-form-explain"></div>
				</div>
				</div>
				<input type="hidden"  id="userId" name="userId" value="${OrmUser.userId}" >
			</div>
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
</body>
<script type="text/javascript" src="resources/commons/js/seajs/sea-debug.js" ></script>
<script type="text/javascript" src="resources/commons/js/seajs/sea-config-debug.js"></script>
<script type="text/javascript">
	seajs.use( "<%=basePath%>orm/org/user/js/changePwd" );
</script>
</html>