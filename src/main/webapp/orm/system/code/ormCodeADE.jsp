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
<!doctype html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>orm/resource/ormcode/css/CodeManager.css">
<base href="<%=basePath%>">
<style></style>
</head>
<body>
	<form class="ui-form clearfix" id="OrmCodeADEForm" data-widget="validator">
		<div class="ui-form-container">
			<div class="ui-form-2item">
				<div class="ui-form-item-column clearfix">
					<div class="ui-form-item-col">
						<label class="ui-label">代码中文名:</label>
						<input class="ui-input ui-input-w190" name="codeValueCn" value="${ormCode.codeValueCn}">
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
					<div class="ui-form-item-col">
						<label class="ui-label">代码值:</label>
						<input class="ui-input ui-input-w190" name="codeValue" value="${ormCode.codeValue}">
						<span class="ui-form-required">*</span>
						<div class="ui-form-explain"></div>
					</div>
				</div>
				<div class="ui-form-item-column clearfix">
					<div class="ui-form-item-col">
						<label class="ui-label">代码英文名:</label>
						<input class="ui-input ui-input-w190"  name ="codeValueEn" value="${ormCode.codeValueEn}">
					</div>
					<div class="ui-form-item-col">
						<label class="ui-label">代码拼音:</label>
						<input class="ui-input ui-input-w190"  name ="codeValuePinyin" value="${ormCode.codeValuePinyin}">
					</div>
				</div>
				<div class="ui-form-item-have2col">
					<label class="ui-label">描述信息:</label>
					<div class="ui-textarea-container">
					    <div class="ui-textarea-border" style="width:92.5%;height:80px;">
						    <textarea class="ui-textarea" name="codeValueDesc">${ormCode.codeValueDesc}</textarea>
						</div>
					</div>
				</div>
				<div class="ui-form-item-column clearfix" style = "display:none">
						<label class="ui-label">codeId:</label>
						<input class="ui-input ui-input-w190"  name ="codeId" value="${ormCode.codeId}">
						<label class="ui-label">pcodeId:</label>
						<input class="ui-input ui-input-w190"  name ="pcodeId" value="${ormCode.pcodeId}">
						<label class="ui-label">validSign:</label>
						<input class="ui-input ui-input-w190"  name ="validSign" value="Y">
						<label class="ui-label">cidxId:</label>
						<input class="ui-input ui-input-w190"  name ="cidxId" value="${ormCode.cidxId}">
				</div>
			</div>
		</div>
		<div class="ui-form-placeholder">
		</div>
		<div class="ui-form-fixed">
			<div class="ui-form-message">
				<i class="iconfont msgiconfont"></i>
				<span class = "msgText"></span>
			</div>
			<div class="ui-form-toolbar">
				<input type="submit" class="ui-button " id="btnSaveContinue" value="保存并继续">
				<input type="submit" class="ui-button " id="btnSave" value="保存">
			 	<input type="button" class="ui-button " id="btnCancel" value="关闭">					
			</div>
		</div>
	</form>
	<input type="hidden" id = "sign" value = "${sign}">
</body>
<script type="text/javascript" src="resources/commons/js/seajs/sea-debug.js" ></script>
<script type="text/javascript" src="resources/commons/js/seajs/sea-config-debug.js"></script>
<script type="text/javascript">
	seajs.use( "<%=basePath%>orm/resource/ormcode/js/ormCodeADE" );
</script>
</html>