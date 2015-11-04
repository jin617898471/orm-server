<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/************************ jsp 头 模 块 ***************************/
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	request.setAttribute("basePath", basePath);
%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>组织机构管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/widget.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>orm/org/org/css/CodeManager.css">
<base href="<%=basePath%>">
</head>
<body>
	<div class="main">
		<div class="ui-grid-row">
			<div class="ui-grid-7">
				<div class="ui-box">
					<div class="ui-box-head">
                        <div class="ui-box-head-container">
                            <h3 class="ui-box-head-title">组织机构管理</h3>
                        </div>
                    </div>
                    <div class="ui-box-container" style="min-height:540px;">
                    	<div class="ui-box-content ui-box-no-padding">
                    		<ul id="tree" class="ztree" style="width:259px;overflow-y:auto;overflow-x:auto;height:538px"></ul>
                    	</div>
                    </div>
				</div>
           	</div>
           	
            <div class="ui-grid-17" id="orgInfo">
            	<div class="ui-box">
            		<div class="ui-box-head">
                        <div class="ui-box-head-container">
                            <h3 class="ui-box-head-title" id="orgPath"></h3>
                        </div>
                    </div>
                    <div class="ui-box-container" id="RYInfo">
                    	<div class="ui-box-content ui-box-no-padding">
                    		<form class="ui-form clearfix" data-widget="validator">
								<div class="ui-form-title ">
									<h5 class="ui-form-title-borderL">人员信息</h5>
									<div class="search-bar">
		                    			<a class="ui-button ui-button-refresh" opttype="ry-update">
		                    				<i class="iconfont">&#xf004b;</i>
		                    				编辑
		                    			</a>
		                    			<a class="ui-button ui-button-delete" opttype="ry-delete">
		                    				<i class="iconfont">&#xf0097;</i>
		                    				删除
		                    			</a>
		                    		</div>
								</div>
								<div class="ui-form-container" contenttype="ryinfo" >
									<div class="ui-form-2item">
										<script id="orgMapTemplate" type="text/html">  
											{{each list}}
													<div class="ui-form-item-have2col" {{if $value.hide}}style="display:none;"{{/if}}>
														<label class="ui-label org-title">{{$value.cn}}:</label>
														<label class="ui-label org-value {{if $value.textarea}}ui-label_width{{/if}}" en="{{$value.en}}">{{$value.text}}</label>
													</div>
											{{/each}}
   										</script>  
									</div>
								</div>
								<div class="ui-form-title ">
									<h5 class="ui-form-title-borderL">组织信息</h5>
									<div class="search-bar" style="display:none;">
		                    			<a class="ui-button ui-button-refresh" opttype="ryorg-update">
		                    				<i class="iconfont">&#xf004b;</i>
		                    				编辑
		                    			</a>
		                    		</div>
								</div>
								<div class="ui-form-container" contenttype="ryorginfo" >
									<div class="ui-form-2item">
										<script id="orgListTemplate" type="text/html">  
											<div class="ui-form-item-have2col">
												<label class="ui-label org-title">所属机构</label>
												<label class="ui-label org-title">所属部门</label>
												<label class="ui-label org-title">所属岗位</label>
											</div>
											{{each list}}
												<div class="ui-form-item-have2col">
													<label class="ui-label">{{$value.Iname}}</label>
													<label class="ui-label">{{$value.oname}}</label>
													<label class="ui-label">{{$value.pname}}</label>
												</div>
											{{/each}}
   										</script>  
									</div>
								</div>
								<div class="ui-form-title ">
									<h5 class="ui-form-title-borderL">角色信息</h5>
									<div class="search-bar">
		                    			<a class="ui-button ui-button-refresh" opttype="ryrole-update">
		                    				<i class="iconfont">&#xf004b;</i>
		                    				编辑
		                    			</a>
		                    		</div>
								</div>
								<div class="ui-form-container" contenttype="ryroleinfo" >
									<div class="ui-form-2item">
										<script id="orgList2Template" type="text/html">  
											<div class="ui-form-item-have2col">
												<label class="ui-label org-title">角色名称</label>
												<label class="ui-label org-title">所属系统</label>
											</div>
											{{each list}}
												<div class="ui-form-item-have2col">
													<label class="ui-label">{{$value.roleName}}</label>
													<label class="ui-label">{{$value.systemName}}</label>
												</div>
											{{/each}}
   										</script>  
									</div>
								</div>
								<div class="ui-form-placeholder"></div>
							</form>
                    	</div>
                    </div>
                    <div class="ui-box-container" id="PInfo">
                    	<div class="ui-box-content ui-box-no-padding">
                    		<form class="ui-form clearfix" data-widget="validator">
								<div class="ui-form-title ">
									<h5 class="ui-form-title-borderL">岗位信息</h5>
									<div class="search-bar">
		                    			<a class="ui-button ui-button-add" opttype="ry-add">
		                    				<i class="iconfont">&#xf00a2;</i>
		                    				新增人员
		                    			</a>
		                    			<a class="ui-button ui-button-refresh" opttype="p-update">
		                    				<i class="iconfont">&#xf004b;</i>
		                    				编辑
		                    			</a>
		                    			<a class="ui-button ui-button-delete" opttype="p-delete">
		                    				<i class="iconfont">&#xf0097;</i>
		                    				删除
		                    			</a>
		                    		</div>
								</div>
								<div class="ui-form-container" contenttype="pinfo" >
									<div class="ui-form-2item">
									</div>
								</div>
								<div class="ui-form-title ">
									<h5 class="ui-form-title-borderL">角色信息</h5>
									<div class="search-bar">
		                    			<a class="ui-button ui-button-refresh" opttype="prole-update">
		                    				<i class="iconfont">&#xf004b;</i>
		                    				编辑
		                    			</a>
		                    		</div>
								</div>
								<div class="ui-form-container" contenttype="proleinfo" >
									<div class="ui-form-2item">
									</div>
								</div>
								<div class="ui-form-placeholder"></div>
							</form>
                    	</div>
                    </div>
                    <div class="ui-box-container" id="OInfo">
                    	<div class="ui-box-content ui-box-no-padding">
                    		<form class="ui-form clearfix" data-widget="validator">
								<div class="ui-form-title ">
									<h5 class="ui-form-title-borderL">部门信息</h5>
									<div class="search-bar">
		                    			<a class="ui-button ui-button-add" opttype="o-add">
		                    				<i class="iconfont">&#xf00a2;</i>
		                    				新增子部门
		                    			</a>
		                    			<a class="ui-button ui-button-add" opttype="p-add">
		                    				<i class="iconfont">&#xf00a2;</i>
		                    				新增岗位
		                    			</a>
		                    			<a class="ui-button ui-button-refresh" opttype="o-update">
		                    				<i class="iconfont">&#xf004b;</i>
		                    				编辑
		                    			</a>
		                    			<a class="ui-button ui-button-delete" opttype="o-delete">
		                    				<i class="iconfont">&#xf0097;</i>
		                    				删除
		                    			</a>
		                    		</div>
								</div>
								<div class="ui-form-container" contenttype="oinfo" >
									<div class="ui-form-2item">
									</div>
								</div>
								<div class="ui-form-placeholder"></div>
							</form>
                    	</div>
                    </div>
                    <div class="ui-box-container" id="IInfo">
                    	<div class="ui-box-content ui-box-no-padding">
                    		<form class="ui-form clearfix" data-widget="validator">
								<div class="ui-form-title ">
									<h5 class="ui-form-title-borderL">机构信息</h5>
									<div class="search-bar">
		                    			<a class="ui-button ui-button-add" opttype="o-add">
		                    				<i class="iconfont">&#xf00a2;</i>
		                    				新增子部门
		                    			</a>
		                    			<a class="ui-button ui-button-refresh" opttype="i-update">
		                    				<i class="iconfont">&#xf004b;</i>
		                    				编辑
		                    			</a>
		                    			<a class="ui-button ui-button-delete" opttype="i-delete">
		                    				<i class="iconfont">&#xf0097;</i>
		                    				删除
		                    			</a>
		                    		</div>
								</div>
								<div class="ui-form-container" contenttype="iinfo" >
									<div class="ui-form-2item">
									</div>
								</div>
								<div class="ui-form-placeholder"></div>
							</form>
                    	</div>
                    </div>
            	</div>
           	</div>
        </div>
	</div>
