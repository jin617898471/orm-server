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

	$(".operate-status").hide();
	
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

	
	var tabInstId = new Array("","");
	var basePath = "/orm-server/";
	var urlcfg= {
		childorg : basePath + "org/children",
		belongEmp : basePath + "org/emps/",
		updatePost : basePath + "org/update",
		forwardEmpAdd : basePath + "org/emp/forward/add",
		forwardEmpEdit : basePath + "org/emp/forward/edit/",
		orgRoleTree : basePath + "org/role/ztree/",
		orgRoleAssign : basePath + "org/role/assign",
		orgRoleNotAssign : basePath + "org/role/notassign"

	}
	var orgId = $("#orgId").val();
	var tabActive = new Array(true,false,false,false);
	$(".dep-tabs-nav").on('click', 'li', function(event) {

		var thisIndex = $(this).index();
		
		switch(thisIndex){
		case 0:
			break;
		case 1:
			if(!tabActive[1]){
				loadEmpGrid(orgId);
				
				tabActive[1] = true;
			}
			break;
		case 2:
			if(!tabActive[2]){
				roleAssign(orgId);
				tabActive[2] = true;
			}
			break;
		case 3:
			if(!tabActive[3]){
				addRoleTree(orgId);
			}
			break;
		}
		var h = document.body.scrollWidth;
		parent.frameHeight(h);
		
	});
	
	function loadEmpGrid(orgId){
//		console.log("loadgrid");
		$("#emp-table").empty();
		$("#emp-table").datagrid({
//			queryParams : {queryCondition : ""},
			nowrap : true,
			autoRowHeight : true,
			striped : true,
			collapsible : true,
			url : urlcfg.belongEmp + orgId,
			columns:[[
				{field:'id',title:'序号',align:'center',
					formatter: function(value,row,index){
						return index + 1;
					}
				},
				{field:'userName',title: '人员名称',align:'center'},
				{field:'userAcct',title: '人员账号',align:'center'},
				{field:'operate',title:'操作',align:'center',
					formatter: function(value,row,index){
						return '<a href="javascript:void(0)" class="operate-items operate-items-edit" onclick="editInst(\''+row.orgId +'\')">编辑</a>' +
								'<a href="javascript:void(0)" class="operate-items" onclick="deleteInst(\''+row.orgId +'\')">删除</a>';
					}
				}
			]],
			pagination : true,
			rownumbers : false,
			pageList : [15,25,50,100],
			onLoadSuccess : function (data) {
				$(this).datagrid("fixColumnSize");
			}
		});
	}
	
	var instForm = new Form({
		trigger: "#org-info",
		addUrl: urlcfg.updatePost
	});
	$("#save").click(function(){
		instForm.saveData({
			type: 1,
			successFn: function(result){
				if(result.status == 200){
//					var selected = $.fn.zTree.getZTreeObj("tree").getSelectedNodes()[0];
//					ztreeNodeAction(null,null,selectedNode);
					$(".operate-status .status-message").text(result.message);
					$(".operate-status").show();
//					console.log("selected: " + selectedNode);
//					$(".operate-status .status-message").text(result.message);
//					$(".operate-status").show();
				}else{
					console.log(result.message);
				}
			},
			errorFn: function(result){
				console.log(result)
			}
		});
	})
	editUser = function(id){
		parent.showDialog(urlcfg.forwardEmpEdit + id,"新增下属人员",565,"E");
	}
	deleteUser = function(id){
		var url = urlcfg.deleteUser + id;
		parent.deleteConfirm(url,"E");
	}
	$("#addEmpDiv").on('click', '#addEmp', function(event) {
		console.log("addEmp")
		$(".ui-dialog").css('boxShadow', '0px 4px 16px #a8adb2');
		$(".ui-dialog-content").css('height', 525 - 40);
//		parent.dialog.show(urlcfg.forwardEmpAdd + orgId,"新增下属人员","525px");
		parent.showDialog(urlcfg.forwardEmpAdd,"新增下属人员","525px","E");
		
	});
	refreshTreeAndList = function(type){
		if(type=="E"){
			loadEmpGrid(orgId)
		}
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
	}
	
	addRoleTree = function (orgId){
		$.post(urlcfg.orgRoleTree + orgId,function(data){
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
		var roleName = $("#roleName").val();
		var systemId = $("#select-system").val();
		console.log(systemId);
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
});