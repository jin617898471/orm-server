define(function(require){
	var $=require("$");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	Dialog=require("inno/dialog/1.0.0/dialog-debug");
	Select = require("inno/select/1.0.0/select-debug");
	var artTemplate=require("../../../org/org/js/art-template");
	
	function setLeftContent(){
		var data = getNotAssignRole();
		var html = artTemplate('roleTemplate', {list:data} );
		$(".role-left").find(".content").html( html );
	}
	function setRightContent(){
		var data = getAssignRole();
		var html = artTemplate('roleTemplate', {list:data} );
		$(".role-right").find(".content").html( html );
	}
	
	var urlCfg = {
		org_assign:"org/role/assign",
		org_notassign:"org/role/notassign",
		org_add:"org/role/add/",
		org_delete:"org/role/delete/",
		user_assign:"org/user/role/assign",
		user_notassign:"org/user/role/notassign",
		user_add:"org/user/role/add/",
		user_delete:"org/user/role/delete/",
		system_list:"resource/ormresource/systemList"
	}
	
	function getUrl( type ){
		var url = urlCfg[ assignObj+"_"+type ];
		return url;
	}
	function getNotAssignRole(){
		var roleName = $("#roleName").val();
		var systemId = $("#select-systemId").val();
		var arr =  [];
		var par = {};
		var url = getUrl("notassign");
		if( assignObj=="org" ){
			par.orgId=orgId;
		}else{
			par.userId=userId;
		}
		par.roleName=roleName;
		par.systemId=systemId;
		$.ajax({
			url:url,
			data:par,
			type:"POST",
			async:false,
			success:function(msg){
				arr = msg ;
			}
		});
		return arr;
	}
	function getAssignRole(){
		var arr =  [];
		var par = {};
		var url = getUrl("assign");
		if( assignObj=="org" ){
			par.orgId=orgId;
		}else{
			par.userId=userId;
		}
		$.ajax({
			url:url,
			data:par,
			type:"POST",
			async:false,
			success:function(msg){
				arr = msg ;
			}
		});
		if("user"==assignObj){
			$.each(arr,function(index,node){
				if(node.mapType=="USER_ORG_TO_ROLE"){
					node.lock=true;
				}
			})
		}
		return arr;
	}
	function addRole(roleId,systemId){
		var url = getUrl("add");
		if( assignObj="org" ){
			url+=orgId;
		}else{
			url+=userId;
		}
		url+="/"+roleId+"/"+systemId;
		$.post(url,function(){
			parent.reloadRightContent();
		});
	}
	function deleteRole(roleId,systemId){
		var url = getUrl("delete");
		if( assignObj=="org" ){
			url+=orgId;
		}else{
			url+=userId;
		}
		url+="/"+roleId;
		$.post(url,function(){
			parent.reloadRightContent();
		});
	}
	
	var systemIdSelect = new Select({
		trigger : '.systemId',
		width : '190px',
		name : 'systemId',
		model : getSystemList(),
	}).render();
	
	// 获取系统name用于列表显示
	function getSystemList(){
		var arr = [];
		var parameterS = {
			url : urlCfg["system_list"],
			type : "POST",
			async : false,
			success : function(data) {
				arr.push({"value":"","text":"&nbsp&nbsp&nbsp&nbsp"});
				$.each(data, function(n, systemObj) {
					modelJSON = {
						value : systemObj.systemId,
						text : systemObj.systemName
					};
					arr.push(modelJSON);
				});
			},
			error : function(result) {
				Confirmbox.alert('获取系统列表数据错误！');
			}
		};
		$.ajax(parameterS);
		return arr;
	}
	
	
	
	setLeftContent();
	setRightContent();
	initCss();
	
	var selectRoleId = "";
	function initCss(){
		$(".content .ui-form-item-have2col").live({
			mouseover:function(){
				$(this).addClass("d_over");
			},
			mouseout:function(){
				$(this).removeClass("d_over");
			},
			dblclick:function(){
				var clss = $(this).parent().parent().attr("class");
				if( clss.indexOf("role-left")>=0 ){
					selectRole( $(this) );
				}else{
					cancelRole( $(this) );
				}
			},
			click:function(){
				$(".content .ui-form-item-have2col").removeClass("d_click");
				$(this).addClass("d_click");
				selectRoleId = $(this).attr("id");
			}
		});
		$(".selectrole").bind({
			click:function(){
				var selected = $("#"+selectRoleId);
				var clss = selected.parent().parent().attr("class");
				if( clss.indexOf("role-left")>=0 ){
					selectRole( selected );
				}else{
					alert("请先选择左侧的角色");
				}
			}
		});
		$(".cancelrole").bind({
			click:function(){
				var selected = $("#"+selectRoleId);
				var clss = selected.parent().parent().attr("class");
				if( clss.indexOf("role-right")>=0 ){
					cancelRole( selected );
				}else{
					alert("请先选择右侧的角色");
				}
			}
		});
		$(".search-bar").bind({
			click:function(){
				setLeftContent();
			}
		});
	}
	function selectRole( role ){
		var parent = role.parent();
		$(".role-right .content").append(role);		
		addRole( role.attr("id"),role.attr("systemId") );
	}
	function cancelRole( role ){
		if("user"==assignObj && "USER_ORG_TO_ROLE"==role.attr("maptype")){
			alert("该角色是从岗位继承的，不允许删除");
			return ;
		}
		var parent = role.parent();
		$(".role-left .content").append(role);		
		deleteRole( role.attr("id"),role.attr("systemId") );
	}
	
});