<script type="text/javascript" src="resources/commons/js/seajs/sea-debug.js" ></script>
<script type="text/javascript" src="resources/commons/js/seajs/sea-config-debug.js"></script>
<script>
	var IOrgId = "d79572935f6f48298a1377255a2b4927" ;
	var basePath="<%=basePath%>";
	seajs.use( "<%=basePath%>orm/org/org/js/orgManager" );
</script>
<script id="orgAddOrUpdate" type="text/html">  
	<form class="ui-form clearfix .org-dialog" data-widget="validator">
			<div class="ui-form-container">
				<div class="ui-form-2item">
				{{each list}}
					<div class="ui-form-item-have2col"  {{if $value.hide}}style="display:none;"{{/if}}>
						<label class="ui-label org-title">{{$value.cn}}:</label>
						{{if $value.textarea}}
							<div class="ui-textarea-border ui-input-w190" style="float: left;height:60px;">
 										<textarea class="ui-textarea" name="{{$value.en}}">{{$value.text}}</textarea>
							</div>
						{{else}}
							<input class="ui-input ui-input-w190 {{$value.en}}" name="{{$value.en}}" {{if $value.disabled}}disabled="disabled"{{/if}} value="{{$value.text}}">
						{{/if}}
					</div>
				{{/each}}
				</div>
			</div>
	</form>
</script> 
</body>
</html>