<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	/************************ jsp 头 模 块 ***************************/
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>系统登录页面</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>orm/login/css/login.css">
</head>
<body>
	<div class="login-main">
		<div class="login-floater">
		</div>
		<div class="login-banner">
		<form action="login"  method="post" id="logonsubmit">
				<div class="login-container">
					<div class="login-content">
						<div style="text-align:right;">
							<span id="errorMsg" class = "msgText" style="color: red;font-size: 10px text-align:right;margin-right:20px;">
								<c:out value="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}" />
							</span>
					    </div>
						<div class="login-panel">
							<dl>
								<dt>
									用户名:
								</dt>
								<dt>
									<input type="text" id="account" name="account" class="ui-input ui-input-user" title="请输入用户名" value="" />
								</dt>
								<dt>
									密码:
								</dt>
								<dt>
									<input id="password" name="password" class="ui-input ui-input-password" title="请输入密码" value="" type="password" />
								</dt>
								<dt class="login-check">
									<input type="checkbox" id="autoLogin" style="vertical-align: middle;">
									<label for="autoLogin" style="vertical-align: middle;">记住密码</label>
									<a class="forget-pwd" style="vertical-align: middle;">忘记密码?</a>
								</dt>
								<dt class="login-operate">
									<input type="button" class="ui-button" onclick="check_button()" value="登 录">
								</dt>
							</dl>
						</div>
					</div>
					<div class="computer-icon"></div>
					<div id="wenxintishi" style="display:none">
						<div class="login-tip">
							<h3><b>温馨提示:</b> <span id="laqname"></span></h3>
							<ul>
								<li>
									<a href="">
										<img src="<%=basePath%>orm/login/css/imgs/chrome.jpg" alt="">
									</a>
								</li>
								<li>
									<a href="">
										<img src="<%=basePath%>orm/login/css/imgs/firefox.jpg" alt="">
									</a>
								</li>
								<li>
									<a href="">
										<img src="<%=basePath%>orm/login/css/imgs/IE.jpg" alt="">
									</a>
									<span>(IE8.0+)</span>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</form>
			</div>
		<div class="login-footer">
			<span>@ 2014 Innosoft Corporation.版权所有</span>
		</div>
	</div>
</body>
<script type="text/javascript" src="resources/commons/js/seajs/sea-debug.js" ></script>
    <script type="text/javascript" src="resources/commons/js/seajs/sea-config-debug.js"></script>
	<script>
		seajs.use("<%=basePath%>orm/login/js/login");
	</script>
</html>