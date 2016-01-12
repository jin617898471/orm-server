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
		userRoleNotAssign : basePath + "user/role/notassign"
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
//					$(".subadd-bottom-status span").text(result.message);
//					$(".subadd-bottom-status").show();
				}else{
//					$(".subadd-bottom-status span").text(result.message);
//					$(".subadd-bottom-status").show();
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
//					$(".subadd-bottom-status span").text(result.message);
//					$(".subadd-bottom-status").show();
				}else{
//					$(".subadd-bottom-status span").text(result.message);
//					$(".subadd-bottom-status").show();
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
				console.log(data);
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
				console.log(data);
			},
			error: function(){
				
			}
		});
	}
});