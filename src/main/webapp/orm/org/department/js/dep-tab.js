define(function(require){
	var $ = require("$"),
		Tabs=require("inno/switchable/1.0.0/tabs-debug"),
		Scroll = require("inno/scroll/1.0.0/scroll-debug");
		require("easyui");
		Form = require("form");
		Confirmbox=require("inno/dialog/1.0.0/confirmbox-debug");
		Dialog = require("inno/dialog/1.0.0/dialog-debug");
	
	var depTab = new Tabs({
		element: '.dep-tabs',
        triggers: '.dep-tabs-nav li',
        panels: '.dep-tabs-content .content-panel',
        triggerType:"click",
        closeStatus:false,
        activeIndex:0
	});

	$(".operate-status").hide();
	
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
		updateDep : basePath + "org/update",
		deleteOrg : basePath + "org/delete/",
		forwardDepAdd : basePath + "org/dep/forward/add/",
		forwardDepEdit : basePath + "org/dep/forward/edit/",
		forwardPostAdd : basePath + "org/post/forward/add/",
		forwardPostEdit : basePath + "org/post/forward/edit/",
		forwardEmpAdd : basePath + "org/emp/forward/add",
		forwardEmpEdit : basePath + "org/emp/forward/edit/"
	}
	var orgId = $("#orgId").val();
	var tabActive = new Array(true,false,false,false,false)
	$(".dep-tabs-nav").on('click', 'li', function(event) {

		var thisIndex = $(this).index();
		
		switch(thisIndex){
		case 0:
			var h = document.body.scrollWidth;
			parent.frameHeight(h);
			break;
		case 1:
			if(!tabActive[1]){
				loadOrgGrid(orgId,"O");
//				console.log(this.height);
				
				tabActive[1] = true;
			}
			break;
		case 2:
			if(!tabActive[2]){
				loadOrgGrid(orgId,"P");
				
				tabActive[2] = true;
			}
			break;
		case 3:
			if(!tabActive[3]){
				loadEmpGrid(orgId);
				tabActive[3] = true;
			}
			break;
		case 4:
			if(!tabActive[4]){
				loadEmpGrid(orgId);
				tabActive[4] = true;
			}
			break;
		}
		var h = document.body.scrollWidth;
		parent.frameHeight(h);
		
	});
	
	function loadOrgGrid(orgId,type){
//		console.log("loadgrid");
		var orgType = null;
		var tableId = null;
		switch(type){
		case "O":
			orgType = "部门";
			tableId = "#dep-table";
			break;
		case "P":
			orgType = "岗位";
			tableId = "#post-table";
			break;
		}
		$(tableId).empty();
		$(tableId).datagrid({
			queryParams : {queryCondition : "{\"rules\" : [{\"field\" : \"parentOrgId\", \"op\" : \"equal\",\"value\":\""+orgId+"\"}" +
			",{\"field\" : \"orgType\", \"op\" : \"equal\",\"value\":\""+type+"\"}" +
			"]," +
			" \"groups\" : [],\"op\" :\"and\"}"},
			nowrap : true,
			autoRowHeight : true,
			striped : true,
			collapsible : true,
			url : urlcfg.childorg,
			columns:[[
				{field:'id',title:'序号',align:'center',
					formatter: function(value,row,index){
						return index + 1;
					}
				},
				{field:'orgName',title: orgType+'名称',align:'center'},
				{field:'orgCode',title: orgType+'代码',align:'center'},
				{field:'operate',title:'操作',align:'center',
					formatter: function(value,row,index){
						return '<a href="javascript:void(0)" class="operate-items operate-items-edit" onclick="editOrg(\''+row.orgId +'\',\''+row.orgType+'\')">编辑</a>' +
						'<a href="javascript:void(0)" class="operate-items" onclick="deleteOrg(\''+row.orgId +'\',\''+row.orgType+'\')">删除</a>';
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
		addUrl: urlcfg.updateDep
	});
	$("#save").click(function(){
		instForm.saveData({
			type: 1,
			successFn: function(result){
				if(result.status == 200){
					$(".operate-status .status-message").text(result.message);
					$(".operate-status").show();
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
	editOrg = function (id,type){
		if(type == "O"){
			parent.showDialog(urlcfg.forwardDepEdit + id,"编辑下属部门",325,"O");
		}else{
			parent.showDialog(urlcfg.forwardPostEdit + id,"编辑下属岗位",225,"p");
		}
	}
	deleteUser = function(id){
		var url = urlcfg.deleteUser + id;
		parent.deleteConfirm(url,"E");
	}
	deleteOrg = function(id,type){
		var url = urlcfg.deleteOrg + id;
		parent.deleteConfirm(url,type);
	};
	
	$("#addDepDiv").on('click', '#addDep', function(event) {
		$(".ui-dialog").css('boxShadow', '0px 4px 16px #a8adb2');
		$(".ui-dialog-content").css('height', 325 - 40);
		parent.showDialog(urlcfg.forwardDepAdd + orgId,"新增下属部门",325,"O");
		
	});
	$("#addPostDiv").on('click', '#addPost', function(event) {
		$(".ui-dialog").css('boxShadow', '0px 4px 16px #a8adb2');
		$(".ui-dialog-content").css('height', 225 - 40);
		parent.showDialog(urlcfg.forwardPostAdd + orgId,"新增下属岗位",225,"P");
		
	});
	$("#addEmpDiv").on('click', '#addEmp', function(event) {
		console.log("addEmp")
		$(".ui-dialog").css('boxShadow', '0px 4px 16px #a8adb2');
		$(".ui-dialog-content").css('height', 525 - 40);
//		parent.dialog.show(urlcfg.forwardEmpAdd + orgId,"新增下属人员","525px");
		parent.showDialog(urlcfg.forwardEmpAdd,"新增下属人员",525,"E");
		
	});
	
//	var dialog = new Dialog({
//		//trigger:'.sub-operate',
//		width:'760px',
////		height:'525px',
//		//content:'./dialog-subadd.html',
//		hasMask:false,
////		title:'新增下属机构',
//		ifEsc:false,
//		closeTpl:'&#xe62a;'
//	}).before('show',function(url,title,height){
////		var url = urlcfg.forwardAdd pId;
//		this.set("content",url);
//		this.set("title",title);
//		this.set("height",height);
//	});
	
	refreshTreeAndList = function(type){
		if(type=="E"){
			loadEmpGrid(orgId)
		}else{
			loadOrgGrid(orgId,type);
		}
	}
	
	
});