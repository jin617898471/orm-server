//var addDepTree = null;
define(function(require){
	var $ = require("$"),
	Tabs=require("inno/switchable/1.0.0/tabs-debug");
	Select = require("inno/select/1.0.0/select-debug");
	Scroll = require("inno/scroll/1.0.0/scroll-debug");
	require("gallery/ztree/3.5.2/core-debug");
	require("gallery/ztree/3.5.2/exedit-debug");
	require("gallery/ztree/3.5.14/ztree-debug.css");
	Dialog = require("inno/dialog/1.0.0/dialog-debug");
	Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");


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
	console.log("load dep-manager.js")
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

	//URL
	var basePath = "/orm-server/";
	var urlcfg= {
		depTree : basePath + "org/depTree/",
		updateTree : basePath + "org/depTree/updateNode/",
		orgRoleAssign : basePath + "org/role/assign",
		orgRoleNotAssign : basePath + "org/role/notassign",
		userRoleAssign : basePath + "user/role/assign",
		userRoleNotAssign : basePath + "user/role/notassign",
		
		fowardInstTab: basePath + "org/forward/instTab/",
		fowardDepTab: basePath + "org/forward/depTab/",
		fowardPostTab: basePath + "org/forward/postTab/",
		fowardEmpTab: basePath + "org/forward/empTab/",
		
		deleteOrg : basePath + "org/delete/"
	}
	var tagIndex = 0;
	$(".dep-tabs-nav").on('click', 'li', function(event) {
		tagIndex = $(this).index();
		console.log("tagIndex:" + tagIndex);
	});
	
	frameHeight= function (height){
//		var h = document.getElementById("orgTab").contentWindow.document.body.scrollHeight + "px";
//		console.log(h);
		$("#orgTab").css("height",height);
	}
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
		async: {
			autoParam : [],
			dataFilter : function(treeId, parentNode, responseData){
				var nodes = responseData.data;
				if (nodes) {
					for(var i =0; i < nodes.length; i++) {
						var type = nodes[i].attributes.type;
						switch(type){
						case "I": 
							nodes[i].icon ="/orm-server/orm/css/imgs/I.png";
							break;
						case "O":
							nodes[i].icon ="/orm-server/orm/css/imgs/O.png";
							break;
						case "P":
							nodes[i].icon ="/orm-server/orm/css/imgs/P.png";
							break;
						case "E":
							nodes[i].icon ="/orm-server/orm/css/imgs/E.png";
							break;
						}
					}
				}
				return nodes;
			},
			dataType : "text",
			enable : true,
			otherParam : [],
			type : "post",
			url: function(treeId, treeNode){
				return urlcfg.updateTree + treeNode.id;
			}
		}
	}
	
	addDepTree = function (orgId){
		$.post(urlcfg.depTree + orgId,function(data){
			if(data.status == 200){
				var nodes = data.data;
				var defaultSelect = null;
				for(var i=0;i<nodes.length;i++){
					var type = nodes[i].attributes.type;
					if(orgId == nodes[i].id){
						nodes[i].open = true;
					}
					switch(type){
					case "I": 
						nodes[i].icon ="/orm-server/orm/css/imgs/I.png";
						break;
					case "O":
						nodes[i].icon ="/orm-server/orm/css/imgs/O.png";
						break;
					case "P":
						nodes[i].icon ="/orm-server/orm/css/imgs/P.png";
						break;
					case "E":
						nodes[i].icon ="/orm-server/orm/css/imgs/E.png";
						break;
					}
				}
				$.fn.zTree.init($("#tree"), setting, data.data);
				var defaultSelect = $.fn.zTree.getZTreeObj("tree").getNodes()[0];
				$.fn.zTree.getZTreeObj("tree").selectNode(defaultSelect);
				clickAction(null,null,defaultSelect);
			}else {
				console.log(data.message);
			}

		});
	}
	function initDepTree(){
		var orgId = $("#orgId").val();
		addDepTree(orgId);
	}
	initDepTree()
	var selectedNode = null;
	//点击ztree节点时执行的操作
	function clickAction (event, treeId, treeNode){
		selectedNode = treeNode;
		var type = treeNode.attributes.type;
		var id = treeNode.id;
		switch(type){
		case "I":
			$("#orgTab").attr("src",urlcfg.fowardInstTab + id);
			break;
		case "O":
			$("#orgTab").attr("src",urlcfg.fowardDepTab + id);
			break;
		case "P":
			$("#orgTab").attr("src",urlcfg.fowardPostTab + id);
			break;
		case "E":
			$("#orgTab").attr("src",urlcfg.fowardEmpTab + id);
			break;
		}
//		roleAssign(type,id);
//		if(tagIndex == 1){
//		}
	}
	
	var dialog = new Dialog({
		//trigger:'.sub-operate',
		width:'760px',
//		height:'525px',
		//content:'./dialog-subadd.html',
		hasMask:false,
//		title:'新增下属机构',
		ifEsc:false,
		closeTpl:'&#xe62a;'
	}).before('show',function(url,title,height){
//		var url = urlcfg.forwardAdd pId;
		this.set("content",url);
		this.set("title",title);
		this.set("height",height);
	});
	
	refreshTree = function(type){
		$.fn.zTree.getZTreeObj("tree").reAsyncChildNodes(selectedNode,"refresh");
		orgTab.window.refreshTreeAndList(type);
	}
	
	
	showDialog = function(url,title,height,type){
		dialog.show(url,title,height+"px");
		switch(type){
		case "O":
			$(".ui-dialog").css('boxShadow', '0px 4px 16px #a8adb2');
			$(".ui-dialog-content").css('height', height - 30);
			break;
		case "P":
			$(".ui-dialog").css('boxShadow', '0px 4px 16px #a8adb2');
			$(".ui-dialog-content").css('height', height - 30);
			break;
		case "E":
			$(".ui-dialog").css('boxShadow', '0px 4px 16px #a8adb2');
			$(".ui-dialog-content").css('height', height - 30);
		}
	}
	deleteConfirm = function(url,type){
		Confirmbox.confirm('是否确定要删除该记录？','',function(){
			var parameter={
				url:url,
				type:"POST",
				async:false,
				success:function(data){
					refreshTree(type);
				},
				error:function(result){
					Confirmbox.alert('删除失败！');
				}
			};
			$.ajax( parameter );
		},function(){

		});
	}
	getRootNodeId = function (){
		 var nodes = $.fn.zTree.getZTreeObj("tree").getNodes();
		 for(var i=0;i<nodes.length;i++){
			 if(nodes[i].attributes.type == "I"){
				 return nodes[i].id;
			 }
		 }
	}
	getSelectNode = function(){
		return  $.fn.zTree.getZTreeObj("tree").getSelectedNodes()[0];
	}
	
	
	
	
	
	
	function roleAssign(type,id){
		var roleName = $("#roleName").val();
		var systemId = systemSelect.currentItem.attr("data-value");
		console.log(systemId);
		if(type == "E"){
			$.ajax({
				url: urlcfg.userRoleAssign,
				data: {
					orgId: id,
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
					orgId: id,
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
		}else{
			$.ajax({
				url: urlcfg.orgRoleAssign,
				data: {
					orgId: id,
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
				url: urlcfg.orgRoleNotAssign,
				data: {
					orgId: id,
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
	}
});