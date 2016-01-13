define(function(require){
	var $ = require("$"),
		Tabs=require("inno/switchable/1.0.0/tabs-debug"),
		Select = require("inno/select/1.0.0/select-debug"),
		Scroll = require("inno/scroll/1.0.0/scroll-debug");
		require("gallery/ztree/3.5.2/core-debug");
		require("gallery/ztree/3.5.2/exedit-debug");
		require("gallery/ztree/3.5.14/ztree-debug.css");
		require("easyui");
		Form = require("form");
	
	
	var depTab = new Tabs({
		element: '.dep-tabs',
        triggers: '.dep-tabs-nav li',
        panels: '.dep-tabs-content .content-panel',
        triggerType:"click",
        closeStatus:false,
        activeIndex:0
	});

	new Select({
		trigger:'#cond-system',
		width:'200px',
		name : "system",
		model:OrmJsObj.system.getHasRight()
	}).render();

	function addressInput(){

		itemsW = $(".basic-list-items")[0].offsetWidth,
		labelW = $(".list-items-label")[0].offsetWidth,
		inpW = $(".list-items-inp")[0].offsetWidth,
		fullWidth = $(".inp-address").parent().width();

		$(".inp-address").css('width', fullWidth - labelW - (itemsW - labelW - inpW) - 12);

	}
	addressInput();
	$(".codeinf-title").on('click', '.title-ico', function(event) {

		var _detail = $(this).parents(".codeinf-sec"),
			_detail_act = _detail.attr("active");

		if(_detail_act == "0"){

			_detail.addClass('codeinf-sec-fold');
			_detail.attr('active', '1');

		}else if(_detail_act == "1"){

			_detail.removeClass('codeinf-sec-fold');
			_detail.attr('active', '0');

		}		
		
	});

	//待选角色滚动条
	var one = new Scroll({
		trigger:"#col-tbody-wait .ui-scroll",
		container:"#col-tbody-wait .ui-scroll-container"
	});

	//已选角色滚动条
	var two = new Scroll({
		trigger:"#col-tbody-already .ui-scroll",
		container:"#col-tbody-already .ui-scroll-container"
	});

	var userId = $("#userId").val();
	var tabActive = new Array(true,true,false,false);
	$(".dep-tabs-nav").on('click', 'li', function(event) {

		var thisIndex = $(this).index();

		switch(thisIndex){
		case 0:
			break;
		case 1:
			break;
		case 2:
			if(!tabActive[2]){
				roleAssign(userId);
				tabActive[2] = true;
			}
			break;
		case 3:
			if(!tabActive[3]){
				addRoleTree(userId);
			}
			break;
		}
		var h = document.body.scrollWidth;
		parent.frameHeight(h);
		
		
	});
	
	var basePath = "/orm-server/";
	var urlcfg= {
		editUser : basePath + "user/edit",
		userRoleTree : basePath + "user/role/ztree/",
		userRoleAssign : basePath + "user/role/assign",
		userRoleNotAssign : basePath + "user/role/notassign",
		userAddRoles : basePath + "user/role/add",
		userdelRoles : basePath + "user/role/delete"
	}
	
	var userForm = new Form({
		trigger: "#user-info",
		addUrl: urlcfg.editUser
	});
	
	
	$("#userSave").click(function(event){
		userForm.saveData({
			type: 1,
			successFn: function(result){
				if(result.status == 200){
					console.log(result)
				}else{
					console.log(result)
				}
			},
			errorFn: function(){
				console.log(result)
			}
		});
	});
	var empForm = new Form({
		trigger: "#emp-info",
		addUrl: urlcfg.editUser
	});
	
	
	$("#empSave").click(function(event){
		empForm.saveData({
			type: 1,
			successFn: function(result){
				if(result.status == 200){
					console.log(result)
				}else{
					console.log(result)
				}
			},
			errorFn: function(){
				console.log(result)
			}
		});
	});
	
	//创建机构树
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : null
			}
		},
		view : {
			selectedMulti: false,
			showLine : false
		},
		callback : {
			onClick : clickAction
		},
	}
	
	addRoleTree = function (orgId){
		$.post(urlcfg.userRoleTree + orgId,function(data){
			if(data.status == 200){
				var nodes = data.data;
				$.fn.zTree.init($("#tree"), setting, nodes);
				var defaultSelect = $.fn.zTree.getZTreeObj("tree").getNodes()[0];
				$.fn.zTree.getZTreeObj("tree").selectNode(defaultSelect);
				clickAction(null,null,defaultSelect);
			}else {
				console.log(data.message);
			}

		});
	}
	function clickAction (event, treeId, treeNode){
		selectedNode = treeNode;
	}
	function roleAssign(id){
		console.log(id);
		var roleName = $("#roleName").val();
		var systemId = $("#select-system").val();
		console.log(systemId +","+roleName+","+id);
		$.ajax({
			url: urlcfg.userRoleAssign,
			data: {
				userId: id,
				roleName: roleName,
				systemId: systemId
			},
			type: "post",
			dataType: "json",
			success: function(data){
				if(data.status == 200){
					showAssignRoles(data.data);
				}else{
					console.log(data.message);
				}
			},
			error: function(){
				
			}
		});
		$.ajax({
			url: urlcfg.userRoleNotAssign,
			data: {
				userId: id,
				roleName: roleName,
				systemId: systemId
			},
			type: "post",
			dataType: "json",
			success: function(data){
				if(data.status == 200){
					showNotAssignRoles(data.data);
				}else{
					console.log(data.message);
				}
			},
			error: function(){
				
			}
		});
	}
	
	
	function showAssignRoles(data){
		var length = data.length;
		var def = 11 - length;
		var table = $("#assignRole");
		table.empty();
		for(var i=0;i<length;i++){
			table.append(createTr(data[i]));
		}
		$("#assignRole").off('dblclick', '.hasRole');
		$("#assignRole").on('dblclick', '.hasRole', function(event) {
			var roleList = new Array();
			var roleId = $(this).children("input[name=roleId]").val();
			roleList.push(roleId);
			doRoleAssignAjax(roleList,urlcfg.userdelRoles);
		});
		if(def > 0){
			for(var j=0;j<def;j++){
				table.append(createTr(null));
			}
		}
		two.reset();
	}
	function doRoleAssignAjax(json,url){
		var data = {
				userId : userId,
				roles : json
		}
		$.ajax({
			url: url,
            type:"post",
            data: data,
            dataType: 'json',
            success:function(result){
				if(result.status == 200){
					roleAssign(userId);
				}else{
					console.log(result.message);
				}
            },
            error:function(result, err){
            }
		});
	}
	function showNotAssignRoles(data){
		var length = data.length;
		var def = 11 - length;
		var table = $("#notAssignRole");
		table.empty();
		for(var i=0;i<length;i++){
			table.append(createTr(data[i]));
		}
		$("#notAssignRole").off('dblclick', '.hasRole');
		$("#notAssignRole").on('dblclick', '.hasRole', function(event) { 
			var roleList = new Array();
			var roleId = $(this).children("input[name=roleId]").val();
			var systemId = $(this).children("input[name=systemId]").val();
			var role = {
					roleId : roleId,
					systemId : systemId
			};
			roleList.push(role);
			var json = window.JSON.stringify(roleList);
			doRoleAssignAjax(json,urlcfg.userAddRoles);
		});
		if(def > 0){
			for(var j=0;j<def;j++){
				table.append(createTr(null));
			}
		}
		one.reset();
	}
	function createTr(role){
		var html = null;
		if(role){
			html = "<tr class='hasRole'>" +
			"<td class='w39 text-center'><input type='checkbox' class='chk-wait' name='checkbox' /></td>"+
			"<td class='w79'>"+role.roleNameCn+"</td>"+
			"<td>"+role.systemName+"</td>"+
			"<input type='hidden' name='roleId' value='"+role.roleId+"'/>"+
			"<input type='hidden' name='systemId' value='"+role.systemId+"'/>"+
		"</tr>";
		}else{
			html = "<tr>" +
			"<td class='w39 text-center'></td>"+
			"<td class='w79'></td>"+
			"<td></td>"+
		"</tr>";
		}
		return html;
	}
	$("#selectButton").click(function(event){
		var selected = $("#notAssignRole input[name='checkbox']:checked");
		if(selected.length < 1){
			return;
		}
		var roleList = new Array();
		selected.each(function(){
			var roleId = $(this).parent().parent().children("input[name=roleId]").val();
			var systemId = $(this).parent().parent().children("input[name=systemId]").val();
			var role = {
					roleId : roleId,
					systemId : systemId
			};
			roleList.push(role);
		});
		var json = window.JSON.stringify(roleList);
		doRoleAssignAjax(json,urlcfg.userAddRoles);
	});
	$("#selectAllButton").click(function(event){
		var selected =$("#notAssignRole .hasRole");
		console.log(selected);
		if(selected.length < 1){
			return;
		}
		var roleList = new Array();
		selected.each(function(){
			var roleId = $(this).children("input[name=roleId]").val();
			var systemId = $(this).children("input[name=systemId]").val();
			var role = {
					roleId : roleId,
					systemId : systemId
			};
			roleList.push(role);
		});
		var json = window.JSON.stringify(roleList);
		doRoleAssignAjax(json,urlcfg.userAddRoles);
	});
	$("#deleteButton").click(function(event){
		var selected = $("#assignRole input[name='checkbox']:checked");
		if(selected.length < 1){
			return;
		}
		var roleList = new Array();
		selected.each(function(){
			var roleId = $(this).parent().parent().children("input[name=roleId]").val();
			roleList.push(roleId)
		});
		console.log(roleList);
		doRoleAssignAjax(roleList,urlcfg.userdelRoles);
	});
	$("#deleteAllButton").click(function(event){
		var selected =$("#assignRole .hasRole");
		console.log(selected);
		if(selected.length < 1){
			return;
		}
		var roleList = new Array();
		selected.each(function(){
			var roleId = $(this).children("input[name=roleId]").val();
			roleList.push(roleId);
		});
		doRoleAssignAjax(roleList,urlcfg.userdelRoles);
	});
	$("#search").click(function(event){
		roleAssign(userId);
	})
});