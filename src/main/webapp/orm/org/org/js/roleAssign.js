define(function(require){
	var $=require("$");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
	Dialog=require("inno/dialog/1.0.0/dialog-debug");
	Select = require("inno/select/1.0.0/select-debug");
	var artTemplate=require("../../../org/org/js/art-template");
	OrmJsObj.resourceInit($,document);
	
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
	
	function getNotAssignRole(){
		var name = $("#roleName").val();
		var id = $("#systemId").attr("data-value");
		var arr =  [];
		for(var i=0;i<20;i++){
			arr.push({"roleId":"id"+i,"roleName":"管理员"+i,"systemName":"统一用户组织机构及权限管理系统","mapType":"USER_TO_ROLE"});
		}
		return arr;
	}
	function getAssignRole(){
		var arr =  [];
		for(var i=20;i<30;i++){
			arr.push({"roleId":"id"+i,"roleName":"管理员"+i,"systemName":"统一用户组织机构及权限管理系统","mapType":"USER_ORG_TO_ROLE"});
		}
		if("user"==assignObj){
			$.each(arr,function(index,node){
				if(node.mapType=="USER_ORG_TO_ROLE"){
					node.lock=true;
				}
			})
		}
		return arr;
	}
	function addRole(){
		
	}
	function deleteRole(){
		
	}
	
	var systemIdSelect = new Select({
		trigger : '.systemId',
		width : '190px',
		name : 'systemId',
		model : getSystemList(),
	}).render();
	
	function getSystemList(){
		return [{"value":"0001","text":"组织机构权限管理系统"},{"value":"0002","text":"清单编制系统"}];
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
		parent.remove( role );
		addRole( role.attr("id") );
	}
	function cancelRole( role ){
		if("user"==assignObj && "USER_ORG_TO_ROLE"==role.attr("maptype")){
			alert("该角色是从岗位继承的，不允许删除");
			return ;
		}
		var parent = role.parent();
		$(".role-left .content").append(role);		
		parent.remove( role );
		deleteRole( role.attr("id") );
	}
	
});